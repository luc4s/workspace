package basics.imagedrawer;


import java.awt.EventQueue;
import javax.swing.JFrame;


public final class ImageExample extends JFrame {
  public ImageExample() {
    initUI(); }
  
  private void initUI() {
    add(new Board());
    
    pack();
    
    setTitle("Funny");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public static void main(final String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        final ImageExample ex = new ImageExample();
        ex.setVisible(true);
      }
    });
  }
}
