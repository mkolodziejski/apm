package pl.mkolodziejski.apm.javaagent.util.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvMapper<T> {

    private final List<CsvColumnMapper<T>> columnMappers;

    CsvMapper(List<CsvColumnMapper<T>> columnMappers) {
        this.columnMappers = columnMappers;
    }

    public List<CsvColumnMapper<T>> getColumnMappers() {
        return columnMappers;
    }


    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private List<CsvColumnMapper<T>> columnMappers;

        public Builder() {
            columnMappers = new ArrayList<>();
        }

        public Builder<T> addColumnMapper(String column, Function<T, String> valueMapper) {
            this.columnMappers.add(new CsvColumnMapper<>(column, valueMapper));
            return this;
        }

        public CsvMapper<T> build() {
            return new CsvMapper<>(this.columnMappers);
        }
    }
}
