package pl.mkolodziejski.apm.javaagent.monitors;

import java.nio.file.Path;

public interface MetricsSerializer {

    void serialize(Path path);
}
