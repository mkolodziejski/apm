package pl.mkolodziejski.apm.javaagent.monitors.jmx;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.monitors.jmx.gc.GcMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.jmx.memory.JvmMemoryMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.jmx.threads.ThreadsMonitor;
import pl.mkolodziejski.apm.javaagent.util.serializers.CompositeMetricsSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JmxMonitor implements MetricsMonitor {

    private final List<MetricsMonitor> monitors;

    public JmxMonitor() {
        monitors = new ArrayList<>();

        monitors.add(new GcMonitor());
        monitors.add(new JvmMemoryMonitor());
        monitors.add(new ThreadsMonitor());
    }

    @Override
    public void collect() {
        monitors.forEach(MetricsMonitor::collect);
    }

    @Override
    public MetricsSerializer serializer() {
        List<MetricsSerializer> serializers = monitors.stream()
                .map(MetricsMonitor::serializer)
                .collect(Collectors.toList());
        return new CompositeMetricsSerializer(serializers);
    }
}
