package pl.mkolodziejski.apm.javaagent.config;

public interface Config {

    Integer getMetricsCollectionInterval();

    String getLogPath();

    String getInstrumentationPackage();

    String getInstrumentationAnnotatonClass();

    Boolean isDebugOn();
}
