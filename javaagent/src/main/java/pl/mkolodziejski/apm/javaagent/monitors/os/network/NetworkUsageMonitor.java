package pl.mkolodziejski.apm.javaagent.monitors.os.network;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsMonitor;
import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.util.Debug;
import pl.mkolodziejski.apm.javaagent.util.serializers.CompositeMetricsSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class NetworkUsageMonitor implements MetricsMonitor {

    // state of last collection
    private Map<String, NetworkUsageInfo> interfacesUsageInfo;
    private Map<String, NetworkUsageInfo> cumulativeInterfacesUsageInfo;


    @Override
    public void collect() {
        Map<String, NetworkUsageInfo> previousCumulativeInfo = cumulativeInterfacesUsageInfo;
        long now = System.currentTimeMillis();

        Map<String, List<String>> interfacesData;
        try {
            interfacesData = Files.lines(Paths.get("/proc/net/dev"))
                    .skip(2) // header
                    .map(String::trim)
                    .map(l -> Arrays.asList(l.split("(\\s)+")))
                    .collect(Collectors.toMap(l -> l.get(0).replace(":", ""), l -> l));

        } catch (IOException e) {
            Debug.dumpStacktrace(e);
            return;
        }


        cumulativeInterfacesUsageInfo = new HashMap<>();
        for(String interfaceName : interfacesData.keySet()) {
            List<String> data = interfacesData.get(interfaceName);

            NetworkUsageInfo networkUsageInfo = NetworkUsageInfo.builder()
                    .setTimestamp(now)
                    .setInterfaceName(interfaceName)
                    .setBytesReceived(Long.valueOf(data.get(1)))
                    .setBytesSent(Long.valueOf(data.get(9)))
                    .build();

            cumulativeInterfacesUsageInfo.put(interfaceName, networkUsageInfo);
        }


        // calculate diffs
        interfacesUsageInfo = new HashMap<>();
        for(String interfaceName : interfacesData.keySet()) {
            long received = 0L;
            long sent = 0L;

            if(previousCumulativeInfo != null) {
                NetworkUsageInfo prevoiusInfo = previousCumulativeInfo.get(interfaceName);
                NetworkUsageInfo currentInfo = cumulativeInterfacesUsageInfo.get(interfaceName);

                received = currentInfo.getBytesReceived() - prevoiusInfo.getBytesReceived();
                sent = currentInfo.getBytesSent() - prevoiusInfo.getBytesSent();
            }

            NetworkUsageInfo networkUsageInfo = NetworkUsageInfo.builder()
                    .setTimestamp(now)
                    .setInterfaceName(interfaceName)
                    .setBytesReceived(received)
                    .setBytesSent(sent)
                    .build();

            interfacesUsageInfo.put(interfaceName, networkUsageInfo);
        }


    }

    @Override
    public MetricsSerializer serializer() {
        List<MetricsSerializer> serializers = new ArrayList<>();

        for(NetworkUsageInfo networkUsageInfo : interfacesUsageInfo.values()) {
            serializers.add(new NetworkUsageMetricsSerializer(networkUsageInfo));
        }

        return new CompositeMetricsSerializer(serializers);
    }
}
