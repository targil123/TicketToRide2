import java.util.*;
/**
 * The creates a Graph object to help
 * 
 * 
 * @author Gili Rusak 
 * @version 6 May 2013
 */
public class Graph
{
    // instance variables - replace the example below with your own
    private cityVertex[]vertices;
    //private EdgeList edges = new EdgeList();
    private ArrayList<Edge> edges;
    private ArrayList<City> cities;
    private int numVertices;

    /**
     * Constructor for the Graph Class objects
     * 
     * @param myEdge ArrayLisy of edges
     * @param myCities ArrayList of cities
     */
    public Graph(ArrayList<Edge> myEdge, ArrayList<City> myCities)
    {
        edges = new ArrayList<Edge>();
        for (Edge e: myEdge)
            edges.add(e);
        cities = new ArrayList<City>();
        for (City c: myCities)
            cities.add(c);

        numVertices = myCities.size();
        vertices = new cityVertex[numVertices];
        for(int i =0;i<numVertices;i++){ 
            vertices[i] = new cityVertex(cities.get(i));
        }
        for(int i =0;i<numVertices;i++){
            for(Edge edge : edges){
                if(edge.getCity1().equals(vertices[i].getCity())){
                    vertices[i].setHead(new cityNode(
                            edge.getCity2(),vertices[i].getHead()));
                }
                else if (edge.getCity2().equals(vertices[i].getCity())){
                    vertices[i].setHead(new cityNode(
                            edge.getCity1(),vertices[i].getHead()));
                }
            }
        }
    }

    /**
     * returns largest number of 
     * connected cities in a graph
     * 
     * @return large int of largest number 
     *          of connections
     */
    public int largest(){
        int large = -1;
        int temp;
        for(int i =0;i<numVertices;i++){
            temp = DFS(vertices[i]);
            if(temp>large) large = temp;
            System.out.print(temp+" ");
            reset();
            //this = new Graph(edges,cities);
        }
        return large;
    }

    /**
     * resets vertices[] to all false
     * 
     */
    public void reset(){
        for(int i =0;i<numVertices;i++){
            vertices[i].setVisited(false);
        }
    }

    /**
     * runs a depth first search on a vertex
     * to find conected compnets of a graph
     * 
     * @param v cityVertex to start search
     */
    public int DFS(cityVertex v){
        v.setVisited(true);
        cityNode first = v.getHead();
        cityVertex firstVert =null;
        int temp=1;

        while(first!=null){
            for(int i =0;i<numVertices;i++){
                if(vertices[i].getCity().equals(first.getCity())){
                    firstVert =  vertices[i];
                    break;
                }
            }
            if(firstVert.getVisited()==false){
                temp += DFS(firstVert);  
            }
            first = first.getNext();
            //return 1;
        }
        return temp;
    }

    /**
     * checks to make sure that two cities are connecter
     * 
     * @param c1 First City to check
     * @param c2 Second City to check
     * @param cityArr array of all Cities
     * 
     * @return boolean true is the cities are connected, 
     *          false otherwise
     */
    public boolean isConnected(City c1,City c2,ArrayList<City> cityArr){
        boolean b1 = false;
        boolean b2 = false;
        for(City c:cityArr){
            if(c.equals(c1)){
                b1 = true;

            }
            if(c.equals(c2)){
                b2 = true;

            }
        }
        if(b1&&b2) return isConnected(c1,c2);
        return false;
    }

    /**
     * checks to see if two cities are connected
     * 
     * @param city1 first City to check
     * @param city2 second City to check
     * 
     *  @return boolean true is the cities are connected, 
     *          false otherwise
     */
    public boolean isConnected(City city1,City city2){
        if(city1.equals(city2)) return true;
        cityVertex v = null;
        for(int i = 0;i<numVertices;i++){
            if(vertices[i].getCity().equals(city1)){
                v = vertices[i];
            }
        }
        if(v.getVisited()==true) return false;
        v.setVisited(true);
        cityNode first = v.getHead();
        while(first!=null){
            boolean temp = isConnected(first.getCity(),city2);
            if(temp) return true;
            first = first.getNext();
        }
        return false;        
    }
}

