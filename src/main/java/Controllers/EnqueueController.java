package Controllers;

import ServerModels.Player;
import Service.PlayerResolverService;
import Service.SearchQueueService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class EnqueueController extends AuthorizedController {

    @Inject
    private PlayerResolverService playerResolverService;

    @Inject
    private SearchQueueService searchQueueService;

    @Override
    public void ExecuteCommandInternal(Message message, Player player) {
        if (playerResolverService.IsPlayerBusy(player)) {
            sendMessageService.Send(new SendMessage(message.getChatId().toString(), "Вы уже совершаете действие, завершите его перед тем как нчать новое"));
            return;
        }

        searchQueueService.addPlayerIntoAQueue(player);
        sendMessageService.Send(new SendMessage(message.getChatId().toString(), "Вы успешно вствли в очередь, для поиска игры"));
    }
}
