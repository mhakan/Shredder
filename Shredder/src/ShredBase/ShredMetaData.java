package ShredBase;

import java.io.File;

import ShredderUI.ShredObserver;

public class ShredMetaData implements IShred {
	File f = null;

	public ShredMetaData(File f) {
		this.f = f;

		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Shred(ShredObserver sho) {
		DeleteAll(sho);
		f.delete();
	}

	protected void DeleteAll(ShredObserver sho) {
		MetaData m = new MetaData(f);
		m.SetWritable();
		m.add(sho);
		m.startClear();

		
	}
}
