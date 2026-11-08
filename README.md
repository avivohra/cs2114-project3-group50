# Terminal Snake - Deliverable 3

## How to Run
- The model is fully independent. Verify functionality by executing the JUnit 5 suite `SnakeGameTest.java`. The test exercises the entire game loop (`step()`, apple consumption, wall/self collisions) without any reliance on `TerminalView` or `SnakeController`. The visual game cannot be launched yet because those components remain stubbed.

## Reflection
- **What changed from your specification?**
	- Added `public int getSize()` to `LinkedQueue` for O(1) size queries.
	- Added `toList()` to `LinkedQueue` to provide an easy iterable view of the snake body.
	- Added an overloaded `SnakeGame(long seed)` constructor enabling deterministic JUnit testing without reflection.
- **Did the model really stand alone?** Yes. All game‑logic tests run successfully without any UI or controller code.
- **What happened to your unknowns?**
	- The uncertainty around apple placement on empty cells was resolved by maintaining a `boolean[][] occupied` grid, giving O(1) vacancy checks.
	- Unknowns concerning terminal redrawing and continuous input remain open for Deliverable 4.
- **What doesn't work yet?** Visual rendering and keyboard handling are not functional; `TerminalView` and `SnakeController` are still empty stubs.

## Next Steps
- **What is left to finish the MVP?**
	1. Implement `TerminalView` to render the board and game state.
	2. Implement `SnakeController` to capture W/A/S/D key inputs.
	3. Wire a game loop that repeatedly calls `step()` and `draw()`.
- **Change Feature:** *Speed Levels* – snake speed increases with score.
	- *Prediction:* Modifications will affect `SnakeGame` (track speed thresholds) and `SnakeController` (adjust loop timing). `TerminalView` and `LinkedQueue` will remain unchanged.
- **Stretch goals:** Commit to *Speed Levels*; drop *Auto‑Play Mode* and *Saved High Scores* to concentrate on a polished MVP.
- **New tests needed:**
	- Tests for `SnakeController` input parsing.
	- Tests in `SnakeGame` verifying speed variables update at defined score thresholds.
- **Who does what?**
	- Avi (Model integration and speed logic)
	- Brinda (Queue iteration support for view)
	- Isaac (TerminalView rendering)
	- Noah (SnakeController input loop)

## Attribution
| Class/Test File | Owner | Contribution |
|---|---|---|
| `SnakeGame.java` & `SnakeGameTest.java` | Avi Vohra | Wrote, refactored, and tested via constrained GenAI prompts |
| `LinkedQueue.java`, `Position.java`, `Direction.java` | Brinda Amidyala | Authored base structures and validated O(1) size accessors |
| `TerminalView.java` | Isaac Morris | Scaffolded stub class |
| `SnakeController.java` | Noah Kitatani | Scaffolded stub class |

**GenAI Note:** Generated initial class skeletons and the JUnit test suite with Copilot/Gemini. Prompted to avoid reflection, leading to a seeded constructor for deterministic testing and replacement of O(N) size calculations with O(1) queue size access.