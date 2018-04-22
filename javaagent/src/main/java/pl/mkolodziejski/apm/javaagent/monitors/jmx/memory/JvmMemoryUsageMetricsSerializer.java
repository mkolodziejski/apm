package pl.mkolodziejski.apm.javaagent.monitors.jmx.memory;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class JvmMemoryUsageMetricsSerializer extends AbstractCsvMetricsSerializer<JvmMemoryUsageInfo> {

    private static final CsvMapper<JvmMemoryUsageInfo> CSV_MAPPER = CsvMapper.<JvmMemoryUsageInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("init", e -> String.valueOf(e.getInit()))
            .addColumnMapper("used", e -> String.valueOf(e.getUsed()))
            .addColumnMapper("committed", e -> String.valueOf(e.getCommitted()))
            .addColumnMapper("max", e -> String.valueOf(e.getMax()))
            .build();


    private final JvmMemoryUsageInfo jvmMemoryUsageInfo;

    JvmMemoryUsageMetricsSerializer(JvmMemoryUsageInfo jvmMemoryUsageInfo) {
        this.jvmMemoryUsageInfo = jvmMemoryUsageInfo;
    }


    @Override
    protected CsvMapper<JvmMemoryUsageInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("jmx");
    }

    @Override
    protected String fileName() {
        return "jvm_memory_usage.csv";
    }

    @Override
    protected JvmMemoryUsageInfo data() {
        return jvmMemoryUsageInfo;
    }
}
