package Service;

import Domain.GameSession;
import Domain.GameState;
import Domain.PlayerColour;
import ServerModels.GameInfo;
import ServerModels.Player;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class GameSessionsService {

    @Inject
    private ISendMessageService sendMessageService;

    @Inject
    private ChessBordRenderer chessBordRenderer;

    @Inject @Named("logger")
    private Logger logger;

    private ConcurrentHashMap<Player, GameInfo> gameSessionsMap = new ConcurrentHashMap<Player, GameInfo>();


    public void startNewMatch(Player player1, Player player2) {
        GameSession gameSession = new GameSession();

        gameSessionsMap.put(player1, new GameInfo(player2, gameSession, PlayerColour.White));
        gameSessionsMap.put(player2, new GameInfo(player1, gameSession, PlayerColour.Black));

        sendMessageService.SendMessage(player1.getChatId(), String.format("Ваш соперник %s.", player2.getUserName()));
        sendMessageService.SendMessage(player2.getChatId(), String.format("Ваш соперник %s.", player1.getUserName()));
        renderBoardAndSendToAPlayer(player1, gameSession.createGameState(), PlayerColour.White);
        renderBoardAndSendToAPlayer(player2, gameSession.createGameState(), PlayerColour.Black);
    }

    private void renderBoardAndSendToAPlayer(Player player, GameState gameState, PlayerColour playerColour) {
        ByteArrayInputStream boardImage = chessBordRenderer.RenderChessBoard(gameState.getBoard(), PlayerColour.White);
        if (boardImage != null)
            sendMessageService.SendPhoto(player.getChatId(), boardImage, "board");
        else {
            logger.fine(String.format("Renderer returned null, don't send photo to User:{%s}", player.getChatId()));
        }
    }

    public void endMatch(Player player1, Player player2) {
        gameSessionsMap.remove(player1);
        gameSessionsMap.remove(player2);
    }

    public GameInfo getGameSession(Player player) {
        return gameSessionsMap.get(player);
    }
}
