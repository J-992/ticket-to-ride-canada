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
 * @author Jaffer
 *
 * <p>
 * This class is the Player panel that has
 * - labels for its titles (6)
 * - a list of Tickets
 * - (along with a scroll pane)
 * - arrays to hold the cards
 * - arrays to hold their amounts,
 * - the amount of trains and
 * - buttons to claim a route
 * - button to proceed to the next turn - updates current player
 * <p>
 * <p>
 * LINK FOR SCROLL PANE DOCUMENTATION
 * @{@link https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html}
 */

//lol i don't think i need to comment a lot of this code cuz the description is in the name :P
    //still gonna comment tho lmao

    //i wanna post this project to github

public class PlayerPanel extends JPanel {

    //fields (jaffer)
    //its in the name lol

    //TODO labels for all titles
    private final JLabel panelLabel = new JLabel("PLAYER PANEL");    //PLAYER PANEL TITLE
    private final JLabel playerNameLabel = new JLabel();                  //PLAYER NAME TITLE
    private final JLabel playerColourLabel = new JLabel();                //PLAYER COLOUR TITLE
    //TODO ^^^ CONVERT THIS TO A PANEL or LABEL THAT SHOWS AN IMAGE OF THE COLOUR INSTEAD OF TEXT
    private final JLabel ticketLabel = new JLabel("TICKETS");        //TICKET LABEL
    private final JLabel trainCardLabel = new JLabel("TRAIN CARDS"); //TRAIN CARD LABEL
    private final JLabel numTrainsLabel = new JLabel();                   //NUMBER OF TRAINS

    //TODO scroll pane for list of tickets
    private final JTextArea ticketTextArea = new JTextArea();//LIST OF TICKETS
    private final JScrollPane ticketScrollPane = new JScrollPane(ticketTextArea);//SCROLL PANE

    //TODO arrays to hold train cards and their amounts
    private final ArrayList<Ticket> trainCardsArray = new ArrayList<>(); //ARRAY TO HOLD TICKET CARDS
    CardColour[] cardColour = CardColour.values();
    //array that stores the value of the different cardcolours from the cardcolour Enumerator
    private final TrainCard[] cardAmountArray = new TrainCard[cardColour.length]; //holds the amount of each tye of card

    //TODO buttons
    private final JButton claimRouteButton = new JButton("CLAIM ROUTE");//USED TO CLAIM ROUTE
    private final JButton proceedButton = new JButton("NEXT TURN"); //USED TO PROCEED TO NEXT PLAYER

    public PlayerPanel(int x, int y, int width, int height) {

        //TODO ASK FERNANDES IF I SHOULD INITIALIZE THE LABELS IN THE CONSTRUCTOR RATHER THAN AT THE BEGINNING OF THE PROGRAM


        setBounds(x, y, width, height);

        //x = 1400
        //y = 180
        //width = 520
        //height =  900

        setLayout(null); //no layout manager

        //setbounds for the different variables
        // keep playing around with the dimensions and stuff to get the placements right - jaffer
        panelLabel.setBounds(0, 26, 520, 17);
        playerNameLabel.setBounds(26, 6, 248, 17);
        playerColourLabel.setBounds(260, 6, 248, 17);
        trainCardLabel.setBounds(26, 6, 248, 17);
        ticketLabel.setBounds(26, 6, 496, 17);
        numTrainsLabel.setBounds(260, 6, 248, 17);
        ticketScrollPane.setBounds(26, 6, 478, 255);
        ticketTextArea.setBounds(width / 2, 34, 248, 17);
        ticketTextArea.setSize(new Dimension(460, 340)); /**see javadoc comment below*/
        ticketTextArea.setEditable(false); //makes sure scroll panel cannot be edited
        claimRouteButton.setBounds(260, 34, 248, 17);
        proceedButton.setBounds(260, 34, 248, 17);

        /**
         // refer to documentation for setSize vs setPreferredSize usage
         @{@link https://stackoverflow.com/questions/1783793/java-difference-between-the-setpreferredsize-and-setsize-methods-in-compone}
         @{@link https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html}
         */

        //set horizontal alignment (this centres the player title, the player name, and the player colour)
        panelLabel.setHorizontalAlignment(JLabel.CENTER);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerColourLabel.setHorizontalAlignment(JLabel.CENTER);

        //add labels to the panel
        add(panelLabel);
        add(playerNameLabel);
        add(playerColourLabel);
        add(ticketLabel);
        add(trainCardLabel);
        add(numTrainsLabel);

        //add scroll/text area to the panel
        add(ticketTextArea);
        add(ticketScrollPane);

        //add buttons to the panel
        add(claimRouteButton);
        add(proceedButton);

        //ARRAY TO HOLD AMOUNT OF CARDS + ADDS THE CARDS ONTO THE PLAYER PANEL
        for (int i = 0; i < cardAmountArray.length; i++) {

            cardAmountArray[i] = new TrainCard(cardColour[i]);

            // numTrainCards will contain the number of each card in the label
            cardAmountArray[i].setBounds(78, 46 + (i + 1), 104, 17);
            cardAmountArray[i].setHorizontalAlignment(JLabel.RIGHT);
            add(cardAmountArray[i]);

            // Create a new JLabel to display the card colour
            JLabel cardColour = new JLabel(this.cardColour[i].toString());
            cardColour.setBounds(52, 40 * (i + 1), 104, 17);
            cardColour.setHorizontalAlignment(JLabel.RIGHT);
            add(cardColour);

        }

    }

    /** code that updates the labels
     * when the game goes to the next player's turn,
     *  - Amount of trains must be updated
     *  - Player name + colour must be updated
     *  - Amount of tickets displayed must be updated
     */

    //This method updates the trains, player, and tickets diplayed for the enxt player turn

    public void nextTurn(Player current){

        //updates the player name
        playerNameLabel.setText("NAME:\t\tPlayer "+current.getPlayerColour().getValue());

        //updates the player colour
        playerColourLabel.setText("COLOUR:\t\t"+current.getPlayerColour());

        //updates the nummber of trains shown on the player panel for the 'next players' turn
        numTrainsLabel.setText("NUMBER OF TRAINS:"+TTRController.getCurrentPlayer().getNumTrains());

        //enhanced for loop displays the array that holds the amount of each card type
        /** Ex:
         *  Rainbow: 0
         *  Blue: 1
         *  Red: 4
         *  Yellow: 0
         *  etc...
         */
        for (TrainCard count : cardAmountArray){ //displays card amounts
            int i = 0; //counter
            ++i; //uses counter as count doesnt work to show the different indexes of the array
            cardAmountArray[i].setText(Integer.toString(TTRController.getCurrentPlayer().getNumCardsOfColour(i)));
            //sets the text of the card amount array
            //converts the int value of each card to a string before displaying it
            //also gets the number of cards of each colour from the player model class
        }

        //these lines of code update the text area where the current player's tickets are shown
        ticketTextArea.setText(""); //clears the area where tickets are shown

        //this enhanced for loop displays all the tickets the player has
        for (Ticket t : TTRController.getCurrentPlayer().getTickets()) {
            ticketTextArea.append(t.toString()+"\n");
        }

    }

    //getters and setters (not sure if i need them??)

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

    public JLabel getTrainCardLabel() {
        return trainCardLabel;
    }

    public JLabel getNumTrainsLabel() {
        return numTrainsLabel;
    }

    public JTextArea getTicketTextArea() {
        return ticketTextArea;
    }

    public JScrollPane getTicketScrollPane() {
        return ticketScrollPane;
    }

    public ArrayList<Ticket> getTrainCardsArray() {
        return trainCardsArray;
    }

    public TrainCard[] getCardAmountArray() {
        return cardAmountArray;
    }

    public JButton getClaimRouteButton() {
        return claimRouteButton;
    }

    public JButton getProceedButton() {
        return proceedButton;
    }

    public void setCardColour(CardColour[] cardColour) {
        this.cardColour = cardColour;
    }





}
