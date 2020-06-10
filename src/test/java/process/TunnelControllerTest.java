package process;

import classes.Train;
import classes.Tunnel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class TunnelControllerTest {

    public Tunnel tunnel1;
    public Train train1;
    public String tunnelInString;

    @Before
    public void setUp(){
        tunnel1 = new Tunnel("FIRST");
        train1 = new Train(TunnelController.getInstance(), true, 235 );
        tunnelInString = "FIRST Tunnel";
    }


    @Test
    public void approachTunnel() {
        tunnel1.setCurrentDirection(new AtomicBoolean(true));
        tunnel1.getTrains().addLast(train1);
        Assert.assertEquals(tunnel1, TunnelController.getInstance().approachTunnel(train1));
    }


}