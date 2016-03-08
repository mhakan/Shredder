package ShredderUI;

import static java.awt.event.ItemEvent.SELECTED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import ShredBase.WipeMethod;

public class frmMain extends JFrame {

	private JDialog dialog;
	private ShredBase.WipeMethod metod = WipeMethod.DoD;
	private ShredBase.ShredFile shr;
	private JButton btnDosyaEkle, btnKlasorEkle, btnRemoveFile, btnRemoveAll, btnStart, btnPart, btnDisk;
	private JTable table;
	public static final String[] columnNames = { "Icon", "File", "Size", "Type", "Last Modified Time",
			"Last Accessed Time", "Created Time" };

	public frmMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmMain.class.getResource("/images/clear.png")));
		setTitle("File Shredder");
		this.setSize(104, 768);

		initializeMenu();

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar("Shred Navigator");
		toolBar.setToolTipText("Shred Navigator");
		addButtons(toolBar);
		toolBar.setRollover(true);

		// Lay out the main panel.
		setPreferredSize(new Dimension(1024, 768));
		panel_1.add(toolBar, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		setJTable(panel_2);
		pack();

	}

	private void setJTable(JPanel panel_2) {
		table = new JTable(new AbstractFileModel(columnNames));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(37);
		table.getColumnModel().getColumn(1).setPreferredWidth((int) this.getWidth() / 2);
		table.getColumnModel().getColumn(2).setPreferredWidth((int) this.getWidth() / 15);
		table.getColumnModel().getColumn(3).setPreferredWidth((int) this.getWidth() / 15);
		table.getColumnModel().getColumn(4).setPreferredWidth((int) this.getWidth() / 5);
		table.getColumnModel().getColumn(5).setPreferredWidth((int) this.getWidth() / 5);
		table.getColumnModel().getColumn(6).setPreferredWidth((int) this.getWidth() / 5);
		JScrollPane js = new JScrollPane(table);
		panel_2.add(js);

	}

	private void initializeData(File f) {
		AbstractFileModel abs = (AbstractFileModel) table.getModel();

		abs.addRow(f);

	}

	private void initializeMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("Dosya");
		menuBar.add(fileMenu);

		JMenuItem mnitmExit = new JMenuItem("Exit");
		mnitmExit.setIcon(new ImageIcon(frmMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
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
				if (arg0.getStateChange() == SELECTED) {
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
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}

	protected void addButtons(JToolBar toolBar) {

		btnDosyaEkle = new JButton("Dosya Ekle");
		btnDosyaEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File("."));
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int tut = jfc.showOpenDialog(frmMain.this);
				if (tut == JFileChooser.APPROVE_OPTION) {
					initializeData(jfc.getSelectedFile());

				}
			}
		});
		btnDosyaEkle.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		btnDosyaEkle.setToolTipText("Silinecek Dosyaları Ekle");
		toolBar.add(btnDosyaEkle);

		// toolBar.addSeparator();

		btnKlasorEkle = new JButton("Klasör Ekle");
		btnKlasorEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File("."));
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int tut = jfc.showOpenDialog(frmMain.this);
				if (tut == JFileChooser.APPROVE_OPTION) {
					LoadListByPath(jfc.getSelectedFile().getAbsolutePath());

				}
			}
		});
		btnKlasorEkle
				.setIcon(new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnKlasorEkle.setToolTipText("Klasör içindeki tüm dosyaları ekle");
		toolBar.add(btnKlasorEkle);

		// toolBar.addSeparator();

		btnRemoveFile = new JButton("Seçimi Sil");
		btnRemoveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount() > 0 && table.getSelectedRow() != -1) {
					int i = table.getSelectedRow();
					String dirPath = (String) table.getValueAt(i, 1);
					AbstractFileModel abs = (AbstractFileModel) table.getModel();
					FileType type = (FileType) table.getValueAt(i, 3);// file_type
																		// index
					if (type == FileType.File)
						abs.DeleteRow(i);
					else if (type == FileType.Directory) {
						int tut = JOptionPane.showConfirmDialog(frmMain.this,
								"Klasörü ve içindeki dosyaları listeden istediğinizden emin misiniz?");
						if (tut == JOptionPane.YES_OPTION) {
							abs.DeleteRow(i);

							for (int j = 0; j < table.getRowCount(); j++) {
								String str = (String) table.getValueAt(j, 1);
								if (str.contains(dirPath)) {
									abs.DeleteRow(j);
									j--;
								}
							}
						}

					}
				}
			}
		});
		btnRemoveFile.setIcon(new ImageIcon(
				getClass().getResource("/com/sun/javafx/scene/web/skin/DrawHorizontalLine_16x16_JFX.png")));
		btnRemoveFile.setToolTipText("Seçili satırı siler");
		toolBar.add(btnRemoveFile);

		// toolBar.addSeparator();

		btnRemoveAll = new JButton("Listeyi Temizle");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractFileModel abs = (AbstractFileModel) table.getModel();
				abs.RemoveAll();
			}
		});
		btnRemoveAll.setIcon(new ImageIcon(
				getClass().getResource("/com/sun/javafx/scene/web/skin/FontBackgroundColor_16x16_JFX.png")));
		btnRemoveAll.setToolTipText("Hepsini Siler");
		toolBar.add(btnRemoveAll);

		toolBar.addSeparator();
		btnStart = new JButton("Wipe");
		btnStart.setToolTipText("Listedeki dosyaları güvenli bir şekilde sil");
		btnStart.setIcon(new ImageIcon(getClass().getResource("/images/clear.png")));
		toolBar.add(btnStart);

		toolBar.addSeparator();

		btnPart = new JButton("Boş Alanları Temizle");
		btnPart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File("."));
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int tut = jfc.showOpenDialog(frmMain.this);
				if (tut == JFileChooser.APPROVE_OPTION) {
					String file=jfc.getSelectedFile().getAbsolutePath();
					
				}
			}
		});
		btnPart.setToolTipText("Disk&Partition için boş alanları temizle.");
		btnPart.setIcon(new ImageIcon(getClass().getResource("/images/cleanup.png")));
		toolBar.add(btnPart);

		btnDisk = new JButton("Full Disk Wipe");
		btnDisk.addActionListener(new ActionListener() {
			frmDisk frm = new frmDisk();

			public void actionPerformed(ActionEvent arg0) {
				if (dialog == null) {
					JComponent comp = (JComponent) arg0.getSource();
					Window win = (Window) SwingUtilities.getWindowAncestor(comp);
					if (win != null) {
						dialog = new JDialog(win, "Disk Seç", ModalityType.APPLICATION_MODAL);
						dialog.getContentPane().add(frm);
						dialog.pack();
						dialog.setLocationRelativeTo(null);
						dialog.setSize(new Dimension(400, 450));
						dialog.setLocationRelativeTo(comp);
					}
				}
				dialog.setVisible(true); // here the modal dialog takes over

				// this line starts *after* the modal dialog has been disposed
				// **** here's the key where I get the String from JTextField in
				// the GUI
				// held
				// by the JDialog and put it into this GUI's JTextField.
				JOptionPane.showMessageDialog(null, frm.getPath());

			}
		});
		btnDisk.setToolTipText("Fiziksel diski güvenli temizler");
		btnDisk.setIcon(new ImageIcon(getClass().getResource("/images/Apps-Drive-Harddisk-icon.png")));
		toolBar.add(btnDisk);
	}

	protected void LoadListByPath(String text) {

		File f = new File(text);
		if (f.exists() && f.isDirectory()) {
			initializeData(f);

			File[] list = f.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					if (list[i].isFile())
						initializeData(list[i]);
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
