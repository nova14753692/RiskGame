package RiskGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class TelegramBotTest {

    TelegramBot bot = new TelegramBot();

    @Test
    public void getBotUsername() {
        assertEquals(bot.getBotUsername(), "RiskGameTelegramBot");
    }

    @Test
    public void getBotToken() {
        assertEquals(bot.getBotToken(), "618605946:AAHc7t5UccV34-V0PLNwio4HbZamCX0OxwQ");
    }

    @Test
    public void getMessage() {
        assertNull(bot.getMessage());
    }

    @Test
    public void clearMessage() {
        bot.setMessage("message");
        bot.clearMessage();
        assertNull(bot.getMessage());
    }

    @Test
    public void waitForInput() {
        bot.setMessage("message");
        assertTrue(bot.waitForInput());
    }

    @Test
    public void setMessage() {
        bot.setMessage("message");
        assertEquals(bot.getMessage(), "message");
    }

    @Test
    public void waitForConnection() {
        bot.waitForConnection();
    }

    @Test
    public void sendMessage() {
        bot.sendMessage("msg");
    }
}