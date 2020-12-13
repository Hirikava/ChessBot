package Service;

import Domain.GameSession;
import Domain.PlayerColour;
import ServerModels.Player;
import com.google.inject.Inject;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;


import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameSessionsService {


    @Inject
    private ISendMessageService sendMessageService;

    @Inject
    private ChessBordRenderer chessBordRenderer;

    private ConcurrentHashMap<Player, Triplet<Player, GameSession, PlayerColour>> gameSessionsMap
            = new ConcurrentHashMap<Player, Triplet<Player, GameSession, PlayerColour>>();


    public void startNewMatch(Player player1, Player player2) {
        GameSession gameSession = new GameSession();

        gameSessionsMap.put(player1, new Triplet<Player, GameSession, PlayerColour>(player2, gameSession, PlayerColour.White));
        gameSessionsMap.put(player2, new Triplet<Player, GameSession, PlayerColour>(player1, gameSession, PlayerColour.Black));

        SendMessage message = new SendMessage(player1.getChatId(), String.format("Ваш соперник %s.", player2.getUserName()));
        SendMessage message2 = new SendMessage(player2.getChatId(), String.format("Ваш соперник %s.", player1.getUserName()));
        sendMessageService.Send(message);
        sendMessageService.Send(message2);

        ByteArrayInputStream forWhite = chessBordRenderer.RenderChessBoard(gameSession.createGameState().getBoard(), PlayerColour.White);
        ByteArrayInputStream forBlack = chessBordRenderer.RenderChessBoard(gameSession.createGameState().getBoard(), PlayerColour.Black);

        SendPhoto sendPhoto = new SendPhoto(player1.getChatId(), new InputFile().setMedia(forWhite, "board"));
        SendPhoto sendPhoto2 = new SendPhoto(player2.getChatId(), new InputFile().setMedia(forBlack, "board"));
        sendMessageService.Send(sendPhoto);
        sendMessageService.Send(sendPhoto2);
    }

    public void endMatch(Player player1, Player player2) {
        gameSessionsMap.remove(player1);
        gameSessionsMap.remove(player2);
    }

    public Triplet<Player, GameSession, PlayerColour> getGameSession(Player player) {
        return gameSessionsMap.get(player);
    }
}
