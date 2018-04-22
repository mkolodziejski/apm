package pl.mkolodziejski.apm.javaagent.util.csv;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CsvSerializer<T> {

    private final CsvMapper<T> mapper;

    public CsvSerializer(CsvMapper<T> mapper) {
        this.mapper = mapper;
    }

    public String serialize(Collection<T> data, boolean skipHeader) {
        StringBuilder sb = new StringBuilder();
        List<CsvColumnMapper<T>> columnMappers = mapper.getColumnMappers();

        if(!skipHeader) {
            writeHeader(sb, columnMappers);
        }
        writeData(data, sb, columnMappers);

        return sb.toString();
    }

    private void writeHeader(StringBuilder sb, List<CsvColumnMapper<T>> columnMappers) {
        String line = columnMappers.stream()
                .map(mapper -> mapper.getColumnLabel())
                .collect(Collectors.joining(";"));

        sb.append(line);
        sb.append("\n");
    }

    private void writeData(Collection<T> data, StringBuilder sb, List<CsvColumnMapper<T>> columnMappers) {
        for(T element : data) {
            String line = columnMappers.stream()
                    .map(mapper -> mapper.getColumnValue(element))
                    .collect(Collectors.joining(";"));

            sb.append(line);
            sb.append("\n");
        }
    }
}