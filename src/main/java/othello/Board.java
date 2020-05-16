package othello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static othello.Square.Symbol.*;

public final class Board implements Iterable<Square> {

    private final List<List<Square>> board;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final int length;

    public Board(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.length = numberOfRows * numberOfColumns;
        this.board = new ArrayList<>();
        for(int i = 0; i < numberOfRows; ++i) {
            List<Square> row = new ArrayList<>();
            for(int j = 0; j < numberOfColumns; ++j) {
                row.add(new Square(i, j));
            }
            this.board.add(row);
        }
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(String.format("%3s", ""));
        for(int i = 0; i < this.numberOfRows; ++i) {
            board.append(String.format("%3d", i));
        }
        board.append(String.format("%n"));

        for(int i = 0; i < this.numberOfRows; ++i) {
            board.append(String.format("%3s", i));
            for (int j = 0; j < this.numberOfColumns; ++j) {
                Square square = getSquare(i, j);
                board.append(square);
            }
            board.append(String.format("%n"));
        }

        return board.toString();
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setSquare(int row, int col, Square.Symbol newSymbol) {
        Square square = getSquare(row, col);
        square.setSymbol(newSymbol);
    }

    public Square getSquare(int row, int col) {
        return this.board.get(row).get(col);
    }

    public boolean hasEmptySquares() {
        return this.stream().anyMatch(Square::isEmpty);
    }

    public void countSquares() {
        PLAYER_ONE.setResult(0);
        PLAYER_TWO.setResult(0);
        this.forEach(square -> square.getSymbol().incrementResult());
    }

    public Stream<Square> stream() {
        Stream.Builder<Square> builder = Stream.builder();
        for (Square square : this) {
            builder.add(square);
        }
        return builder.build();
    }

    @Override
    public Iterator<Square> iterator() {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<Square> {

        private int currentSquare = 0;

        @Override
        public boolean hasNext() {
            return this.currentSquare < length;
        }

        @Override
        public Square next() {
            int row = this.currentSquare / numberOfRows;
            int col = this.currentSquare % numberOfColumns;
            ++this.currentSquare;
            return board.get(row).get(col);
        }
    }
}
