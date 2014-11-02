////////////////////////////////////////////////////////////////////////////////
import java.util.*;

/**
 * creates the deck of train cards
 * some may be randomly chosen
 * 
 * @author Gili Rusak 
 * @version 6 May 2013
 */
public class Deck
{
    //instance variables
    private ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * creates a deck object for train cards
     */
    public Deck()
    {
        for(Color c : Color.values()){
            if(c!=Color.GREY&&c!=Color.LOCO){
                for(int i = 0;i<12;i++)
                    cards.add(new Card(c));
            }
        }
        for(int i = 0; i<14;i++){
            cards.add(new Card(Color.LOCO));
        }
    }

    /**
     * returns a random card off the top of the new deck
     * 
     * @return     c      random card from the deck
     */
    public Card deal()
    {
        if(cards.size()==0){
            return null;
        }
        Random r = new Random();
        int a = r.nextInt(cards.size());
        Card c = cards.get(a);
        cards.remove(a);

        return c;
    }

    public void add(Card c){
        cards.add(c);
    }

    public int size(){
        return cards.size();
    }
    
    public ArrayList<Card> getCards(){
        return cards;
    }
}
