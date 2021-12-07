package view;

import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.TTRController;

/**
 * 
 * This class is the Card panel that has: labels for its titles; - labels for
 * the ticket deck - labels for the Card deck - an array of Train cards for the
 * five face up cards
 * 
 * https://www.javatpoint.com/java-actionlistener
 * 
 * @author johnselvin
 */

@SuppressWarnings("serial")
public class CardPanel extends JPanel implements ActionListener {

	// Create labels for all titles used in the CardPanel
	private JLabel ticketDeckTitle = new JLabel("TICKET DECK"); // labels for deck of tickets
	private JLabel cardDeckTitle = new JLabel("CARD DECK"); // labels for deck of cards given
	private JLabel cardPanelTitle = new JLabel("CARD PANEL"); // displays the card panel

	// Create buttons for ticket deck and card deck
	public static JButton ticketDeckImageButton = new JButton();
	private static JButton cardDeckImageButton = new JButton();

	// Create buttons for the cards
	// Used to show the hand of the current player
	public static JButton[] faceUpTrainCards = new JButton[5];

	public CardPanel(int x, int y, int width, int height) {

		setBounds(0, 900, 1400, 180); // Sets bounds for the CardPanel
		setLayout(null); // set to null, placement is set manually with coordinates

		// setBounds for the titles
		ticketDeckTitle.setBounds(75, 5, 100, 30);
		cardDeckTitle.setBounds(250, 5, 100, 30);
		cardPanelTitle.setBounds(840, 5, 100, 30);

		// add and make the titles visible
		add(ticketDeckTitle);
		add(cardDeckTitle);
		add(cardPanelTitle);

		/**
		 * Add images to ticketDeck and cardDeck buttons.
		 * 
		 * @link: https://stackoverflow.com/questions/4801386/how-do-i-add-an-image-to-a-jbutton
		 */

		// Create ImageIcons with reference to the corresponding image filepaths
		ImageIcon ticketDeckPic = new ImageIcon("images/ticketDeck.png");
		ImageIcon cardDeckPic = new ImageIcon("images/cardBack.png");

		// Displays the ticket deck
		ticketDeckImageButton = new JButton(ticketDeckPic); // Creates buttons using images
		ticketDeckImageButton.setBounds(50, 75 - (75 / 2), 125, 75); // Sets bounds for the deck
		ticketDeckImageButton.setFocusable(false); // Disables the ability for the element to be focused
		ticketDeckImageButton.addActionListener(this); // Add an action listening operation for the current object
		add(ticketDeckImageButton); // Add and make the button(s) visible

		// Displays the card deck
		cardDeckImageButton = new JButton(cardDeckPic);
		cardDeckImageButton.setBounds(225, 75 - (75 / 2), 125, 75);
		cardDeckImageButton.setFocusable(false);
		cardDeckImageButton.addActionListener(this);
		add(cardDeckImageButton);

		// Displays the 5 face up cards
		for (int i = 1; i <= 5; i++) {
			faceUpTrainCards[i - 1] = new JButton();
			JButton faceUpCardButton = faceUpTrainCards[i - 1];

			faceUpCardButton.setBounds(200 + (205 * i), (int)(75 / 2), 125, 75);
			faceUpCardButton.setFocusable(false);
			faceUpCardButton.addActionListener(this);
			add(faceUpCardButton);
		}

	}

	// Method to set each button to be enabled
	public static void moreButtonPressing(boolean isEnabled) {
		for (int i = 0; i <= 4; i++)
			faceUpTrainCards[i].setEnabled(isEnabled);

		cardDeckImageButton.setEnabled(isEnabled);
		ticketDeckImageButton.setEnabled(isEnabled);

	}

	// Getters and setters
	public JLabel getCardPanelTitle() {
		return cardPanelTitle;
	}

	public void setCardPanelTitle(JLabel cardPanelTitle) {
		this.cardPanelTitle = cardPanelTitle;
	}

	public JLabel getTicketDeckTitle() {
		return ticketDeckTitle;
	}

	public void setTicketDeckTitle(JLabel ticketDeckTitle) {
		this.ticketDeckTitle = ticketDeckTitle;
	}

	public JLabel getCardDeckTitle() {
		return cardDeckTitle;
	}

	public void setCardDeckTitle(JLabel cardDeckTitle) {
		this.cardDeckTitle = cardDeckTitle;
	}

	public static JButton[] getfaceUpTrainCardButtons() {
		return faceUpTrainCards;
	}

	// Method to handle the actions of components
	public void actionPerformed(ActionEvent event) {

		// If the ticket deck is selected, display the ticket selection window
		if (event.getSource() == ticketDeckImageButton) {
			TTRController.ticketController.showTicketSelectionDialogue(false);
			// } else if (e.getSource()==cardDeckButton) {
			// TTRController.ticketController
		}

		// If the card deck is selected, give a card from the card deck
		if (event.getSource() == cardDeckImageButton) {
			TTRController.TrainCardController.giveDeckCard();
		}

		// If the shown cards are selected, give the cards from the shown card area
		for (int x = 0; x < faceUpTrainCards.length; x++) {
			JButton btn = faceUpTrainCards[x];
			if (event.getSource() == btn) {
				TTRController.TrainCardController.giveShownCard(x);
			}
		}

	}

	public static Object getTicketDeckImageButton() {
		return ticketDeckImageButton;
	}
}
