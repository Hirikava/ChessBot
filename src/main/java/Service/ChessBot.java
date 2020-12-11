package Service;

import Controllers.ControllerFactory;
import Controllers.IController;
import com.google.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChessBot extends TelegramLongPollingBot implements ISendMessageService {

    @Inject
    private ControllerFactory controllerFactory;
    @Inject
    private PlayerLockService playerLockService;


    @Override
    public void onUpdateReceived(Update update) {
        Lock lock = new ReentrantLock();
        try {
            Integer userId = update.getMessage().getFrom().getId();
            lock = playerLockService.getPlayerLock(userId);
            IController controller = controllerFactory.GetController(update.getMessage());
            BotApiMethod<Message> executionResult = controller.ExecuteCommand(update.getMessage());
            execute(executionResult);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
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
