package Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGateway extends Remote {
    String jouer(Integer numero,String score) throws RemoteException; 
    String obtenirMeilleurScore() throws RemoteException; 
    void resetScore(String playerName) throws RemoteException;
    int getLastScore(String playerName) throws RemoteException;

}
