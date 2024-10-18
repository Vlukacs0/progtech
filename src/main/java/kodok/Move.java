package kodok;

public record Move(int column, char symbol) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return column == move.column && symbol == move.symbol;
    }

    @Override
    public String toString() {
        return "Move{" +
                "column=" + column +
                ", symbol=" + symbol +
                '}';
    }
}


