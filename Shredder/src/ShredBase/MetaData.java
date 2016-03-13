package ShredBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.rmi.CORBA.Util;

import ShredderUI.ShredObservable;
import ShredderUI.ShredObserver;

public class MetaData implements ShredObservable, Runnable {
	boolean changed;
	File file;
	Random rand;
	ArrayList<ShredObserver> lst;
	final Object MUTEX;

	public MetaData(File f) {
		MUTEX = new Object();
		this.file = f;
		rand = new Random();
		lst = new ArrayList<ShredObserver>();
	}

	public void startClear() {
		Thread thread = new Thread(this);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void metaDataClear() {

		this.changed = true;
		percent(0);
		SetFlag();
		percent(20);
		SetCreationTime();
		percent(40);
		SetLastModifiedTime();
		percent(60);
		SetLastAccessTime();
		percent(80);
		NameClear();
		percent(100);
	}

	private void SetFlag() {
		ShredderUI.Util.GetExec("chattr +s "+file.getAbsolutePath());
		
	}

	private void NameClear() {
		// Not implemented yet
	}

	public void SetWritable() {
		file.setWritable(true);
	}

	private void SetCreationTime() {

		try {
			FileTime ft = FileTime.fromMillis(Math.abs(rand.nextLong()));

			Files.setAttribute(file.toPath(), "creationTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}

	private void SetLastAccessTime() {

		try {
			FileTime ft = FileTime.fromMillis(Math.abs(rand.nextLong()));
			Files.setAttribute(file.toPath(), "lastAccessTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}

	private void SetLastModifiedTime() {

		try {
			FileTime ft = FileTime.fromMillis(Math.abs(rand.nextLong()));
			Files.setAttribute(file.toPath(), "lastModifiedTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void add(ShredObserver o) {
		if (o == null)
			throw new NullPointerException();
		synchronized (MUTEX) {
			if (lst.contains(o) == false)
				lst.add(o);
		}

	}

	@Override
	public void remove(ShredObserver o) {
		synchronized (MUTEX) {
			if (lst.contains(o) == false)
				lst.remove(o);
		}

	}

	public void percent(long currentValue) {
		this.changed = true;
		notifyServer(currentValue);

	}

	@Override
	public void notifyServer(long val) {
		List<ShredObserver> list;
		synchronized (MUTEX) {

			if (changed == false)
				return;// Hiç değişiklik yok demektir
			list = new ArrayList<>(this.lst);

		}
		for (ShredObserver o : list) {
			o.update((int) val);

		}

	}

	public void finalize() {
		lst.clear();

	}

	@Override
	public void run() {
		metaDataClear();

	}
}
