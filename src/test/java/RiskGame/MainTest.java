package RiskGame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MainTest {

    @Before
    public void setUp() {
        System.out.println("StartTest");
    }

    @After
    public void tearDown() {
        System.out.println("Test Ended");
    }

    @Test
    public void Main() {
        Scanner userInput = new Scanner(System.in);

        final int minPlayer = 2;    //Minimum number of player
        final int maxPlayer = 6;    //Maximum number of player
        final int maxNumbOfDie = 3; //Maximum number of die each player can have

        //Name of the file contains list of all territories
        final String territoriesDataFileName = "TerritoryList";

        //The path to the Data folder which contains territories list and their adjacent territories
        final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

        //The path to the map image
        final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";

        /*String fileNamePlayerList = "ReplayPlayerList.re";
        String fileNameTerritory = "ReplayTerritory.re";
        String fileNameBattle = "ReplayBattle.re";*/

        List<Player> players = null;   //List contains players

        //List contain territories
        List<Territory> finalTerritories = Main.createTerritories(territoriesDataPath, territoriesDataFileName, userInput);
        List<Territory> availableTerritories = null;
        /*finalTerritories = new ArrayList<>();
        finalTerritories.add(new Territory("Alaska", 0));
        finalTerritories.add(new Territory("Northwest", 1));*/

        assertEquals(finalTerritories.size(), 42);
        //boolean isContinue = false;

        /*File filePlayerList = new File(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator + fileNamePlayerList);
        File fileTerritory = new File(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator + fileNameTerritory);
        File fileBattle = new File(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator + fileNameBattle);
        if (filePlayerList.exists() && fileTerritory.exists() && fileBattle.exists()) isContinue = true;
        else isContinue = false;*/

        /*if (DownloadSave.IsFileExist(fileNamePlayerList) && DownloadSave.IsFileExist(fileNameTerritory) &&
                DownloadSave.IsFileExist(fileNameBattle)) isContinue = true;
        else isContinue = false;

        if (!isContinue) {
            try {
                FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                        "Replay" + File.separator + fileNamePlayerList);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                //bufferedWriter.flush();

                bufferedWriter.close();
            }
            catch(IOException e) {
                System.out.println("Error recording replay.");
            }

            try {
                FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                        "Replay" + File.separator + fileNameTerritory);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                //bufferedWriter.flush();

                bufferedWriter.close();
            }
            catch(IOException e) {
                System.out.println("Error recording replay.");
            }

            try {
                FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                        "Replay" + File.separator + fileNameBattle);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                //bufferedWriter.flush();

                bufferedWriter.close();
            }
            catch(IOException e) {
                System.out.println("Error recording replay.");
            }*/

        //Telegram Bot
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot();
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        assertNotNull(telegramBot);
        telegramBot = null;
        //telegramBot.sendMessage("Hi, let' start the game");

        //Create player objects and store them in players list
        //players = createPlayers(minPlayer, maxPlayer, maxNumbOfDie, userInput);
        players = Main.createPlayers(minPlayer, maxPlayer, maxNumbOfDie, telegramBot);
        //recordPlayerNames(players);

        //Create territory objects and store them in territories list
        availableTerritories = new ArrayList<>();
        availableTerritories.addAll(finalTerritories);
        assertEquals(availableTerritories.size(), 42);

        //Set territory state of the game
        //Allow each player to set up their territories
        //setTerritory(players, availableTerritories, finalTerritories, userInput, mapPath);

        //Test
        players.add(new Player("Ton", 3));
        players.add(new Player("AI", 3));
        players.get(0).setNumOfAvailableArmy(Main.getNumberOfArmyEachPlayer(players.size()));
        players.get(1).setNumOfAvailableArmy(Main.getNumberOfArmyEachPlayer(players.size()));
        for (int i = 1; i <= 42; i++) {
            if (i % 2 != 0) {
                players.get(0).addOwnedTerritory(Main.findTerritory(i, finalTerritories));
                //recordSetTerritory(players.get(0), findTerritory(i, finalTerritories));
            } else {
                players.get(1).addOwnedTerritory(Main.findTerritory(i, finalTerritories));
                //recordSetTerritory(players.get(1), findTerritory(i, finalTerritories));
            }
        }
        for (int i = 1; i <= 28; i++) {
            if (i % 2 != 0) {
                players.get(0).addOwnedTerritory(Main.findTerritory(i, finalTerritories));
                //recordSetTerritory(players.get(0), findTerritory(i, finalTerritories));
            }
            else {
                players.get(1).addOwnedTerritory(Main.findTerritory(i, finalTerritories));
                //recordSetTerritory(players.get(1), findTerritory(i, finalTerritories));
            }
        }
        assertEquals(players.get(0).getOwnedTerritories().size(), 21);
        assertEquals(players.get(1).getOwnedTerritories().size(), 21);
        /*}
        else {
            players = readPlayerName();
            readToTerritory(players, finalTerritories);
            readToTerritoryArmy(players, finalTerritories);
            availableTerritories = new ArrayList<>();
            for (Territory territory : finalTerritories) {
                if (!territory.isOccupied()) availableTerritories.add(territory);
            }
        }
*/

        if (finalTerritories.size() > 0 && players != null)
        {
            if (!Main.checkWinCondition(players)) {
                Main.battleStage(finalTerritories, players, mapPath, telegramBot);

                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setBonusArmies(0);
                    Main.setTerritory(players.get(i), players, availableTerritories, finalTerritories, telegramBot, mapPath);
                }

                /*players.forEach(player -> {
                    player.setBonusArmies(0);
                    setTerritory(player, players, availableTerritories, finalTerritories, userInput, mapPath);
                });*/
            }
            else System.out.println(players.stream().filter(player -> !player.isLost()).findFirst().get().getPlayerName() + " wins.");
        }
        else System.out.println("Error when import territories data.\nExit.");
    }

    @Test
    public void createPlayers() {
        int numOfPlayer = 0;
        List<Player> players = new ArrayList<>();

        //Asking input number of player
        //Number of player must be >= minPlayer
        do {
            System.out.print("How many players: ");
            if (true) {
                try {
                    numOfPlayer = Integer.parseInt("2");
                } catch (NumberFormatException e) {
                    numOfPlayer = 0;
                }
            }
        } while (numOfPlayer < 1 || numOfPlayer > 6);

        int i = 1; //The index of player
        //Asking names of the players
        while(i <= numOfPlayer) {
            System.out.print("What is the name of player " + i + ": ");
            String name;
            if (i == 1) name = "Ton";
            else name = "AI";
            if (true) {
                Player player = new Player(name, 3);
                player.setLastPlayer(new Player(player.getPlayerName(), 3));
                players.add(player);
                i++;
            }
            else System.out.println("Invalid input");
        }
        assertEquals(players.size(), 2);
        assertEquals(players.get(0).getPlayerName(), "Ton");
        assertEquals(players.get(1).getPlayerName(), "AI");
    }

    @Test
    public void createPlayers1() {
        int numOfPlayer = 0;
        List<Player> players = new ArrayList<>();

        //Asking input number of player
        //Number of player must be >= minPlayer
        do {
            System.out.print("How many players: ");
            try {
                numOfPlayer = Integer.parseInt("2");
            } catch (NumberFormatException e) {
                numOfPlayer = 0;
            }
        } while (numOfPlayer < 2 || numOfPlayer > 6);

        int i = 1; //The index of player
        //Asking names of the players
        while(i <= numOfPlayer) {
            System.out.print("What is the name of player " + i + ": ");
            String name = "";
            if (i == 1) name = "Ton";
            else name = "AI";
            Player player = new Player(name, 3);
            player.setLastPlayer(new Player(player.getPlayerName(), 3));
            players.add(player);
            i++;
        }
        assertEquals(players.size(), 2);
        assertEquals(players.get(0).getPlayerName(), "Ton");
        assertEquals(players.get(1).getPlayerName(), "AI");
    }

    @Test
    public void createPlayers2() {
        List<Player> players = new ArrayList<>();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Ton");
        playerNames.add("AI");
        int i = 0; //The index of player
        //Asking names of the players
        while(i < 2) {
            Player player = new Player(playerNames.get(i), 3);
            player.setLastPlayer(new Player(playerNames.get(i), 3));
            players.add(player);
            i++;
        }
        assertEquals(players.size(), 2);
        assertEquals(players.get(0).getPlayerName(), "Ton");
        assertEquals(players.get(1).getPlayerName(), "AI");
    }

    @Test
    public void createTerritories() {
        //Name of the file contains list of all territories
        final String territoriesDataFileName = "TerritoryList";

        //The path to the Data folder which contains territories list and their adjacent territories
        final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

        List<Territory> territories = new ArrayList<>();
        Pair<List<String>, Integer> territoryDuplicatedPair = Main
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        String answer = null; //Store the user input if there are duplications whether if they want to continue

        //Create each territory base on each territory name in the territoryName list
        for (int i = 0; i < territoryDuplicatedPair.getFirst().size(); i++) {
            Territory territory = new Territory(territoryDuplicatedPair.getFirst().get(i), i + 1);
            territory.setLastTerritory(new Territory(territoryDuplicatedPair.getFirst().get(i), i + 1));
            territories.add(territory);
        }

        //Search and open file which contains adjacent territories for each territory in territories list
        for (Territory territory : territories) {
            Pair<List<String>, Integer> listIntegerPair = Main
                    .readTerritoriesData(territoriesDataPath, territory.getTerritoryName(), ".adj");

            //Compare the adjacent territories in the territories list
            //If there is an adjacent territory does not exist in territories list, then output error
            //Otherwise, add it to the adjacent territories list of each territory
            for (String s : listIntegerPair.getFirst()) {
                Territory territoryToAdd = Main.findTerritory(s, territories);
                if (territoryToAdd != null) territory.getAdjTerritories().add(territoryToAdd);
                else {
                    System.out.println("Mismatch between available .Territory and their adjacent territories.");
                }
            }
        }

        assertEquals(territories.size(), 42);
    }

    @Test
    public void getNumberOfArmyEachPlayer() {
        int numbOfPlayer = 2;
        int numOfTroops = 0;

        //case 1
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                numOfTroops = 35;
                break;
            case 3:
                numOfTroops =  35;
                break;
            case 4:
                numOfTroops =  30;
                break;
            case 5:
                numOfTroops =  25;
                break;
            case 6:
                numOfTroops =  20;
                break;
        }
        assertEquals(numOfTroops, 35);

        //case 2
        numbOfPlayer = 3;
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                numOfTroops = 35;
                break;
            case 3:
                numOfTroops =  35;
                break;
            case 4:
                numOfTroops =  30;
                break;
            case 5:
                numOfTroops =  25;
                break;
            case 6:
                numOfTroops =  20;
                break;
        }
        assertEquals(numOfTroops, 35);

        //case 3
        numbOfPlayer = 4;
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                numOfTroops = 35;
                break;
            case 3:
                numOfTroops =  35;
                break;
            case 4:
                numOfTroops =  30;
                break;
            case 5:
                numOfTroops =  25;
                break;
            case 6:
                numOfTroops =  20;
                break;
        }
        assertEquals(numOfTroops, 30);

        //case 4
        numbOfPlayer = 5;
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                numOfTroops = 35;
                break;
            case 3:
                numOfTroops =  35;
                break;
            case 4:
                numOfTroops =  30;
                break;
            case 5:
                numOfTroops =  25;
                break;
            case 6:
                numOfTroops =  20;
                break;
        }
        assertEquals(numOfTroops, 25);

        //case 5
        numbOfPlayer = 6;
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                numOfTroops = 35;
                break;
            case 3:
                numOfTroops =  35;
                break;
            case 4:
                numOfTroops =  30;
                break;
            case 5:
                numOfTroops =  25;
                break;
            case 6:
                numOfTroops =  20;
                break;
        }
        assertEquals(numOfTroops, 20);
    }

    @Test
    public void userInputRequest() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        List<Player> players = new ArrayList<>();
        List<Territory> territories = new ArrayList<>();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "-la";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
        }
        assertEquals(command, true);

        //Case 2
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "-lm";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
        }
        assertEquals(command, true);

        //Case 3
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "-lav";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
        }
        assertEquals(command, true);

        //Case 4
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "-map";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
        }
        assertEquals(command, true);

        //Case 5
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "-shde Alaska";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
            assertEquals(input, "Alaska");
        }
        assertEquals(command, true);

        //Case 6
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "1";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
                assertEquals(tIndex, 1);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
            }
        }
        assertEquals(command, false);

        //Case 7
        //The questions program will ask each player each move
        //Need refactor
        System.out.println("======================================================================");
        //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
        System.out.println("");
        System.out.println("Enter -la to list all territories of all player and available territories.");
        System.out.println("Enter -lm to list all territories of your possession.");
        System.out.println("Enter -lav to list all available territories.");
        System.out.println("Enter -map to the map.");
        System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                " to list detail about that territory and its adjacent territories.");
        System.out.print("Enter .Territory name, or index, or command: ");

        players.clear();
        territories.clear();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        //Execute special command
        if (true) {
            command = false;
            String input = "Alaska";

            switch (input) {
                case "-la":
                    Main.printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    Main.printTerritory(players.get(0), players, territories);
                    System.out.println(players.get(0).getPlayerName() + " has: " + players.get(0).getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    Main.printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    Main.displayMap("");
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                try {
                    if (!Main.printTerritory(input, territories) && !Main.printTerritory(Integer.parseInt(input), territories))
                        System.out.println(".Territory not found.");
                } catch (NumberFormatException e) {
                    System.out.println(".Territory not found.");
                }
                command = true;
            }

            //Try to parse the input string to a integer
            //If the string can be parse successfully, then the player entered a territory index instead of territory name
            try {
                tIndex = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                //If the input string cannot be parse, then the player entered a territory name
                tName = input;
                assertEquals(tName, "Alaska");
            }
        }
        assertEquals(command, false);
    }

    @Test
    public void askForUndo() {
        boolean result = false;

        //case 1
        String input = "";
        while (!input.toLowerCase().equals("n") || !input.toLowerCase().equals("y")) {
            System.out.print("Do you want to undo (Y/N): ");
            if (true) {
                input = "n";
                if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                    System.out.println("You have to answer either Y or N.");
                    continue;
                } else if (input.toLowerCase().equals("n")) {
                    result = false;
                    break;
                }
                else if (input.toLowerCase().equals("y")) {
                    result = true;
                    break;
                }
            }
        }
        assertEquals(result, false);

        //case 2
        while (!input.toLowerCase().equals("n") || !input.toLowerCase().equals("y")) {
            System.out.print("Do you want to undo (Y/N): ");
            if (true) {
                input = "y";
                if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                    System.out.println("You have to answer either Y or N.");
                    continue;
                } else if (input.toLowerCase().equals("n")) {
                    result = false;
                    break;
                }
                else if (input.toLowerCase().equals("y")) {
                    result = true;
                    break;
                }
            }
        }
        assertEquals(result, true);
    }

    @Test
    public void askForUndo1() {
    }

    @Test
    public void undo() {
    }

    @Test
    public void saveState() {
    }

    @Test
    public void setTerritory() {
        Scanner userInput = null;
        List<Player> players = new ArrayList<>();
        players.add(new Player("Ton", 3));
        players.add(new Player("AI", 3));
        final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";
        final String territoriesDataFileName = "TerritoryList";
        final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;
        List<Territory> finalTerritories = Main.createTerritories(territoriesDataPath, territoriesDataFileName, userInput);
        List<Territory> availableTerritories = new ArrayList<>();
        availableTerritories.addAll(finalTerritories);

        //Assign the number of army each player will have base on the number of player
        int armiesEachPlayer = Main.getNumberOfArmyEachPlayer(players.size());
        assertEquals(armiesEachPlayer, 35);
        if (armiesEachPlayer <= 0) return;
        players.forEach(player -> player.setNumOfAvailableArmy(armiesEachPlayer));
        assertEquals(players.get(0).getNumOfAvailableArmy(), 35);
        assertEquals(players.get(1).getNumOfAvailableArmy(), 35);

        //Set territory stage
        for (int i = 0; i < armiesEachPlayer; i++) {
            for (int j = 0; j < players.size(); j++) {
                Territory foundTerritory = null;

                //The loop will continue to ask what move the player wants
                //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                if (foundTerritory == null) {
                    String addOutput = "It's " + players.get(j).getPlayerName() + "'s turn to place armies.";
                    Pair<String, Integer> stringIntegerPair = Main.userInputRequest(availableTerritories, finalTerritories,
                            players, players.get(j), mapPath, addOutput, userInput);
                    String tName = stringIntegerPair.getFirst();
                    int tIndex = stringIntegerPair.getSecond();
                    //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                    //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                    //Then, that territory need to be removed from the available availableTerritories list
                    if (availableTerritories.size() > 0) {
                        foundTerritory = Main.findTerritory(tName, tIndex, availableTerritories);

                        //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                        //Otherwise, output error and ask that player again
                        if (foundTerritory != null) {
                            players.get(j).addOwnedTerritory(foundTerritory);
                            availableTerritories.remove(foundTerritory);
                            if (Main.askForUndo(userInput)) {
                                Main.undo(players.get(j), foundTerritory);
                                availableTerritories.add(foundTerritory);
                                j--;
                                i--;
                            }
                        } else System.out.println(".Territory not found");
                    } else {
                        //Occurred when there is no available territory but there are armies left that have not set
                        //At this time, each player can place their armies any where within their owned availableTerritories

                        if (tName != null) {
                            //String territoryName = tName;
                            //Find territory in the player's owned availableTerritories list by its name, then store in the foundTerritory variable
                            foundTerritory = Main.findTerritory(tName, players.get(j).getOwnedTerritories());
                        } else {
                            //int territoryIndex = tIndex;
                            //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
                            foundTerritory = Main.findTerritory(tIndex, players.get(j).getOwnedTerritories());
                        }

                        //If territory is found, then call the function addOwnedTerritory from player object
                        //If the territory is already owned, then just increase its number of army
                        //Otherwise, output error
                        if (foundTerritory != null) {
                            Main.saveState(players.get(j), foundTerritory);
                            players.get(j).addOwnedTerritory(foundTerritory);
                            if (Main.askForUndo(userInput)) {
                                Main.undo(players.get(j), foundTerritory);
                                j--;
                                i--;
                            }
                        }
                        else if (tName != null) System.out.println(".Player " + players.get(j).getPlayerName() + " does not own " + tName);
                        else if (tIndex >= 0) System.out.println(".Player " + players.get(j).getPlayerName() + " does not own territory has index of " + tIndex);
                    }
                }
            }
        }
    }

    @Test
    public void setTerritory1() {
    }

    @Test
    public void setTerritory2() {
    }

    @Test
    public void setTerritory3() {
    }

    @Test
    public void battleStage() {
    }

    @Test
    public void battleStage1() {
    }

    @Test
    public void play() {
    }

    @Test
    public void play1() {
    }

    @Test
    public void checkWinCondition() {
    }

    @Test
    public void printTerritory() {
    }

    @Test
    public void printTerritory1() {
    }

    @Test
    public void printTerritory2() {
    }

    @Test
    public void printTerritory3() {
    }

    @Test
    public void printTerritory4() {
    }

    @Test
    public void printTerritory5() {
    }

    @Test
    public void printTerritory6() {
    }

    @Test
    public void printTerritory7() {
    }

    @Test
    public void printTerritory8() {
    }

    @Test
    public void printTerritory9() {
    }

    @Test
    public void findTerritory() {
    }

    @Test
    public void findTerritory1() {
    }

    @Test
    public void findTerritory2() {
    }
}