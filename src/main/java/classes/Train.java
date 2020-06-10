package classes;

import org.apache.log4j.Logger;
import process.TunnelController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Train extends Thread{
    private int number;
    private boolean direction;
    private TunnelController tunnelController;

    private final static int TIME_IN_TUNNEL_IN_SECONDS = 3;
    private static final Logger LOGGER = Logger.getLogger(Train.class);

    public Train() {}

    public Train(TunnelController tunnelController, boolean direction, int number) {
        this.tunnelController = tunnelController;
        this.direction = direction;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean getDirection() {
        return direction;
    }

    public TunnelController getTunnelController() {
        return tunnelController;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setTunnelController(TunnelController tunnelController) {
        this.tunnelController = tunnelController;
    }

    @Override
    public void run() {
        Tunnel tunnel = new Tunnel();
        try {
            tunnel = tunnelController.approachTunnel(this);
            LOGGER.info(toString() + " entered the " + tunnel);
            TimeUnit.SECONDS.sleep(TIME_IN_TUNNEL_IN_SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            tunnelController.leaveTunnel(tunnel);
            LOGGER.info(toString() + " left the " + tunnel);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;
        Train train = (Train) o;
        return getNumber() == train.getNumber() &&
                getDirection() == train.getDirection() &&
                Objects.equals(getTunnelController(), train.getTunnelController());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDirection(), getTunnelController());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " №" + number +
                ", direction " + direction;
    }
}
