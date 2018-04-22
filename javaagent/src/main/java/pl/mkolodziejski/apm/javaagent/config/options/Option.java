package pl.mkolodziejski.apm.javaagent.config.options;

import pl.mkolodziejski.apm.javaagent.config.ConfigImpl;

import java.util.Map;
import java.util.function.BiConsumer;

public abstract class Option<T> {
    final String name;
    final BiConsumer<T, ConfigImpl> optionSetter;

    Option(String name, BiConsumer<T, ConfigImpl> optionSetter) {
        this.name = name;
        this.optionSetter = optionSetter;
    }

    abstract T parse(String value);

    abstract T getDefaultValue();

    public void set(Map<String, String> options, ConfigImpl config) {
        String optionalStringValue = options.get(name);

        T value;
        if(optionalStringValue == null) {
            value = getDefaultValue();
        } else {
            value = parse(optionalStringValue);
        }

        optionSetter.accept(value, config);
    }
}
