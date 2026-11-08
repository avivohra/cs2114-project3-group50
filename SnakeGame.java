/** Model for a terminal snake game. */
import java.util.List;
import java.util.Random;

/** Model for a terminal snake game. No I/O performed. */
public class SnakeGame {
    private LinkedQueue<Position> snake;
    private Position apple;
    private Direction direction;
    private Direction pendingDirection;
    private int score;
    private boolean gameOver;
    private final int width;
    private final int height;
    private final boolean[][] occupied;
    private final Random rand;

    /** Default 15×15 board with a 2‑segment snake spawned near centre. */
    /** Default constructor creates a game with nondeterministic apple placement. */
    public SnakeGame() {
        this.width = 15;
        this.height = 15;
        this.occupied = new boolean[height][width];
        this.snake = new LinkedQueue<>();
        this.rand = new Random();
        // centre position
        int centerRow = height / 2;
        int centerCol = width / 2;
        // initial direction right, tail to the left of head
        Position tailPos = new Position(centerRow, centerCol - 1);
        Position headPos = new Position(centerRow, centerCol);
        snake.enqueue(tailPos);
        occupied[tailPos.getRow()][tailPos.getColumn()] = true;
        snake.enqueue(headPos);
        occupied[headPos.getRow()][headPos.getColumn()] = true;
        this.direction = Direction.RIGHT;
        this.pendingDirection = this.direction;
        this.score = 0;
        this.gameOver = false;
        spawnApple();
    }

    /** Constructor with explicit random seed for deterministic testing. */
    public SnakeGame(long randomSeed) {
        this.width = 15;
        this.height = 15;
        this.occupied = new boolean[height][width];
        this.snake = new LinkedQueue<>();
        this.rand = new Random(randomSeed);
        // centre position
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
        Position head = snake.peek();
        int newRow = head.getRow();
        int newCol = head.getColumn();
        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }
        // wall collision
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
            gameOver = true;
            return;
        }
        // self collision
        if (occupied[newRow][newCol]) {
            gameOver = true;
            return;
        }
        // advance head
        Position newHead = new Position(newRow, newCol);
        snake.enqueue(newHead);
        occupied[newRow][newCol] = true;
        // apple handling
        if (apple != null && newRow == apple.getRow() && newCol == apple.getColumn()) {
            score++;
            apple = null;
            // spawn new apple if space remains
            if (snake.getSize() < width * height) {
                spawnApple();
            } else {
                // board filled – game over
                gameOver = true;
            }
        } else {
            // normal move – remove tail
            Position tail = snake.dequeue();
            occupied[tail.getRow()][tail.getColumn()] = false;
        }
    }

    /** Attempts to change direction; rejects opposite of current direction. */
    public void changeDirection(Direction newDir) {
        if (newDir == null) {
            return;
        }
        // reject direct opposite moves
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

    /** Helper: current snake length. */
    /** Places an apple on a random empty cell. */
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
}