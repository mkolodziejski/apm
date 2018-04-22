package pl.mkolodziejski.apm.javaagent.util.csv;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class CsvSerializerTest {

    @Test
    public void testSingleColumnNoValues() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Collections.emptyList(), false);

        // then
        assertEquals("col1\n", csv);
    }

    @Test
    public void testSingleColumnNoValuesSkipHeader() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Collections.emptyList(), true);

        // then
        assertEquals("", csv);
    }

    @Test
    public void testSingleColumnSingleValue() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Collections.singletonList(123), false);

        // then
        assertEquals("col1\n123\n", csv);
    }

    @Test
    public void testSingleColumnSingleValueSkipHeader() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Collections.singletonList(123), true);

        // then
        assertEquals("123\n", csv);
    }

    @Test
    public void testMultipleColumnsNoValues() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "" + i)
                .addColumnMapper("col2", i -> "" + i)
                .addColumnMapper("col3", i -> "" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Collections.emptyList(), false);

        // then
        assertEquals("col1;col2;col3\n", csv);
    }

    @Test
    public void testMultipleColumnsMultipleValues() {
        // given
        CsvMapper<Integer> mapper = CsvMapper.<Integer>builder()
                .addColumnMapper("col1", i -> "x" + i)
                .addColumnMapper("col2", i -> "y" + i)
                .addColumnMapper("col3", i -> "z" + i)
                .build();
        CsvSerializer<Integer> serializer = new CsvSerializer<>(mapper);

        // when
        String csv = serializer.serialize(Arrays.asList(111, 222, 333), false);

        // then
        assertEquals("col1;col2;col3\n"
                + "x111;y111;z111\n"
                + "x222;y222;z222\n"
                + "x333;y333;z333\n", csv);
    }

}