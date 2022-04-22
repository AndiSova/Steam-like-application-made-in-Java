package model;

public enum Genre {
	HORROR("Horror"), //
	RPG("Roleplay"), //
	FPS("First Person Shooter"), //
	HACK_SLASH("Hack & Slash"), //
	STRATEGY("Strategy"), //
	RACING("Racing"), //
	OPEN_WORLD("Open World"), //
	SURVIVAL("Survival"), //
	PLATFORMER("Platformer"), //
	SPORTS("Sports"), //
	MOBA("MOBA"), //
	SANDBOX("Sandbox"), //
	CASUAL("Casual"), //
	FIGHTING("Fighting"), //
	TOWER_DEFENSE("Tower_Defense"), //
	CARD_GAME("Card Game"), //
	RTS("RTS"), //
	PUZZLE("Puzzle"), //
	ACTION_ADVENTURE("Action-Adventure");

	private String name;

	private Genre(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Genre getGenre(String text) {
		for (Genre genre : Genre.values()) {
			if (genre.getName().equals(text))
				return genre;
		}
		return null;
	}
}
