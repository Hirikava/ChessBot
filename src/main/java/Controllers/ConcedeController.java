package Controllers;

import Providers.MatchesDao;
import ServerModels.GameInfo;
import ServerModels.Match;
import ServerModels.Player;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ConcedeController extends GameSessionController {
    @Inject
    private MatchesDao matchesDao;

    @Override
    protected void ExecuteGameSessionCommand(Message message, Player player, GameInfo gameInfo) {
        gameSessionsService.endMatch(player, gameInfo.getOpponent());
        matchesDao.Insert(new Match(player.getId(), gameInfo.getOpponent().getId(), gameInfo.getOpponent().getId()));
        sendMessageService.SendMessage(gameInfo.getOpponent().getChatId(), String.format("%s сдался", player.getUserName()));
    }
}
