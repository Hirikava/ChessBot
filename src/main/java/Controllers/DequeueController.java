package Controllers;
import ServerModels.Player;
import Service.SearchQueueService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;


public class DequeueController extends AuthorizedController {

    @Inject
    private SearchQueueService searchQueueService;


    @Override
    public void ExecuteCommandInternal(Message message, Player player) {
        if (!searchQueueService.isPlayerBusy(player)) {
            sendMessageService.SendMessage(message.getChatId().toString(), "Вы не находится в очереди подбора соперников. Для того чтобы встать в очерель воспользуйтесь командой /play");
            return;
        }

        searchQueueService.removePlayerFromQueue(player);
        sendMessageService.SendMessage(message.getChatId().toString(), "Вы вышли из очереди подбора игроков.");
    }
}
