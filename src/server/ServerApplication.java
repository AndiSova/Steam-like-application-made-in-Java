package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import server.persistence.SessionFactoryProvider;

public class ServerApplication {

	private ServerApplication() throws RemoteException {
	}

	public void startServer() {
		try {
			System.err.println("Starting server...");

			SessionFactoryProvider.getSessionFactory();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("//localhost/MyServer", new ServerCallsImpl());

			System.err.println("Server ready!");
		} catch (Exception e) {
			System.out.println("Server exception: ");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException {
		ServerApplication server = new ServerApplication();
		server.startServer();
	}
}
