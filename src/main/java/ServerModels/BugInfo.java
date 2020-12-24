package ServerModels;

public class BugInfo {

    private String message;
    private int userId;

    public BugInfo(int userId, String message)
    {
        this.userId = userId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }
}
