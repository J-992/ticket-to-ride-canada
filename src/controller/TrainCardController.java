package controller;

import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.*;
import view.CardPanel;

import java.util.Collections;

public class TrainCardController {

	private int cardsBeingUsed = 0; // cards in use

	// create train card deck
	public Stack<TrainCard> createTrainCards() {

		Stack<TrainCard> cardDeck = new Stack<>();

		CardColour[] colourValues = CardColour.values();

		//create the coloured cards (non-rainbow)
		for (int i = 1; i < colourValues.length; ++i) {
			if (colourValues[i] != CardColour.GRAY) {
				for (int j = 0; j < 12; j++) {
					// create TrainCard object (no rainbow)
					cardDeck.push(new TrainCard(colourValues[i]));
					
				}
			}
		}

		// create the rainbow cards 
		for (int r = 0; r < 14; r++) {
			// create rainbow TrainCard object (14 times)
			cardDeck.push(new TrainCard(CardColour.RAINBOW));
		}
		//shuffles arraylist
		Collections.shuffle(cardDeck);
		return cardDeck;
	}
	//deals train cards
	public void dealTrainCards() {

		for (Player player : TTRController.players) {
			for (int i = 0; i < 5; i++) {
				player.addCard(TTRController.trainCardDeck.pop());
			}
		}
	}
	//adds the card being shown to the player if they click on it
	public void giveShownCard(int index) {

		TrainCard card = TTRController.shownCards.get(index);

		if (card == null) {
			return;
		}

		TTRController.getCurrentPlayer().addCard(card);
		replaceCardTaken(index);
		//if  rainbow card is selected from the cards shown
				// then the player only receives 1 rainbow card
		if (card.getColour() == CardColour.RAINBOW) {
			cardsBeingUsed = 0;
			CardPanel.moreButtonPressing(false);
		} else {
			cardsBeingUsed++;
			 CardPanel.ticketDeckImageButton.setEnabled(false);
			if (cardsBeingUsed == 2) {
				cardsBeingUsed = 0;
				CardPanel.moreButtonPressing(false);
			}
		}
	}
	//if the player clicks on the deck of cards
	public void giveDeckCard() {

		if (!TTRController.trainCardDeck.isEmpty()) {
			//tells the player what type of card they drew
			TrainCard card = TTRController.trainCardDeck.pop();
			JOptionPane.showMessageDialog(TTRController.frame, "You drew a " + card.getColour().toString() + " card!",
					"Notification", JOptionPane.INFORMATION_MESSAGE);

			TTRController.getCurrentPlayer().addCard(card);
			CardPanel.ticketDeckImageButton.setEnabled(false);
			cardsBeingUsed++;
		}

		if (cardsBeingUsed >= 2) {
			CardPanel.moreButtonPressing(false);
			cardsBeingUsed = 0;
		}
	}
	//flips 5 cards for the next turn
	public void flipCards() {
		TTRController.shownCards.clear();
		for (int i = 0; i < Math.min(TTRController.trainCardDeck.size(), 5); i++) {
			TTRController.shownCards.add(TTRController.trainCardDeck.pop());
		}
		//flips 5 cards for the next turn
		for (int i = 0; i < 5; i++) {
			CardPanel.faceUpTrainCards[i]
					.setIcon(new ImageIcon(TTRController.shownCards.get(i).getColour().getImagePath()));
		}
	}
	//checks for 3 rainbow cards
	public boolean checkRainbowCards() {
		int rcCounter = 0;
		for (TrainCard card : TTRController.shownCards) {
			if (card.getColour() == CardColour.RAINBOW) {
				rcCounter++;
			}
		}

		if (rcCounter == 3) {
			TTRController.trainCardDiscards.addAll(TTRController.shownCards);
			flipCards();
			return true;
		}

		return false;

	}

	//replaces cards that were previously taken from the stack
	@SuppressWarnings("unchecked")
	public void replaceCardTaken(int index) {
		//if the stack is empty then start using cards from the discard pile
		if (TTRController.trainCardDeck.isEmpty()) {
			TTRController.trainCardDeck = (Stack<TrainCard>) TTRController.trainCardDiscards.clone();
			TTRController.trainCardDiscards.clear();
		}

		if (!TTRController.trainCardDeck.isEmpty()) {
			TTRController.shownCards.set(index, TTRController.trainCardDeck.pop());
			if (checkRainbowCards()) {
				JOptionPane.showMessageDialog(TTRController.frame, "3 rainbow cards found. Re-picking.", "Notification",
						JOptionPane.INFORMATION_MESSAGE);
				cardsBeingUsed = 0;
			}
			for (int i = 0; i < 5; i++) {
				CardPanel.faceUpTrainCards[i]
						.setIcon(new ImageIcon(TTRController.shownCards.get(i).getColour().getImagePath()));
			}

		} else {
			//if there are no more cards then make the button unusable
			CardPanel.getfaceUpTrainCardButtons()[index].setEnabled(false);
			CardPanel.getfaceUpTrainCardButtons()[index].setIcon(null);
			CardPanel.getfaceUpTrainCardButtons()[index].setText("No cards left");
		}
	}
}
