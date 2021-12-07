package model;

/**
 * 
 * @author jayden chyn
 *
 */

public enum CardColour {

	// enumerators with corresponding images (card colour images)
	// CARDCOLOUR(image path)

	RAINBOW(0, "images/trainCardRainbow.png"), BLACK(1, "images/trainCardBlack.png"),
	BLUE(2, "images/trainCardBlue.png"), GREEN(3, "images/trainCardGreen.png"), ORANGE(4, "images/trainCardOrange.png"),
	PURPLE(5, "images/trainCardPurple.png"), RED(6, "images/trainCardRed.png"), WHITE(7, "images/trainCardWhite.png"),
	YELLOW(8, "images/trainCardYellow.png"), GRAY(0, null); // default colour

	// used to retrieve info on which card and the
	// card image (from image files) to display
	private final String imagePath;
	private final int num;

	// constructor for the card info
	CardColour(int num, String imagePath) {
		this.imagePath = imagePath;
		this.num = num;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return num;
	}

}