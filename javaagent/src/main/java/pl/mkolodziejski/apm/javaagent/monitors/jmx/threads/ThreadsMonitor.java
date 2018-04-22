package pl.mkolodziejski.apm.javaagent.monitors.jmx.threads;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadsMonitor implements MetricsMonitor {

    // state of last collection
    private ThreadsInfo threadsInfo;

    @Override
    public void collect() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        threadsInfo = ThreadsInfo.builder()
                .setTimestamp(System.currentTimeMillis())
                .setThreadCount(threadMXBean.getThreadCount())
                .setPeakThreadCount(threadMXBean.getPeakThreadCount())
                .build();

        threadMXBean.resetPeakThreadCount();
    }

    @Override
    public MetricsSerializer serializer() {
        return new ThreadsMetricsSerializer(threadsInfo);
    }
}
