package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Game;
import model.Message;
import model.Model;
import model.User;

public interface ServerCalls extends Remote {

	User getUserByUsername(String username) throws RemoteException;

	void registerUser(String username, String password, int accesLevel) throws RemoteException;

	void addUser(User user) throws RemoteException;

	void addGame(Game game) throws RemoteException;

	List<Game> getAllGames() throws RemoteException;

	Game getGameByName(String gameTitle) throws RemoteException;

	void buyGame(User user, Game gmf) throws RemoteException;

	List<Message> getReceivedMessages(User user) throws RemoteException;

	List<Message> getSentMessages(User user) throws RemoteException;

	void sendMessage(Message toBeSendMessage) throws RemoteException;

	List<? extends Model> getAllUsers() throws RemoteException;

	void deleteGameByTitle(String text) throws RemoteException;

	void deleteUserByTitle(String text) throws RemoteException;

	void updateDescription(User user, String desc) throws RemoteException;

	void addMoney(User user, int sum) throws RemoteException;
}
