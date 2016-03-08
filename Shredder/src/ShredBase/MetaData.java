package ShredBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaData implements IMetaData {
	File file;

	public MetaData(File f) {
		this.file = f;
		if (f == null || Files.exists(f.toPath()) == false) {
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void MetaDataClear() {
		SetCreationTime();
		SetLastModifiedTime();
		SetLastAccessTime();

	}

	public void NameClear() {
		// Not implemented yet
	}

	public void SetCreationTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473600000L) * 10000L);

			Files.setAttribute(file.toPath(), "creationTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void SetLastAccessTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473700000L) * 10000L);
			Files.setAttribute(file.toPath(), "lastAccessTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void SetLastModifiedTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 1164447400000L) * 10000L);
			Files.setAttribute(file.toPath(), "lastModifiedTime", ft);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
		}
	}
}
