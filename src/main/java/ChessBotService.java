import AppStart.BotConfiguration;
import AppStart.DataBaseConfiguration;
import DI.DIContainer;
import Infrastructer.DataBaseConnectionInfo;
import Service.SearchQueueService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.deploy.cache.Cache;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.server.threadpool.JobRunrExecutor;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;


import javax.sql.DataSource;


public class ChessBotService {
    private static Cache ApiContextInitializer;

    public static void main(String[] args) {
        DataBaseConnectionInfo dataBaseConnectionInfo = new DataBaseConnectionInfo("jdbc:h2:./ChessBotDB", org.h2.Driver.load());
        Injector injector = Guice.createInjector(new DIContainer(dataBaseConnectionInfo));
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        ApiContextInitializer.init();
        ChessBot chessBot = injector.getInstance(ChessBot.class);
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
