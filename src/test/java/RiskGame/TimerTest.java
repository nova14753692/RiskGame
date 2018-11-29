package RiskGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimerTest {

    Timer time = new Timer(0, null);

    @Test
    public void run() {
        time.start();
    }

    @Test
    public void resetTime() {
        time.resetTime();
        assertEquals(time.getTime(), 0);
    }

    @Test
    public void isTimeOut() {
        assertTrue(time.isTimeOut());
    }

    @Test
    public void getTime() {
        assertEquals(time.getTime(), 0);
    }

    @Test
    public void getTimeOut() {
        assertEquals(time.getTimeOut(), 0);
    }
}