import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel{
    public static Map<Integer, Set<Country>> continents;
    public static final int[] continentBonuses = {5, 2, 5, 3, 7, 2};
    Country[] countries;
    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 600;
    public static final Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
    
    enum Mode {
        PlacingMode, AttackFromMode, AttackToMode, NewCountryMode, ReinforceMode
    }
    
    private Mode mode = Mode.PlacingMode;
    
    public Board() {
        
        initializeCountries();       
        
        continents = new TreeMap<Integer, Set<Country>>();
        for (int i = 0; i < 2; i++) {
            continents.put(i, new TreeSet<Country>());
        }
        Set<Country> thisContinent = continents.get(0);
        for (int i = 0; i < 9; i++) {
            thisContinent.add(countries[i]);
        }
        thisContinent = continents.get(1);
        for (int i = 9; i < 13; i++) {
            thisContinent.add(countries[i]);
        }
//        thisContinent = continents.get(2);
//        for (int i = 13; i < 20; i++) {
//            thisContinent.add(countries[i]);
//        }
//        thisContinent = continents.get(3);
//        for (int i = 20; i < 26; i++) {
//            thisContinent.add(countries[i]);
//        }
//        thisContinent = continents.get(4);
//        for (int i = 26; i < 38; i++) {
//            thisContinent.add(countries[i]);
//        }
//        thisContinent = continents.get(5);
//        for (int i = 38; i < 42; i++) {
//            thisContinent.add(countries[i]);
//        }
        
        initialCountryOwners(4);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                switch (mode) {
                case PlacingMode: 
                    break;
                default: 
                    break;
                }
                Point mouse = e.getPoint();
                for (Country c : countries) {
                    if (c.inBounds(mouse)) {
                        c.highlight();
                        repaint();
                    }
                }
            }

        });

    }
    
    private void initializeCountries() {
        countries = new Country[42];
        countries[0] = new Country("Alaska", 30, 30, 80, 60);
        countries[1] = new Country("Alberta", 110, 80, 80, 50);
        countries[2] = new Country("Central America", 140, 210, 65, 60);
        countries[3] = new Country("Eastern United States", 200, 130, 50, 80);
        countries[4] = new Country("Greenland", 280, 20, 80, 60);
        countries[5] = new Country("Northwest Territory", 110, 30, 120, 50);
        countries[6] = new Country("Ontario", 190, 80, 60, 50);
        countries[7] = new Country("Quebec", 250, 90, 50, 70);
        countries[8] = new Country("Western United States", 110, 130, 90, 80);
        countries[9] = new Country("Venezuela", 200, 270, 70, 50);
        countries[10] = new Country("Brazil", 230, 320, 90, 120);
        countries[11] = new Country("Peru", 190, 320, 40, 120);
        countries[12] = new Country("Argentina", 200, 440, 70, 100);
        countries[13] = new Country("Great Britain", 400, 110, 20, 40);
        countries[14] = new Country("Iceland", 370, 90, 20, 20);
        countries[15] = new Country("Northern Europe", 440, 140, 60, 40);
        countries[16] = new Country("Scandinavia", 450, 20, 50, 100);
        countries[17] = new Country("Ukraine", 500, 70, 100, 110);
        countries[18] = new Country("Southern Europe", 440, 180, 80, 40);
        countries[19] = new Country("Western Europe", 380, 160, 60, 40);
        countries[20] = new Country("Madagascar", 540, 400, 20, 40);
        countries[21] = new Country("Egypt", 450, 240, 70, 40);
        countries[22] = new Country("North Africa", 360, 240, 90, 90);
        countries[23] = new Country("East Africa", 450, 280, 80, 110);
        countries[24] = new Country("Congo", 390, 330, 60, 60);
        countries[25] = new Country("South Africa", 420, 390, 80, 110);
        countries[26] = new Country("Middle East", 520, 180, 110, 70);
        countries[27] = new Country("Afghanistan", 600, 110, 70, 70);
        countries[28] = new Country("Ural", 600, 55, 80, 55);
        countries[29] = new Country("India", 630, 180, 60, 100);
        countries[30] = new Country("China", 670, 110, 90, 70);
        countries[31] = new Country("Siberia", 680, 35, 30, 75);
        countries[32] = new Country("Siam", 690, 180, 40, 70);
        countries[33] = new Country("Mongolia", 710, 80, 60, 30);
        countries[34] = new Country("Irkutsk", 710, 55, 60, 25);
        countries[35] = new Country("Yakutsk", 710, 30, 60, 25);
        countries[36] = new Country("Kamchatka", 770, 30, 30, 70);
        countries[37] = new Country("Japan", 810, 80, 20, 40);
        countries[38] = new Country("Indonesia", 730, 300, 30, 30);
        countries[39] = new Country("Western Australia", 740, 360, 60, 70);
        countries[40] = new Country("Eastern Australia", 800, 360, 50, 70);
        countries[41] = new Country("New Guinea", 800, 310, 40, 25);
        
    }
    
    private Country[] shuffle() {
        Country[] shuffledCountries = countries.clone();
        for (int i = 0; i < countries.length; i++) {
            int j = i + (int) ((countries.length - i) * Math.random());
            Country temp = shuffledCountries[i];
            shuffledCountries[i] = shuffledCountries[j];
            shuffledCountries[j] = temp;
        }
        return shuffledCountries;
    }
    
    public void initialCountryOwners(int numPlayers) {
        int playerID = 0;
        Country[] shuffledCountries = shuffle();
        for (int i = 0; i < countries.length; i++) {
            shuffledCountries[i].ownerID = playerID;
            playerID = (playerID + 1) % numPlayers;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Country c : countries) {
            c.draw(g);   
        }
        
        g.drawLine(0, 60, 30, 60);
        g.drawLine(800, 60, 900, 60);
        
        g.drawLine(230, 55, 280, 50);
        g.drawLine(250, 80, 280, 50);
        g.drawLine(300, 90, 310, 80);
        g.drawLine(360, 80, 370, 90);
        g.drawLine(390, 95, 410, 110);
        g.drawLine(410, 150, 410, 160);
        g.drawLine(410, 110, 450, 70);
        g.drawLine(420, 130, 440, 140);
        g.drawLine(475, 120, 470, 140);
        g.drawLine(480, 220, 485, 240);
        g.drawLine(440, 220, 405, 240);
        g.drawLine(410, 200, 405, 240);
        g.drawLine(530, 280, 575, 250);
        g.drawLine(530, 390, 540, 400);
        g.drawLine(500, 445, 540, 420);
        g.drawLine(320, 320, 360, 285);
        g.drawLine(810, 120, 770, 110);
        g.drawLine(810, 80, 800, 65);
        g.drawLine(710, 250, 745, 300);
        g.drawLine(760, 315, 800, 323);
        g.drawLine(745, 330, 770, 360);
        g.drawLine(820, 335, 825, 360);
        g.drawLine(770, 360, 800, 335);
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
    
    public static void main(String args[]) {
        Board board = new Board();
        //board.initialCountries(4);
        for (int i = 0; i < board.countries.length; i++) {
            System.out.println(board.countries[i].getName());
        }
    }

}
