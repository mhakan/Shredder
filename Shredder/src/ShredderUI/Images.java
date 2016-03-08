package ShredderUI;

import javax.swing.ImageIcon;

public class Images {
	private ImageIcon fileImg;
	private ImageIcon diskImg;
	private ImageIcon partImg;

	private ImageIcon dirImg;
	public ImageIcon defImg = new ImageIcon("/com/sun/java/swing/plaf/windows/icons/image-delayed.png");

	public ImageIcon getFileImg() {
		return new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/file.gif"));
	}

	protected void setFileImg(ImageIcon fileImg) {
		this.fileImg = fileImg;
	}

	public ImageIcon getDirImg() {
		return new ImageIcon(getClass().getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif"));
	}

	protected void setDirImg(ImageIcon dirImg) {
		this.dirImg = dirImg;
	}

	public ImageIcon getPartImg() {
		return new ImageIcon(getClass().getResource("/images/Partition-Magic-icon.png"));

	}

	protected ImageIcon getDiskImg() {
		return new ImageIcon(getClass().getResource("/images/disk-icon.png"));
	}

	protected void setDiskImg(ImageIcon diskImg) {
		this.diskImg = diskImg;
	}
}
