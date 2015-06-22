import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

/* JPanel displaying the five dice used in a battle
 */
@SuppressWarnings("serial")
public class Dice extends JPanel {
    
    Die[] dice = new Die[5];
    BufferedImage[] diceImage = new BufferedImage[7];
    BufferedImage[] redDiceImage = new BufferedImage[7];

    public Dice() {
        try {
            for (int i = 0; i < diceImage.length; i++) {
                String filename = "die" + i + ".png";
                diceImage[i] = ImageIO.read(new File("images/" + filename));
                redDiceImage[i] = ImageIO.read(new File("images/red" + filename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(new GridLayout(3, 2, 5, 5));
        dice[0] = new Die(true);
        dice[1] = new Die(false);
        dice[2] = new Die(true);
        dice[3] = new Die(false);
        dice[4] = new Die(true);

        for (int i = 0; i < dice.length; i++) {
            this.add(dice[i]);
        }

    }
    /* JComponent representing one die
     */
    public class Die extends JComponent {
        private int die;
        private boolean isRed;

        public Die(boolean isRed) {
            this.isRed = isRed;
        }

        public void update(int x) {
            die = x;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isRed) {
                g.drawImage(redDiceImage[die], 0, 0, null);
            } else {
                g.drawImage(diceImage[die], 0, 0, null);
            }
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 50);
        }
    }
}
