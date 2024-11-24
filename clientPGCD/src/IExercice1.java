
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IExercice1 extends Remote {
    String test() throws RemoteException;

    int calculatePgcd(int a, int b) throws RemoteException;

    int calculatePpcm(int a, int b) throws RemoteException;

    String decomposeIntoPrimeFactors(int number) throws RemoteException;

    boolean isPalindrome(String str) throws RemoteException;

    int countCharacterOccurrences(String str, char c) throws RemoteException;

}