package Service;

import Controllers.ControllerFactory;
import Controllers.IController;
import com.google.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;

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
    public void SendMessage(String chatId, String message) {
        try {
            execute(new SendMessage(chatId, message));
        } catch (TelegramApiException telegramApiException) {
            //log
            telegramApiException.printStackTrace();
        }
    }

    @Override
    public void SendPhoto(String chatId, ByteArrayInputStream byteArrayInputStream, String mediaFileName) {
        try {
            execute(new SendPhoto(chatId, new InputFile().setMedia(byteArrayInputStream, mediaFileName)));
        } catch (TelegramApiException telegramApiException) {
            //log
            telegramApiException.printStackTrace();
        }
    }
}
