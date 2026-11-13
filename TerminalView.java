
/** Terminal view for a snake game. */
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Draws the current game state and displays the "Game Over" message when the
 * game ends
 * 
 * @author Isaac Morris
 * @version Nov 13, 2026
 */
public class TerminalView
{
    private SnakeGame game;

    /** Draws the current game state. */
    public void draw()
    {
        // Note: May be O(n^3) - could take a while to load.

        for (int y = 0; y < game.getHeight(); y++)
        {
            for (int x = 0; x < game.getWidth(); x++)
            {
                boolean bodyFound = false;
                Position currPos = new Position(x, y);
                for (Position pos : game.getSnakeBody())
                {
                    if (pos == currPos)
                    {
                        bodyFound = true;
                    }
                }
                if (bodyFound)
                {
                    System.out.println("#");
                }
                else if (game.getApplePosition() == currPos)
                {
                    System.out.println("@");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }


    /** Draws the game-over state. */
    public void drawGameOver()
    {
        System.out.println("####  ##  ##       ## ####");
        System.out.println("#    #  # # #     # # #");
        System.out.println("#### #### #  #   #  # ####");
        System.out.println("#  # #  # #   # #   # #");
        System.out.println("#### #  # #    #    # ####");

        System.out.println("");

        System.out.println("#### #       # #### ####");
        System.out.println("#  #  #     #  #    #  #");
        System.out.println("#  #   #   #   #### ####");
        System.out.println("#  #    # #    #    # #");
        System.out.println("####     #     #### #  #");

    }
}
