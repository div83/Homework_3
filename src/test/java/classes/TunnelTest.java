package classes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import process.TunnelController;

import java.util.ArrayDeque;
import java.util.Deque;

public class TunnelTest {

    public Tunnel tunnel;
    public Train train1;
    public Train train2;
    public String tunnelInString;

    @Before
    public void setUp(){
        tunnel = new Tunnel("FIRST");
        train1 = new Train(TunnelController.getInstance(), true, 235 );
        train2 = new Train(TunnelController.getInstance(), false, 415);
        tunnelInString = "FIRST Tunnel";
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("FIRST", tunnel.getName());
    }


    @Test
    public void addTrainTest() {
        Deque<Train> list = new ArrayDeque<Train>();
        list.addLast(train1);
        tunnel.addTrain(train1);
        Assert.assertEquals(list, tunnel.getTrains());
    }

    @Test
    public void popTrainTest() {
        Deque<Train> list = new ArrayDeque<Train>();
        list.addLast(train1);
        list.addLast(train2);
        tunnel.setTrains(list);
        list.pop();
        tunnel.popTrain();
        Assert.assertEquals(list, tunnel.getTrains());
    }


    @Test
    public void toStringTest() {
        Assert.assertEquals(tunnelInString, tunnel.toString());
    }
}