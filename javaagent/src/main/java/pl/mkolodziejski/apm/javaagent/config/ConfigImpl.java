package pl.mkolodziejski.apm.javaagent.config;

import pl.mkolodziejski.apm.javaagent.config.options.BooleanOption;
import pl.mkolodziejski.apm.javaagent.config.options.IntegerOption;
import pl.mkolodziejski.apm.javaagent.config.options.Option;
import pl.mkolodziejski.apm.javaagent.config.options.StringOption;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigImpl implements Config {


    private static final List<Option> OPTIONS;

    static {
        OPTIONS = new ArrayList<>();

        OPTIONS.add(new IntegerOption("interval", 60, (i, c) -> c.setMetricsCollectionInterval(i)));
        OPTIONS.add(new StringOption("logPath", "/apm/logs", (s, c) -> c.setLogPath(s)));
        OPTIONS.add(new StringOption("instrumentationPackage",
                                     "pl.mkolodziejski.apm.client_app",
                                     (s, c) -> c.setInstrumentationPackage(s)));
        OPTIONS.add(new StringOption("instrumentationAnnotatonClass",
                                     "org.springframework.web.bind.annotation.RequestMapping",
                                     (s, c) -> c.setInstrumentationAnnotatonClass(s)));
        OPTIONS.add(new BooleanOption("debugOn", Boolean.TRUE, (b, c) -> c.setDebugOn(b)));
    }


    private Integer metricsCollectionInterval;
    private String logPath;
    private String instrumentationPackage;
    private String instrumentationAnnotatonClass;
    private Boolean debugOn;


    private ConfigImpl() {
    }

    @Override
    public Integer getMetricsCollectionInterval() {
        return metricsCollectionInterval;
    }

    @Override
    public String getLogPath() {
        return logPath;
    }

    @Override
    public String getInstrumentationPackage() {
        return instrumentationPackage;
    }

    @Override
    public String getInstrumentationAnnotatonClass() {
        return instrumentationAnnotatonClass;
    }

    @Override
    public Boolean isDebugOn() {
        return debugOn;
    }


    private void setMetricsCollectionInterval(Integer metricsCollectionInterval) {
        this.metricsCollectionInterval = metricsCollectionInterval;
    }

    private void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    private void setInstrumentationPackage(String instrumentationPackage) {
        this.instrumentationPackage = instrumentationPackage;
    }

    private void setInstrumentationAnnotatonClass(String instrumentationAnnotatonClass) {
        this.instrumentationAnnotatonClass = instrumentationAnnotatonClass;
    }

    private void setDebugOn(Boolean debugOn) {
        this.debugOn = debugOn;
    }

    public static ConfigImpl parse(String argsString) {
        Map<String, String> options;

        if(argsString != null) {
            String[] args = argsString.split(",");
            options = Stream.of(args)
                    .peek(Debug::print)
                    .filter(arg -> arg.contains("="))
                    .collect(Collectors.toMap(
                            arg -> arg.substring(0, arg.indexOf("=")),
                            arg -> arg.substring(arg.indexOf("=") + 1)));
        } else {
            options = new HashMap<>();
        }

        return parse(options);
    }

    public static ConfigImpl parse(Map<String, String> options) {
        ConfigImpl config = new ConfigImpl();
        OPTIONS.forEach(o -> o.set(options, config));
        return config;
    }
}