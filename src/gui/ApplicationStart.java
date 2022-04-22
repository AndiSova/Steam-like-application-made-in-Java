package gui;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;

import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import helper.Util;

public class ApplicationStart {

	private Display display;
	private Shell shell;
	private Properties properties;
	private Properties gameProperties;
	private static ApplicationStart instance = new ApplicationStart();

	public static void main(String[] args) throws RemoteException {
		ApplicationStart instance = ApplicationStart.getInstance();
		instance.run();
	}

	private void run() {
		new LogInDialog("Log In").run();
		this.shell.setSize(1, 1);
		this.shell.open();
		this.shell.setVisible(false);
		this.shell.setEnabled(false);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public ApplicationStart() {
		this.display = new Display();
		this.shell = new Shell(display);
		try {
			properties = new Properties();
			properties.load(new FileReader(Util.openConfigFile("/SteamCopyCat/config/gameList.config")));
			gameProperties = new Properties();
			gameProperties.load(new FileReader(Util.openConfigFile("/SteamCopyCat/config/profileGame.config")));
			if (gameProperties.getProperty("lastButton") == null) {
				gameProperties.put("lastButton", "-1");
				gameProperties.store(new FileWriter(Util.openConfigFile("/SteamCopyCat/config/profileGame.config")),
						"");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ApplicationStart getInstance() {
		return instance;
	}

	public Display getDisplay() {
		return display;
	}

	public Decorations getShell() {
		return shell;
	}

	public Properties getProperties() {
		return properties;
	}

	public Properties getGameProperties() {
		return gameProperties;
	}
}
