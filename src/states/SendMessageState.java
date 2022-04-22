package states;

public class SendMessageState implements State {

	@Override
	public void setup(ObjectEditor objectEditor) {
		objectEditor.setObjectForKey("Message Viewer", "Send Message");
	}

}
