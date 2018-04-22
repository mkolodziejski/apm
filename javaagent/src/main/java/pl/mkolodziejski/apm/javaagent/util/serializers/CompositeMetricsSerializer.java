package pl.mkolodziejski.apm.javaagent.util.serializers;

import pl.mkolodziejski.apm.javaagent.monitors.MetricsSerializer;

import java.nio.file.Path;
import java.util.List;

public class CompositeMetricsSerializer implements MetricsSerializer {

    private final List<? extends MetricsSerializer> metricsSerializers;

    public CompositeMetricsSerializer(List<? extends MetricsSerializer> metricsSerializers) {
        this.metricsSerializers = metricsSerializers;
    }

    @Override
    public void serialize(Path path) {
        for(MetricsSerializer metricsSerializer : metricsSerializers) {
            metricsSerializer.serialize(path);
        }
    }
}
