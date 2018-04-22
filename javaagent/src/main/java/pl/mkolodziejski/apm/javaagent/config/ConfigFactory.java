package pl.mkolodziejski.apm.javaagent.config;

public class ConfigFactory {

    public static Config getConfig(String argsString) {
        return ConfigImpl.parse(argsString);
    }
}
