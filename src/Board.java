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
    public static final int BOARD_WIDTH = 1000;
    public static final int BOARD_HEIGHT = 512;
    public static final Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
    
    public Board() {
        countries = new Country[9];
        countries[0] = new Country("Alaska", 30, 30, 80, 60);
        countries[1] = new Country("Alberta", 110, 80, 90, 50);
        countries[2] = new Country("Central America", 140, 220, 60, 50);
        countries[3] = new Country("Eastern United States", 180, 130, 70, 90);
        countries[4] = new Country("Greenland", 250, 250, 50, 50);
        countries[5] = new Country("Northwest Territory", 110, 30, 120, 50);
        countries[6] = new Country("Ontario", 200, 80, 50, 50);
        countries[7] = new Country("Quebec", 250, 250, 50, 50);
        countries[8] = new Country("Western United States", 110, 130, 70, 90);
        countries[9] = new Country("Argentina", 250, 250, 50, 50);
        
        continents = new TreeMap<Integer, Set<Country>>();
        for (int i = 0; i < continents.size(); i++) {
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
        thisContinent = continents.get(2);
        for (int i = 13; i < 20; i++) {
            thisContinent.add(countries[i]);
        }
        thisContinent = continents.get(3);
        for (int i = 20; i < 26; i++) {
            thisContinent.add(countries[i]);
        }
        thisContinent = continents.get(4);
        for (int i = 26; i < 38; i++) {
            thisContinent.add(countries[i]);
        }
        thisContinent = continents.get(5);
        for (int i = 38; i < 42; i++) {
            thisContinent.add(countries[i]);
        }
        
        initialCountries(4);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
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
    
    public void initialCountries(int numPlayers) {
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
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
    
    public static void main(String args[]) {
        Board board = new Board();
        board.shuffle();
        for (int i = 0; i < board.countries.length; i++) {
            System.out.println(board.countries[i].getName());
        }
    }

}
