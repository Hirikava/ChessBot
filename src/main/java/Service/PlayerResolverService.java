package Service;

import ServerModels.Player;
import com.google.inject.Inject;

public class PlayerResolverService {

    @Inject
    private SearchQueueService searchQueueService;

    @Inject
    private GameSessionsService gameSessionsService;

    public boolean IsPlayerBusy(Player player) {
        return searchQueueService.isPlayerBusy(player) || gameSessionsService.getGameSession(player) != null;
    }

}
