/** A row and column location on the game board. */
/** Row‑column coordinate on the board. */
public class Position {
    private final int row;
    private final int column;

    /** Creates a position. */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** Returns the row index. */
    public int getRow() {
        return row;
    }

    /** Returns the column index. */
    public int getColumn() {
        return column;
    }
}