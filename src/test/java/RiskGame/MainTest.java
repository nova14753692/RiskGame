package RiskGame;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void createPlayers() {
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
    }

    @Test
    public void userInputRequest() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //Execute special command
        if (true) {
            command = false;
            String input = "-lm";

            switch (input) {
                case "-la":
                    //printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    //printTerritory(player, players, territories);
                    //System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    //printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    //displayMap(mapPath);
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                    /*try {
                        if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                            System.out.println(".Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println(".Territory not found.");
                    }*/
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
    }

    @Test
    public void userInputRequest_1() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //Execute special command
        if (true) {
            command = false;
            String input = "-lm";

            switch (input) {
                case "-la":
                    //printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    //printTerritory(player, players, territories);
                    //System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    //printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    //displayMap(mapPath);
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                    /*try {
                        if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                            System.out.println(".Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println(".Territory not found.");
                    }*/
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
    }

    @Test
    public void userInputRequest_2() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //Execute special command
        if (true) {
            command = false;
            String input = "-lav";

            switch (input) {
                case "-la":
                    //printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    //printTerritory(player, players, territories);
                    //System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    //printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    //displayMap(mapPath);
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                    /*try {
                        if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                            System.out.println(".Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println(".Territory not found.");
                    }*/
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
    }

    @Test
    public void userInputRequest_3() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //Execute special command
        if (true) {
            command = false;
            String input = "-map";

            switch (input) {
                case "-la":
                    //printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    //printTerritory(player, players, territories);
                    //System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    //printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    //displayMap(mapPath);
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                    /*try {
                        if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                            System.out.println(".Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println(".Territory not found.");
                    }*/
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
    }

    @Test
    public void userInputRequest_4() {
        int tIndex = -1; //.Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //.Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        //Execute special command
        if (true) {
            command = false;
            String input = "-shde Alaska";

            switch (input) {
                case "-la":
                    //printTerritory(players, territories);
                    command = true;
                    break;
                case "-lm":
                    //printTerritory(player, players, territories);
                    //System.out.println(player.getPlayerName() + " has: " + player.getNumOfAvailableArmy() + " armies.");
                    command = true;
                    break;
                case "-lav":
                    //printTerritory(players, territories, true);
                    command = true;
                    break;
                case "-map":
                    //displayMap(mapPath);
                    command = true;
                    break;
            }
            if (input.length() > 6 && input.substring(0, 5).equals("-shde")) {
                input = input.substring(6);
                    /*try {
                        if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                            System.out.println(".Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println(".Territory not found.");
                    }*/
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
    }

    @Test
    public void askForUndo() {
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