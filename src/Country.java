import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Set;

public class Country implements Comparable<Country> {
    Set<Country> adjacentCountries;
    private String name;
    int ownerID;
    int numSoldiers;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean selected;
    private boolean showName = false;

    public Country (String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        numSoldiers = 1;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getRightX() {
        return x + width;
    }
    
    public int getCenterX() {
        return x + width / 2;
    }

    public int getY() {
        return y;
    }
    
    public int getBottomY() {
        return y + height;
    }

    public int getCenterY() {
        return y + height / 2;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void draw(Graphics g) {
        Color color;
        if (selected) {
            color = Board.colors[ownerID].darker();
        } else {
            color = Board.colors[ownerID];
        }
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        if (showName) {
            g.drawString(name, x, y + height / 2);
        } else {
            int offset = 3;
            if (numSoldiers >= 10) {
                offset = 7;
            }
            g.drawString("" + numSoldiers, x - offset + width / 2, y + 4 + height / 2);
        }
    }
    
    public void toggleNameOrNum() {
        showName = !showName;
    }
    
    public void highlight() {
        selected = true;
    }
    
    public void unhighlight() {
        selected = false;
    }
    
    public boolean inBounds(Point mouse) {
        int mouseX = (int) mouse.getX();
        int mouseY = (int) mouse.getY();
        
        if (mouseX < x || mouseX > x + width) {
            return false;
        }
        if (mouseY > y + height || mouseY < y) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Country c) {
        return name.compareTo(c.name);
    }
    


}