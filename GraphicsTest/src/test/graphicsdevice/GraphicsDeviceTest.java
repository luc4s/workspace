package test.graphicsdevice;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
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
          
          JList<String> displayModeList = new JList<String>();
            displayModeList.setModel(new AbstractListModel<String>() {
              private static final long serialVersionUID = 1L;
              
              final List<String> displayModes = new ArrayList<String>();
              {
                final Set<String> dm = new HashSet<String>();
                for (final DisplayMode displayMode : graphicsDev.getDisplayModes())
                  dm.add(String.format("%4dx%-4d [%dbit]",
                    displayMode.getWidth(), displayMode.getHeight(), displayMode.getBitDepth()));
                for (final String displayMode : dm)
                  displayModes.add(displayMode);
              }
              
              public int getSize() {
                return displayModes.size();
              }
  
              @Override
              public String getElementAt(int index) {
                return displayModes.get(index);
              }
            });
            displayModeList.setVisibleRowCount(4);
            displayModeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            displayModeList.setSelectedIndex(0);
          
          final JScrollPane scrollPane = new JScrollPane(displayModeList);
          
          final JButton submit = new JButton("Launch");
            submit.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                final String displayModeStr = displayModeList.getSelectedValue();
                final DisplayMode displayMode = new DisplayMode;
              }
            });
          
          frame.getContentPane().setLayout(new BorderLayout());
          frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
          frame.getContentPane().add(submit, BorderLayout.SOUTH);
          
          frame.setResizable(false);
          frame.pack();
          frame.setLocationRelativeTo(null);
          
          frame.setVisible(true);
      }
    });
  }
}
