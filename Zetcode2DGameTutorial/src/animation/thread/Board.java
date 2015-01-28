package animation.thread;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable {
  private final int B_WIDTH  = 350;
  private final int B_HEIGHT = 350;
  private final int INITIAL_X = -40;
  private final int INITIAL_Y = -40;
  private final int DELAY = 25;
  
  private Image star;
  private Thread animator;
  private int x, y;
  
  public Board() {
    initBoard(); }
  
  private void loadImage() {
    final ImageIcon ii = new ImageIcon("star.png");
    star = ii.getImage();
  }
  
  private void initBoard() {
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    setDoubleBuffered(true);
    
    loadImage();
    
    x = INITIAL_X;
    y = INITIAL_Y;
  }
  
  @Override
  public void addNotify() {
    super.addNotify();
    
    animator = new Thread(this);
    animator.start();
  }
  
  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    
    drawStar(g);
  }
  
  private void drawStar(final Graphics g) {
    g.drawImage(star, x, y, this);
    Toolkit.getDefaultToolkit().sync();
  }
  
  private void cycle() {
    x += 1;
    y += 1;
    
    if (y > B_HEIGHT) {
      x = INITIAL_X;
      y = INITIAL_Y;
    }
  }
  
  @Override
  public void run() {
    long beforeTime, timeDiff, sleep;
    
    beforeTime = System.currentTimeMillis();
    
    while (true) {
      cycle();
      repaint();
      
      timeDiff = System.currentTimeMillis() - beforeTime;
      sleep = DELAY - timeDiff;
      
      if (sleep < 0)
        sleep = 2;
      
      try {
        Thread.sleep(sleep);
      } catch (final InterruptedException ex) {
        System.out.println("Interrupted: "  + ex.getMessage());
      }
      
      beforeTime = System.currentTimeMillis();
    }
  }
}
