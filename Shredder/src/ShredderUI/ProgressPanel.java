/**
 * 
 */
package ShredderUI;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.plaf.ProgressBarUI;

import ShredBase.IShred;
import ShredBase.ShredFactory;
import ShredBase.WipeMethod;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;

/**
 * @author Selami
 *
 */
public class ProgressPanel extends JPanel implements ShredObserver, Runnable {

	private List<String> list;
	private final WipeMethod method;
	private JLabel lblFile;
	private JProgressBar pBarCurrent, pBarTotal;
	private JButton btnStop, startButton;
	boolean canceled = false;

	private Object MUTEX;

	public ProgressPanel(List<String> lst, WipeMethod wip) {
		setLayout(null);

		pBarCurrent = new JProgressBar(0, 100);
		pBarCurrent.setBounds(31, 54, 344, 20);
		pBarCurrent.setValue(0);
		pBarCurrent.setStringPainted(true);

		pBarTotal = new JProgressBar(0, 100);
		pBarTotal.setMaximum(100);
		pBarTotal.setBounds(31, 86, 344, 20);
		pBarTotal.setValue(0);
		pBarTotal.setStringPainted(true);
		setBounds(20, 20, 453, 210);
		JPanel panel = new JPanel();
		panel.setBounds(20, 20, 410, 169);

		add(panel);
		panel.setLayout(null);
		panel.add(pBarCurrent);
		panel.add(pBarTotal);

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				Thread thr = new Thread(ProgressPanel.this);
				thr.start();
				startButton.setEnabled(false);
			}
		});

		startButton.setBounds(31, 136, 91, 25);
		panel.add(startButton);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canceled = true;
			}
		});
		btnStop.setBounds(253, 136, 123, 25);
		btnStop.setEnabled(false);
		panel.add(btnStop);

		lblFile = new JLabel("  ");
		lblFile.setBounds(31, 26, 344, 15);
		panel.add(lblFile);

		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		this.list = Collections.synchronizedList(lst);
		this.method = wip;
		MUTEX = new Object();
	}

	public Dimension getDimension() {
		return new Dimension(453, 210);

	}

	public void setProgress(JProgressBar bar, int val) {
		bar.setValue(val);
		bar.setString("%" + String.valueOf(val));
		bar.repaint();
	}

	public void Work() {
		System.out.println("Starting!...");

		for (int i = 0; i < list.size(); i++) {
			if (canceled == true)
				break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			File f = new File(list.get(i));
			lblFile.setText(f.getName());

			IShred shrd = new ShredFactory().ShredType(this.method, list.get(i).toString());
			shrd.Shred(this);
			int current = ((i + 1) * 100) / list.size();

			// System.out.println(current);
			ProgressPanel.this.setProgress(pBarTotal, current);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Wipe finished!...");
		this.startButton.setEnabled(true);
		this.btnStop.setEnabled(false);
	}

	public void RemoveAt(int i) {
		if (this.list.isEmpty())
			return;
		synchronized (MUTEX) {
			this.remove(i);
		}

	}

	@Override
	public void run() {
		Work();

	}

	@Override
	public void update(int i) {
		setProgress(pBarCurrent, i);

	}
}
