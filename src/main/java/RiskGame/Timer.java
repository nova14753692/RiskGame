package RiskGame;

public class Timer extends Thread {

    private int time;
    private int timeOut = 5;

    public Timer(int timeOut) {
        time = 0;
        this.timeOut = timeOut;
    }

    public boolean startTimer() {
        while (time < timeOut) {
            try {
                sleep(1000);
                time++;
            } catch (InterruptedException e) { return false;}
        }
        return true;
    }

    public void resetTime() { time = 0; }

    public boolean isTimeOut() {
        return time >= timeOut;
    }

    public int getTime() {
        return time;
    }

    public int getTimeOut() {
        return timeOut;
    }
}
