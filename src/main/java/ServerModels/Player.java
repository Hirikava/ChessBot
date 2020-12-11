package ServerModels;

import java.util.Objects;

public class Player {

    final int Id;
    final String ChatId;
    final String UserName;

    public Player(int id, String chatId, String userName) {
        ChatId = chatId;
        Id = id;
        UserName = userName;
    }

    public int getId() {
        return this.Id;
    }

    public String getChatId() {
        return ChatId;
    }

    public String getUserName() {
        return UserName;
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
