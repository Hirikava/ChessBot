package Controllers;

import Providers.PlayerDao;
import ServerModels.Player;
import Service.PlayerResolverService;
import Service.SearchQueueService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class EnqueueController extends AuthorizedController {

    @Inject
    private PlayerResolverService playerResolverService;

    @Inject
    private SearchQueueService searchQueueService;

    @Override
    public BotApiMethod<Message> ExecuteCommandInternal(Message message) {
        Integer userId = message.getFrom().getId();

        Optional<Player> player = playerDao.Get(userId);
        if(!player.isPresent())


        if(playerResolverService.IsPlayerBusy(player.get()))
            return new SendMessage(message.getChatId().toString(), "Вы уже совершаете действие, завершите его перед тем как нчать новое");

        searchQueueService.addPlayerIntoAQueue(player.get());
        return new SendMessage(message.getChatId().toString(), "Вы успешно вствли в очередь, для поиска игры");
    }
}