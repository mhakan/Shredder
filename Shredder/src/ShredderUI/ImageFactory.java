package ShredderUI;
import java.io.File;

import javax.swing.ImageIcon;

public class ImageFactory {
	public ImageIcon FileType(String path) {
		File f = new File(path);
		if (f.isFile()) {
			return new Images().getFileImg();

		} else if (f.isDirectory()) {
			return new Images().getDirImg();

		} else
			return new Images().defImg;

	}
}
