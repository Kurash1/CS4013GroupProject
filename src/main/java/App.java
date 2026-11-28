public class App {
	public static IMenu menu = new LoginMenu();
	public static boolean running = true;

	public static void main(String[] args) throws Exception {
		CSVDatabase.deserialize();

		while (running) {
			menu.Open();
		}

		CSVDatabase.serialize();
	}
}
