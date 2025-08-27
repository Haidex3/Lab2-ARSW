package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int max = 30000000;
        int numThreads = 3;
        int range = max / numThreads;

        PrimeFinderThread[] threads = new PrimeFinderThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * range;
            int end = (i == numThreads - 1) ? max : (i + 1) * range;
            threads[i] = new PrimeFinderThread(start, end);
            threads[i].start();
        }

        Thread.sleep(5000);

        for (PrimeFinderThread t : threads) {
            t.pauseThread();
        }

        int total = 0;
        for (PrimeFinderThread t : threads) {
            total += t.getPrimes().size();
        }
        System.out.println("==> Primos encontrados hasta ahora: " + total);

        System.out.println("Presione ENTER para continuar...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();

        for (PrimeFinderThread t : threads) {
            t.resumeThread();
        }

        for (PrimeFinderThread t : threads) {
            t.join();
        }

        System.out.println("==> Cálculo terminado. Total de primos: " + total);
    }
}
