package basics.donut;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public final class Board extends JPanel {
  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    
    drawDonut(g);
  }
  
  private void drawDonut(final Graphics g) {
    final Graphics2D g2d = (Graphics2D) g.create();
    
    final RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    
    g2d.setRenderingHints(rh);
    
    final Dimension size = getSize();
    double w = size.getWidth();
    double h = size.getHeight();
    
    final Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 130);
    g2d.setStroke(new BasicStroke(1));
    g2d.setColor(Color.GRAY);
    
    for (double deg = 0; deg < 360; deg += 5) {
      final AffineTransform at = AffineTransform.getTranslateInstance(
          w / 2.0, h / 2.0);
      at.rotate(Math.toRadians(deg));
      g2d.draw(at.createTransformedShape(e));
    }
  }
}
