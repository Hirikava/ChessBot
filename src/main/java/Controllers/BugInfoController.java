package Controllers;

import Providers.BugInfoDao;
import ServerModels.BugInfo;
import ServerModels.Player;
import Service.ISendMessageService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Named;
import java.util.logging.Logger;

public class BugInfoController extends AuthorizedController {
    @Inject
    private BugInfoDao bugInfoDao;

    @Inject @Named("logger")
    private Logger logger;

    @Inject
    private ISendMessageService sendMessageService;

    @Override
    protected void ExecuteAuthorizedCommand(Message message, Player player) {
        bugInfoDao.Insert(new BugInfo(player.getId(), message.getText()));
        logger.info(String.format("User:{%s} send bug Report:{%s}", player.getChatId(), message.getText()));
        sendMessageService.SendMessage(player.getChatId(), "Спасибо, что помогаете сделать наш сервис лучше.");
    }
}
