import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            IExercice1 service = (IExercice1) Naming.lookup("rmi://localhost/Pgcd");

            Scanner scanner = new Scanner(System.in);
            List<String> history = new ArrayList<>();

            boolean running = true;

            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. Tester la connexion");
                System.out.println("2. Calculer le PGCD de deux entiers");
                System.out.println("3. Calculer le PPCM de deux entiers");
                System.out.println("4. Décomposition en facteurs premiers");
                System.out.println("5. Quitter");
                System.out.println("6. Vérifier si une chaîne est un palindrome");
                System.out.println("7. Compter les occurrences d'un caractère dans une chaîne");
                System.out.print("Choisissez une option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Test connection
                        String testResult = service.test();
                        System.out.println("Réponse du serveur: " + testResult);
                        history.add("Test de connexion: " + testResult);
                        break;

                    case 2:
                        // Calculate PGCD
                        System.out.print("Entrez le premier entier: ");
                        int a = scanner.nextInt();
                        System.out.print("Entrez le deuxième entier: ");
                        int b = scanner.nextInt();
                        int pgcd = service.calculatePgcd(a, b);
                        System.out.println("PGCD(" + a + ", " + b + ") = " + pgcd);
                        history.add("PGCD de " + a + " et " + b + ": " + pgcd);
                        break;

                    case 3:
                        // Calculer PPCM
                        System.out.print("Entrez le premier entier: ");
                        a = scanner.nextInt();
                        System.out.print("Entrez le deuxième entier: ");
                        b = scanner.nextInt();
                        int ppcm = service.calculatePpcm(a, b);
                        System.out.println("PPCM(" + a + ", " + b + ") = " + ppcm);
                        history.add("PPCM de " + a + " et " + b + ": " + ppcm);
                        break;

                    case 4:
                        // Factorisation en nombres premiers
                        System.out.print("Entrez un entier: ");
                        int number = scanner.nextInt();
                        String factors = service.decomposeIntoPrimeFactors(number);
                        System.out.println("Décomposition de " + number + ": " + factors);
                        history.add("Décomposition de " + number + ": " + factors);
                        break;

                    case 5:
                        // Quitter
                        System.out.println("\nHistorique des actions exécutées:");
                        for (String action : history) {
                            System.out.println("- " + action);
                        }
                        running = false;
                        break;
                    case 6:
                        // Check if a string is a palindrome
                        System.out.print("Entrez une chaîne: ");
                        scanner.nextLine(); // Consume newline
                        String inputStr = scanner.nextLine();
                        boolean isPalindrome = service.isPalindrome(inputStr);
                        System.out.println("La chaîne \"" + inputStr + "\" est un palindrome: " + isPalindrome);
                        history.add("Test de palindrome pour \"" + inputStr + "\": " + isPalindrome);
                        break;

                    case 7:
                        // Count character occurrences in a string
                        System.out.print("Entrez une chaîne: ");
                        scanner.nextLine(); // Consume newline
                        String str = scanner.nextLine();
                        System.out.print("Entrez un caractère: ");
                        char c = scanner.next().charAt(0);
                        int occurrences = service.countCharacterOccurrences(str, c);
                        System.out.println(
                                "Le caractère '" + c + "' apparaît " + occurrences + " fois dans \"" + str + "\".");
                        history.add("Occurrences de '" + c + "' dans \"" + str + "\": " + occurrences);
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
