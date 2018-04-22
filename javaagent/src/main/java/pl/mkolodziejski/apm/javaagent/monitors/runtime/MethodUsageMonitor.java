package pl.mkolodziejski.apm.javaagent.monitors.runtime;

import pl.mkolodziejski.apm.javaagent.util.serializers.CompositeMetricsSerializer;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class MethodUsageMonitor implements MetricsMonitor {

    // state of last collection
    List<MethodUsageInfo> methodUsages;

    @Override
    public void collect() {
        methodUsages = new ArrayList<>();

        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> methodExecutions =
                MethodExecutionCollector.getInstance().getMethodExecutions();

        long now = System.currentTimeMillis();

        for(String signature : methodExecutions.keySet()) {
            Queue<MethodExecutionInfo> singleMethodExecutions = methodExecutions.get(signature);

            LongSummaryStatistics stats = singleMethodExecutions.stream()
                    .collect(Collectors.summarizingLong(MethodExecutionInfo::getExecutionTime));


            MethodUsageInfo methodUsageInfo = MethodUsageInfo.builder()
                    .setTimestamp(now)
                    .setSignature(signature)
                    .setCount(stats.getCount())
                    .setMinTime(stats.getMin())
                    .setMaxTime(stats.getMax())
                    .setAvgTime((long) stats.getAverage())
                    .build();

            methodUsages.add(methodUsageInfo);
        }
    }

    @Override
    public MetricsSerializer serializer() {
        List<MethodUsageMetricsSerializer> serializers = methodUsages.stream()
                .map(MethodUsageMetricsSerializer::new)
                .collect(Collectors.toList());

        return new CompositeMetricsSerializer(serializers);
    }
}
