import java.util.*;
import java.io.*;
import java.io.FileReader;

/**
 * creates a player edgeList object
 * 
 * @author Gili Rusak
 * @version 6 May 2013
 */
public class EdgeList
{
    private ArrayList<Edge> edgeList = new ArrayList<Edge>();
    
    /**
     * Constructs EdgeList object
     */
    public EdgeList()
    {
        try{
            FileInputStream fstream = new FileInputStream("Edges.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader
                        (new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if(!strLine.equals("")){
                    String[] str = strLine.split(" : ");
                    String []cities = str[0].split("-");
                    City one = new City(cities[0]);
                    City two = new City(cities[1]);
                    String [] info = str[1].split(" ");
                    if(info.length==2){
                        Color c = Color.valueOf(info[1].toUpperCase());
                        int len = Integer.parseInt(info[0]);
                        edgeList.add(new Edge(one, two, c,len));
                    }

                    else if(info.length==3){
                        Color c = Color.valueOf(info[1].toUpperCase());
                        int len = Integer.parseInt(info[0]);

                        String special = info[2];
                        edgeList.add(
                                new Edge(one, two, c,len,special,-1));
                    }
                    else if(info.length==4){

                        Color c = Color.valueOf(info[1].toUpperCase());
                        int len = Integer.parseInt(info[0]);

                        String special = info[2];

                        int mtn = Integer.parseInt(info[3]);
                        edgeList.add(
                                new Edge(one, two, c,len,special,mtn));
                    }
                }
            }

            in.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        String strLine = "Peking-Ulan Bator: 3 Orange Mountain 1";
        String[] str = strLine.split(":");
        String []cities = str[0].split("-");
        System.out.println(cities[0]);
                System.out.println(cities[1]);
        City two = new City(cities[1]);
        String [] info = str[1].split(" ");
        System.out.print(" dlksjfdlfkds");
    }
    
    /**
     * Returns ArrayList of edges
     * 
     * @returns edgeList ArrayList of edges
     */
    public ArrayList<Edge> getEdges(){
        return edgeList;
    }
}
