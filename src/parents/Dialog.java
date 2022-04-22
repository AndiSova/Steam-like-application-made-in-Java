package parents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import enums.Styles;
import gui.ApplicationStart;

public abstract class Dialog {
	protected Shell shell;

	public Dialog(String dialogTitle, boolean hardClose) {
		this.shell = new Shell(ApplicationStart.getInstance().getDisplay(), Styles.SHELL_SIZE.getStyle());
		this.shell.setText(dialogTitle);

		if (hardClose) {
			this.shell.addListener(SWT.Close, e -> {
				ApplicationStart.getInstance().getShell().dispose();
			});
		}
	}

	public abstract void createDialog();

	public void run() {
		createDialog();
		shellSetSize();

		this.shell.open();
	}

	public void shellSetSize() {
		this.shell.pack();
	}

	public void dispose() {
		this.shell.dispose();
	}
}
