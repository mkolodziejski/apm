package pl.mkolodziejski.apm.javaagent.monitors.os.cpu;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class CpuUsageMetricsSerializer extends AbstractCsvMetricsSerializer<CpuUsageInfo> {

    private static final CsvMapper<CpuUsageInfo> CSV_MAPPER = CsvMapper.<CpuUsageInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("avg_use", e -> String.valueOf(e.getAvgUse()))
            .build();


    private final CpuUsageInfo cpuUsageInfo;

    CpuUsageMetricsSerializer(CpuUsageInfo cpuUsageInfo) {
        this.cpuUsageInfo = cpuUsageInfo;
    }


    @Override
    protected CsvMapper<CpuUsageInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("os");
    }

    @Override
    protected String fileName() {
        return "cpu.csv";
    }

    @Override
    protected CpuUsageInfo data() {
        return cpuUsageInfo;
    }
}
