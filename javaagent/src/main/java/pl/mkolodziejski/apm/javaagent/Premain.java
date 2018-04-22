package pl.mkolodziejski.apm.javaagent;

import pl.mkolodziejski.apm.javaagent.asm.ClassTransformer;
import pl.mkolodziejski.apm.javaagent.config.Config;
import pl.mkolodziejski.apm.javaagent.config.ConfigFactory;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.monitors.jmx.JmxMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.os.OsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.runtime.MethodUsageMonitor;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.lang.instrument.Instrumentation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Premain {

    private static final List<MetricsMonitor> METRICS_MONITORS;
    private static final ScheduledExecutorService EXECUTOR;

    // configuration
    private static Config config;

    static {
        METRICS_MONITORS = new ArrayList<>();
        METRICS_MONITORS.add(new MethodUsageMonitor());
        METRICS_MONITORS.add(new JmxMonitor());
        METRICS_MONITORS.add(new OsMonitor());

        EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    }


    public static void premain(String args, Instrumentation instrumentation) {
        Debug.print("************************");
        Debug.print("       PREMAIN");
        Debug.print("************************");

        config = ConfigFactory.getConfig(args);
        Debug.setIsDebugMode(config.isDebugOn());

        instrumentation.addTransformer(new ClassTransformer(config));

        scheduleCollectingMetrics();
    }


    private static void scheduleCollectingMetrics() {
        EXECUTOR.scheduleWithFixedDelay(() -> {
            try {
                Path logPath = Paths.get(config.getLogPath());

                METRICS_MONITORS.forEach(MetricsMonitor::collect);
                METRICS_MONITORS.forEach(mm -> {
                    MetricsSerializer serializer = mm.serializer();
                    serializer.serialize(logPath);
                });

            } catch (Exception e) {
                Debug.dumpStacktrace(e);
            }
        }, 0L, config.getMetricsCollectionInterval(), TimeUnit.SECONDS);
    }
}
