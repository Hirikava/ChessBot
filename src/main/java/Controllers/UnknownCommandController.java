package Controllers;

import Service.ISendMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Inject;

public class UnknownCommandController implements IController {

    @Inject
    private ISendMessageService sendMessageService;

    @Override
    public void ExecuteCommand(Message message) {
        sendMessageService.Send(new SendMessage(message.getChatId().toString(), "Неизвсетная комманда :("));
    }
}
