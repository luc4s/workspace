package animation.timer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public final class Board extends JPanel {
  private final int B_WIDTH  = 350;
  private final int B_HEIGHT = 350;
  private final int INITIAL_X = -40;
  private final int INITIAL_Y = -40;
  private final int INITIAL_DELAY   = 100;
  private final int PERIOD_INTERVAL = 25;
  
  private Image star;
  private Timer timer;
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
    
    timer = new Timer();
    timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
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
  
  private class ScheduleTask extends TimerTask {
    @Override
    public void run() {
      x += 1;
      y += 1;
      
      if (y > B_HEIGHT) {
        x = INITIAL_X;
        y = INITIAL_Y;
      }
      
      repaint();
    }
  }
}
