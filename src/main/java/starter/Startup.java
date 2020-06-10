package starter;

import classes.Train;
import org.apache.log4j.Logger;
import process.TunnelController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Startup {
    private static final Logger LOGGER = Logger.getLogger(Startup.class);
    private static final int NUMBER_OF_TRAINS = 10;
    private static final int SLEEP_IN_SECONDS = 1;
    private static final int RANDOM_ID_TRAIN = 1000;

    public static void main(String[] args) {
        LOGGER.info("----------Entering the application----------");

        TunnelController tunnelController = TunnelController.getInstance();
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_TRAINS; i++) {
            try {
                TimeUnit.SECONDS.sleep(SLEEP_IN_SECONDS);
                Train train = new Train(tunnelController, random.nextBoolean(), random.nextInt(RANDOM_ID_TRAIN));
                LOGGER.info(train + " arrived");
                train.start();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

}
