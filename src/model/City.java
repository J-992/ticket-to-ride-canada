package model;

/**
 * @author jaffer razavi
 * //This is a template class that is used to
 * // create City objects with name and point (location) fields
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class City extends JLabel {

    public String name; //variable for name
    public Point point; //point x,y

    private ArrayList<Route> routes = new ArrayList<>();

    //constructor
    public City(String name, Point point) {
        this.name = name;
        this.point = point;
    }

    //getters and setters

	@Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
    
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    
    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    //toString

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", point=" + point +
                '}';
    }
}
