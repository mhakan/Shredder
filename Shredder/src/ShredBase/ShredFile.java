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
public class ShredFile extends Body  {

	protected File f;

	public ShredFile(File file) {
		super(file);
		if (file.isDirectory())
			try {
				finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	

	public void WipeFile() {

		WipeMetod((short) 0);
	
		SetFileLength();
		f.delete();
	}




	private void SetFileLength() {
		try {
			RandomAccessFile rand = new RandomAccessFile(f, "rws");
			rand.setLength(0);
			rand.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}