import Controllers.ControllerFactory;
import Controllers.IController;
import com.google.inject.Inject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChessBot extends TelegramLongPollingBot {

    @Inject
    private ControllerFactory controllerFactory;


    @Override
    public void onUpdateReceived(Update update) {
        IController controller = controllerFactory.GetController(update.getMessage());
        BotApiMethod<Message> executionResult = controller.ExecuteCommand(update.getMessage());
        try {
            execute(executionResult);
        } catch (TelegramApiException exception) {
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


}
