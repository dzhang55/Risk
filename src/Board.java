import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel{
    private static List<Set<Country>> continents;
    //private static ArrayList<Integer[]> playerDecks;
    private static final int[] continentBonuses = {5, 2, 5, 3, 7, 2};
    static Country[] countries;
    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 600;
    public static final Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, 
        Color.ORANGE, Color.MAGENTA};
    private static int turn = 0;
    // private static ArrayList<Player> players;
    private static int troopsToPlace;
    private static Country selectedCountry;
    private static Country selectedSecondCountry;
    private final JLabel turnInfo;
    private final JLabel[] cardInfo;
    private static Player[] players;

    enum Mode {
        UseCardMode, InitialPlacingMode, PlacingMode, AttackFromMode, AttackToMode, 
        KeepAttackingMode, NewCountryMode, FortifyFromMode, FortifyToMode, 
        KeepFortifyingMode, GameOverMode;
    }

    private Mode mode = Mode.InitialPlacingMode;

    public Board(final JLabel turnInfo, final JLabel[] cardInfo, int numPlayers) {

        this.turnInfo = turnInfo;
        this.cardInfo = cardInfo;

        initializeCountries();   
        initializeContinents();
        initializePlayers(numPlayers);
        initialCountryOwners(numPlayers);
        Player.initialDeck();
        initialTroopsToPlace();
        
        setCardLabels();

        turnInfo.setText(getStringForMode());

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();

                switch (mode) {
                case UseCardMode:
                    break;
                case InitialPlacingMode: 
                    placeSoldier(mouse);
                    break;
                case PlacingMode: 
                    placeSoldier(mouse);
                    break;
                case AttackFromMode:
                    selectOwnerCountry(mouse);
                    break;
                case AttackToMode:
                    selectEnemyCountry(mouse);
                    break;
                case KeepAttackingMode:
                    if (selectedSecondCountry.inBounds(mouse)) {
                        attack(selectedCountry, selectedSecondCountry);
                        checkOutcome();
                    }
                    break;
                case NewCountryMode:
                    placeSoldierNewCountry(mouse);
                    break;   
                case FortifyFromMode:
                    selectOwnerCountry(mouse);
                    break;
                case FortifyToMode:
                    selectFortify(mouse);
                    break;
                case KeepFortifyingMode:
                    if (selectedSecondCountry.inBounds(mouse)) {
                        fortify();
                    }
                    break;
                case GameOverMode:
                    break;
                }
                setCardLabels();
                turnInfo.setText(getStringForMode());
                repaint();
            }

        });

    }

    private void initializePlayers(int numPlayers) {
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player();
        }
    }


    /* creates the ArrayList of Set<Country> that represents continents
     * necessary for checking continent bonuses
     */
    private void initializeContinents() {

        continents = new ArrayList<Set<Country>>();
        for (int i = 0; i < 6; i++) {
            continents.add(i, new TreeSet<Country>());
        }
        // North America
        Set<Country> thisContinent = continents.get(0);
        for (int i = 0; i < 9; i++) {
            thisContinent.add(countries[i]);
        }

        // South America
        thisContinent = continents.get(1);
        for (int i = 9; i < 13; i++) {
            thisContinent.add(countries[i]);
        }

        // Europe
        thisContinent = continents.get(2);
        for (int i = 13; i < 20; i++) {
            thisContinent.add(countries[i]);
        }

        // Africa
        thisContinent = continents.get(3);
        for (int i = 20; i < 26; i++) {
            thisContinent.add(countries[i]);
        }

        // Asia
        thisContinent = continents.get(4);
        for (int i = 26; i < 38; i++) {
            thisContinent.add(countries[i]);
        }

        // Australia
        thisContinent = continents.get(5);
        for (int i = 38; i < 42; i++) {
            thisContinent.add(countries[i]);
        }
    }

    /* initializes the 42 countries that make up the standard risk map
     */
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

        countries[0].adjacentCountries = new TreeSet<Country>();
        countries[0].adjacentCountries.add(countries[1]);
        countries[0].adjacentCountries.add(countries[5]);
        countries[0].adjacentCountries.add(countries[36]);

        countries[1].adjacentCountries = new TreeSet<Country>();
        countries[1].adjacentCountries.add(countries[5]);
        countries[1].adjacentCountries.add(countries[6]);
        countries[1].adjacentCountries.add(countries[8]);

        countries[2].adjacentCountries = new TreeSet<Country>();
        countries[2].adjacentCountries.add(countries[3]);
        countries[2].adjacentCountries.add(countries[8]);
        countries[2].adjacentCountries.add(countries[9]);

        countries[3].adjacentCountries = new TreeSet<Country>();
        countries[3].adjacentCountries.add(countries[6]);
        countries[3].adjacentCountries.add(countries[7]);
        countries[3].adjacentCountries.add(countries[8]);

        countries[4].adjacentCountries = new TreeSet<Country>();
        countries[4].adjacentCountries.add(countries[5]);
        countries[4].adjacentCountries.add(countries[6]);
        countries[4].adjacentCountries.add(countries[7]);
        countries[4].adjacentCountries.add(countries[14]);

        countries[5].adjacentCountries = new TreeSet<Country>();
        countries[5].adjacentCountries.add(countries[6]);

        countries[6].adjacentCountries = new TreeSet<Country>();
        countries[6].adjacentCountries.add(countries[7]);
        countries[6].adjacentCountries.add(countries[8]);

        countries[7].adjacentCountries = new TreeSet<Country>();

        countries[8].adjacentCountries = new TreeSet<Country>();

        countries[9].adjacentCountries = new TreeSet<Country>();
        countries[9].adjacentCountries.add(countries[10]);
        countries[9].adjacentCountries.add(countries[11]);

        countries[10].adjacentCountries = new TreeSet<Country>();
        countries[10].adjacentCountries.add(countries[11]);
        countries[10].adjacentCountries.add(countries[12]);
        countries[10].adjacentCountries.add(countries[22]);

        countries[11].adjacentCountries = new TreeSet<Country>();
        countries[11].adjacentCountries.add(countries[12]);

        countries[12].adjacentCountries = new TreeSet<Country>();

        countries[13].adjacentCountries = new TreeSet<Country>();
        countries[13].adjacentCountries.add(countries[14]);
        countries[13].adjacentCountries.add(countries[15]);
        countries[13].adjacentCountries.add(countries[16]);
        countries[13].adjacentCountries.add(countries[19]);

        countries[14].adjacentCountries = new TreeSet<Country>();

        countries[15].adjacentCountries = new TreeSet<Country>();
        countries[15].adjacentCountries.add(countries[16]);
        countries[15].adjacentCountries.add(countries[17]);
        countries[15].adjacentCountries.add(countries[18]);
        countries[15].adjacentCountries.add(countries[19]);

        countries[16].adjacentCountries = new TreeSet<Country>();
        countries[16].adjacentCountries.add(countries[17]);

        countries[17].adjacentCountries = new TreeSet<Country>();
        countries[17].adjacentCountries.add(countries[18]);
        countries[17].adjacentCountries.add(countries[26]);
        countries[17].adjacentCountries.add(countries[27]);
        countries[17].adjacentCountries.add(countries[28]);

        countries[18].adjacentCountries = new TreeSet<Country>();
        countries[18].adjacentCountries.add(countries[19]);
        countries[18].adjacentCountries.add(countries[21]);
        countries[18].adjacentCountries.add(countries[22]);
        countries[18].adjacentCountries.add(countries[26]);

        countries[19].adjacentCountries = new TreeSet<Country>();
        countries[19].adjacentCountries.add(countries[22]);

        countries[20].adjacentCountries = new TreeSet<Country>();
        countries[20].adjacentCountries.add(countries[23]);
        countries[20].adjacentCountries.add(countries[25]);

        countries[21].adjacentCountries = new TreeSet<Country>();
        countries[21].adjacentCountries.add(countries[22]);
        countries[21].adjacentCountries.add(countries[23]);
        countries[21].adjacentCountries.add(countries[26]);

        countries[22].adjacentCountries = new TreeSet<Country>();
        countries[22].adjacentCountries.add(countries[23]);
        countries[22].adjacentCountries.add(countries[24]);

        countries[23].adjacentCountries = new TreeSet<Country>();
        countries[23].adjacentCountries.add(countries[24]);
        countries[23].adjacentCountries.add(countries[25]);
        countries[23].adjacentCountries.add(countries[26]);

        countries[24].adjacentCountries = new TreeSet<Country>();
        countries[24].adjacentCountries.add(countries[25]);

        countries[25].adjacentCountries = new TreeSet<Country>();

        countries[26].adjacentCountries = new TreeSet<Country>();
        countries[26].adjacentCountries.add(countries[27]);
        countries[26].adjacentCountries.add(countries[29]);

        countries[27].adjacentCountries = new TreeSet<Country>();
        countries[27].adjacentCountries.add(countries[28]);
        countries[27].adjacentCountries.add(countries[29]);
        countries[27].adjacentCountries.add(countries[30]);

        countries[28].adjacentCountries = new TreeSet<Country>();
        countries[28].adjacentCountries.add(countries[30]);
        countries[28].adjacentCountries.add(countries[31]);

        countries[29].adjacentCountries = new TreeSet<Country>();
        countries[29].adjacentCountries.add(countries[30]);
        countries[29].adjacentCountries.add(countries[32]);

        countries[30].adjacentCountries = new TreeSet<Country>();
        countries[30].adjacentCountries.add(countries[31]);
        countries[30].adjacentCountries.add(countries[32]);
        countries[30].adjacentCountries.add(countries[33]);

        countries[31].adjacentCountries = new TreeSet<Country>();
        countries[31].adjacentCountries.add(countries[33]);
        countries[31].adjacentCountries.add(countries[34]);
        countries[31].adjacentCountries.add(countries[35]);

        countries[32].adjacentCountries = new TreeSet<Country>();
        countries[32].adjacentCountries.add(countries[38]);

        countries[33].adjacentCountries = new TreeSet<Country>();
        countries[33].adjacentCountries.add(countries[34]);
        countries[33].adjacentCountries.add(countries[36]);
        countries[33].adjacentCountries.add(countries[37]);

        countries[34].adjacentCountries = new TreeSet<Country>();
        countries[34].adjacentCountries.add(countries[35]);
        countries[34].adjacentCountries.add(countries[36]);

        countries[35].adjacentCountries = new TreeSet<Country>();
        countries[35].adjacentCountries.add(countries[36]);

        countries[36].adjacentCountries = new TreeSet<Country>();
        countries[36].adjacentCountries.add(countries[37]);

        countries[37].adjacentCountries = new TreeSet<Country>();

        countries[38].adjacentCountries = new TreeSet<Country>();
        countries[38].adjacentCountries.add(countries[39]);
        countries[38].adjacentCountries.add(countries[41]);

        countries[39].adjacentCountries = new TreeSet<Country>();
        countries[39].adjacentCountries.add(countries[40]);
        countries[39].adjacentCountries.add(countries[41]);

        countries[40].adjacentCountries = new TreeSet<Country>();
        countries[40].adjacentCountries.add(countries[41]);

        countries[41].adjacentCountries = new TreeSet<Country>();

        for (int i = 0; i < countries.length; i++) {
            for (Country c : countries[i].adjacentCountries) {
                c.adjacentCountries.add(countries[i]);
            }
        }

    }

    /* creates a shuffled array of countries
     */
    private Country[] shuffleCountries() {
        Country[] shuffledCountries = countries.clone();
        for (int i = 0; i < countries.length; i++) {
            int j = i + (int) ((countries.length - i) * Math.random());
            Country temp = shuffledCountries[i];
            shuffledCountries[i] = shuffledCountries[j];
            shuffledCountries[j] = temp;
        }
        return shuffledCountries;
    }

    /* iterates through a shuffled array of countries to randomly
     * assign owners to countries
     * @param numPlayers the number of players
     */
    private void initialCountryOwners(int numPlayers) {
        int playerID = 0;
        Country[] shuffledCountries = shuffleCountries();
        for (int i = 0; i < countries.length; i++) {
            players[playerID].countriesOwned.add(shuffledCountries[i]);
            playerID = (playerID + 1) % numPlayers;
        }
    }

    /* draws the connecting lines for countries that are adjacent
     * but not visibly so
     */
    private void drawLines(Graphics g) {
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

    /* ends the game if only one player is remaining
     */
    private void checkWin() {
        int numDead = 0;
        for (Player p : players) {
            if (p.dead) {
                numDead++;
            }
        }
        if (numDead == players.length - 1) {
            mode = Mode.GameOverMode;
            turnInfo.setText(getStringForMode());
            repaint();
        }
    }

    /* places soldier in a country provided the current player owns the country
     * moves on to next mode after all soldiers have been placed
     * @param: mouse for the mouse click location
     */
    private void placeSoldier(Point mouse) {
        if (troopsToPlace == 0) {
            System.out.println("this shouldn't happen");
            nextMode();
        }
        for (Country c : players[turn].countriesOwned) {
            if (c.inBounds(mouse)) {
                c.numSoldiers++;
                troopsToPlace--;
            }
        }
        if (troopsToPlace == 0) {
            if (mode == Mode.InitialPlacingMode){
                turn++;
                if (turn == players.length) {
                    turn = 0;
                    updateTroopsToPlace();
                    nextMode();  
                } else {
                    initialTroopsToPlace();
                }
            } else {
                nextMode();
            }
        }
    }

    /* calculates the initial troops for a player to place
     */
    private void initialTroopsToPlace() {

        int countriesOwned = players[turn].countriesOwned.size();
        troopsToPlace = 40 - countriesOwned - (players.length - 2) * 5;
    }
    /* return true if current player owns the continent, false otherwise
     * @param continent index for continent 
     */
    private boolean continentOwned(int continent) {
        for (Country c : continents.get(continent)) {
            if (!players[turn].countriesOwned.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /* calculates the number of troops a player can place at 
     * the beginning of his/her turn
     */
    private void updateTroopsToPlace() {

        int countryBonus = players[turn].countriesOwned.size() / 3;
        troopsToPlace = Math.max(3, countryBonus);

        for (int i = 0; i < Board.continentBonuses.length; i++) {
            if (continentOwned(i)) {
                troopsToPlace += continentBonuses[i];
            }
        }
    }


    /* selects a country and stores it given that the current player owns it
     * @param mouse for the mouse click location
     */
    private void selectOwnerCountry(Point mouse) {
        for (Country c : players[turn].countriesOwned) {
            boolean enoughSoldiers = (mode == Mode.AttackFromMode && c.numSoldiers > 1) || 
                    mode == Mode.FortifyFromMode;
            if (c.inBounds(mouse) && enoughSoldiers) {
                selectedCountry = c;
                nextMode();
            }
        }
    }

    /* selects a country and stores it given that the current player does not own it
     * @param mouse for the mouse click location
     */
    private void selectEnemyCountry(Point mouse) {

        // unselect the country to attack from
        if (selectedCountry.inBounds(mouse)) {
            selectedCountry = null;
            mode = Mode.AttackFromMode;
            return;
        }
        for (Country c : selectedCountry.adjacentCountries) {
            if (c.inBounds(mouse) && !players[turn].countriesOwned.contains(c)) {
                selectedSecondCountry = c;
                attack(selectedCountry, selectedSecondCountry);
                checkOutcome();
                if (mode == Mode.AttackToMode) {
                    nextMode();
                }
            }
        }
    }

    /* simulates the dice rolling for an attack
     * number of dice is dependent on available soldiers
     * @param own for the attacking country
     * @param enemy for the defending country
     */
    private void attack(Country own, Country enemy) {
        int die2 = 0;
        int die3 = 0;
        int defDie2 = 0;

        int die1 = (int) Math.ceil(6 * Math.random());
        int defDie1 = (int) Math.ceil(6 * Math.random());

        if (own.numSoldiers > 2) {
            die2 = (int) Math.ceil(6 * Math.random());
            if (own.numSoldiers > 3) {
                die3 = (int) Math.ceil(6 * Math.random());
            }
        }
        if (enemy.numSoldiers > 1) {
            defDie2 = (int) Math.ceil(6 * Math.random());
        }

        int maxAtk = 0;
        int sndAtk = 0;;
        int thirdAtk = 0;

        int midAtk = Math.max(die1, die2);
        if (die3 > midAtk) {
            maxAtk = die3;
            sndAtk = midAtk;
        } else {
            maxAtk = midAtk;
            if (Math.min(die1, die2) > die3) {
                sndAtk = Math.min(die1, die2);
                thirdAtk = die3;
            } else {
                sndAtk = die3;
                thirdAtk = Math.min(die1, die2);
            } 
        }

        int maxDef = Math.max(defDie1, defDie2);
        if (maxAtk > maxDef) {
            enemy.numSoldiers--;
        } else {
            own.numSoldiers--;
        }
        int sndDef = Math.min(defDie1, defDie2);
        if (own.numSoldiers > 2 && enemy.numSoldiers > 1) {
            if (sndAtk > sndDef) {
                enemy.numSoldiers--;
            } else {
                own.numSoldiers--;
            }
        }

    }


    /* checks the number of available soldiers to see if a battle is over
     */
    private void checkOutcome() {
        if (selectedCountry.numSoldiers == 1) {
            selectedCountry = null;
            selectedSecondCountry = null;
            if (mode == Mode.AttackToMode) {
                nextMode();
            }
            nextMode();
            nextMode();
            return;
        }
        if (selectedSecondCountry.numSoldiers == 0) {
            if (!Player.wonCardAlready) {
                players[turn].winCard();
                Player.wonCardAlready = true;
            }

            if (mode == Mode.AttackToMode) {
                nextMode();
            }
            nextMode();
            conquer();
        }
    }

    /* takes all the troops remaining after a conquest and allow them to be placed
     */
    private void conquer() {
        Player enemy = null;
        for (Player p : players) {
            if (p.countriesOwned.contains(selectedSecondCountry)) {
                enemy = p;
            }
        }
        enemy.countriesOwned.remove(selectedSecondCountry);
        players[turn].countriesOwned.add(selectedSecondCountry);
        
        if (enemy.countriesOwned.isEmpty()) {
            enemy.dead = true;
            for (int i = 0; i < enemy.cards.length; i++) {
                players[turn].cards[i] += enemy.cards[i];
            }
        }
        checkWin();
        selectedSecondCountry.numSoldiers = 1;
        troopsToPlace = selectedCountry.numSoldiers - 2;
        selectedCountry.numSoldiers = 1;

        // deal with edge case where there are no remaining soldiers right after a conquest
        if (troopsToPlace == 0) {
            selectedCountry = null;
            selectedSecondCountry = null;
            nextMode();
        }
    }

    /* place a soldier in a newly conquered country 
     * if there are no more soldiers, move on to the next mode
     * @param mouse for the mouse click location
     */
    private void placeSoldierNewCountry(Point mouse) {

        if (selectedCountry.inBounds(mouse)) {
            troopsToPlace--;
            selectedCountry.numSoldiers++;
        }
        if (selectedSecondCountry.inBounds(mouse)) {
            troopsToPlace--;
            selectedSecondCountry.numSoldiers++;
        }

        if (troopsToPlace == 0) {
            selectedCountry = null;
            selectedSecondCountry = null;
            nextMode();
        }
    }

    /* fortifies a soldier from one country to another given that they are adjacent
     * @param mouse for the mouse click location
     */
    private void selectFortify(Point mouse) {
        if (selectedCountry.inBounds(mouse)) {
            selectedCountry = null;
            mode = Mode.FortifyFromMode;
            return;
        }

        for (Country c : selectedCountry.adjacentCountries) {
            if (c.inBounds(mouse) && players[turn].countriesOwned.contains(c)) {
                selectedSecondCountry = c;
                fortify();
                nextMode();
            }
        } 
    }

    /* fortifies a soldier from one country to another
     * if there are no more soldiers available, move on to next mode
     */
    private void fortify() {
        // edge case where initially it is 1
        if (selectedCountry.numSoldiers == 1) {
            nextMode();
        }
        selectedCountry.numSoldiers--;
        selectedSecondCountry.numSoldiers++;

        // immediately switch to next mode if no longer possible to fortify
        if (selectedCountry.numSoldiers == 1) {
            nextMode();
        }
    }

    /* returns a String that contains information
     * on the game state
     */
    public String getStringForMode() {
        String init = "Player " + (turn + 1) + ": ";
        switch(mode) {
        case UseCardMode:
            if (players[turn].fullHand()) {
                return init + "You have a full hand. You must use a set.";
            }
            return init + "Would you like to use your cards?";
        case InitialPlacingMode:
            return init + "Welcome to Risk! Place troops: " + troopsToPlace + " remaining";
        case PlacingMode:
            return init + "Place troops: " + troopsToPlace + " remaining";
        case AttackFromMode:
            return init + "Choose country to attack from: ___ -> ___";
        case AttackToMode:
            return init + "Choose country to attack: " + selectedCountry.getName() + " -> ___";
        case KeepAttackingMode:
            return init + "Keep Attacking? " + selectedCountry.getName() + 
                    " -> " + selectedSecondCountry.getName();
        case NewCountryMode:
            return init + "You successfully conquered " + selectedSecondCountry.getName() + 
                    "! Add troops to your new or old country: " + troopsToPlace + " remaining";
        case FortifyFromMode:
            return init + "Choose country to fortify from: ___ -> ___";
        case FortifyToMode:
            return init + "Choose country to fortify: " + selectedCountry.getName() + " -> ___";
        case KeepFortifyingMode:
            return init + "Continue to fortify " + selectedCountry.getName() + " -> " +
            selectedSecondCountry.getName() + "?";
        case GameOverMode:
            return init + "You won!!!";
        default:
            return "did you just break this game why";
        }
    }

    public void next() {
        switch(mode) {
        case UseCardMode:
            if (!players[turn].fullHand()) {
                nextMode();
            }
            break;
        case InitialPlacingMode:
            break;
        case PlacingMode:
            break;
        case AttackFromMode:
            mode = Mode.FortifyFromMode;
            selectedCountry = null;
            break;
        case AttackToMode:
            mode = Mode.AttackFromMode;
            selectedCountry = null;
            selectedSecondCountry = null;
            break;
        case KeepAttackingMode:
            mode = Mode.FortifyFromMode;
            selectedCountry = null;
            selectedSecondCountry = null;
            break;
        case NewCountryMode:
            break;
        case FortifyFromMode:
            nextPlayer();
            break;
        case FortifyToMode:
            nextPlayer();
            break;
        case KeepFortifyingMode:
            nextPlayer();
            break;
        case GameOverMode:
            break;    
        }
        turnInfo.setText(getStringForMode());
        repaint();
    }

    private void nextPlayer() {
        selectedCountry = null;
        selectedSecondCountry = null;
        Player.wonCardAlready = false;
        turn = (turn + 1) % players.length;
        while (players[turn].dead) {
            turn = (turn + 1) % players.length;
        }
        if (players[turn].hasSet()) {
            mode = Mode.UseCardMode;
        } else {
            mode = Mode.PlacingMode;
        }
        updateTroopsToPlace();
        String[] cardLabels = players[turn].StringOfCards();
        for (int i = 0; i < cardLabels.length; i++) {
            cardInfo[i].setText(cardLabels[i]);    
        }
    }

    /* iterates the mode to the next
     * on the end of a turn, iterates to next player
     */
    private void nextMode() {
        switch(mode) {
        case UseCardMode:
            mode = Mode.PlacingMode;
            break;
        case InitialPlacingMode:
            mode = Mode.PlacingMode;
            break;
        case PlacingMode:
            mode = Mode.AttackFromMode;
            break;
        case AttackFromMode:
            mode = Mode.AttackToMode;
            break;
        case AttackToMode:
            mode = Mode.KeepAttackingMode;
            break;
        case KeepAttackingMode:
            mode = Mode.NewCountryMode;
            break;
        case NewCountryMode:
            mode = Mode.AttackFromMode;
            break;
        case FortifyFromMode:
            mode = Mode.FortifyToMode;
            break;
        case FortifyToMode:
            mode = Mode.KeepFortifyingMode;
            break;
        case KeepFortifyingMode:
            nextPlayer();
            break;
        case GameOverMode:
            break;
        }
    }
    
    public void useCards() {
        if (mode != Mode.UseCardMode) {
            return;
        }
            int bonus = Player.cardBonus();
            troopsToPlace += bonus;
            turnInfo.setText("You just traded in a set for " + bonus + " soldiers!");
            players[turn].useSet();
            setCardLabels();
            if (!players[turn].hasSet()) {
                nextMode();
            }

    }
    
    public void setCardLabels() {
        String[] cardLabels = players[turn].StringOfCards();
        for (int i = 0; i < cardLabels.length; i++) {
            cardInfo[i].setText(cardLabels[i]);    
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(25, 25, 154));
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            for(Country c : player.countriesOwned) {
                g.setColor(colors[i]);

                if (c == selectedCountry || c == selectedSecondCountry) {
                    c.highlight();
                } else {
                    c.unhighlight();
                }
                c.draw(g);   
            }
            drawLines(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
