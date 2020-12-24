package DI;

import Infrastructer.DataBaseConnectionInfo;
import Service.*;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.logging.Logger;


public class DIContainer extends AbstractModule {

    private DataBaseConnectionInfo dataBaseConnectionInfo;

    public DIContainer(DataBaseConnectionInfo dataBaseConnectionInfo) {
        this.dataBaseConnectionInfo = dataBaseConnectionInfo;
    }

    @Override
    protected void configure() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(dataBaseConnectionInfo.getConnectionDriver());
        dataSource.setUrl(dataBaseConnectionInfo.getConnectionUri());
        bind(DataSource.class).toInstance(dataSource);

        ChessBot chessBot = new ChessBot();

        bind(IChessGameAssetProvider.class).toInstance(new ChessGameAssetProvider());
        bind(GameSessionsService.class).toInstance(new GameSessionsService());
        bind(ChessBot.class).toInstance(chessBot);
        bind(ISendMessageService.class).toInstance(chessBot);
        bind(SearchQueueService.class).toInstance(new SearchQueueService());
        bind(Logger.class).annotatedWith(Names.named("logger")).toInstance(Logger.getLogger(""));
    }
}
