package Controllers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ControllerFactory {

    @Inject
    private Injector injector;

    public IController GetController(Message message) {
        String text = message.getText();
        if (text.startsWith("/start"))
            return injector.getInstance(StartController.class);
        return injector.getInstance(UnknownCommandController.class);
    }
}
