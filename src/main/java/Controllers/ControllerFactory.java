package Controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ControllerFactory {

    @Inject
    private Injector injector;

    public IController GetController(Message message) {
        String text = message.getText();

        if (text.equals("/start"))
            return injector.getInstance(StartController.class);
        if (text.equals("/play"))
            return injector.getInstance(EnqueueController.class);
        if (text.equals("/quit"))
            return injector.getInstance(DequeueController.class);
        if (text.equals("/help"))
            return injector.getInstance(HelpController.class);
        if (text.startsWith("/turn "))
            return injector.getInstance(TurnController.class);
        if (text.equals("/concede"))
            return injector.getInstance(ConcedeController.class);
        return injector.getInstance(UnknownCommandController.class);
    }
}
