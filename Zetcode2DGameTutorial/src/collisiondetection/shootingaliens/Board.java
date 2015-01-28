package collisiondetection.shootingaliens;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import collisiondetection.shootingaliens.Missile;


public final class Board extends JPanel implements ActionListener {
  private Timer timer;
  private Craft craft;
  private ArrayList aliens;
  private boolean ingame;
  private int B_WIDTH;
  private int B_HEIGHT;
  
  private int[][] pos = {
    { 2380, 29 }, { 2500, 59 }, { 1380, 89 },
    { 780, 109 }, { 580, 139 }, { 680, 239 },
    { 790, 259 }, { 760, 50 }, { 790, 150 },
    { 980, 209 }, { 560, 45 }, { 510, 70 },
    { 930, 159 }, { 590, 80 }, { 530, 60 },
    { 940, 59 }, { 990, 30 }, { 920, 200 },
    { 900, 259 }, { 660, 50 }, { 540, 90 },
    { 810, 220 }, { 860, 20 }, { 740, 180 },
    { 820, 128 }, { 490, 170 }, { 700, 30 }
  };
  
  public Board() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    setBackground(Color.BLACK);
    setDoubleBuffered(true);
    ingame = true;
    
    setSize(400, 300);
    
    craft = new Craft();
    
    initAliens();
    
    timer = new Timer(5, this);
    timer.start();
  }
  
  public void addNotify() {
    super.addNotify();
    B_WIDTH = getWidth();
    B_HEIGHT = getHeight();
  }
  
  public void initAliens() {
    aliens = new ArrayList();
    
    for (int i = 0; i < pos.length; ++i)
      aliens.add(new Alien(pos[i][0], pos[i][1]));
  }
  
  public void paint(final Graphics g) {
    super.paint(g);
    
    if (ingame) {
      final Graphics2D g2d = (Graphics2D) g.create();
      
      if (craft.visible())
        g2d.drawImage(craft.image(), craft.x(), craft.y(), this);
      
      final ArrayList ms = craft.missiles();
      
      for (int i = 0; i < ms.size(); ++i) {
        final Missile m = (Missile) ms.get(i);
        g2d.drawImage(m.image(), m.x(), m.y(), this);
      }
      
      for (int i = 0; i < aliens.size(); ++i) {
        final Alien a = (Alien) aliens.get(i);
        if (a.visible())
          g2d.drawImage(a.image(), a.x(), a.y(), this);
      }
      
      g2d.setColor(Color.WHITE);
      g2d.drawString("Aliens left : " + aliens.size(), 5, 15);
    } else {
      final String msg = "Game Over";
      final Font small = new Font("Helvetica", Font.BOLD, 14);
      final FontMetrics metr = this.getFontMetrics(small);
      
      g.setColor(Color.WHITE);
      g.setFont(small);
      g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
    
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }
  
  public void actionPerformed(final ActionEvent evt) {
    if (aliens.size() == 0)
      ingame = false;
    
    final ArrayList ms = craft.missiles();
    
    for (int i = 0; i < ms.size(); ++i) {
      final Missile m = (Missile) ms.get(i);
      if (m.visible())
        m.move();
      else
        ms.remove(i);
    }
    
    for (int i = 0; i < aliens.size(); ++i) {
      final Alien a = (Alien) aliens.get(i);
      if (a.visible())
        a.move();
      else
        aliens.remove(i);
    }
    
    craft.move();
    checkCollisions();
    repaint();
  }
  
  public void checkCollisions() {
    final Rectangle r3 = craft.bounds();
    
    for (int j = 0; j < aliens.size(); ++j) {
      final Alien a = (Alien) aliens.get(j);
      final Rectangle r2 = a.bounds();
      
      if (r3.intersects(r2)) {
        craft.visible(false);
        a.visible(false);
        ingame = false;
      }
    }
    
    final ArrayList ms = craft.missiles();
    
    for (int i = 0; i < ms.size(); ++i) {
      final Missile m = (Missile) ms.get(i);
      final Rectangle r1 = m.bounds();
      
      for (int j = 0; j < aliens.size(); ++j) {
        final Alien a = (Alien) aliens.get(j);
        final Rectangle r2 = a.bounds();
        
        if (r1.intersects(r2)) {
          m.visible(false);
          a.visible(false);
        }
      }
    }
  }
  
  private class TAdapter extends KeyAdapter {
    @Override
    public void keyPressed(final KeyEvent evt) {
      craft.keyPressed(evt);
    }
    
    @Override
    public void keyReleased(final KeyEvent evt) {
      craft.keyReleased(evt);
    }
  }
}
