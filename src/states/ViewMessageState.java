package states;

public class ViewMessageState implements State {

	@Override
	public void setup(ObjectEditor objectEditor) {
		objectEditor.setObjectForKey("Message Viewer", "View Message");
	}

}
