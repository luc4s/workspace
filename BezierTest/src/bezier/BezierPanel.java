package bezier;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public final class BezierPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private static final int WIDTH  = 500;
  private static final int HEIGHT = 500;
  
  private static final int CP_RADIUS = 10;
  
  private static final int STEPS = 1024;
  
  
  private final Point[] controlPoints;
  private Point start = null;
  private int index = -1;
  private boolean debug = true;
  
  
  public static void main(final String[] args) {
    final JPanel contentPane = new BezierPanel();
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame frame = new JFrame("Bezier Test");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setResizable(false);
          frame.setContentPane(contentPane);
          frame.pack();
          frame.setVisible(true);
      }
    });
  }
  
  
  public BezierPanel() {
    final MouseAdapter mouseAdapter = new MouseListener();
    addMouseListener(mouseAdapter);
    addMouseMotionListener(mouseAdapter);
    
    final KeyAdapter keyAdapter = new KeyListener();
    addKeyListener(keyAdapter);
    
    setFocusable(true);
    
    
    controlPoints = new Point[] {
      new Point(100, 100),
      new Point(100, 200),
      new Point(450, 200),
      new Point(400, 400)
    };
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(WIDTH, HEIGHT); }
  
  @Override
  public void paintComponent(final Graphics g) {
    final Graphics2D g2d = (Graphics2D) g.create();
    
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(0, 0, getWidth(), getHeight());
    
    if (debug) {
      g2d.setColor(Color.YELLOW);
      g2d.drawLine((int) controlPoints[0].getX(), (int) controlPoints[0].getY(),
                   (int) controlPoints[1].getX(), (int) controlPoints[1].getY());
      g2d.drawLine((int) controlPoints[2].getX(), (int) controlPoints[2].getY(),
                   (int) controlPoints[3].getX(), (int) controlPoints[3].getY());
    }
    
    g2d.setColor(Color.BLUE);
    Point lastPoint = controlPoints[0];
    for (int i = 0; i < STEPS; ++i) {
      final double t = i / (double) STEPS;
      final Point newPoint = bezier(t, controlPoints[0], controlPoints[1], controlPoints[2], controlPoints[3]);
      
      g2d.drawLine((int) lastPoint.getX(), (int) lastPoint.getY(), (int) newPoint.getX(), (int) newPoint.getY());
      lastPoint = newPoint;
    }
    g2d.drawLine((int) lastPoint.getX(), (int) lastPoint.getY(), (int) controlPoints[3].getX(), (int) controlPoints[3].getY());
    
    if (debug) {
      g2d.setColor(Color.YELLOW);
      for (final Point controlPoint : controlPoints)
        g2d.fillOval((int) (controlPoint.getX() - CP_RADIUS), (int) (controlPoint.getY() - CP_RADIUS),
            CP_RADIUS * 2, CP_RADIUS * 2);
    }
  }
  
  private Point bezier(double t, final Point p0, final Point p1, final Point p2, final Point p3) {
    final double tt = t * t;
    final double ttt = tt * t;
    final double u = 1 - t;
    final double uu = u * u;
    final double uuu = uu * u;
    
    final int x = (int) ((uuu * p0.getX()) + (3.0 * uu * t * p1.getX()) + (3.0 * u * tt * p2.getX()) + (ttt * p3.getX()));
    final int y = (int) ((uuu * p0.getY()) + (3.0 * uu * t * p1.getY()) + (3.0 * u * tt * p2.getY()) + (ttt * p3.getY()));
    return new Point(x, y);
  }
  
  
  private class MouseListener extends MouseAdapter {
    @Override
    public void mousePressed(final MouseEvent evt) {
      start = evt.getPoint();
      index = -1;
      for (int i = 0; i < 4; ++i) {
        if (start.distance(controlPoints[i]) < CP_RADIUS)
          index = i;
      }
    }
    
    @Override
    public void mouseDragged(final MouseEvent evt) {
      if (index != -1) {
        controlPoints[index].move((int) (controlPoints[index].getX() + evt.getX() - start.getX()),
                                  (int) (controlPoints[index].getY() + evt.getY() - start.getY()));
        start = evt.getPoint();
        repaint();
      }
    }
  }
  
  private class KeyListener extends KeyAdapter {
    @Override
    public void keyPressed(final KeyEvent evt) {
      if (evt.getKeyCode() == KeyEvent.VK_D) {
        debug = !debug;
        repaint();
      }
    }
  }
}
