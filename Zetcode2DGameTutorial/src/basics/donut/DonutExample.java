package basics.donut;


import java.awt.EventQueue;
import javax.swing.JFrame;


public final class DonutExample extends JFrame {
  public DonutExample() {
    initUI(); }
  
  public void initUI() {
    add(new Board());
    
    setSize(350, 330);
    
    setTitle("Donut");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        final DonutExample ex = new DonutExample();
        ex.setVisible(true);
      }
    });
  }
}
