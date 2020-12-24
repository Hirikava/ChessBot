package Controllers;

import Providers.PlayerDao;
import ServerModels.Player;
import Service.ISendMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Inject;
import java.util.Optional;

public class StartController implements IController {

    @Inject
    private ISendMessageService sendMessageService;

    @Inject
    private PlayerDao playerDao;

    public void ExecuteCommand(Message message) {
        Optional<Player> optionalPlayer = playerDao.Get(message.getFrom().getId());
        if (optionalPlayer.isPresent()) {
            sendMessageService.SendMessage(message.getChat().getId().toString(), "Вы уже зарегестрированы.");
            return;
        }

        playerDao.Insert(new Player(message.getFrom().getId(), message.getChatId().toString(), message.getFrom().getUserName()));
        sendMessageService.SendMessage(message.getChat().getId().toString(), "Вы успешно зарегистрированы.");
    }
}
