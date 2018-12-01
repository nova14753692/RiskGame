package RiskGame;

import org.junit.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.Assert.*;

public class TimerTest {

    Timer time = new Timer(5, null);


    private TelegramBot getBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot(true);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return telegramBot;
    }

    @Test
    public void run() {
        time.run();

        Timer timeBot = new Timer(5, getBot());
        timeBot.run();
    }

    @Test
    public void resetTime() {
        time.resetTime();
        assertEquals(time.getTime(), 5);
    }

    @Test
    public void isTimeOut() {
        assertFalse(time.isTimeOut());
    }

    @Test
    public void getTime() {
        assertEquals(time.getTime(), 5);
    }

    @Test
    public void getTimeOut() {
        assertEquals(time.getTimeOut(), 5);
    }
}