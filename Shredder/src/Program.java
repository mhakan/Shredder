import java.awt.EventQueue;

import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				frmMain frm = new frmMain();
				
				frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				frm.setVisible(true);
			}

		});

	}

}
