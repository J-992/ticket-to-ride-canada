package model;

/**
 * @author jaffer razavi
 * <p>
 * This is a template class that is used to
 * create Route objects with set that holds
 * - two terminal cities,
 * - along with its length,
 * - colour,
 * - completion point (location of the marker),
 * - the owner, and
 * - whether it is a dual route
 */


import javax.swing.*;

import controller.Point;

import java.util.Arrays;

@SuppressWarnings("serial")
public class Route extends JLabel {

    //fields
    public final City[] cities;
    public final int length; //length of route
    public final CardColour colour; //colour of route (corresponds with card colours)
    public final Point endPoint; //location of marker
    public Player owner; //if it is player 1/2/3/4 (owner of route)
    public boolean goesBothWays; //

    //constructor class

    public Route(City city1, City city2, int length, CardColour colour, Point endPoint, boolean goesBothWays) {
        cities = new City[]{city1, city2}; //has the value of the 2 terminal cities
        this.owner = null;
        /**   ^^^^
         * sets the default value of owner to nothing
         * (so that in actuality nothing can be claimed by nothing)
         *  - this is so that when we are checking if a route has been claimed, the default will have nothing claimed
         */
        this.length = length;
        this.colour = colour;
        this.endPoint = endPoint;
        this.goesBothWays = goesBothWays;
    }

    //getters and setters
    public City[] getCities() {
        return cities;
    }

    public int getLength() {
        return length;
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

    public boolean goesBothWays() {
        return goesBothWays;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setDualRoute(boolean dualRoute) {
        goesBothWays = dualRoute;
    }

    //toString
    @Override
    public String toString() {
        return "Route{" +
                "cities=" + Arrays.toString(cities) +
                ", length=" + length +
                ", colour=" + colour +
                '}';
    }

	public City getCity(int count) {
		// TODO Auto-generated method stub
		return cities[count];
	}
}