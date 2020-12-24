package Controllers;

import Domain.*;
import Providers.MatchesDao;
import ServerModels.GameInfo;
import ServerModels.Match;
import ServerModels.Player;
import Service.ChessBordRenderer;
import com.google.inject.Inject;
import org.javatuples.Pair;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.io.ByteArrayInputStream;

public class TurnController extends GameSessionController {


    @Inject
    private ChessBordRenderer chessBordRenderer;

    @Inject
    private MatchesDao matchesDao;


    @Override
    protected void ExecuteCommandInternal(Message message, Player player, GameInfo gameInfo) {
        Pair<Cords, Cords> cords = GetCordsFromMessage(message.getText());
        if (cords == null) {
            sendMessageService.SendMessage(player.getChatId(), "Введены неверные координаты.");
            return;
        }

        GameSession gameSession = gameInfo.getGameSession();
        TurnResult turnResult = gameSession.MakeTurn(gameInfo.getPlayerColour(), cords.getValue0(), cords.getValue1());

        if (turnResult.getTurnError() != TurnError.None) {
            sendMessageService.SendMessage(player.getChatId(), "Неправильный ход.");
            return;
        }
        Player opponent = gameInfo.getOpponent();
        sendMessageService.SendMessage(opponent.getChatId(), String.format("%s: %s", player.getUserName(), message.getText().split(" ")[1]));

        renderBoardAndSendToAPlayer(player, turnResult, gameInfo.getPlayerColour());
        renderBoardAndSendToAPlayer(opponent, turnResult, gameInfo.getPlayerColour() == PlayerColour.White ? PlayerColour.Black : PlayerColour.White);

        if (turnResult.getGameState().getWinner().isPresent()) {
            PlayerColour winnerColor = turnResult.getGameState().getWinner().get();
            matchesDao.Insert(new Match(player.getId(), opponent.getId(), gameInfo.getPlayerColour() == winnerColor ? player.getId() : opponent.getId()));
            gameSessionsService.endMatch(player, opponent);

            String winnerMessage = String.format("%s одерживает победу", gameInfo.getPlayerColour() == winnerColor ? player.getUserName() : opponent.getUserName());
            sendMessageService.SendMessage(opponent.getChatId(), winnerMessage);
            sendMessageService.SendMessage(player.getChatId(), winnerMessage);
        }
    }

    private void renderBoardAndSendToAPlayer(Player player, TurnResult turnResult, PlayerColour playerColour) {
        ByteArrayInputStream imageForPlayer = chessBordRenderer.RenderChessBoard(turnResult.getGameState().getBoard(), playerColour);
        if (imageForPlayer != null)
            sendMessageService.SendPhoto(player.getChatId(), imageForPlayer, "board");
        else {
            //log error

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
