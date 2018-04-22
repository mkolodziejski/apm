package pl.mkolodziejski.apm.javaagent.monitors.jmx.threads;

import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.serializers.AbstractCsvMetricsSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

class ThreadsMetricsSerializer extends AbstractCsvMetricsSerializer<ThreadsInfo> {

    private static final CsvMapper<ThreadsInfo> CSV_MAPPER = CsvMapper.<ThreadsInfo>builder()
            .addColumnMapper("timestamp", e -> String.valueOf(e.getTimestamp()))
            .addColumnMapper("thread_count", e -> String.valueOf(e.getThreadCount()))
            .addColumnMapper("peak_thread_count", e -> String.valueOf(e.getPeakThreadCount()))
            .build();


    private final ThreadsInfo threadsInfo;

    ThreadsMetricsSerializer(ThreadsInfo threadsInfo) {
        this.threadsInfo = threadsInfo;
    }


    @Override
    protected CsvMapper<ThreadsInfo> csvMapper() {
        return CSV_MAPPER;
    }

    @Override
    protected Path subDir() {
        return Paths.get("jmx");
    }

    @Override
    protected String fileName() {
        return "threads.csv";
    }

    @Override
    protected ThreadsInfo data() {
        return threadsInfo;
    }
}
