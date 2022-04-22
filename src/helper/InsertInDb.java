package helper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import model.Genre;
import model.User;
import server.ApplicationSession;
import server.ServerApplication;

public class InsertInDb {
	private List<User> users = new ArrayList<User>();

	public static void main(String[] args) {
		InsertInDb db = new InsertInDb();
		try {
			ServerApplication.main(args);
			db.addGames();
			db.addUsers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void addGames() throws RemoteException {
		List<Game> games = new ArrayList<>();

		games.add(new Game("Assassin's Creed Empire",
				"Expirience history through the eyes of Aurelius, a roman assassin during the third century crysis",
				"Ubisoft", Genre.ACTION_ADVENTURE, 60));
		games.add(new Game("Half Life 3", "The return of the beloved franchise!", "Valve", Genre.FPS, 33333.33));
		games.add(new Game("Total War: Rome3",
				"Control history as the mighty romans powerfull greeks or brave celts in the newst intallment in the Total War franchise",
				"Sega", Genre.STRATEGY, 60));
		games.add(new Game("Ryse:Son of Rome 2",
				"Continue the story of Marcus Titus, years after defeating Emperor Nero in a new battlefield under a new Emperor, Trajan's conquest of Dacia",
				"Microsoft", Genre.ACTION_ADVENTURE, 70));
		games.add(new Game("Far Cry 2 Remake", "Reexpirience the timeless classic", "Ubisoft", Genre.FPS, 40));
		games.add(new Game("Assassin's Creed Unity remake", "The actual release of the game", "Ubisoft",
				Genre.ACTION_ADVENTURE, 40));
		games.add(new Game("The Elder Scrolls V:Skyrim 2", "There's another one coming", "Bethesda", Genre.OPEN_WORLD,
				70));
		games.add(new Game("Dark Souls 4", "I wished this was true ngl", "Fromsoftware", Genre.RPG, 60));
		games.add(new Game("Super Mario PC", "Experience the Super Mario games collection on PC for the first time",
				"Nintendo", Genre.PLATFORMER, 50));
		games.add(new Game("Donkey Kong", "Monke", "Nintendo", Genre.PLATFORMER, 70));
		games.add(new Game("Sleeping Dogs 2",
				"Experience Shanghai through the eyes of a detective, searching for the killer of his wife through the slums of the city, in an Open World setting",
				"THQ Nordic", Genre.OPEN_WORLD, 100));
		games.add(new Game("Kingdom Come Deliverance 2",
				"Continue the sotry of Henry and Hans Capon through 15th century Bohemia and Bavaria", "THQ Nordic",
				Genre.OPEN_WORLD, 110));
		games.add(new Game("Watch_Dogs Grandma", "Like Legion, but only grandmas", "Ubisoft", Genre.OPEN_WORLD, 90));
		games.add(new Game("Beyond Good and Evil 2", "Release date when", "Ubisoft", Genre.OPEN_WORLD, 200));
		games.add(new Game("Ghost of Tsushima 2", "This time, the game is not on the island but on mainland Japan",
				"Sony", Genre.OPEN_WORLD, 250));
		games.add(new Game("Forza Horizon 6", "Race through the beautifull country of Romania", "Microsoft",
				Genre.RACING, 80));
		games.add(new Game("GTA 6", "Finally, no more GTA 5", "Rockstar Games", Genre.OPEN_WORLD, 6666.66));
		games.add(new Game("Red Dead Redemption 3",
				"Experience Dutch and Hoseas backstory in the middle of the 19th century Wild West", "Rockstar Games",
				Genre.OPEN_WORLD, 300));
		games.add(new Game("Star Wars KOTOR3", "Time for a real game", "EA", Genre.ACTION_ADVENTURE, 200));
		games.add(new Game("Batman Arkham Protector",
				"Set several years after Arkham Knight, play again as Bruce Wayne and fight against your biggest threat yet, Superman! IGN quote about the game: It really makes you feeeel like Batman",
				"Warner Bros.", Genre.ACTION_ADVENTURE, 300));
		games.add(new Game("My Little Pony the Battle Royale Game", "Finally, a battle royale I can get behind",
				"Rockstar Games", Genre.FPS, 555));
		games.add(new Game("Minecraft 2", "Notch is back at Mojang and is ready to reshape gaming again!", "Microsoft",
				Genre.SURVIVAL, 1000));
		games.add(new Game("Outlast 3", "Scarier than the other 2 combined!", "Red Barrels", Genre.HORROR, 70));
		games.add(new Game("Call of Duty Squirrel Wars",
				"Experience the most brutal battlefield in existence, the squirrel war theather. It's gonna be NUTS!",
				"Activision", Genre.FPS, 80));
		games.add(new Game("Subnautica 2",
				"Best survival game on the market, with even scarier and larger leviathans than the previous entries!",
				"Unknown Worlds", Genre.SURVIVAL, 300));
		games.add(new Game("The Witcher 4", "Another genre defining RPG experience through the eyes of Geralt of Rivia",
				"CD Projekt Red", Genre.RPG, 150));
		games.add(new Game("Cyberpunk 2078",
				"Somehow it took longer to make than the previous one and it is more broken and buggy than 2077 at launch.",
				"CD Projekt Red", Genre.OPEN_WORLD, 5));
		games.add(new Game("League of LOL",
				"NO, the previous rooster of heroes does not change through the games, so you'll have to start from 0, skins and all",
				"Riot Games", Genre.MOBA, 0));
		games.add(new Game("Roblox 2",
				"This time instead of Lego looking characters, it has biblically accurate angels looking character models",
				"Roblox Company", Genre.SANDBOX, 0));
		games.add(new Game("Garry's Mod 2", "Unity in a video game format", "Valve", Genre.SANDBOX, 15));
		games.add(new Game("Sven Bømwøllen", "If you know, you know", "AK Tronic Software", Genre.CASUAL, 10));
		games.add(
				new Game("Counter Stryke Local Defensive", "Like CSGO but on a smaller scale", "VALVE", Genre.FPS, 30));
		games.add(new Game("Sekiro Shadows Die Twice 2", "Harder than all other Fromsoftware games combined",
				"Fromsoftware", Genre.ACTION_ADVENTURE, 300));
		games.add(new Game("Elden Ring", "Best game ever, mark my words", "Fromsoftware", Genre.OPEN_WORLD, 600));
		games.add(new Game("Mortal Kombat SFW", "Fun for the entire family", "Warner Bros.", Genre.FIGHTING, 60));
		games.add(new Game("Halo Zero", "Instead of just one grapling hook, now you have two of them", "Microsoft",
				Genre.FPS, 70));
		games.add(new Game("Portal 3", "Now, you are Glados, you control the facility", "Valve", Genre.PUZZLE, 333.33));
		games.add(new Game("Left4Dead3", "Step back Back4Blood, the king is back", "Valve", Genre.SURVIVAL, 3333.33));
		games.add(new Game("Hearthstone 2", "Even more card packs in this one", "Blizzard", Genre.CARD_GAME, 20));
		games.add(new Game("Warcraft 4", "The undisputed king of RTS is back", "Blizzard", Genre.RTS, 1000));
		games.add(new Game("God of War Dacia", "Kratos has beef with Zalmoxis in this one", "SONY",
				Genre.ACTION_ADVENTURE, 1000));
		games.add(new Game("Human of Peace", "God of War but backwards", "SONY", Genre.ACTION_ADVENTURE, 20));
		games.add(new Game("Chess", "The newest bestest chess game from yours truly", "Andi", Genre.STRATEGY, 1000));
		games.add(new Game("SlimeRush", "The 2nd bestest and newest of video games from yours truly", "Andi",
				Genre.PLATFORMER, 500));

		for (Game game : games) {
			ApplicationSession.getInstance().getServer().addGame(game);
		}
	}

	private void addUsers() throws RemoteException {
		List<User> users = new ArrayList<>();

		User test = new User("test", "test", 1);
		test.setAmount(1000000);
		users.add(test);
		users.add(new User("Andrei", "parola123", 0));
		users.add(new User("Alex", "123parola", 0));
		users.add(new User("thewok", "xijin", 0));
		users.add(new User("johnxina", "binchilin", 0));
		users.add(new User("2pac", "rip", 0));
		users.add(new User("whatever", "something", 0));
		users.add(new User("username", "password", 0));
		users.add(new User("universe", "cold", 0));
		users.add(new User("parola123", "Andrei", 0));
		users.add(new User("AndreiSova", "parola", 0));
		users.add(new User("Sokrates123", "Herodotus123", 0));
		users.add(new User("Xx_n00b_xX", "noob", 0));
		users.add(new User("#bestguyever", "bts", 0));
		users.add(new User("Ana", "Maria", 0));

		for (User user : users) {
			ApplicationSession.getInstance().getServer().addUser(user);
		}
	}
}