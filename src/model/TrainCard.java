package model;

import javax.swing.JLabel;

/** 
 * 
 *
 *  This is a template class that is used to create TrainCard objects with its colour as a field
 *
 */
@SuppressWarnings("serial")
public class TrainCard extends JLabel {
    
    // fields
    private CardColour colour;

    // constructor method

    public TrainCard(CardColour colour) {
        this.colour = colour;
    }

    public CardColour getColour() {
        return colour;
    }

    public void setColour(CardColour colour) {
        this.colour = colour;
    }

    
}