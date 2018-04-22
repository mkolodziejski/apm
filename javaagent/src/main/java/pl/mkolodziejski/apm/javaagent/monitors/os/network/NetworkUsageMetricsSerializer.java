package pl.mkolodziejski.apm.javaagent.monitors.os.network;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class NetworkUsageMetricsSerializer extends AbstractCsvMetricsSerializer<NetworkUsageInfo> {

    private static final CsvMapper<NetworkUsageInfo> CSV_MAPPER = CsvMapper.<NetworkUsageInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("interface", e -> e.getInterfaceName())
            .addColumnMapper("bytes_received", e -> String.valueOf(e.getBytesReceived()))
            .addColumnMapper("bytes_sent", e -> String.valueOf(e.getBytesSent()))
            .build();


    private final NetworkUsageInfo networkUsageInfo;

    NetworkUsageMetricsSerializer(NetworkUsageInfo networkUsageInfo) {
        this.networkUsageInfo = networkUsageInfo;
    }


    @Override
    protected CsvMapper<NetworkUsageInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("os");
    }

    @Override
    protected String fileName() {
        String interfaceName = networkUsageInfo.getInterfaceName().toLowerCase();
        return "network-" + interfaceName + ".csv";
    }

    @Override
    protected NetworkUsageInfo data() {
        return networkUsageInfo;
    }
}
