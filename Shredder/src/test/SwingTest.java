package test;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SwingTest extends JFrame {
	private JTable table = new JTable();
	private JScrollPane scroll = new JScrollPane(table);
	private DefaultTableModel tm = new DefaultTableModel(new String[] { "a", "b", "c" }, 2);

	public SwingTest() {
		table.setModel(tm);
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
				File f = new File( fileList.getItem(0));
				table.setValueAt(f.getAbsolutePath(), 0, 1);
				table.setValueAt(f.length(), 0,  2);
				Point point = dtde.getLocation();
				int column = table.columnAtPoint(point);
				int row = table.rowAtPoint(point);
				// handle drop inside current table
				super.drop(dtde);
			}
		});
		scroll.setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				// handle drop outside current table (e.g. add row)
				super.drop(dtde);
			}
		});
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(scroll);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SwingTest();
	}
}