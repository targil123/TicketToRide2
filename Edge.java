
/**
 * creates an edge between two cities
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class Edge
{
    //instance variables
    private City city1;
    private City city2;
    private Color color;
    private int claimed;//-1 if not claimed
    private int length;
    private boolean isFerry;
    private int isMountain;//0 if not mountain 

    /**
     * creates an unclaimed edge between two cities
     * 
     * @param     c1     first city
     * @param     c2     second city
     */
    public Edge(City c1, City c2){
        city1 = c1;
        city2= c2;
        claimed = -1;
    }

    /**
     * creates an edge of a certain length between two cities of a 
     * certain color
     * 
     * @param     c1     first city
     * @param     c2     second city
     * @param     myC      color of route
     * @param     l      length of route
     */
    public Edge(City c1, City c2, Color myC, int l)
    {
        city1=c1;
        city2=c2;
        color = myC;
        length = l;
        isFerry =false;
        isMountain=0 ;
        claimed = -1;

    }

    /**
     * creates an edge of specific attributes
     * 
     * @param     c1     first city
     * @param     c2     second city
     * @param     myC     color of route
     * @param     l     length of route
     * @param     specialAttribute     special attribute of route
     * @param     mountain      determines if it is a mountain route
     */
    public Edge(City c1, City c2, Color myC, int l, String 
    specialAttribute, int mountain){
        city1=c1;
        city2=c2;
        color = myC;
        length = l;

        claimed = -1;

        if(specialAttribute.equals("Ferry")) isFerry = true;
        else isMountain = mountain;

    }

    /**
     * returns first city
     * 
     * @return     city1     first city
     */
    public City getCity1(){return city1;}

    /**
     * returns second city
     * 
     * @return     city2     second city
     */
    public City getCity2(){return city2;}

    /**
     * returns if the edge exists
     * 
     * @return     boolean     true if edge exists, false if else
     */
    public boolean equals(Edge e){
        if(city1.equals(e.getCity1())&&city2.equals(e.getCity2())
        || city2.equals(e.getCity1())&&city1.equals(e.getCity2())) 
        return true;
        return false;
    }

    /**
     * returns if the edge is claimed
     * 
     * @return     claimed     sets to number of who claims it
     */
    public int getClaimed(){
        return claimed;
    }

    /**
     * sets the value of who claims a route
     * 
     * @param     i     number of who claimed route
     */
    public void setClaimed(int i){
        claimed = i;   
    }
    
    /**
     * returns length of route
     * 
     * @return     length     length of route
     */
    public int getLength(){
        return length;
    }
    
    /**
     * returns color of route
     * 
     * @return     color     color of route
     */
    public Color getColor(){
        return color;
    }
    
    /**
     * returns if its a ferry or not
     * 
     * @return     isFerry     true if ferry, false if not
     */
    public boolean isFerry(){
        return isFerry;
    }
    
    /**
     * returns how many mountains the route has
     * 
     * @return     isMountain      returns the number of mountains
     */
    public int isMountain(){
        return isMountain;
    }
}
