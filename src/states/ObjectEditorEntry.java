package states;

import java.util.ArrayList;
import java.util.List;

public class ObjectEditorEntry {
	String key;
	Object value;
	List<ObjectEditorListener> listeners = new ArrayList<>();

	public void setValue(Object value) {
		this.value = value;
	}

	public void notifyListeners() {
		for (ObjectEditorListener oel : listeners) {
			oel.onUpdate(value);
		}
	}

	public void addListener(ObjectEditorListener listener) {
		listeners.add(listener);
	}
}
