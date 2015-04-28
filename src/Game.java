import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {
    
    public void run() {
        final JFrame frame = new JFrame("Risk");

        final OverlayLayout overlay = new OverlayLayout(frame);
        
        final JPanel turnPanel = new JPanel();
        frame.add(turnPanel, BorderLayout.SOUTH);
        final JLabel turnInfo = new JLabel();
        turnPanel.add(turnInfo);
        
        
        final JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(4, 1));
        final JLabel[] cardInfo = new JLabel[4];
        for (int i = 0; i < cardInfo.length; i++) {
            cardInfo[i] = new JLabel();
        }
        //statusPanel.add(cardInfo);
       // statusPanel.add(done, BorderLayout.SOUTH);
        final Board board = new Board(turnInfo, cardInfo);
        frame.add(board, BorderLayout.CENTER);
        
        final JPanel statusPanel = new JPanel();
        final JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.next();
            }
        });
        statusPanel.add(next, BorderLayout.NORTH);
        
        frame.add(statusPanel, BorderLayout.WEST);
        
       // overlay.addLayoutComponent(done, constraints);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);    
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
