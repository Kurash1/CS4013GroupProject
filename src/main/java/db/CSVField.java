package db;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CSVField {
	private String[] fields;

	public CSVField(String[] fields) {
		this.fields = fields;
	}

	public String getType() {
		return fields[0];
	}

	public String get(int index) {
		return fields[index + 1];
	}

	public int getInt(int index) {
		return Integer.parseInt(getString(index));
	}

	public String getString(int index) {
		return new String(getBytes(index), StandardCharsets.UTF_8);
	}

	public byte[] getBytes(int index) {
		return Base64.getDecoder().decode(get(index));
	}

	public SecretKey getKey(int index) {
		return new SecretKeySpec(getBytes(index), "AES");
	}
}
