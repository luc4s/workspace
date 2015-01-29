package test.graphicsdevice;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public final class GraphicsDeviceTest {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final GraphicsEnvironment graphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice graphicsDev = graphicsEnv.getDefaultScreenDevice();
        
        final JFrame frame = new JFrame("Resolution Selection");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
          final List<String> displayModeStr = new ArrayList<String>();
          for (final DisplayMode displayMode : graphicsDev.getDisplayModes())
            displayModeStr.add(
              String.format("%4dx%-4d [%2dbit|%2dHz]",
                displayMode.getWidth(), displayMode.getHeight(), displayMode.getBitDepth(), displayMode.getRefreshRate()));
          final JList<String> displayModeList = new JList<String>(displayModeStr.toArray(new String[0]));
            displayModeList.setVisibleRowCount(4);
            displayModeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            displayModeList.setSelectedIndex(0);
          
          final JScrollPane scrollList = new JScrollPane(displayModeList);
          
          final JButton submitButton = new JButton("Launch");
            submitButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(final ActionEvent evt) {
                final DisplayMode displayMode = graphicsDev.getDisplayModes()[displayModeList.getSelectedIndex()];
                
                frame.dispose();
                
                final JFrame fullscreen = new JFrame("Fullscreen");
                  fullscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  fullscreen.setUndecorated(true);
                  final JLabel hello = new JLabel("Hello C:");
                    hello.setFont(new Font("Helvetica", Font.BOLD, 30));
                  
                  fullscreen.getContentPane().setLayout(new BorderLayout());
                  fullscreen.getContentPane().add(hello, BorderLayout.CENTER);
                
                graphicsDev.setFullScreenWindow(fullscreen);
                graphicsDev.setDisplayMode(displayMode);
              }
            });
          
          frame.getContentPane().setLayout(new BorderLayout());
          frame.getContentPane().add(scrollList, BorderLayout.CENTER);
          frame.getContentPane().add(submitButton, BorderLayout.SOUTH);
          
          frame.setResizable(false);
          frame.pack();
          frame.setLocationRelativeTo(null);
          
          frame.setVisible(true);
      }
    });
  }
}
