

/**
 * the color code creates a destination card
 * 
 * @author Gili Rusak
 * 
 * @version 6 May 2013
 */
public class DestinationCard
{
    //instance variables
    private City city1;
    private City city2;
    private int numPoints;
    
    /**
     * creates a destination card object
     * 
     * @param     one     first city
     * @param     two     second city
     * @param     points     how many points the connection is worth
     */
    public DestinationCard(City one, City two, int points)
    {
        city1 = one;
        city2 = two;
        numPoints = points;
    }

    /**
     * returns the first city object
     * 
     * @return     city1     returns city object
     */
    public City getActualCity1(){
        return city1;
    }
    
    /**
     * returns the second city object
     * 
     * @return     city2      returns city object
     */
    public City getActualCity2(){
        return city2;
    }
    
    /**
     * returns the name of the first city
     * 
     * @return      city1.getName()     name of city 1
     */
    public String getCity1(){
        return city1.getName();
    }
    
    /**
     * returns the name of the second city
     * 
     * @return     city2.getName()     name of city 2
     */
    public String getCity2(){
        return city2.getName();
    }
    
    /**
     * returns the worth of the connection between cities
     * 
     * @return     numPoints     int worth of the connection
     */
    public int getPoints(){
        return numPoints;
    }
}
