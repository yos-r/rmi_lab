import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Exercice1 extends UnicastRemoteObject implements IExercice1{
    public Exercice1() throws RemoteException{
        System.out.println("instanciation");
    }
    public String test() throws RemoteException{
        return "Test";
    }
    @Override
    public int calculatePgcd(int a, int b) throws RemoteException {
        // Euclidean algorithm for GCD
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    @Override
    public int calculatePpcm(int a, int b) throws RemoteException {
        // LCM formula: (|a * b|) / GCD(a, b)
        int gcd = calculatePgcd(a, b);
        return Math.abs(a * b) / gcd;
    }


    @Override
    public String decomposeIntoPrimeFactors(int number) throws RemoteException {
        if (number <= 1) {
            return "No prime factorization for numbers <= 1";
        }

        StringBuilder result = new StringBuilder();
        int count;
        for (int i = 2; i <= number; i++) {
            count = 0;
            while (number % i == 0) {
                number /= i;
                count++;
            }
            if (count > 0) {
                result.append(i).append("^").append(count).append(" * ");
            }
        }
        // Remove the trailing " * "
        if (result.length() > 0) {
            result.setLength(result.length() - 3);
        }
        return result.toString();
    }
    @Override
    public boolean isPalindrome(String str) throws RemoteException {
        String cleanedStr = str.replaceAll("\\s+", "").toLowerCase();
        int left = 0, right = cleanedStr.length() - 1;
        while (left < right) {
            if (cleanedStr.charAt(left) != cleanedStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @Override
    public int countCharacterOccurrences(String str, char c) throws RemoteException {
        int count = 0;
        for (char ch : str.toCharArray()) {
            if (ch == c) {
                count++;
            }
        }
        return count;
    }
    public static void main(String[] args) {
         try {
            Exercice1 service = new Exercice1();
            Naming.rebind("rmi://localhost/Pgcd", service);
            System.out.println("Service pgcd publie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
