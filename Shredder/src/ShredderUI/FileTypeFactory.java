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

	public FileType GetFileType(String f) {

		if (f.equals("disk"))
			return FileType.Disk;
		else if (f.equals( "part"))
			return FileType.Partition;
		else
			return FileType.Unknown;
	}

}
