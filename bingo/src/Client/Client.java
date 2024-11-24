package Client;

import java.rmi.Naming;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Shared.IGateway;

public class Client {
    public static void main(String[] args) {
        try {
            List<String> funnyNames = List.of(
                    "Captain Obvious",
                    "Sir Laughs-a-Lot",
                    "The Code Whisperer",
                    "Debugger Supreme",
                    "Null Pointer",
                    "Array Out of Bounds",
                    "Stack Overflow",
                    "Infinite Looper",
                    "Syntax Error",
                    "Bug Magnet",
                    "TurtleNinja");
            Random random = new Random();
            String playerName = funnyNames.get(random.nextInt(funnyNames.size()));
            System.out.println("Votre nom est: " + playerName);

            IGateway gateway = (IGateway) Naming.lookup("rmi://localhost/Gateway");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. Jouer Bingo");
                System.out.println("2. Connaître le meilleur score");
                System.out.println("3. Quitter");
                System.out.print("Choisissez une option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        gateway.resetScore(playerName);
                        for (int i = 0; i < 10; i++) {
                            System.out.print(i + " : Entrez votre prédiction (0-9): ");
                            int prediction = scanner.nextInt();
                            String result = gateway.jouer(prediction, playerName);
                            System.out.println(result);
                        }
                        System.out.println("Votre score est " + gateway.getLastScore(playerName));

                        break;

                    case 2:
                        String meilleurScore = gateway.obtenirMeilleurScore();
                        System.out.println("Le meilleur score actuel est: " + meilleurScore);
                        break;

                    case 3:
                        running = false;
                        System.out.println("Merci d'avoir joué !");
                        break;

                    default:
                        System.out.println("Option invalide.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
