
/**
 * creates a city object
 * 
 * @author Gili Rusak 
 * @version 6 May 2013
 */
public class City
{
    //instance variables
    private int x;
    private int y;
    private String name;
    private CityList list;

    /**
     * creates a city object with set coordinates
     * 
     * @param     cityName     name of the city
     */
    public City(String cityName){
        list = new CityList();
        name = cityName;
        x=list.getX(cityName);
        y=list.getY(cityName);
    }

    /**
     * creates a city object
     * 
     * @param     xCoord     x coordinate of city
     * @param     yCoord     y coordinate of city
     * @param     cityName     name of city
     */
    public City(int xCoord,int yCoord,String cityName)
    {
        x = xCoord;
        y = yCoord;
        name = cityName;
    }

    /**
     * returns the name of the city
     * 
     * @return     name     string city name
     */
    public String getName(){return name;}

    /**
     * returns x coordinate of city
     * 
     * @return     x     x coordinate
     */
    public int getX(){return x;}

    /**
     * returns y coordinate of city
     * 
     * @return     y     y coordinate
     */
    public int getY(){return y;}

    /**
     * determines if two cities are the same city
     * 
     * @return     boolean     true if cities are the same, else false
     */
    public boolean equals(City c){
        if(name.equals(c.getName())) return true;
        return false;
    }
}
