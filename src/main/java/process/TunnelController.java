package process;

import classes.Train;
import classes.Tunnel;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TunnelController {
    private Tunnel firstTunnel;
    private Tunnel secondTunnel;
    private Semaphore firstTunnelSemaphor;
    private Semaphore secondTunnelSemaphor;

    private final static int SIZE = 3;
    private final static String FIRST_TUNNEL_NAME = "FIRST";
    private final static String SECOND_TUNNEL_NAME = "SECOND";

    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static TunnelController instance = null;
    private static ReentrantLock lock = new ReentrantLock();

    private TunnelController() {
        firstTunnel = new Tunnel(FIRST_TUNNEL_NAME);
        secondTunnel = new Tunnel(SECOND_TUNNEL_NAME);

        firstTunnelSemaphor = new Semaphore(SIZE, true);
        secondTunnelSemaphor = new Semaphore(SIZE, true);
    }

    public static TunnelController getInstance() {
        if (!instanceCreated.get()){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TunnelController();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Tunnel approachTunnel (Train train) {
        while (true) {
            if ((firstTunnel.getCurrentDirection() == null)
                    || (train.getDirection() == firstTunnel.getCurrentDirection().get())) {
                if (firstTunnelSemaphor.tryAcquire()) {
                    startMovingTrainInTunnel(firstTunnel, train);
                    return firstTunnel;
                }
            } else if ((secondTunnel.getCurrentDirection() == null)
                        || (train.getDirection() == secondTunnel.getCurrentDirection().get())) {
                if (secondTunnelSemaphor.tryAcquire()) {
                    startMovingTrainInTunnel(secondTunnel, train);
                    return secondTunnel;
                }
            }
        }
    }

    public void leaveTunnel (Tunnel tunnel) {
        if (tunnel.getName().equals(firstTunnel.getName())) {
            firstTunnel.popTrain();
            if (firstTunnel.getTrains().isEmpty()) {
                firstTunnel.setCurrentDirection(null);
            }
            firstTunnelSemaphor.release();
        } else {
            secondTunnel.popTrain();
            if (secondTunnel.getTrains().isEmpty()) {
                secondTunnel.setCurrentDirection(null);
            }
            secondTunnelSemaphor.release();
        }
    }

    private void startMovingTrainInTunnel (Tunnel tunnel, Train train) {
        if (tunnel.getTrains().isEmpty()) {
            tunnel.setCurrentDirection(new AtomicBoolean(train.getDirection()));
        }
        tunnel.addTrain(train);
    }
}
