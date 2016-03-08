package ShredBase;

import java.io.File;

public class ShredMetaData extends MetaData {
	public ShredMetaData(String path) {
		super(new File(path));
	}

}
