package kodok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState(6, 7);
    }

    @Test
    void testInitialBoard() {
        char[][] board = gameState.getBoard();
        for (char[] row : board) {
            for (char cell : row) {
                assertEquals(ConnectFour.EMPTY, cell);
            }
        }
    }

    @Test
    void testDropPiece() {
        Move move = new Move(0, 'S');
        gameState.dropPiece(move);
        assertEquals('S', gameState.getBoard()[5][0]);
    }

    @Test
    void testIsBoardFull() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                gameState.dropPiece(new Move(i, 'S'));
            }
        }
        assertTrue(gameState.isBoardFull());
    }
}