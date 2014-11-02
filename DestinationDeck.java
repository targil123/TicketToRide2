import java.util.*;
import java.io.*;
import java.io.FileReader;

/**
 * the following code determines the locations to be used in 
 * the destination card deck
 * 
 * @author Gili Rusak
 * 
 * @version 6 May 2013
 */
public class DestinationDeck
{
    private ArrayList <DestinationCard> destinations = 
    new ArrayList<DestinationCard>();
    private ArrayList<DestinationCard> copy = 
    new ArrayList<DestinationCard>();
    private ArrayList<DestinationCard> longs = 
    new ArrayList<DestinationCard>();
    
    /**
     * The following code creates a destination card object by
     * reading in the information from Destinations.txt
     */
    public DestinationDeck()
    {

        try{
            FileInputStream fstream = new FileInputStream
                    ("Destinations.txt");
            DataInputStream in = new DataInputStream
                    (fstream);
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(!strLine.equals("")){
                    String[] str = strLine.split(" : ");
                    String []cities = str[0].split("-");
                    
                    int num = Integer.parseInt(str[1]);
                    destinations.add(new DestinationCard
                            (new City(cities[0]),
                    new City(cities[1]),num));
                
                   System.out.println(cities[0]+"   "+cities[1]
                            +"   "+num);
                }
            }

            in.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        copy.addAll(destinations);
     
        longs.add(new DestinationCard(new City("Moscow"),
        new City("Calcutta"),16));
        longs.add(new DestinationCard(new City("Omsk"),
        new City("Kobe"),16));
        longs.add(new DestinationCard(new City("Ankara"),
        new City("Colombo"),18));
        longs.add(new DestinationCard(new City("Astrakhan"),
        new City("Hanoi"),18));
        longs.add(new DestinationCard(new City("Khabarovsk"),
        new City("Karachi"),17));
        longs.add(new DestinationCard(new City("Krasnoyarsk"),
        new City("Singapore"),17));
    }
    
    /**
     * the following method chooses a random destination
     * card from the deck
     * 
     * @return     c     random destination card
     */
    public DestinationCard deal()
    {
        Random r = new Random();
        int a = r.nextInt(destinations.size());
        DestinationCard c = destinations.get(a);
        destinations.remove(a);
        
        if(destinations.size()==0){
            destinations.addAll(copy);
        }

        return c;
    }
    
    /**
     * this method returns a random long destination card
     * for the user
     * 
     * @return     c     returns a long distance destination
     *                   card
     */
    public DestinationCard dealLong(){
        Random r = new Random();
        int a = r.nextInt(longs.size());
        DestinationCard c = longs.get(a);
        destinations.remove(a);

        if(destinations.size()==0){
            destinations.addAll(copy);
        }

        return c; 

    }
}

