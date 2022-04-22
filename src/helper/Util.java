package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.List;

import model.Model;

public class Util {
	public static FormData getFormData(int top, int bottom, int left, int right) {
		FormData data = new FormData();

		data.top = new FormAttachment(top, 0);
		data.bottom = new FormAttachment(bottom, 0);
		data.left = new FormAttachment(left, 0);
		data.right = new FormAttachment(right, 0);

		return data;
	}

	public static void fillList(List games, java.util.List<? extends Model> allGames) {
		games.removeAll();

		allGames.stream().forEach(g -> {
			games.add(g.getData()[0]);
		});
	}

	public static File openConfigFile(String stringPath) throws IOException {
		File file = new File(stringPath);

		if (!file.exists()) {
			Path path = Paths.get(file.getAbsolutePath());
			Files.createDirectories(path.getParent());
			Files.createFile(path);
		}

		return file;
	}
}
