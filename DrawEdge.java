
/**
 * creates an edge between two cities
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class DrawEdge
{
    private Edge edge;
    
    private int[] xPoints = new int[6];
    private int[] yPoints = new int[6];
    
    
    /**
     * Constructor for objects of class DrawEdge
     * 
     * @param e Edge that is being made
     * @param x array of x coordinates
     * @param y array of y coordinates
     * 
     */
    public DrawEdge(Edge e, int[] x, int[] y)
    {
        edge = e;
        xPoints=x;
        yPoints=y;
    }

    
}
