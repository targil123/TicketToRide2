import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException.*;
import javax.swing.*;
import java.util.*;

/**
 * this class creates a card object
 * 
 * @author Gili Rusak
 * 
 * @version 6 May 2013
 */
public class Card
{
    //instance variables
    private Color color;
    
    /**
     * this class creates a card object of a defined color
     * 
     * @param     c     Color of the card
     */
    public Card(Color c)
    {
        color = c;
    }

    /**
     * returns the color of the card
     * 
     * @return     color     the color of the card
     */
    public Color getColor(){
        return color;
    }
}
