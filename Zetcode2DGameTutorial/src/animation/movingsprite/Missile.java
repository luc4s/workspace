package animation.movingsprite;


import java.awt.Image;
import java.awt.Rectangle;
import java.lang.reflect.WildcardType;

import javax.swing.ImageIcon;


public final class Missile {
  private int x, y;
  private int width, height;
  private Image image;
  boolean visible;
  
  private final int BOARD_WIDTH = 390;
  private final int MISSILE_SPEED = 2;
  
  public Missile(int x, int y) {
    final ImageIcon ii = new ImageIcon(this.getClass().getResource("missile.png"));
    image = ii.getImage();
    width = image.getWidth(null);
    height = image.getHeight(null);
    this.x = x;
    this.y = y;
    visible = true;
  }
  
  public Image image() {
    return image; }
  public int x() {
    return x; }
  public int y() {
    return y; }
  public boolean visible() {
    return visible; }
  public Rectangle bounds() {
    return new Rectangle(x, y, width, height); }
  
  public void visible(boolean visible) {
    this.visible = visible; }
  
  public void move() {
    x += MISSILE_SPEED;
    if (x > BOARD_WIDTH)
      visible = false;
  }
}
