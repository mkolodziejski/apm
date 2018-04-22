package pl.mkolodziejski.apm.javaagent.config.options;

import pl.mkolodziejski.apm.javaagent.config.ConfigImpl;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.util.function.BiConsumer;

public class IntegerOption extends Option<Integer> {
    final Integer defaultValue;

    public IntegerOption(String name, Integer defaultValue, BiConsumer<Integer, ConfigImpl> optionSetter) {
        super(name, optionSetter);
        this.defaultValue = defaultValue;
    }

    @Override
    Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    Integer parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return getDefaultValue();
        }
    }
}
