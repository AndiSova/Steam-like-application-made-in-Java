package gui;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import customs.CustomTable;
import model.Message;
import model.User;
import parents.Dialog;
import server.ApplicationSession;
import states.KeyPages;
import states.ObjectEditor;
import states.SendMessageState;
import states.ViewMessageState;

public class MessageDialog extends Dialog {

	private User user;
	private java.util.List<Message> receivedMessages;
	private java.util.List<Message> sentMessages;
	private Button composeMessage;
	private Label senderL;
	private Text messageText;
	private ObjectEditor oe = new ObjectEditor();;

	public MessageDialog(User user) {
		super("Messages", false);
		this.user = user;
		try {
			this.receivedMessages = ApplicationSession.getInstance().getServer().getReceivedMessages(this.user);
			this.sentMessages = ApplicationSession.getInstance().getServer().getSentMessages(this.user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createDialog() {
		FormLayout fl = new FormLayout();
		fl.marginHeight = 4;
		fl.marginWidth = 4;
		this.shell.setLayout(fl);

		Composite buttonsComposite = new Composite(shell, SWT.BORDER);
		FormData fdB = new FormData();
		fdB.bottom = new FormAttachment(100, 0);
		fdB.left = new FormAttachment(0, 0);
		fdB.right = new FormAttachment(50, 0);
		buttonsComposite.setLayout(new FillLayout());
		buttonsComposite.setLayoutData(fdB);
		composeMessage = new Button(buttonsComposite, SWT.NONE);
		composeMessage.setText("Compose Message");
		Button sentMessages = new Button(buttonsComposite, SWT.NONE);
		sentMessages.setText("View Sent Messages");

		CustomTable table = new CustomTable(shell);
		table.setColumns(new String[] { "sender", "message" });
		table.fill(receivedMessages);
		FormData fdL = new FormData();
		fdL.top = new FormAttachment(0, 0);
		fdL.left = new FormAttachment(0, 0);
		fdL.right = new FormAttachment(50, 0);
		fdL.bottom = new FormAttachment(buttonsComposite);
		table.setLayoutData(fdL);

		Composite message = new Composite(shell, SWT.NONE);
		message.setLayout(new FillLayout());
		FormData fdM = new FormData();
		fdM.top = new FormAttachment(0, 0);
		fdM.left = new FormAttachment(table.getTable(), 0);
		fdM.right = new FormAttachment(100, 0);
		fdM.bottom = new FormAttachment(100, 0);
		message.setLayoutData(fdM);

		Composite messageViewer = new Composite(message, SWT.NONE);
		messageViewer.setLayout(new StackLayout());
		KeyPages messageViewerStates = new KeyPages("Message Viewer", messageViewer, oe);
		createMessageComposites(messageViewer, messageViewerStates);

		composeMessage.addListener(SWT.MouseUp, getComposeListener(composeMessage));
		sentMessages.addListener(SWT.MouseUp, e -> {
			Shell nShell = new Shell(shell.getDisplay());
			nShell.setLayout(new FillLayout());
			CustomTable sentTable = new CustomTable(nShell);
			sentTable.setColumns(new String[] { "receiver", "message" });
			sentTable.fill(this.sentMessages);

			nShell.setSize(300, 400);
			nShell.setMinimumSize(300, 400);
			nShell.open();
		});

		new ViewMessageState().setup(oe);

		table.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String sender = table.getTable().getSelection()[0].getText(0).split(" ")[1];
				String messageTxt = table.getTable().getSelection()[0].getText(1);
				senderL.setText("Sender: " + sender);
				messageText.setText(messageTxt);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!ApplicationStart.getInstance().getDisplay().isDisposed() && !shell.isDisposed()) {
					try {
						receivedMessages = ApplicationSession.getInstance().getServer().getReceivedMessages(user);
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								if (!shell.isDisposed()) {
									table.fill(receivedMessages);
								}
							}
						});
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private Listener getComposeListener(Button composeMessage) {
		return new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				new SendMessageState().setup(oe);
				composeMessage.setEnabled(false);
			}
		};
	}

	private void createMessageComposites(Composite messageViewer, KeyPages messageViewerStates) {
		Composite viewMessage = new Composite(messageViewer, SWT.BORDER);
		viewMessage.setLayout(new FormLayout());
		Composite sender = new Composite(viewMessage, SWT.NONE);
		FormData fdSR = new FormData();
		fdSR.top = new FormAttachment(0, 0);
		fdSR.left = new FormAttachment(0, 0);
		fdSR.right = new FormAttachment(100, 0);
		sender.setLayoutData(fdSR);
		sender.setLayout(new FillLayout(SWT.VERTICAL));
		senderL = new Label(sender, SWT.NONE);
		senderL.setText("Sender: ");

		messageText = new Text(viewMessage, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		messageText.setEditable(false);
		FormData fdT = new FormData();
		fdT.top = new FormAttachment(sender, 4);
		fdT.bottom = new FormAttachment(100, 0);
		fdT.left = new FormAttachment(0, 0);
		fdT.right = new FormAttachment(100, 0);
		messageText.setLayoutData(fdT);

		messageViewerStates.addPage("View Message", viewMessage);

		//

		Composite sendMessage = new Composite(messageViewer, SWT.BORDER);
		sendMessage.setLayout(new FormLayout());
		Composite receiver = new Composite(sendMessage, SWT.BORDER);
		receiver.setLayout(new GridLayout(2, false));
		FormData fdR = new FormData();
		fdR.top = new FormAttachment(0, 0);
		fdR.left = new FormAttachment(0, 0);
		fdR.right = new FormAttachment(100, 0);
		receiver.setLayoutData(fdR);
		Label receiverL = new Label(receiver, SWT.NONE);
		receiverL.setText("Receiver:");
		Text receiverT = new Text(receiver, SWT.BORDER);
		receiverT.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		Composite buttonsCompositeSB = new Composite(sendMessage, SWT.NONE);
		buttonsCompositeSB.setLayout(new FillLayout());
		Text textMessage = new Text(sendMessage, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		FormData fdTM = new FormData();
		fdTM.top = new FormAttachment(receiver, 4);
		fdTM.left = new FormAttachment(0, 0);
		fdTM.right = new FormAttachment(100, 0);
		fdTM.bottom = new FormAttachment(buttonsCompositeSB, 0);
		textMessage.setLayoutData(fdTM);

		FormData fmB = new FormData();
		fmB.bottom = new FormAttachment(100, 0);
		fmB.left = new FormAttachment(0, 0);
		fmB.right = new FormAttachment(100, 0);
		buttonsCompositeSB.setLayoutData(fmB);

		Button sendButton = new Button(buttonsCompositeSB, SWT.NONE);
		Button backButton = new Button(buttonsCompositeSB, SWT.NONE);
		sendButton.setText("Send Message");
		backButton.setText("Go Back!");

		backButton.addListener(SWT.MouseUp, e -> {
			composeMessage.setEnabled(true);
			new ViewMessageState().setup(oe);
		});
		sendButton.addListener(SWT.MouseUp, e -> {
			try {
				User receiverUser = ApplicationSession.getInstance().getServer().getUserByUsername(receiverT.getText());
				System.out.println(receiverUser.getUserName());
				Message toBeSendMessage = new Message(user, receiverUser, textMessage.getText());
				ApplicationSession.getInstance().getServer().sendMessage(toBeSendMessage);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			composeMessage.setEnabled(true);
			new ViewMessageState().setup(oe);
		});

		messageViewerStates.addPage("Send Message", sendMessage);
	}

	@Override
	public void shellSetSize() {
		this.shell.setSize(600, 400);
		this.shell.setMinimumSize(600, 400);
	}
}
