package pl.mkolodziejski.apm.javaagent.config.options;

import pl.mkolodziejski.apm.javaagent.config.ConfigImpl;

import java.util.function.BiConsumer;

public class BooleanOption extends Option<Boolean> {
    final Boolean defaultValue;

    public BooleanOption(String name, Boolean defaultValue, BiConsumer<Boolean, ConfigImpl> optionSetter) {
        super(name, optionSetter);
        this.defaultValue = defaultValue;
    }

    @Override
    Boolean getDefaultValue() {
        return defaultValue;
    }

    @Override
    Boolean parse(String value) {
        return Boolean.valueOf(value);
    }
}
