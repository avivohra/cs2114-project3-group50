/** Model for a terminal snake game. No I/O performed. */
import java.util.List;
import java.util.Random;

public class SnakeGame {
    private LinkedQueue<Position> snake;
    private Position apple;
    private Direction direction;
    private Direction pendingDirection;
    private Position currentHead;
    private int score;
    private boolean gameOver;
    private final int width;
    private final int height;
    private final boolean[][] occupied;
    private final Random rand;

    /** Default 15x15 board with true random apple placement. */
    public SnakeGame() {
        this.width = 15;
        this.height = 15;
        this.occupied = new boolean[height][width];
        this.snake = new LinkedQueue<>();
        this.rand = new Random(); // True random for live play
        initializeGame();
    }

    /** Constructor with explicit random seed for deterministic testing. */
    public SnakeGame(long randomSeed) {
        this.width = 15;
        this.height = 15;
        this.occupied = new boolean[height][width];
        this.snake = new LinkedQueue<>();
        this.rand = new Random(randomSeed); // Seeded for JUnit tests
        initializeGame();
    }

    private void initializeGame() {
        int centerRow = height / 2;
        int centerCol = width / 2;
        Position tailPos = new Position(centerRow, centerCol - 1);
        Position headPos = new Position(centerRow, centerCol);
        snake.enqueue(tailPos);
        occupied[tailPos.getRow()][tailPos.getColumn()] = true;
        snake.enqueue(headPos);
        occupied[headPos.getRow()][headPos.getColumn()] = true;
        this.direction = Direction.RIGHT;
        this.pendingDirection = this.direction;
        this.currentHead = headPos;
        this.score = 0;
        this.gameOver = false;
        spawnApple();
    }

    /** Moves the snake one tick forward applying pending direction changes. */
    public void step() {
        if (gameOver) {
            return;
        }
        direction = pendingDirection;
        int newRow = currentHead.getRow();
        int newCol = currentHead.getColumn();
        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        // Wall collision
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
            gameOver = true;
            return;
        }

        // Self collision check (accounting for tail movement if not eating)
        boolean willEat = (apple != null && newRow == apple.getRow() && newCol == apple.getColumn());
        if (occupied[newRow][newCol]) {
            Position tail = snake.peek();
            boolean isTail = (!willEat && tail != null && tail.getRow() == newRow && tail.getColumn() == newCol);
            if (!isTail) {
                gameOver = true;
                return;
            }
        }

        // Advance head
        Position newHead = new Position(newRow, newCol);
        currentHead = newHead;
        snake.enqueue(newHead);
        occupied[newRow][newCol] = true;

        // Apple handling
        if (willEat) {
            score++;
            apple = null;
            if (snake.getSize() < width * height) {
                spawnApple();
            } else {
                gameOver = true;
            }
        } else {
            Position removedTail = snake.dequeue();
            occupied[removedTail.getRow()][removedTail.getColumn()] = false;
        }
    }

    /** Attempts to change direction; rejects opposite of current direction. */
    public void changeDirection(Direction newDir) {
        if (newDir == null) {
            return;
        }
        if (direction == Direction.UP && newDir == Direction.DOWN) return;
        if (direction == Direction.DOWN && newDir == Direction.UP) return;
        if (direction == Direction.LEFT && newDir == Direction.RIGHT) return;
        if (direction == Direction.RIGHT && newDir == Direction.LEFT) return;
        pendingDirection = newDir;
    }

    /** Returns board width. */
    public int getWidth() {
        return width;
    }

    /** Returns board height. */
    public int getHeight() {
        return height;
    }

    /** Returns current apple position or (-1,-1) if none. */
    public Position getApplePosition() {
        return (apple == null) ? new Position(-1, -1) : apple;
    }

    /** Returns current score. */
    public int getScore() {
        return score;
    }

    /** Returns true when the game cannot continue. */
    public boolean isGameOver() {
        return gameOver;
    }

    /** Returns an iterable view of snake body from tail to head. */
    public Iterable<Position> getSnakeBody() {
        List<Position> list = snake.toList();
        return list;
    }

    int getSnakeSize() {
        return snake.getSize();
    }

    private void spawnApple() {
        int emptyCount = width * height - snake.getSize();
        if (emptyCount <= 0) {
            apple = null;
            return;
        }
        int idx = rand.nextInt(emptyCount);
        int seen = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (!occupied[r][c]) {
                    if (seen == idx) {
                        apple = new Position(r, c);
                        return;
                    }
                    seen++;
                }
            }
        }
    }

    /**
     * Returns the current tick delay in milliseconds based on the score.
     * Implements the Speed Levels change feature (faster as score rises).
     * 
     * @return delay in milliseconds
     */
    public long getTickDelay() {
        int level = score / 5; // Speed increases every 5 points
        long baseDelay = 300L;
        long minDelay = 80L;
        long delay = baseDelay - (level * 40L);
        return Math.max(minDelay, delay);
    }
}