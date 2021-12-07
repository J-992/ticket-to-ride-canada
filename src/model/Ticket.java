package model;

/*
 *			This is a template class that is used to create Ticket
 *         objects with set that holds its - two terminal cities, - along with
 *         its value - whether it is completed
 */

public class Ticket {

	public City city1;
	public City city2; // the 2 terminal cities
	public int value; // # of points the ticket is worth?
	public boolean isDone; // if the route is completed or not

	// constructor method
	public Ticket(City city1, City city2, int value, boolean isDone) {
		this.city1 = city1;
		this.city2 = city2;
		this.value = value;
		this.isDone = isDone;
	}

	// getters and setters
	public City getCity1() {
		return city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean done) {
		isDone = done;
	}

	// toString
	@Override
	public String toString() {
		return "Ticket > " + "From: " + city1.getName() + ", To: " + city2.getName() + ", Score Value: " + value;
	}
}