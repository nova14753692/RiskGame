package RiskGame;

public class Timer extends Thread {

    private int time;
    private final int timeOut = 5;

    public Timer() {
        time = 0;
    }

    private boolean startTimer() {
        while (time < timeOut) {
            try {
                sleep(1000);
                time++;
            } catch (InterruptedException e) { return false;}
        }
        return true;
    }

    public void run() {
        startTimer();
    }

    public void resetTime() { time = 0; }

    public boolean isTimeOut() {
        return time >= timeOut;
    }
}
