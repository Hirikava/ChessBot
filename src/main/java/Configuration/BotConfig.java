package Configuration;

import Services.ChessBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BotConfig {

    public static void Configure() {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            ChessBot chessBot = new ChessBot();
            telegramBotsApi.registerBot(chessBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
