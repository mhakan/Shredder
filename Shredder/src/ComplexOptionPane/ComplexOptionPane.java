package ComplexOptionPane;

import java.awt.event.*;
import javax.swing.*;

import ComplexOptionPane.PlayerEditorPanel.FieldTitle;

@SuppressWarnings("serial")
public class ComplexOptionPane extends JPanel {
   private PlayerEditorPanel playerEditorPanel = new PlayerEditorPanel();
   private JTextArea textArea = new JTextArea(20, 40);

   public ComplexOptionPane() {
      add(new JScrollPane(textArea));
      add(new JButton(new AbstractAction("Get Player Information") {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            int result = JOptionPane.showConfirmDialog(null, playerEditorPanel,
                  "Edit Player", JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
               for (FieldTitle fieldTitle : 
                  PlayerEditorPanel.FieldTitle.values()) {
                  textArea.append(String.format("%10s: %s%n", fieldTitle.getTitle(),
                        playerEditorPanel.getFieldText(fieldTitle)));
               }
            }
         }
      }));
   }

   private static void createAndShowGui() {
      ComplexOptionPane mainPanel = new ComplexOptionPane();

      JFrame frame = new JFrame("ComplexOptionPane");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}
