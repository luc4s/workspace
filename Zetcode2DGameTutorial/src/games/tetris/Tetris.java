package games.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {
  JLabel statusBar;
  
  public Tetris() {
    statusBar = new JLabel(" 0");
    add(statusBar, BorderLayout.SOUTH);
    
    Board board = new Board(this);
    add(board);
    board.start();
    
    setSize(200, 400);
    setTitle("Tetris");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public JLabel getStatusBar() {
    return statusBar; }
  
  public static void main(final String[] args) {
    Tetris game = new Tetris();
    game.setLocationRelativeTo(null);
    game.setVisible(true);
  }
}
