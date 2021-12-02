package model;

import java.util.*;

public class Player{

    private final String name;
    private final PlayerColour playerColour;
    private final ArrayList<Ticket> tickets;
    private final ArrayList<Route> claimedRoutes;
    
    private int totalCards;
    private final int[] numCardsOfColour;
    private int numTrains;
    private int score;
    
    public Player (String name, PlayerColour playerColour) {
        
        this.name = name;
        this.playerColour = playerColour;
        this.tickets = new ArrayList<>();
        this.claimedRoutes = new ArrayList<>();
        this.numCardsOfColour = new int[CardColour.values().length-1];
        this.numTrains = 45;
        this.totalCards = this.score = 0;
        
    }
    
    public ArrayList<Route> getClaimedRoutes () {
        return claimedRoutes;
    }

    public String getName() {
        return name;
    }
    
    public int getScore () {
        return score;
    }

    
    public void removeCards (int cardColour, int numberRemoved) {
        totalCards -= totalCards;
        numCardsOfColour[cardColour] -= numberRemoved;
    }
    
    public int getTotalCards () {
        return totalCards;
    }
    
    public ArrayList<Ticket> getTickets(){
        return tickets;
    }
    
    
    public int getNumTrains () {
        return numTrains;
    }
    
    public void setNumTrains (int numTrains) {
        this.numTrains = numTrains;
    }
    
    public int getNumCardsOfColour (int index) {
        return numCardsOfColour[index];
    }
    
    public PlayerColour getPlayerColour () {
        return playerColour;
    }
    
    @Override
    public String toString () {
        return "Player{"+
                "name='"+name+'\''+
                ", playerColour="+playerColour+
                ", numTrains="+numTrains+
                ", score="+score+
                '}';
    }
    
}
