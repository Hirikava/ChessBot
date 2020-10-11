import AppStart.BotConfig;
import AppStart.DataBaseConfiguration;
import DI.DIContainer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.telegram.telegrambots.ApiContextInitializer;


import javax.sql.DataSource;


public class ChessBotService {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DIContainer());
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        ApiContextInitializer.init();
        ChessBot chessBot = injector.getInstance(ChessBot.class);
        BotConfig.Configure(chessBot);
    }
}
