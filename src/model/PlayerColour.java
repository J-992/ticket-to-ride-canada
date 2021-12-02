package model;

public enum PlayerColour {
	BLUE(0), GREEN(1), RED(2), YELLOW(3);

	private final int val;

	PlayerColour(int val) {
		this.val = val;
	}

	public int getValue() {
		return val;
	}
}
