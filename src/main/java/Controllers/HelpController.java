package Controllers;

import Service.ISendMessageService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpController implements IController {

    @Inject
    private ISendMessageService sendMessageService;

    private static String helpMessage = "/start - начать взаимодействие с ботом \n\n" +
            "Поиск соперников:\n" +
            "/play - встать в очередь подбора игроков \n" +
            "/quit - выйти из очереди подбора игроков \n\n" +
            "Внутреигровые команды: \n" +
            "/turn x1y1-x2y2 - сделать ход из клетки x1y1 в клетку x2y2, где x - это литерал от a-h, а y - это чифра от 1 - 8. \n" +
            "/concede - сдаться \n\n" +
            "Дополнительные команды:\n" +
            "/history - посмотреть список недавних матчей\n" +
            "/bug {сообщение об ошибке} - оставить сообщение об ошибке";

    @Override
    public void ExecuteCommand(Message message) {
        sendMessageService.SendMessage(message.getChatId().toString(), helpMessage);
    }
}
