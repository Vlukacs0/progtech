package kodok;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class ConnectFour {
    protected static final char EMPTY = '.';
    private int rows;
    private int columns;

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    private Player human;
    private Player ai;
    private GameState gameState;

    public ConnectFour(int rows, int columns) {
        if (rows < 4 || columns < 4 || rows > 12 || columns > 12 || columns > rows) {
            throw new IllegalArgumentException("A sorok (N) és oszlopok (M) mérete 4 <= M <= N <= 12 kell legyen.");
        }
        this.rows = rows;
        this.columns = columns;
        this.ai = new Player("Gép", 'P'); // Gép név és szín
        this.gameState = new GameState(rows, columns);
    }

    public char getEmptySymbol() {
        return EMPTY;
    }

    public Player getHuman() {
        return human;
    }

    public Player getAI() {
        return ai;
    }

    public GameState getGameState() {
        return gameState;
    }
    public void playGame() {
        System.out.print("Kérlek, add meg a játékállás fájl nevét (vagy nyomj Entert az új játékhoz): ");
        String filePath = scanner.nextLine().trim();
        System.out.println("Fájl név beolvasva: " + filePath);
        try {
            if (!filePath.isEmpty()) {
                gameState = GameState.loadFromFile(filePath);
            } else {
                System.out.print("Kérlek, add meg a neved: ");
                String playerName = scanner.nextLine();
                human = new Player(playerName, 'S');
                gameState = new GameState(rows, columns);
            }
        } catch (IOException e) {
            System.out.println("Hiba a fájl beolvasása közben: " + e.getMessage());
            return;
        }

        Player currentPlayer = human;

        while (true) {
            gameState.printBoard();
            Move move;

            if (currentPlayer == human) {
                move = humanTurn();
            } else {
                move = aiTurn();
            }

            gameState.dropPiece(move);

            if (checkWin(currentPlayer.symbol())) {
                gameState.printBoard();
                System.out.println(currentPlayer.name() + " nyert!");
                break;
            }

            if (gameState.isBoardFull()) {
                gameState.printBoard();
                System.out.println("Döntetlen!");
                break;
            }

            currentPlayer = (currentPlayer == human ? ai : human);
        }
    }

    private Move humanTurn() {
        int column;
        do {
            System.out.print("Válassz egy oszlopot (a-" + (char) ('a' + columns - 1) + "): ");
            String input = scanner.nextLine();
            column = input.charAt(0) - 'a';
        } while (column < 0 || column >= columns || !isColumnAvailable(column));
        return new Move(column, human.symbol());
    }

    private Move aiTurn() {
        int column;
        do {
            column = random.nextInt(columns);
        } while (!isColumnAvailable(column));
        System.out.println("A gép az '" + (char) ('a' + column) + "' oszlopba rakott.");
        return new Move(column, ai.symbol());
    }

    private boolean isColumnAvailable(int column) {
        return gameState.isColumnAvailable(column);
    }

    public boolean checkWin(char piece) {
        return checkHorizontalWin(piece) || checkVerticalWin(piece) || checkDiagonalWin(piece);
    }

    private boolean checkHorizontalWin(char piece) {
        for (int r = 0; r < rows; r++) {
            int count = 0;
            for (int c = 0; c < columns; c++) {
                if (gameState.getBoard()[r][c] == piece) {
                    count++;
                    if (count == 4) return true;
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin(char piece) {
        for (int c = 0; c < columns; c++) {
            int count = 0;
            for (int r = 0; r < rows; r++) {
                if (gameState.getBoard()[r][c] == piece) {
                    count++;
                    if (count == 4) return true;
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(char piece) {
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < columns - 3; c++) {
                if (gameState.getBoard()[r][c] == piece &&
                        gameState.getBoard()[r - 1][c + 1] == piece &&
                        gameState.getBoard()[r - 2][c + 2] == piece &&
                        gameState.getBoard()[r - 3][c + 3] == piece) {
                    return true;
                }
            }
        }

        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < columns - 3; c++) {
                if (gameState.getBoard()[r][c] == piece &&
                        gameState.getBoard()[r + 1][c + 1] == piece &&
                        gameState.getBoard()[r + 2][c + 2] == piece &&
                        gameState.getBoard()[r + 3][c + 3] == piece) {
                    return true;
                }
            }
        }

        return false;
    }
}



