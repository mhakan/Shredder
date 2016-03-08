package ShredderUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frmDisk extends JPanel {
	public static final String[] columnNames = { "Icon", "Disk", "Size", "Path" };

	public frmDisk() {
		setLayout(new BorderLayout(0, 0));

		setJTable((JPanel) this);

		getDisks();
	}

	private void getDisks() {
		List<String> lst = Util.GetExec("lsblk -o KNAME");
		List<String> lstSize = Util.GetExec("lsblk -o SIZE");
		List<String> lstType = Util.GetExec("lsblk -o TYPE");
		AbstractDiskModel disk = (AbstractDiskModel) table.getModel();
		disk.RemoveAll();
		for (int i = 1; i < lst.size(); i++) {

			String[] line = new String[3];
			line[0] = lst.get(i);
			line[1] = lstSize.get(i);
			line[2] = lstType.get(i);
			disk.addRow(line);
		}
	}

	private void setJTable(JPanel panel_2) {
		table = new JTable(new AbstractDiskModel(columnNames));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				if (e.getClickCount() == 2) {
					setPath((String) table.getValueAt(row, 3));
					try {
						Window window = SwingUtilities.getWindowAncestor(frmDisk.this);
						window.setVisible( false );
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth((int) this.getWidth() / 2);
		table.getColumnModel().getColumn(2).setPreferredWidth((int) this.getWidth() / 10);
		table.getColumnModel().getColumn(3).setPreferredWidth((int) this.getWidth() / 5);
		JScrollPane js = new JScrollPane(table);
		panel_2.add(js);

	}

	private JTable table;
	private String selectedPath;

	public String getPath() {
		return selectedPath;
	}

	public void setPath(String str) {
		selectedPath = str;
	}
}
