import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/* JPanel representing the start menu
 * 
 */
@SuppressWarnings("serial")
public class Start extends JPanel{
    private boolean startHover;
    private boolean rulesHover;
    private boolean backHover;
    private boolean hover2;
    private boolean hover3;
    private boolean hover4;
    private boolean hover5;   
    private boolean hover6;

    BufferedImage risk;
    BufferedImage riskStart;
    BufferedImage riskRules;
    BufferedImage riskPlayers;
    BufferedImage risk2Players;
    BufferedImage risk3Players;
    BufferedImage risk4Players;
    BufferedImage risk5Players;
    BufferedImage risk6Players;
    BufferedImage riskInstructions;
    BufferedImage riskInstructionsBack;

    enum Menu {
        StartScreen, SelectPlayers, InstructionScreen;
    }

    private Menu menu = Menu.StartScreen;

    public Start() {
        try {
            risk = ImageIO.read(new File("images/risk.png"));
            riskStart = ImageIO.read(new File("images/riskstart.png"));
            riskRules = ImageIO.read(new File("images/riskrules.png"));
            riskPlayers = ImageIO.read(new File("images/riskplayers.png"));
            risk2Players = ImageIO.read(new File("images/risk2players.png"));
            risk3Players = ImageIO.read(new File("images/risk3players.png"));
            risk4Players = ImageIO.read(new File("images/risk4players.png"));
            risk5Players = ImageIO.read(new File("images/risk5players.png"));
            risk6Players = ImageIO.read(new File("images/risk6players.png"));
            riskInstructions = ImageIO.read(new File("images/riskinstructions.png"));
            riskInstructionsBack = ImageIO.read(new File("images/riskinstructionsback.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseMotionListener(new MouseAdapter() {

            public void mouseMoved(MouseEvent e) {
                Point mouse = e.getPoint();

                switch(menu) {
                //System.out.println("x: " + x + " y: " + y);
                case StartScreen: 
                    if (inBoundsStart(mouse)) {
                        startHover = true;
                    } else {
                        startHover = false;
                    }
                    if (inBoundsRules(mouse)) {
                        rulesHover = true;
                    } else {
                        rulesHover = false;
                    }
                    break;
                case SelectPlayers:
                    if (inBounds2(mouse)) {
                        hover2 = true;
                    } else {
                        hover2 = false;
                    }                    
                    if (inBounds3(mouse)) {
                        hover3 = true;
                    } else {
                        hover3 = false;
                    }                    
                    if (inBounds4(mouse)) {
                        hover4 = true;
                    } else {
                        hover4 = false;
                    }                    
                    if (inBounds5(mouse)) {
                        hover5 = true;
                    } else {
                        hover5 = false;
                    }                    
                    if (inBounds6(mouse)) {
                        hover6 = true;
                    } else {
                        hover6 = false;
                    }
                    break;
                case InstructionScreen:
                    if (inBoundsBack(mouse)) {
                        backHover = true;
                    } else {
                        backHover = false;
                    }
                    break;
                }                    
                repaint();
            }
        });
    }
        
    /* Checks the position of the mouse to see what number of players
     * the user has selected
     * @param e the mouse click
     */
    public int selectPlayers(MouseEvent e) {
        Point mouse = e.getPoint();
        
        int numPlayers = -1;

        switch(menu) {
        case StartScreen:
            if (inBoundsStart(mouse)) {
                menu = Menu.SelectPlayers;
            }
            if (inBoundsRules(mouse)) {
                menu = Menu.InstructionScreen;
            }
            break;
        case SelectPlayers:
            if (inBounds2(mouse)) {
                numPlayers = 2;
            }
            if (inBounds3(mouse)) {
                numPlayers = 3;
            }
            if (inBounds4(mouse)) {
                numPlayers = 4;
            }
            if (inBounds5(mouse)) {
                numPlayers = 5;
            }
            if (inBounds6(mouse)) {
                numPlayers = 6;
            }
            break;
        case InstructionScreen:
            if (inBoundsBack(mouse)) {
                menu = Menu.StartScreen;
            }
            break;
        }
        repaint();
        return numPlayers;
    }

    public boolean inBoundsStart(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 110 || mouseX > 290) {
            return false;
        }
        if (mouseY < 510 || mouseY > 555) {
            return false;
        }
        return true;
    }

    public boolean inBoundsRules(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 620 || mouseX > 820) {
            return false;
        }
        if (mouseY > 555 || mouseY < 510) {
            return false;
        }
        return true;
    }
    public boolean inBoundsBack(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 633 || mouseX > 814) {
            return false;
        }
        if (mouseY > 555 || mouseY < 510) {
            return false;
        }
        return true;
    }

    public boolean inBounds2(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 110 || mouseX > 426) {
            return false;
        }
        if (mouseY > 412 || mouseY < 357) {
            return false;
        }
        return true;
    }

    public boolean inBounds3(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 110 || mouseX > 426) {
            return false;
        }
        if (mouseY > 504 || mouseY < 448) {
            return false;
        }
        return true;
    }

    public boolean inBounds4(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 502 || mouseX > 821) {
            return false;
        }
        if (mouseY > 412 || mouseY < 357) {
            return false;
        }
        return true;
    }

    public boolean inBounds5(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 502 || mouseX > 821) {
            return false;
        }
        if (mouseY > 504 || mouseY < 448) {
            return false;
        }
        return true;
    }

    public boolean inBounds6(Point p) {
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();

        if (mouseX < 286 || mouseX > 603) {
            return false;
        }
        if (mouseY > 577 || mouseY < 521) {
            return false;
        }
        return true;
    }


    @Override
    public void paintComponent(Graphics g) {
        switch(menu) {
        case StartScreen: 
            if (startHover) {
                g.drawImage(riskStart, 0, 0, null);
            } 
            else if (rulesHover) {
                g.drawImage(riskRules, 0, 0, null);
            } 
            else {
                g.drawImage(risk, 0, 0, null);
            }
            break;
        case SelectPlayers:
            if (hover2) {
                g.drawImage(risk2Players, 0, 0, null);
            }
            else if (hover3) {
                g.drawImage(risk3Players, 0, 0, null);
            }
            else if (hover4) {
                g.drawImage(risk4Players, 0, 0, null);
            }
            else if (hover5) {
                g.drawImage(risk5Players, 0, 0, null);
            }
            else if (hover6) {
                g.drawImage(risk6Players, 0, 0, null);
            }
            else {
                g.drawImage(riskPlayers, 0, 0, null);
            }
            break;
        case InstructionScreen:
            if (backHover) {
                g.drawImage(riskInstructionsBack, 0, 0, null);
            } else {
                g.drawImage(riskInstructions, 0, 0, null);
            }
            break;
        }
    }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(risk.getWidth(), risk.getHeight());
        }


    }
