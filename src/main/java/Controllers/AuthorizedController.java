package Controllers;

import Providers.PlayerDao;
import ServerModels.Player;
import Service.ISendMessageService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

public class AuthorizedController implements IController {

    @Inject
    protected ISendMessageService sendMessageService;

    @Inject
    protected PlayerDao playerDao;

    @Override
    public void ExecuteCommand(Message message) {
        Integer userId = message.getFrom().getId();
        Optional<Player> player = playerDao.Get(userId);

        if (!player.isPresent()) {
            sendMessageService.Send(new SendMessage(message.getChatId().toString(), "Для таго что бы начать взаимодействовать с ботом зарегистрируйтесь с помощью команды /start"));
            return;
        }

        ExecuteCommandInternal(message, player.get());
    }

    protected void ExecuteCommandInternal(Message message, Player player) {
        throw new NotImplementedException();
    };
}
