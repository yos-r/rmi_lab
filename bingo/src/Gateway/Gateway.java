package Gateway;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Shared.IBingo;
import Shared.IGateway;

public class Gateway extends UnicastRemoteObject implements IGateway {
    private IBingo bingoService;

    public Gateway() throws RemoteException {
        super();
        try {
            this.bingoService = (IBingo) Naming.lookup("rmi://localhost/Bingo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String jouer(Integer numero, String playerName) throws RemoteException {
        final String[] result = new String[1];
        Thread thread = new Thread(() -> {
            try {
                result[0] = bingoService.jouer(numero, playerName);
            } catch (Exception e) {
                result[0] = "Error: " + e.getMessage();
            }
        });
        thread.start();
        try {
            thread.join(); 
        } catch (InterruptedException e) {
            throw new RemoteException("Thread was interrupted", e);
        }
        return result[0];
    }

    @Override
    public void resetScore(String playerName) throws RemoteException {
        Thread thread = new Thread(() -> {
            try {
                bingoService.resetScore(playerName);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
        thread.start();
        try {
            thread.join(); // Wait for the thread to complete
        } catch (InterruptedException e) {
            throw new RemoteException("Thread was interrupted", e);
        }
    }

    @Override
    public int getLastScore(String playerName) throws RemoteException {
        final int[] result = new int[1];
        Thread thread = new Thread(() -> {
            try {
                result[0] = bingoService.getLastScore(playerName);
            } catch (Exception e) {
                result[0] = -1;
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RemoteException("Thread was interrupted", e);
        }
        return result[0];

    }

    @Override
    public String obtenirMeilleurScore() throws RemoteException {
        final String[] result = new String[1];
        Thread thread = new Thread(() -> {
            try {
                result[0] = bingoService.obtenirMeilleurScore();
            } catch (Exception e) {
                result[0] = "-1"; 
            }
        });
        thread.start();
        try {
            thread.join(); 
        } catch (InterruptedException e) {
            throw new RemoteException("Thread was interrupted", e);
        }
        return result[0];
    }

    public static void main(String[] args) {
        try {
            Gateway gateway = new Gateway();
            Naming.rebind("rmi://localhost/Gateway", gateway);
            System.out.println("Gateway prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
