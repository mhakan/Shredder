import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import javax.swing.ImageIcon;

public class FileModel {
	public FileModel(File f) {

		setFileName(f.getAbsolutePath());
		setSize(f.length());
		setType(new FileTypeFactory().GetFileType(f));
		setImg(new ImageFactory().FileType(f.getAbsolutePath()));
		try {
			setLastAccess((FileTime) Files.getAttribute(f.toPath(), "lastAccessTime"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setLastModified((FileTime) Files.getAttribute(f.toPath(), "lastModifiedTime"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setCreatedTime((FileTime) Files.getAttribute(f.toPath(), "creationTime"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final short IMAGE_INDEX = 0;
	private static final int NAME_INDEX = 1;
	private static final int SIZE_INDEX = 2;
	private static final int FILETYPE_INDEX = 3;
	private static final int CREATED_INDEX = 4;
	private static final int LASTACCES_INDEX = 5;
	private static final int LASTMODIFIED_INDEX = 6;

	private ImageIcon img;
	private String fileName;
	private long Size;
	private FileType type;
	private FileTime lastModified;
	private FileTime lastAccess;
	private FileTime createdTime;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getSize() {
		return Size;
	}

	private void setSize(long size) {
		Size = size;
	}

	public FileType getType() {
		return type;
	}

	private void setType(FileType type) {
		this.type = type;
	}

	public FileTime getLastModified() {
		return lastModified;
	}

	private void setLastModified(FileTime lastModified) {
		this.lastModified = lastModified;
	}

	public FileTime getLastAccess() {
		return lastAccess;
	}

	private void setLastAccess(FileTime lastAccess) {
		this.lastAccess = lastAccess;
	}

	protected FileTime getCreatedTime() {
		return createdTime;
	}

	private void setCreatedTime(FileTime createdTime) {
		this.createdTime = createdTime;
	}

	public Object GetValue(int colIndex) {
		switch (colIndex) {
		case NAME_INDEX:
			return getFileName();
		case SIZE_INDEX:
			return getSize();
		case FILETYPE_INDEX:
			return getType();
		case LASTMODIFIED_INDEX:
			return getLastModified();
		case LASTACCES_INDEX:
			return getLastAccess();
		case CREATED_INDEX:
			return getCreatedTime();
		case IMAGE_INDEX:
			return getImg();
		default:
			return new Object();
		}

	}

	public void setValue(Object value, int colIndex) {
		switch (colIndex) {
		case NAME_INDEX:
			setFileName((String) value);
		case SIZE_INDEX:
			setSize((long) value);
		case FILETYPE_INDEX:
			setType((FileType) value);
		case LASTMODIFIED_INDEX:
			setLastModified((FileTime) value);
		case LASTACCES_INDEX:
			setLastAccess((FileTime) value);
		case CREATED_INDEX:
			setCreatedTime((FileTime) value);
		case IMAGE_INDEX:
			setImg((ImageIcon) value);
		default:
			System.out.println("No column such  in here!...");

		}

	}

	public ImageIcon getImg() {
		return img;
	}

	private void setImg(ImageIcon img) {
		this.img = img;
	}

}
