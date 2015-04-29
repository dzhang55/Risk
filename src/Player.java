import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JLabel;


public class Player {
    int[] cards;
    Set<Country> countriesOwned;
    static List<Integer> deck;
    static int cardBonusIndex = 0;
    static boolean wonCardAlready;
    boolean dead;

    public Player() {
        cards = new int[4];
        countriesOwned = new TreeSet<Country>();
    }
    /* creates the Risk deck with 14 of each card type
     * along with 2 wildcards
     */
    public static void initialDeck() {
        deck = new LinkedList<Integer>();
        for (int i = 0; i < 14; i++) {
            deck.add(0);
            deck.add(1);
            deck.add(2);
        }
        deck.add(3);
        deck.add(3);

        shuffleDeck();
    }

    private static void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int r = i + (int) (Math.random() * (deck.size() - i));
            int card = deck.remove(r);
            deck.add(0, card);
        }
    }

    /* calculates the current card bonus for turning in a set of cards
     */
    public static int cardBonus() {
        int bonus;
        if (cardBonusIndex < 5) {
            bonus = 2 * cardBonusIndex + 4;
        } else {
            bonus = 5 * cardBonusIndex - 10;
        }
        return bonus;
    }
    
    /* creates the String[] used in the JLabel for cardInfo
     */
    public String[] StringOfCards() {
        String[] stringOfCards = new String[8];
        stringOfCards[1] = "Cards:";
        stringOfCards[2] = "Infantry: " + cards[0];
        stringOfCards[3] = "Cavalry: " + cards[1];
        stringOfCards[4] = "Artillery: " + cards[2];
        stringOfCards[5] = "WildCards: " + cards[3];
        stringOfCards[6] = "                     ";
        stringOfCards[7] = "Bonus: " + cardBonus();
        return stringOfCards;
                
    }
    
    public boolean fullHand() {
        int sum = 0;
        for (int i = 0; i < cards.length; i++) {
            sum += cards[i];
        }
        if (sum >= 5) {
            return true;
        }
        return false;
    }
    
    /* checks if a player has a set of cards that can be turned in
     */
    public boolean hasSet() {
        return hasSet1() || hasSet2();
    }

    public boolean hasSet1() {
        if (cards[3] > 0) {
            return cards[0] > 2 - cards[3] || cards[1] > 2 - cards[3] || 
                    cards[2] > 2 - cards[3];
        }
        return cards[0] > 2 || cards[1] > 2 || cards[2] > 2;
    }

    public boolean hasSet2() {
        if (cards[3] == 1) {
            return (cards[0] > 0 && cards[1] > 0) || (cards[0] > 0 && cards[2] > 0) ||
                    (cards[1] > 0 && cards[2] > 0);
        }
        if (cards[3] == 2) {
            return cards[0] > 0 || cards[1] > 0 || cards[2] > 0;
        }
        return cards[0] > 0 && cards[1] > 0 && cards[2] > 0;
    }

    /* uses a set of the player's cards
     */
    public void useSet() {
        cardBonusIndex++;
        if (hasSet1()) {
            for (int i = 0; i < 3; i++) {
                if (cards[i] > 2) {
                    cards[i] -= 3;
                    deck.add(i);
                    deck.add(i);
                    deck.add(i);
                    return;
                }
            }

            for (int i = 0; i < 3; i++) {
                if (cards[i] > 1) {
                    cards[i] -= 2;
                    cards[3]--;
                    deck.add(i);
                    deck.add(i);
                    deck.add(3);
                    return;
                }
            }
            
            for (int i = 0; i < 3; i++) {
                if (cards[i] > 0) {
                    cards[i]--;
                    cards[3] -= 2;
                    deck.add(i);
                    deck.add(3);
                    deck.add(3);
                    return;
                }
            }
        }

        if (hasSet2()) {
            for (int i = 0; i < 3; i++) {
                if (cards[i] == 0) {
                    cards[3]--;
                    deck.add(3);
                } else {
                    cards[i]--;
                    deck.add(i);
                }
            }

        }
    }
    
    public void winCard() {
        int card = deck.remove(0);
        cards[card]++;
    }
}