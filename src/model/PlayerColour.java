package model;


public enum PlayerColour {

    // PLAYERCOLOUR(number or rank, whatever you want to call it)
    WHITE(0), 
    RED(1), 
    GREEN(2), 
    BLUE(3),
    YELLOW(4);

    // used to retrieve info on which colour is 
    // selected for a player (player 1, 2, 3, 4)
    public final int playerColourNum;

    // constructor for players' colour info
    PlayerColour(int playerColourNum) {
        this.playerColourNum = playerColourNum;
    }

    public int getPlayerColourNum() {
        return playerColourNum;
    }

 
}