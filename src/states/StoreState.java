package states;

public class StoreState implements State {

	@Override
	public void setup(ObjectEditor objectEditor) {
		objectEditor.setObjectForKey("ViewComposite", "StoreView");
	}

}
