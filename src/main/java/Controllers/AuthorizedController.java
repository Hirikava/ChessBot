package Controllers;

import Providers.PlayerRepository;
import ServerModels.Player;
import Service.ISendMessageService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

public class AuthorizedController implements IController {

    @Inject
    protected ISendMessageService sendMessageService;

    @Inject
    protected PlayerRepository playerDao;

    @Override
    public void ExecuteCommand(Message message) {
        Integer userId = message.getFrom().getId();
        Optional<Player> player = playerDao.Get(userId);

        if (!player.isPresent()) {
            sendMessageService.SendMessage(message.getChatId().toString(), "Для таго что бы начать взаимодействовать с ботом зарегистрируйтесь с помощью команды /start");
            return;
        }
        ExecuteAuthorizedCommand(message, player.get());
    }

    protected void ExecuteAuthorizedCommand(Message message, Player player) {
        throw new NotImplementedException();
    }


}
