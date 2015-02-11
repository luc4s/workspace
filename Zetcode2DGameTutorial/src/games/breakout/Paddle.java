package games.breakout;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Paddle extends Sprite implements Commons {
  String paddle = "../images/paddle.png";
  
  int dX;
  
  public Paddle() {
    ImageIcon ii = new ImageIcon(this.getClass().getResource(paddle));
    image = ii.getImage();
    
    width = image.getWidth(null);
    height = image.getHeight(null);
    
    resetState();
  }
  
  public void move() {
    x += dX;
    if (x <= 2)
      x = 2;
    if (x >= PADDLE_RIGHT)
      x = PADDLE_RIGHT;
  }
  
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    
    if (key == KeyEvent.VK_LEFT)
      dX = -2;
    if (key == KeyEvent.VK_RIGHT)
      dX = 2;
  }
  
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    
    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
      dX = 0;
  }
  
  public void resetState() {
    x = 200;
    y = 360;
  }
}
