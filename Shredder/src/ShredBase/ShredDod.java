package ShredBase;

import java.io.File;
import java.nio.file.Files;

import ShredderUI.ShredObserver;

public class ShredDod extends ShredFile {

	public ShredDod(File f) {
		super(f);

	}

	public void Shred(ShredObserver sho) {
		MetaData m = new MetaData(f);
		m.SetWritable();

		Body bd=null;
		for (int i = 1; i < 4; i++) {
			bd = new Body(f,(short) i);
			bd.add(sho);
			bd.startWipe();// Wiping body
			m.startClear();
		}
		bd.SetFileLength();
		f.delete();
	}

}
