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

import java.util.List;

public class TurnController extends AuthorizedController {

    @Inject
    private GameSessionsService gameSessionsService;

    @Override
    protected BotApiMethod<Message> ExecuteCommandInternal(Message message, Player player) {

        Triplet<List<Player>, GameSession, PlayerColour> gameInfo = gameSessionsService.getGameSession(player);
        if (gameInfo == null)
            return new SendMessage(player.getChatId(), "У вас нет активной игровой сессии.");


        Pair<Cords, Cords> cords = GetCordsFromMessage(message.getText());
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

    private Pair<Cords, Cords> GetCordsFromMessage(String messageText) {
        String[] tokens = messageText.split(" ");

        if (tokens.length < 2)
            return null;
        String[] cords = tokens[1].split("-");
        if (cords.length != 2)
            return null;

        Cords cords1 = GetCoordinate(cords[0]);
        Cords cords2 = GetCoordinate(cords[0]);
        if (cords1 != null && cords2 != null)
            return new Pair<Cords, Cords>(cords1, cords2);
        return null;
    }

    private Cords GetCoordinate(String text) {
        if (text.length() != 2)
            return null;
        return new Cords(text.charAt(0) - 'a', text.charAt(1) - '1');
    }
}
