package states;

public class ProfileState implements State {

	@Override
	public void setup(ObjectEditor objectEditor) {
		objectEditor.setObjectForKey("ViewComposite", "ProfileView");
	}

}
