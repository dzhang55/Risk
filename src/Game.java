import java.awt.BorderLayout;

import javax.swing.*;

public class Game implements Runnable {
    
    public void run() {
        final JFrame frame = new JFrame("Risk");
        frame.setLocation(800, 600);
        final Board board = new Board();
        frame.add(board, BorderLayout.CENTER);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
