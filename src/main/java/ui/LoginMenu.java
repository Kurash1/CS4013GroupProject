package ui;

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
		boolean logged_in = false;
		logged_in = IUser.tryLogin(user, App.input.next());
		while (logged_in == false) {
			System.out.println("Invalid password");
			System.out.println("Enter your Password: ");
			logged_in = IUser.tryLogin(user, App.input.next());
		}

		App.menu = user.getMenu();
	}
}
