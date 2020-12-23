package Controllers;

import Domain.GameSession;
import Domain.PlayerColour;
import Providers.MatchesDao;
import ServerModels.Match;
import ServerModels.Player;
import Service.GameSessionsService;
import com.google.inject.Inject;
import org.javatuples.Triplet;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ConcedeController extends AuthorizedController {

    @Inject
    private GameSessionsService gameSessionsService;

    @Inject
    private MatchesDao matchesDao;

    @Override
    protected void ExecuteCommandInternal(Message message, Player player) {
        Triplet<Player, GameSession, PlayerColour> gameInfo = gameSessionsService.getGameSession(player);

        if (gameInfo == null) {
            sendMessageService.Send(new SendMessage(player.getChatId(), "У вас нет активной игровой сессии."));
            return;
        }

        gameSessionsService.endMatch(player, gameInfo.getValue0());
        matchesDao.Insert(new Match(player.getId(), gameInfo.getValue0().getId(), gameInfo.getValue0().getId()));
        sendMessageService.Send(new SendMessage(gameInfo.getValue0().getChatId(), String.format("%s сдался", player.getUserName())));
    }
}
