package Controllers;

import Providers.PlayerDao;
import ServerModels.Player;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Inject;
import java.util.Optional;

public class StartController implements IController {

    @Inject
    private PlayerDao playerDao;

    public BotApiMethod<Message> ExecuteCommand(Message message) {
        Optional<Player> optionalPlayer = playerDao.Get(message.getFrom().getId());
        if (optionalPlayer.isPresent())
            return new SendMessage(message.getChat().getId().toString(), "Вы уже зарегестрированы.");

        playerDao.Insert(new Player(message.getFrom().getId()));
        return new SendMessage(message.getChat().getId().toString(), "Вы успешно зарегистрированы.");
    }
}
