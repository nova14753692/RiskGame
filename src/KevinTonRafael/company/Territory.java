package KevinTonRafael.company;
import org.jetbrains.annotations.NotNull;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Territory {

    private String territoryName;
    private int territoryIndex;
    private boolean isOccupied;
    private Player occupiedBy;
    private int numbOfArmy;

    private List<Territory> adjTerritories;

    public Territory(@NotNull String territoryName, int territoryIndex) {
        this.territoryName = territoryName;
        this.isOccupied = false;
        this.occupiedBy = null;
        this.numbOfArmy = 0;
        this.territoryIndex = territoryIndex;
        adjTerritories = new ArrayList<>();
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public int getTerritoryIndex() {
        return territoryIndex;
    }

    public List<Territory> getAdjTerritories() {
        return adjTerritories;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Player getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(@NotNull Player occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public int getNumbOfArmy() {
        return numbOfArmy;
    }

    public void setNumbOfArmy(int numbOfArmy) {
        this.numbOfArmy = numbOfArmy;
    }

    public void printAdjTerritories() {
        System.out.print(territoryName);
        if (occupiedBy != null) System.out.print(" - " + occupiedBy.getPlayerName());
        System.out.println(" has Adjacent territories: ");
        System.out.print("====> ");
        adjTerritories.forEach(t -> {
            System.out.print(t.getTerritoryName());
            if (t.getOccupiedBy() != null) System.out.print(" - " + t.getOccupiedBy().getPlayerName());
            System.out.print(": " + t.getNumbOfArmy());
            if (adjTerritories.indexOf(t) < adjTerritories.size() - 1) System.out.print(", ");
        });
        System.out.println();
    }

}
