package Controllers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IController {
    BotApiMethod<Message> ExecuteCommand(Message message);
}
