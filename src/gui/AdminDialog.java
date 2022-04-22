package gui;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;

import model.Game;
import model.User;
import parents.Dialog;

public class AdminDialog extends Dialog {

	public AdminDialog() {
		super("Admin Dialog", false);
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new FillLayout(SWT.VERTICAL));
		Button viewGameList = new Button(shell, SWT.NONE);
		viewGameList.setText("View Games");
		Button viewUserList = new Button(shell, SWT.NONE);
		viewUserList.setText("View Users");

		viewGameList.addListener(SWT.MouseUp, e -> {
			try {
				new AdminTableDialog(Game.class, shell.getDisplay()).run();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
		viewUserList.addListener(SWT.MouseUp, e -> {
			try {
				new AdminTableDialog(User.class, shell.getDisplay()).run();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

	}

	@Override
	public void shellSetSize() {
		this.shell.setSize(300, 400);
		this.shell.setMinimumSize(300, 400);
		this.shell.setMaximized(false);
		this.shell.setMinimized(false);
	}
}
