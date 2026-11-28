package user;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import ui.*;

public interface IUser {
	public byte[] getPasswordHashed();

	public SecretKey getPasswordSalt();

	public IMenu getMenu();

	public static Map<String, IUser> users = new HashMap<String, IUser>();

	public static IUser getUser(String username) {
		return users.getOrDefault(username, null);
	}
}
