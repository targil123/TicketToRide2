
/**
 * setting and getting the many different options for a card
 * can change or return the value of whether a card
 * is of a certain color or if it has specific qualities
 * 
 * @author Gili Rusak
 * 
 * @version 6 May 2013
 */
public class Options
{
    // instance variables
    private int numColor;
    private Color color;
    private int numLoc;
    private int numMountain;


    /**
     * sets various parameters for a card
     * 
     * @param     c     color of the card
     * @param     num     sets the number of colored tiles
     * @param     loc     sets the number of locomotive cards
     * @param     mtn     determines whether it is a moutain card or not
     */
    public Options(Color c, int num, int loc,int mtn)
    {
        color = c;
        numColor = num;
        numLoc = loc;
        numMountain = mtn;
    }

    /**
     * returns the color of the card
     * 
     * @return     color     the color of the card
     */
    public Color getColor(){
        return color;
    }

    /**
     * returns the number of colored tiles
     * 
     * @return     numColor     number associated with color
     */
    public int getNumColor(){
        return numColor;
    }

    /**
     * returns the number of locomotive cards
     * 
     * @return     numLoc     the number of locomotive cards
     */
    public int getLoco(){
        return numLoc;
    }

    /**
     * returns the number of mountain cards
     * 
     * @return     numMountain     returns the number of moutain cards
     */
    public int getMountain(){
        return numMountain;
    }

}
