package othello;

import java.util.Objects;

public final class Square {

    public enum Symbol {

        PLAYER_ONE('O', "\033[1;31m"), PLAYER_TWO('X', "\033[1;34m"), EMPTY('-', "\033[1;37m"), LEGAL('?', "\033[1;32m");

        private final char c;
        private final String color;
        private int result;

        Symbol(char c) {
            this.c = c;
            this.color = "\033[0m";
        }

        Symbol(char c, String color) {
            this.c = c;
            this.color = color;
        }

        public int getResult() {
            return this.result;
        }

        public void setResult(int r) {
            this.result = r;
        }

        public void incrementResult() {
            ++this.result;
        }

        public static Square.Symbol getWinner() {
            if (PLAYER_ONE.getResult() > PLAYER_TWO.getResult()) return PLAYER_ONE;
            else if (PLAYER_ONE.getResult() < PLAYER_TWO.getResult()) return PLAYER_TWO;
            else return EMPTY;
        }

        public static char getSign() {
            if (PLAYER_ONE.getResult() < PLAYER_TWO.getResult()) return '<';
            else if (PLAYER_ONE.getResult() > PLAYER_TWO.getResult()) return '>';
            else return '=';
        }

        @Override
        public String toString() {
            return String.format("%s", this.color + this.c + "\033[0m");
        }

    }

    private class Position {

        private final int row;
        private final int col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return String.format("%s %s", this.row, this.col);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o.getClass() != Position.class) return false;
            if (this == o) return true;
            return this.row == ((Position) o).row && this.col == ((Position) o).col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private Symbol symbol;
    private final Position position;

    public Square(int row, int col) {
        this.symbol = Symbol.EMPTY;
        this.position = new Position(row, col);
    }

    public void setSymbol(Symbol newSymbol) {
	    this.symbol = newSymbol;
    }
    
    public Symbol getSymbol() {
        return this.symbol;
    }

    public int getRow() {
        return this.position.row;
    }

    public int getColumn() {
        return this.position.col;
    }

    public boolean isEmpty() {
        return this.symbol == Symbol.EMPTY;
    }

    public boolean isOpposite(Symbol currentSymbol) {
        return (this.symbol == Symbol.PLAYER_ONE && currentSymbol == Symbol.PLAYER_TWO)
                || (this.symbol == Symbol.PLAYER_TWO && currentSymbol == Symbol.PLAYER_ONE);
    }

    public boolean isSame(Symbol currentSymbol) {
        return this.symbol == currentSymbol;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != Square.class) return false;
        if (this == o) return true;
        return this.position.equals(((Square) o).position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return String.format("%14s", this.symbol);
    }
}
