package games.breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Board extends JPanel implements Commons {
  Image ii;
  Timer timer;
  String message = "Game Over";
  Ball ball;
  Paddle paddle;
  Brick bricks[];
  
  boolean inGame = true;
  int timerId;
  
  public Board() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    
    bricks = new Brick[30];
    setDoubleBuffered(true);
    timer = new Timer();
    timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
  }
  
  public void addNotify() {
    super.addNotify();
    gameInit();
  }
  
  public void gameInit() {
    ball = new Ball();
    paddle = new Paddle();
    
    int k = 0;
    for (int i = 0; i < 5; ++i)
      for (int j = 0; j < 6; ++j)
        bricks[k++] = new Brick(j * 40 + 30, i * 10 + 50);
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    
    if (inGame) {
      g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
          ball.getWidth(), ball.getHeight(), this);
      g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
          paddle.getWidth(), paddle.getHeight(), this);
      
      for (int i = 0; i < 30; ++i)
        if (!bricks[i].isDestroyed())
          g.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(),
              bricks[i].getWidth(), bricks[i].getHeight(), this);
    } else {
      Font font = new Font("Verdana", Font.BOLD, 18);
      FontMetrics metr = getFontMetrics(font);
      
      g.setColor(Color.BLACK);
      g.setFont(font);
      g.drawString(message, Commons.WIDTH - metr.stringWidth(message) / 2, Commons.WIDTH  /2);
    }
    
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }
  
  private class TAdapter extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
      paddle.keyReleased(e); }
    
    @Override
    public void keyPressed(KeyEvent e) {
      paddle.keyPressed(e); }
  }
  
  class ScheduleTask extends TimerTask {
    @Override
    public void run() {
      ball.move();
      paddle.move();
      checkCollision();
      repaint();
    }
  }
  
  public void stopGame() {
    inGame = false;
    timer.cancel();
  }
  
  public void checkCollision() {
    if (ball.getRect().getMaxY() > Commons.BOTTOM)
      stopGame();
    
    for (int i = 0, j = 0; i < 30; ++i) {
      if (bricks[i].isDestroyed())
        ++j;
      if (j == 30) {
        message = "Victory";
        stopGame();
      }
    }
    
    if (ball.getRect().intersects(paddle.getRect())) {
      int paddlePos = (int) paddle.getRect().getMinX();
      int ballPos = (int) ball.getRect().getMinX();
      
      int first = paddlePos + 8;
      int second = paddlePos + 16;
      int third = paddlePos + 24;
      int fourth = paddlePos + 32;
      
      if (ballPos < first) {
        ball.setXDir(-1);
        ball.setYDir(-1);
      }
      
      if (ballPos >= first && ballPos < second) {
        ball.setXDir(-1);
        ball.setYDir(-1 * ball.getYDir());
      }
      
      if (ballPos >= second && ballPos < third) {
        ball.setXDir(0);
        ball.setYDir(-1);
      }
      
      if (ballPos >= third && ballPos < fourth) {
        ball.setXDir(1);
        ball.setYDir(-1 * ball.getYDir());
      }
      
      if (ballPos > fourth) {
        ball.setXDir(1);
        ball.setYDir(-1);
      }
    }
    
    for (int i = 0; i < 30; ++i) {
      if (ball.getRect().intersects(bricks[i].getRect())) {
        int ballLeft = (int) ball.getRect().getMinX();
        int ballHeight = (int) ball.getRect().getHeight();
        int ballWidth = (int) ball.getRect().getWidth();
        int ballTop = (int) ball.getRect().getMinY();
        
        Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
        Point pointLeft = new Point(ballLeft - 1, ballTop);
        Point pointTop = new Point(ballLeft, ballTop - 1);
        Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
        
        if (!bricks[i].isDestroyed()) {
          if (bricks[i].getRect().contains(pointRight))
            ball.setXDir(-1);
          else if (bricks[i].getRect().contains(pointLeft))
            ball.setXDir(1);
          
          if (bricks[i].getRect().contains(pointTop))
            ball.setYDir(1);
          else if (bricks[i].getRect().contains(pointBottom))
            ball.setYDir(-1);
          
          bricks[i].setDestroyed(true);
        }
      }
    }
  }
}