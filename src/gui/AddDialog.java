package gui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import customs.InputField;
import enums.ControlDimensions;
import model.Game;
import model.Genre;
import model.User;
import server.ApplicationSession;

public class AddDialog {
	private Shell shell;
	private List<InputField> fields = new ArrayList<>();
	private Combo genreCombo;

	public AddDialog(Display display) {
		this.shell = new Shell(display);
	}

	public void run(String[] strings, Class type) {
		this.shell.setLayout(new FillLayout(SWT.VERTICAL));
		for (String string : strings) {
			if (string.equals("Genre")) {
				genreCombo = new Combo(shell, SWT.BORDER);
				for (Genre genre : Genre.values()) {
					genreCombo.add(genre.getName());
				}
			} else {
				InputField ipf = new InputField(shell, SWT.BORDER, ControlDimensions.INPUT_FIELD_WIDTH,
						ControlDimensions.INPUT_FIELD_HEIGHT, true);
				ipf.setText(string);
				fields.add(ipf);
			}
		}

		Button submitButton = new Button(shell, 0);
		submitButton.setText("Add");
		submitButton.addListener(SWT.MouseUp, e -> {
			if (type == Game.class) {
				Genre genre = Genre.getGenre(genreCombo.getText());
				if (genre != null) {
					Game game = new Game(fields.get(0).getText(), fields.get(2).getText(), fields.get(1).getText(),
							genre, Double.parseDouble(fields.get(3).getText()));
					try {
						ApplicationSession.getInstance().getServer().addGame(game);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				User user = new User(fields.get(0).getText(), fields.get(1).getText(), 0);
				try {
					ApplicationSession.getInstance().getServer().addUser(user);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		shell.pack();
		shell.open();
	}
}
