package com.example.projectloom.projectloom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class HomeController {

    @GetMapping("/")
    public HashMap<String, String> GetData() {
        // Use a fixed thread pool to simulate a scoped executor
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            // Submit tasks to fetch data from different sources
            Future<String> data1 = executor.submit(() -> fetchDataFromSource1());
            Future<String> data2 = executor.submit(() -> fetchDataFromSource2());
            try {
                // Process the results of the tasks
                String result1 = data1.get();
                String result2 = data2.get();
                processResults(result1, result2);
                var obj = new HashMap<String, String>();
                obj.put("result-1", result1);
                obj.put("result-2", result2);
                return obj;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            // Both tasks will be completed or canceled when the scope exits
        }
        return null;
    }

    private static String fetchDataFromSource1() {
        // Simulate fetching data from source 1
        System.out.println("Fetching data from source 1...");
        try {
            Thread.sleep(1000); // Simulate 1 second of work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Fetching data from source 1 was interrupted");
        }
        return "Data from source 1";
    }

    private static String fetchDataFromSource2() {
        // Simulate fetching data from source 2
        System.out.println("Fetching data from source 2...");
        try {
            Thread.sleep(1500); // Simulate 1.5 seconds of work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Fetching data from source 2 was interrupted");
        }
        return "Data from source 2";
    }

    private static void processResults(String result1, String result2) {
        // Process the combined results
        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
    }
}
