import java.io.File;

/**
 * 
 */

/**
 * @author Selami
 * @param <WipeMetod>
 *
 */
public class FileShred extends Shred {
	private File shredFile;
	private WipeMethod m;

	public FileShred(File f) {
		this.shredFile = f;

	}

	public FileShred(String path, WipeMethod metod) {
		shredFile = new File(path);
		this.m = metod;
	}

	public void Shredding() {

		SetCreationTime(shredFile);
		SetLastModifiedTime(shredFile);
		SetLastAccessTime(shredFile);
		WipeBody(shredFile, m);
		shredFile.delete();
	}

}
