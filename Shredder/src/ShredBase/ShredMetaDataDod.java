package ShredBase;

import java.io.File;

import ShredderUI.ShredObserver;

public class ShredMetaDataDod extends ShredMetaData {
	File f;

	public ShredMetaDataDod(File f) {
		super(f);
		this.f = f;
	}

	public void Shred(ShredObserver sho) {

		for (int i = 0; i < 3; i++) {

			DeleteAll(sho);
		}
		f.delete();
	}
}
