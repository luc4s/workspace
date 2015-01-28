package basics.template;


import java.awt.EventQueue;
import javax.swing.JFrame;


public final class Application extends JFrame {
  public Application() {
    initUI(); }
  
  public void initUI() {
    add(new Board());
    
    setSize(250, 200);
    
    setTitle("Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        final Application ex = new Application();
        ex.setVisible(true);
      }
    });
  }
}
