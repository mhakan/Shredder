import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Selami
 *
 */
public class Shred {
	private static final Logger LOGGER = Logger.getLogger(Shred.class.getName());
	private int buffer = 1024;

	public byte[] SetRawBuf(byte[] b, byte val) {
		for (int i = 0; i < b.length; i++) {
			b[i] = val;
		}
		return b;

	}

	public byte[] SetRawBuf(byte[] b, WipeValues val) {
		for (int i = 0; i < b.length; i++) {
			b[i] = val.getPass3();

		}
		return b;

	}

	public void SetCreationTime(File f) {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473600000L) * 10000L);

			Files.setAttribute(f.toPath(), "creationTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void SetLastAccessTime(File f) {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473600000L) * 10000L);
			Files.setAttribute(f.toPath(), "lastAccessTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void SetLastModifiedTime(File f) {

		try {
			FileTime ft = FileTime.fromMillis((new Date().getTime() + 11644473600000L) * 10000L);
			Files.setAttribute(f.toPath(), "lastModifiedTime", ft);
		} catch (IOException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}
	}

	public void WipeBody(File f, WipeMethod method) {

		try {

			RandomAccessFile out = new RandomAccessFile(f.getAbsolutePath(), "rws");

			long fsize = f.length();
			if (fsize < 8192)
				buffer = (short) fsize;
			int lastPart = (int) fsize % buffer;
			int loop = (int) (fsize - lastPart) / buffer;
			byte[] byt = new byte[buffer];
			WipeValues val = new WipeValues();
			if (method == WipeMethod.Zero) {
				try {
					out.seek(0);
				} catch (IOException e1) {
					Logger.getLogger(Shred.class.getName()).log(Level.ALL, e1.getMessage());
				}
				for (int i = 0; i < loop; i++) {
					try {
						out.write(SetRawBuf(byt, (byte) 0));
					} catch (IOException e) {
						Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
					}
				}
				byt = new byte[lastPart];
				try {
					out.write(SetRawBuf(byt, (byte) 0));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (method == WipeMethod.DoD) {
				for (short j = 0; j < 3; j++) {
					try {
						out.seek(0);
					} catch (IOException e1) {
						Logger.getLogger(Shred.class.getName()).log(Level.ALL, e1.getMessage());
					}
					for (int i = 0; i < loop + 1; i++) {
						try {

							if (j < 2)
								out.write(SetRawBuf(byt, val.PassByOrdinary((short) j)), i * buffer, buffer);
							// else
							// out.write(SetRawBuf(byt, val));
						} catch (IOException e) {
							Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
						}
					}
					// Kalan part len%1024
					// try {
					//
					// out.write(new byte[lastPart - 10], (int)
					// out.getFilePointer(), lastPart-10);
					//
					// } catch (IOException e) {
					// Logger.getLogger(Shred.class.getName()).log(Level.ALL,
					// e.getMessage());
					// } finally {
					// try {
					// out.close();
					// } catch (IOException e) {
					//
					// }
					// }
				}
			}

			try {
				out.close();
			} catch (IOException e) {

			}

		} catch (FileNotFoundException e) {
			Logger.getLogger(Shred.class.getName()).log(Level.ALL, e.getMessage());
		}

	}

}
