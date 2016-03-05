package ShredderUI;
import java.io.File;

public class FileTypeFactory {
	public FileType GetFileType(File f) {

		if (f.isDirectory())
			return FileType.Directory;
		else if (f.isFile())
			return FileType.File;
		else
			return FileType.Unknown;
	}
}
