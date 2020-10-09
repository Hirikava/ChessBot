import Configuration.BotConfig;
import Configuration.DataBaseConfiguration;
import Services.ChessBot;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ChessBotService {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        DataBaseConfiguration.Configure();
        BotConfig.Configure();
    }
}
