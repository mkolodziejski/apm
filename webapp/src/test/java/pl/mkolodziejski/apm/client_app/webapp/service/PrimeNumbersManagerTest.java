package pl.mkolodziejski.apm.client_app.webapp.service;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrimeNumbersManagerTest {
    @Test
    public void primeNumbers() {
        // given
        PrimeNumbersManager manager = new PrimeNumbersManager();

        // when
        List<Integer> primeNumbers = manager.primeNumbers(11);

        // then
        assertEquals(Arrays.asList(2, 3, 5, 7, 11), primeNumbers);
    }


    @Test
    public void isPrime() {
        // given
        PrimeNumbersManager manager = new PrimeNumbersManager();

        // when
        boolean is107Prime = manager.isPrime(107);
        boolean is108Prime = manager.isPrime(108);

        // then
        assertTrue(is107Prime);
        assertFalse(is108Prime);
    }
}