package ui;

import java.util.Scanner;
import user.*;
import app.*;

public class LoginMenu implements IMenu {
	public void Open() throws Exception {
		System.out.println("===== LOGIN MENU =====");

		System.out.println("Enter your User Name: ");
		IUser user = IUser.getUser(App.input.next());
		while (user == null) {
			System.out.println("No such user");
			System.out.println("Enter your User Name: ");
			user = IUser.getUser(App.input.next());
		}

		System.out.println("Enter your Password: ");
		Login login = Login.TryLogin(user, App.input.next());
		while (login == null) {
			System.out.println("Invalid password");
			System.out.println("Enter your Password: ");
			login = Login.TryLogin(user, App.input.next());
		}

		App.menu = user.getMenu();
	}
}
