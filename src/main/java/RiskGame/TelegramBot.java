package RiskGame;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    private String message = null;
    private long chatID = 0;

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = update.getMessage().getText();
        }
        chatID = update.getMessage().getChatId();
    }

    @Override
    public String getBotUsername() {
        return "RiskGameTelegramBot";
    }

    @Override
    public String getBotToken() {
        return "618605946:AAHc7t5UccV34-V0PLNwio4HbZamCX0OxwQ";
    }

    public String getMessage() {
        return message;
    }

    public void sendMessage(String messageToSend) {
        if (waitForConnection()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(chatID)
                    .setText(messageToSend);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearMessage() {
        message = null;
    }

    private boolean waitForConnection() {

        while (chatID == 0)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupted connection.");
                return false;
            }
        }
        return true;
    }

    public boolean waitForInput() {
        boolean timerOut = false;
        Timer timer = new Timer(30, this);
        timer.start();
        while (getMessage() == null) {
            try {
                if (timer.isTimeOut()) {
                    timerOut = true;
                    break;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupted connection.");
                return false;
            }
        }
        timer.resetTime();
        return !timerOut;
    }

    public void setMessage(String message) { this.message = message; }
}
