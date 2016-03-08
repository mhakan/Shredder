package ShredderUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import javax.swing.ImageIcon;

public class DiskModel {
	public DiskModel(String[] f) {

		setdeviceName(f[0]);
		setSize(f[1]);
		setPath(GetPath(f[0]));
		setImg(new ImageFactory().DiskType(new FileTypeFactory().GetFileType(f[2])));
		if (f[2].equals("part"))
			setdeviceName("    " + f[0]);

	}

	private String GetPath(String string) {
		return "/dev/" + string;
	}

	private static final short IMAGE_INDEX = 0;
	private static final int DEVICE_INDEX = 1;
	private static final int SIZE_INDEX = 2;
	private static final int DIRECTORY_INDEX = 3;

	private ImageIcon img;
	private String deviceName;
	private String Size;
	private String path;

	public String getdeviceName() {
		return deviceName;
	}

	public void setdeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSize() {
		return Size;
	}

	private void setSize(String size) {
		Size = size;
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}

	public Object GetValue(int colIndex) {
		switch (colIndex) {
		case DEVICE_INDEX:
			return getdeviceName();
		case SIZE_INDEX:
			return getSize();
		case DIRECTORY_INDEX:
			return getPath();
		case IMAGE_INDEX:
			return getImg();
		default:
			return new Object();
		}

	}

	public void setValue(Object value, int colIndex) {
		switch (colIndex) {
		case DEVICE_INDEX:
			setdeviceName((String) value);
		case SIZE_INDEX:
			setSize((String) value);
		case DIRECTORY_INDEX:
			setPath((String) value);
		case IMAGE_INDEX:
			setImg((ImageIcon) value);

		}

	}

	public ImageIcon getImg() {
		return img;
	}

	private void setImg(ImageIcon img) {
		this.img = img;
	}

}
