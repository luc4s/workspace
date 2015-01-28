package animation.movingsprite;


import java.awt.Image;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;


public final class Craft {
  private final String craft = "craft.png";
  
  private int dX, dY;
  private int x, y;
  private final Image image;
  
  private ArrayList missiles;
  
  private final int CRAFT_SIZE = 64;
  
  public Craft() {
    final ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
    image = ii.getImage();
    
    missiles = new ArrayList();
    x = 40;
    y = 60;
  }
  
  public void move() {
    x += dX;
    y += dY;
  }
  
  public int x() {
    return x; }
  public int y() {
    return y; }
  public Image image() {
    return image; }
  public ArrayList missiles() {
    return missiles; }
  
  public void keyPressed(final KeyEvent evt) {
    int key = evt.getKeyCode();
    
    if (key == KeyEvent.VK_SPACE)
      fire();
    if (key == KeyEvent.VK_LEFT)
      dX = -1;
    if (key == KeyEvent.VK_RIGHT)
      dX = 1;
    
    if (key == KeyEvent.VK_UP)
      dY = -1;
    if (key == KeyEvent.VK_DOWN)
      dY = 1;
  }
  
  public void fire() {
    missiles.add(new Missile(x + CRAFT_SIZE / 2, y + CRAFT_SIZE / 2)); }
  
  public void keyReleased(final KeyEvent evt) {
    int key = evt.getKeyCode();
    
    if (key == KeyEvent.VK_LEFT ||
        key == KeyEvent.VK_RIGHT)
      dX = 0;
    
    if (key == KeyEvent.VK_UP ||
        key == KeyEvent.VK_DOWN)
      dY = 0;
  }
}
