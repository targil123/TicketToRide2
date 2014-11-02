import java.util.*;
import java.io.*;
import java.io.FileInputStream;

/**
 * creates a player cityList object
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class CityList
{
    // instance variables - replace the example below with your own
    public ArrayList<City> cities = new ArrayList<City>();
//////////////////////////////////////////////////////////////////////////
    /**
     * Constructor of CityLsit object
     */
    public CityList()
    {
        try{
            FileInputStream fstream = new FileInputStream
                ("Cities.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader
                (new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(!strLine.equals("")){
                    String[] str = strLine.split(" : ");
                    String [] coordinates = str[1].split("-");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    City c = new City(x,y,str[0]);
                    cities.add(c);
                }
            }

            in.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * returns x coordinate of a city
     * 
     * @param s city to find
     * @return int of x coordinate
     */
    public int getX(String s){
        for(City c: cities){
            if(c.getName().equals(s)) return c.getX();
        }
        return -1;
    }

    /**
     * returns x coordinate of a city
     * 
     * @param s city to find
     * @return int of x coordinate
     */
    public int getY(String s){
        for(City c: cities){
            if(c.getName().equals(s)) return c.getY();
        }
        return -1;
    }
    
    /**
     * checks if a point has a city around it
     * 
     * @param x int x coordinate to check
     * @param y int y coordinate to check
     * 
     * @return c City that contains a point
     */
    public City contains(int x, int y){
        for(City c: cities){
            if(Math.abs(c.getX()-x)<7&&Math.abs(c.getY()-y)<7) return c;
        }
        return null;
    }
}
