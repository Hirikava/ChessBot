package Service;

import ServerModels.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerLockService {

    private ConcurrentHashMap<Integer, Lock> concurrentHashMap = new ConcurrentHashMap<Integer, Lock>();

    public Lock getPlayerLock(Integer userId) {
        Lock lock;
        if (concurrentHashMap.containsKey(userId)) {
            lock = concurrentHashMap.get(userId);
            lock.lock();
        }
        else
        {
            lock = new ReentrantLock();
            lock.lock();
            concurrentHashMap.put(userId, lock);
        }
        return lock;
    }
}
