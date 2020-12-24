package Service;

import ServerModels.Player;
import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.Queue;



public class SearchQueueService {

    @Inject
    private GameSessionsService gameSessionsService;

    final Queue<Player> playersQueue = new LinkedList<Player>();


    public void addPlayerIntoAQueue(Player player) {
        synchronized (playersQueue) {
            playersQueue.add(player);
        }
    }

    public boolean removePlayerFromQueue(Player player) {
        synchronized (playersQueue) {
            return playersQueue.remove(player);
        }
    }

    public boolean isPlayerBusy(Player player) {
        synchronized (playersQueue) {
            return playersQueue.contains(player);
        }
    }

    public void startMatch() {
        synchronized (playersQueue) {
            if (playersQueue.size() < 2)
                return;

            Player player1 = playersQueue.poll();
            Player player2 = playersQueue.poll();
            gameSessionsService.startNewMatch(player1, player2);
        }
    }
}
