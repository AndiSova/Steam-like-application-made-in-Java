package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import customs.InputField;
import enums.ControlDimensions;
import exceptions.FieldsNotProvided;
import exceptions.PasswordDoesNotMatchException;
import exceptions.UserAlreadyExistsException;
import handlers.RegisterHandler;
import parents.Dialog;

public class RegisterDialog extends Dialog {
	private InputField usernameField;
	private InputField passwordField;
	private InputField confirmPasswordField;

	private Label errorLabel;

	public RegisterDialog(String title) {
		super(title, true);
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new GridLayout(1, false));

		usernameField = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		usernameField.setText("Username: ");

		passwordField = new InputField(shell, SWT.BORDER | SWT.PASSWORD, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		passwordField.setText("Password: ");

		confirmPasswordField = new InputField(shell, SWT.BORDER | SWT.PASSWORD, ControlDimensions.INPUT_FIELD_WIDTH, //
				ControlDimensions.INPUT_FIELD_HEIGHT, true);
		confirmPasswordField.setText("Confirm Password: ");

		errorLabel = new Label(shell, SWT.NONE);
		errorLabel.setText("-".repeat(62));
		errorLabel.setVisible(false);

		Button register = new Button(shell, SWT.NONE);
		register.setText("REGISTER");

		register.addListener(SWT.MouseUp, e -> {
			registerProcess();
		});
	}

	private void registerProcess() {
		try {
			new RegisterHandler(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText())
					.process();

			new MainDialog(usernameField.getText()).run();
			this.shell.dispose();
		} catch (FieldsNotProvided | UserAlreadyExistsException | PasswordDoesNotMatchException e) {
			errorLabel.setText(e.getMessage());
			errorLabel.setVisible(true);
		}

	}

}
