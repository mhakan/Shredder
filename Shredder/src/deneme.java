import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import JTableTree.FileSystemModel2;
import JTableTree.JTreeTable;

import java.awt.GridLayout;

public class deneme extends JFrame{
	JTreeTable j;
	 protected FileSystemModel2    model; 
	public deneme() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
	 AbstractFileModel a=new AbstractFileModel(new  String[]{"",""});
	}
	 
		 
}
