package games.breakout;

import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons {
  private int xDir, yDir;
  
  protected String ball = "../images/ball.png";
  
  public Ball() {
    xDir = 1;
    yDir = -1;
    
    ImageIcon ii = new ImageIcon(this.getClass().getResource(ball));
    image = ii.getImage();
    
    width = image.getWidth(null);
    height = image.getHeight(null);
    
    resetState();
  }
  
  public void move() {
    x += xDir;
    y += yDir;
    
    if (x == 0)
      setXDir(1);
    
    if (x == BALL_RIGHT)
      setXDir(-1);
    
    if (y == 0)
      setYDir(1);
  }
  
  public void resetState() {
    x = 230;
    y = 355;
  }
  
  public void setXDir(int xDir) {
    this.xDir = xDir; }
  public void setYDir(int yDir) {
    this.yDir = yDir; }
  
  public int getYDir() {
    return yDir; }
}
