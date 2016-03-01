
public class ShredFactory {

	public Shred ShredType(WipeMethod wipe, String path) {
		if (wipe == WipeMethod.Zero) {
			return new Shred(path);

		} else if (wipe == WipeMethod.DoD) {
			return new ShredDod(path);

		} else
			return null;

	}

}
