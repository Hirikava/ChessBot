package Service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ISendMessageService {
    void Send(BotApiMethod<Message> message);
}
