import java.util.EnumSet;
import java.util.Scanner;

public class LoginMenu implements IMenu {
	private static EnumSet<Permission> sys = Permission.SUPER_PERMISSIONS;

	public void Open() throws Exception {
		Scanner input = new Scanner(System.in);

		System.out.println("=== LOGIN MENU ===");

		System.out.println("Enter your User Name: ");
		User user = User.getUser(input.next(), sys);
		while (user == null) {
			System.out.println("No such user");
			System.out.println("Enter your User Name: ");
		}

		System.out.println("Enter your Password: ");
		Login login = Login.TryLogin(user, input.next());
		while (login == null) {
			System.out.println("Invalid password");
			System.out.println("Enter your Password: ");
			login = Login.TryLogin(user, input.next());
		}

		UserType type = user.getType(sys); // users do not have the right to read themself
		if (type == UserType.Student) {
			App.running = false;
		} else if (type == UserType.Faculty) {
			App.running = false;
		} else if (type == UserType.Admin) {
			App.running = false;
		} else if (type == UserType.System) {
			App.running = false;
		} else {
			App.running = false;
		}

		input.close();
	}
}
