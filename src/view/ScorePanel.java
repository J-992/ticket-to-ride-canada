package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * This class is the Score panel that has: 
 * - a label for its title and 
 * - label arrays to hold the player names and their scores
 * 
 * @author lukas
 * @author john
 * 
 */

/**
 * 
 * @author lukas
 */

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

	// label for the title
	private final JLabel title;

	// label arrays to hold the player scores
	public final static JLabel[] playerScoresLabel = new JLabel[4];

	// ScorePanel Constructor
	public ScorePanel(int x, int y, int width, int height) {
		
		// Creates the score panel title label
		title = new JLabel("SCORE PANEL");

		setBounds(1400, 0, 520, 180); // set bounds for the ScorePanel
		setLayout(null); // set to null, placement is set manually with coordinates

		title.setSize(100, 25);
		title.setLocation(260 - title.getWidth() / 2, 30); 

		// Display the players score, starting at player 1
		for (int count = 0; count < 4; count++) { // 0, 1, 2, 3, 4
			 											
			// Initializes ScorePanel as Player x (1, 2, 3, 4) : 0
			playerScoresLabel[count] = new JLabel("Player " + (count + 1) + ": 0"); // print statement

			// Set bounds for the scores label
			playerScoresLabel[count].setBounds(140, 80 + (25 * count), 200, 30); // coordinates not finalized
			add(playerScoresLabel[count]);
		}

		/**
		 * - Display title - Display the players scores to the board
		 * 
		 * @author john
		 */

		add(title); // Add and make title visible on ScorePanel

	}

	// Draws a horizontal line to divide the ScorePanel and PlayerPanel
	public void lineBorder(Graphics g) {
		g.drawLine(100, 100, 10, 20); // coordinates not finalized
		g.setColor(Color.BLACK);

	}

}