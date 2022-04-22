package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ApplicationUser")
@NamedQueries(value = { @NamedQuery(name = User.USER_BY_USERNAME, query = "from User where userName = :username"), //
		@NamedQuery(name = User.GET_ALL_USERS, query = "from User") //
})
public class User implements Model, Serializable, Columns {

	public static final String USER_BY_USERNAME = "User.by.username";

	public static final String GET_ALL_USERS = "All.users";

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;

	private String description;

	@Column(nullable = false)
	private double amount;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Game", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "game_id") })
	private List<Game> ownedGames = new ArrayList<>();

	private int accesLevel;

	@OneToMany(mappedBy = "sender")
	private List<Message> sentMessages = new ArrayList<>();

	@OneToMany(mappedBy = "receiver")
	private List<Message> receivedMessages = new ArrayList<>();

	public User() {
	}

	public User(String userName, String password, int accesLevel) {
		this.userName = userName;
		this.password = password;
		this.accesLevel = accesLevel;
		this.description = "";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getAccesLevel() {
		return accesLevel;
	}

	@Override
	public String[] getData() {
		return null;
	}

	public List<Game> getOwnedGames() {
		return ownedGames;
	}

	public void setOwnedGames(List<Game> ownedGames) {
		this.ownedGames = ownedGames;
	}

	public long getId() {
		return id;
	}

	public void addGame(Game gm) {
		this.ownedGames.add(gm);
	}

	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}

	@Override
	public String[] getColumns() {
		return new String[] { "Username", "Password" };
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
