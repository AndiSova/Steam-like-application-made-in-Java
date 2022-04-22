package handlers;

import java.rmi.RemoteException;

import exceptions.FieldsNotProvided;
import exceptions.PasswordDoesNotMatchException;
import exceptions.UserAlreadyExistsException;
import model.User;
import server.ApplicationSession;

public class RegisterHandler {

	private String username;
	private String password;
	private String confirmPassword;

	public RegisterHandler(String text, String text2, String text3) {
		this.username = text;
		this.password = text2;
		this.confirmPassword = text3;
	}

	public void process() throws FieldsNotProvided, UserAlreadyExistsException, PasswordDoesNotMatchException {
		if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
			throw new FieldsNotProvided("Please provide all the fields!");
		} else if (!password.equals(confirmPassword)) {
			throw new PasswordDoesNotMatchException("The passwords do not match!");
		}

		try {
			User user = ApplicationSession.getInstance().getServer().getUserByUsername(username);
			if (user != null) {
				throw new UserAlreadyExistsException("This username is taken!");
			}

			ApplicationSession.getInstance().getServer().registerUser(username, password, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
