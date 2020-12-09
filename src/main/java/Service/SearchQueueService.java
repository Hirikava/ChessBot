package Service;

import ServerModels.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchQueueService {


    private ConcurrentLinkedQueue<Player> playersQueue = new ConcurrentLinkedQueue<Player>();

    public void addPlayerIntoAQueue(Player player) {
        playersQueue.add(player);
    }

    public boolean removePlayerFromQueue(Player player) {
        return playersQueue.remove(player);
    }

    public boolean isPlayerBusy(Player player) {
        return playersQueue.contains(player);
    }
}
