package net.serg;

public class StringCalculator {

    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        String[] tokens = numbers.split("[,\n]");
        int sum = 0;
        for (String token : tokens) {
            if (!token.isEmpty()) {
                sum += Integer.parseInt(token);
            }
        }
        return sum;
    }
}