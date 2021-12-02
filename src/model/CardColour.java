package model;

public enum CardColour {

	BLACK(0, "allFiles/trainCardBlack.png"), BLUE(1, "allFiles/trainCardBlue.png"),
	GREEN(2, "allFiles/trainCardGreen.png"), ORANGE(3, "allFiles/trainCardOrange.png"),
	PURPLE(4, "allFiles/trainCardPurple.png"), RED(5, "allFiles/trainCardRed.png"),
	WHITE(6, "allFiles/trainCardWhite.png"), YELLOW(7, "allFiles/trainCardYellow.png"),
	RAINBOW(8, "allFiles/trainCardRainbow.png");

	private final int value;
	private final String filepath;

	CardColour(int value, String filepath) {
		this.value = value;
		this.filepath = filepath;
	}

	public int getValue() {
		return value;
	}

	public String getFilepath() {
		return filepath;
	}

}
