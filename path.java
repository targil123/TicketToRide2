import java.util.*;
/**
 * creates a connection between two cities and their coordinates on the 
 * applet
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class path
{
    // instance variables
    private ArrayList<int[]> paths = new ArrayList<int[]>();
    private City c1;
    private City c2;
    private ArrayList<Integer> x = new ArrayList<Integer>();
    private ArrayList<Integer> y = new ArrayList<Integer>();

    /**
     * Constructor for objects of class path
     * 
     * @param     city1     first city
     * @param     city2     second city
     * @param     temp      array of the x coordinates
     * @param     temp2     array of the y coordinates
     */
    public path(City city1, City city2,ArrayList<Integer> temp,
    ArrayList<Integer> temp2)
    {
        c1 = city1;
        c2 = city2;

        for(int r : temp)
            x.add(r);

        for(int r : temp2)
            y.add(r);

    }

    /**
     * adds a set of x and y coordinates
     * 
     * @param     int1     adding an x coordinate
     * @param     int2     adding a y coordinate
     */
    public void addPath(int int1,int int2)
    {
        int [] p = new int[2];
        p[0] = int1;
        p[1] = int2;

        paths.add(p);
    }

    /**
     * returns a set of coordinates
     * 
     * @return     paths      returns an array list of coordinates
     */
    public ArrayList<int[]> getPaths(){
        return paths;
    }

    /**
     * adds an x coordinate to the path
     * 
     * @param     temp     adds an x coordinate
     */
    public void addX(ArrayList<Integer>temp){
        for(int r : temp)
            x.add(r);
    }

    /**
     * adds a y coordinate to the path
     * 
     * @param     temp     adds a y coordinate
     */
    public void addY(ArrayList<Integer>temp){
        for(int r : temp)
            y.add(r);
    }
    
    /**
     * returns the first city
     * 
     * @return     c1     first city
     */
    public City getCity1(){
        return c1;
    }
    
    /**
     * returns second city
     * 
     * @return     c2     second city
     */
    public City getCity2(){
        return c2;
    }
    
    /**
     * returns the x coordinate
     * 
     * @returns     x     x coordinate
     */
    public ArrayList<Integer> getX(){
        return x;
    }
    
    /**
     * returns the y coordinate
     * 
     * @returns     y     y coordinate
     */
    public ArrayList<Integer> getY(){
        return y;
    }
}

