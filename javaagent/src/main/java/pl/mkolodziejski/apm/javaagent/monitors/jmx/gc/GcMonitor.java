package pl.mkolodziejski.apm.javaagent.monitors.jmx.gc;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.util.serializers.CompositeMetricsSerializer;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcMonitor implements MetricsMonitor {

    // state of last collection
    private Map<String, GcInfo> cumulativeGcInfoMap;
    private Map<String, GcInfo> gcInfoMap;

    @Override
    public void collect() {
        collectGcInfo();
    }

    @Override
    public MetricsSerializer serializer() {
        List<MetricsSerializer> serializers = new ArrayList<>();
        for(GcInfo gcInfo : gcInfoMap.values()) {
            serializers.add(new GcMetricsSerializer(gcInfo));
        }
        return new CompositeMetricsSerializer(serializers);
    }


    private void collectGcInfo() {
        Map<String, GcInfo> previousCumulativeGcInfoMap = cumulativeGcInfoMap;
        cumulativeGcInfoMap = new HashMap<>();

        long now = System.currentTimeMillis();

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {

            GcInfo cumulativeGcInfo = GcInfo.builder()
                    .setTimestamp(now)
                    .setGcName(garbageCollectorMXBean.getName())
                    .setCollectionCount(garbageCollectorMXBean.getCollectionCount())
                    .setCollectionTime(garbageCollectorMXBean.getCollectionTime())
                    .build();

            cumulativeGcInfoMap.put(cumulativeGcInfo.getGcName(), cumulativeGcInfo);
        }


        // calculate count and time diffs
        if(previousCumulativeGcInfoMap == null) {
            gcInfoMap = cumulativeGcInfoMap;

        } else {
            gcInfoMap = new HashMap<>();
            for(GcInfo cumulativeGcInfo : cumulativeGcInfoMap.values()) {
                GcInfo previousCumulativeGcInfo = previousCumulativeGcInfoMap.get(cumulativeGcInfo.getGcName());

                GcInfo gcInfo = GcInfo.builder()
                        .setTimestamp(now)
                        .setGcName(cumulativeGcInfo.getGcName())
                        .setCollectionCount(cumulativeGcInfo.getCollectionCount() - previousCumulativeGcInfo.getCollectionCount())
                        .setCollectionTime(cumulativeGcInfo.getCollectionTime() - previousCumulativeGcInfo.getCollectionTime())
                        .build();
                gcInfoMap.put(gcInfo.getGcName(), gcInfo);
            }
        }
    }
}
