package kodok;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:connect_four.db";

    public DatabaseManager() {
        createTables();
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    wins INTEGER DEFAULT 0
                );
            """;
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Hiba az adatbázis inicializálása közben: " + e.getMessage());
        }
    }

    public void addOrUpdatePlayer(String name) {
        String sql = """
            INSERT INTO players (name, wins) VALUES (?, 0)
            ON CONFLICT(name) DO NOTHING;
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a játékos hozzáadása közben: " + e.getMessage());
        }
    }

    public void incrementWins(String name) {
        String sql = "UPDATE players SET wins = wins + 1 WHERE name = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a győzelmek frissítése közben: " + e.getMessage());
        }
    }

    public void printHighScores() {
        String sql = "SELECT name, wins FROM players ORDER BY wins DESC;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("=== High Scores ===");
            System.out.printf("%-20s | %-10s%n", "Név", "Győzelmek");
            System.out.println("----------------------------");
            while (rs.next()) {
                System.out.printf("%-20s | %-10d%n", rs.getString("name"), rs.getInt("wins"));
            }
        } catch (SQLException e) {
            System.err.println("Hiba a high score lekérdezése közben: " + e.getMessage());
        }
    }
}

