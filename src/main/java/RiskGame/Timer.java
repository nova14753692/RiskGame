package RiskGame;

public class Timer extends Thread {

    private int time;
    private int timeOut = 5;
    private volatile boolean isStop;
    private TelegramBot bot;

    public Timer(int timeOut, TelegramBot bot) {
        this.timeOut = timeOut;
        time = timeOut;
        isStop = false;
        this.bot = bot;
    }

    public void run() {
        resetTime();
        isStop = false;
        while (!isStop && time > 0) {
            try {
                if (bot != null && time <= 5) bot.sendMessage(Integer.toString(time));
                sleep(1000);
                time--;
            } catch (InterruptedException e) {

            }
            yield();
        }
    }

    public void resetTime() {
        time = timeOut;
        isStop = true;
        this.stop();
    }

    public boolean isTimeOut() {
        return time <= 0;
    }

    public int getTime() {
        return time;
    }

    public int getTimeOut() {
        return timeOut;
    }
}
