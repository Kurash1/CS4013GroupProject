package app;

import ui.*;
import java.util.Scanner;
import db.*;

public class App {
	public static IMenu menu = new LoginMenu();
	public static boolean running = true;
	public static Scanner input;

	public static void main(String[] args) throws Exception {
		input = new Scanner(System.in);
		CSVDatabase.deserialize();

		while (running) {
			menu.Open();
		}

		CSVDatabase.serialize();
	}
}
