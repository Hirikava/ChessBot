package Controllers;

import Domain.*;
import Providers.MatchesDao;
import ServerModels.GameInfo;
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

        GameInfo gameInfo = gameSessionsService.getGameSession(player);
        if (gameInfo == null) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "У вас нет активной игровой сессии."));
            return;
        }

        Pair<Cords, Cords> cords = GetCordsFromMessage(message.getText());
        if (cords == null) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "Введены неверные координаты."));
            return;
        }

        GameSession gameSession = gameInfo.getGameSession();
        TurnResult turnResult = gameSession.MakeTurn(gameInfo.getPlayerColour(), cords.getValue0(), cords.getValue1());

        if (turnResult.getTurnError() != TurnError.None) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "Неправильный ход."));
            return;
        }
        Player opponent = gameInfo.getOpponent();

        ByteArrayInputStream imageForPlayer = chessBordRenderer.RenderChessBoard(turnResult.getGameState().getBoard(), gameInfo.getPlayerColour());
        ByteArrayInputStream imageForOpponent = chessBordRenderer.RenderChessBoard(turnResult.getGameState().getBoard(), gameInfo.getPlayerColour() == PlayerColour.White ? PlayerColour.Black : PlayerColour.White);
        sendMessageService.Send(new SendMessage(opponent.getChatId(), String.format("%s: %s", player.getUserName(), message.getText().split(" ")[1])));
        sendMessageService.Send(new SendPhoto(player.getChatId(), new InputFile().setMedia(imageForPlayer, "board")));
        sendMessageService.Send(new SendPhoto(opponent.getChatId(), new InputFile().setMedia(imageForOpponent, "board")));


        if (turnResult.getGameState().getWinner().isPresent()) {
            PlayerColour winnerColor = turnResult.getGameState().getWinner().get();
            matchesDao.Insert(new Match(player.getId(), opponent.getId(), gameInfo.getPlayerColour() == winnerColor ? player.getId() : opponent.getId()));
            gameSessionsService.endMatch(player, opponent);
            sendMessageService.Send(new SendMessage(opponent.getChatId(), String.format("%s одерживает победу", gameInfo.getPlayerColour() == winnerColor ? player.getUserName() : opponent.getUserName())));
            sendMessageService.Send(new SendMessage(player.getChatId(), String.format("%s одерживает победу", gameInfo.getPlayerColour() == winnerColor ? player.getUserName() : opponent.getUserName())));
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
        return new Cords(text.charAt(1) - '1', text.charAt(0) - 'a');
    }
}
