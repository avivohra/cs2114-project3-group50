/** Terminal view for a snake game. */
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Draws the current game state and displays the "Game Over" message.
 * 
 * @author Isaac Morris & Avi Vohra
 * @version Nov 20, 2026
 */
public class TerminalView
{
    private SnakeGame game;

    /**
     * Constructor for TerminalView.
     * @param game The SnakeGame model instance.
     */
    public TerminalView(SnakeGame game)
    {
        this.game = game;
    }

    /** Draws the current game state. */
    public void draw()
    {
        // Clear terminal reliably across all OS/shells using blank lines instead of ANSI codes
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }

        System.out.println("=== TERMINAL SNAKE ===");
        System.out.println("Score: " + game.getScore() + " | Type W/A/S/D + Enter to move");

        for (int y = 0; y < game.getHeight(); y++)
        {
            for (int x = 0; x < game.getWidth(); x++)
            {
                boolean bodyFound = false;
                for (Position pos : game.getSnakeBody())
                {
                    if (pos.getRow() == y && pos.getColumn() == x)
                    {
                        bodyFound = true;
                        break;
                    }
                }

                Position apple = game.getApplePosition();
                if (bodyFound)
                {
                    System.out.print("#");
                }
                else if (apple != null && apple.getRow() == y && apple.getColumn() == x)
                {
                    System.out.print("@");
                }
                else
                {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    /** Draws the game-over state. */
    public void drawGameOver()
    {
        System.out.println("\n==============================");
        System.out.println("         GAME OVER!           ");
        System.out.println("     Final Score: " + game.getScore());
        System.out.println("==============================\n");
    }
}