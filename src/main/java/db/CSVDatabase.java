package db;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import user.AdminUser;
import user.FacultyUser;
import user.StudentUser;

public class CSVDatabase {
	public static void serialize() throws Exception {
		FileWriter csv = new FileWriter("db.csv");
		CSVWriter writer = new CSVWriter();

		AdminUser.serialize(writer);
		FacultyUser.serialize(writer);
		StudentUser.serialize(writer);

		csv.write(writer.construct());
		csv.close();
	}

	public static void deserialize() throws Exception {
		File csv = new File("db.csv");
		Scanner reader = new Scanner(csv);
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			CSVField field = new CSVField(data.split(";", -1));

			AdminUser.deserialize(field);
			FacultyUser.deserialize(field);
			StudentUser.deserialize(field);

		}
		reader.close();
	}

}
