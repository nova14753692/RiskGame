package KevinTonRafael.company;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
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

        //Name of the file contains list of all territories
        final String territoriesDataFileName = "TerritoryList";

        //The path to the Data folder which contains territories list and their adjacent territories
        final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

        //The path to the map image
        final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";
        int numOfPlayer = 0; //Number of player

        List<Player> players;   //List contains players
        List<Territory> territories;    //List contain territories

        players = createPlayers(numOfPlayer, minPlayer, maxPlayer, userInput);    //Create player objects and store them in players list

        //Create territory objects and store them in territories list
        territories = createTerritories(territoriesDataPath, territoriesDataFileName, userInput);

        //Set territory state of the game
        //Allow each player to set up their territories
        setTerritory(players, territories, userInput, mapPath);
        System.out.println();

        if (territories != null)
        {
            //Continue the game
        }//If not, end the program
    }

    /**
     * <p style="color:blue;">Return the list of every player</p>
     * @param numOfPlayer The number of player to be created
     * @param userInput Variable that accepts user input
     * @return The list of every player
     */
    public static List<Player> createPlayers(int numOfPlayer, int minPlayer, int maxPlayer, @NotNull Scanner userInput) {
        List<Player> players = new ArrayList<>();

        //Asking input number of player
        //Number of player must be >= minPlayer
        do {
            System.out.print("How many players: ");
            if (userInput.hasNextInt()) numOfPlayer = userInput.nextInt();
        } while (numOfPlayer < minPlayer || numOfPlayer > maxPlayer);
        userInput.nextLine();

        int i = 1; //The index of player
        //Asking names of the players
        while(i <= numOfPlayer) {
            System.out.print("What is the name of player " + i + ": ");
            if (userInput.hasNextLine()) {
                players.add(new Player(userInput.nextLine()));
                i++;
            }
            else System.out.println("Invalid input");
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
    @Nullable
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
    @Nullable
    public static  List<Territory> createTerritories(String filePath, String fileName, @NotNull Scanner userInput) {
        List<Territory> territories = new ArrayList<>();
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
                System.out.println("Territory list is updated and without duplication.");
            } else return null;
        }

        //Create each territory base on each territory name in the territoryName list
        for (int i = 0; i < territoryDuplicatedPair.getFirst().size(); i++)
            territories.add(new Territory(territoryDuplicatedPair.getFirst().get(i), i + 1));

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
                    System.out.println("Mismatch between available Territory and their adjacent territories.");
                    return null;
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
                return 40;
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
                                                         List<Player> players, Player player, String mapPath, Scanner userInput) {
        int tIndex = -1; //Territory index temporary variable, because no variable is allowed inside java lambda expression
        String tName = null; //Territory name temporary variable, because no variable is allowed inside java lambda expression
        boolean command = true;
        while (command) {
            //The questions program will ask each player each move
            //Need refactor
            System.out.println("======================================================================");
            System.out.println("It's " + player.getPlayerName() + "'s turn to place armies.");
            System.out.println("Enter -la to list all territories of all player and available territories.");
            System.out.println("Enter -lm to list all territories of your possession.");
            System.out.println("Enter -lav to list all available territories.");
            System.out.println("Enter -shde [Territory name] or -shde [Territory index] (eg: -shde Alaska or -shde 1)\n" +
                    " to list detail about that territory and its adjacent territories.");
            System.out.print("Enter Territory name, or index, or command: ");

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
                            System.out.println("Territory not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Territory not found.");
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
        return new Pair<String, Integer>(tName, tIndex);
    }

    /**
     * <p style="color:blue;">Each player take turn to set up their territories</p>
     * @param players The player list
     * @param territories The territory list
     * @param userInput Variable accepts user input
     * @param mapPath The path to the map image
     */
    public static void setTerritory(@NotNull List<Player> players, @NotNull List<Territory> territories, @NotNull Scanner userInput, String mapPath) {
        //Assign the number of army each player will have base on the number of player
        int armiesEachPlayer = getNumberOfArmyEachPlayer(players.size());
        if (armiesEachPlayer <= 0) return;

        List<Territory> finalTerritories = new ArrayList<>(); //The temporary list of territories
        //Add everything from territories list to the finalTerritories
        //territory list is now serves as the list contains only available territories
        finalTerritories.addAll(territories);

        //Set territory stage
        for (int i = 0; i < armiesEachPlayer; i++) {
            for (int j = 0; j < players.size(); j++) {
                Territory foundTerritory = null;

                //The loop will continue to ask what move the player wants
                //It will continue to ask for user input until the player has entered a valid territory (when foundTerritory != null)
                while (foundTerritory == null) {
                    Pair<String, Integer> stringIntegerPair = userInputRequest(territories, finalTerritories,
                            players, players.get(j), mapPath, userInput);
                    String tName = stringIntegerPair.getFirst();
                    int tIndex = stringIntegerPair.getSecond();
                    //If territories list size is still > 0, then there is at least 1 free territory available
                    //If so, we add the territory the player entered to that player's owned territories list as a new territory
                    //Then, that territory need to be removed from the available territories list
                    if (territories.size() > 0) {

                        if (tName != null) {
                            //String territoryName = tName;
                            //Find territory in the available territories list by its name, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tName, territories);
                        } else {
                            //int territoryIndex = tIndex;
                            //Find territory in the available territories list by its index, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tIndex, territories);
                        }

                        //When found a territory, add it to the player's owned territories list and remove it from available territories list
                        //Otherwise, output error and ask that player again
                        if (foundTerritory != null) {
                            players.get(j).addOwnedTerritory(foundTerritory);
                            territories.remove(foundTerritory);
                        } else System.out.println("Territory not found");
                    } else {
                        //Occurred when there is no available territory but there are armies left that have not set
                        //At this time, each player can place their armies any where within their owned territories

                        if (tName != null) {
                            //String territoryName = tName;
                            //Find territory in the player's owned territories list by its name, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tName, players.get(j).getOwnedTerritories());
                        } else {
                            //int territoryIndex = tIndex;
                            //Find territory in the player's owned territories list by its index, then store in the foundTerritory variable
                            foundTerritory = findTerritory(tIndex, players.get(j).getOwnedTerritories());
                        }

                        //If territory is found, then call the function addOwnedTerritory from player object
                        //If the territory is already owned, then just increase its number of army
                        //Otherwise, output error
                        if (foundTerritory != null) players.get(j).addOwnedTerritory(foundTerritory);
                        else if (tName != null) System.out.println("Player " + players.get(j) + " does not own " + tName);
                        else System.out.println("Player " + players.get(j) + " does not own territory has index of " + tIndex);
                    }
                }
            }
        }
    }

    /**
     * <p style="color:blue;">Print every territories of each player and free territories that players can still claim</p>
     * @param players The player list
     * @param territories The territory list
     */
    public static void printTerritory(@NotNull List<Player> players, @NotNull List<Territory> territories) {
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
    public static void printTerritory(@NotNull Player player, @NotNull List<Player> players, @NotNull List<Territory> territories) {
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
    public static void printTerritory(@NotNull List<Player> players, @NotNull List<Territory> territories, boolean unoccupied) {
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
     * @param territoryName Territory name which the information is pulled from
     * @param territories Territory list
     * @return <b>True</b> when territoryName is valid territory, and <b>False</b> when territoryName is not found
     */
    public static boolean printTerritory(@NotNull String territoryName, @NotNull List<Territory> territories) {
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
     * @param territoryIndex Territory index which corresponding to the order the territory names are appeared in the console window
     *                       and from that we can pull the information of the territory
     * @param territories The territory list
     * @return <b>True</b> when territoryName is valid territory, and <b>False</b> when territoryName is not found
     */
    public static boolean printTerritory(@NotNull int territoryIndex, @NotNull List<Territory> territories) {
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

    /**
     * <p style="color:blue;">Find the territory base on its name</p>
     * @param territoryName The territory name to find
     * @param territories The territory list
     * @return The territory if found one, or <b>null</b> of it is not found
     */
    @Nullable
    public static Territory findTerritory(String territoryName, @NotNull List<Territory> territories) {
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
    @Nullable
    public static Territory findTerritory(int territoryIndex, @NotNull List<Territory> territories) {
        return territories.stream()
                .filter(territory -> territory.getTerritoryIndex() == territoryIndex)
                .findFirst().orElse(null);
    }

    /**
     * <p style="color:blue;">Display the map image in another window</p>
     * @param mapPath The path to the map image to draw
     */
    public static void displayMap(String mapPath) {
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

}
