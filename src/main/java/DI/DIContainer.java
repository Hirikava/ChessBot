package DI;

import com.google.inject.AbstractModule;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;


public class DIContainer extends AbstractModule {
    @Override
    protected void configure() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(org.h2.Driver.load());
        dataSource.setUrl("jdbc:h2:./ChessBotDB");

        bind(DataSource.class).toInstance(dataSource);

    }
}
