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

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);

        final int minPlayer = 2;
        final int maxPlayer = 6;
        final String territoriesDataFileName = "TerritoryList.list";
        final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;
        final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";
        int numOfPlayer = 0;

        List<Player> players;
        List<Territory> territories;

        do {
            System.out.print("How many players: ");
            if (userInput.hasNextInt()) numOfPlayer = userInput.nextInt();
        } while (numOfPlayer < minPlayer);

        players = createPlayers(numOfPlayer, userInput);
        territories = createTerritories(territoriesDataPath, territoriesDataFileName, userInput);
        setTerritory(players, territories, userInput, mapPath);
        System.out.println();
    }

    public static List<Player> createPlayers(int numOfPlayer, Scanner userInput) {
        List<Player> players = new ArrayList<>();

        int i = 0;
        while(i < numOfPlayer) {
            System.out.print("What is the name of player " + (i + 1) + ": ");
            if (userInput.hasNext()) {
                players.add(new Player(userInput.next()));
                i++;
            }
            else System.out.println("Invalid input");
        }
        return players;
    }

    @Nullable
    public static  List<Territory> createTerritories(String filePath, String fileName, Scanner userInput) {
        List<Territory> territories = new ArrayList<>();
        List<String> territoryNames = new ArrayList<>();
        String line;

        try {
            FileReader fileReader = new FileReader(filePath + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
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

        List<String> territoryNamesWithoutDup = territoryNames.stream().distinct().collect(Collectors.toList());
        String answer = null;
        if (territoryNamesWithoutDup.size() < territoryNames.size()) {
            System.out.println("There are duplications in TerritoryData!");
            while (answer != "Yes" && answer != "No") {
                System.out.print("Match territory list and continue? (Yes/No): ");
                if (userInput.hasNext()) answer = userInput.next();
            }
            if (answer == "Yes") {
                System.out.println("Territory list is updated and without duplication.");
            } else return null;
        }

        for (int i = 0; i < territoryNamesWithoutDup.size(); i++) territories.add(new Territory(territoryNames.get(i), i + 1));

        for (Territory territory : territories) {
            try {
                FileReader fileReader = new FileReader(filePath + territory.getTerritoryName() + ".adj");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while((line = bufferedReader.readLine()) != null) {
                    Territory territoryToAdd = findTerritory(line, territories);
                    if(territoryToAdd != null) territory.getAdjTerritories().add(territoryToAdd);
                    else {
                        System.out.println("Mismatch between available Territory and their adjacent territories.");
                        return null;
                    }
                }
                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println("Unable to open file '" + territory.getTerritoryName() + "'");
                return null;
            }
            catch(IOException ex) {
                System.out.println("Error reading file '" + territory.getTerritoryName() + "'");
                return null;
            }
        }

        return territories;
    }

    public static void setTerritory(@NotNull List<Player> players, @NotNull List<Territory> territories, Scanner userInput, String mapPath) {
        int armiesEachPlayer = 0;
        List<Territory> finalTerritories = new ArrayList<>();
        finalTerritories.addAll(territories);
        switch(players.size())
        {
            case 2:
            case 3:
                armiesEachPlayer = 35;
                break;
            case 4:
                armiesEachPlayer = 30;
                break;
            case 5:
                armiesEachPlayer = 25;
                break;
            case 6:
                armiesEachPlayer = 20;
                break;
        }

        for (int i = 0; i < armiesEachPlayer; i++)
        {
            for (int j = 0; j < players.size(); j++)
            {
                Territory foundTerritory = null;
                while (foundTerritory == null) {
                    int tIndex = -1;
                    String tName = null;
                    System.out.println("======================================================================");
                    System.out.println("It's " + players.get(j).getPlayerName() + "'s turn to place armies.");
                    System.out.println("Enter -la to list all territories of all player and available territories.");
                    System.out.println("Enter -lm to list all territories of your possession.");
                    System.out.println("Enter -lav to list all available territories.");
                    System.out.println("Enter -shde [Territory name] or -shde [Territory index] (eg: -shde Alaska or -shde 1)\n" +
                            " to list detail about that territory and its adjacent territories.");
                    System.out.print("Enter Territory name, index, or command: ");
                    if (userInput.hasNext()) {
                        String input = userInput.next();
                        boolean command = false;
                        switch (input) {
                            case "-la":
                                printTerritory(players, territories);
                                command = true;
                                break;
                            case "-lm":
                                printTerritory(players.get(j), players, territories);
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
                        if (input.equals("-shde")) {
                            input = userInput.next();
                            try {
                                if (!printTerritory(input, finalTerritories) && !printTerritory(Integer.parseInt(input), finalTerritories))
                                    System.out.println("Territory not found.");
                            } catch (NumberFormatException e) {
                                System.out.println("Territory not found.");
                            }
                            command = true;
                        }
                        if (command) continue;
                        try {
                            tIndex = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            tName = input;
                        }
                    }

                    if (territories.size() > 0) {

                        if (tName != null) {
                            String territoryName = tName;
                            foundTerritory = findTerritory(territoryName, territories);
                        } else {
                            int territoryIndex = tIndex;
                            foundTerritory = findTerritory(territoryIndex, territories);
                        }
                        if (foundTerritory != null) {
                            players.get(j).addOwnedTerritory(foundTerritory);
                            territories.remove(foundTerritory);
                        } else System.out.println("Territory not found");
                    } else {
                        if (tName != null) {
                            String territoryName = tName;
                            foundTerritory = findTerritory(territoryName, players.get(j).getOwnedTerritories());
                        } else {
                            int territoryIndex = tIndex;
                            foundTerritory = findTerritory(territoryIndex, players.get(j).getOwnedTerritories());
                        }
                        if (foundTerritory != null) players.get(j).addOwnedTerritory(foundTerritory);
                        else if (tName != null) System.out.println("Territory " + tName + " is not available.");
                        else System.out.println("Territory index " + tIndex + " is not available.");
                    }
                }
            }
        }
    }

    public static void printTerritory(@NotNull List<Player> players, @NotNull List<Territory> territories) {
        System.out.println("======================================================================");
        players.forEach(player -> {
            System.out.println(player.getPlayerName() + "'s owned territories:");
            player.getOwnedTerritories().forEach(territory -> {
                System.out.print(territory.getTerritoryName() + ": " + territory.getNumbOfArmy());
                if (player.getOwnedTerritories().indexOf(territory) < player.getOwnedTerritories().size() - 1)
                    System.out.print(", ");
                else System.out.println();
            });
            System.out.println("======================================================================");
        });

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
        System.out.println("======================================================================");
    }

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
        System.out.println("======================================================================");
    }

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
        System.out.println("======================================================================");
    }

    public static boolean printTerritory(@NotNull String territoryName, @NotNull List<Territory> territories) {
        Territory territory = territories.stream().filter(t -> t.getTerritoryName().equals(territoryName)).findFirst().orElse(null);
        if (territory != null) {
            System.out.println("======================================================================");
            territory.printAdjTerritories();
            System.out.println("======================================================================");
            return true;
        }
        return false;
    }

    public static boolean printTerritory(@NotNull int territoryIndex, @NotNull List<Territory> territories) {
        Territory territory = territories.stream().filter(t -> t.getTerritoryIndex() == territoryIndex).findFirst().orElse(null);
        if (territory != null) {
            System.out.println("======================================================================");
            territory.printAdjTerritories();
            System.out.println("======================================================================");
            return true;
        }
        return false;
    }

    @Nullable
    public static Territory findTerritory(String territoryName, @NotNull List<Territory> territories) {
        return territories.stream()
                .filter(territory -> territory.getTerritoryName().equals(territoryName))
                .findFirst().orElse(null);
    }

    @Nullable
    public static Territory findTerritory(int territoryIndex, @NotNull List<Territory> territories) {
        return territories.stream()
                .filter(territory -> territory.getTerritoryIndex() == territoryIndex)
                .findFirst().orElse(null);
    }

    public static void displayMap(String mapPath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(mapPath));
        } catch (IOException e) {
        }

        if (img != null) {
            final BufferedImage image = img;
            JPanel jPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics graphic) {
                    super.paintComponent(graphic);
                    graphic.drawImage(image, 0, 0, null);
                }
            };

            JFrame jFrame = buildFrame();
            jFrame.add(jPanel);
        }
    }

    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(1200, 850);
        frame.setVisible(true);
        return frame;
    }

}
