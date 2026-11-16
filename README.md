# Terminal Snake - Deliverable 4

## How to Compile and Run
- **To play the game:** Run `javac *.java` followed by `java SnakeController`. Control the snake by typing `w`, `a`, `s`, or `d` and pressing Enter.
- **To run the tests:** Execute the JUnit 5 test suite in `SnakeGameTest.java` via your IDE. Tests exercise the model independently of the view.

## Reflection
- **How did your design absorb the change?** Our "Speed Levels" feature touched `SnakeGame` (adding `getTickDelay()` based on score) and `SnakeController` (timing the game loop with `Thread.sleep()`). This matched our Deliverable 3 prediction perfectly; `LinkedQueue` and `TerminalView` remained entirely untouched.
- **Did the change cross the model/view line?** No. The view does not know or care about speed; the controller handles the tick timing using the model's exposed state, and the view simply draws when commanded.
- **What changed from your D3 plan?** Our core plan remained consistent. We implemented Speed Levels as predicted, and added a `currentHead` tracker in `SnakeGame` to resolve a queue-reading bug while preserving standard FIFO Queue operations.
- **What doesn't work yet?** The core MVP and Speed Levels are fully functional. Due to standard Java console limitations, input requires typing a letter plus Enter, and the terminal display exhibits slight frame clearing flicker between ticks.

## Next Steps
- **What is left for the final build?** Final code cleanup, comment polish, and preparation for the 3-minute oral defense demo.
- **Stretch goals:** We are committing to our completed "Speed Levels" feature. We have dropped "Auto-Play Mode" and "Saved High Scores" to ensure the core MVC architecture remains flawless.
- **3-Minute Demo Plan:** We will launch the game, demonstrate normal movement via controller inputs, eat multiple apples to show the Speed Level increase, and intentionally crash into a wall to display the Game Over screen.
- **Who does what?** Avi (Model integration and speed logic), Brinda (Queue iteration and JUnit test suite), Isaac (TerminalView rendering), Noah (SnakeController input loop and game pacing).

## Attribution
| Class/Test File | Owner | Contribution |
| :--- | :--- | :--- |
| `SnakeGame.java`, `SnakeGameTest.java` | Avi Vohra | Wrote logic, fixed head-tracking bug, integrated speed levels, tested via GenAI. |
| `LinkedQueue.java`, `Position.java`, `Direction.java` | Brinda Amidyala | Authored base structures, O(1) size management. |
| `TerminalView.java` | Isaac Morris | Scaffolded UI, implemented grid drawing. |
| `SnakeController.java` | Noah Kitatani | Scaffolded controller, implemented input thread and speed-paced game loop. |

**GenAI Note:** We used GenAI (Copilot/Gemini) to assist with debugging the FIFO queue head-tracking logic, structuring the game loop thread, and drafting this reflection document.