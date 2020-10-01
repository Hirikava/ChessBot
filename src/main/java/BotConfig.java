import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotConfig {

    public static void Configure() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            ChessBot requesterBot = new ChessBot();
            telegramBotsApi.registerBot(requesterBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
