package test.graphicsdevice;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public final class GraphicsDeviceTest {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final GraphicsEnvironment graphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice graphicsDev = graphicsEnv.getDefaultScreenDevice();
        
        final JFrame resolutionSelection = new JFrame("Resolution Selection");
          resolutionSelection.setVisible(true);
      }
    });
  }
}
