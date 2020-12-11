package Controllers;

import Domain.*;
import ServerModels.Player;
import Service.GameSessionsService;
import com.google.inject.Inject;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class TurnController extends AuthorizedController {

    @Inject
    private GameSessionsService gameSessionsService;

    @Override
    protected BotApiMethod<Message> ExecuteCommandInternal(Message message, Player player) {

        Triplet<List<Player>, GameSession, PlayerColour> gameInfo = gameSessionsService.getGameSession(player);
        if (gameInfo == null)
            return new SendMessage(player.getChatId(), "У вас нет активной игровой сессии.");


        Pair<Coords, Coords> cords = GetCordsFromMessage(message.getText());
        if (cords == null) {
            return new SendMessage(player.getChatId(), "Введены неверные координаты.");
        }

        GameSession gameSession = gameInfo.getValue1();
        TurnResult turnResult = gameSession.MakeTurn(gameInfo.getValue2(), cords.getValue0(), cords.getValue1());

        if (turnResult.getTurnError() != TurnError.None) {
            return new SendMessage(player.getChatId(), "Неправильный ход.");
        }

        return new SendMessage(player.getChatId(), "Верный ход");
    }

    private Pair<Coords, Coords> GetCordsFromMessage(String messageText) {
        String[] tokens = messageText.split(" ");

        if (tokens.length < 2)
            return null;
        String[] cords = tokens[1].split("-");
        if (cords.length != 2)
            return null;

        Coords cords1 = GetCoordinate(cords[0]);
        Coords cords2 = GetCoordinate(cords[0]);
        if (cords1 != null && cords2 != null)
            return new Pair<Coords, Coords>(cords1, cords2);
        return null;
    }

    private Coords GetCoordinate(String text) {
        if (text.length() != 2)
            return null;
        Coords cord = new Coords();
        cord.X = text.charAt(0) - 'a';
        cord.Y = text.charAt(1) - '1';
        return cord;
    }
}
