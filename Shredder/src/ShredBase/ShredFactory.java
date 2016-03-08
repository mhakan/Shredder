package ShredBase;
import java.io.File;

public class ShredFactory {

	public ShredFile ShredType(WipeMethod wipe, String path) {
		if (wipe == WipeMethod.Zero) {
			return new ShredFile(new File(path));

		} else if (wipe == WipeMethod.DoD) {
			return new ShredDod(path);

		} else
			return null;

	}

}
