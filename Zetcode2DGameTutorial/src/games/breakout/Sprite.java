package games.breakout;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
  protected int x, y;
  protected int width, height;
  protected Image image;
  
  public void setX(int x) {
    this.x = x; }
  public void setY(int y) {
    this.y = y; }
  
  public int getX() {
    return x; }
  public int getY() {
    return y; }
  public int getWidth() {
    return width; }
  public int getHeight() {
    return height; }
  Image getImage() {
    return image; }
  Rectangle getRect() {
    return new Rectangle(x, y, image.getWidth(null), image.getHeight(null)); }
}
