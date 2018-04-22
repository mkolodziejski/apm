package pl.mkolodziejski.apm.javaagent.monitors.os.memory;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class OsMemoryUsageMetricsSerializer extends AbstractCsvMetricsSerializer<OsMemoryUsageInfo> {

    private static final CsvMapper<OsMemoryUsageInfo> CSV_MAPPER = CsvMapper.<OsMemoryUsageInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("memory_total", e -> String.valueOf(e.getMemoryTotal()))
            .addColumnMapper("memory_free", e -> String.valueOf(e.getMemoryFree()))
            .addColumnMapper("memory_available", e -> String.valueOf(e.getMemoryAvailable()))
            .addColumnMapper("swap_total", e -> String.valueOf(e.getSwapTotal()))
            .addColumnMapper("swap_free", e -> String.valueOf(e.getSwapFree()))
            .build();


    private final OsMemoryUsageInfo osMemoryUsageInfo;

    OsMemoryUsageMetricsSerializer(OsMemoryUsageInfo osMemoryUsageInfo) {
        this.osMemoryUsageInfo = osMemoryUsageInfo;
    }


    @Override
    protected CsvMapper<OsMemoryUsageInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("os");
    }

    @Override
    protected String fileName() {
        return "memory.csv";
    }

    @Override
    protected OsMemoryUsageInfo data() {
        return osMemoryUsageInfo;
    }
}
