package ServerModels;

public class Match {
    private int PlayerId1;
    private int PlayerId2;
    private int WinnerId;

    public Match(int playerId1, int playerId2, int winnerId) {
        PlayerId1 = playerId1;
        PlayerId2 = playerId2;
        WinnerId = winnerId;
    }

    public int getPlayerId1() {
        return PlayerId1;
    }

    public int getPlayerId2() {
        return PlayerId2;
    }

    public int getWinnerId() {
        return WinnerId;
    }
}
