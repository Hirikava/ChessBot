import AppStart.BotConfiguration;
import AppStart.DataBaseConfiguration;
import DI.DIContainer;
import Infrastructer.DataBaseConnectionInfo;
import Service.SearchQueueService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.deploy.cache.Cache;


import javax.sql.DataSource;


public class ChessBotService {
    private static Cache ApiContextInitializer;

    public static void main(String[] args) {
        DataBaseConnectionInfo dataBaseConnectionInfo = new DataBaseConnectionInfo("jdbc:h2:./ChessBotDB", org.h2.Driver.load());
        Injector injector = Guice.createInjector(new DIContainer(dataBaseConnectionInfo));
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        ApiContextInitializer.init();
        Service.ChessBot chessBot = injector.getInstance(Service.ChessBot.class);
        BotConfiguration.Configure(chessBot);

        SearchQueueService searchQueueService = injector.getInstance(SearchQueueService.class);
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    searchQueueService.startMatch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
