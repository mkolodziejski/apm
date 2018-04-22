package pl.mkolodziejski.apm.javaagent.monitors.runtime;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MethodUsageMetricsSerializer extends AbstractCsvMetricsSerializer<MethodUsageInfo> {

    private static final CsvMapper<MethodUsageInfo> CSV_MAPPER = CsvMapper.<MethodUsageInfo>builder()
            .addColumnMapper("timestamp", m -> String.valueOf(m.getTimestamp()))
            .addColumnMapper("signature", m -> m.getSignature())
            .addColumnMapper("count", m -> String.valueOf(m.getCount()))
            .addColumnMapper("minTime", m -> String.valueOf(m.getMinTime()))
            .addColumnMapper("maxTime", m -> String.valueOf(m.getMaxTime()))
            .addColumnMapper("avgTime", m -> String.valueOf(m.getAvgTime()))
            .build();


    private final MethodUsageInfo methodUsage;

    public MethodUsageMetricsSerializer(MethodUsageInfo methodUsage) {
        this.methodUsage = methodUsage;
    }

    @Override
    protected CsvMapper<MethodUsageInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("runtime");
    }

    @Override
    protected String fileName() {
        return methodUsage.getSignature() + ".csv";
    }

    @Override
    protected MethodUsageInfo data() {
        return methodUsage;
    }
}
