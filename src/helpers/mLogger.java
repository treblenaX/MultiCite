package src.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

public class mLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static public void setup() throws IOException {
        // Load in and set the log properties
        InputStream stream = mLogger.class.getClassLoader()
                .getResourceAsStream("src/resources/logging.properties");
        LogManager.getLogManager().readConfiguration(stream);

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Constants.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();


        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("log.txt");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}
