package pl.mkolodziejski.apm.javaagent.monitors.os.memory;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class OsMemoryUsageMonitor implements MetricsMonitor {

    private static final String MEM_TOTAL = "MemTotal";
    private static final String MEM_FREE = "MemFree";
    private static final String MEM_AVAILABLE = "MemAvailable";
    private static final String SWAP_TOTAL = "SwapTotal";
    private static final String SWAP_FREE = "SwapFree";

    // state of last collection
    OsMemoryUsageInfo osMemoryUsageInfo;


    @Override
    public void collect() {

        Map<String, String> properties;
        try {
            properties = Files.lines(Paths.get("/proc/meminfo"))
                    .map(l -> Arrays.asList(l.split("(\\s)+")))
                    .collect(Collectors.toMap(l -> l.get(0).replace(":", ""), l -> l.get(1)));

        } catch (IOException e) {
            Debug.dumpStacktrace(e);
            return;
        }

        Long memTotal = Long.valueOf(properties.get(MEM_TOTAL));
        Long memFree = Long.valueOf(properties.get(MEM_FREE));
        Long memAvail = Long.valueOf(properties.get(MEM_AVAILABLE));
        Long swapTotal = Long.valueOf(properties.get(SWAP_TOTAL));
        Long swapFree = Long.valueOf(properties.get(SWAP_FREE));

        osMemoryUsageInfo = OsMemoryUsageInfo.builder()
                .setTimestamp(System.currentTimeMillis())
                .setMemoryTotal(memTotal)
                .setMemoryFree(memFree)
                .setMemoryAvailable(memAvail)
                .setSwapTotal(swapTotal)
                .setSwapFree(swapFree)
                .build();
    }

    @Override
    public MetricsSerializer serializer() {
        return new OsMemoryUsageMetricsSerializer(osMemoryUsageInfo);
    }
}
