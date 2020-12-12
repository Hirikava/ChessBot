package Controllers;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface IController {
    void ExecuteCommand(Message message);
}
