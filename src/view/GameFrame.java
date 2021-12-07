package view;
//Lukas Bozinov

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private JMenuBar menubar = new JMenuBar(); // add other stuff
	private BoardPanel boardPanel = new BoardPanel(0, 0, 1400, 900);
	private ScorePanel scorePanel = new ScorePanel(1400, 150, 520, 180);
	private CardPanel cardPanel = new CardPanel(0, 900, 1400, 180);
	private PlayerPanel playerPanel = new PlayerPanel(1400, 180, 520, 1000);

	private JMenu extrasMenu = new JMenu("Extras");
	private JMenuItem backstoryMenu = new JMenuItem("Backstory");
	private JMenuItem aboutMenu = new JMenuItem("About");
	private JMenuItem instructionsMenu = new JMenuItem("Instructions");
	private JMenuItem[] extrasMenuContents = { backstoryMenu, aboutMenu, instructionsMenu };

	public GameFrame() {
			
		setTitle("Ticket to Ride: CANADA!"); // set title of the window

		setLayout(null); // CHOOSE A LAYOUT LATER

		setSize(1920, 1080); // SET SIZE OF WINDOW

		setDefaultCloseOperation(EXIT_ON_CLOSE); // terminates the program when the window is closed

		// creates all the panels held in the frame
		// SET ALL BOUNDS HERE
		// bounds go (x,y,width,height)

		for (JMenuItem item : extrasMenuContents) {
			extrasMenu.add(item);
        }

		
		menubar.add(extrasMenu);
		setJMenuBar(menubar);
		
		add(boardPanel);
		add(cardPanel);
		add(scorePanel);
		add(playerPanel);

		// set the window to be visible
		setVisible(true);
	}
	// ANY GETTERS/SETTERS CAN GO HERE IF NEEDED

	public JMenu getExtras() {
		return extrasMenu;
	}

	public JMenuItem getBackstoryMenu() {
		return backstoryMenu;
	}

	public JMenuItem getAboutMenu() {
		return aboutMenu;
	}

	public JMenuItem getInstructionsMenu() {
		return instructionsMenu;
	}

	public JMenuItem[] getExtrasMenuContents() {
		return extrasMenuContents;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

}