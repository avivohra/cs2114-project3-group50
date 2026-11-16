import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** JUnit test suite for SnakeGame. */
public class SnakeGameTest {
    private SnakeGame game;

    @BeforeEach
    void setUp() {
        game = new SnakeGame(12345L); // Use seeded constructor for deterministic tests
    }

    @Test
    void testApplePositionExists() {
        Position apple = game.getApplePosition();
        assertTrue(apple.getRow() >= 0 && apple.getRow() < game.getHeight());
        assertTrue(apple.getColumn() >= 0 && apple.getColumn() < game.getWidth());
    }

    @Test
    void testIsGameOverDefaultFalse() {
        assertFalse(game.isGameOver());
    }

    @Test
    void testStepMovement() {
        int initialHeadCol = 7; // centerCol for 15x15 is 7
        game.step();
        boolean foundNewHead = false;
        for (Position p : game.getSnakeBody()) {
            if (p.getRow() == 7 && p.getColumn() == initialHeadCol + 1) {
                foundNewHead = true;
            }
        }
        assertTrue(foundNewHead);
    }

    @Test
    void testOppositeDirectionRejected() {
        game.changeDirection(Direction.LEFT);
        game.step();
        assertFalse(game.isGameOver());
    }

    /** Terminal test runner entry point to verify tests. */
    public static void main(String[] args) {
        System.out.println("Running SnakeGameTest suite...");
        SnakeGameTest tester = new SnakeGameTest();
        int passed = 0;
        int failed = 0;

        try {
            tester.setUp();
            tester.testApplePositionExists();
            System.out.println("  [PASS] testApplePositionExists");
            passed++;
        } catch (Throwable t) {
            System.out.println("  [FAIL] testApplePositionExists: " + t.getMessage());
            failed++;
        }

        try {
            tester.setUp();
            tester.testIsGameOverDefaultFalse();
            System.out.println("  [PASS] testIsGameOverDefaultFalse");
            passed++;
        } catch (Throwable t) {
            System.out.println("  [FAIL] testIsGameOverDefaultFalse: " + t.getMessage());
            failed++;
        }

        try {
            tester.setUp();
            tester.testStepMovement();
            System.out.println("  [PASS] testStepMovement");
            passed++;
        } catch (Throwable t) {
            System.out.println("  [FAIL] testStepMovement: " + t.getMessage());
            failed++;
        }

        try {
            tester.setUp();
            tester.testOppositeDirectionRejected();
            System.out.println("  [PASS] testOppositeDirectionRejected");
            passed++;
        } catch (Throwable t) {
            System.out.println("  [FAIL] testOppositeDirectionRejected: " + t.getMessage());
            failed++;
        }

        System.out.println("\nTest Results: " + passed + " passed, " + failed + " failed.");
    }
}