package animation.swingtimer;


import java.awt.EventQueue;
import javax.swing.JFrame;


public final class SwingTimerExample extends JFrame {
  public SwingTimerExample() {
    initUI(); }
  
  private void initUI() {
    add(new Board());
    
    setResizable(false);
    pack();
    
    setTitle("Start");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame ex = new SwingTimerExample();
        ex.setVisible(true);
      }
    });
  }
}
