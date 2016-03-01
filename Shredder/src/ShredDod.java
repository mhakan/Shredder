import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class ShredDod extends Shred {

	public ShredDod(String path) {
		super(path);
		this.f = new File(path);
		if (f == null) {
			try {
				this.finalize();
			} catch (Throwable e) {
				Logger.getLogger(ShredDod.class.getName()).log(Level.ALL, e.getMessage());
			}
		}
	}

	public ShredDod(File file) {
		super(file);
		this.f = file;
		if (f == null) {
			try {
				this.finalize();
			} catch (Throwable e) {
				Logger.getLogger(ShredDod.class.getName()).log(Level.ALL, e.getMessage());
			}
		}
	}

	public void WipeFile() {
 
		for (short j = 0; j < 3; j++) {
			WipeMetod((short) (j + 1));

		}
		SetCreationTime();
		SetLastModifiedTime();
		SetLastAccessTime();
	}

}
