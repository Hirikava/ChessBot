package ServerModels;

import java.util.Objects;

public class Player {
    final int Id;

    public Player(int id) {
        Id = id;
    }

    public int getId() {
        return this.Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId() == player.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
