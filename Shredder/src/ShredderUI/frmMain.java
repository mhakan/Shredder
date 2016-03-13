package ShredderUI;

import static java.awt.event.ItemEvent.SELECTED;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DropMode;
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
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import ShredBase.Body;
import ShredBase.IShred;
import ShredBase.MetaData;
import ShredBase.ShredFactory;
import ShredBase.WipeMethod;

public class frmMain extends JFrame implements ActionListener, PropertyChangeListener {

	private ProgressMonitor progressMonitor;
	private Task task;
	private JDialog dialog;
	private ShredBase.WipeMethod metod = WipeMethod.DoD;
	private ShredBase.ShredFile shr;
	private JButton btnDosyaEkle, btnKlasorEkle, btnRemoveFile, btnRemoveAll, btnStart, btnPart, btnDisk;
	private JTable table;
	public static final String[] columnNames = { "Icon", "File", "Size", "Type", "Last Modified Time",
			"Last Accessed Time", "Created Time" };

	class Task extends SwingWorker<Void, Void> implements ShredObserver {
		private final WipeMethod method;
		private List<String> list;

		public Task(List<String> list, WipeMethod method) {

			this.list = list;
			this.method = method;
		}

		@Override
		public Void doInBackground() {
			System.out.println("Starting!...");
			while (this.list.size() > 0 && !isCancelled()) {
				// progressMonitor.setNote(list.get(0).toString());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				IShred shrd = new ShredFactory().ShredType(this.method, list.get(0).toString());
				shrd.Shred(this);
				list.remove(0);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			task.done();
			return null;
		}

		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			btnStart.setEnabled(true);
			progressMonitor.setProgress(0);
			progressMonitor.close();
			JOptionPane.showMessageDialog(null, "Wipe completed.\n");
		}

		@Override
		public void update(int i) {

			setProgress(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public frmMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmMain.class.getResource("/images/clear.png")));
		setTitle("File Shredder");
		this.setSize(104, 768);

		initializeMenu();

		setContentUI();
		pack();

	}

	private void setContentUI() {
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
	}

	private void setJTable(JPanel panel_2) {
		table = new JTable(new AbstractFileModel(columnNames));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		TableCellRenderer renderer = new EvenOddRenderer();
		table.setDefaultRenderer(Object.class, renderer);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(33);
		table.getColumnModel().getColumn(1).setPreferredWidth((int) this.getWidth() / 2);
		table.getColumnModel().getColumn(1).setMinWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth((int) this.getWidth() / 15);
		table.getColumnModel().getColumn(3).setPreferredWidth((int) this.getWidth() / 15);
		table.getColumnModel().getColumn(4).setPreferredWidth((int) this.getWidth() / 5);
		table.getColumnModel().getColumn(5).setPreferredWidth((int) this.getWidth() / 5);
		table.getColumnModel().getColumn(6).setPreferredWidth((int) this.getWidth() / 5);
		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT);
		
		table.setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				Transferable t = dtde.getTransferable();
				List fileList = null;
				try {
					fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < fileList.size(); i++) {
					if (((File) fileList.get(i)).isFile()) {
						initializeData(((File) fileList.get(i)));
					} else if (((File) fileList.get(i)).isDirectory()) {
						LoadListByPath(((File) fileList.get(i)).getAbsolutePath());
					}
				}
//				super.drop(dtde);
			}
		});

		JScrollPane js = new JScrollPane(table);
		panel_2.add(js);

	}

	private void initializeData(File f) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 1).toString().equals(f.getAbsolutePath())) {
				return;
			}
		}
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
//				System.exit(0);
JOptionPane.showMessageDialog(null, table.getRowCount());
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

							for (int j = table.getRowCount(); j >= 0; j--) {
								String str = (String) table.getValueAt(j, 1);
								if (str.contains(dirPath)) {
									abs.DeleteRow(j);
									j++;
								}
							}
							abs.DeleteRow(i);
						}

					}
				}
			}
		});
		btnRemoveFile.setIcon(new ImageIcon(
				getClass().getResource("/com/sun/javafx/scene/web/skin/DrawHorizontalLine_16x16_JFX.png")));

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
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> lst = new ArrayList<String>();
				for (int i = table.getRowCount() - 1; i >= 0; i--) {
					lst.add((String) table.getValueAt(i, 1));
				}
				ProgressPanel prg = new ProgressPanel(lst, metod);
				createDialog((JComponent) e.getSource(), prg, "Shred File!...", prg.getDimension());

			}
		});
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
					String file = jfc.getSelectedFile().getAbsolutePath();
					if (Files.isDirectory(jfc.getSelectedFile().toPath())) {
						System.out.println("this is directory");
					}

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
				createDialog((JComponent) arg0.getSource(), frm, "Disk Seç", new Dimension(450, 500));
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

	private void createDialog(Component arg0, Component frm, String str, Dimension dim) {

		JComponent comp = (JComponent) arg0;
		Window win = (Window) SwingUtilities.getWindowAncestor(comp);
		if (win != null) {
			dialog = new JDialog(win, str, ModalityType.APPLICATION_MODAL);
			dialog.getContentPane().add(frm);
			dialog.pack();
			dialog.setLocationRelativeTo(null);

			dialog.setSize(dim);
			dialog.setLocationRelativeTo(comp);
		}

		dialog.setVisible(true); // here the modal dialog takes over
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressMonitor.setProgress(progress);
			String message = String.format("Completed %d%%.\n", progress);
			progressMonitor.setNote(message);
			if (progressMonitor.isCanceled() || task.isDone()) {
				Toolkit.getDefaultToolkit().beep();
				if (progressMonitor.isCanceled()) {
					task.cancel(true);

				} else {

				}
				btnStart.setEnabled(true);
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> lst = new ArrayList<String>();
		for (int i = table.getRowCount() - 1; i >= 0; i--) {
			lst.add((String) table.getValueAt(i, 1));
		}
		progressMonitor = new ProgressMonitor(frmMain.this, "Running a Wipe Task", "", 0, 100);
		progressMonitor.setProgress(0);
		task = new Task(lst, metod);
		task.addPropertyChangeListener(this);
		task.execute();
		btnStart.setEnabled(false);

	}
}
