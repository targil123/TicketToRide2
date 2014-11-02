import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException.*;
import javax.swing.*;
import java.util.*;

/**
 *This Class controls the game play
 *
 *It runs the graphics and behind the scenes configurations 
 *for the Game Ticket to Ride, Legendary Asis
 * 
 * @author Gili Rusak 
 * @version 6 May 2013
 */
public class Game extends Applet
implements MouseListener, MouseMotionListener,ActionListener {
    //instance variables
    Image backGround, asianGuy, greenSuitMan, instructions, 
    origBackGround, blueRibbon, wood, table, paper,
    DTL, DTL2, DTL3, compass;
    Rectangle backGroundRect = new Rectangle(0,0,900,600);

    Image[] coloredCards = new Image[9];
    Image pile, destinationPile;
    Rectangle pileRect, destinationPileRect;

    int width, height;
    int mx, my;  // the mouse coordinates
    boolean isStartGamePressed = false;
    Button startGame;//Starts the game

    DestinationDeck destinationDeck;
    Deck cardDeck;

    private int numPlayers = 0;
    CityList cities = new CityList();

    Rectangle [] dealtCards = new Rectangle[5];
    Player currentPlayer;

    Image [] initialCardsDealt = new Image[5];
    Color [] initialColorsDealt = new Color[5];

    int cardsSelected = 0;
    int numCities = 0;
    City[] citiesSelected = new City[2];

    EdgeList edges = new EdgeList();

    boolean LASTTURN;
    int lastplayer=-1;
    boolean done;

    public ArrayList<Player> players = new ArrayList<Player>();

    Graphics bufferGraphics;
    Image bufferImage;
    boolean GAMESTART = false;

    pathlist myPaths = new pathlist();

    /**
     * This method initializes the start of the game
     * 
     * It also sets up several important instance variables
     */
    public void init() 
    {
        addMouseListener( this );
        addMouseMotionListener( this );
        destinationDeck = new DestinationDeck();
        cardDeck = new Deck();

        width = getSize().width;
        height = getSize().height;

        mx = width/2;
        my = height/2;
        if(GAMESTART == false){
            startGame = new Button("Start Game");
            startGame.setBounds(400,400,200,200);

            add(startGame);
            startGame.addActionListener(this);

            origBackGround = getImage(getDocumentBase(), "ttr.jpg");
            wood = getImage(getDocumentBase(), "wood-background.jpg");
            instructions = getImage(
                getDocumentBase(), "instructions.jpg");
        }
    }

    /**
     * This method sets up the board and other images that are
     * vital for the game to run smoothly and make sense
     */
    public void setBoard(){
        GAMESTART = true;
        backGround = getImage(getDocumentBase(),"board.jpg");
        asianGuy = getImage(getDocumentBase(), "con2.jpg");
        blueRibbon = getImage(getDocumentBase(), "blueRibbin.jpg");

        greenSuitMan = getImage(
            getDocumentBase(), "conductorGreen.png");
        paper = getImage(getDocumentBase(), "paper.jpg");
        table = getImage(getDocumentBase(), "table.jpg");
        compass = getImage(getDocumentBase(), "compass.jpg");

        DTL = getImage(getDocumentBase(), "DarrenLim.jpg");
        DTL2 = getImage(getDocumentBase(), "lim2.jpg");
        DTL3 = getImage(getDocumentBase(), "lim3.jpg");

        coloredCards[0] = getImage(getDocumentBase(),"black.gif");
        coloredCards[1] = getImage(getDocumentBase(),"red.gif");
        coloredCards[2] = getImage(getDocumentBase(),"brown.gif");
        coloredCards[3] = getImage(getDocumentBase(),"yellow.gif");
        coloredCards[4] = getImage(getDocumentBase(),"green.gif");
        coloredCards[5] = getImage(getDocumentBase(),"blue.gif");
        coloredCards[6] = getImage(getDocumentBase(),"purple.gif");
        coloredCards[7] = getImage(getDocumentBase(),"white.gif");
        coloredCards[8] = getImage(getDocumentBase(),"loco.gif");

        pile = getImage(getDocumentBase(),"back.png");
        pileRect = new Rectangle(950,550,130,80);

        destinationPile = getImage(
            getDocumentBase(),"destinationCards.png");

        destinationPileRect = new Rectangle(1100,550,130,80);
        int numLoco = 0;
        for(int i =0;i<5;i++){
            if(i<2){
                dealtCards[i] = new Rectangle(
                    width-295+i*100,height/2-100,100,75);
            }
            else{                
                dealtCards[i]= new Rectangle(
                    width-345+(i-2)*100,(height/2)-40,100,75);
            }
            Card c = cardDeck.deal();
            if(c.getColor()==Color.LOCO){
                numLoco++;
                if(numLoco>2){
                    while(c.getColor()==Color.LOCO){
                        c = cardDeck.deal();
                    }
                }
            }
            initialColorsDealt[i] = c.getColor();
            initialCardsDealt[i] = getImage(c);
        }

    }

    /**
     * This method waits for the start button to
     * be pushed. When it is the game starts playing
     * 
     * @param e ActionEvent 
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startGame){
            launchIntro();
            setBoard();
        }
        repaint();

    }

    /**
     * controls events when mouse enters the window
     * @param e MouseEvent
     */
    public void mouseEntered( MouseEvent e ) {}

    /**
     * controls events when mouse exits the window
     * @param e MouseEvent
     */
    public void mouseExited( MouseEvent e ) {}

    /**
     * gets options of how an edge can be claimed
     * 
     * @param e Edge that is trying to be claimed
     * 
     * @return ArrayList of the different options
     */
    public ArrayList<Options> getOptions(Edge e){
        ArrayList<Options> options = new ArrayList<Options>();

        int loc = currentPlayer.getNumCards(Color.LOCO);
        int isFerry=0;

        for(Edge edge : edges.getEdges()){
            int edgeLength = edge.getLength();

            if(e.equals(edge)&&edge.getClaimed()==-1){

                Color c = edge.getColor();
                if(edge.isFerry()) isFerry = 1;
                if(c!=Color.GREY){

                    int numCards = currentPlayer.getNumCards(c);
                    int mostFerry;
                    if(loc>=edgeLength)mostFerry = edgeLength;
                    else mostFerry = loc;

                    for(int i = isFerry;i<=mostFerry;i++){

                        if((i+numCards)>=edgeLength){

                            int numMountain = edge.isMountain();
                            Options opt = new Options(
                                    c,edgeLength-i,i,numMountain);

                            options.add(opt);
                        }
                    }
                }
                else{
                    for(Color col:Color.values()){
                        if(col!=Color.LOCO&&col!=Color.GREY){
                            int numCards = 
                                currentPlayer.getNumCards(col);
                            int mostFerry;
                            if(loc>=edgeLength)mostFerry = edgeLength;
                            else mostFerry = loc;

                            for(int i = isFerry;i<=mostFerry;i++){

                                if((i+numCards)>=edgeLength){

                                    int numMountain = edge.isMountain();
                                    Options opt = new Options(
                                            col,edgeLength-i,i,numMountain);

                                    options.add(opt);
                                }
                            }
                        }
                    }
                }
            }
        }

        return options;
    }

    /**
     * returns number of points to add
     * 
     * @param     i     number of spaces per route
     * @return     0     returns 0
     */
    public int getNumPointsToAdd(int i){
        switch(i){
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 7;
            case 5: return 10;
            case 6: return 15;

        }
        return 0;
    }

    /**
     * claims an edge for the player
     * 
     * @param     e     edge to be claimed
     */
    public void claim(Edge e){
        for(Edge edge : edges.getEdges()){
            if(edge.equals(e)){
                edge.setClaimed(currentPlayer.getId());
                currentPlayer.addEdge(edge);
                currentPlayer.addCity(edge.getCity1());
                currentPlayer.addCity(edge.getCity2());
            }
        }
    }

    /**
     * displays destination cards for player's choice
     * 
     * @param     p     player in question
     */
    City drawCity1 = null;
    City drawCity2 = null;
    public void drawThings(Player p){
        ArrayList<DestinationCard> dests =  p.getDestinations();
        ButtonGroup g = new ButtonGroup();
        ArrayList<Object> object = new ArrayList<Object>();
        object.add("Which destination card?");
        ArrayList<JRadioButton> myButtons = new ArrayList<JRadioButton>();
        for(int i = 0;i<dests.size();i++){
            JRadioButton j = new JRadioButton(dests.get(i).getCity1()
                    +" "+dests.get(i).getCity2());
            g.add(j);
            object.add(j);
            myButtons.add(j);
        }
        Object [] arr2 = object.toArray();

        JOptionPane.showMessageDialog(null,arr2,"",
            JOptionPane.INFORMATION_MESSAGE);
        for(int i = 0; i<myButtons.size();i++){
            JRadioButton j = myButtons.get(i);
            if(j.isSelected()){
                drawCity1 = dests.get(i).getActualCity1();
                drawCity2 = dests.get(i).getActualCity2();
            }
        }

    }

    /**
     * determines where the mouse is clicking
     * 
     * @param     e     MouseEvent
     */
    public void mouseClicked( MouseEvent e ) {
        mx = e.getX();
        my = e.getY();
        if(lastplayer!=-1) done = true;
        if(mx>=800&&mx<=1000&my>=725&&my<=925){
            String [] blah = 
                {"Please select the route you want highlighted"};
            JOptionPane.showMessageDialog(null,blah,"",
                JOptionPane.INFORMATION_MESSAGE);
            drawThings(currentPlayer);
        }

        if(backGroundRect.contains(mx,my)){
            City myCity = cities.contains(mx,my);
            if(myCity!=null&&cardsSelected==0){
                if(numCities==0){
                    String[] arr = {""+myCity.getName()};
                    int n = JOptionPane.showConfirmDialog(null,
                            arr,"First city",JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.YES_OPTION){
                        citiesSelected [0]=myCity;
                        numCities++;
                    }
                }else if(numCities==1){
                    String[] arr = {""+myCity.getName()};
                    int n = JOptionPane.showConfirmDialog
                        (null,arr,"Second city",JOptionPane.YES_NO_OPTION);

                    if(n==JOptionPane.YES_OPTION){
                        citiesSelected [1] = myCity;
                        Edge myEdge = new Edge(citiesSelected[0]
                            ,citiesSelected[1]);
                        ArrayList<Options> myOptions = 
                            getOptions(myEdge);
                        if(myOptions.size()>0){
                            Object[]possibilities = new 
                                Object[myOptions.size()];

                            for(int i =0;i<myOptions.size();i++){
                                Options opt = myOptions.get(i);
                                String s = opt.getColor()+" "+
                                    opt.getNumColor()+" LOCO "+opt.getLoco()
                                    +" Mountain: "+opt.getMountain();

                                possibilities[i]= s;

                            }

                            String s = (String)
                                JOptionPane.showInputDialog
                                ( this,"Here are your options",
                                    "Customized Dialog",
                                    JOptionPane.PLAIN_MESSAGE, 
                                    null,possibilities,"ham");
                            String [] split = s.split(" ");
                            Color c = Color.valueOf(split[0]);
                            int numC = Integer.parseInt(split[1]);
                            int loc = Integer.parseInt(split[3]);
                            int mtn = Integer.parseInt(split[5]);
                            showStatus(c+" "+numC+" "+loc+ " mtn " +mtn);

                            if(currentPlayer.removeTrain(numC+loc+mtn)){
                                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                for(int i = 0;i<numC;i++){
                                    cardDeck.add(new Card(c));
                                }

                                for(int i = 0;i<loc;i++){
                                    cardDeck.add(new Card(Color.LOCO));
                                }
                                int temp;
                                if(numC>=5){
                                    temp = 5;
                                }else{
                                    temp = numC;
                                }
                                int temp2;
                                if(loc>=5){
                                    temp2 = 5;
                                }
                                else{
                                    temp2 = loc;
                                }
                                int count=0,countB=0;
                                for(int i = 0;i<5;i++){
                                    if(count<temp){
                                        if(initialColorsDealt[i]==null){
                                            initialColorsDealt[i] = c;
                                            initialCardsDealt[i] = getImage(new Card(c));
                                            count++;
                                        }
                                    }
                                }
                                for(int i = 0;i<5;i++){
                                    if(countB<temp2)
                                    {
                                        if(initialColorsDealt[i]==null){
                                            initialColorsDealt[i] = Color.LOCO;
                                            initialCardsDealt[i] = getImage(new Card(Color.LOCO));
                                            countB++;
                                        }
                                    }
                                }

                                claim(myEdge);

                                currentPlayer.addPoints
                                (getNumPointsToAdd(numC+loc)+2*mtn);

                                currentPlayer.removeCards(c,numC);
                                currentPlayer.removeCards(Color.LOCO,loc);

                                if(currentPlayer.getTrains()<=2){
                                    if(LASTTURN==false){
                                        LASTTURN=true;
                                        lastplayer = 
                                        currentPlayer.getId();

                                        String [] arr3 = {"Last turns!"};
                                        JOptionPane.showMessageDialog
                                        (null,arr3,"",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                                if(done&&lastplayer==
                                currentPlayer.getId()){
                                    String [] arr3 = {"Game is over!"};
                                    int k = JOptionPane.showConfirmDialog
                                        (null,arr3,"",
                                            JOptionPane.YES_NO_OPTION);
                                    if(k==JOptionPane.YES_OPTION){}
                                    gameOver();
                                }
                                else{
                                    currentPlayer = 
                                    getNextPlayer(currentPlayer);
                                    String [] arr2 ={"Player "+
                                            currentPlayer.getId()+" turn"};
                                    JOptionPane.showMessageDialog
                                    (null,arr2,"",
                                        JOptionPane.INFORMATION_MESSAGE);

                                    reset();
                                }
                            }else{
                                String [] arr4 = 
                                    {"Not enough train tokens."};
                                JOptionPane.showMessageDialog
                                (null,arr4,"",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                        else{
                            String [] arr3 = 
                                {"That edge was not possible."};
                            JOptionPane.showMessageDialog
                            (null,arr3,"",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        citiesSelected = new City[2];
                        numCities = 0;
                    }
                }
            }
        }

        else if(pileRect.contains(mx,my)){
            if(cardsSelected==0){    
                Card c = cardDeck.deal();
                if(c!=null)
                {
                    currentPlayer.addColoredCard(c.getColor());
                    cardsSelected++;
                }else{

                    String [] arr ={"No cards left in pile."};
                    JOptionPane.showMessageDialog(null,arr,"",
                        JOptionPane.INFORMATION_MESSAGE);

                }
            }else if(cardsSelected==1){
                Card c = cardDeck.deal();
                if(c!=null){
                    currentPlayer.addColoredCard
                    (c.getColor());
                    cardsSelected=0;
                    cardsSelected =0;
                    reset();
                    if(done&&lastplayer==currentPlayer.getId()){
                        String [] blah = {"Game is over"};
                        JOptionPane.showMessageDialog
                        (null,blah,"",
                            JOptionPane.INFORMATION_MESSAGE);
                        gameOver();
                    }
                    else{
                        currentPlayer = getNextPlayer(currentPlayer);
                        String [] arr ={"Player "
                                +currentPlayer.getId()+" turn"};
                        JOptionPane.showMessageDialog
                        (null,arr,"",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else{
                    String [] arr ={"No cards left in pile."};
                    JOptionPane.showMessageDialog(null,
                        arr,"",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if(destinationPileRect.contains(mx,my)&&cardsSelected==0
        &&numCities==0){
            ArrayList<DestinationCard> newDestinations=
                setUpDestinations(3,1,3);
            currentPlayer.addDestinations(newDestinations);
            if(done&&lastplayer==currentPlayer.getId()){
                String [] blah = {"Game is over"};
                JOptionPane.showMessageDialog(null,blah,"",
                    JOptionPane.INFORMATION_MESSAGE);
                gameOver();
            }
            currentPlayer=getNextPlayer(currentPlayer);
            String [] arr ={"Player "+currentPlayer.getId()+" turn"};
            JOptionPane.showMessageDialog(null,arr,"",
                JOptionPane.INFORMATION_MESSAGE);

        }
        for(int i =0;i<5;i++){
            Rectangle r = dealtCards[i];
            if(r.contains(mx,my)) {
                if(initialColorsDealt[i]!=Color.LOCO){
                    currentPlayer.addColoredCard
                    (initialColorsDealt[i]);
                    Card c = cardDeck.deal();
                    //JOptionPane.showInputDialog("bjakel");
                    if(c!=null){
                        initialColorsDealt[i]=c.getColor();
                        initialCardsDealt[i] = getImage(c);

                        int numLocomotivesInDeck = 0;
                        for(int j =0;j<5;j++){
                            if(initialColorsDealt[j]==
                            Color.LOCO) numLocomotivesInDeck++;
                        }

                        if(numLocomotivesInDeck>=3){
                            refresh();
                        }
                    }
                    else{
                        String [] arr ={"No cards left in pile."};
                        JOptionPane.showMessageDialog
                        (null,arr,"",
                            JOptionPane.INFORMATION_MESSAGE);

                        initialColorsDealt[i] = null;
                        initialCardsDealt[i] = null;

                    }

                    ArrayList<Card> cards = cardDeck.getCards();
                    int count =0;
                    for(Card k : cards)
                    {
                        if(k.getColor()==Color.LOCO){
                            count++;

                        }
                    }

                    int a=0;
                    for(int j=0;j<5;j++){
                        if(initialColorsDealt[j]!=Color.LOCO)a++;
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    int numberofcards = cards.size()-count+5-a;
                    //JOptionPane.showInputDialog("hi");
                  
                    if(numberofcards<=0||a<=1){
                        getNextPlayer(currentPlayer);
                        String [] arr7 = 
                            {"Player "+
                                currentPlayer.getId()+" turn"};
                        JOptionPane.showMessageDialog
                        (null,arr7,"",JOptionPane.INFORMATION_MESSAGE);
                        cardsSelected =0;
                        reset();
                    }

                    cardsSelected++;
                    if(cardsSelected==2){
                        cardsSelected =0;
                        reset();
                        if(done&&lastplayer==currentPlayer.getId()){
                            String [] blah = {"Game is over"};
                            JOptionPane.showMessageDialog
                            (null,blah,"",
                                JOptionPane.INFORMATION_MESSAGE);
                            gameOver();
                            break;
                        }
                        currentPlayer=getNextPlayer(currentPlayer);
                        String [] arr ={"Player "+
                                currentPlayer.getId()+" turn"};
                        JOptionPane.showMessageDialog
                        (null,arr,"",JOptionPane.INFORMATION_MESSAGE);
                    }

                }
                else{
                    if(cardsSelected==0){
                        currentPlayer.addColoredCard
                        (initialColorsDealt[i]);

                        Card c = cardDeck.deal();

                        if(c!=null){
                            initialColorsDealt[i]=c.getColor();
                            initialCardsDealt[i] = getImage(c);

                            int numLocomotivesInDeck = 0;
                            for(int j =0;j<5;j++){
                                if(initialColorsDealt[j]==
                                Color.LOCO) numLocomotivesInDeck++;
                            }

                            if(numLocomotivesInDeck>=3){
                                refresh();
                            }
                            
                            
                            
                            
                            
                            
                            
                            ArrayList<Card> cards = cardDeck.getCards();
                            int count =0;
                            for(Card k : cards)
                            {
                                if(k.getColor()==Color.LOCO){
                                    count++;
                                }
                            }
                            int a=0;
                            for(int j =0;j<5;j++){
                                if(initialColorsDealt[j]!=Color.LOCO)a++;
                            }
                            //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            int numberofcards = cards.size()-count+5-a;
                            //JOptionPane.showInputDialog("hi");
                  
                            if(numberofcards<=0||a<=1){
                                getNextPlayer(currentPlayer);
                                String [] arr7 = 
                                    {"Player "+
                                        currentPlayer.getId()+" turn"};
                                JOptionPane.showMessageDialog
                                (null,arr7,"",JOptionPane.INFORMATION_MESSAGE);
                                cardsSelected =0;
                                reset();
                            }
                            
                            
                        }
                        else if(cardDeck.size()==0){
                            String [] arr ={"No cards left in pile."};
                            JOptionPane.showMessageDialog(null,arr,""
                            ,JOptionPane.INFORMATION_MESSAGE);

                            initialColorsDealt[i] = null;
                            initialCardsDealt[i] = null;
                        }

                        if(done&&lastplayer==currentPlayer.getId()){
                            String [] blah = {"Game is over"};
                            JOptionPane.showMessageDialog
                            (null,blah,"",
                                JOptionPane.INFORMATION_MESSAGE);
                            gameOver();
                            break;
                        }
                        currentPlayer=getNextPlayer(currentPlayer);
                        String [] arr7 = 
                            {"Player "+
                                currentPlayer.getId()+" turn"};
                        JOptionPane.showMessageDialog
                        (null,arr7,"",JOptionPane.INFORMATION_MESSAGE);
                        cardsSelected =0;
                        reset();
                    }
                }
            }

        }
        repaint();
        e.consume();
    }

    /**
     * Refreshes the cards when there are more than three
     * locomotive cards available.
     */
    public void refresh(){
        ArrayList<Card> deck = cardDeck.getCards();
        int num=0;
        for(Card d: deck){
            if(d.getColor()==Color.LOCO) num++;
        }
        if(deck.size()<=num+3)return;

        for(int i = 0 ;i<5;i++){
            cardDeck.add(new Card(initialColorsDealt[i]));     
        }

        for(int i = 0; i<5;i++){
            Card c = cardDeck.deal();
            if(c==null){
                initialColorsDealt[i]=null;
                initialCardsDealt[i] = null;
            }
            else
            {
                initialColorsDealt[i]=c.getColor();
                initialCardsDealt[i] = getImage(c);
            }
        }

        int numLocomotivesInDeck = 0;
        for(int j =0;j<5;j++){
            if(initialColorsDealt[j]==
            Color.LOCO) numLocomotivesInDeck++;
        }

        if(numLocomotivesInDeck>=3){
            refresh();
        }

    }

    /**
     * handles code for the end of the game
     */
    public void gameOver(){
        removeMouseListener(this);
        int largest = -1;
        Player largestPlayer = null;

        ArrayList<Integer> myLengths = new ArrayList<Integer>();
        for(Player p : players){
            ArrayList<City> cities = p.getCities();
            ArrayList<City> lessCities = new ArrayList<City>();
            for(City c: cities){
                boolean b = false;
                for(City city : lessCities){
                    if(c.equals(city)){
                        b = true;
                    }
                }
                if(!b){
                    lessCities.add(c);
                }
            }

            Graph graph = new Graph(p.getEdges(),lessCities);
            ArrayList<DestinationCard> myDests = p.getDestinations();
            for(DestinationCard dest: myDests){
                graph.reset();
                if(graph.isConnected(dest.getActualCity1(),
                    dest.getActualCity2(),p.getCities())){
                    p.addPoints(dest.getPoints());
                    JOptionPane.showInputDialog("Player "+
                        p.getId()+" got the destination: "+
                        dest.getCity1()+" - "+dest.getCity2());
                }else{
                    p.removePoints(dest.getPoints());
                    JOptionPane.showInputDialog("Player "+
                        p.getId()+" didnt get the destination "+
                        dest.getCity1()+" - "+dest.getCity2());
                }
            }
            int myL = graph.largest();
            myLengths.add(myL);
            if(myL>=largest) {
                largest = myL;
                largestPlayer = p;
            }
        }
        ArrayList<Player> largestCompArr = new ArrayList<Player>();
        for(int i = 0 ;i<players.size();i++){
            if(largest == myLengths.get(i)){
                String [] arr = {"The largest component was "
                        +largest+" by player "+players.get(i).getId()};
                JOptionPane.showMessageDialog(null,arr,
                    "Largest Component",
                    JOptionPane.INFORMATION_MESSAGE);
                players.get(i).addPoints(10);
            }
        }
        int winner=-999;
        Player WINNER = null;
        ArrayList<Player> tie = new ArrayList<Player>();
        for(Player p : players){
            if(p.numPoints()>winner){
                winner = p.numPoints();
                WINNER = p;
            }
        }
        for(Player p : players){
            if(p.numPoints()==winner){
                tie.add(p);
            }
        }
        int greatestTrains = -999;
        for(Player p : tie){
            if(p.getTrains()>greatestTrains){
                greatestTrains = p.getTrains();
                WINNER = p;
            }
        }
        ArrayList<Player> tie2 = new ArrayList<Player>();
        for(Player p : players){
            if(p.getTrains()==greatestTrains){
                tie2.add(p);
            }
        }
        if(tie2.size()==1){
            String [] blah = {"The winner is "+WINNER.getId()};
            JOptionPane.showMessageDialog
            (null,blah,"",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            ArrayList<Player> tie3 = new ArrayList<Player>();
            int greatestMountain = -999;
            for(Player p : players){
                if(p.getNumMountain()==greatestMountain){
                    greatestMountain = p.getNumMountain();
                    WINNER = p;
                }
            }
        }
    }

    public boolean isClaimable(Edge e,Color c){
        int isFerry=0;
        if(e.isFerry())isFerry =1;
        for(Edge edge : edges.getEdges()){
            if(e.equals(edge)){
                if(enoughCards(edge,isFerry))

                    if(claim(e,currentPlayer.getPlayerNum())){
                        currentPlayer.addEdge(e);
                        int index = players.indexOf(currentPlayer);
                        JOptionPane.showInputDialog("Player"+
                            index+"claimed");
                        currentPlayer = getNextPlayer(currentPlayer);
                        break;
                    }else if(!enoughCards(edge,isFerry)){
                    }
                    else{
                }
                if(claim(e,currentPlayer.getPlayerNum())&&
                enoughCards(edge,isFerry)){
                    currentPlayer.addEdge(e);
                    int index = players.indexOf(currentPlayer);
                    JOptionPane.showInputDialog("Player"+index+"claimed");
                    currentPlayer = getNextPlayer(currentPlayer);
                    break;
                }else if(!enoughCards(edge,isFerry)){

                }
                else{

                }
            }   
        }
        return false;
    }

    /**
     * resets the turn for the specific player
     */
    public void reset(){
        cardsSelected = 0;
        citiesSelected = new City[2];
        numCities = 0;
    }

    /**
     * determines if there is enough cards needed
     * 
     * @param     e     Edge in questino
     * @param     numFerry     determines how many Ferry route pieces
     * 
     * @return     boolean     true if possible, else false
     */
    public boolean enoughCards(Edge e,int numFerry){
        int length = e.getLength();
        Color c = e.getColor();
        int myLength = 0;
        int i =0;
        int numLoc = numLoc = currentPlayer.getColoredCards()[8];
        switch(c){

            case BLACK: myLength=currentPlayer.getColoredCards()[0];
            i = 0;
            break;
            case RED:  myLength=currentPlayer.getColoredCards()[1];
            i = 1;
            break;
            case ORANGE:  myLength=currentPlayer.getColoredCards()[2];
            i = 2;
            break;
            case YELLOW: myLength=currentPlayer.getColoredCards()[3];
            i = 3;
            break;
            case GREEN:  myLength=currentPlayer.getColoredCards()[4];
            i = 4;
            break;
            case BLUE:  myLength=currentPlayer.getColoredCards()[5];
            i = 5;
            break;
            case PURPLE:  myLength=currentPlayer.getColoredCards()[6];
            i = 6;
            break;
            case WHITE:  myLength=currentPlayer.getColoredCards()[7];
            i = 7;
            break;
            case LOCO:  myLength=currentPlayer.getColoredCards()[8];
            i =8;

            break;
        }
        if(numFerry==0&&myLength>=length){
            currentPlayer.getColoredCards()[i]=myLength-length;
            return true;
        }
        else if(numFerry==1&&numLoc>=1&&myLength>=(length-1))
        {
            currentPlayer.getColoredCards()[i]=myLength-length+1;
            currentPlayer.getColoredCards()[8]= numLoc-1;
            return true;   
        }
        return false;
    }

    /**
     * determines if an edge can be claimed
     * 
     * @param     e     edge in question
     * @param     id     player
     * 
     * @return boolean     true if possible, else false
     */
    public boolean claim(Edge e, int id){

        for(Edge f: edges.getEdges()){
            if(f.equals(e)){
                if(f.getClaimed()!=-1){
                    return false;
                }
                else{
                    f.setClaimed(id);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * allows next player to play game
     * 
     * @param     p     current player
     */
    public Player getNextPlayer(Player p){
        int index = players.indexOf(p);
        if(index == players.size()-1) {
            index =0;
            return players.get(0);
        }
        else{
            return currentPlayer=players.get(index+1);
        }

    }

    /**
     * events occuring on mouse pressed
     * 
     * @param     e     MouseEvent
     */
    public void mousePressed( MouseEvent e ) {}

    /**
     * events occuring on mouse moved
     * 
     * @param     e     MouseEvent
     */
    public void mouseMoved( MouseEvent e){}

    /**
     * events occuring on mouse dragged
     * 
     * @param     e     MouseEvent
     */
    public void mouseDragged(MouseEvent e){}

    /**
     * events occuring on mouse released
     * 
     * @param     e     MouseEvent
     */
    public void mouseReleased( MouseEvent e ) 
    {  
        mx = e.getX();
        my = e.getY();

        repaint();
        e.consume();
    }

    /**
     * repaints all images that need to be redraw 
     * after board is clicked on
     * 
     * @param     gg     Graphics
     */
    public void paint( Graphics gg ) {
        Graphics2D g = (Graphics2D)(gg);
        g.drawImage(wood, 0, 0, width, height, this);
        if(GAMESTART == false){
            g.drawImage(origBackGround, 
                (width/2)-150, (height/2)-420, 300, 300, this);
            g.drawImage(instructions, 
                (width/2)-500, (height/2)-100, 1000, 550, this);
        }
        g.drawImage(backGround, 0, 0, 900, 600, this);
        g.drawImage(paper, 230, 700, 500, 275, this);

        if(GAMESTART == true){
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(800,750,200,200);
            g.drawImage(compass, 800,715, 200,200,this);
            g.setColor(currentPlayer.getColor());
            g.setFont(new Font("SanSerif", 
                    Font.BOLD, 18));
            g.drawString("Destination Finder:", 825,710);
            if(drawCity1!=null&&drawCity2!=null){
                g.fillOval(drawCity1.getX()-10,
                    drawCity1.getY()-10,20,20);
                g.fillOval(drawCity2.getX()-10,
                    drawCity2.getY()-10,20,20);
                drawCity1 = null;
                drawCity2 = null;
            }
        }

        g.setColor(java.awt.Color.BLACK);

        g.drawString(cardDeck.size()+"",pileRect.x,pileRect.y);
        g.setColor(java.awt.Color.BLACK);
        playerTurnController(g, currentPlayer.getId());

        for(int i =0;i<9;i++){
            g.drawImage(coloredCards[i],0+i*100,600,100,75,this);
            g.drawString(currentPlayer.getColoredCards()[i]
                +"",i*100+50,685);
        }

        for(int i =0;i<5;i++){
            if(initialCardsDealt[i]!=null)
                g.drawImage(initialCardsDealt[i],
                    dealtCards[i].x,dealtCards[i].y,
                    dealtCards[i].width,dealtCards[i].height,this);

        }

        g.drawImage(pile,pileRect.x,pileRect.y,
            pileRect.width,pileRect.height,this);
        g.fillRect(destinationPileRect.x,
            destinationPileRect.y,destinationPileRect.width,
            destinationPileRect.height);
        g.drawImage(destinationPile,destinationPileRect.x,
            destinationPileRect.y,destinationPileRect.width,
            destinationPileRect.height,this);

        g.drawImage(table, 925,25,300,300, this);
        g.drawString("Player",935,109);
        g.drawString("Remaining Trains",1000,109);
        g.drawString("Points",1150,109);

        if(numPlayers == 2){
            g.setColor(players.get(0).getColor());
            g.drawString(players.get(0).getName(),935,135);
            g.drawString(Integer.toString
                (players.get(0).getTrains()),1050,135);
            g.drawString(Integer.toString
                (players.get(0).numPoints()),1150,135);

            g.setColor(players.get(1).getColor());
            g.drawString(players.get(1).getName(),935,165);
            g.drawString(Integer.toString
                (players.get(1).getTrains()),1050,165);
            g.drawString(Integer.toString
                (players.get(1).numPoints()),1150,165);
        }
        else if(numPlayers == 3){
            g.setColor(players.get(0).getColor());
            g.drawString(players.get(0).getName(),935,135);
            g.drawString(Integer.toString
                (players.get(0).getTrains()),1050,135);
            g.drawString(Integer.toString
                (players.get(0).numPoints()),1150,135);

            g.setColor(players.get(1).getColor());
            g.drawString(players.get(1).getName(),935,165);
            g.drawString(Integer.toString
                (players.get(1).getTrains()),1050,165);
            g.drawString(Integer.toString
                (players.get(1).numPoints()),1150,165);

            g.setColor(players.get(2).getColor());
            g.drawString
            (players.get(2).getName(),935,195);
            g.drawString
            (Integer.toString(players.get(2).getTrains()),1050,195);
            g.drawString
            (Integer.toString(players.get(2).numPoints()),1150,195);
        }

        for(Player p : players){
            ArrayList<Edge> myEdges = p.getEdges();
            java.awt.Color c;
            c = p.getColor();
            g.setColor(c);
            for(Edge r : myEdges){
                g.setStroke(new BasicStroke(13));

                for(path myPath: myPaths.getPaths())
                {
                    if(myPath.getCity1().equals(r.getCity1())&&
                    myPath.getCity2().equals(r.getCity2())||
                    myPath.getCity2().equals(r.getCity1())&&
                    myPath.getCity1().equals(r.getCity2())){
                        ArrayList<Integer> x = myPath.getX();
                        ArrayList<Integer> y = myPath.getY();

                        for(int i = 0;i<x.size()-1;i++){
                            g.drawLine(x.get(i),y.get(i),
                                x.get(i+1),y.get(i+1));
                        }
                    }
                }
            }
        }

    }

    /**
     * Prelimnary controller for the player turns. Changes Gaphics of the
     * screen according to hows turn it is.
     * 
     * Items printed include: 
     * Turn identifier image, player destinaiton cards
     * 
     * @param g Graphic object
     * @param currentPlayerNumber the current player whose turn it is
     */
    public void playerTurnController(Graphics g, int currentPlayerNumber){
        int playerTurn = currentPlayerNumber;
        g.setFont(new Font("SanSerif", Font.BOLD, 24));     
        if(numPlayers==2){           
            if(playerTurn==1){
                String message = new String(players.get(0).getName());
                if( (message.equalsIgnoreCase("Darren")) || 
                (message.equalsIgnoreCase("DTL")) || 
                (message.equalsIgnoreCase("Lim")) || 
                (message.equalsIgnoreCase("DL"))){
                    g.drawImage(DTL, 10, 730, 200,200,this);

                }
                else{
                    g.drawImage(asianGuy,10, 730, 200, 200, this);
                }

                g.setColor(players.get(0).getColor());
                g.drawString(message, 75, 720);
                printDestinaitons(g);
            }
            else{

                String message = new String(players.get(1).getName());
                if( (message.equalsIgnoreCase("Darren")) || 
                (message.equalsIgnoreCase("DTL")) || 
                (message.equalsIgnoreCase("Lim")) || 
                (message.equalsIgnoreCase("DL"))){
                    g.drawImage(DTL2, 10, 730, 200,200,this);

                }
                else{
                    g.drawImage(greenSuitMan,20, 730, 150, 250, this);
                }

                g.setColor(players.get(1).getColor());
                g.drawString(message, 75, 720);
                printDestinaitons(g);
            }
        }
        else if(numPlayers == 3){            
            if(playerTurn==1){
                String message = new String(players.get(0).getName());
                if( (message.equalsIgnoreCase("Darren")) || 
                (message.equalsIgnoreCase("DTL")) || 
                (message.equalsIgnoreCase("Lim")) || 
                (message.equalsIgnoreCase("DL"))){
                    g.drawImage(DTL, 10, 730, 200,200,this);

                }
                else{
                    g.drawImage(asianGuy,10, 730, 200, 200, this);
                }

                g.setColor(players.get(0).getColor());
                g.drawString(message, 75, 720);
                printDestinaitons(g);
            }
            else if(playerTurn==2){
                String message = new String(players.get(1).getName());
                if( (message.equalsIgnoreCase("Darren")) || 
                (message.equalsIgnoreCase("DTL")) || 
                (message.equalsIgnoreCase("Lim")) || 
                (message.equalsIgnoreCase("DL"))){
                    g.drawImage(DTL2, 10, 730, 200,200,this);

                }
                else{
                    g.drawImage(greenSuitMan,20, 730, 150, 250, this);
                }
                g.setColor(players.get(1).getColor());
                g.drawString(message, 75, 720);
                printDestinaitons(g);
            }
            else{
                String message = new String(players.get(2).getName());
                if( (message.equalsIgnoreCase("Darren")) || 
                (message.equalsIgnoreCase("DTL")) || 
                (message.equalsIgnoreCase("Lim")) || 
                (message.equalsIgnoreCase("DL"))){
                    g.drawImage(DTL3, 30, 730, 150,200,this);

                }
                else{
                    g.drawImage(blueRibbon,20, 730, 150, 250, this);
                }
                g.setColor(players.get(2).getColor());
                g.drawString(message, 75, 720);
                printDestinaitons(g);
            }
        }
        g.setColor(java.awt.Color.BLACK);
    }

    /**
     * This method prints out the Array of a the current Player's
     * destination cards to the screen
     * 
     * @param g Graphic object
     */
    public void printDestinaitons(Graphics g){
        ArrayList<DestinationCard> currentDestinationsToDisplay = 
            currentPlayer.getPlayerDestinations();
        g.setFont(new Font("SanSerif", Font.BOLD, 18));

        g.drawString("Destination Cards:", 258, 731);
        g.drawString("_______________",258,731);
        g.setFont(new Font("SanSerif",Font.PLAIN, 18));     
        for(int i = 0; i < currentDestinationsToDisplay.size(); i++){
            String dest =
                currentDestinationsToDisplay.get(i).getCity1() + " ";
            String dest2 = 
                currentDestinationsToDisplay.get(i).getCity2() + " ";
            String pointValue = 
                currentDestinationsToDisplay.get(i).getPoints() + " ";
            if(i< 8){
                g.drawString(dest + " --> "+ 
                    dest2 + ", " + pointValue,260,750+i*20); 
            }
            else{
                g.drawString(dest + " --> " + 
                    dest2 + ", " + pointValue, 500,750+((i-8)*20));
            }

        }
    }
    boolean FIRSTTIME = false;
    /**
     * launches the beginning of the game
     * asks all necessary prompt questions
     */
    public void launchIntro(){
        JRadioButton j1 = new JRadioButton("2 players");
        JRadioButton j2 = new JRadioButton("3 players");
        ButtonGroup group = new ButtonGroup();
        group.add(j1);
        group.add(j2);
        Object [] arr2 = {"Number Players",j1,j2};

        JOptionPane.showMessageDialog(null,
            arr2,"",JOptionPane.INFORMATION_MESSAGE);
        if(j1.isSelected()){
            numPlayers = 2;
        }
        else if(j2.isSelected()){
            numPlayers = 3;
        }
        int[] nums = {0,0,0,0};

        JRadioButton red = new JRadioButton("Red");
        JRadioButton blue = new JRadioButton("Blue");
        JRadioButton purple = new JRadioButton("Purple");
        JRadioButton green = new JRadioButton("Green");

        for(int i =0;i<numPlayers;i++){

            FIRSTTIME = true;
            String message = "Player " + (i+1);
            message = message + " Name:";

            String playerName = JOptionPane.showInputDialog(message);
            ArrayList<DestinationCard> chosen =  
                setUpDestinations(4,2,4);
            Player p = new Player(i+1, chosen);
            p.setPlayerName(playerName);
            ArrayList<Object> arr6 = new ArrayList<Object>();
            arr6.add("Chose a color.");

            ButtonGroup group2 = new ButtonGroup();
            if(nums[0]==0){
                group2.add(red);
                arr6.add(red);
            }
            if(nums[1]==0) {group2.add(blue);
                arr6.add(blue);
            }
            if(nums[2]==0) {group2.add(purple);
                arr6.add(purple);
            }
            if(nums[3]==0) {group2.add(green);
                arr6.add(green);
            }

            Object[] arr7 = arr6.toArray();
            JOptionPane.showMessageDialog(null,arr7,"",
                JOptionPane.INFORMATION_MESSAGE);

            if(nums[0]==0&&red.isSelected()){
                p.setPlayerColor(java.awt.Color.RED);
                nums[0]=1;
            }
            else if(nums[1]==0&&blue.isSelected()){
                p.setPlayerColor(java.awt.Color.BLUE);
                nums[1]=1;
            }else if(nums[2]==0&&purple.isSelected()){
                p.setPlayerColor(java.awt.Color.MAGENTA);
                nums[2]=1;
            }else if(nums[3]==0&&green.isSelected()){
                p.setPlayerColor(java.awt.Color.GREEN);
                nums[3]=1;
            }else{
                p.setPlayerColor(java.awt.Color.BLACK);
            }
            players.add(p);            
        }

        for(Player p : players){
            for(int i = 0;i<4;i++){
                Card c = cardDeck.deal();
                p.addColoredCard(c.getColor());
            }
        }

        remove(startGame);
        currentPlayer=players.get(0);
        String [] arr = {"Player 1 turn"};
        JOptionPane.showMessageDialog(null,arr,"",
            JOptionPane.INFORMATION_MESSAGE);
    }
    private boolean[] colorSelect = new boolean[4];
    /**
     * determines what color the user chose
     * 
     * @param     c     array of boolean
     * @return    string     chosen
     */
    public String colorSelection(boolean[] c){
        String options = " ";       
        for(int i = 0; i < c.length; i++){
            if(c[i] == false){
                options = options + i;
            }
        }
        options = options.replace("0","Red ");
        options = options.replace("1","Blue ");
        options = options.replace("2","Purple ");
        options = options.replace("3","Green");
        return options;
    }

    /**
     * determines which user is associated with which color
     * 
     * @param     playerColor     string
     * 
     * @return     java.awt.Color     player color
     */
    public java.awt.Color whichColor(String playerColor){
        if(playerColor.equals(null)){
            return java.awt.Color.BLACK;
        }
        else if(playerColor.equalsIgnoreCase("red")){

            colorSelect[0] = true;
            return java.awt.Color.RED;
        }
        else if(playerColor.equalsIgnoreCase("blue")){

            colorSelect[1] = true;
            return java.awt.Color.BLUE;
        }
        else if(playerColor.equalsIgnoreCase("purple")){

            colorSelect[2] = true;
            return java.awt.Color.MAGENTA;
        }
        else if(playerColor.equalsIgnoreCase("green")){

            colorSelect[3] = true;
            return java.awt.Color.GREEN;
        }
        return java.awt.Color.BLACK;
    }

    /**
     * displays destination cards in the applet
     * 
     * @param     numDestinationCards    quantity of destination cards
     * @param     numrequiredforselectionlower     number required
     * @param     numrequiredforselectionupper     number required
     * 
     * @return arraylist<Destinationcard>     
     * array list of destination cards
     */
    public ArrayList<DestinationCard> setUpDestinations
    (int numDestinationCards, int numRequiredForSelectionLower,
    int numRequiredForSelectionUpper){
        ArrayList<DestinationCard> local = 
            new ArrayList<DestinationCard>();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
        for(int i =0;i<numDestinationCards-1;i++){
            DestinationCard destCard = destinationDeck.deal();
            local.add(destCard);
            JCheckBox destCheckBox = new 
                JCheckBox(destCard.getCity1()+" - "
                    +destCard.getCity2()+" "+destCard.getPoints());
            checkBoxes.add(destCheckBox);
        }

        if(FIRSTTIME){
            DestinationCard destCardLong = destinationDeck.dealLong();
            local.add(destCardLong);
            JCheckBox destCheckBoxLong = new 
                JCheckBox(destCardLong.getCity1()+" - "+
                    destCardLong.getCity2()+" "+destCardLong.getPoints());
            checkBoxes.add(destCheckBoxLong);
        }else{
            DestinationCard destCard = destinationDeck.deal();
            local.add(destCard);
            JCheckBox destCheckBox = new 
                JCheckBox(destCard.getCity1()+" - "
                    +destCard.getCity2()+" "+destCard.getPoints());
            checkBoxes.add(destCheckBox);
        }
        String msg = "Pick your destinations:"; 
        ArrayList<Object> msgContent = new ArrayList<Object>();
        msgContent.add(msg);
        for(int i =0;i<checkBoxes.size();i++){
            msgContent.add(checkBoxes.get(i));
        }
        Object [] arr = msgContent.toArray();

        String stringforGUI = "Welcome!";
        ArrayList<DestinationCard> chosenDest =
            new ArrayList<DestinationCard>();

        while(true){
            int n =  JOptionPane.showConfirmDialog 
                ( null,  arr,  stringforGUI, JOptionPane.YES_NO_OPTION); 
            int count = 0;
            for(int i =0;i<checkBoxes.size();i++){
                if(checkBoxes.get(i).isSelected()){
                    count++;
                    chosenDest.add(local.get(i));
                }
            }

            if(n ==JOptionPane.YES_OPTION){
                if(count>=numRequiredForSelectionLower&&
                n<=numRequiredForSelectionUpper){
                    break;
                }
            }
            stringforGUI = 
            "You don't have the right number of cards!";
            chosenDest = new ArrayList<DestinationCard>();
        }
        FIRSTTIME = false;
        return chosenDest;

    }

    /**
     * associates the card image with the color
     * 
     * @param     c     card
     * 
     * @return     image    image of card
     */
    public Image getImage(Card c){
        switch(c.getColor()){
            case BLACK: return getImage(getDocumentBase(),"black.gif");
            case RED:  return getImage(getDocumentBase(),"red.gif");
            case ORANGE:  return getImage(getDocumentBase(),"brown.gif");
            case YELLOW: return getImage(getDocumentBase(),"yellow.gif");
            case GREEN:  return getImage(getDocumentBase(),"green.gif");
            case BLUE:  return getImage(getDocumentBase(),"blue.gif");
            case PURPLE:  return getImage(getDocumentBase(),"purple.gif");
            case WHITE:  return getImage(getDocumentBase(),"white.gif");
            case LOCO:  return getImage(getDocumentBase(),"loco.gif");
        }
        return null;
    }
}

    