//////////////////////////////////////////////////////////////////////////
import java.util.*;

/**
 * creates a player object
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class Player
{
    //instance variables
    private int playerNum;//0 is player 1
    private Hand hand= new Hand();
    private ArrayList<DestinationCard> destinations = 
        new ArrayList<DestinationCard>();
    private int points;
    private int numTrains=45;
    private ArrayList<Edge> edges = new ArrayList<Edge>(); 
    private ArrayList<City> cities = new ArrayList<City>(); 
    private int[] coloredCards = new int[9];
    private String name;
    private java.awt.Color playerColor;

    /**
     * creates a player object
     * 
     * @param     myNum     sets player number
     */
    public Player(int myNum)
    {
        points = 0;
        playerNum = myNum;
    }

    /**
     * returns how many mountains on an edge
     * 
     * @return     count     number of mountains on an edge
     */
    public int getNumMountain(){
        int count = 0;
        for(Edge e: edges){
            count+=e.isMountain();
        }
        return count;
    }

    /**
     * Attributes a color to a player
     * 
     * @param   c   Color to set
     */

    public void setPlayerColor(java.awt.Color c){
        playerColor = c;
    }

    /**
     * returns a players specific color
     * 
     * @return  playerColor color attributed to Player
     */
    public java.awt.Color getColor(){
        return playerColor;
    }

    /**
     * Sets the Player's name
     * 
     * @param   n   String to set name to
     */
    public void setPlayerName(String n){
        name = n;
    }

    /**
     * Constructs Player Object
     * 
     * @param   myNum   number of the player
     * @param   k ArrayList of destination cards to accosiate to a player
     */
    public Player(int myNum,ArrayList<DestinationCard> k)
    {
        points = 0;
        playerNum = myNum;
        destinations = k;
    }

    /**
     * adds points to the players total
     * 
     * @param i number of points to add
     */

    public void addPoints(int i){
        points+=i;
    }

    /**
     * returns player number
     * 
     * @return playerNum the number the player is
     */

    public int getPlayerNum(){
        return playerNum;
    }

    /**
     * removes points from a players overall score
     * 
     * @param i points to subtract
     */

    public void removePoints(int i){
        points-=i;
    }

    /**
     * returns total number of points
     * 
     * @return points Players's point total
     */
    public int numPoints(){
        return points;
    }

    /**
     * adds a destination to a player
     * 
     * @param k ArrayList of destination cards to be player's
     */

    public void addDestination(ArrayList<DestinationCard> k){
        destinations=k;
    }

    /**
     * returns list of destination cards a player has
     * 
     * @return destinations ArryList of DestinationCards 
     *      that are a player's
     */

    public ArrayList<DestinationCard> getDestinations(){
        return destinations;
    }

    /**
     * Adds a city 
     * 
     * @param c City to add
     */

    public void addCity(City c){
        cities.add(c);
    }

    /**
     * gets ArrayList of cities have a player has claimed
     * 
     * @returns cities ArrayList of player's cities
     */
    public ArrayList<City> getCities(){
        return cities;
    }

    /**
     * returns list of edges a player has
     * 
     * @returns edges list of edges a player has captured
     */
    public ArrayList<Edge> getEdges(){
        return edges;
    }

    /**
     * Adds a colored card to a players pile of cards
     * 
     * @param c Color card to add
     */
    public void addColoredCard(Color c){
        switch(c){
            case BLACK: coloredCards[0]=coloredCards[0]+1;
            break;
            case RED: coloredCards[1]=coloredCards[1]+1;
            break;
            case ORANGE: coloredCards[2]=coloredCards[2]+1;
            break;
            case YELLOW: coloredCards[3]=coloredCards[3]+1;
            break;
            case GREEN: coloredCards[4]=coloredCards[4]+1;
            break;
            case BLUE: coloredCards[5]=coloredCards[5]+1;
            break;
            case PURPLE: coloredCards[6]=coloredCards[6]+1;
            break;
            case WHITE: coloredCards[7]=coloredCards[7]+1;
            break;
            case LOCO: coloredCards[8]=coloredCards[8]+1;
            break;

        }
    }

    /**
     * returns number of cards a player has
     * of a certain colored card
     * 
     * @param c Color to find the number of cards for
     * @return int number of cards
     */
    public int getNumCards(Color c){
        switch(c){
            case BLACK: return coloredCards[0];
            case RED: return coloredCards[1];
            case ORANGE: return coloredCards[2];
            case YELLOW: return coloredCards[3];
            case GREEN: return coloredCards[4];
            case BLUE: return coloredCards[5];
            case PURPLE: return coloredCards[6];
            case WHITE: return coloredCards[7];
            case LOCO: return coloredCards[8];

        }
        return 0;

    }
    
    
    /**
     * removes a card from a certian color pile
     * 
     * @param c Color to remove from
     * @param i int to decrease card number
     */
    public void removeCards(Color c, int i){
        switch(c){
            case BLACK: coloredCards[0]-=i;
            break;
            case RED: coloredCards[1]-=i;
            break;
            case ORANGE: coloredCards[2]-=i;
            break;
            case YELLOW: coloredCards[3]-=i;
            break;
            case GREEN: coloredCards[4]-=i;
            break;
            case BLUE: coloredCards[5]-=i;
            break;
            case PURPLE: coloredCards[6]-=i;
            break;
            case WHITE: coloredCards[7]-=i;
            break;
            case LOCO: coloredCards[8]-=i;
            break;

        }
    }

    /**
     * returns player's name
     * 
     * @return name String of players name
     */
    public String getName(){
        return name;
    }
    
    /**
     * gets array of colored cards
     * 
     * @return coloredCards int[] of player's cards
     */

    public int[] getColoredCards(){
        return coloredCards;
    }

    /**
     * adds edge to the players list of edges
     * 
     * @param e edge to add
     */
    public void addEdge(Edge e){
        edges.add(e);
    }

    /**
     * returns player number
     * 
     * @return playerNum int number of player
     */
    public int getId(){
        return playerNum;
    }

    /**
     * adds destination to the arraylist of player's desintation
     * @param arr arryList of destinationcards to add to players current
     *          destination cards
     */
    public void addDestinations(ArrayList<DestinationCard>arr){
        destinations.addAll(arr);
    }

    /**
     * returns arrayList of a player's destination cards
     * 
     * @return destinations player's Arraylist of destinations
     */
    public ArrayList getPlayerDestinations(){
        return destinations;
    }

    /**
     * removes trains from a play's total numebr
     * 
     * @param i int of trains to subtract
     * @return boolean if subtration was possible
     */
    public boolean removeTrain(int i){
        if(numTrains>=i){
            numTrains= numTrains-i;
            return true;
        }
        return false;

    }

    /**
     * returns number of trains
     * @return numTrains int of number of trinas a player has
     */
    public int getTrains(){
        return numTrains;
    }
}
