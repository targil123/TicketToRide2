////////////////////////////////////////////////////////////////////////////
import java.util.*;

/**
 * creates a players "hand" of cards
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class Hand
{
    // instance variables
    private ArrayList <Card> myCards = new ArrayList<Card>();

    /**
     * creates a hand object
     */
    public Hand()
    {}

    /**
     * adds a card to the hand object
     * 
     * @param     c     card to add
     */
    public void add(Card c)
    {
        myCards.add(c);
    }
    
    /**
     * returns if the hand has enough cards to fill the edge.
     * 
     * @param      c      color of edge
     * @param      length      length of edge
     * 
     * @return     boolean      true if its a possible edge
     *                          false if it isnt
     */
    public boolean isPossible(Color c,int length){
        int count =0;
        for(int i =0;i<myCards.size();i++){
            if(myCards.get(i).getColor()==c) count++;
        }
        if(count>=length){
            return true;
        }
        return false;
        
    }
}
