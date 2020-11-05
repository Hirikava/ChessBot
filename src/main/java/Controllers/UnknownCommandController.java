package Controllers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnknownCommandController implements IController {
    @Override
    public BotApiMethod<Message> ExecuteCommand(Message message) {
        return new SendMessage(message.getChatId().toString(), "Неизвсетная комманда :(");
    }
}
