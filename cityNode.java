
/**
 * The creates a cityNode object to help
 * keep track of cities as they are
 * connected by the player
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class cityNode
{
    private City city;
    private cityNode next;

    /**
     * Constructor for objects of class cityNode
     * 
     * @param c City to add
     */
    public cityNode(City c)
    {
        city = c;
    }
    
    /**
     * Constructor for CityNode
     * 
     * @param c City to add 
     * @param cityNode refernce to next node in list
     */
    public cityNode(City c, cityNode next){
        this.next = next;
        city = c;
    }
    
    /**
     * returns a city refernce in a node
     * 
     * @return city City object the node refers to
     */
    public City getCity(){
        return city;
    }

    /**
     * returns refernce to next cityNode in the list
     * 
     * @return next refernce to nextCity node
     */
    public cityNode getNext(){
        return next;
    }
}
