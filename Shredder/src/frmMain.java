
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
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box.Filler;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class frmMain extends JFrame {
	private JTextField txtPath;
	private DefaultListModel listModel;
	private List<String> results;
	private WipeMethod metod = WipeMethod.DoD;
	private Shred shr;

	public frmMain() {
		setTitle("File Shredder");

		this.setSize(822, 700);

		this.setMaximumSize(new Dimension(800, 700));
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
		rdbtnmnitmZeroPass.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					metod = WipeMethod.Zero;
				} else
					metod = WipeMethod.DoD;
			}
		});
		menuEdit.add(rdbtnmnitmZeroPass);

		JRadioButtonMenuItem rdbtnmnitmDodPass = new JRadioButtonMenuItem("DoD 3 Pass");
		rdbtnmnitmDodPass.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					metod = WipeMethod.DoD;
				} else
					metod = WipeMethod.Zero;
			}
		});

		rdbtnmnitmDodPass.setSelected(true);
		menuEdit.add(rdbtnmnitmDodPass);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnmnitmDodPass);
		bg.add(rdbtnmnitmZeroPass);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Silinecek Dizin:");

		txtPath = new JTextField();
		txtPath.setDisabledTextColor(Color.WHITE);
		txtPath.setEditable(false);
		txtPath.setColumns(10);

		JButton btnSelectPath = new JButton("...");
		btnSelectPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfile = new JFileChooser();
				jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int i = jfile.showOpenDialog(frmMain.this);
				if (i == JFileChooser.APPROVE_OPTION) {
					txtPath.setText(jfile.getSelectedFile().getAbsolutePath());
					results = new ArrayList<String>();
					ListItem.GetListModel().clear();
					LoadListByPath(txtPath.getText());
					frmMain.this.setTitle(String.valueOf(results.size()));
				}
			}
		});

		JButton btnStart = new JButton("");

		btnStart.setIcon(new ImageIcon(
				frmMain.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(
						gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addGap(5).addComponent(lblNewLabel)
										.addGap(5)
										.addComponent(txtPath, GroupLayout.PREFERRED_SIZE, 637,
												GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(btnSelectPath, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addGap(54)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addGap(8).addComponent(lblNewLabel))
								.addGroup(gl_panel_1.createSequentialGroup().addGap(5)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
												.addComponent(txtPath, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSelectPath).addComponent(btnStart))))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		JList list = new JList();
		list.setModel(ListItem.GetListModel());
		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane, BorderLayout.CENTER);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				progressBar.setMaximum(ListItem.GetListModel().getSize());
				Thread t = new Thread() {
					public void run() {

						for (int i = 0; i < ListItem.GetListModel().getSize(); i++) {
							final int t = i;
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {

									progressBar.setValue(t + 1);
									int tmp = ((t + 1) * 100) / ListItem.GetListModel().getSize();
									progressBar.setString(tmp + " % ");
									Shred shr = new ShredFactory().ShredType(metod,
											ListItem.GetListModel().elementAt(t).toString());

									shr.WipeFile();
									progressBar.repaint();
								}
							});

						}
					}
				};
				t.start();
			}
		});

	}

	protected void LoadListByPath(String text) {

		File f = new File(text);
		if (f.exists() && f.isDirectory()) {
			results.add(text);

			File[] list = f.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					if (list[i].isFile())
						ListItem.GetListModel().AddListItem(list[i].getAbsolutePath());
					else if (list[i].isDirectory()) {

						LoadListByPath(list[i].getAbsolutePath());
					}
				}
			} else {
				System.err.println(text);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
