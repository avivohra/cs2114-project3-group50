/** Controller for a terminal snake game. */
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Mediates between user input, the SnakeGame model, and TerminalView.
 * 
 * @author Noah Kitatani & Avi Vohra
 * @version Nov 20, 2026
 */
public class SnakeController {
    private SnakeGame game;
    private TerminalView view;
    private volatile boolean running;

    /**
     * Constructor for SnakeController.
     * @param game The SnakeGame model.
     * @param view The TerminalView presentation.
     */
    public SnakeController(SnakeGame game, TerminalView view) {
        this.game = game;
        this.view = view;
        this.running = true;
    }

    /** Starts the game loop and asynchronous input listener. */
    public void start() {
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try {
                while (running && !game.isGameOver()) {
                    if (scanner.hasNext()) {
                        String token = scanner.next();
                        if (!token.isEmpty()) {
                            handleKey(token.charAt(0));
                        }
                    }
                }
            } catch (Exception e) {
                // Stream closed or thread interrupted
            } finally {
                scanner.close();
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        while (!game.isGameOver()) {
            view.draw();
            game.step();

            try {
                Thread.sleep(game.getTickDelay());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        view.draw();
        view.drawGameOver();
        running = false;
    }

    /** Handles an input key. */
    public void handleKey(char key) {
        char lower = Character.toLowerCase(key);
        switch (lower) {
            case 'w':
                game.changeDirection(Direction.UP);
                break;
            case 's':
                game.changeDirection(Direction.DOWN);
                break;
            case 'a':
                game.changeDirection(Direction.LEFT);
                break;
            case 'd':
                game.changeDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    /**
     * Main entry point to launch the Terminal Snake game.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
        TerminalView view = new TerminalView(game);
        SnakeController controller = new SnakeController(game, view);
        controller.start();
    }
}