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

/**
 * 
 */

/**
 * @author Selami
 *
 */
public class Shred implements IShredFile {

	protected File f;

	public Shred(File file) {
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

	public Shred(String path) {
		f = new File(path);
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

		WipeMetod((short) 0);
		SetCreationTime();
		SetLastModifiedTime();
		SetLastAccessTime();

		f.delete();
	}

	protected void SetCreationTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473600000L) * 10000L);

			Files.setAttribute(f.toPath(), "creationTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	protected void SetLastAccessTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473700000L) * 10000L);
			Files.setAttribute(f.toPath(), "lastAccessTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	protected void SetLastModifiedTime() {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473500000L) * 10000L);
			Files.setAttribute(f.toPath(), "lastModifiedTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void NameClear() {

	}

	protected void WipeMetod(short id) {
		RandomAccessFile out;
		try {
			out = new RandomAccessFile(f.getAbsolutePath(), "rws");

			int buffer = 4096;
			long fsize = f.length();
			if (fsize < 8192)
				buffer = (int) fsize;
			int lastPart = (int) fsize % buffer;
			int loop = (int) (fsize - lastPart) / buffer;

			try {
				out.seek(0);
			} catch (IOException e1) {
				Logger.getLogger(Shred.class.getName()).log(Level.ALL, e1.getMessage());
			}
			for (int i = 0; i < loop; i++) {
				try {
					out.write(new WipeValues(buffer, id).GenerateValue());
				} catch (IOException e) {
					Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
				}
			}

			try {
				out.write(new WipeValues(lastPart, id).GenerateValue());
			} catch (IOException e) {
				Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
			}

			try {
				out.close();
			} catch (IOException e) {

			}
			SetFileLength(out);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	private void SetFileLength(RandomAccessFile out) {
		try {
			out.setLength(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}