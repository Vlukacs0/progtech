package kodok;

import java.util.Objects;

public record Player(String name, char symbol) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return symbol == player.symbol && Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", symbol=" + symbol +
                '}';
    }
}