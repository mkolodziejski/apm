package pl.mkolodziejski.apm.javaagent.monitors;

public interface MetricsMonitor {

    void collect();

    MetricsSerializer serializer();
}
