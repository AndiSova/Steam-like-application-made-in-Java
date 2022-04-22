package handlers;

import java.rmi.RemoteException;

import exceptions.FieldsNotProvided;
import exceptions.InvalidUserException;
import model.User;
import server.ApplicationSession;

public class LogInHandler {

	private final String username;
	private final String password;

	public LogInHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void process() throws FieldsNotProvided, InvalidUserException {
		if (username.isBlank() || password.isBlank()) {
			throw new FieldsNotProvided("You have not provided a username or password!");
		}

		try {
			User user = ApplicationSession.getInstance().getServer().getUserByUsername(username);
			if (user == null) {
				throw new InvalidUserException("This username/password combination does not exist!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
