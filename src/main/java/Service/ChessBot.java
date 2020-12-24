package Service;

import Controllers.ControllerFactory;
import Controllers.IController;
import com.google.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChessBot extends TelegramLongPollingBot implements ISendMessageService {

    @Inject
    private ControllerFactory controllerFactory;


    @Override
    public void onUpdateReceived(Update update) {
        try {
            IController controller = controllerFactory.GetController(update.getMessage());
            controller.ExecuteCommand(update.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Do Not Touch
    @Override
    public String getBotToken() {
        return "1365111325:AAENOA5DI70PDD-Q2AkmORcVpSh9PQpN680";
    }


    @Override
    public String getBotUsername() {
        return "PvP Chess Bot";
    }


    @Override
    public void Send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    @Override
    public void Send(SendPhoto message) {
        try {
            execute(message);
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }
}
