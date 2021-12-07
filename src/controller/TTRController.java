package controller;

import model.*;
import view.GameFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.*;
import view.*;
import utilities.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;

public class TTRController implements ActionListener {

	// create the GameFrame portion of the board game
	public static GameFrame frame;

	// implement all the controllers for use in this one big controller
	public static TrainCardController TrainCardController;
	public static TicketController ticketController;
	public static RouteController routeController;

	// implement all of the data sets that have been filled thus far
	public static ArrayList<Route> routes;
	public static HashSet<Route> availableRoutes;
	public static ArrayList<City> cities;
	public static Stack<Ticket> tickets;

	// create three new datasets
	public static Stack<TrainCard> trainCardDeck = new Stack<>(); // deck of train cards
	public static Stack<TrainCard> trainCardDiscards = new Stack<>(); // trash pile for used cards
	public static ArrayList<TrainCard> shownCards = new ArrayList<>(); // cards displayed at the bottom of the screen

	// set the required constants like turn number, and end game conditions
	public static int turnNumber = 1;
	public static int endGameTurn = 1;
	public static boolean isEndGame = false;

	// create data structures for the amount of players and whose turn it is
	public static Player[] players;
	public static int playerTurn;

	// runs the game (but technically doesn't because TTRApp.java does that)
	@SuppressWarnings("unchecked")
	public TTRController() {

		FileImportController.execute(); // import all of the files in the files foler
		// System.out.println(players); (from debugging)

		// create players and assign their names
		createPlayers();

		// plays background music
		backgroundSound();

		// make a "clone" of the routes that were imported in FileImportController
		routes = (ArrayList<Route>) FileImportController.listOfRoutes.clone();

		/*
		 * A HashSet holds a set of objects. However, the unique thing about HashSets is
		 * that they internally manage an array and the store the data using an index
		 * that is relative to the "hash code" of the object. It can take almost any
		 * unique elements and store them efficiently.
		 */

		// creates a new hash set using the routes we just cloned
		availableRoutes = new HashSet<>(routes);

		// also makes a clone of the cities and tickets from FileImportController
		cities = (ArrayList<City>) FileImportController.listOfCities.clone();
		tickets = (Stack<Ticket>) FileImportController.pileOfTickets.clone();

		// shuffles the deck of tickets
		Collections.shuffle(tickets);

		// creates the controller that will help move along the rest of the game
		TrainCardController = new TrainCardController();
		ticketController = new TicketController();
		routeController = new RouteController();

		// create the train cards and game frame to hold the images and JLabels
		trainCardDeck = TrainCardController.createTrainCards();
		frame = new GameFrame();

		// setup all the action listeners
		setupActionListeners();

		// start the game!
		beginNewGame();
	}

	// this method runs the beginning of the TTR game
	public void beginNewGame() {

		// set icons for the routes to null (for now)
		for (Route r : routes) {
			r.setIcon(null);
		}

		// initialize the constants that we need for the game to run
		playerTurn = 0;
		turnNumber = 1;
		endGameTurn = 1;
		isEndGame = false;

		// deal the cards out to each player, ask them to pick their tickets, and "flip"
		// the five cards shown at the bottom of the card panel
		TrainCardController.dealTrainCards();
		TrainCardController.flipCards();
		ticketController.dealStartGameTickets();

		// enable the controls and begin the game with a starting message
		CardPanel.moreButtonPressing(true);
		JOptionPane.showMessageDialog(frame, "Player 1 starts the game.");

	}

	// this method creates the player objects that will be playing the game
	public static void createPlayers() {

		players = new Player[4]; // creates the players
		PlayerColour[] clrValues = PlayerColour.values(); // assigns colour values

		// gives each player a name such as "Player 1, Player 2", etc.
		for (int count = 1; count <= players.length; ++count) {
			players[count - 1] = new Player("Player " + count, clrValues[count]);
		}

	}

	// this method runs everything required when the game ends
	public static void endGame() {

		// adds up the score at the end of the game
		ticketController.totalScoreAtEndGame();

		// creates an arrayList of people with the most/least score
		ArrayList<Player> highest = new ArrayList<>();
		highest.add(players[0]);

		// EXTRA FEATURE
		// set an array list with the names of the longest route owners
		ArrayList<Object> para = new ArrayList<>();
		para.add("Longest route owner(s): ");

		// create an array list with the longest paths of a player
		ArrayList<Route> longestPaths = new ArrayList<>();

		// runs a loop to get the longest paths ranked for each player
		for (Path path : TTRController.routeController.getLongestContinuousPathOwners()) {

			Player player = path.getOwner();
			player.setScore(player.getScore() + 10);
			para.add(player.getName());
			longestPaths.addAll(path.getLongestPathList());

		}

		// shows the notification of who had the longest route
		JOptionPane.showMessageDialog(frame, para.toArray(), "Bonus", JOptionPane.INFORMATION_MESSAGE);
		TTRController.frame.getBoardPanel().repaint();

		// creates a loop that gets the score of each player and ranks it in the
		// arrayList "highest"
		for (int i = 1; i < players.length; i++) {
			Player p = players[i];

			if (p.getScore() == highest.get(0).getScore()) {
				highest.add(p);

			} else if (p.getScore() > highest.get(0).getScore()) {
				highest.clear();
				highest.add(p);
			}
		}

		// runs this code if there's a tie
		if (highest.size() > 1) {
			StringBuilder msg = new StringBuilder("There has been a tie between: ");
			for (Player p : highest) {
				msg.append(p.getName()).append(" ");
			}

			JOptionPane.showMessageDialog(frame, msg.toString());

			// runs this code if there's a clear winner
		} else {
			JOptionPane.showMessageDialog(frame, "Winner: " + highest.get(0).getName() + "!!!!");
		}

	}

	// set the action listeners for the buttons and menu items that we need
	private void setupActionListeners() {

		PlayerPanel.getProceedButton().addActionListener(this);
		PlayerPanel.getClaimRouteButton().addActionListener(this);
		frame.getBackstoryMenu().addActionListener(this);
		frame.getAboutMenu().addActionListener(this);
		frame.getInstructionsMenu().addActionListener(this);

	}

	// this method sets what happens when a player clicks the next turn button
	public static void nextTurn() {

		turnNumber++; // turn number increases
		playerTurn = playerTurn == 3 ? 0 : playerTurn + 1; // ternary operator (if playerTurn ==3 is false then 0,
															// if true, playerTurn+1
		// if it's time to end the game, run endGame()
		if (isEndGame) {
			if (endGameTurn == 3) {
				endGame();
				return;
				// otherwise, increase the endGameTurn constant
			} else {
				endGameTurn++;
			}

			// otherwise, run the check to end the game
		} else {
			whenToEndTheGame();
		}

		// re-enables the controls for the buttons
		CardPanel.moreButtonPressing(true);

		// declare who's turn is next up
		JOptionPane.showMessageDialog(frame, getCurrentPlayer().getName() + "'s turn", "Notification",
				JOptionPane.INFORMATION_MESSAGE);

	}

	// this method sets the conditions that must happen in order to end the game
	public static void whenToEndTheGame() {

		boolean playerPosRoute;

		// have a loop that checks if there's <=2 trains left OR enough turns have
		// passed & enough routes have been claimed
		for (Player p : players) {
			playerPosRoute = p.getnumTrainsLeft() < 45;
			if (p.getnumTrainsLeft() <= 2 || turnNumber / players.length >= 5 && playerPosRoute) {
				isEndGame = true;
				JOptionPane.showMessageDialog(frame, "Final Round!");
				return;
			}
		}

		// otherwise, conditions have not been fulfilled, therefore do not end the game
		isEndGame = false;

	}

	// get the current player who's turn it is at the moment
	public static Player getCurrentPlayer() {
		return players[playerTurn];
	}

	// this method sets what should happen when an actionListener is clicked
	@Override
	public void actionPerformed(ActionEvent e) {

		// if the ticket deck button is clicked, disable controls and stop showing the
		// ticket selection dialogue
		if (e.getSource() == CardPanel.getTicketDeckImageButton()) {

			ticketController.showTicketSelectionDialogue(false);
			CardPanel.moreButtonPressing(false);

			// if the claim route button is pressed, show the menu to pick a route
		} else if (e.getSource() == PlayerPanel.getClaimRouteButton()) {
			routeController.getChoiceOfRoute(getCurrentPlayer());

			// if the next turn button is clicked, proceed to the next turn
		} else if (e.getSource() == PlayerPanel.getProceedButton()) {
			nextTurn();

			// get the back story of the game if the menu button is clicked
		} else if (e.getSource() == frame.getBackstoryMenu()) {
			backstoryMenu();

			// get the about menu if the about button is clicked
		} else if (e.getSource() == frame.getAboutMenu()) {
			aboutMenu();

			// get the instructions on how to play the game if the button is clicked
		} else if (e.getSource() == frame.getInstructionsMenu()) {
			instructionMenu();

		}

	}

	// this method displays the instructions in a JOptionPane
	private void instructionMenu() {
		JOptionPane.showMessageDialog(frame,
				"\n\n " + "To Score Points:\n" + "	a) Claim a route between two cities on the map, \n"
						+ "	b) Complete a path between two cities based on your destination tickets, or\n"
						+ "	c) Completing the Longest Continuous Path of routes.\n\n" +

						"Game Construction:\n" + "	a) Each player gets 4 random train cards\n"
						+ "	b) There are 45 coloured trains total\n"
						+ "	c) Each player must select at least 2 tickets at the start of the game (up to 3) and at least 1 if they want to pick the ticket deck for their round\n\n"
						+

						"Gameplay:\n" + "	Players can only do the following things when their turn is active:\n"
						+ "	a) Draw Train Car Cards\n" + "	b) Claim a route\n" + "	c) Take Destination Tickets\n"
						+ "   *The game ends when one of the players reach 2 or less trains*\n\n" +

						"Drawing Train Cards:\n"
						+ "	a) Players are allowed to draw 2 train cards. Two from the face up cards OR 2 from the card deck OR one of each\n"
						+ "   b) If a rainbow card is chosen from the face up cards, that card is the only one the player can take during that turn.\n\n"
						+

						"Claiming Route(s):\n"
						+ "   a) Available routes that a players can claim is are shown in the drop-down menu");

	}

	// this method displays notes about the project
	private void aboutMenu() {
		JOptionPane.showMessageDialog(frame, "A few notes about this project:\n" + "- SO MANY NULLPOINTERS\n"
				+ "- index out of bounds is very annoying\n" + "- i never truly knew how much off-by-one errors suck\n"
				+ "- spent like 5+ hours trying to get gui to fit on the screen only to realize it was scaling\n"
				+ "- also random error occurred for a specific route and it made no sense until we forgot to import something to our class so that was annoying\n"
				+ "- and yeah thanks for playing!\n"
				+ "- also shoutout to javatpoint for re-teaching me how to use hashmaps - Lukas");

	}

	// this method displays the back story of the ticket to ride game
	private void backstoryMenu() {
		JOptionPane.showMessageDialog(frame,
				"On a blustery autumn evening five old friends met in the backroom of one of the city’s oldest and most private clubs. Each had\n"
						+ "traveled a long distance — from all corners of the world — to meet on this very specific day... October 2, 1900 — 28 years to the\n"
						+ "day that the London eccentric, Phileas Fogg accepted and then won a £20,000 bet that he could travel Around the World in 80 Days.\n"
						+ "When the story of Fogg’s triumphant journey filled all the newspapers of the day, the five attended University together. Inspired by\n"
						+ "his impetuous gamble, and a few pints from the local pub, the group commemorated his circumnavigation with a more modest excursion\n"
						+ "and wager – a bottle of good claret to the first to make it to Le Procope in Paris.\n"
						+ "Each succeeding year, they met to celebrate the anniversary and pay tribute to Fogg. And each year a new expedition (always more\n"
						+ "difficult) with a new wager (always more expensive) was proposed. Now at the dawn of the century it was time for a new impossible\n"
						+ "journey. The stakes: $1 Million in a winner-takes-all competition. The objective: to see which of them could travel by rail to the\n"
						+ "most cities in Canada — in just 7 days. The journey would begin immediately...\n"
						+ "Ticket to Ride is a cross-country train adventure. Players compete to connect different cities by laying claim to railway routes on a\n"
						+ "map of Canada.");
	}

	// this method plays background music while playing
	public void backgroundSound() {

		// audioinputstream documentation
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioInputStream.html
		File bMusic = new File("audio/song.wav");

		try {
			Clip music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(bMusic));
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}