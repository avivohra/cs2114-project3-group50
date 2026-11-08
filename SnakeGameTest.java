import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** JUnit test suite for {@link SnakeGame}. */
public class SnakeGameTest {
    private SnakeGame game;

    @BeforeEach
    void setUp() {
        game = new SnakeGame();
    }

    @Test
    void testApplePositionExists() {
        Position apple = game.getApplePosition();
        assertTrue(apple.getRow() >= 0 && apple.getRow() < game.getHeight());
        assertTrue(apple.getColumn() >= 0 && apple.getColumn() < game.getWidth());
    }

    @Test
    void testApplePositionNoApple() {
        // After filling the board completely, apple should be absent.
        // Use seeded game to make behavior deterministic.
        SnakeGame fullGame = new SnakeGame(1L);
        // Fill the board by repeatedly eating apples until game over.
        while (!fullGame.isGameOver()) {
            fullGame.step();
        }
        Position apple = fullGame.getApplePosition();
        assertEquals(-1, apple.getRow());
        assertEquals(-1, apple.getColumn());
    }

    @Test
    void testIsGameOverDefaultFalse() {
        assertFalse(game.isGameOver());
    }

    @Test
    void testWallCollision() {
        // Point the snake upward and run until it hits the top wall.
        game.changeDirection(Direction.UP);
        while (!game.isGameOver()) {
            game.step();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    void testOppositeDirectionRejected() {
        // Default direction RIGHT; attempt illegal LEFT turn.
        // After a step the snake should still have moved right.
        List<Position> before = (List<Position>) game.getSnakeBody();
        Position headBefore = before.get(before.size() - 1);
        game.changeDirection(Direction.LEFT);
        game.step();
        List<Position> after = (List<Position>) game.getSnakeBody();
        Position headAfter = after.get(after.size() - 1);
        assertEquals(headBefore.getRow(), headAfter.getRow());
        assertEquals(headBefore.getColumn() + 1, headAfter.getColumn());
    }

    @Test
    void testStepMovement() {
        // Capture initial head position.
        Position initialHead = game.getSnakeBody().iterator().next(); // head is last element; we fetch via list.
        List<Position> body = (List<Position>) game.getSnakeBody();
        Position head = body.get(body.size() - 1);
        game.step();
        List<Position> after = (List<Position>) game.getSnakeBody();
        Position newHead = after.get(after.size() - 1);
        assertEquals(head.getRow(), newHead.getRow());
        assertEquals(head.getColumn() + 1, newHead.getColumn()); // default direction RIGHT
    }

    @Test
    void testEatApple() {
        // Use a seeded game where the first apple is at (8,14).
        SnakeGame seeded = new SnakeGame(12345L);
        // Move snake down one row then right until it reaches the apple.
        seeded.changeDirection(Direction.DOWN);
        seeded.step(); // now head at (8,7)
        seeded.changeDirection(Direction.RIGHT);
        while (seeded.getScore() == 0 && !seeded.isGameOver()) {
            seeded.step();
        }
        assertEquals(1, seeded.getScore());
        // Snake should have grown by one compared to initial size 2.
        List<Position> body = (List<Position>) seeded.getSnakeBody();
        assertEquals(3, body.size());
        // New apple should not be at the previous location (8,14).
        Position newApple = seeded.getApplePosition();
        assertFalse(newApple.getRow() == 8 && newApple.getColumn() == 14);
    }
}