package ShredBase;

import java.io.File;
import java.nio.file.Files;

import javax.swing.SwingUtilities;

import ShredderUI.ShredObservable;
import ShredderUI.ShredObserver;

public class ShredFactory {

	public IShred ShredType(WipeMethod wipe, String path) {
		File f = new File(path);
		if (f.exists()) {
			if (f.isFile()) {
				if (wipe == WipeMethod.Zero) {
					return new ShredFile(f);
				} else if (wipe == WipeMethod.DoD) {
					return new ShredDod(f);

				}
			} else if (f.isDirectory()) {
				if (wipe == WipeMethod.Zero) {
					return new ShredMetaData(f);

				} else if (wipe == WipeMethod.DoD) {
					return new ShredMetaDataDod(f);

				}
			}
		}
		return null;
	}

}
