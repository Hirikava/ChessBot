package AppStart;

import java.util.logging.*;

public class LoggerConfiguration {

    public static void Configure(Logger logger, String loggerName) {
        try {
            FileHandler fileHandler = new FileHandler(loggerName);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
