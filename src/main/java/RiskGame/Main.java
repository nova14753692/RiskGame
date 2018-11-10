package RiskGame;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    /**
        <p style="color:blue;">Execute other functions and is called when the program runs</p>
     * @param args arguments to use as parameters when user input console command when run the program
     */
    public static void main(String[] args) {

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
        List<Territory> finalTerritories = createTerritories(territoriesDataPath, territoriesDataFileName, userInput);
        List<Territory> availableTerritories = null;
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
        /*ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot();
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        telegramBot.sendMessage("Hi, let' start the game");*/

            //Create player objects and store them in players list
            players = createPlayers(minPlayer, maxPlayer, maxNumbOfDie, userInput);
            //players = createPlayers(minPlayer, maxPlayer, maxNumbOfDie, telegramBot);
            //recordPlayerNames(players);

            //Create territory objects and store them in territories list
            availableTerritories = new ArrayList<>();
            availableTerritories.addAll(finalTerritories);

            //Set territory state of the game
            //Allow each player to set up their territories
            //setTerritory(players, availableTerritories, finalTerritories, userInput, mapPath);

            //Test
            players.get(0).setNumOfAvailableArmy(getNumberOfArmyEachPlayer(players.size()));
            players.get(1).setNumOfAvailableArmy(getNumberOfArmyEachPlayer(players.size()));
            for (int i = 1; i <= 42; i++) {
                if (i % 2 != 0) {
                    players.get(0).addOwnedTerritory(findTerritory(i, finalTerritories));
                    //recordSetTerritory(players.get(0), findTerritory(i, finalTerritories));
                } else {
                    players.get(1).addOwnedTerritory(findTerritory(i, finalTerritories));
                    //recordSetTerritory(players.get(1), findTerritory(i, finalTerritories));
                }
            }
            for (int i = 1; i <= 28; i++) {
                if (i % 2 != 0) {
                    players.get(0).addOwnedTerritory(findTerritory(i, finalTerritories));
                    //recordSetTerritory(players.get(0), findTerritory(i, finalTerritories));
                }
                else {
                    players.get(1).addOwnedTerritory(findTerritory(i, finalTerritories));
                    //recordSetTerritory(players.get(1), findTerritory(i, finalTerritories));
                }
            }
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
            if (!checkWinCondition(players)) {
                //battleStage(finalTerritories, players, mapPath, telegramBot);
                battleStage(finalTerritories, players, mapPath, userInput);

                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setBonusArmies(0);
                    //setTerritory(players.get(i), players, availableTerritories, finalTerritories, telegramBot, mapPath);
                    setTerritory(players.get(i), players, availableTerritories, finalTerritories, userInput, mapPath);
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

    /**
     * <p style="color:blue;">Return the list of every player</p>
     * @param userInput Variable that accepts user input
     * @param maxNumbOfDie The maximum number of die the player can have
     * @return The list of every player
     */
    public static List<Player> createPlayers(int minPlayer, int maxPlayer, int maxNumbOfDie, Scanner userInput) {
        int numOfPlayer = 0;
        List<Player> players = new ArrayList<>();

        if (userInput != null) {
            //Asking input number of player
            //Number of player must be >= minPlayer
            do {
                System.out.print("How many players: ");
                if (userInput.hasNextLine()) {
                    try {
                        numOfPlayer = Integer.parseInt(userInput.nextLine());
                    } catch (NumberFormatException e) {
                        numOfPlayer = 0;
                    }
                }
            } while (numOfPlayer < minPlayer || numOfPlayer > maxPlayer);

            int i = 1; //The index of player
            //Asking names of the players
            while (i <= numOfPlayer) {
                System.out.print("What is the name of player " + i + ": ");
                if (userInput.hasNextLine()) {
                    Player player = new Player(userInput.nextLine(), maxNumbOfDie);
                    player.setLastPlayer(new Player(player.getPlayerName(), maxNumbOfDie));
                    players.add(player);
                    i++;
                } else System.out.println("Invalid input");
            }
        }
        return players;
    }

    public static List<Player> createPlayers(int minPlayer, int maxPlayer, int maxNumbOfDie, TelegramBot bot) {
        int numOfPlayer = 0;
        List<Player> players = new ArrayList<>();
        if (bot != null) {
            //Asking input number of player
            //Number of player must be >= minPlayer
            do {
                bot.clearMessage();
                bot.sendMessage("How many players: ");
                while (bot.getMessage() == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                if (bot.getMessage() != null) {
                    try {
                        numOfPlayer = Integer.parseInt(bot.getMessage());
                    } catch (NumberFormatException e) {
                        numOfPlayer = 0;
                    }
                }
            } while (numOfPlayer < minPlayer || numOfPlayer > maxPlayer);

            int i = 1; //The index of player
            //Asking names of the players
            while (i <= numOfPlayer) {
                bot.clearMessage();
                bot.sendMessage("What is the name of player " + i + ": ");
                while (bot.getMessage() == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                if (bot.getMessage() != null) {
                    Player player = new Player(bot.getMessage(), maxNumbOfDie);
                    player.setLastPlayer(new Player(player.getPlayerName(), maxNumbOfDie));
                    players.add(player);
                    i++;
                } else bot.sendMessage("Invalid input");
            }
        }
        return players;
    }

    //For unit test
    public static List<Player> createPlayers(int minPlayer, int maxPlayer, int maxNumbOfDie, int numOfPlayer, List<String> playerNames) {
        List<Player> players = new ArrayList<>();

        int i = 0; //The index of player
        //Asking names of the players
        while(i < numOfPlayer) {
            Player player = new Player(playerNames.get(i), maxNumbOfDie);
            player.setLastPlayer(new Player(playerNames.get(i), maxNumbOfDie));
            players.add(player);
            i++;
        }
        return players;
    }

    /**
     * <p style="color:blue;">Return the pair of the list of all territory name the game has just read, and the indicate number</p>
     * <p style="color:blue;">if the indicate number is 1 means there are duplications in the data, if it's 0 means there is no duplication</p>
     * @param filePath The path to the territories data folder
     * @param fileName The of the territory data file
     * @param fileExtension The extension of the territory data file
     * @return The pair of the list of all territory name the game has just read, and the indicate number
     */
    public static Pair<List<String>, Integer> readTerritoriesData(String filePath, String fileName, String fileExtension) {
        String line;
        List<String> territoryNames = new ArrayList<>();

        //Read territory data file to get the list of all territory
        try {
            FileReader fileReader = new FileReader(filePath + fileName + fileExtension);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Store territory names to a list
            while((line = bufferedReader.readLine()) != null) { territoryNames.add(line); }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return null;
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            return null;
        }

        //Create another list to filter territoryName to delete all duplications
        List<String> territoryNamesWithoutDup = territoryNames.stream().distinct().collect(Collectors.toList());
        Integer isDuplicated = 0;
        if (territoryNamesWithoutDup.size() < territoryNames.size()) isDuplicated = 1;
        return new Pair<List<String>, Integer>(territoryNamesWithoutDup, isDuplicated);
    }

    /**
     * <p style="color:blue;">Return the list of every territory allowed in the game</p>
     * @param filePath The path to the folder contains territory data
     * @param fileName The territory list file name
     * @param userInput Variable accepts user input
     * @return The list of every territory allowed in the game
     */
    public static  List<Territory> createTerritories(String filePath, String fileName,  Scanner userInput) {
        List<Territory> territories = new ArrayList<>();
        if (userInput != null) {
            Pair<List<String>, Integer> territoryDuplicatedPair = readTerritoriesData(filePath, fileName, ".list");
            if (territoryDuplicatedPair == null) return null;

            String answer = null; //Store the user input if there are duplications whether if they want to continue

            //If the territory name list without duplications is less in size then territory names list
            //Means the territoryDuplicatedPair returns 1 as the second value
            //It means that there are duplications
            //If user answers no, then quit the program
            if (territoryDuplicatedPair.getSecond() == 1) {
                System.out.println("There are duplications in TerritoryData!");
                while (answer != "Yes" && answer != "No") {
                    System.out.print("Match territory list and continue? (Yes/No): ");
                    if (userInput.hasNextLine()) answer = userInput.nextLine();
                }
                if (answer == "Yes") {
                    System.out.println(".Territory list is updated and without duplication.");
                } else return null;
            }

            //Create each territory base on each territory name in the territoryName list
            for (int i = 0; i < territoryDuplicatedPair.getFirst().size(); i++) {
                Territory territory = new Territory(territoryDuplicatedPair.getFirst().get(i), i + 1);
                territory.setLastTerritory(new Territory(territoryDuplicatedPair.getFirst().get(i), i + 1));
                territories.add(territory);
            }

            //Search and open file which contains adjacent territories for each territory in territories list
            for (Territory territory : territories) {
                Pair<List<String>, Integer> listIntegerPair = readTerritoriesData(filePath, territory.getTerritoryName(), ".adj");

                //Compare the adjacent territories in the territories list
                //If there is an adjacent territory does not exist in territories list, then output error
                //Otherwise, add it to the adjacent territories list of each territory
                for (String s : listIntegerPair.getFirst()) {
                    Territory territoryToAdd = findTerritory(s, territories);
                    if (territoryToAdd != null) territory.getAdjTerritories().add(territoryToAdd);
                    else {
                        System.out.println("Mismatch between available .Territory and their adjacent territories.");
                        return null;
                    }
                }
            }
        }

        return territories;
    }

    /**
     * <p style="color:blue;">Return number of army each player can have</p>
     * @param numbOfPlayer The total number of player
     * @return Number of army each player can have
     */
    public static int getNumberOfArmyEachPlayer(int numbOfPlayer) {
        switch(numbOfPlayer) {
            //40-((n-2)*5)
            case 2:
                return 35;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
        }
        return -1;
    }

    /**
     * <p style="color:blue;">Return the pair of string territory name and int territory index</p>
     * @param territories The list contains all the available territories
     * @param finalTerritories The list contains all the territories supported in the game
     * @param players The list contains all player
     * @param player The player the game need to ask for input
     * @param mapPath The path to the map image
     * @param userInput The object to retrieve the user input
     * @return The pair of string territory name and int territory index
     */
    public static Pair<String, Integer> userInputRequest(List<Territory> territories, List<Territory> finalTerritories,
                                                         List<Player> players, Player player, String mapPath, String addOutput, Scanner userInput) {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        if (userInput != null) {
            while (command) {
                //The questions program will ask each player each move
                //Need refactor
                System.out.println("======================================================================");
                //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
                System.out.println(addOutput);
                System.out.println("Enter -la to list all territories of all player and available territories.");
                System.out.println("Enter -lm to list all territories of your possession.");
                System.out.println("Enter -lav to list all available territories.");
                System.out.println("Enter -map to the map.");
                System.out.println("Enter -shde [.Territory name] or -shde [.Territory index] (eg: -shde Alaska or -shde 1)\n" +
                        " to list detail about that territory and its adjacent territories.");
                System.out.print("Enter .Territory name, or index, or command: ");

                //Execute special command
                if (userInput.hasNextLine()) {
                    command = false;
                    String input = userInput.nextLine();

                    switch (input) {
                        case "-la":
                            printTerritory(players, territories);
                            command = true;
                            break;
                        case "-lm":
                            printTerritory(player, players, territories);
                            System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                            command = true;
                            break;
                        case "-lav":
                            printTerritory(players, territories, true);
                            command = true;
                            break;
                        case "-map":
                            displayMap(mapPath);
                            command = true;
                            break;
                    }
                    if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                        input = input.substring(6);
                        try {
                            if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                                System.out.println(".Territory not found.");
                        } catch (NumberFormatException e) {
                            System.out.println(".Territory not found.");
                        }
                        command = true;
                    }
                    if (command) continue;

                    //Try to parse the input string to a integer
                    //If the string can be parse successfully, then the player entered a territory index instead of territory name
                    try {
                        tIndex = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        //If the input string cannot be parse, then the player entered a territory name
                        tName = input;
                    }
                    command = false;
                }
            }
        }
        return new Pair<String, Integer>(tName, tIndex);
    }

    public static Pair<String, Integer> userInputRequest(List<Territory> territories, List<Territory> finalTerritories,
                                                         List<Player> players, Player player, String mapPath, String addOutput, TelegramBot bot) {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        if (bot != null) {
            while (command) {
                //The questions program will ask each player each move
                //Need refactor
                //System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
                bot.sendMessage(addOutput);
                bot.sendMessage("Enter -la to list all territories of all player and available territories.");
                bot.sendMessage("Enter -lm to list all territories of your possession.");
                bot.sendMessage("Enter -lav to list all available territories.");
                bot.sendMessage("Enter -map to the map.");
                bot.sendMessage("Enter -shde [Territory name] or -shde [Territory index] (eg: -shde Alaska or -shde 1)\n" +
                        " to list detail about that territory and its adjacent territories.");
                bot.sendMessage("Enter Territory name, or index, or command: ");

                //Execute special command
                bot.clearMessage();
                while (bot.getMessage() == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                if (bot.getMessage() != null) {
                    command = false;
                    String input = bot.getMessage();

                    switch (input) {
                        case "-la":
                            printTerritory(players, territories, bot);
                            command = true;
                            break;
                        case "-lm":
                            printTerritory(player, players, territories, bot);
                            bot.sendMessage(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                            command = true;
                            break;
                        case "-lav":
                            printTerritory(players, territories, true, bot);
                            command = true;
                            break;
                        case "-map":
                            displayMap(mapPath);
                            command = true;
                            break;
                    }
                    if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                        input = input.substring(6);
                        try {
                            if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                                bot.sendMessage(".Territory not found.");
                        } catch (NumberFormatException e) {
                            bot.sendMessage(".Territory not found.");
                        }
                        command = true;
                    }
                    if (command) continue;

                    //Try to parse the input string to a integer
                    //If the string can be parse successfully, then the player entered a territory index instead of territory name
                    try {
                        tIndex = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        //If the input string cannot be parse, then the player entered a territory name
                        tName = input;
                    }
                    command = false;
                    bot.clearMessage();
                }
            }
        }
        return new Pair<String, Integer>(tName, tIndex);
    }

    public static boolean askForUndo(Scanner userInput) {
        if (userInput != null) {
            String input = "";
            while (!input.toLowerCase().equals("n") || !input.toLowerCase().equals("y")) {
                System.out.print("Do you want to undo (Y/N): ");
                if (userInput.hasNextLine()) {
                    input = userInput.nextLine();
                    if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                        System.out.println("You have to answer either Y or N.");
                        continue;
                    } else if (input.toLowerCase().equals("n")) return false;
                    else if (input.toLowerCase().equals("y")) return true;
                }
            }
        }
        return false;
    }

    public static boolean askForUndo(TelegramBot bot) {
        if (bot != null) {
            String input = "";
            bot.clearMessage();
            while (!input.toLowerCase().equals("n") || !input.toLowerCase().equals("y")) {
                bot.sendMessage("Do you want to undo (Y/N): ");
                while (bot.getMessage() == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                if (bot.getMessage() != null) {
                    input = bot.getMessage();
                    if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                        bot.sendMessage("You have to answer either Y or N.");
                        continue;
                    } else if (input.toLowerCase().equals("n")) return false;
                    else if (input.toLowerCase().equals("y")) return true;
                }
                bot.clearMessage();
            }
        }
        return false;
    }

    public static void undo(Player player, Territory territory) {
        player.Revoke();
        territory.Revoke();
    }

    public static void saveState(Player player, Territory territory) {
        player.saveState();
        territory.saveState();
    }

    /**
     * <p style="color:blue;">Each player take turn to set up their availableTerritories</p>
     * @param players The player list
     * @param availableTerritories The territory list
     * @param userInput Variable accepts user input
     * @param mapPath The path to the map image
     */
    public static void setTerritory(List<Player> players,  List<Territory> availableTerritories,
                                     List<Territory> finalTerritories,  Scanner userInput, String mapPath) {
        if (userInput != null) {
            //Assign the number of army each player will have base on the number of player
            int armiesEachPlayer = getNumberOfArmyEachPlayer(players.size());
            if (armiesEachPlayer <= 0) return;
            players.forEach(player -> player.setNumOfAvailableArmy(armiesEachPlayer));

            //Set territory stage
            for (int i = 0; i < armiesEachPlayer; i++) {
                for (int j = 0; j < players.size(); j++) {
                    Territory foundTerritory = null;

                    //The loop will continue to ask what move the player wants
                    //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                    while (foundTerritory == null) {
                        String addOutput = "It's " + players.get(j).getPlayerName() + "'s turn to place armies.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(availableTerritories, finalTerritories,
                                players, players.get(j), mapPath, addOutput, userInput);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                        //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                        //Then, that territory need to be removed from the available availableTerritories list
                        if (availableTerritories.size() > 0) {
                            foundTerritory = findTerritory(tName, tIndex, availableTerritories);

                            //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                            //Otherwise, output error and ask that player again
                            if (foundTerritory != null) {
                                players.get(j).addOwnedTerritory(foundTerritory);
                                availableTerritories.remove(foundTerritory);
                                if (askForUndo(userInput)) {
                                    undo(players.get(j), foundTerritory);
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
                                foundTerritory = findTerritory(tName, players.get(j).getOwnedTerritories());
                            } else {
                                //int territoryIndex = tIndex;
                                //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
                                foundTerritory = findTerritory(tIndex, players.get(j).getOwnedTerritories());
                            }

                            //If territory is found, then call the function addOwnedTerritory from player object
                            //If the territory is already owned, then just increase its number of army
                            //Otherwise, output error
                            if (foundTerritory != null) {
                                saveState(players.get(j), foundTerritory);
                                players.get(j).addOwnedTerritory(foundTerritory);
                                if (askForUndo(userInput)) {
                                    undo(players.get(j), foundTerritory);
                                    j--;
                                    i--;
                                }
                            } else if (tName != null)
                                System.out.println(".Player " + players.get(j).getPlayerName() + " does not own " + tName);
                            else
                                System.out.println(".Player " + players.get(j).getPlayerName() + " does not own territory has index of " + tIndex);
                        }
                    }
                }
            }
        }
    }

    /**
     * <p style="color:blue;">Each player take turn to set up their availableTerritories</p>
     * @param player The player wish to set territories
     * @param players The player list
     * @param availableTerritories The territory list
     * @param userInput Variable accepts user input
     * @param mapPath The path to the map image
     */
    public static void setTerritory(Player player,  List<Player> players,  List<Territory> availableTerritories,
                                     List<Territory> finalTerritories,  Scanner userInput, String mapPath) {
        if (userInput != null) {
            //Set territory stage
            for (int i = 0; i < player.getNumOfAvailableArmy(); i++) {
                Territory foundTerritory = null;

                //The loop will continue to ask what move the player wants
                //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                while (foundTerritory == null) {
                    String addOutput = "It's " + player.getPlayerName() + "'s turn to place armies.";
                    Pair<String, Integer> stringIntegerPair = userInputRequest(availableTerritories, finalTerritories,
                            players, player, mapPath, addOutput, userInput);
                    String tName = stringIntegerPair.getFirst();
                    int tIndex = stringIntegerPair.getSecond();
                    //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                    //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                    //Then, that territory need to be removed from the available availableTerritories list
                    if (availableTerritories.size() > 0) {
                        foundTerritory = findTerritory(tName, tIndex, availableTerritories);

                        //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                        //Otherwise, output error and ask that player again
                        if (foundTerritory != null) {
                            player.addOwnedTerritory(foundTerritory);
                            availableTerritories.remove(foundTerritory);
                            if (askForUndo(userInput)) {
                                undo(player, foundTerritory);
                                availableTerritories.add(foundTerritory);
                                i--;
                            }
                        } else System.out.println("Territory not found");
                    } else {
                        //Occurred when there is no available territory but there are armies left that have not set
                        //At this time, each player can place their armies any where within their owned availableTerritories

                        if (tName != null) {
                            //String territoryName = tName;
                            //Find territory in the player's owned availableTerritories list by its name, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tName, player.getOwnedTerritories());
                        } else {
                            //int territoryIndex = tIndex;
                            //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tIndex, player.getOwnedTerritories());
                        }

                        //If territory is found, then call the function addOwnedTerritory from player object
                        //If the territory is already owned, then just increase its number of army
                        //Otherwise, output error
                        if (foundTerritory != null) {
                            saveState(player, foundTerritory);
                            player.addOwnedTerritory(foundTerritory);
                            if (askForUndo(userInput)) {
                                undo(player, foundTerritory);
                                i--;
                            }
                        } else if (tName != null)
                            System.out.println("Player " + player.getPlayerName() + " does not own " + tName);
                        else
                            System.out.println("Player " + player.getPlayerName() + " does not own territory has index of " + tIndex);
                    }
                }
            }
        }
    }

    public static void setTerritory(List<Player> players,  List<Territory> availableTerritories,
                                    List<Territory> finalTerritories,  TelegramBot bot, String mapPath) {
        if (bot != null) {
            //Assign the number of army each player will have base on the number of player
            int armiesEachPlayer = getNumberOfArmyEachPlayer(players.size());
            if (armiesEachPlayer <= 0) return;
            players.forEach(player -> player.setNumOfAvailableArmy(armiesEachPlayer));

            //Set territory stage
            for (int i = 0; i < armiesEachPlayer; i++) {
                for (int j = 0; j < players.size(); j++) {
                    Territory foundTerritory = null;

                    //The loop will continue to ask what move the player wants
                    //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                    while (foundTerritory == null) {
                        String addOutput = "It's " + players.get(j).getPlayerName() + "'s turn to place armies.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(availableTerritories, finalTerritories,
                                players, players.get(j), mapPath, addOutput, bot);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                        //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                        //Then, that territory need to be removed from the available availableTerritories list
                        if (availableTerritories.size() > 0) {
                            foundTerritory = findTerritory(tName, tIndex, availableTerritories);

                            //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                            //Otherwise, output error and ask that player again
                            if (foundTerritory != null) {
                                players.get(j).addOwnedTerritory(foundTerritory);
                                availableTerritories.remove(foundTerritory);
                                if (askForUndo(bot)) {
                                    undo(players.get(j), foundTerritory);
                                    availableTerritories.add(foundTerritory);
                                    j--;
                                    i--;
                                }
                            } else bot.sendMessage(".Territory not found");
                        } else {
                            //Occurred when there is no available territory but there are armies left that have not set
                            //At this time, each player can place their armies any where within their owned availableTerritories

                            if (tName != null) {
                                //String territoryName = tName;
                                //Find territory in the player's owned availableTerritories list by its name, then store in the foundTerritory variable
                                foundTerritory = findTerritory(tName, players.get(j).getOwnedTerritories());
                            } else {
                                //int territoryIndex = tIndex;
                                //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
                                foundTerritory = findTerritory(tIndex, players.get(j).getOwnedTerritories());
                            }

                            //If territory is found, then call the function addOwnedTerritory from player object
                            //If the territory is already owned, then just increase its number of army
                            //Otherwise, output error
                            if (foundTerritory != null) {
                                saveState(players.get(j), foundTerritory);
                                players.get(j).addOwnedTerritory(foundTerritory);
                                if (askForUndo(bot)) {
                                    undo(players.get(j), foundTerritory);
                                    j--;
                                    i--;
                                }
                            } else if (tName != null)
                                bot.sendMessage(".Player " + players.get(j).getPlayerName() + " does not own " + tName);
                            else
                                bot.sendMessage(".Player " + players.get(j).getPlayerName() + " does not own territory has index of " + tIndex);
                        }
                    }
                }
            }
        }
    }

    public static void setTerritory(Player player,  List<Player> players,  List<Territory> availableTerritories,
                                    List<Territory> finalTerritories,  TelegramBot bot, String mapPath) {
        if (bot != null) {
            //Set territory stage
            for (int i = 0; i < player.getNumOfAvailableArmy(); i++) {
                Territory foundTerritory = null;

                //The loop will continue to ask what move the player wants
                //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                while (foundTerritory == null) {
                    String addOutput = "It's " + player.getPlayerName() + "'s turn to place armies.";
                    Pair<String, Integer> stringIntegerPair = userInputRequest(availableTerritories, finalTerritories,
                            players, player, mapPath, addOutput, bot);
                    String tName = stringIntegerPair.getFirst();
                    int tIndex = stringIntegerPair.getSecond();
                    //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                    //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                    //Then, that territory need to be removed from the available availableTerritories list
                    if (availableTerritories.size() > 0) {
                        foundTerritory = findTerritory(tName, tIndex, availableTerritories);

                        //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                        //Otherwise, output error and ask that player again
                        if (foundTerritory != null) {
                            player.addOwnedTerritory(foundTerritory);
                            availableTerritories.remove(foundTerritory);
                            if (askForUndo(bot)) {
                                undo(player, foundTerritory);
                                availableTerritories.add(foundTerritory);
                                i--;
                            }
                        } else bot.sendMessage("Territory not found");
                    } else {
                        //Occurred when there is no available territory but there are armies left that have not set
                        //At this time, each player can place their armies any where within their owned availableTerritories

                        if (tName != null) {
                            //String territoryName = tName;
                            //Find territory in the player's owned availableTerritories list by its name, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tName, player.getOwnedTerritories());
                        } else {
                            //int territoryIndex = tIndex;
                            //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tIndex, player.getOwnedTerritories());
                        }

                        //If territory is found, then call the function addOwnedTerritory from player object
                        //If the territory is already owned, then just increase its number of army
                        //Otherwise, output error
                        if (foundTerritory != null) {
                            saveState(player, foundTerritory);
                            player.addOwnedTerritory(foundTerritory);
                            if (askForUndo(bot)) {
                                undo(player, foundTerritory);
                                i--;
                            }
                        } else if (tName != null)
                            bot.sendMessage("Player " + player.getPlayerName() + " does not own " + tName);
                        else
                            bot.sendMessage("Player " + player.getPlayerName() + " does not own territory has index of " + tIndex);
                    }
                }
            }
        }
    }

    /**
     * <p style="color:blue;">Set up everything to prepare battles</p>
     * <p style="color:blue;">Users will be ask to choose a territory to attack from and a territory they want to attack</p>
     * @param finalTerritories The list of all supported territories
     * @param players The list of all players
     * @param mapPath The path to the map image
     * @param userInput User input variable
     */
    public static void battleStage(List<Territory> finalTerritories, List<Player> players, String mapPath, Scanner userInput) {
        if (userInput != null) {
            for (int i = 0; i < players.size(); i++) {
                while (!players.get(i).isLost()) {
                    //Timer timer = players.get(i).getTimer();
                    String input;
                    //timer.start();
                    String addOutput = "It's " + players.get(i).getPlayerName() + " to attack.\nDo you want to attack (Y/N): ";
                    input = userInputRequest(finalTerritories, finalTerritories, players, players.get(i), mapPath, addOutput, userInput).getFirst();
                    if (input == null) input = "";
                    if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                        System.out.println("You have to answer either Y or N.");
                        //timer.resetTime();
                        continue;
                    } else if (input.toLowerCase().equals("n")) break;

                    Player currentPlayer = players.get(i);

                    Territory attackerTerritory = null;
                    //while (attackerTerritory == null && !timer.isTimeOut()) {
                    while (attackerTerritory == null) {
                        addOutput = "It's " + players.get(i).getPlayerName() + " to attack.\n" +
                                "First choose the territory to attack from.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, finalTerritories, players, currentPlayer,
                                mapPath, addOutput, userInput);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        attackerTerritory = findTerritory(tName, tIndex, players.get(i).getOwnedTerritories());
                        if (attackerTerritory == null) System.out.println(".Territory not found.");
                        System.out.println("======================================================================");
                    }

                    Territory defenderTerritory = null;
                    //while (defenderTerritory == null && !timer.isTimeOut()) {
                    while (defenderTerritory == null) {
                        addOutput = "Now choose the territory you wish to attack.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, finalTerritories, players, players.get(i),
                                mapPath, addOutput, userInput);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        List<Territory> otherPlayerTerritories = finalTerritories.stream()
                                .filter(territory -> !currentPlayer.getOwnedTerritories().contains(territory))
                                .collect(Collectors.toList());
                        defenderTerritory = findTerritory(tName, tIndex, otherPlayerTerritories);
                        if (defenderTerritory == null)
                            System.out.println(".Territory not found." +
                                    "\nCheck if the territory you enter is valid and not one of your owned territories.");
                    /*else if (askForUndo(userInput)) {
                        i--;
                        continue;
                    }*/
                        System.out.println("======================================================================");
                    }
                    //if (timer.isTimeOut()) break;
                    /*else*/
                    play(currentPlayer, attackerTerritory, defenderTerritory.getOccupiedBy(), defenderTerritory, userInput);
                }
                if (i == players.size() - 1 && !checkWinCondition(players)) i = -1;
            }
        }
    }

    public static void battleStage(List<Territory> finalTerritories, List<Player> players, String mapPath, TelegramBot bot) {
        if (bot != null) {
            for (int i = 0; i < players.size(); i++) {
                while (!players.get(i).isLost()) {
                    //Timer timer = players.get(i).getTimer();
                    String input;
                    //timer.start();
                    String addOutput = "It's " + players.get(i).getPlayerName() + " to attack.\nDo you want to attack (Y/N): ";
                    input = userInputRequest(finalTerritories, finalTerritories, players, players.get(i), mapPath, addOutput, bot).getFirst();
                    if (input == null) input = "";
                    if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
                        bot.sendMessage("You have to answer either Y or N.");
                        //timer.resetTime();
                        continue;
                    } else if (input.toLowerCase().equals("n")) break;

                    Player currentPlayer = players.get(i);

                    Territory attackerTerritory = null;
                    //while (attackerTerritory == null && !timer.isTimeOut()) {
                    while (attackerTerritory == null) {
                        addOutput = "It's " + players.get(i).getPlayerName() + " to attack.\n" +
                                "First choose the territory to attack from.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, finalTerritories, players, currentPlayer,
                                mapPath, addOutput, bot);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        attackerTerritory = findTerritory(tName, tIndex, players.get(i).getOwnedTerritories());
                        if (attackerTerritory == null) bot.sendMessage(".Territory not found.");
                    }

                    Territory defenderTerritory = null;
                    //while (defenderTerritory == null && !timer.isTimeOut()) {
                    while (defenderTerritory == null) {
                        addOutput = "Now choose the territory you wish to attack.";
                        Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, finalTerritories, players, players.get(i),
                                mapPath, addOutput, bot);
                        String tName = stringIntegerPair.getFirst();
                        int tIndex = stringIntegerPair.getSecond();
                        List<Territory> otherPlayerTerritories = finalTerritories.stream()
                                .filter(territory -> !currentPlayer.getOwnedTerritories().contains(territory))
                                .collect(Collectors.toList());
                        defenderTerritory = findTerritory(tName, tIndex, otherPlayerTerritories);
                        if (defenderTerritory == null)
                            bot.sendMessage(".Territory not found." +
                                    "\nCheck if the territory you enter is valid and not one of your owned territories.");
                    /*else if (askForUndo(userInput)) {
                        i--;
                        continue;
                    }*/
                    }
                    //if (timer.isTimeOut()) break;
                    /*else*/
                    play(currentPlayer, attackerTerritory, defenderTerritory.getOccupiedBy(), defenderTerritory, bot);
                }
                if (i == players.size() - 1 && !checkWinCondition(players)) i = -1;
            }
        }
    }

    /**
     * <p style="color:blue;">Each player involves in the battle will choose number of dice they want to
     * roll depends on how many troops they have on their territories which involved in the battle</p>
     * @param attacker The player who is going to attack
     * @param attackerTerritory The territory the attacker is going to attack from
     * @param defender The player who is going to have to defend
     * @param defenderTerritory The territory the attack is going to attack, also the territory the defender has to defend
     * @param userInput User input variable
     * @return
     */
    public static int play(Player attacker, Territory attackerTerritory, Player defender, Territory defenderTerritory, Scanner userInput) {
        int numbOfAttackArmy = 0;
        int numbOfDefendArmy = defenderTerritory.getNumbOfArmy();
        int numbOfAttackerSpareArmy = 1;
        int numbOfDefenderSpareArmy = 0;
        int numbOfAttackerPenaltyArmy = 1;
        int numbOfDefenderPenaltyArmy = 1;
        int result = -2;
        if (userInput != null) {
            Battle atk = new Attack(attacker, attackerTerritory, defenderTerritory, attackerTerritory.getNumbOfArmy(),
                    numbOfAttackerSpareArmy, numbOfAttackerPenaltyArmy, numbOfDefenderPenaltyArmy);

            if (attackerTerritory.getNumbOfArmy() == 1)
                System.out.println("You cannot launch attack from " + attackerTerritory.getTerritoryName() +
                        ". Because it has only 1 army.");
            else if (atk.startBattle(atk.askUserNumberOfDice(userInput))) {
                Battle def = new Defend(defender, defenderTerritory, attackerTerritory, defenderTerritory.getNumbOfArmy(),
                        numbOfDefenderSpareArmy);
                if (def.startBattle(def.askUserNumberOfDice(userInput))) {
                    result = Battle.getBattleResult(atk, def);
                    saveState(atk.thisPlayer, atk.thisTerritory);
                    atk.afterBattle(result, userInput);
                    saveState(def.thisPlayer, def.thisTerritory);
                    def.afterBattle(result, userInput);
                }
                if (askForUndo(userInput)) {
                    undo(atk.thisPlayer, atk.thisTerritory);
                    undo(def.thisPlayer, def.thisTerritory);
                }
                //recordBattle((Attack)atk, (Defend)def, result);
            }
        }
        return result;
    }

    public static int play(Player attacker, Territory attackerTerritory, Player defender, Territory defenderTerritory, TelegramBot bot) {
        int numbOfAttackArmy = 0;
        int numbOfDefendArmy = defenderTerritory.getNumbOfArmy();
        int numbOfAttackerSpareArmy = 1;
        int numbOfDefenderSpareArmy = 0;
        int numbOfAttackerPenaltyArmy = 1;
        int numbOfDefenderPenaltyArmy = 1;
        int result = -2;
        if (bot != null) {
            Battle atk = new Attack(attacker, attackerTerritory, defenderTerritory, attackerTerritory.getNumbOfArmy(),
                    numbOfAttackerSpareArmy, numbOfAttackerPenaltyArmy, numbOfDefenderPenaltyArmy);

            if (attackerTerritory.getNumbOfArmy() == 1)
                bot.sendMessage("You cannot launch attack from " + attackerTerritory.getTerritoryName() +
                        ". Because it has only 1 army.");
            else if (atk.startBattle(atk.askUserNumberOfDice(bot), bot)) {
                Battle def = new Defend(defender, defenderTerritory, attackerTerritory, defenderTerritory.getNumbOfArmy(),
                        numbOfDefenderSpareArmy);
                if (def.startBattle(def.askUserNumberOfDice(bot), bot)) {
                    result = Battle.getBattleResult(atk, def);
                    saveState(atk.thisPlayer, atk.thisTerritory);
                    atk.afterBattle(result, bot);
                    saveState(def.thisPlayer, def.thisTerritory);
                    def.afterBattle(result, bot);
                }
                if (askForUndo(bot)) {
                    undo(atk.thisPlayer, atk.thisTerritory);
                    undo(def.thisPlayer, def.thisTerritory);
                }
                //recordBattle((Attack)atk, (Defend)def, result);
            }
            bot.clearMessage();
        }
        return result;
    }

    /**
     * <p style="color:blue;">Check whether we have the final winner of the game</p>
     * @param players The list contains all player
     * @return The result of the battle:
     * <p><b style="color:blue;">result</b> <b> = -2</b> means there are error(s) when execute battle function</p>
     * <p><b style="color:blue;">result</b> <b> = -1</b> means the attacker loses, and the defender wins</p>
     * <p><b style="color:blue;">result</b> <b> = 0</b> means neither the attacker nor the defender win</p>
     * <p><b style="color:blue;">result</b> <b> = 1</b> means the attacker wins, and the defender loses</p>
     */
    public static boolean checkWinCondition(List<Player> players) {
        int playerLostCounter = 0;
        for (Player player : players) {
            if (player.isLost()) playerLostCounter++;
        }
        return playerLostCounter >= players.size() - 1;
    }

    /**
     * <p style="color:blue;">Print every territories of each player and free territories that players can still claim</p>
     * @param players The player list
     * @param territories The territory list
     */
    public static void printTerritory(List<Player> players,  List<Territory> territories) {
        System.out.println("======================================================================");

        //Print out each player's owned territories
        players.forEach(player -> {
            System.out.println(player.getPlayerName() + "'s owned territories:");
            player.getOwnedTerritories().forEach(territory -> {
                System.out.print(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());

                //If this is the last territory in the player's owned territories list, then print ", " as well
                if (player.getOwnedTerritories().indexOf(territory) < player.getOwnedTerritories().size() - 1)
                    System.out.print(", ");
                else System.out.println();
            });
            System.out.println("======================================================================");
        });

        //Print out the available territories
        System.out.println("Unoccupied territories: ");

        //This will need refactoring, normally will need to put into another function
        boolean firstTerritory = true; //The function to check if it print the first territory in the list
        for (Territory t : territories) {

            //Check whether the territory is already in any player's owned territory
            boolean isExist = false;
            for (Player p : players) {
                if (p.getOwnedTerritories().contains(t)) {
                    isExist = true;
                    break;
                }
            }

            //If there the territory t is not in any of the player's owned territories, then print it out
            if (!isExist) {
                //If the territory is the first one to be print, then do not print the ", " before print it
                if (!firstTerritory) System.out.print(", ");
                firstTerritory = false;
                System.out.print(t.getTerritoryName());
            }
        }
        System.out.println("\n======================================================================");
    }

    /**
     * <p style="color:blue;">Print every territories of the current player and every free territories that players can still claim</p>
     * @param player The player which the information is pulled from
     * @param players The player list
     * @param territories The territory list
     */
    public static void printTerritory(Player player,  List<Player> players,  List<Territory> territories) {
        System.out.println("======================================================================");
        System.out.println(player.getPlayerName() + "'s owned territories:");
        player.getOwnedTerritories().forEach(territory -> {
            System.out.print(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());
            if (player.getOwnedTerritories().indexOf(territory) < player.getOwnedTerritories().size() - 1)
                System.out.print(", ");
            else System.out.println();
        });
        System.out.println("======================================================================");

        System.out.println("Unoccupied territories: ");
        boolean firstTerritory = true;
        for (Territory t : territories) {
            boolean isExist = false;
            for (Player p : players) {
                if (p.getOwnedTerritories().contains(t)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!firstTerritory) System.out.print(", ");
                firstTerritory = false;
                System.out.print(t.getTerritoryName());
            }
        }
        System.out.println("\n======================================================================");
    }

    /**
     * <p style="color:blue;">Print all available territories that players can claim</p>
     * @param players The player list
     * @param territories The territory list
     * @param unoccupied Just the variable so I can overload this function
     */
    public static void printTerritory(List<Player> players,  List<Territory> territories, boolean unoccupied) {
        System.out.println("======================================================================");
        System.out.println("Unoccupied territories: ");
        boolean firstTerritory = true;
        for (Territory t : territories) {
            boolean isExist = false;
            for (Player p : players) {
                if (p.getOwnedTerritories().contains(t)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!firstTerritory) System.out.print(", ");
                firstTerritory = false;
                System.out.print(t.getTerritoryName());
            }
        }
        System.out.println("\n======================================================================");
    }

    /**
     * <p style="color:blue;">Print basic information of territory base on <b>territoryName</b> and all its adjacent territories</p>
     * @param territoryName .Territory name which the information is pulled from
     * @param territories .Territory list
     * @return <b>True</b> when territoryName is valid territory, and <b>False</b> when territoryName is not found
     */
    public static boolean printTerritory(String territoryName,  List<Territory> territories) {
        //Find the territory by name in the territories list, then store it in the territory variable
        Territory territory = territories.stream().filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().orElse(null);
        if (territory != null) {
            System.out.println("======================================================================");
            //Print out all adjacent territories of the territory which is stored in territory variable
            territory.printAdjTerritories();
            System.out.println("======================================================================");
            return true;
        }
        return false;
    }

    /**
     * <p style="color:blue;">Print basic information of territory base on <b>territoryIndex</b> and all its adjacent territories</p>
     * @param territoryIndex .Territory index which corresponding to the order the territory names are appeared in the console window
     *                       and from that we can pull the information of the territory
     * @param territories The territory list
     * @return <b>True</b> when territoryName is valid territory, and <b>False</b> when territoryName is not found
     */
    public static boolean printTerritory(int territoryIndex,  List<Territory> territories) {
        //Find the territory by index in the territories list, then store it in the territory variable
        Territory territory = territories.stream().filter(t -> t.getTerritoryIndex() == territoryIndex).findFirst().orElse(null);
        if (territory != null) {
            System.out.println("======================================================================");
            //Print out all adjacent territories of the territory which is stored in territory variable
            territory.printAdjTerritories();
            System.out.println("======================================================================");
            return true;
        }
        return false;
    }

    public static void printTerritory(List<Player> players,  List<Territory> territories, TelegramBot bot) {
        if (bot != null) {
            //Print out each player's owned territories
            players.forEach(player -> {
                bot.sendMessage(player.getPlayerName() + "'s owned territories:");
                player.getOwnedTerritories().forEach(territory -> {
                    bot.sendMessage(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());

                    //If this is the last territory in the player's owned territories list, then print ", " as well
                    if (player.getOwnedTerritories().indexOf(territory) < player.getOwnedTerritories().size() - 1)
                        bot.sendMessage(", ");
                    else bot.sendMessage("\n");
                });
            });

            //Print out the available territories
            bot.sendMessage("Unoccupied territories: ");

            //This will need refactoring, normally will need to put into another function
            boolean firstTerritory = true; //The function to check if it print the first territory in the list
            for (Territory t : territories) {

                //Check whether the territory is already in any player's owned territory
                boolean isExist = false;
                for (Player p : players) {
                    if (p.getOwnedTerritories().contains(t)) {
                        isExist = true;
                        break;
                    }
                }

                //If there the territory t is not in any of the player's owned territories, then print it out
                if (!isExist) {
                    //If the territory is the first one to be print, then do not print the ", " before print it
                    if (!firstTerritory) bot.sendMessage(", ");
                    firstTerritory = false;
                    bot.sendMessage(t.getTerritoryName());
                }
            }
        }
    }

    public static void printTerritory(Player player,  List<Player> players,  List<Territory> territories, TelegramBot bot) {
        if (bot != null) {
            bot.sendMessage(player.getPlayerName() + "'s owned territories:");
            player.getOwnedTerritories().forEach(territory -> {
                bot.sendMessage(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());
                if (player.getOwnedTerritories().indexOf(territory) < player.getOwnedTerritories().size() - 1)
                    bot.sendMessage(", ");
                else bot.sendMessage("\n");
            });

            bot.sendMessage("Unoccupied territories: ");
            boolean firstTerritory = true;
            for (Territory t : territories) {
                boolean isExist = false;
                for (Player p : players) {
                    if (p.getOwnedTerritories().contains(t)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    if (!firstTerritory) System.out.print(", ");
                    firstTerritory = false;
                    System.out.print(t.getTerritoryName());
                }
            }
        }
    }

    public static void printTerritory(List<Player> players,  List<Territory> territories, boolean unoccupied, TelegramBot bot) {
        if (bot != null) {
            bot.sendMessage("Unoccupied territories: ");
            boolean firstTerritory = true;
            for (Territory t : territories) {
                boolean isExist = false;
                for (Player p : players) {
                    if (p.getOwnedTerritories().contains(t)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    if (!firstTerritory) bot.sendMessage(", ");
                    firstTerritory = false;
                    bot.sendMessage(t.getTerritoryName());
                }
            }
        }
    }

    public static boolean printTerritory(String territoryName,  List<Territory> territories, TelegramBot bot) {
        if (bot != null) {
            //Find the territory by name in the territories list, then store it in the territory variable
            Territory territory = territories.stream().filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().orElse(null);
            if (territory != null) {
                //Print out all adjacent territories of the territory which is stored in territory variable
                territory.printAdjTerritories(bot);
                return true;
            }
        }
        return false;
    }

    public static boolean printTerritory(int territoryIndex,  List<Territory> territories, TelegramBot bot) {
        if (bot != null) {
            //Find the territory by index in the territories list, then store it in the territory variable
            Territory territory = territories.stream().filter(t -> t.getTerritoryIndex() == territoryIndex).findFirst().orElse(null);
            if (territory != null) {
                //Print out all adjacent territories of the territory which is stored in territory variable
                territory.printAdjTerritories(bot);
                return true;
            }
        }
        return false;
    }

    /**
     * <p style="color:blue;">Find the territory base on its name</p>
     * @param territoryName The territory name to find
     * @param territories The territory list
     * @return The territory if found one, or <b>null</b> of it is not found
     */
    public static Territory findTerritory(String territoryName,  List<Territory> territories) {
        return territories.stream()
                .filter(territory -> territory.getTerritoryName().equals(territoryName))
                .findFirst().orElse(null);
    }

    /**
     * <p style="color:blue;">Find the territory base on its index</p>
     * @param territoryIndex The territory index to find
     * @param territories The territory list
     * @return The territory if found one, or <b>null</b> of it is not found
     */
    public static Territory findTerritory(int territoryIndex,  List<Territory> territories) {
        return territories.stream()
                .filter(territory -> territory.getTerritoryIndex() == territoryIndex)
                .findFirst().orElse(null);
    }

    /**
     * <p style="color:blue;">Find the territory base on its index</p>
     * @param territoryName The territory name to find
     * @param territoryIndex The territory index to find
     * @param territories The territory list
     * @return The territory if found one, or <b>null</b> of it is not found
     */
    public static Territory findTerritory(String territoryName ,int territoryIndex,  List<Territory> territories) {
        Territory foundTerritory = null;
        if (territoryName != null) {
            //String territoryName = tName;
            //Find territory in the available territories list by its name, then store in the foundTerritory variable
            foundTerritory = findTerritory(territoryName, territories);
        } else {
            //int territoryIndex = tIndex;
            //Find territory in the available territories list by its index, then store in the foundTerritory variable
            foundTerritory = findTerritory(territoryIndex, territories);
        }
        return  foundTerritory;
    }

    /**
     * <p style="color:blue;">Display the map image in another window</p>
     * @param mapPath The path to the map image to draw
     */
    public static void displayMap(String mapPath) {
        if (mapPath != "") {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(mapPath));
            } catch (IOException e) {
            }

            if (img != null) {
                //Store the img variable in another variable to pass to inner class
                BufferedImage image = img; //Effectively final image variable
                //Create new panel to draw on
                JPanel jPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics graphic) {
                        super.paintComponent(graphic);
                        graphic.drawImage(image, 0, 0, null); //Draw the image
                    }
                };

                JFrame jFrame = buildFrame(); //Build the frame
                jFrame.add(jPanel); //Add the drawn panel to frame and display it
            }
        }
    }

    /**
     * Build the frame or window to display the map image
     * @return The frame or window which the image will be drawn on
     */
    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(1200, 850);
        frame.setVisible(true);
        return frame;
    }

    private  static void recordPlayerNames(List<Player> players) {
        String fileName = "ReplayPlayerList.re";
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            players.forEach(player -> printWriter.println(player.getPlayerName()));
            printWriter.close();
            bufferedWriter.close();
        }
        catch(IOException e) {
            System.out.println("Error recording replay.");
        }
        UploadSave.Upload(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator, fileName);
    }

    private static void recordSetTerritory(Player player, Territory territory) {
        String fileName = "ReplayTerritory.re";
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.println("Player: " + player.getPlayerName() + " acquired " + territory.getTerritoryName());
            printWriter.close();
            bufferedWriter.close();
        }
        catch(IOException e) {
            System.out.println("Error recording replay.");
        }
        UploadSave.Upload(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator, fileName);
    }

    private static void recordBattle(Attack atk, Defend def, int result) {
        String fileName = "ReplayBattle.re";
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            printWriter.print(atk.thisPlayer.getPlayerName() + " rolled: ");
            atk.thisPlayer.printRolledDice(printWriter);
            printWriter.print(def.thisPlayer.getPlayerName() + " rolled: ");
            def.thisPlayer.printRolledDice(printWriter);
            if (result < 0) {
                printWriter.println(atk.thisPlayer.getPlayerName() + " won.");
            }
            else if (result >= 0){
                printWriter.println(def.thisPlayer.getPlayerName() + " defended " + def.thisTerritory.getTerritoryName());
            }
            printWriter.println(atk.thisTerritory.getTerritoryName() + " has " + atk.thisTerritory.getNumbOfArmy() +
                    " on it. It's currently owned by " + atk.thisTerritory.getOccupiedBy().getPlayerName());
            printWriter.println(atk.otherTerritory.getTerritoryName() + " has " + atk.otherTerritory.getNumbOfArmy() +
                    " on it. It's currently owned by " + atk.otherTerritory.getOccupiedBy().getPlayerName());
            bufferedWriter.close();
            printWriter.close();
        }
        catch(IOException e) {
            System.out.println("Error recording replay.");
        }
        UploadSave.Upload(System.getProperty("user.dir") + File.separator +
                "Replay" + File.separator, fileName);
    }

    private static List<Player> readPlayerName() {
        String fileName = "ReplayPlayerList.re";
        String line;
        List<String> playerNames = new ArrayList<>();
        try {
            /*FileReader fileReader = new FileReader(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName);
            BufferedReader bf = new BufferedReader(fileReader);*/
            BufferedReader bf = new BufferedReader(new InputStreamReader(DownloadSave.GetInputStream(fileName)));
            while ((line = bf.readLine()) != null) if (!line.equals("")) playerNames.add((line));
            bf.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }
        if (playerNames.size() > 0) return createPlayers(1, 6, 3, playerNames.size(), playerNames);
        return null;
    }

    private static Pair<List<Player>, List<Territory>> readToTerritory(List<Player> players, List<Territory> finalTerritories) {
        String fileName = "ReplayTerritory.re";
        String line;
        try {
            /*FileReader fileReader = new FileReader(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName);
            BufferedReader bf = new BufferedReader(fileReader);*/
            BufferedReader bf = new BufferedReader(new InputStreamReader(DownloadSave.GetInputStream(fileName)));
            while ((line = bf.readLine()) != null) {
                if (!line.equals("")) {
                    line = line.substring("Player: ".length());
                    String playerName = line.substring(0, line.length() - line.substring(line.indexOf(" acquired ")).length());
                    Player thePlayer = players.stream().filter(player -> player.getPlayerName().equals(playerName))
                            .findFirst().orElse(null);
                    String territoryName = line.substring((playerName + " acquired ").length());
                    Territory theTerritory = findTerritory(territoryName, finalTerritories);
                    thePlayer.addOwnedTerritory(theTerritory);
                    //saveState(thePlayer, theTerritory);
                }
            }
            bf.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }
        return new Pair<>(players, finalTerritories);
    }

    private static Pair<List<Player>, List<Territory>> readToTerritoryArmy(List<Player> players, List<Territory> finalTerritories) {
        String fileName = "ReplayBattle.re";
        String line;
        try {
            /*FileReader fileReader = new FileReader(System.getProperty("user.dir") + File.separator +
                    "Replay" + File.separator + fileName);
            BufferedReader bf = new BufferedReader(fileReader);*/
            BufferedReader bf = new BufferedReader(new InputStreamReader(DownloadSave.GetInputStream(fileName)));
            while ((line = bf.readLine()) != null) {
                if (!line.equals("")) {
                    line = bf.readLine();
                    line = bf.readLine();
                    line = bf.readLine();

                    String territoryName = line.substring(0, line.length() - line.substring(line.indexOf(" has ")).length());
                    int numOfTroops = Integer.parseInt(line.substring(line.indexOf(" has ") + " has ".length(),
                            line.indexOf(" has ") + " has ".length() + (line.length() -
                            line.substring(0, line.indexOf(" has ") + " has ".length()).length() -
                            line.substring(line.indexOf(" on ")).length())));
                    String playerName = line.substring(line.substring(0, line.indexOf("by ") + 3).length());
                    Territory theTerritory = finalTerritories.stream().filter(territory -> territory.getTerritoryName()
                            .equals(territoryName)).findFirst().orElse(null);
                    if (theTerritory != null) theTerritory.setArmy(numOfTroops);
                    Player thePlayer = players.stream().filter(player -> player.getPlayerName().equals(playerName))
                            .findFirst().orElse(null);
                    if (theTerritory != null) theTerritory.setOccupiedBy(thePlayer);
                    //saveState(thePlayer, theTerritory);

                    line = bf.readLine();
                    String territoryName2 = line.substring(0, line.length() - line.substring(line.indexOf(" has ")).length());
                    int numOfTroops2 = Integer.parseInt(line.substring(line.indexOf(" has ") + " has ".length(),
                            line.indexOf(" has ") + " has ".length() + (line.length() -
                                    line.substring(0, line.indexOf(" has ") + " has ".length()).length() -
                                    line.substring(line.indexOf(" on ")).length())));
                    String playerName2 = line.substring(line.substring(0, line.indexOf("by ") + 3).length());
                    Territory theTerritory2 = finalTerritories.stream().filter(territory -> territory.getTerritoryName()
                            .equals(territoryName2)).findFirst().orElse(null);
                    if (theTerritory2 != null) theTerritory2.setArmy(numOfTroops2);
                    Player thePlayer2 = players.stream().filter(player -> player.getPlayerName().equals(playerName2))
                            .findFirst().orElse(null);
                    if (theTerritory2 != null) theTerritory2.setOccupiedBy(thePlayer2);
                    //saveState(thePlayer2, theTerritory2);
                }
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
        return new Pair<>(players, finalTerritories);
    }
}