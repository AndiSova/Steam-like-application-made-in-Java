package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Game;
import model.Message;
import model.User;
import server.persistence.WithSessionAndTransaction;

public class ServerCallsImpl extends UnicastRemoteObject implements ServerCalls {

	protected ServerCallsImpl() throws RemoteException {
		super();
	}

	@Override
	public User getUserByUsername(String username) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<User> searchQuery = session.getNamedQuery(User.USER_BY_USERNAME);
				searchQuery.setParameter("username", username);

				User user = searchQuery.uniqueResult();
				setReturnValue(user);
			}
		};
		return action.run();
	}

	@Override
	public void registerUser(String username, String password, int accesLevel) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User user = new User(username, password, accesLevel);
				session.save(user);
			}
		};
		action.run();
	}

	@Override
	public void addUser(User user) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				session.save(user);
			}
		};
		action.run();
	}

	@Override
	public void addGame(Game game) throws RemoteException {
		WithSessionAndTransaction<Game> action = new WithSessionAndTransaction<Game>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				session.save(game);
			}
		};
		action.run();
	}

	@Override
	public List<Game> getAllGames() throws RemoteException {
		WithSessionAndTransaction<List<Game>> action = new WithSessionAndTransaction<List<Game>>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<Game> gameQuery = session.getNamedQuery(Game.GET_ALL_GAMES);
				List<Game> games = gameQuery.list();

				setReturnValue(games);
			}
		};
		return action.run();
	}

	@Override
	public Game getGameByName(String gameTitle) throws RemoteException {
		WithSessionAndTransaction<Game> action = new WithSessionAndTransaction<Game>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<Game> gameQuery = session.getNamedQuery(Game.GET_GAME_BY_TITLE);
				gameQuery.setParameter("title", gameTitle);
				Game game = gameQuery.uniqueResult();

				setReturnValue(game);
			}
		};
		return action.run();
	}

	@Override
	public void buyGame(User user, Game gmf) throws RemoteException {
		WithSessionAndTransaction<Game> action = new WithSessionAndTransaction<Game>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User usr = session.load(User.class, user.getId());
				Game gm = session.load(Game.class, gmf.getId());
				usr.setAmount(usr.getAmount() - gm.getPrice());

				usr.addGame(gm);
				gm.addUser(usr);

				session.save(gm);
				session.save(usr);
			}
		};
		action.run();
	}

	@Override
	public List<Message> getReceivedMessages(User user) throws RemoteException {
		WithSessionAndTransaction<List<Message>> action = new WithSessionAndTransaction<List<Message>>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<Message> messagesQuery = session.getNamedQuery(Message.GET_RECEIVED_MESSAGES);
				messagesQuery.setParameter("receiver", user);

				List<Message> messages = messagesQuery.list();
				setReturnValue(messages);
			}
		};
		return action.run();
	}

	@Override
	public List<Message> getSentMessages(User user) throws RemoteException {
		WithSessionAndTransaction<List<Message>> action = new WithSessionAndTransaction<List<Message>>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<Message> messagesQuery = session.getNamedQuery(Message.GET_SENT_MESSAGES);
				messagesQuery.setParameter("sender", user);

				List<Message> messages = messagesQuery.list();
				setReturnValue(messages);
			}
		};
		return action.run();
	}

	@Override
	public void sendMessage(Message toBeSendMessage) throws RemoteException {
		WithSessionAndTransaction<Message> action = new WithSessionAndTransaction<Message>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User sender = session.load(User.class, toBeSendMessage.getSender().getId());
				User receiver = session.load(User.class, toBeSendMessage.getReceiver().getId());

				toBeSendMessage.setSender(sender);
				toBeSendMessage.setReceiver(receiver);

				session.save(toBeSendMessage);
			}
		};
		action.run();
	}

	@Override
	public List<User> getAllUsers() throws RemoteException {
		WithSessionAndTransaction<List<User>> action = new WithSessionAndTransaction<List<User>>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Query<User> gameQuery = session.getNamedQuery(User.GET_ALL_USERS);
				List<User> games = gameQuery.list();

				setReturnValue(games);
			}
		};
		return action.run();
	}

	@Override
	public void deleteGameByTitle(String text) throws RemoteException {
		WithSessionAndTransaction<Game> action = new WithSessionAndTransaction<Game>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				Game game = (Game) session.getNamedQuery(Game.GET_GAME_BY_TITLE).setParameter("title", text)
						.uniqueResult();
				session.delete(game);
			}
		};
		action.run();
	}

	@Override
	public void deleteUserByTitle(String text) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User game = (User) session.getNamedQuery(User.USER_BY_USERNAME).setParameter("username", text)
						.uniqueResult();
				session.delete(game);
			}
		};
		action.run();
	}

	@Override
	public void updateDescription(User user, String desc) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User userD = session.load(User.class, user.getId());
				userD.setDescription(desc);
				session.update(userD);
			}
		};
		action.run();
	}

	@Override
	public void addMoney(User user, int sum) throws RemoteException {
		WithSessionAndTransaction<User> action = new WithSessionAndTransaction<User>() {

			@Override
			protected void executeBusinessLogic(Session session) {
				User userD = session.load(User.class, user.getId());
				userD.setAmount(user.getAmount() + sum);
				session.update(userD);
			}
		};
		action.run();
	}
}