package ShredBase;

import java.nio.ByteBuffer;
//
//*Builder pattern
//
import java.util.Random;

public class WipeValues {
	private int Length;
	private int id;

	public WipeValues(int len, short id) {
		this.Length = len;
		this.id = id;

	}

	public WipeValues(int len) {
		this.Length = len;
	}

	public ByteBuffer GenerateValue() {
		if (id == 0)
			return SetRawBuf(Length, ZERO);
		if (id == 1)
			return SetRawBuf(Length, PASS1);
		if (id == 2)
			return SetRawBuf(Length, PASS2);
		else if (id == 3)
			return SetRawBuf(Length);
		return null;

	}

	public static byte ZERO = (0x00), PASS1 = (0X55), PASS2 = (byte) (0XAA);

	private ByteBuffer SetRawBuf(int len, byte val) {
		ByteBuffer b = ByteBuffer.allocate(len);

		for (int i = 0; i < len; i++) {
			b.array()[i] = val;
		}
		return b;

	}

	private ByteBuffer SetRawBuf(int len) {
		ByteBuffer b = ByteBuffer.allocate(len);
		new Random().nextBytes(b.array());
		return b;

	}

}
