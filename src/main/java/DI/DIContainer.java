package DI;
import com.google.inject.AbstractModule;


public class DIContainer extends AbstractModule {
    @Override
    protected void configure() {
        bind(Communicator.class).to(DefaultCommunicatorImpl.class);
    }
}
