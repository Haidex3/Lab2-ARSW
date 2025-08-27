package edu.eci.arsw.primefinder;

public class Main {

    public static void main(String[] args) {
        int max = 30000000;
        int numThreads = 3;
        int range = max / numThreads;

        // Crear arreglo para manejar los hilos
        PrimeFinderThread[] threads = new PrimeFinderThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * range;
            int end = (i == numThreads - 1) ? max : (i + 1) * range;

            threads[i] = new PrimeFinderThread(start, end);
            threads[i].start();
        }

        // Esperar a que terminen todos los hilos
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Cálculo terminado con " + numThreads + " hilos.");
    }
}
