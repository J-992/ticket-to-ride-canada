package view;

import controller.TTRController;
import model.CardColour;
import model.Player;
import model.Ticket;
import model.TrainCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 
 *         <p>
 *         This class is the Player panel that has - labels for its titles (6) -
 *         a list of Tickets - (along with a scroll pane) - arrays to hold the
 *         cards - arrays to hold their amounts, - the amount of trains and -
 *         buttons to claim a route - button to proceed to the next turn -
 *         updates current player
 *         <p>
 *         <p>
 *         LINK FOR SCROLL PANE DOCUMENTATION
 * @{@link https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html}
 */

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {

	// fields (jaffer)

	// Create titles for the PlayerPanel
	private final JLabel panelLabel = new JLabel(); 		// PLAYER PANEL TITLE
	private final JLabel playerNameLabel = new JLabel(); 	// PLAYER NAME TITLE
	private final JLabel playerColourLabel = new JLabel(); 	// PLAYER COLOUR TITLE

	// Create titles and labels for the ticket info area of PlayerPanel
	private final JLabel ticketLabel = new JLabel("TICKETS"); 			// TICKET LABEL
	private final JLabel trainCardTitle = new JLabel("TRAIN CARDS"); 	// TRAIN CARD LABEL
	private final static JLabel numTrainsLeftLabel = new JLabel(); 		// NUMBER OF TRAINS

	// Create the other elements to display on PlayerPanel
	private final static JTextArea ticketTextArea = new JTextArea();				// LIST OF TICKETS
	private final JScrollPane ticketScrollPane = new JScrollPane(ticketTextArea);	// SCROLL PANE

	// Create an ArrayList to hold the ticket cards
	private final ArrayList<Ticket> trainCardsArray = new ArrayList<>();; 	// ARRAY TO HOLD TICKET CARDS
	static CardColour[] cardColour = CardColour.values(); 					// Create an array by pulling values from the enumerator class: CardColour

	// Enumerator
	private final static TrainCard[] cardAmountArray = new TrainCard[cardColour.length]; // holds the amount of each type of card

	private final static JButton claimRouteButton = new JButton("CLAIM ROUTE");	// USED TO CLAIM ROUTE
	private final static JButton proceedButton = new JButton("NEXT TURN"); 		// USED TO PROCEED TO NEXT PLAYER

	public PlayerPanel(int x, int y, int width, int height) {

		setBounds(1400, 180, 520, 900); // Sets bounds for PlayerPanel

		setLayout(null); // No layout manager, elements are set by coordinates

		// Start of section: Custom and coordinate-based element placement with use of JLabel alignment methods
		int currentYAxisPlacement = 0;

		currentYAxisPlacement += 520 / 20;
		panelLabel.setBounds(0, currentYAxisPlacement, width, 900 * 3 / 160);
		panelLabel.setHorizontalAlignment(JLabel.CENTER);

		currentYAxisPlacement += 900 * 3 / 160 * 2;
		playerNameLabel.setBounds(520 / 20, currentYAxisPlacement, 520 - 520 / 20 * 2 / 2, 900 * 3 / 160);

		playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
		

		playerColourLabel.setBounds(width / 2, currentYAxisPlacement, 520 - 520 / 20 * 2 / 2, 900 * 3 / 160);
		playerColourLabel.setHorizontalAlignment(JLabel.CENTER);

		currentYAxisPlacement += 900 * 3 / 160 * 2;
		ticketLabel.setBounds(520 / 20, currentYAxisPlacement, 520 - 520 / 20 * 2, 900 * 3 / 160);

		ticketTextArea.setPreferredSize(new Dimension(520 - 520 / 20 * 2 - 18 * 2, 900 * 3 / 160 * 20));
		ticketTextArea.setEditable(false);

		currentYAxisPlacement += 900 * 3 / 160 * 2;
		ticketScrollPane.setBounds(520 / 20, currentYAxisPlacement, 520 - 520 / 20 * 2 - 18, 900 * 3 / 160 * 15);

		currentYAxisPlacement += 900 * 3 / 160 * 17;
		trainCardTitle.setBounds(50, currentYAxisPlacement, 520 - 520 / 20 * 2 / 2, 900 * 3 / 160);
		// End of section

		// Add and make titles, labels, and additional elements visible on the PlayerPanel
		add(panelLabel);
		add(playerNameLabel);
		add(playerColourLabel);
		add(ticketLabel);
		add(ticketScrollPane);
		add(trainCardTitle);

		// Create the cardAmountArray list
		CardColour[] values = CardColour.values();

		for (int i = 0; i < cardAmountArray.length; ++i) {

			cardAmountArray[i] = new TrainCard(values[i]);

			// cardAmountArray will contain the number of each card in the label
			cardAmountArray[i].setBounds(520 / 20, currentYAxisPlacement + 900 * 3 / 160 * 2 * (i + 1),
					520 / 20 * 4, 900 * 3 / 160);
			cardAmountArray[i].setHorizontalAlignment(JLabel.RIGHT);
			add(cardAmountArray[i]);

			// Create a new JLabel to display the card colours
			JLabel cardColour = new JLabel(values[i].toString());
			cardColour.setBounds(100 / 20 * 2, currentYAxisPlacement + 900 * 3 / 160 * 2 * (i + 1), 520 / 20 * 4,
					900 * 3 / 160);
			cardColour.setHorizontalAlignment(JLabel.RIGHT);
			add(cardColour);

		}

		// Custom and coordinate-based element placement with use of JLabel alignment methods
		numTrainsLeftLabel.setBounds(260, currentYAxisPlacement, 520 - 520 / 20 * 2 / 2, 900 * 3 / 160);
		add(numTrainsLeftLabel);

		currentYAxisPlacement += 900 * 3 / 160 * 10;
		claimRouteButton.setBounds(260, currentYAxisPlacement, 200, 900 * 3 / 160 * 4);
		claimRouteButton.setBackground(Color.GREEN);
		add(claimRouteButton);

		currentYAxisPlacement += 900 * 3 / 160 * 7;
		proceedButton.setBounds(260, currentYAxisPlacement, 200, 900 * 3 / 160 * 6);
		proceedButton.setBackground(Color.BLUE);
		proceedButton.setForeground(Color.WHITE);
		add(proceedButton);

	}

	// Method for updating data on next turns for different players
	public void nextTurn(Player current) {

		// Updates the player name
		playerNameLabel.setText("name:\t\tPlayer " + current.getPlayerColour().getPlayerColourNum());

		// Updates the player colour
		playerColourLabel.setText("colour:\t\t" + current.getPlayerColour());

		// Updates the number of trains (calls the update method) shown on the player panel for the next player's turn
		updateTrainScoreAndData();

	}

	// Getters and setters

	public JScrollPane getTicketScrollPane() {
		return ticketScrollPane;
	}

	public ArrayList<Ticket> getTrainCardsArray() {
		return trainCardsArray;
	}

	public TrainCard[] getCardAmountArray() {
		return cardAmountArray;
	}

	public static JButton getClaimRouteButton() {
		return claimRouteButton;
	}

	public static JButton getProceedButton() {
		return proceedButton;
	}

	public void setCardColour(CardColour[] cardColour) {
		PlayerPanel.cardColour = cardColour;
	}

	// Method to update train and score data for each player
	public static void updateTrainScoreAndData() {

		numTrainsLeftLabel.setText("# of trains left:    " + TTRController.getCurrentPlayer().getnumTrainsLeft());
		for (int x = 0; x < cardAmountArray.length; ++x) {
			cardAmountArray[x].setText(Integer.toString(TTRController.getCurrentPlayer().getNumCardsOfColour(x)));
		}

	}

	// cnt'd Getters and setters
	public JLabel getPanelLabel() {
		return panelLabel;
	}

	public JLabel getPlayerNameLabel() {
		return playerNameLabel;
	}

	public JLabel getPlayerColourLabel() {
		return playerColourLabel;
	}

	public JLabel getTicketLabel() {
		return ticketLabel;
	}

	public JLabel gettrainCardTitle() {
		return trainCardTitle;
	}

	public JLabel getnumTrainsLeftLabel() {
		return numTrainsLeftLabel;
	}

	public JTextArea getTicketTextArea() {
		return ticketTextArea;
	}

	// Method to update each player's ticket data
	public static void updateTicketData() {
		ticketTextArea.setText("");
		for (Ticket ticket : TTRController.getCurrentPlayer().getTickets()) {
			ticketTextArea.append(ticket.toString() + "\n");
		}
	}

}