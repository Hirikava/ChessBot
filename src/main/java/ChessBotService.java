import AppStart.BotConfiguration;
import AppStart.DataBaseConfiguration;
import DI.DIContainer;
import Infrastructer.DataBaseConnectionInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.telegram.telegrambots.ApiContextInitializer;


import javax.sql.DataSource;


public class ChessBotService {
    public static void main(String[] args) {
        DataBaseConnectionInfo dataBaseConnectionInfo = new DataBaseConnectionInfo("jdbc:h2:./ChessBotDB", org.h2.Driver.load());
        Injector injector = Guice.createInjector(new DIContainer(dataBaseConnectionInfo));
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        ApiContextInitializer.init();
        ChessBot chessBot = injector.getInstance(ChessBot.class);
        BotConfiguration.Configure(chessBot);
    }
}
