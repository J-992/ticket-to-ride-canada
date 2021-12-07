package controller;

/* Sources for this class:
 * http://www.java2s.com/Tutorial/Java/0240__Swing/OKcanceloptiondialog.htm
 *
 * This class contains methods to manage the
 * tickets including showing three tickets for
 * selection, checking tickets for completion,
 * and scoring tickets
 */

import model.City;
import model.Player;
import model.Route;
import model.Ticket;
import view.CardPanel;

import java.util.*;
import javax.swing.*;

public class TicketController {
	// deals tickets at the start of the game
	@SuppressWarnings("unused")
	public void dealStartGameTickets() {
		for (Player p : TTRController.players) {
			showTicketSelectionDialogue(true);
			TTRController.nextTurn();
		}
		TTRController.turnNumber = 1;
	}

	// shows the option pane with ticket choices
	// that the player has to choose from
	private void goThroughDialogue(Ticket[] choices, boolean isFirst) {

		boolean hasSelected = false;
		int size = choices.length;
		// while the user has not selected a ticket
		while (!hasSelected) {
			// initialize gui elements for joptionpane
			JCheckBox[] checkBoxes = new JCheckBox[size];

			Object[] parameters = { TTRController.getCurrentPlayer().getName() + " select at least " + (isFirst ? 2 : 1)
					+ " ticket(s)! ", checkBoxes };

			for (int i = 0; i < size; i++) {
				checkBoxes[i] = new JCheckBox(choices[i].toString());
			}

			// Show optionpane options
			JOptionPane.showMessageDialog(TTRController.frame, parameters);
			// count number of selected tickets
			int numSelected = 0;
			for (JCheckBox c : checkBoxes) {
				if (c.isSelected()) {
					numSelected++;
				}
			}
			// if its the first round then the players must select TWO tickets
			if (isFirst && numSelected < 2) {
				JOptionPane.showMessageDialog(TTRController.frame, "Choose 2 or more tickets");
			}
			// restart the process if the player wants to be a dumb and doesnt select a
			// ticket
			else if (numSelected < 1) {
				int i = JOptionPane.showConfirmDialog(TTRController.frame, "Choose 1 or more ticket(s)", "Continue?",
						JOptionPane.OK_CANCEL_OPTION);

				if (i == 2) {
					for (Ticket tix : choices) {

						TTRController.tickets.push(tix);
					}
					return;
				}
			} else {
				// add selected tickets to the player
				for (int i = 0; i < size; i++) {
					if (checkBoxes[i].isSelected()) {
						TTRController.getCurrentPlayer().addTicket(choices[i]);
					} else {
						// place unselected tickets at the bottom of the stack
						TTRController.tickets.insertElementAt(choices[i], TTRController.tickets.size() - 1);
					}
				}
				hasSelected = true;
			}
		}
		CardPanel.moreButtonPressing(false);
	}

	// shows player 3 tickets they have to choose
	public void showTicketSelectionDialogue(boolean isInitial) {

		int size = Math.min(TTRController.tickets.size(), 3);

		if (size <= 0) {
			JOptionPane.showMessageDialog(TTRController.frame, "No tickets left.");
			return;
		}

		Ticket[] choices = new Ticket[size];
		// initialize the tickets by "popping" them off the stack
		for (int x = 0; x < size; x++) {
			choices[x] = TTRController.tickets.pop();
		}

		goThroughDialogue(choices, isInitial);

	}

	// checks whether the ticket is complete and marks it for completion
	public boolean checkTicketComplete(Ticket t, Player p) {

		// HashSet is explained in TTRController

		HashSet<City> goneTo = new HashSet<>();
		City endPoint = t.getCity2();

		/*
		 * A LinkedList is basically a list of a data, "linked" together with pointers.
		 * As an example, Element A is "linked" by a pointer to Element B as shown:
		 * 
		 * [A] --> [B]
		 * 
		 * As such, they aren't stored as common values, but separate values linked
		 * together by one common thing, in this case city1/city2
		 * https://www.geeksforgeeks.org/data-structures/linked-list/
		 */
		Queue<City> queue = new LinkedList<>();
		queue.add(t.getCity1());
		// gets the enxt city in the queue
		// mark the city as visited
		while (!queue.isEmpty()) {

			City curr = queue.poll();
			goneTo.add(curr);

			for (Route r : curr.retrieveRoutesOwnedByPlayer(p)) {
				// switch depending on the destination city
				City c1 = r.getCity(0);
				City c2 = r.getCity(1);
				// if any cities are the destination then the ticket is complete
				if (c1.equals(endPoint) || c2.equals(endPoint)) {
					t.setDone(true);
					JOptionPane.showMessageDialog(TTRController.frame,
							"You finished a ticket: " + t.getCity1() + " --> " + t.getCity2(), "Notification",
							JOptionPane.INFORMATION_MESSAGE);

					p.setScore(p.getScore() + t.getValue());
					return true;
				}
				// add connecting city to queue
				if (!c1.equals(curr) && !goneTo.contains(c1)) {
					queue.add(c1);
				} else if (!goneTo.contains(c2)) {
					queue.add(c2);
				}
			}
		}

		return false;
	}

	// scores tickets when a route is added
	public void scoreTicketsOnRouteAdded(Player p) {
		for (Ticket ticket : p.getTickets()) {
			if (!ticket.isDone()) {
				checkTicketComplete(ticket, p);
			}
		}
	}

	// removes unfinished ticket points from the players
	// when the game ends
	public void totalScoreAtEndGame() {
		for (Player player : TTRController.players) {
			for (Ticket t : player.getTickets()) {
				if (!t.isDone()) {
					player.setScore(player.getScore() - t.getValue());
				}
			}
		}
	}
}
