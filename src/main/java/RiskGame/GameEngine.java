package RiskGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameEngine {

    //Name of the file contains list of all territories
    final String territoriesDataFileName = "TerritoryList";

    //The path to the Data folder which contains territories list and their adjacent territories
    final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

    //The path to the map image
    final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";

    final int minPlayer = 2;    //Minimum number of player
    final int maxPlayer = 6;    //Maximum number of player
    final int maxNumbOfDie = 3; //Maximum number of die each player can have

    public List<Territory> finalTerritories;
    public List<Player> players;

    public void startGame(TelegramBot telegramBot) {

        //List contain territories
        createTerritories(territoriesDataFileName);

        //Create player objects and store them in players list
        players = createPlayers(telegramBot);

        createTestData();

        //Telegram Bot
        if (telegramBot != null) telegramBot.sendMessage("Hi, let' start the game");

        initBattle(telegramBot);
    }

    public void initBattle(TelegramBot bot) {
        //Create territory objects and store them in territories list
        List<Territory>  availableTerritories = new ArrayList<>();
        availableTerritories.addAll(finalTerritories);

        if (finalTerritories.size() > 0)
        {
            if (!checkWinCondition()) {
                battleStage(bot);

                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setBonusArmies(0);
                    setAllTerritory(players.get(i), availableTerritories, bot);
                }

            }
            else {
                String winnerName = getWinner().getPlayerName();
                if (bot != null) bot.sendMessage( winnerName + " wins.");
            }
        }
    }

    public Player getWinner() {
        return players.stream().filter(player -> !player.isLost()).findFirst().get();
    }

    public void createTestData() {
        if (finalTerritories == null) createTerritories(territoriesDataFileName);

        //Set territory state of the game
        //Allow each player to set up their territories
        //setAllTerritory(players, availableTerritories, finalTerritories, userInput, mapPath);

        //Test
        if (players == null || (players != null && players.size() == 0)) {
            players = new ArrayList<>();
            players.add(new Player("Ton"));
            players.add(new Player("AI"));
        }
        players.get(0).setNumOfAvailableArmy(getNumberOfArmyEachPlayer(players.size()));
        players.get(1).setNumOfAvailableArmy(getNumberOfArmyEachPlayer(players.size()));
        for (int i = 1; i <= 42; i++) {
            if (i % 2 != 0) {
                players.get(0).addOwnedTerritory(findTerritory(i, finalTerritories));
            } else {
                players.get(1).addOwnedTerritory(findTerritory(i, finalTerritories));
            }
        }
        for (int i = 1; i <= 28; i++) {
            if (i % 2 != 0) {
                players.get(0).addOwnedTerritory(findTerritory(i, finalTerritories));
            }
            else {
                players.get(1).addOwnedTerritory(findTerritory(i, finalTerritories));
            }
        }
    }

    public List<Player> createPlayers(TelegramBot bot) {
        List<Player> players = new ArrayList<>();

        int numbOfPlayer = askForNumberOfPlayer(bot);
        //Asking names of the players
        List<String> playerNames = askForPlayerName(numbOfPlayer, bot);

        playerNames.forEach(name ->
        {
           Player player =  new Player(name, maxNumbOfDie);
           players.add(player);
        });

        return players;
    }

    public List<String> askForPlayerName(int numbOfPlayer, TelegramBot bot) {
        List<String> playerNames = new ArrayList<>();

        String playerName = "";
        for (int i = 0; i < numbOfPlayer; i++) {
            if (bot != null) {
                bot.clearMessage();
                bot.sendMessage("What is the name of player " + (i + 1) + ": ");
            } else playerName = "Player " + (i + 1);
            if (bot != null && bot.waitForInput() && bot.getMessage() != null) playerName = bot.getMessage();
            else if (bot != null) {
                i--;
                if (bot.isTest()) break;
                continue;
            }

            playerNames.add(playerName);
        }
        return playerNames;
    }

    public int askForNumberOfPlayer(TelegramBot bot) {

        int numbOfPlayer = 0;
        //Asking input number of player
        //Number of player must be >= minPlayer
        do {
            if (bot != null) {
                bot.sendMessage("How many players: ");
                bot.clearMessage();
                if (bot.waitForInput() && bot.getMessage() != null) {
                    try {
                        numbOfPlayer = Integer.parseInt(bot.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Number format exception.");
                        numbOfPlayer = 0;
                    }
                }
                if (bot.isTest()) break;
            } else numbOfPlayer = minPlayer;
        } while (numbOfPlayer < minPlayer || numbOfPlayer > maxPlayer);
        return numbOfPlayer;
    }

    public List<String> readTerritoriesData(String fileName, String fileExtension) {
        String line;
        List<String> territoryNames = new ArrayList<>();

        //Read territory data file to get the list of all territory
        try {
            FileReader fileReader = new FileReader(territoriesDataPath + fileName + fileExtension);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //Store territory names to a list
            while((line = bufferedReader.readLine()) != null) { territoryNames.add(line); }
            bufferedReader.close();
        }
        catch(IOException ex) {
            //System.out.println("Error reading file '" + fileName + "'");
            return null;
        }

        return territoryNames;
    }

    public boolean checkDuplicationsInTerritoryNames(List<String> territoryNames) {
        if (territoryNames != null) {
            List<String> territoryNamesWithoutDup = territoryNames.stream().distinct().collect(Collectors.toList());
            return territoryNamesWithoutDup.size() < territoryNames.size();
        }
        return false;
    }

    public List<String> filterOutTerritoryNamesDuplications(List<String> territoryNames) {
        if(territoryNames != null)
            return territoryNames.stream().distinct().collect(Collectors.toList());
        else return null;
    }

    public List<Territory> createTerritoriesFromNames(List<String> territoryNames) {
        List<Territory> territories = null;
        if (territoryNames != null) {
            territories = new ArrayList<>();
            for (int i = 0; i < territoryNames.size(); i++) {
                Territory territory = new Territory(territoryNames.get(i), i + 1);
                territories.add(territory);
            }
        }
        return territories;
    }

    public List<String> findAdjacentTerritoryNames(String territoryName) {
        return readTerritoriesData(territoryName, ".adj");
    }

    public void createTerritoryAdjacents(Territory territory, List<String> adjacentNames) {
        adjacentNames.forEach(item -> {
            Territory territoryToAdd = findTerritory(item, finalTerritories);
            if (territoryToAdd != null) territory.getAdjTerritories().add(territoryToAdd);
        });
    }

    public void createTerritories(String fileName) {
        List<String> territoryNames = readTerritoriesData(fileName, ".list");
        if (territoryNames == null) return;

        //If the territory name list without duplications is less in size then territory names list
        //Means the territoryDuplicatedPair returns 1 as the second value
        //It means that there are duplications
        //If user answers no, then quit the program
        if (checkDuplicationsInTerritoryNames(territoryNames)) return;

        territoryNames = filterOutTerritoryNamesDuplications(territoryNames);
        //Create each territory base on each territory name in the territoryName list
        finalTerritories = createTerritoriesFromNames(territoryNames);

        //Search and open file which contains adjacent territories for each territory in territories list
        for (Territory territory : finalTerritories) {
            List<String> adjacentNames = findAdjacentTerritoryNames(territory.getTerritoryName());
            createTerritoryAdjacents(territory, adjacentNames);
        }
    }

    /**
     * <p style="color:blue;">Return number of army each player can have</p>
     * @param numbOfPlayer The total number of player
     * @return Number of army each player can have
     */
    public int getNumberOfArmyEachPlayer(int numbOfPlayer) {
        switch(numbOfPlayer) {
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

    public boolean executeSpecialCommand(String command, Player player, List<Territory> territories, TelegramBot bot) {
        switch (command) {
            case "-la":
                printTerritory(players, territories, bot);
                if (bot != null) return true;
            case "-lm":
                printTerritory(player, players, territories, bot);
                if (bot != null) {
                    bot.sendMessage(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    return true;
                }
            case "-lav":
                printUnoccupiedTerritory(players, territories, bot);
                if (bot != null) return true;
        }
        return false;
    }

    public Pair<String, Integer> userInputRequest(List<Territory> territories, Player player, String addOutput, TelegramBot bot) {

        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        while (true) {
            //The questions program will ask each player each move
            if (bot != null) {
                bot.sendMessage(addOutput);
                bot.sendMessage("Enter -la to list all territories of all player and available territories.");
                bot.sendMessage("Enter -lm to list all territories of your possession.");
                bot.sendMessage("Enter -lav to list all available territories.");
                bot.sendMessage("Enter Territory name, or index, or command: ");
            }

            //Execute special command
            String input = "-2";
            if (bot == null) input = "1";
            if (bot != null) {
                bot.clearMessage();
                if (bot.waitForInput() && bot.getMessage() != null) input = bot.getMessage();
            }

            if (!executeSpecialCommand(input, player, territories, bot)) {
                //Try to parse the input string to a integer
                //If the string can be parse successfully, then the player entered a territory index instead of territory name
                try {
                    tIndex = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    //If the input string cannot be parse, then the player entered a territory name
                    tName = input;
                }
                if (bot != null) bot.clearMessage();
                break;
            } else if (bot != null){
                tIndex = -2;
                break;
            }

            if(bot == null || (bot != null && bot.isTest())) break;
        }
        return new Pair<>(tName, tIndex);
    }

    public Territory setTerritory(Player player, String tName, int tIndex, List<Territory> availableTerritories) {
        Territory foundTerritory = findTerritory(tName, tIndex, availableTerritories);
        if (foundTerritory != null) {
            player.addOwnedTerritory(foundTerritory);
            availableTerritories.remove(foundTerritory);
        }
        return foundTerritory;
    }

    public Territory addTroopsToOwnedTerritory(Player player, String tName, int tIndex) {
        Territory foundTerritory;

        if (tName != null) {
            //Find territory in the player's owned availableTerritories list by its name, then store in the foundTerritory variable
            foundTerritory = findTerritory(tName, player.getOwnedTerritories());
        } else {
            //Find territory in the player's owned availableTerritories list by its index, then store in the foundTerritory variable
            foundTerritory = findTerritory(tIndex, player.getOwnedTerritories());
        }

        if (foundTerritory != null) {
            player.addOwnedTerritory(foundTerritory);
        }
        return foundTerritory;
    }

    public void setAllTerritory(Player player, List<Territory> availableTerritories, TelegramBot bot) {
        //Set territory stage
        for (int i = 0; i < player.getNumOfAvailableArmy(); i++) {
            Territory foundTerritory = null;

            //The loop will continue to ask what move the player wants
            //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
            while (foundTerritory == null) {
                String addOutput = "It's " + player.getPlayerName() + "'s turn to place armies.";
                Pair<String, Integer> stringIntegerPair = new Pair<>("Alaska", 0);
                if (bot != null) stringIntegerPair = userInputRequest(availableTerritories, player, addOutput, bot);
                String tName = stringIntegerPair.getFirst();
                int tIndex = stringIntegerPair.getSecond();
                //If availableTerritories list size is still > 0, then there is at least 1 free territory available
                //If so, we add the territory the player entered to that player's owned availableTerritories list as a new territory
                //Then, that territory need to be removed from the available availableTerritories list
                if (availableTerritories.size() > 0) {
                    //When found a territory, add it to the player's owned availableTerritories list and remove it from available availableTerritories list
                    //Otherwise, output error and ask that player again
                    foundTerritory = setTerritory(player, tName, tIndex, availableTerritories);
                    if (foundTerritory == null && bot != null) bot.sendMessage("Territory not found");
                } else {
                    //Occurred when there is no available territory but there are armies left that have not set
                    //At this time, each player can place their armies any where within their owned availableTerritories
                    foundTerritory = addTroopsToOwnedTerritory(player, tName, tIndex);
                    if (foundTerritory == null && tName != null && bot != null)
                        bot.sendMessage("Player " + player.getPlayerName() + " does not own " + tName);
                    else if (bot != null)
                        bot.sendMessage("Player " + player.getPlayerName() + " does not own territory has index of " + tIndex);
                }

                if(bot == null || (bot != null && bot.isTest())) break;
            }
            if(bot == null || (bot != null && bot.isTest())) break;
        }
    }

    public int askToAttack(Player player, TelegramBot bot) {
        String input;
        String addOutput = "It's " + player.getPlayerName() + " to attack.\nDo you want to attack (Y/N): ";
        Pair<String, Integer> userInput = userInputRequest(finalTerritories, player, addOutput, bot);
        input = userInput.getFirst();
        if (userInput.getSecond() == -2 || bot == null) input = "N";
        if (input == null) input = "";
        if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n") && bot != null) {
            bot.sendMessage("You have to answer either Y or N.");
            return -1;
        } else if (input.toLowerCase().equals("n")) return 0;
        return 1;
    }

    public Territory askTerritoryToAttackFrom(Player player, TelegramBot bot) {
        String addOutput = "It's " + player.getPlayerName() + " to attack.\n" +
                "First choose the territory to attack from.";
        Territory attackerTerritory = null;

        while (attackerTerritory == null) {
            Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, player, addOutput, bot);

            String tName = stringIntegerPair.getFirst();
            int tIndex = stringIntegerPair.getSecond();

            if(tIndex == -2) break;

            attackerTerritory = findTerritory(tName, tIndex, player.getOwnedTerritories());
            if (attackerTerritory == null && bot != null) bot.sendMessage("Territory not found.");

            if(bot == null || (bot != null && bot.isTest())) break;
        }
        return attackerTerritory;
    }

    public Territory askTerritoryToAttackTo(Player player, Territory attackerTerritory, TelegramBot bot) {
        String addOutput = "Now choose the territory you wish to attack.";
        Territory defenderTerritory = null;

        while (defenderTerritory == null && attackerTerritory != null) {
            Pair<String, Integer> stringIntegerPair = userInputRequest(finalTerritories, player, addOutput, bot);

            String tName = stringIntegerPair.getFirst();
            int tIndex = stringIntegerPair.getSecond();
            if(tIndex == -2) break;

            List<Territory> otherPlayerTerritories = finalTerritories.stream()
                    .filter(territory -> !player.getOwnedTerritories().contains(territory))
                    .collect(Collectors.toList());

            defenderTerritory = findTerritory(tName, tIndex, otherPlayerTerritories);
            if ((defenderTerritory == null || !attackerTerritory.getAdjTerritories().contains(defenderTerritory)) && bot != null) {
                bot.sendMessage(".Territory not found." +
                        "\nCheck if the territory you enter is valid and not one of your owned territories.");
                defenderTerritory = null;
            }

            if (bot == null || (bot != null && bot.isTest())) break;
        }
        return defenderTerritory;
    }

    public void battleStage(TelegramBot bot) {
        for (int i = 0; i < players.size(); i++) {
            while (!players.get(i).isLost()) {
                Player currentPlayer = players.get(i);

                //wantToAttack == -1 -> user fail to answer either Y or N
                //wantToAttack == 0 -> user answers N
                //wantToAttack == 1 -> user answers Y
                int wantToAttack = askToAttack(currentPlayer, bot);
                if (bot != null) {
                    if (wantToAttack == 0) break;
                    else if (wantToAttack == -1) continue;
                }

                Territory attackerTerritory = askTerritoryToAttackFrom(currentPlayer, bot);
                if (attackerTerritory == null && bot != null) break;

                Territory defenderTerritory = askTerritoryToAttackTo(currentPlayer, attackerTerritory, bot);
                if (defenderTerritory == null && bot != null) break;

                int result = -2;
                if (attackerTerritory != null && defenderTerritory != null)
                    result = play(currentPlayer, attackerTerritory, defenderTerritory.getOccupiedBy(), defenderTerritory, bot);
                if (result == -2 || (bot != null && bot.isTest()))
                    break;
            }
            if (i == players.size() - 1 && !checkWinCondition()) i = -1;

            if (bot == null || (bot != null && bot.isTest())) break;
        }
    }

    public int play(Player attacker, Territory attackerTerritory, Player defender, Territory defenderTerritory, TelegramBot bot) {
        int numbOfAttackerSpareArmy = 1;
        int numbOfDefenderSpareArmy = 0;
        int numbOfAttackerPenaltyArmy = 1;
        int numbOfDefenderPenaltyArmy = 1;
        int result = -2;
        if (attacker != null && defender != null) {
            Battle atk = new Attack(attacker, attackerTerritory, defenderTerritory, attackerTerritory.getNumbOfArmy(),
                    numbOfAttackerSpareArmy, numbOfAttackerPenaltyArmy, numbOfDefenderPenaltyArmy);

            if (attackerTerritory.getNumbOfArmy() == 1 && bot != null)
                bot.sendMessage("You cannot launch attack from " + attackerTerritory.getTerritoryName() +
                        ". Because it has only 1 army.");
            else if (atk.startBattle(atk.askUserNumberOfDice(bot))) {
                atk.thisPlayer.printRolledDice(bot);
                Battle def = new Defend(defender, defenderTerritory, attackerTerritory, defenderTerritory.getNumbOfArmy(),
                        numbOfDefenderSpareArmy);
                if (def.startBattle(def.askUserNumberOfDice(bot))) {
                    def.thisPlayer.printRolledDice(bot);
                    result = Battle.getBattleResult(atk, def);

                    atk.afterBattle(result, bot);
                    def.afterBattle(result, bot);
                }
            }
            if (bot != null) bot.clearMessage();
        }
        return result;
    }

    public boolean checkWinCondition() {
        int playerLostCounter = 0;
        for (Player player : players) {
            if (player.isLost()) playerLostCounter++;
        }
        return playerLostCounter >= players.size() - 1;
    }

    public void printTerritory(List<Player> players,  List<Territory> territories, TelegramBot bot) {
            //Print out each player's owned territories
            players.forEach(player -> {
                if (bot != null) bot.sendMessage(player.getPlayerName() + "'s owned territories:");
                player.getOwnedTerritories().forEach(territory -> {
                    if (bot != null) bot.sendMessage(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());
                });
            });

            //Print out the available territories
            printUnoccupiedTerritory(players, territories, bot);
    }

    public void printTerritory(Player player,  List<Player> players,  List<Territory> territories, TelegramBot bot) {
        if (bot != null) bot.sendMessage(player.getPlayerName() + "'s owned territories:");
        player.getOwnedTerritories().forEach(territory -> {
            if (bot != null) bot.sendMessage(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());
        });

        printUnoccupiedTerritory(players, territories, bot);
    }

    public void printUnoccupiedTerritory(List<Player> players, List<Territory> territories, TelegramBot bot) {
        if (bot != null) bot.sendMessage("Unoccupied territories: ");
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
            if (!isExist && bot != null) {
                bot.sendMessage(t.getTerritoryName());
            }
        }
    }

    /**
     * <p style="color:blue;">Find the territory base on its name</p>
     * @param territoryName The territory name to find
     * @param territories The territory list
     * @return The territory if found one, or <b>null</b> of it is not found
     */
    public Territory findTerritory(String territoryName,  List<Territory> territories) {
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
    public Territory findTerritory(int territoryIndex,  List<Territory> territories) {
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
    public Territory findTerritory(String territoryName, int territoryIndex,  List<Territory> territories) {
        Territory foundTerritory;
        if (territoryName != null) {
            //Find territory in the available territories list by its name, then store in the foundTerritory variable
            foundTerritory = findTerritory(territoryName, territories);
        } else {
            //Find territory in the available territories list by its index, then store in the foundTerritory variable
            foundTerritory = findTerritory(territoryIndex, territories);
        }
        return foundTerritory;
    }
}
