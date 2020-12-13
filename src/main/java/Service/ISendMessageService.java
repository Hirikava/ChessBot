package Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.Serializable;

public interface ISendMessageService {
    void Send(SendMessage message);

    void Send(SendPhoto message);
}
