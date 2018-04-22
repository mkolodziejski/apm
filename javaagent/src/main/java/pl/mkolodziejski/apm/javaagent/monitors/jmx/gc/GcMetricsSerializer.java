package pl.mkolodziejski.apm.javaagent.monitors.jmx.gc;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class GcMetricsSerializer extends AbstractCsvMetricsSerializer<GcInfo> {

    private static final CsvMapper<GcInfo> CSV_MAPPER = CsvMapper.<GcInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("gc_name", e -> e.getGcName())
            .addColumnMapper("count", e -> String.valueOf(e.getCollectionCount()))
            .addColumnMapper("time", e -> String.valueOf(e.getCollectionTime()))
            .build();


    private final GcInfo gcInfo;

    GcMetricsSerializer(GcInfo gcInfo) {
        this.gcInfo = gcInfo;
    }


    @Override
    protected CsvMapper<GcInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("jmx");
    }

    @Override
    protected String fileName() {
        String gcNamePart = gcInfo.getGcName().replaceAll(" ", "_").toLowerCase();
        return "gc-" + gcNamePart + ".csv";
    }

    @Override
    protected GcInfo data() {
        return gcInfo;
    }
}
