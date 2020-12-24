package Controllers;

import Providers.PlayerDao;
import ServerModels.Player;
import Service.ISendMessageService;
import com.google.inject.name.Named;
import org.telegram.telegrambots.meta.api.objects.Message;
import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

public class StartController implements IController {

    @Inject
    private ISendMessageService sendMessageService;
    @Inject
    private PlayerDao playerDao;

    @Inject @Named("logger")
    private Logger logger;

    public void ExecuteCommand(Message message) {
        Optional<Player> optionalPlayer = playerDao.Get(message.getFrom().getId());
        if (optionalPlayer.isPresent()) {
            sendMessageService.SendMessage(message.getChat().getId().toString(), "Вы уже зарегестрированы.");
            return;
        }

        Player newPlayer =  new Player(message.getFrom().getId(), message.getChatId().toString(), message.getFrom().getUserName());
        playerDao.Insert(newPlayer);
        sendMessageService.SendMessage(message.getChat().getId().toString(), "Вы успешно зарегистрированы.");
        logger.info(String.format("New user: %s - %s", newPlayer.getUserName(), newPlayer.getChatId()));
    }
}
