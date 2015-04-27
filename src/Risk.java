import java.util.Map;

public class Risk {

    int idTurn;
    int bonusForCards;
    Map<Integer, Player> idToPlayer;

    public void attack(int attacker, int defender) {
        int die1 = (int) Math.ceil(6 * Math.random());
        int die2 = (int) Math.ceil(6 * Math.random());
        int die3 = (int) Math.ceil(6 * Math.random());
        int defDie1 = (int) Math.ceil(6 * Math.random());
        int defDie2 = (int) Math.ceil(6 * Math.random());

        int maxAtk;
        int sndAtk;

        int midAtk = Math.max(die1, die2);
        if (die3 > midAtk) {
            maxAtk = die3;
            sndAtk = midAtk;
        } else {
            maxAtk = midAtk;
            if (Math.min(die1, die2) > die3) {
                sndAtk = Math.min(die1, die2);
            } else {
                sndAtk = die3;
            } 
        }

        int maxDef = Math.max(defDie1, defDie2);
        if (maxAtk > maxDef) {
            defender--;
        } else {
            attacker--;
        }
        int sndDef = Math.min(defDie1, defDie2);

        if (sndAtk > sndDef) {
            defender--;
        } else {
            attacker--;
        }
    }

//    public void makeBoard() {
//    Country[] countries = new Country[42];
//    countries[0] = new Country("Alaska");
//    countries[1] = new Country("Alberta");
//    countries[2] = new Country("");
//    
//    }
    
//    public void testBoard() {
//    Country[] countries = new Country[4];
//    countries[0] = new Country("A");
//    countries[1] = new Country("B");
//    countries[2] = new Country("C");
//    countries[3] = new Country("D");
//    }
    



}



