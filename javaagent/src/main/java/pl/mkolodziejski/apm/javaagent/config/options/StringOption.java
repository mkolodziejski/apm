package pl.mkolodziejski.apm.javaagent.config.options;

import pl.mkolodziejski.apm.javaagent.config.ConfigImpl;

import java.util.function.BiConsumer;

public class StringOption extends Option<String> {
    final String defaultValue;

    public StringOption(String name, String defaultValue, BiConsumer<String, ConfigImpl> optionSetter) {
        super(name, optionSetter);
        this.defaultValue = defaultValue;
    }

    @Override
    String getDefaultValue() {
        return defaultValue;
    }

    @Override
    String parse(String value) {
        return value;
    }
}
