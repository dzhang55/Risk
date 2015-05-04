import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Game implements Runnable {

    public void run() {

        final JFrame frame = new JFrame("Risk");
        final Start startScreen = new Start();
        frame.add(startScreen);
        startScreen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int numPlayers = startScreen.selectPlayers(e);
                if (numPlayers != -1) {
                    startScreen.setVisible(false);
                    startScreen.setEnabled(false);
                    initializeGame(frame, numPlayers);
                }
            }

        });

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);    

    }

    private static void initializeGame(JFrame frame, int numPlayers) {

        final JPanel turnPanel = new JPanel();
        final JLabel turnInfo = new JLabel();
        turnPanel.add(turnInfo);

        final JPanel statusPanel = new JPanel();
        final JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(100, 300));
        statusPanel.setPreferredSize(new Dimension(120, 0));

        final JLabel[] cardInfo = new JLabel[8];
        for (int i = 0; i < cardInfo.length; i++) {
            cardInfo[i] = new JLabel();
            cardPanel.add(cardInfo[i]);
        }
        Dice diceInfo = new Dice();

        final Board board = new Board(turnInfo, cardInfo, diceInfo, numPlayers);
        frame.add(board, BorderLayout.CENTER);

        final JButton use = new JButton("Use");
        use.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.useCards();
            }
        });
        cardPanel.add(use);

        final JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.next();
            }
        });
        statusPanel.add(next);
        statusPanel.add(cardPanel);
        statusPanel.add(diceInfo, BorderLayout.SOUTH);

        frame.add(statusPanel, BorderLayout.WEST);
        frame.add(turnPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);   

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
