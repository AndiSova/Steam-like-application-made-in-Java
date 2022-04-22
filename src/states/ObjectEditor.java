package states;

import java.util.HashMap;
import java.util.Map;

public class ObjectEditor {
	private Map<String, ObjectEditorEntry> data = new HashMap<>();

	public void setObjectForKey(String key, Object value) {
		ObjectEditorEntry entry = data.get(key);
		if (entry == null) {
			entry = new ObjectEditorEntry();
			data.put(key, entry);
		}
		entry.setValue(value);
		entry.notifyListeners();
	}

	public void addObjectEditorListener(String key, ObjectEditorListener listener) {
		ObjectEditorEntry entry = data.get(key);
		if (entry == null) {
			entry = new ObjectEditorEntry();
			data.put(key, entry);
		}
		entry.addListener(listener);
	}

	public Map<String, ObjectEditorEntry> getData() {
		return data;
	}

	public Object getObjectForKey(String key) {
		return getData().get(key).value;
	}
}
