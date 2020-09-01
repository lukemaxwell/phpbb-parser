package xyz.codepunk.phpbbparser;
import java.util.Arrays;
import java.util.List;

public class Utility {
    List<String> allUsers = Arrays.asList("Mahesh", "Ram", "Krishn", "Arjun", "Bheem");
    public long multiplyNumbers(int num1, int num2) {
        return num1 * num2;
    }
    public long addNumbers(int num1, int num2) {
        return num1 + num2;
    }
    public boolean isUserAvailable(String userName) {
        return allUsers.contains(userName);
    }
}
