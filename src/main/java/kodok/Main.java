package kodok;

import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Kérlek, add meg a táblázat sorainak számát (N): ");
        int rows = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Kérlek, add meg a táblázat oszlopainak számát (M): ");
        int columns = scanner.nextInt();
        scanner.nextLine();

        ConnectFour game = new ConnectFour(rows, columns);
        System.out.print("Add meg a játékállás fájl nevét (vagy nyomj Entert az új játékhoz): ");
        String filePath = scanner.nextLine().trim();

        if (!filePath.isEmpty()) {
            try {
                game.getGameState().loadFromFile(filePath);
            } catch (IOException e) {
                System.out.println("Hiba a fájl beolvasása közben: " + e.getMessage());
                return;
            }
        }

        game.playGame();

        System.out.print("Kérlek, add meg a fájl nevét, ahová a játék állapotát szeretnéd menteni: ");
        String saveFilePath = scanner.nextLine();

        try {
            game.getGameState().saveToFile(saveFilePath);
            System.out.println("A pálya sikeresen kiírva a '" + saveFilePath + "' fájlba.");
        } catch (IOException e) {
            System.out.println("Hiba a fájl kiírása közben: " + e.getMessage());
        }

        scanner.close();
    }
}