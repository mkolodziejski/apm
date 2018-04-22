package pl.mkolodziejski.apm.javaagent.monitors.os;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.monitors.os.cpu.CpuUsageMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.os.memory.OsMemoryUsageMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.os.network.NetworkUsageMonitor;
import pl.mkolodziejski.apm.javaagent.util.serializers.CompositeMetricsSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OsMonitor implements MetricsMonitor {

    private final List<MetricsMonitor> monitors;

    public OsMonitor() {
        monitors = new ArrayList<>();

        monitors.add(new CpuUsageMonitor());
        monitors.add(new OsMemoryUsageMonitor());
        monitors.add(new NetworkUsageMonitor());
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
