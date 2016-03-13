package ShredBase;

//
//used for Disk and Partition
//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ShredderUI.ShredObservable;
import ShredderUI.ShredObserver;

public class Body implements ShredObservable, Runnable {
	private final File f;
	private final short id;
	private ArrayList<ShredObserver> lst;
	private final Object MUTEX;
	private boolean changed;

	public Body(File f, short id) {
		MUTEX = new Object();
		lst = new ArrayList<ShredObserver>();
		this.f = f;
		this.id = id;

	}

	public void startWipe() {

		Thread t = new Thread(this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void percent(long currentValue, long totalMax) {
		this.changed = true;
		notifyServer((currentValue * 100) / totalMax);

	}

	public void finalize() {
		lst.clear();

	}

	public void SetFileLength() {
		try {
			RandomAccessFile rand = new RandomAccessFile(f, "rws");
			rand.setLength(0);
			rand.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void add(ShredObserver o) {
		if (o == null)
			throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
			if (!lst.contains(o))
				lst.add(o);
		}

	}

	@Override
	public void remove(ShredObserver o) {
		synchronized (MUTEX) {
			lst.remove(o);
		}

	}

	@Override
	public void notifyServer(long val) {

		List<ShredObserver> list;
		synchronized (MUTEX) {
			if (this.changed == false) {
				return;
			}
			list = new ArrayList<>(this.lst);
			this.changed = false;

		}
		for (ShredObserver o : list) {
			o.update((int) val);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void Wipe() {
		if (f.length() > 0) {
			RandomAccessFile out = null;
			FileChannel ch = null;
			try {
				out = new RandomAccessFile(f.getAbsolutePath(), "rws");
				ch = out.getChannel();
				int buffer = 4096;
				long call = 0;
				long fsize = f.length();
				if (fsize < 8192 * 2)
					buffer = (int) fsize;
				// System.out.println(buffer);
				// System.out.println(fsize);
				int lastPart = (int) fsize % buffer;
				int loop = (int) (fsize - lastPart) / buffer;
				if (loop == 0)// Unnecessary
					loop = 1;
				ch.position(0);
				ch.force(true);
				percent(call, fsize);
				for (int i = 0; i < loop; i++) {

					ch.write(new WipeValues(buffer, id).GenerateValue());
					call += buffer;
					percent(call, fsize);
				}
				ch.write(new WipeValues(lastPart, id).GenerateValue());
				call += lastPart;
				percent(call, fsize);
			} catch (IOException e2) {

				e2.printStackTrace();
			} finally {
				try {
					ch.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	@Override
	public void run() {
		Wipe();
	}

}
