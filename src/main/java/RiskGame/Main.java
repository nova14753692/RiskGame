package RiskGame;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    /**
        <p style="color:blue;">Execute other functions and is called when the program runs</p>
     * @param args arguments to use as parameters when user input console command when run the program
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot();
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        GameEngine gameEngine = new GameEngine();
        gameEngine.startGame(telegramBot);
    }
}