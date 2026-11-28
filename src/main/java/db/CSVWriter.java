package db;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;

public class CSVWriter {
	private StringBuilder builder;

	public CSVWriter() {
		builder = new StringBuilder();
	}

	private void write(String value) {
		builder.append(";");
		builder.append(Base64.getEncoder().encodeToString(
				value.getBytes(StandardCharsets.UTF_8)));
	}

	public void writeBytes(byte[] value) {
		builder.append(";");
		builder.append(Base64.getEncoder().encodeToString(value));
	}

	public void startObject(String type) {
		builder.append(type);
	}

	public void endObject() {
		builder.append("\n");
	}

	public void writeInt(int value) {
		write(String.valueOf(value));
	}

	public void writeString(String value) {
		write(value);
	}

	public void writeKey(SecretKey key) {
		writeBytes(key.getEncoded());
	}

	public String construct() {
		return builder.toString();
	}
}
