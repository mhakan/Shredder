import java.util.Random;

public class WipeValues {

	private byte ZERO = (0x00), PASS1 = (0X55), PASS2 = (byte) (0XAA), PASS3 = (GetRandom());

	public byte getZERO() {
		return ZERO;

	}

	public byte getPass1() {
		return PASS1;

	}

	public byte getPass2() {
		return PASS2;

	}

	public byte getPass3() {
		return GetRandom();

	}

	public byte PassByOrdinary(short i) {
		if (i == 0)
			return getZERO();
		else if (i == 1)
			return getPass1();
		else if (i == 2)
			return getPass2();
		else if (i == 3)
			return getPass3();
		return 0;

	}

	private byte GetRandom() {
		Random r = new Random();
		return (byte) r.nextInt(255);
	}

}
