package app;

import ui.*;
import uni.*;

import java.util.Scanner;

import db.*;
import user.*;

public class App {
	public static IMenu menu = new LoginMenu();
	public static boolean running = true;
	public static Scanner input;

	public static void main(String[] args) throws Exception {
		input = new Scanner(System.in);
		CSVDatabase.deserialize();

		Student s = new Student("Test", "None", "254", "a@gmail.com", "", "", "");
		new StudentUser("s1", "password", s);

		while (running) {
			menu.Open();
		}

		CSVDatabase.serialize();
	}
}
