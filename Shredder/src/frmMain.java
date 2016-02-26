
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import java.awt.Component;

public class frmMain extends JFrame {
	private JTextField txtPath;
	private DefaultListModel listModel;
	public frmMain() {

		 this.setSize(822,700);
		 
		 this.setMaximumSize(getSize());
		 getContentPane().setLayout(new BorderLayout(0, 0));
		 
		 JMenuBar menuBar = new JMenuBar();
		 getContentPane().add(menuBar, BorderLayout.NORTH);
		 
		 JMenu fileMenu = new JMenu("Dosya");
		 menuBar.add(fileMenu);
		 
		 JMenuItem mnitmExit = new JMenuItem("Exit");
		 mnitmExit.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		System.exit(0);
		 	}
		 });
		
		 fileMenu.add(mnitmExit);
		 
		 JMenu menuEdit = new JMenu("Wipe Metodu");
		 menuBar.add(menuEdit);
		 
		 JRadioButtonMenuItem rdbtnmnitmZeroPass = new JRadioButtonMenuItem("Zero Pass");
		 menuEdit.add(rdbtnmnitmZeroPass);
		 
		 JRadioButtonMenuItem rdbtnmnitmDodPass = new JRadioButtonMenuItem("DoD 3 Pass");
		 menuEdit.add(rdbtnmnitmDodPass);
		 ButtonGroup bg=new ButtonGroup();
		 bg.add(rdbtnmnitmDodPass);
		 bg.add(rdbtnmnitmZeroPass);
		 
		 JPanel panel = new JPanel();
		 getContentPane().add(panel, BorderLayout.CENTER);
		 panel.setLayout(new BorderLayout(0, 0));
		 
		 JPanel panel_1 = new JPanel();
		 panel.add(panel_1, BorderLayout.NORTH);
		 
		 JLabel lblNewLabel = new JLabel("Silinecek Dizin:");
		 
		 txtPath = new JTextField();
		 txtPath.setColumns(10);
		 
		 JButton btnSelectPath = new JButton("...");
		 btnSelectPath.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		JFileChooser jfile=new JFileChooser();
		 		int i=jfile.showOpenDialog(this);
		 	}
		 });
		 GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		 gl_panel_1.setHorizontalGroup(
		 	gl_panel_1.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_panel_1.createSequentialGroup()
		 			.addGap(5)
		 			.addComponent(lblNewLabel)
		 			.addGap(5)
		 			.addComponent(txtPath, GroupLayout.PREFERRED_SIZE, 637, GroupLayout.PREFERRED_SIZE)
		 			.addPreferredGap(ComponentPlacement.UNRELATED)
		 			.addComponent(btnSelectPath, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
		 			.addGap(31))
		 );
		 gl_panel_1.setVerticalGroup(
		 	gl_panel_1.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_panel_1.createSequentialGroup()
		 			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
		 				.addGroup(gl_panel_1.createSequentialGroup()
		 					.addGap(8)
		 					.addComponent(lblNewLabel))
		 				.addGroup(gl_panel_1.createSequentialGroup()
		 					.addGap(5)
		 					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
		 						.addComponent(txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		 						.addComponent(btnSelectPath))))
		 			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		 );
		 panel_1.setLayout(gl_panel_1);
		 
		 JPanel panel_2 = new JPanel();
		 panel.add(panel_2, BorderLayout.CENTER);
		 panel_2.setLayout(new BorderLayout(0, 0));
		 
		 JList list = new JList();
		 panel_2.add(list);
		 list.setModel(ListItem.GetListModel());
		 
	}
@Override
protected void finalize() throws Throwable {
	// TODO Auto-generated method stub
	super.finalize();
}

}
