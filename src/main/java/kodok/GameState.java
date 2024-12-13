package kodok;

import java.io.*;
import java.util.Arrays;

public class GameState {
    private final char[][] board;
    private final int rows;
    private final int columns;
    private char currentPlayer;

    public GameState(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new char[rows][columns];
        resetBoard();
        this.currentPlayer = 'S';
    }

    public void resetBoard() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], ConnectFour.EMPTY);
        }
    }

    public void dropPiece(Move move) {
        if (move.column() < 0 || move.column() >= columns) {
            throw new IllegalArgumentException("Érvénytelen oszlop!");
        }

        boolean placed = false;
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][move.column()] == ConnectFour.EMPTY) {
                board[i][move.column()] = move.symbol();
                placed = true;
                break;
            }
        }
        if (!placed) {
            throw new IllegalArgumentException("A sor már tele van!");
        }
    }

    public boolean isColumnAvailable(int column) {
        return board[0][column] == ConnectFour.EMPTY;
    }

    public boolean isBoardFull() {
        for (int c = 0; c < columns; c++) {
            if (board[0][c] == ConnectFour.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                sb.append(board[r][c]).append(" ");
            }
            sb.append("\n");
        }
        sb.append("Oszlopok: ").append(String.join(" ", Arrays.stream("abcdefghijklmnopqrstuvwxyz".split("")).limit(columns).toArray(String[]::new)));
        System.out.println(sb);
    }

    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (char[] row : board) {
                writer.write(row);
                writer.newLine();
            }
            writer.write(currentPlayer);
            writer.newLine();
        }
    }

    public static GameState loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder boardBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                boardBuilder.append(line).append("\n");
            }
            String[] rows = boardBuilder.toString().trim().split("\n");
            int rowCount = rows.length;
            int columnCount = rows[0].length();
            GameState gameState = new GameState(rowCount, columnCount);
            for (int i = 0; i < rowCount; i++) {
                gameState.board[i] = rows[i].toCharArray();
            }
            line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                gameState.setCurrentPlayer(line.charAt(0));
            }
            return gameState;
        } catch (IOException e) {
            throw new IOException("Hiba a fájl beolvasása közben: " + e.getMessage());
        }
    }

    public boolean checkWin(char symbol) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns - 3; col++) {
                if (board[row][col] == symbol &&
                        board[row][col + 1] == symbol &&
                        board[row][col + 2] == symbol &&
                        board[row][col + 3] == symbol) {
                    return true;
                }
            }
        }

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows - 3; row++) {
                if (board[row][col] == symbol &&
                        board[row + 1][col] == symbol &&
                        board[row + 2][col] == symbol &&
                        board[row + 3][col] == symbol) {
                    return true;
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < columns - 3; col++) {
                if (board[row][col] == symbol &&
                        board[row + 1][col + 1] == symbol &&
                        board[row + 2][col + 2] == symbol &&
                        board[row + 3][col + 3] == symbol) {
                    return true;
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 3; col < columns; col++) {
                if (board[row][col] == symbol &&
                        board[row + 1][col - 1] == symbol &&
                        board[row + 2][col - 2] == symbol &&
                        board[row + 3][col - 3] == symbol) {
                    return true;
                }
            }
        }

        return false;
    }
}
