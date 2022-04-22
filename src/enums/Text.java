package enums;

public enum Text {
	BUY_TEXT("Buy Game!");

	private String text;

	Text(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
