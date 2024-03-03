import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Map<String, Integer> minResults = new HashMap<>();
        ArrayList<Object> objects = new ArrayList<>();
        ArrayList<Object> testObjects = new ArrayList<>();
        ArrayList<Result> results;
        final int k = 5;

        try (BufferedReader in = new BufferedReader(new FileReader("train-set.csv"))) {
            String str;
            while ((str = in.readLine()) != null) {
                String[] tokens = str.split(";");
                Object obj = new Object();
                for (int i = 0; i < tokens.length; i++) {
                    if (i + 1 < tokens.length) {
                        obj.addAttribute(Double.parseDouble(tokens[i]));
                    } else {
                        obj.setName(tokens[i]);
                    }
                }
                objects.add(obj);
            }
        }

        BufferedReader test = new BufferedReader(new FileReader("test-set.csv"));
        String line;
        while ((line = test.readLine()) != null) {
            String[] tokens = line.split(";");
            Object obj = new Object();
            for (int i = 0; i < tokens.length; i++) {
                if (i + 1 < tokens.length) {
                    obj.addAttribute(Double.parseDouble(tokens[i]));
                } else {
                    obj.setName(tokens[i]);
                }
            }
            testObjects.add(obj);
        }

        int attributeCount = objects.get(0).attributes.size();
        double testObjectCount = testObjects.size();
        double correctCount = 0;

        for (Object testObject : testObjects) {
            results = calculateResults(objects, attributeCount, testObject.attributes);
            results = sortResults(results);
            String answer = classify(results, minResults, k);
            System.out.println(answer + " poprawna odp: " + testObject.name);
            if (answer.equals(testObject.name)) {
                correctCount++;
            }
            results.clear();
            minResults.clear();
        }

        double accuracy = correctCount / testObjectCount;
        System.out.println("dokladnosc: " + accuracy);

        double[] inputAttributes = new double[attributeCount];
        Scanner scan = new Scanner(System.in);
        System.out.println("tryb wpisywania ręcznego");
        while (true) {
            for (int i = 0; i < attributeCount; i++) {
                System.out.println("podaj atrybut " + i + " w formie: (liczba, liczba)");
                boolean error = true;
                do {
                    if (scan.hasNextDouble()) {
                        inputAttributes[i] = scan.nextDouble();
                        error = false;
                    } else {
                        System.out.println("zły format, napisz jeszcze raz.");
                    }
                    scan.nextLine();
                } while (error);
            }
            results = calculateResults(objects, attributeCount, inputAttributes);
            results = sortResults(results);
            String answer = classify(results, minResults, k);
            System.out.println("odpowiedz: " + answer + " poprawnosc: " + accuracy);
        }
    }

    public static <K, V extends Comparable<V>> K maxUsingIteration(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    public static ArrayList<Result> sortResults(ArrayList<Result> results) {
        Collections.sort(results, (o1, o2) -> o2.getResult() < o1.getResult() ? 1 : o2.getResult() == o1.getResult() ? 0 : -1);
        return results;
    }

    public static String classify(ArrayList<Result> results, Map<String, Integer> minResults, int k) {
        for (int i = 0; i < k; i++) {
            String classValue = results.get(i).getClassValue();
            if (!minResults.containsKey(classValue)) {
                minResults.put(classValue, 1);
            } else {
                minResults.replace(classValue, minResults.get(classValue), minResults.get(classValue) + 1);
            }
        }
        return maxUsingIteration(minResults);
    }

    public static ArrayList<Result> calculateResults(ArrayList<Object> objects, int attributeCount, ArrayList<Double> inputAttributes) {
        ArrayList<Result> results = new ArrayList<>();
        for (Object obj : objects) {
            double result = 0;
            for (int i = 0; i < attributeCount; i++) {
                result += Math.pow(obj.attributes.get(i) - inputAttributes.get(i), 2);
            }
            result = Math.sqrt(result);
            Result r = new Result(obj.name, result);
            results.add(r);
        }
        return results;
    }

    public static ArrayList<Result> calculateResults(ArrayList<Object> objects, int attributeCount, double[] inputAttributes) {
        ArrayList<Result> results = new ArrayList<>();
        for (Object obj : objects) {
            double result = 0;
            for (int i = 0; i < attributeCount; i++) {
                result += Math.pow(obj.attributes.get(i) - inputAttributes[i], 2);
            }
            result = Math.sqrt(result);
            Result r = new Result(obj.name, result);
            results.add(r);
        }
        return results;
    }
}