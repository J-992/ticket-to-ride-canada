package model;

import java.util.*;

import controller.TTRController;
import view.*;

/**
 * Jaffer
 * This is a template class that is used to create
 * Player objects with name and colour fields; also
 * it contains lists for the player's tickets and
 * claimed routes, as well as an array for the amount
 * of cards of each colour the player has; finally
 * it also contains the number of trains the player has and their current score
 */

public class Player {


	//fields
	public final String name;
	public final PlayerColour playerColour;
	public final ArrayList<Ticket> tickets;
	public final ArrayList<Route> claimedRoutes;

	public int totalCards;
	public final int[] numCardsOfColour;
	public int numTrainsLeft;
	public int score;

	//constructor
	public Player(String name, PlayerColour playerColour) {

		this.name = name;
		this.playerColour = playerColour;
		this.tickets = new ArrayList<>();
		this.claimedRoutes = new ArrayList<>();
		
		this.numCardsOfColour = new int[CardColour.values().length];
		this.numTrainsLeft = 45;
		this.totalCards = this.score = 0;

	}

	//getters and setters
	public ArrayList<Route> getClaimedRoutes() {
		return claimedRoutes;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	//sets the score
	public void setScore(int score) {
		this.score = score;
		for (int i = 0; i < TTRController.players.length; i++) {
			ScorePanel.playerScoresLabel[i].setText("Player " + (i + 1) + ": " + TTRController.players[i].getScore());
        }
	}

	public void removeCards(int cardColour, int numRemoved) {
		totalCards -= totalCards;
		numCardsOfColour[cardColour] -= numRemoved;
	}

	public int getTotalCards() {
		return totalCards;
	}

	public ArrayList<Ticket> getTickets() {	
		return tickets;
	}
	
	public void addTicket (Ticket ticket) {
        tickets.add(ticket);
        PlayerPanel.updateTicketData();
    }

	public int getnumTrainsLeft(int numTrainsLeft) {
		return numTrainsLeft;
	}

	public void setnumTrainsLeft(int numTrainsLeft) {
		this.numTrainsLeft = numTrainsLeft;
	}

	public int getNumCardsOfColour(int index) {
		return numCardsOfColour[index];
	}

	public PlayerColour getPlayerColour() {
		return playerColour;
	}
	
	 public void addCard (TrainCard card) {
	        totalCards++;
	        numCardsOfColour[card.getColour().getValue()] += 1;
	        PlayerPanel.updateTrainScoreAndData();
	        
	    }

	@Override
	public String toString() {
		return "Player: '" + name + "', Colour: " + playerColour + ", # Trains left: " + numTrainsLeft + ", Score = " + score;
	}

	//utility method
	public int getnumTrainsLeft() {
		// TODO Auto-generated method stub
		return numTrainsLeft;
	}

}