import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Console;


public class ChessBot extends TelegramLongPollingBot {

    public ChessBot() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Console log = System.console();
        log.printf(message.getText());
    }

    public String getBotUsername() {
        return "Chess Fun Club";
    }

    public String getBotToken() {
        return "1365111325:AAENOA5DI70PDD-Q2AkmORcVpSh9PQpN680";
    }
}
