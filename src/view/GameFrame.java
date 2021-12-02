package view;
//Lukas Bozinov

import javax.swing.JFrame;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private JMenuBar menubar = new JMenuBar();  //add other stuff
	private BoardPanel boardPanel = new BoardPanel(0, 0, 1400, 900);
    private ScorePanel scorePanel = new ScorePanel(1400,0,520,180);
    private CardPanel cardPanel = new CardPanel();
    private PlayerPanel playerPanel = new PlayerPanel();

	public GameFrame() {
		setTitle("Ticket to Ride: CANADA!"); // set title of the window

		setLayout(null); // CHOOSE A LAYOUT LATER

		setSize(1920, 1080); // SET SIZE OF WINDOW

		setDefaultCloseOperation(EXIT_ON_CLOSE); // terminates the program when the window is closed

		// creates all the panels held in the frame
		cardPanel = new CardPanel();
		playerPanel = new PlayerPanel();

		// SET ALL BOUNDS HERE
		// bounds go (x,y,width,height)
		cardPanel.setBounds(0, 900, 1400, 180); //towards bottom
		add(cardPanel);


		add(boardPanel);
		add(scorePanel);

		playerPanel.setBounds(1400, 180, 520, 900); //top right
		add(playerPanel);
		
		// set the window to be visible
		setVisible(true);
	}

	// ANY GETTERS/SETTERS CAN GO HERE IF NEEDED

}
