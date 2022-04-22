package states;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

public class KeyPages implements ObjectEditorListener {
	Map<Object, Composite> pages = new HashMap<>();
	private Composite container;

	public KeyPages(String key, Composite container, ObjectEditor oe) {
		this.container = container;
		oe.addObjectEditorListener(key, this);
	}

	public void addPage(String pageId, Composite page) {
		pages.put(pageId, page);
	}

	@Override
	public void onUpdate(Object value) {
		StackLayout stackLayou = ((StackLayout) container.getLayout());
		stackLayou.topControl = pages.get(value);
		container.layout();
	}
}
