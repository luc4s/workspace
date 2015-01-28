package collisiondetection.shootingaliens;


import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import collisiondetection.shootingaliens.Missile;


public final class Craft {
  private static final String craft = "craft.png";
  
  private int dX, dY;
  private int x, y;
  private int width, height;
  private boolean visible;
  private Image image;
  private ArrayList missiles;
  
  public Craft() {
    final ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
    image = ii.getImage();
    
    width = image.getWidth(null);
    height = image.getHeight(null);
    
    missiles = new ArrayList();
    visible = true;
    
    x = 40;
    y = 60;
  }
  
  public void move() {
    x += dX;
    y += dY;
    
    if (x < 1)
      x = 1;
    if (y < 1)
      y = 1;
  }
  
  public int x() {
    return x; }
  public int y() {
    return y; }
  public Image image() {
    return image; }
  public ArrayList missiles() {
    return missiles; }
  public boolean visible() {
    return visible; }
  public Rectangle bounds() {
    return new Rectangle(x, y, width, height); }
  
  public void visible(boolean visible) {
    this.visible = visible; }
  
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
    missiles.add(new Missile(x + width, y + height / 2));
  }
  
  public void keyReleased(final KeyEvent evt) {
    int key = evt.getKeyCode();
    
    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
      dX = 0;
    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
      dY = 0;
  }
}
