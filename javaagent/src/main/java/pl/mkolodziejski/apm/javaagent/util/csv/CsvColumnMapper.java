package pl.mkolodziejski.apm.javaagent.util.csv;

import java.util.function.Function;

class CsvColumnMapper<T> {
    private String column;
    private Function<T, String> valueMapper;

    public CsvColumnMapper(String column, Function<T, String> valueMapper) {
        this.column = column;
        this.valueMapper = valueMapper;
    }

    public String getColumnLabel() {
        return column;
    }

    public String getColumnValue(T t) {
        return valueMapper.apply(t);
    }
}
