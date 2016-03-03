
import static java.awt.event.ItemEvent.SELECTED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class frmMain extends JFrame {

	private WipeMethod metod = WipeMethod.DoD;
	private IShredFile shr;
	private JButton btnDosyaEkle, btnKlasorEkle, btnRemoveFile, btnRemoveAll, btnStart;
	private JTable table;
	public static final String[] columnNames = { "Icon", "File", "Size", "Type", "Last Modified Time",
			"Last Accessed Time", "Created Time" };

	public frmMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmMain.class.getResource("/images/clear.png")));
		setTitle("File Shredder");

		this.setSize(822, 700);

		this.setMaximumSize(new Dimension(800, 700));

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
		setPreferredSize(new Dimension(450, 32));
		panel_1.add(toolBar, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		setJTable(panel_2);

	}

	private void setJTable(JPanel panel_2) {
		table = new JTable(new AbstractFileModel(columnNames));
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
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

		JMenuItem mn�tmKlasrEkle = new JMenuItem("Klas\u00F6r Ekle");
		mn�tmKlasrEkle
				.setIcon(new ImageIcon(frmMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		fileMenu.add(mn�tmKlasrEkle);

		JMenuItem menuItemDosyaAc = new JMenuItem("Dosya Ekle");
		menuItemDosyaAc
				.setIcon(new ImageIcon(frmMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		fileMenu.add(menuItemDosyaAc);

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
		btnDosyaEkle.setToolTipText("Silinecek Dosyalar\u0131 Ekle");
		toolBar.add(btnDosyaEkle);

		// toolBar.addSeparator();

		btnKlasorEkle = new JButton("Klas�r Ekle");
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
		btnKlasorEkle.setToolTipText("Klas�r i�indeki t�m dosyalar� ekle");
		toolBar.add(btnKlasorEkle);

		// toolBar.addSeparator();

		btnRemoveFile = new JButton("Se�imi Sil");
		btnRemoveFile.setIcon(new ImageIcon(
				getClass().getResource("/com/sun/javafx/scene/web/skin/DrawHorizontalLine_16x16_JFX.png")));
		btnRemoveFile.setToolTipText("Se�ili sat�r� siler");
		toolBar.add(btnRemoveFile);

		// toolBar.addSeparator();

		btnRemoveAll = new JButton("Listeyi Temizle");
		btnRemoveAll.setIcon(new ImageIcon(
				getClass().getResource("/com/sun/javafx/scene/web/skin/FontBackgroundColor_16x16_JFX.png")));
		btnRemoveAll.setToolTipText("Hepsini Siler");
		toolBar.add(btnRemoveAll);

		toolBar.addSeparator();
		btnStart = new JButton("Wipe");
		btnStart.setToolTipText("Listedeki dosyalar� g�venli bir �ekilde sil");
		btnStart.setIcon(new ImageIcon(getClass().getResource("/images/clear.png")));
		toolBar.add(btnStart);
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
