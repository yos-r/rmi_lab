package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBingo extends Remote {
    String jouer(Integer numero,String score) throws RemoteException; 
    List<Integer> obtenirTirage() throws RemoteException; 
    String obtenirMeilleurScore() throws RemoteException; 
     void resetScore(String playerName) throws RemoteException;
     int getLastScore(String playerName) throws RemoteException;

}
