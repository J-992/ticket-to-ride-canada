package model;

import javax.swing.*;
import java.util.Arrays;
import utilities.Point;

/**
 * This is a template class that is used to
 * create Route objects with set that holds
 * its two terminal cities, along with its
 * length, colour, completion point (location
 * of the marker), the owner, and whether it
 * is a dual route
 */

@SuppressWarnings("serial")
public class Route extends JLabel {

	// fields
	public final City[] cities; // array for coordinate and name info on two cities
	public final int lengthRoute; // length of route
	public final CardColour colour; // colour of route (corresponds with card colours enum)
	public final Point endPoint; // endpoint location of marker
	public Player owner; // current player (player 1, 2, 3, 4)
	public boolean goesBothWays; // checks if the route is a dual route of trains

	// constructor class

	public Route(City city1, City city2, int lengthRoute, CardColour colour, Point endPoint, boolean goesBothWays) {

		setBounds(endPoint.getPointx(), endPoint.getPointy(), 15, 15);
		cities = new City[] { city1, city2 }; // has the value of the 2 terminal cities
		this.owner = null;
		/**
		 * ^^^^ sets the default value of owner to nothing (so that in actuality nothing
		 * can be claimed by nothing) - this is so that when we are checking if a route
		 * has been claimed, the default will have nothing claimed
		 */
		this.lengthRoute = lengthRoute;
		this.colour = colour;
		this.endPoint = endPoint;
		this.goesBothWays = goesBothWays;
	}

	// getters and setters

	// multiple cities
	public City[] getCities() {
		return cities;
	}

	// singular cities
	public City getCity(int count) {
		return cities[count];
	}

	public int getlengthRoute() {
		return lengthRoute;
	}

	public CardColour getColour() {
		return colour;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public Player getOwner() {
		return owner;
	}

	public boolean goesBothWays() { // validation code? none needed
		// if and else statement to return
		// true or false
		return goesBothWays;
	}

	public void setOwner(Player own) {
		this.owner = own;
		own.getClaimedRoutes().add(this);
	}

	public void setDualRoute(boolean dualRoute) {
		goesBothWays = dualRoute;
	}

	// toString
	@Override
	public String toString() {
		return "Route > " + "Cities: " + Arrays.toString(cities) + ", Length: " + lengthRoute + ", Colour: " + colour;
	}

	// compare routes to sort them by cities as pairs
	public int compareTo(Route route) {
		int compare = cities[0].getName().compareTo(route.cities[0].getName());
		// syntax is as follows:
		// variable = (expression) ? expressionIsTrue : expressionIsFalse;
		return compare != 0 ? compare : cities[1].getName().compareTo(route.cities[1].getName());
	}
}
