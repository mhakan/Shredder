package ShredBase;

import java.io.File;
import java.nio.file.Files;

public class ShredDod extends ShredFile {

	public ShredDod(String path) {
		super(new File(path));
		this.f = new File(path);

		if (f == null || Files.exists(f.toPath()) == false) {
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	public ShredDod(File file) {
		super(file);
		this.f = file;
		if (f == null || Files.exists(f.toPath()) == false) {
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void WipeFile() {

		for (short j = 0; j < 3; j++) {
			WipeMetod((short) (j + 1));

		}
		// new new A(f).MetaDataClear();

		f.delete();
	}

}
