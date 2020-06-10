package classes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import process.TunnelController;

public class TrainTest {

    public Train train1;
    public Train train2;
    public String train1InString;

    @Before
    public void setUp() throws Exception {
        train1 = new Train(TunnelController.getInstance(), true, 235 );
        train2 = new Train(TunnelController.getInstance(), false, 415);
        train1InString = "Train №235, direction true";

    }

    @Test
    public void getNumber1Test(){
        Assert.assertEquals(235, train1.getNumber());
    }


    @Test
    public void toStringTest() {
        Assert.assertEquals(train1InString, train1.toString());
    }


}