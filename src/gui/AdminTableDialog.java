package gui;

import java.rmi.RemoteException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import customs.CustomTable;
import model.Game;
import model.Model;
import server.ApplicationSession;

public class AdminTableDialog {

	private Class type;
	private CustomTable table;
	private Shell shell;
	private Composite buttonsComposite;
	private List<? extends Model> filling;

	public AdminTableDialog(Class type, Display display) {
		this.type = type;
		shell = new Shell(display);
		this.buttonsComposite = new Composite(shell, 0);
		table = new CustomTable(shell);
		table.getTable().setHeaderVisible(true);
	}

	public void run() throws RemoteException {
		this.shell.setLayout(new FormLayout());
		this.buttonsComposite.setLayout(new RowLayout());

		FormData buttonsData = new FormData();
		buttonsData.top = new FormAttachment(0, 0);
		buttonsData.left = new FormAttachment(0, 0);
		buttonsData.right = new FormAttachment(100, 0);
		buttonsComposite.setLayoutData(buttonsData);

		FormData tableData = new FormData();
		tableData.top = new FormAttachment(buttonsComposite, 0);
		tableData.left = new FormAttachment(0, 0);
		tableData.right = new FormAttachment(100, 0);
		tableData.bottom = new FormAttachment(100, 0);
		table.getTable().setLayoutData(tableData);

		refill();

		Button addButton = new Button(buttonsComposite, 0);
		addButton.setText("Add");
		addButton.addListener(SWT.MouseUp, e -> {
			if (type == Game.class) {
				new AddDialog(shell.getDisplay())
						.run(new String[] { "Title", "Publisher", "Genre", "Description", "Price" }, type);
			} else {
				new AddDialog(shell.getDisplay()).run(new String[] { "Username", "Password", "Type" }, type);
			}
		});
		Button deleteButton = new Button(buttonsComposite, 0);
		deleteButton.setText("Delete");
		deleteButton.addListener(SWT.MouseUp, e -> {
			if (table.getTable().getSelection().length != 0) {
				try {
					if (type == Game.class) {
						ApplicationSession.getInstance().getServer()
								.deleteGameByTitle(table.getTable().getSelection()[0].getText(0));
					} else {
						ApplicationSession.getInstance().getServer()
								.deleteUserByTitle(table.getTable().getSelection()[0].getText(0));
					}
					refill();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.shell.setMinimumSize(500, 600);
		this.shell.open();
	}

	private void refill() throws RemoteException {
		if (type == Game.class) {
			if (table.getTable().getColumns().length == 0)
				table.setColumns(new String[] { "Title", "Publisher", "Genre", "Price" });
			filling = ApplicationSession.getInstance().getServer().getAllGames();
		} else {
			if (table.getTable().getColumns().length == 0)
				table.setColumns(new String[] { "Username", "Password", "Type" });
			filling = ApplicationSession.getInstance().getServer().getAllUsers();
		}
		table.fillAdmin(filling);
	}

}
