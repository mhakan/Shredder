package ShredBase;
//
//used for Disk and Partition
//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Body  implements IBody {
	File f;

	public Body(File f) {
		
		this.f = f;

	}

	public void WipeMetod(short id) {
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
				Logger.getLogger(getClass().getName()).log(Level.ALL, e1.getMessage());
			}
			for (int i = 0; i < loop; i++) {
				try {
					out.write(new WipeValues(buffer, id).GenerateValue());
				} catch (IOException e) {
					Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
				}
			}

			try {
				out.write(new WipeValues(lastPart, id).GenerateValue());
			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.ALL, e.getMessage());
			}

			try {
				out.close();
			} catch (IOException e) {

			}

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

}
