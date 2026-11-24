import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class CSVDatabase {
	public static void serialize() throws Exception {
		FileWriter csv = new FileWriter("db.csv");
		CSVWriter writer = new CSVWriter();
		User.serialize(writer);
		csv.write(writer.construct());
		csv.close();
	}

	public static void deserialize() throws Exception {
		File csv = new File("db.csv");
		Scanner reader = new Scanner(csv);
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			CSVField field = new CSVField(data.split(";", -1));
			User.deserialize(field);
		}
		reader.close();
	}
}
