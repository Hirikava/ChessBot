package Controllers;

import Domain.*;
import Providers.MatchesDao;
import ServerModels.Match;
import ServerModels.Player;
import Service.ChessBordRenderer;
import Service.GameSessionsService;
import com.google.inject.Inject;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.ByteArrayInputStream;

public class TurnController extends AuthorizedController {

    @Inject
    private GameSessionsService gameSessionsService;

    @Inject
    private ChessBordRenderer chessBordRenderer;

    @Inject
    private MatchesDao matchesDao;


    @Override
    protected void ExecuteCommandInternal(Message message, Player player) {

        Triplet<Player, GameSession, PlayerColour> gameInfo = gameSessionsService.getGameSession(player);
        if (gameInfo == null) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "У вас нет активной игровой сессии."));
            return;
        }

        Pair<Cords, Cords> cords = GetCordsFromMessage(message.getText());
        if (cords == null) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "Введены неверные координаты."));
            return;
        }

        GameSession gameSession = gameInfo.getValue1();
        TurnResult turnResult = gameSession.MakeTurn(gameInfo.getValue2(), cords.getValue0(), cords.getValue1());

        if (turnResult.getTurnError() != TurnError.None) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "Неправильный ход."));
            return;
        }
        Player oponent = gameInfo.getValue0();

        ByteArrayInputStream forPlayer = chessBordRenderer.RenderChessBoard(turnResult.getGameState().getBoard(), gameInfo.getValue2());
        ByteArrayInputStream forOponent = chessBordRenderer.RenderChessBoard(turnResult.getGameState().getBoard(), gameInfo.getValue2() == PlayerColour.White ? PlayerColour.Black : PlayerColour.White);
        sendMessageService.Send(new SendMessage(oponent.getChatId(), String.format("%s: %s", player.getUserName(), message.getText().split(" ")[1])));
        sendMessageService.Send(new SendPhoto(player.getChatId(), new InputFile().setMedia(forPlayer, "board")));
        sendMessageService.Send(new SendPhoto(oponent.getChatId(), new InputFile().setMedia(forOponent, "board")));


        if (turnResult.getGameState().getWinner().isPresent()) {
            PlayerColour winnerColor = turnResult.getGameState().getWinner().get();
            matchesDao.Insert(new Match(player.getId(), oponent.getId(), gameInfo.getValue2() == winnerColor ? player.getId() : oponent.getId()));
            gameSessionsService.endMatch(player, oponent);
        }
    }

    private Pair<Cords, Cords> GetCordsFromMessage(String messageText) {
        String[] tokens = messageText.split(" ");

        if (tokens.length < 2)
            return null;
        String[] cords = tokens[1].split("-");
        if (cords.length != 2)
            return null;

        Cords cords1 = GetCoordinate(cords[0]);
        Cords cords2 = GetCoordinate(cords[1]);
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
