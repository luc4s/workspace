package animation.timer;


import java.awt.EventQueue;
import javax.swing.JFrame;


public final class UtilityTimerExample extends JFrame {
  public UtilityTimerExample() {
    initUI(); }
  
  private void initUI() {
    add(new Board());
    
    setResizable(false);
    pack();
    
    setTitle("Star");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        final JFrame ex = new UtilityTimerExample();
        ex.setVisible(true);
      }
    });
  }
}
