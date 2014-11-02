
/**
 * creates cityVertex object
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class cityVertex
{
    private cityNode head=null;
    private City city;
    private boolean visited;
    
    /**
     * Constructor for City object
     * 
     * @param c City to create vertex at
     */
    public cityVertex(City c)
    {
        city = c;
    }

    /**
     * sets the head of the list to the gievn node
     * 
     * @param c cityNode to make the head
     * 
     */
    public void setHead(cityNode c){
        head = c;
    }
    
    /**
     * sets vertex to visited on unvisited
     * @param b booelan of how to set vertex
     * 
     */
    public void setVisited(boolean b){
        visited = b;
    }
    
    /**
     * gets the city object of a given vertex
     * 
     * @return city City object
     */
    public City getCity(){
        return city;
    }
    
    /**
     * gets the head of the list
     * 
     * @return head cityNode object 
     */
    public cityNode getHead(){
        return head;
    }
    
    /**
     * returns whether or not a vertex is visited
     * 
     * @return boolean true it it is visited, false otherwise
     */
    public boolean getVisited(){
        return visited;
    }
}
