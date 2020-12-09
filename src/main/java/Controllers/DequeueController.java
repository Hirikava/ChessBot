package Controllers;
import Providers.PlayerDao;
import ServerModels.Player;
import Service.SearchQueueService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class DequeueController implements IController {
    @Inject
    private PlayerDao playerDao;

    @Inject
    private SearchQueueService searchQueueService;


    @Override
    public BotApiMethod<Message> ExecuteCommand(Message message) {
        Integer userId = message.getFrom().getId();
        Optional<Player> player = playerDao.Get(userId);
        if(!player.isPresent())
            return new SendMessage(message.getChatId().toString(), "Для таго что бы начать взаимодействовать с ботом зарегистрируйтесь с помощью команды /start");


        if(!searchQueueService.isPlayerBusy(player.get()))
            return new SendMessage(message.getChatId().toString(), "Вы не находится в очереди подбора соперников.");

        searchQueueService.removePlayerFromQueue(player.get());
        return new SendMessage(message.getChatId().toString(), "Вы вышли из очереди подбора игроков");
    }
}
