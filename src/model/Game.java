package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;

@Entity
@NamedQueries(value = { @NamedQuery(name = Game.GET_ALL_GAMES, query = "from Game"), //
		@NamedQuery(name = Game.GET_GAME_BY_TITLE, query = "from Game where title = :title") })
public class Game implements Model, Serializable, Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7702806188723896962L;

	public static final String GET_ALL_GAMES = "Games.all";

	public static final String GET_GAME_BY_TITLE = "Game.by.title";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Genre getGameGenres() {
		return genre;
	}

	public void setGameGenres(Genre genre) {
		this.genre = genre;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	@Lob
	private String description;

	@Column(nullable = false)
	private String publisher;

	@Column(nullable = false)
	private double price;

	@Enumerated(EnumType.STRING)
	private Genre genre;

	@ManyToMany(mappedBy = "ownedGames", fetch = FetchType.EAGER)
	private List<User> players = new ArrayList<>();

	public Game() {
	}

	public Game(String title, String description, String publisher, Genre genre, double price) {
		this.title = title;
		this.description = description;
		this.publisher = publisher;
		this.price = price;
		this.genre = genre;
	}

	@Override
	public String[] getData() {
		return new String[] { title };
	}

	public long getId() {
		return id;
	}

	public void addUser(User usr) {
		this.players.add(usr);
	}

	@Override
	public String[] getColumns() {
		return new String[] { "Title", "Description" };
	}

}
