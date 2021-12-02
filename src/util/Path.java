package util;

import model.Player;
import model.Route;

import java.util.ArrayList;

/**
 * @author jaffer
 *
 * this class is used to find the longest available path during the player's turn
 *
 */

public class Path {

    public int routeLength;
    public ArrayList<Route> longestPathList;


    //constructor
    public void Path() {
        routeLength = 0;
        longestPathList = new ArrayList<>();
    }

    //getters and setters
    public int getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(int routeLength) {
        this.routeLength = routeLength;
    }

    public ArrayList<Route> getLongestPathList() {
        return longestPathList;
    }

    //util classes

    //
    public void addRouteLength(int addLength, Route route){

        //adds the length of the train route to the total length
        routeLength += addLength;
        longestPathList.add(route);

    }

    //gets the owner of the path
    //(assigns the owner to the longest path)
    public Player getOwner(){
        return longestPathList.get(0).getOwner();
    }

    public void setLongestPathList(Path path){

        this.routeLength = path.routeLength;
        this.longestPathList = path.longestPathList;

    }

    //clears the value of the current longest path
    public void clearPathValue(){
        this.routeLength = 0;
        longestPathList.clear(); //clears the array that holds the path values
    }

}
