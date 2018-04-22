package pl.mkolodziejski.apm.client_app.webapp.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PrimeNumbersManager {

    public List<Integer> primeNumbers(int boundary) {

        int boundaryIncl = boundary + 1;

        ArrayList<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[boundaryIncl];

        for (int i = 2; i < boundaryIncl; i++) {
            isPrime[i] = true;
        }


        for (int i = 2; i < boundaryIncl; i++) {
            if(!isPrime[i]) {
                continue;
            }

            primes.add(i);

            for (int j = i * 2; j < boundaryIncl; j += i) {
                isPrime[j] = false;
            }
        }

        return primes;
    }


    public boolean isPrime(int number) {
        return Collections.binarySearch(primeNumbers(number), number) >= 0;
    }
}
