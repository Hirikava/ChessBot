package Controllers;

import Providers.MatchesDao;
import ServerModels.Match;
import ServerModels.Player;
import Service.ISendMessageService;
import com.google.inject.Inject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;


public class HistoryController extends AuthorizedController {

    @Inject
    private MatchesDao matchesDao;

    @Inject
    private ISendMessageService sendMessageService;

    @Override
    protected void ExecuteCommandInternal(Message message, Player player) {

        Optional<List<Match>> matсhesOptional = matchesDao.Get(player.getId());
        if (matсhesOptional.isPresent()) {
            List<Match> matches = matсhesOptional.get();
            if (matches.size() == 0) {
                sendMessageService.SendMessage(player.getChatId(), "Вы ещё не сыграли ни одного матча.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Недавние матчи:");
                sb.append("\n");
                for (Match match : matches) {
                    Optional<Player> player1 = playerDao.Get(match.getPlayerId1());
                    Optional<Player> player2 = playerDao.Get(match.getPlayerId2());
                    sb.append(String.format("%s - %s : %s", player1.isPresent() ? player1.get().getUserName() : "unknown", player1.isPresent() ? player2.get().getUserName() : "unknown", match.getWinnerId() == player.getId() ? "win" : "lose"));
                    sb.append("\n");
                }
                sendMessageService.SendMessage(player.getChatId(), sb.toString());
            }

        }
    }
}
