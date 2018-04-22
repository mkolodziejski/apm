package pl.mkolodziejski.apm.javaagent.util.serializers;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;
import pl.mkolodziejski.apm.javaagent.util.Debug;
import pl.mkolodziejski.apm.javaagent.util.csv.CsvMapper;
import pl.mkolodziejski.apm.javaagent.util.csv.CsvSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public abstract class AbstractCsvMetricsSerializer<T> implements MetricsSerializer {

    protected abstract CsvMapper<T> csvMapper();

    protected abstract Path subDir();

    protected abstract String fileName();

    protected abstract T data();



    @Override
    public void serialize(Path path) {
        CsvSerializer<T> csvSerializer = new CsvSerializer<>(csvMapper());

        Path dirPath = path.resolve(subDir());
        if(!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                Debug.dumpStacktrace(e);
                return;
            }
        }

        Path filePath = dirPath.resolve(fileName());
        boolean fileAlreadyExists = Files.exists(filePath);

        String csv = csvSerializer.serialize(Arrays.asList(data()), fileAlreadyExists);

        try {
            OpenOption[] options;
            if (fileAlreadyExists) {
                options = new OpenOption[]{StandardOpenOption.APPEND};
            } else {
                options = new OpenOption[0];
            }

            Files.write(filePath, csv.getBytes(), options);

        } catch (IOException e) {
            Debug.dumpStacktrace(e);
        }
    }
}
