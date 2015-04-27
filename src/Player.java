import java.util.List;
import java.util.Map;
import java.util.Set;


public class Player {
    Map<Integer, Integer> cardIDToNumCards;
    int extraSoldiers;
    Set<Country> countriesOwned;


    public void updateBonus() {
        extraSoldiers = Math.min(3, countriesOwned.size() / 3);

        for (int i = 0; i < Board.continentBonuses.length; i++) {
            if (countriesOwned.containsAll(Board.continents.get(i))) {
                extraSoldiers += Board.continentBonuses[i];
            }
        }

    }





}