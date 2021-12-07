package view;

import model.City;
import model.Route;

import static controller.FileImportController.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    
	// Create a new label with new ImageIcon that is referenced to the board image 
	public static final JLabel boardPic = new JLabel(new ImageIcon("images/board.png"));
	
    public BoardPanel (int x, int y, int width, int height) {
       
        setBounds(0, 0, 1400, 900); // Sets bounds for the BoardPanel
        boardPic.setBounds(0,0, 1400, 900); // Sets bounds for the board images
        add(boardPic); // Adds and makes board image visible on the BoardPanel

        // Adds the cities and routes to the game board using for-each loops
        for (City city : listOfCities) {
        	boardPic.add(city);
        }
        for (Route route : listOfRoutes) {
        	boardPic.add(route);
        }
    
        // Sets elements to be visible
        setVisible(true); 
    
    }
    
    // Getter for board image
    public JLabel getGameBoardImage () {
        return boardPic;
    }
    
}