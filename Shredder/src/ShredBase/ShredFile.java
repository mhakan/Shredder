package ShredBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import ShredderUI.ShredObserver;

/**
 * 
 */

/**
 * @author Selami
 *
 */

public class ShredFile implements IShred {

	protected File f;

	public ShredFile(File file) {
		this.f = file;
	}

	public void Shred(ShredObserver sho) {
		MetaData m = new MetaData(f);
		m.SetWritable();
		Body bd = new Body(f,(short) 0);
		bd.add(sho);
		bd.startWipe();// Wipe Zero
		
		m.startClear();
		bd.SetFileLength();
		f.delete();
	}

}