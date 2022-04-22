package states;

public class LibraryState implements State {

	@Override
	public void setup(ObjectEditor objectEditor) {
		objectEditor.setObjectForKey("ViewComposite", "LibraryView");
	}

}
