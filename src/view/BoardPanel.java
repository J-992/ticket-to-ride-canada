package view;

import model.City;
import model.Route;

import static controller.FileImportController.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    
    private final JLabel gameBoardImage;
    
    public BoardPanel (int x, int y, int width, int height) {
       
        setBounds(x, y, width, height);
        gameBoardImage = new JLabel(new ImageIcon("allFiles/board.png"));
        gameBoardImage.setBounds(0,0, this.getWidth(), this.getHeight());
        add(gameBoardImage);

        // add the cities and routes to the game board
        for (City city : listOfCities) {
            gameBoardImage.add(city);
        }
        for (Route route : listOfRoutes) {
            gameBoardImage.add(route);
        }
    
        setVisible(true);
    
    }
    
    public JLabel getGameBoardImage () {
        return gameBoardImage;
    }
    
}
