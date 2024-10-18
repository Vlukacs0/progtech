package kodok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConnectFourTest {
    private ConnectFour game;

    @BeforeEach
    void setUp() {
        game = new ConnectFour(7, 6);
    }

    @Test
    void testInitialGameState() {
        assertNotNull(game.getGameState());
        assertEquals(7, game.getGameState().getBoard().length);
        assertEquals(6, game.getGameState().getBoard()[0].length);
    }

    @Test
    void testDropPiece() {
        Move move = new Move(0, 'S');
        game.getGameState().dropPiece(move);
        assertEquals('S', game.getGameState().getBoard()[6][0]);
    }
    @Test
    void testWinningCondition() {
        game.getGameState().dropPiece(new Move(0, 'S'));
        game.getGameState().dropPiece(new Move(1, 'S'));
        game.getGameState().dropPiece(new Move(2, 'S'));
        game.getGameState().dropPiece(new Move(3, 'S'));

        assertTrue(game.checkWin('S'));
    }
}

