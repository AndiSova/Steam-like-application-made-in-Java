package customs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import model.Game;
import model.Model;
import model.User;

public class CustomTable {

	private Table table;

	public CustomTable(Composite parent) {
		table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
	}

	public void fill(List<? extends Model> filling) {
		table.removeAll();
		for (Model fill : filling) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(fill.getData());
		}

		setSize();
	}

	public void fillAdmin(List<? extends Model> filling) {
		table.removeAll();
		for (Model fill : filling) {
			TableItem item = new TableItem(table, SWT.NONE);
			if (fill instanceof Game) {
				Game game = (Game) fill;
				item.setText(new String[] { game.getTitle(), game.getPublisher(), game.getGameGenres().getName(),
						game.getPrice() + "" });
			} else {
				User user = (User) fill;
				item.setText(new String[] { user.getUserName(), user.getPassword(), user.getAccesLevel() + "" });
			}
		}

		setSize();
	}

	public void setColumns(String[] titles) {
		for (String title : titles) {
			new TableColumn(table, SWT.NONE).setText(title);
		}
	}

	public void setSize() {
		TableColumn clm = table.getColumn(0);
		clm.pack();
		for (TableColumn clmn : table.getColumns()) {
			clmn.setWidth(clm.getWidth());
		}
	}

	public void setLayoutData(FormData formData) {
		table.setLayoutData(formData);
	}

	public Table getTable() {
		return table;
	}
}
