package controller;

/**
@author lukas bozinov 
 */

/* https://stackoverflow.com/questions/10768619/paint-and-repaint-in-java
 * https://www.geeksforgeeks.org/collections-sort-java-examples/
 */

import model.*;
import utilities.*;
import javax.swing.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.util.*;

import view.CardPanel;
import view.PlayerPanel;

public class RouteController {

	// this hashmap is used to store the values for how long a route is, and how
	// much score a route should
	// give to a player when it is claimed
	@SuppressWarnings("serial")
	public static HashMap<Integer, Integer> scorePerRouteTakenHashMap = new HashMap<>() {
		{
			put(1, 1);
			put(2, 2);
			put(3, 4);
			put(4, 7);
			put(5, 10);
			put(6, 15);
			put(7, 18);
		}
	};

	// this method shows the routes that players can claim in a list
	public ArrayList<Route> getValidRoutesForPlayers(Player player) {

		// creates an arraylist to store the available routes
		ArrayList<Route> routesAvailable = new ArrayList<>();

		// loops through all the routes to compile a list in the arrayList
		for (Route route : TTRController.availableRoutes) {

			if (route.getlengthRoute() <= player.getnumTrainsLeft()

					&& (route.getlengthRoute() <= player.getTotalCards() && route.getColour() == CardColour.GRAY

							|| route.getlengthRoute() <= player.getNumCardsOfColour(route.getColour().getValue())

									+ player.getNumCardsOfColour(CardColour.RAINBOW.getValue()))) {

				routesAvailable.add(route);

			}
		}

		return routesAvailable; // return the list of available routes to the player

	}

	// get the choice of route from the player
	public void getChoiceOfRoute(Player p) {

		// get that routes available arrayList that we had from the previous method
		ArrayList<Route> routesAvailable = getValidRoutesForPlayers(p);

		// if there are no routes, display a message saying that there are no routes
		if (routesAvailable.isEmpty()) {
			JOptionPane.showMessageDialog(TTRController.frame, "No routes are available. Transaction cancelled.");
			return;
		}

		// notifies a user that they should pick a route to claim
		Route route = (Route) JOptionPane.showInputDialog(TTRController.frame, "Pick a route to claim", "Claim Route",
				JOptionPane.QUESTION_MESSAGE, null, routesAvailable.toArray(), routesAvailable.get(0));

		// if they close the selection window
		if (route == null) {
			TTRController.frame.getBoardPanel().repaint(); // refresh gui
			return;
		}

		// if the cardcolour is default then get the trainchoice otherwise give back
		// null
		int[] numTrainCardsUsed = route.getColour() == CardColour.GRAY ? getTrainChoiceFromPlayer(p, route) : null;

		// update the game
		updateGame(p, route, numTrainCardsUsed);

		// add score based on the tickets
		TTRController.ticketController.scoreTicketsOnRouteAdded(p);

		// disable controls
		CardPanel.moreButtonPressing(false);

		// refresh gui
		TTRController.frame.getBoardPanel().repaint();

	}

	// this method updates the game elements to reflect the things that just
	// happened
	private void updateGame(Player player, Route route, int[] numTrainCardsUsed) {

		// add route colour value as a refernce throughout the rest of the program
		final int routeColourValue = route.getColour().getValue();

		// check how many trains are left
		player.setnumTrainsLeft(player.getnumTrainsLeft() - route.getlengthRoute());

		// create an arraylist for the used cards
		ArrayList<Object> param = new ArrayList<>();

		// show the cards used to complete the route
		param.add("Retreived");
		param.add(route.toString());
		param.add("Used:");

		// if the cardcolour isn't the default, find the length difference
		if (route.getColour() != CardColour.GRAY) {

			final int diff = player.getNumCardsOfColour(routeColourValue) - route.getlengthRoute();

			// if the difference isn't negative
			if (diff >= 0) {

				// remove the cards used and get the route colour displayed
				player.removeCards(routeColourValue, route.getlengthRoute());
				param.add(route.getColour().toString() + ": " + route.getlengthRoute());

				// if the difference is negative
			} else {

				param.add(route.getColour().toString() + ": " + player.getNumCardsOfColour(routeColourValue));
				player.removeCards(routeColourValue, player.getNumCardsOfColour(routeColourValue));

				param.add(CardColour.RAINBOW.toString() + ": " + (-diff));
				player.removeCards(CardColour.RAINBOW.getValue(), -diff);

			}

			//if card colour GRAY is chosen
		} else {

			CardColour[] values = CardColour.values();
			
			//fix the number of train cards used after claiming
			for (int x = 0; x < values.length; x++) {

				if (numTrainCardsUsed[x] == 0)
					continue;
				player.removeCards(x, numTrainCardsUsed[x]);
				param.add(values[x].toString() + ": " + numTrainCardsUsed[x]);

			}

		}

		//nice!
		JOptionPane.showMessageDialog(TTRController.frame, param.toArray(), "Nice", JOptionPane.INFORMATION_MESSAGE);

		//addthe route that was just claimed to "claimed routes"
		player.getClaimedRoutes().add(route);
		route.setOwner(player); //set the owner of that route to be the player
		player.setScore(player.getScore() + scorePerRouteTakenHashMap.get(route.getlengthRoute())); //get the length of the route and add it relative to score

		//check off the route that the player claimed on the board
		String colour = player.getPlayerColour().toString().toLowerCase();
		colour = Character.toUpperCase(colour.charAt(0)) + colour.substring(1);
		route.setIcon(new ImageIcon("images/checkmark" + colour + ".png"));

		//update trains and ticket data
		PlayerPanel.updateTrainScoreAndData();
		PlayerPanel.updateTicketData();

		//get used cards and push them onto the discarded cards stack
		for (int x = 0; x < player.getNumCardsOfColour(routeColourValue); ++x) {
			TTRController.trainCardDiscards.push(new TrainCard(route.getColour()));
		}

		//remove the route that was claimed from the list of available routes
		TTRController.availableRoutes.remove(route);

	}

	//this method gets the choice of trains from the player
	private int[] getTrainChoiceFromPlayer(Player p, Route r) {

		//create a new arraylist for the parameters
		ArrayList<Object> param = new ArrayList<>();
		
		//create a cardcolour array for the values of the colours
		CardColour[] values = CardColour.values();

		//print the statements needed on the JOptionPane
		param.add("Number of train cards needed: " + r.getlengthRoute());
		param.add("How many train cards will you use?");

		//format numbers and how they'll be entered
		NumberFormatter format = new NumberFormatter(NumberFormat.getInstance());

		//make sure no number higher than these can be entered (and don't let people enter invalid inputs
		format.setMinimum(0);
		format.setMaximum(2147483647);
		format.setAllowsInvalid(false);
		format.setCommitsOnValidEdit(true);

		//create an arraylist with textfields that users will fill with their inputs
		ArrayList<JFormattedTextField> input = new ArrayList<>();

		//ask about each colour of card with a separate text field for each of them
		for (int count = 0; count < values.length; ++count) {

			if (values[count] == CardColour.GRAY)
				continue;
			param.add(values[count].toString() + " train cards:");

			input.add(new JFormattedTextField(format));
			input.get(count).setText("0");
			param.add(input.get(count));

		}

		//create a new int array of the number of train cards that were used to claim a route
		int[] numTrainCardsUsed = new int[values.length];
		int totalChosenCards;
		
		//validate the amount of cards entered
		do {

			totalChosenCards = 0;
			Arrays.fill(numTrainCardsUsed, 0);

			JOptionPane.showMessageDialog(TTRController.frame, param.toArray(), "Train Cards",
					JOptionPane.QUESTION_MESSAGE);

			//check to see if the right amount was added to the text fields
			for (int i = 0; i < input.size(); ++i) {

				numTrainCardsUsed[i] = Integer.parseInt(input.get(i).getText());
				totalChosenCards += numTrainCardsUsed[i];

				//can't have less than the amount used
				if (p.getNumCardsOfColour(i) < numTrainCardsUsed[i]) {
					totalChosenCards = -1;
					break;
				}

			}
			//if i didn't enter the correct amount of cards
			if (totalChosenCards != r.getlengthRoute()) {
				JOptionPane.showMessageDialog(TTRController.frame, "Too many/little cards. Try again please!",
						"Notification", JOptionPane.ERROR_MESSAGE);
			}

			//keep the loop going while the chosen cards and length of the route are not equal
		} while (totalChosenCards != r.getlengthRoute());

		return numTrainCardsUsed;

	}

	//this method calculate the score that is given tom a player after claiming a route
	public int scoreRoutes(Player player) {

		int score = 0;
		for (Route r : player.getClaimedRoutes()) {
			if (r.getOwner() == player) {
				score += scorePerRouteTakenHashMap.get(r.getlengthRoute());
			}
		}

		return score;

	}

	//this method finds out who owns the longest path (connected by cities)
	@SuppressWarnings("serial")
	public ArrayList<Path> getLongestContinuousPathOwners() {

		ArrayList<Path> longestContinuousPathOwners = new ArrayList<>() {
			{
				for (int i = 0; i < PlayerColour.values().length; ++i) {
					add(new Path());
				}
			}
		};

		//create a new hashset to store where someone has gone (collectively)
		HashSet<Route> goneTo = new HashSet<>();

		//set a loop to check each city and pathway
		for (Route route : TTRController.routes) {

			if (goneTo.contains(route) || route.getOwner() == null)
				continue;

			//conduct a DEPTH FIRST SEARCH into which routes are the longest and which ones belong to which player
			Path path = new Path();
			depthFirstSearch(goneTo, path, route);

			int owner = route.getOwner().getPlayerColour().getPlayerColourNum();
			Path longestLength = longestContinuousPathOwners.get(owner); //find the owner of the path

			//sort between which path is longer
			if (longestLength.getRouteLength() <= path.getRouteLength()) {

				if (longestLength.getRouteLength() < path.getRouteLength()) {
					for (int x = 1; x < longestContinuousPathOwners.size(); ++x) {
						longestContinuousPathOwners.get(x).clearPathValue();
					}
				}

				longestContinuousPathOwners.get(owner).setLongestPathList(path); //set the longest path owner

			}

		}

		longestContinuousPathOwners.removeIf(path -> path.getRouteLength() == 0);

		return longestContinuousPathOwners;

	}

	//this method performs a depth first search on how to get the longest path (as seen in the above method)
	//we discussed this in computing club!
	private void depthFirstSearch(HashSet<Route> goneTo, Path path, Route currentRoute) {

		goneTo.add(currentRoute);
		path.addRouteLength(currentRoute.getlengthRoute(), currentRoute);

		for (int count = 0; count <= 1; ++count) {
			for (Route nextRoute : currentRoute.getCity(count).getRoutes()) {

				if (currentRoute.getOwner() == nextRoute.getOwner() && currentRoute != nextRoute
						&& !goneTo.contains(nextRoute)) {
					depthFirstSearch(goneTo, path, nextRoute);
				}

			}
		}

	}

}