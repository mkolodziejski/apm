package pl.mkolodziejski.apm.javaagent.monitors.os.cpu;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CpuUsageMonitor implements MetricsMonitor {

    // state of last collection
    CpuUsageInfo cpuUsageInfo;
    long sum;
    long idle;

    @Override
    public void collect() {
        long previousSum = (cpuUsageInfo != null) ? sum : 0;
        long previousIdle = (cpuUsageInfo != null) ? idle : 0;

        String cpuAggregateLine;
        try {
            cpuAggregateLine = Files.lines(Paths.get("/proc/stat"))
                    .filter(l -> l.startsWith("cpu"))
                    .findFirst()
                    .get();

        } catch (IOException e) {
            Debug.dumpStacktrace(e);
            return;
        }


        // based on description from: http://www.linuxhowtos.org/System/procstat.htm

        String[] split = cpuAggregateLine.split("(\\s)+");

        sum = Stream.of(split)
                .skip(1) // skipping label
                .mapToLong(Long::valueOf)
                .sum();

        idle = Long.valueOf(split[4]);

        long diffSum = sum - previousSum;
        long diffIdle = idle - previousIdle;

        int avgUse = (int) (100 - (diffIdle * 100) / diffSum);

        cpuUsageInfo = CpuUsageInfo.builder()
                .setTimestamp(System.currentTimeMillis())
                .setAvgUse(avgUse)
                .build();
    }

    @Override
    public MetricsSerializer serializer() {
        return new CpuUsageMetricsSerializer(cpuUsageInfo);
    }
}
