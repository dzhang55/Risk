import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Player {
    Map<Integer, Integer> cardIDToNumCards;
    int extraSoldiers;
    Set<Country> countriesOwned;
    int id;


    public void draw(Graphics g) {
        Color color;
        for (Country c : countriesOwned) {
            if (selected) {
                color = Board.colors[ownerID].darker();
            } else {
                color = Board.colors[ownerID];
            }
            g.setColor(color);
            g.fillRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            if (showName) {
                g.drawString(c.getName(), c.getX(), c.getY() + c.getHeight() / 2);
            } else {
                int offset = 3;
                if (c.numSoldiers >= 10) {
                    offset = 7;
                }
                g.drawString("" + c.numSoldiers, c.getX() - offset + c.getWidth() / 2,
                             c.getY() + 4 + c.getHeight() / 2);
            }
        }
    }




}