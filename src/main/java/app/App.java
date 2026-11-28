package app;

import ui.*;
import db.*;
import user.*;

public class App {
	public static IMenu menu = new LoginMenu();
	public static boolean running = true;

	public static void main(String[] args) throws Exception {
		CSVDatabase.deserialize();

		new User(UserType.Student, "s1", "student 1", "password");
		new User(UserType.Faculty, "f1", "faculty 1", "password");
		new User(UserType.Admin, "a1", "admin 1", "password");

		while (running) {
			menu.Open();
		}

		CSVDatabase.serialize();
	}
}
