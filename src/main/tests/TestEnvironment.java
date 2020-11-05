import DI.DIContainer;
import Infrastructer.DataBaseConnectionInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestEnvironment {
    public static DataBaseConnectionInfo dataBaseConnectionInfo = new DataBaseConnectionInfo("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", org.h2.Driver.load());
    public static Injector CreateInjector()
    {
        return Guice.createInjector(new DIContainer(dataBaseConnectionInfo));
    }
}
