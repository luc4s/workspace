package basics.imagedrawer;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public final class Board extends JPanel {
  private Image funny;
  
  public Board() {
    initBoard(); }
  
  private void initBoard() {
    loadImage();
    
    int w = funny.getWidth(this);
    int h = funny.getHeight(this);
    setPreferredSize(new Dimension(w, h));
  }
  
  private void loadImage() {
    final ImageIcon ii = new ImageIcon("funny.jpg");
    funny = ii.getImage();
  }
  
  @Override
  public void paintComponent(final Graphics g) {
    g.drawImage(funny, 0, 0, null); }
}
