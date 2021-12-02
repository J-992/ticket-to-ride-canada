package model;

import java.util.ArrayList;

public class lengthRoute {
    
    private int length;
    private ArrayList<Route> path;
    
    public void clear () {
        this.length = 0;
        path.clear();
    }
    
    public void set (lengthRoute path) {
        this.length = path.length;
        this.path = path.path;
    }
    
    public lengthRoute () {
        length = 0;
        path = new ArrayList<>();
    }
    
    public int getLength () {
        return length;
    }
    
    public ArrayList<Route> getPath () {
        return path;
    }
    
    public void addLength (int additional, Route route) {
        length += additional;
        path.add(route);
    }
    
    
    public Player getOwner() {
        return path.get(0).getOwner();
    }
    
    
    
    @Override
    public String toString () {
        return Integer.toString(length);
    }
    
}
