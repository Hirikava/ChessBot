package DI;

import Infrastructer.DataBaseConnectionInfo;
import Service.PlayerLockService;
import Service.SearchQueueService;
import com.google.inject.AbstractModule;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;


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

        bind(PlayerLockService.class).toInstance(new PlayerLockService());
        bind(SearchQueueService.class).toInstance(new SearchQueueService());
    }
}
