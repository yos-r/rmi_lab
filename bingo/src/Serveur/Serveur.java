package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Shared.IBingo;

public class Serveur extends UnicastRemoteObject implements IBingo {
    private List<Integer> urne;
    private int meilleurScore;
    private String meilleurJoueur;
    private Map<String, Integer> scoresJoueurs; 

    public Serveur() throws RemoteException {
        super();
        this.scoresJoueurs = new HashMap<>();

        this.urne = new ArrayList<>();
        this.meilleurScore = 0;
        this.meilleurJoueur="None";
        initUrne();
    }

    private void initUrne() {
        for (int i = 0; i <= 9; i++) {
            urne.add(i);
        }
        Collections.shuffle(urne); 
    }
    @Override
    public void resetScore(String playerName) throws RemoteException{
        scoresJoueurs.put(playerName, 0);
    }
    @Override
    public int getLastScore (String playerName) throws RemoteException{
        return scoresJoueurs.get(playerName);
    }
    @Override
    public String jouer(Integer numero, String playerName) throws RemoteException {
        if (!scoresJoueurs.containsKey(playerName)) {
            scoresJoueurs.put(playerName, 0); 
        }

        if (urne.isEmpty()) {
            for (int i = 0; i <= 9; i++) {
                urne.add(i);
            }
            Collections.shuffle(urne); 
        }

        int boule = urne.remove(0); 
        boolean correct = boule == numero;

        if (correct) {
            scoresJoueurs.put(playerName, scoresJoueurs.get(playerName) + 1); 
        }

        if (scoresJoueurs.get(playerName) > meilleurScore) {
            meilleurScore = scoresJoueurs.get(playerName);
            meilleurJoueur=playerName;
        }

        return "Nom: " + playerName + " | Boule tirée: " + boule + " | Prédiction: " + numero + " | " +
                (correct ? "Correct!" : "Incorrect.");
    }

    @Override
    public List<Integer> obtenirTirage() throws RemoteException {
        return urne;
    }

    @Override
    public String obtenirMeilleurScore() throws RemoteException {
        return meilleurScore +  "par" + meilleurJoueur;
    }

    public static void main(String[] args) {
        try {
            Serveur serveur = new Serveur();
            Naming.rebind("rmi://localhost/Bingo", serveur);
            System.out.println("Serveur Bingo prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
