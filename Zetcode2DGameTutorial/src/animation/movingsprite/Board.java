package animation.movingsprite;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public final class Board extends JPanel implements ActionListener {
  private Timer timer;
  private Craft craft;
  
  public Board() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    setBackground(Color.BLACK);
    setDoubleBuffered(true);
    
    craft = new Craft();
    
    timer = new Timer(5, this);
    timer.start();
  }
  
  public void paint(final Graphics g) {
    super.paint(g);
    
    final Graphics2D g2d = (Graphics2D) g.create();
    g2d.drawImage(craft.image(), craft.x(), craft.y(), this);
    
    final ArrayList ms = craft.missiles();
    for (int i = 0; i < ms.size(); ++i) {
      final Missile m = (Missile) ms.get(i);
      g2d.drawImage(m.image(), m.x(), m.y(), this);
    }
    
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }
  
  public void actionPerformed(final ActionEvent evt) {
    final ArrayList ms = craft.missiles();
    for (int i = 0; i < ms.size(); ++i) {
      final Missile m = (Missile) ms.get(i);
      if (m.visible())
        m.move();
      else
        ms.remove(i);
    }
    
    craft.move();
    repaint();
  }
  
  private class TAdapter extends KeyAdapter {
    @Override
    public void keyReleased(final KeyEvent evt) {
      craft.keyReleased(evt); }
    
    @Override
    public void keyPressed(final KeyEvent evt) {
      craft.keyPressed(evt); }
  }
}
