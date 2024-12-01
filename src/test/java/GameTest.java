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

    @Test
    void testHorizontalWin() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(3, 'S'));

        assertTrue(gameState.checkWin('S'), "Player 'S' should have won horizontally");
    }

    @Test
    void testVerticalWin() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(0, 'S'));

        assertTrue(gameState.checkWin('S'), "Player 'S' should have won vertically");
    }

    @Test
    void testDiagonalWin() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(3, 'S'));

        assertTrue(gameState.checkWin('S'), "Player 'S' should have won diagonally (left to right)");
    }

    @Test
    void testReverseDiagonalWin() {
        gameState.dropPiece(new Move(3, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(2, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(0, 'S'));

        assertTrue(gameState.checkWin('S'), "Player 'S' should have won diagonally (right to left)");
    }

    @Test
    void testNoWin() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'S'));
        gameState.dropPiece(new Move(2, 'O'));
        gameState.dropPiece(new Move(3, 'S'));

        assertFalse(gameState.checkWin('S'), "Player 'S' should not have won");
        assertFalse(gameState.checkWin('O'), "Player 'O' should not have won");
    }

    @Test
    void testColumnFull() {
        // Fill the first column
        for (int i = 0; i < 6; i++) {
            gameState.dropPiece(new Move(0, 'S'));
        }

        assertThrows(IllegalArgumentException.class, () -> gameState.dropPiece(new Move(0, 'O')));
    }

    @Test
    void testInvalidMoveColumnOutOfRange() {

        assertThrows(IllegalArgumentException.class, () -> gameState.dropPiece(new Move(-1, 'S')));
        assertThrows(IllegalArgumentException.class, () -> gameState.dropPiece(new Move(7, 'S')));
    }

    @Test
    void testInvalidMoveRowFull() {

        for (int i = 0; i < 6; i++) {
            gameState.dropPiece(new Move(1, 'S'));
        }

        assertThrows(IllegalArgumentException.class, () -> gameState.dropPiece(new Move(1, 'O')));
    }

    @Test
    void testGameOverState() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'O'));
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'O'));
        gameState.dropPiece(new Move(0, 'S'));
        gameState.dropPiece(new Move(1, 'O'));
        gameState.dropPiece(new Move(0, 'S'));

        assertTrue(gameState.checkWin('S'), "Player 'S' should have won");
        assertFalse(gameState.checkWin('O'), "Player 'O' should not have won");
    }

    @Test
    void testResetBoard() {
        gameState.dropPiece(new Move(0, 'S'));
        gameState.resetBoard();

        char[][] board = gameState.getBoard();
        for (char[] row : board) {
            for (char cell : row) {
                assertEquals(ConnectFour.EMPTY, cell);
            }
        }
    }
}
