import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String example = "abcdabcdef";
        System.out.println("\nExample String: " + example);
        System.out.println("Slow: " + slowFindSubstring(example));
        System.out.println("Fast: " + fastFindSubstring(example));
        System.out.println("");

        BufferedReader input = new BufferedReader(new FileReader("data.txt"));
        String line = input.readLine();
        input.close();

        long startTime, estimatedTime1, estimatedTime2;
        String result1, result2;
        System.out.printf("Performance results with a %,d character string.\n", line.length());

        startTime = System.nanoTime();
        result1 = slowFindSubstring(line);
        estimatedTime1 = System.nanoTime() - startTime;
        System.out.printf("Slow Method: %s\tTime: %1.5f seconds\n", result1, (estimatedTime1 / 1000000000.0));

        startTime = System.nanoTime();
        result2 = fastFindSubstring(line);
        estimatedTime2 = System.nanoTime() - startTime;
        System.out.printf("Fast Method: %s\tTime: %1.5f seconds\n", result2, (estimatedTime2 / 1000000000.0));
        System.out.printf("\nFast method is %,d times faster.\n", estimatedTime1 / estimatedTime2);
    }

    /**
     * Code the follwoing method with a time complexity of O(n).
     */
    public static String fastFindSubstring(String str) {
        // make a map that stores a character and its last index
        Map<String, Integer> map = new HashMap<>();

        // largest string without duplicates
        String largest = "";

        // initiate two integer variables
        int startIdx = 0; int endIdx = 0; 

        // iterate through the string 
        while (endIdx < str.length())  {
            // current character of the string
            String current = str.substring(endIdx, endIdx + 1);

            // check if the current character has already occurred or if next index doesn't exist
            if (map.containsKey(current) && (map.get(current) >= startIdx)) {
                // check if the substr is larger than largest
                if (str.substring(startIdx, endIdx).length() > largest.length()) {
                    largest = str.substring(startIdx, endIdx);
                }

                // set startIdx to the next index after the last occurence
                startIdx = map.get(current) + 1;
            } else if (endIdx + 1 == str.length()) {
                // check if the substr is larger than largest
                if (str.substring(startIdx, endIdx + 1).length() > largest.length()) {
                    largest = str.substring(startIdx, endIdx + 1);
                }

                return largest;
            }
            
            // update the occurence of the current character on the map
            map.put(current, endIdx);

            // go to the next index
            endIdx++;
        }
        
        return largest;
    }

    /*
    *  AP Computer Science A solution. Time complexity is O(N^2)
    */
    public static String slowFindSubstring(String str) {
        String largest = "";

        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                String subStr = str.substring(i, j);
                boolean[] array = new boolean[256];
                boolean noDuplicates = true;
                for (char c : subStr.toCharArray()) {
                    if (array[c] == true) {
                        noDuplicates = false;
                    } else {
                        array[c] = true;
                    }
                }
                if (noDuplicates && subStr.length() > largest.length()) {
                    largest = subStr;
                }
            }
        }
        return largest;
    }
}
