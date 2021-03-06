package Controllers;

import ServerModels.GameInfo;
import ServerModels.Player;
import Service.GameSessionsService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GameSessionController extends AuthorizedController {

    @Inject
    protected GameSessionsService gameSessionsService;

    @Override
    protected void ExecuteAuthorizedCommand(Message message, Player player) {
        GameInfo gameInfo = gameSessionsService.getGameSession(player);
        if (gameInfo == null) {
            sendMessageService.SendMessage(player.getChatId(), "У вас нет активной игровой сессии.");
            return;
        }
        ExecuteGameSessionCommand(message, player, gameInfo);
    }

    protected void ExecuteGameSessionCommand(Message message, Player player, GameInfo gameInfo) {
        throw new NotImplementedException();
    }
}
