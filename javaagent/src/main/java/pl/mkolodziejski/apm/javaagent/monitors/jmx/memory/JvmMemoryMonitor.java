package pl.mkolodziejski.apm.javaagent.monitors.jmx.memory;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class JvmMemoryMonitor implements MetricsMonitor {

    // state of last collection
    private JvmMemoryUsageInfo jvmMemoryUsageInfo;

    @Override
    public void collect() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        jvmMemoryUsageInfo = JvmMemoryUsageInfo.builder()
                .setTimestamp(System.currentTimeMillis())
                .setInit(heapMemoryUsage.getInit())
                .setUsed(heapMemoryUsage.getUsed())
                .setCommitted(heapMemoryUsage.getCommitted())
                .setMax(heapMemoryUsage.getMax())
                .build();
    }

    @Override
    public MetricsSerializer serializer() {
        return new JvmMemoryUsageMetricsSerializer(jvmMemoryUsageInfo);
    }
}
