
import java.util.*;
import java.io.*;
/**
 * determines how each of the cities connect to each other graphicly
 * find the coordinates on the map and how they will appear to the user
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class pathlist
{
    // instance variables
    private ArrayList<path> paths = new ArrayList<path>();

    /**
     * Constructor for objects of class path
     */
    public pathlist()
    {
        try{
            FileInputStream fstream = new FileInputStream
                    ("citiesconnect.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(!strLine.equals("")){
                    String[] str = strLine.split(":");

                    String [] secondPart = str[1].split(" - ");

                    String [] xCoords = secondPart[1].split(" , ");
                    String [] yCoords = secondPart[2].split(" , ");

                    ArrayList<Integer> xIntCoords = 
                            new ArrayList<Integer>(); 
                    for(String x: xCoords){
                        xIntCoords.add(Integer.parseInt(x));
                        //System.out.println(x);    
                    }
                    ArrayList<Integer> yIntCoords = 
                            new ArrayList<Integer>(); 
                    for(String y: yCoords)
                        yIntCoords.add(Integer.parseInt(y));

                    path q = new path(new City(str[0]),new City
                            (secondPart[0]),xIntCoords,yIntCoords); 
                    paths.add(q);
                }
            }
            in.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * returns the array of path objects
     * 
     * @return      paths     array of path objects
     */
    public ArrayList<path> getPaths(){
        return paths;
    }
}
