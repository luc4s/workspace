package collisiondetection.shootingaliens;


import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public final class Alien {
  private String craft = "alien.png";
  
  private int x, y;
  private int width, height;
  private boolean visible;
  private Image image;
  
  public Alien(int x, int y) {
    final ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
    image = ii.getImage();
    width = image.getWidth(null);
    height = image.getHeight(null);
    visible = true;
    this.x = x;
    this.y = y;
  }
  
  public void move() {
    if (x < 0)
      x = 400;
    x -= 1;
  }
  
  public int x() {
    return x; }
  public int y() {
    return y; }
  public boolean visible() {
    return visible; }
  public Image image() {
    return image; }
  public Rectangle bounds() {
    return new Rectangle(x, y, width, height); }
  
  public void visible(boolean visible) {
    this.visible = visible; }
}
