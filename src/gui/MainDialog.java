package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import customs.CustomTable;
import helper.Util;
import model.Game;
import model.User;
import parents.Dialog;
import server.ApplicationSession;
import states.KeyPages;
import states.LibraryState;
import states.ObjectEditor;
import states.ProfileState;
import states.StoreState;

public class MainDialog extends Dialog {

	private ObjectEditor oe = new ObjectEditor();
	private User user;
	private Label username;
	private Label amount;
	private List games;
	private java.util.List<Game> gamesList;
	private Game gme;
	private String gamePATH;
	private int lastButton = Integer
			.parseInt(ApplicationStart.getInstance().getGameProperties().getProperty("lastButton"));

	private CustomTable table;

	public MainDialog(String username) {
		super("Steam CopyCat", true);
		try {
			this.user = ApplicationSession.getInstance().getServer().getUserByUsername(username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createDialog() {
		int insetX = 4, insetY = 4;
		FormLayout fl = new FormLayout();
		fl.marginWidth = insetX;
		fl.marginHeight = insetY;
		this.shell.setLayout(fl);

		Composite buttonComposite = new Composite(shell, SWT.NONE);
		buttonComposite.setLayout(new FillLayout());

		Composite butoane = new Composite(buttonComposite, SWT.NONE);
		butoane.setLayout(new RowLayout());
		Button storeButton = new Button(butoane, SWT.NONE);
		storeButton.setText("STORE");
		Button libraryButton = new Button(butoane, SWT.NONE);
		libraryButton.setText("LIBRARY");
		Button profileButton = new Button(butoane, SWT.NONE);
		profileButton.setText("PROFILE");
		Button messagesButton = new Button(butoane, SWT.NONE);
		messagesButton.setText("MESSAGES");

		Composite userInfo = new Composite(buttonComposite, SWT.RIGHT_TO_LEFT);
		userInfo.setLayout(new RowLayout());
		username = new Label(userInfo, SWT.NONE);
		username.setText(user.getUserName());
		new Label(userInfo, SWT.NONE).setText("    ");
		amount = new Label(userInfo, SWT.NONE);
		amount.setText(user.getAmount() + "");
		FontData[] dont = username.getFont().getFontData();
		dont[0].setHeight(12);
		username.setFont(new Font(username.getDisplay(), dont[0]));
		amount.setFont(new Font(username.getDisplay(), dont[0]));

		FormData fdCompo = new FormData(buttonComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, SWT.DEFAULT);
		fdCompo.left = new FormAttachment(0, 0);
		fdCompo.right = new FormAttachment(100, 0);
		buttonComposite.setLayoutData(fdCompo);

		if (user.getAccesLevel() == 1) {
			Button adminButton = new Button(butoane, SWT.NONE);
			adminButton.setText("ADMIN");
			adminButton.addListener(SWT.MouseUp, e -> {
				new AdminDialog().run();
			});
		}

		Composite fulLComposite = new Composite(shell, SWT.NONE);
		FormData fullFD = new FormData();
		fullFD.top = new FormAttachment(buttonComposite, 0);
		fullFD.left = new FormAttachment(0, 0);
		fullFD.right = new FormAttachment(100, 0);
		fullFD.bottom = new FormAttachment(100, 0);
		fulLComposite.setLayoutData(fullFD);
		fulLComposite.setLayout(new FillLayout());
		Composite viewComposite = new Composite(fulLComposite, SWT.NONE);
		viewComposite.setLayout(new StackLayout());
		FormData fd = new FormData();
		fd.top = new FormAttachment(buttonComposite, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, 0);
		viewComposite.setLayoutData(fd);
		KeyPages viewPages = new KeyPages("ViewComposite", viewComposite, oe);
		setUpViewPages(viewComposite, viewPages);

		new LibraryState().setup(oe);

		libraryButton.addListener(SWT.MouseUp, e -> {
			new LibraryState().setup(oe);
			games.removeAll();
			if (gamesList != null) {
				gamesList.clear();
			}
			table.fill(user.getOwnedGames());
		});

		storeButton.addListener(SWT.MouseUp, e -> {
			new StoreState().setup(oe);
			try {
				gamesList = ApplicationSession.getInstance().getServer().getAllGames();
				Util.fillList(games, gamesList);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		profileButton.addListener(SWT.MouseUp, e -> {
			new ProfileState().setup(oe);
		});

		messagesButton.addListener(SWT.MouseUp, e -> {
			new MessageDialog(user).run();
		});
	}

	private void setUpViewPages(Composite viewComposite, KeyPages viewPages) {
		Composite library = new Composite(viewComposite, SWT.NONE);
		library.setLayout(new FormLayout());
		table = new CustomTable(library);
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(25, 0);
		table.setLayoutData(fd);
		table.setColumns(new String[] { "ceva", "altceva" });
		table.fill(user.getOwnedGames());
		table.setSize();

		Composite descComposite = new Composite(library, SWT.NONE);
		descComposite.setLayout(viewComposite.getShell().getLayout());

		FormData descFormData = new FormData();
		descFormData.top = new FormAttachment(0, 0);
		descFormData.left = new FormAttachment(table.getTable(), 0);
		descFormData.right = new FormAttachment(100, 0);
		descFormData.bottom = new FormAttachment(100, 0);
		descComposite.setLayoutData(descFormData);

		Composite gameInfo = new Composite(descComposite, SWT.BORDER);
		gameInfo.setLayoutData(getFormData(0, 50, 0, 100));
		gameInfo.setLayout(new FillLayout(SWT.VERTICAL));
		Label gTitle = new Label(gameInfo, SWT.NONE);
		Label gPublisher = new Label(gameInfo, SWT.NONE);
		Label gGenre = new Label(gameInfo, SWT.NONE);
		Composite gameInfoButtonComposite = new Composite(gameInfo, SWT.NONE);
		gameInfoButtonComposite.setLayout(new FillLayout());
		Button installButton = new Button(gameInfoButtonComposite, SWT.NONE);
		installButton.setText("Install Game");
		installButton.setAlignment(SWT.CENTER);
		installButton.setVisible(false);
		Button playButton = new Button(gameInfoButtonComposite, SWT.NONE);
		playButton.setText("Play Game");
		playButton.setAlignment(SWT.CENTER);
		playButton.setVisible(false);

		Composite descriptionComposite = new Composite(descComposite, SWT.BORDER);
		descriptionComposite.setLayoutData(getFormData(50, 100, 0, 100));
		descriptionComposite.setLayout(new FillLayout());
		Label descriptionLabel = new Label(descriptionComposite, SWT.WRAP);
		descriptionLabel.setText("");
		descriptionLabel.setAlignment(SWT.CENTER);

		table.getTable().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String gameTitle = table.getTable().getSelection()[0].getText();
				gamePATH = ApplicationStart.getInstance().getProperties()
						.getProperty(gameTitle.replace(":", ".").replace(" ", ".").replace("'", ".").replace("_", "."));
				try {
					Game game = ApplicationSession.getInstance().getServer().getGameByName(gameTitle);
					gTitle.setText("Tile: " + game.getTitle());
					gPublisher.setText("Publisher: " + game.getPublisher());
					gGenre.setText("Genre: " + game.getGameGenres().getName());
					descriptionLabel.setText(game.getDescription());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}

				if (gamePATH == null) {
					installButton.setVisible(true);
					playButton.setVisible(true);
					playButton.setEnabled(false);
					installButton.addListener(SWT.MouseUp, e -> {
						try {
							if (!gameTitle.equals("Chess") && !gameTitle.equals("SlimeRush")) {
								ApplicationStart.getInstance().getProperties()
										.put(gameTitle
												.replace(":", ".").replace(" ", ".").replace("'",
														".")
												.replace("_", "."),
												Util.openConfigFile("/SteamCopyCat/games/" + gameTitle.replace(":", "")
														.replace(" ", "").replace("'", "").replace("_", "") + ".text")
														.getAbsolutePath());
								ApplicationStart.getInstance().getProperties().store(
										new FileWriter(Util.openConfigFile("/SteamCopyCat/config/gameList.config")),
										"Added game: " + gameTitle);
							} else {
								System.out.println("Here");
								File file = null;
								if (gameTitle.equals("Chess")) {
									file = new File("./Chess-Game/Chess.exe");
								} else {
									file = new File("./SlimeRush/Atestat.exe");
								}
								ApplicationStart.getInstance().getProperties().put(gameTitle, file.getAbsolutePath());
								ApplicationStart.getInstance().getProperties().store(
										new FileWriter(Util.openConfigFile("/SteamCopyCat/config/gameList.config")),
										"Added game: " + gameTitle);
							}
							installButton.setVisible(false);
							playButton.setEnabled(true);
							gamePATH = ApplicationStart.getInstance().getProperties().getProperty(
									gameTitle.replace(":", ".").replace(" ", ".").replace("'", ".").replace("_", "."));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
				} else {
					installButton.setVisible(false);
					playButton.setVisible(true);
					playButton.setEnabled(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		playButton.addListener(SWT.MouseUp, e -> {
			if (!Desktop.isDesktopSupported()) {
				System.out.println("not supported");
			} else {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File(gamePATH));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		viewPages.addPage("LibraryView", library);

		///

		Composite store = new Composite(viewComposite, SWT.NONE);
		store.setLayout(viewComposite.getShell().getLayout());
		Composite gameList = new Composite(store, SWT.BORDER);
		gameList.setLayout(new FormLayout());
		gameList.setLayoutData(fd);
		Text storeSearchBar = new Text(gameList, SWT.BORDER);
		FormData sFD = new FormData();
		sFD.top = new FormAttachment(0, 0);
		sFD.left = new FormAttachment(0, 0);
		sFD.right = new FormAttachment(100, 0);
		storeSearchBar.setLayoutData(sFD);
		games = new List(gameList, SWT.V_SCROLL | SWT.BORDER);

		FormData gameFormData = new FormData();
		gameFormData.top = new FormAttachment(storeSearchBar, 4);
		gameFormData.left = new FormAttachment(0, 0);
		gameFormData.right = new FormAttachment(100, 0);
		gameFormData.bottom = new FormAttachment(100, 0);
		games.setLayoutData(gameFormData);

		storeSearchBar.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (storeSearchBar.getText().isBlank()) {
					Util.fillList(games, gamesList);
				} else {
					Util.fillList(games,
							gamesList.stream().filter(
									g -> g.getTitle().toLowerCase().startsWith(storeSearchBar.getText().toLowerCase()))
									.collect(Collectors.toList()));
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		Composite storeInfoComposite = new Composite(store, SWT.BORDER);
		storeInfoComposite.setLayout(new FormLayout());
		FormData storeInfoData = new FormData();
		storeInfoData.top = new FormAttachment(0, 0);
		storeInfoData.bottom = new FormAttachment(100, 0);
		storeInfoData.left = new FormAttachment(gameList, 4);
		storeInfoData.right = new FormAttachment(100, 0);
		storeInfoComposite.setLayoutData(storeInfoData);

		Composite buyThingy = new Composite(storeInfoComposite, SWT.BORDER);
		buyThingy.setLayout(new FormLayout());
		buyThingy.setLayoutData(Util.getFormData(0, 50, 0, 100));
		Composite gameDetails = new Composite(buyThingy, SWT.NONE);
		gameDetails.setLayoutData(Util.getFormData(0, 100, 0, 50));
		gameDetails.setLayout(new FillLayout(SWT.VERTICAL));
		Label title = new Label(gameDetails, SWT.NONE);
		Label publisher = new Label(gameDetails, SWT.NONE);
		Label price = new Label(gameDetails, SWT.NONE);
		Label genre = new Label(gameDetails, SWT.NONE);
		Composite buyButtonComposite = new Composite(buyThingy, SWT.NONE);
		buyButtonComposite.setLayoutData(Util.getFormData(0, 100, 50, 100));
		buyButtonComposite.setLayout(new FillLayout(SWT.VERTICAL));
		Button buyButton = new Button(buyButtonComposite, SWT.NONE);
		buyButton.setVisible(false);
		buyButton.setText(enums.Text.BUY_TEXT.getText());
		buyButton.setAlignment(SWT.CENTER);
		Label buyError = new Label(buyButtonComposite, SWT.NONE);
		buyError.setAlignment(SWT.CENTER);

		Composite descriptionComposite1 = new Composite(storeInfoComposite, SWT.BORDER);
		descriptionComposite1.setLayout(new FillLayout());
		descriptionComposite1.setLayoutData(Util.getFormData(50, 100, 0, 100));
		Label description = new Label(descriptionComposite1, SWT.WRAP);
		description.setAlignment(SWT.CENTER);
		games.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				buyButton.setVisible(true);
				buyButton.setEnabled(true);
				buyButton.setText(enums.Text.BUY_TEXT.getText());
				String gameTitle = games.getSelection()[0];
				try {
					gme = ApplicationSession.getInstance().getServer().getGameByName(gameTitle);
					title.setText("Title: " + gme.getTitle());
					publisher.setText("Publisher: " + gme.getPublisher());
					price.setText("Price: " + gme.getPrice());
					genre.setText("Genre: " + gme.getGameGenres().getName());
					description.setText(gme.getDescription());
					if (user.getOwnedGames().stream().filter(f -> f.getTitle().equals(gme.getTitle()))
							.collect(Collectors.toList()).size() != 0) {
						buyButton.setEnabled(false);
						buyButton.setText("You already own this game!");
						buyError.setText("You already own this game!");
					} else if (user.getAmount() < gme.getPrice()) {
						buyButton.setEnabled(false);
						buyError.setText("You don't have enough money!");
					} else {
						buyButton.setEnabled(true);
						buyError.setText("You have enough funds to buy this game!");
					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		buyButton.addListener(SWT.MouseUp, e -> {
			try {
				ApplicationSession.getInstance().getServer().buyGame(user, gme);
				user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
				amount.setText(user.getAmount() + "");
				buyButton.setEnabled(false);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		viewPages.addPage("StoreView", store);

		//

		Composite profile = new Composite(viewComposite, SWT.NONE);
		profile.setLayout(new FormLayout());

		FormData profileData = new FormData();
		profileData.top = new FormAttachment(0, 0);
		profileData.bottom = new FormAttachment(100, 0);
		profileData.left = new FormAttachment(0, 0);
		profileData.right = new FormAttachment(50, 0);
		Composite profileDetails = new Composite(profile, SWT.NONE);
		profileDetails.setLayoutData(profileData);
		profileDetails.setLayout(new FormLayout());

		FormData nameData = new FormData();
		nameData.top = new FormAttachment(0, 0);
		nameData.left = new FormAttachment(0, 0);
		nameData.right = new FormAttachment(100, 0);
		Composite nameAndLevel = new Composite(profileDetails, SWT.NONE);
		nameAndLevel.setLayoutData(nameData);
		nameAndLevel.setLayout(new FillLayout());
		Label usernameprofile = new Label(nameAndLevel, SWT.NONE);
		usernameprofile.setText(user.getUserName());
		Label levelProfile = new Label(nameAndLevel, SWT.NONE);
		levelProfile.setText("Level " + user.getOwnedGames().size() / 5);

		FontData[] nameFD = usernameprofile.getFont().getFontData();
		nameFD[0].setHeight(20);
		usernameprofile.setFont(new Font(ApplicationStart.getInstance().getDisplay(), nameFD[0]));
		levelProfile.setFont(new Font(ApplicationStart.getInstance().getDisplay(), nameFD[0]));

		Text userDescription = new Text(profileDetails, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		FormData userDescData = new FormData();
		userDescData.top = new FormAttachment(nameAndLevel, 0);
		userDescData.left = new FormAttachment(0, 0);
		userDescData.right = new FormAttachment(100, 0);
		userDescData.bottom = new FormAttachment(50, 0);
		userDescription.setLayoutData(userDescData);
		userDescription.setText(user.getDescription() == null ? "" : user.getDescription());
		Button updateDescription = new Button(profileDetails, SWT.NONE);
		FormData descButtonData = new FormData();
		descButtonData.top = new FormAttachment(userDescription, 0);
		descButtonData.left = new FormAttachment(0, 0);
		descButtonData.right = new FormAttachment(100, 0);
		updateDescription.setText("Update your description!");
		updateDescription.setLayoutData(descButtonData);

		Label ownedGamesNumber = new Label(profileDetails, SWT.NONE);
		ownedGamesNumber.setText("You own " + user.getOwnedGames().size() + " games!");
		FormData gamesfd = new FormData();
		gamesfd.top = new FormAttachment(updateDescription, 0);
		gamesfd.left = new FormAttachment(0, 0);
		gamesfd.right = new FormAttachment(100, 0);
		ownedGamesNumber.setFont(new Font(ApplicationStart.getInstance().getDisplay(), nameFD[0]));
		ownedGamesNumber.setLayoutData(gamesfd);

		updateDescription.addListener(SWT.MouseUp, e -> {
			String desc = userDescription.getText();
			try {
				ApplicationSession.getInstance().getServer().updateDescription(user, desc);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		//

		FormData capitalistData = new FormData();
		capitalistData.top = new FormAttachment(0, 0);
		capitalistData.bottom = new FormAttachment(100, 0);
		capitalistData.left = new FormAttachment(50, 0);
		capitalistData.right = new FormAttachment(100, 0);
		Composite pseudoAdventruCapitalis = new Composite(profile, SWT.NONE);
		pseudoAdventruCapitalis.setLayoutData(capitalistData);
		pseudoAdventruCapitalis.setLayout(new FillLayout(SWT.VERTICAL));

		Label infoLabel = new Label(pseudoAdventruCapitalis, SWT.BORDER | SWT.WRAP);
		infoLabel.setText(
				"Here you can increase your money count, by pressing the buttons on the first column. To increase the amount of money you get "
						+ "you will have to buy boosters, "
						+ "represented by the following buttons which increase the amount gained by 5.");
		java.util.List<Button> capButtons = new ArrayList<>();
		int noButtons = 6;
		for (int i = 1; i <= 6; i++) {
			Composite buttonCompo = new Composite(pseudoAdventruCapitalis, SWT.NONE);
			buttonCompo.setLayout(new FillLayout());
			Button capButton = new Button(buttonCompo, SWT.NONE);
			capButton.setText("" + i * 5);

			Button unlockButton = new Button(buttonCompo, SWT.NONE);
			capButtons.add(unlockButton);
			if (i == 1) {
				unlockButton.setText("0");
			} else {
				unlockButton.setText("" + i * 20);
			}

			if (user.getAmount() >= Integer.parseInt(unlockButton.getText())) {
				unlockButton.setEnabled(true);
			} else {
				unlockButton.setEnabled(false);
			}

			if (i <= lastButton) {
				capButton.setEnabled(true);
				unlockButton.setVisible(false);
			} else {
				capButton.setEnabled(false);
			}

			final int j = i;
			unlockButton.addListener(SWT.MouseUp, e -> {
				int sum = Integer.parseInt(unlockButton.getText());
				capButton.setEnabled(true);
				ApplicationStart.getInstance().getGameProperties().put("lastButton", j + "");
				try {
					ApplicationSession.getInstance().getServer().addMoney(user, -sum);
					user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
					amount.setText(user.getAmount() + "");
					ApplicationStart.getInstance().getGameProperties().store(
							new FileWriter(Util.openConfigFile("/SteamCopyCat/config/profileGame.config")),
							"Unlocked button #" + j);
					unlockButton.setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			capButton.addListener(SWT.MouseUp, e -> {
				int sum = Integer.parseInt(capButton.getText());
				try {
					ApplicationSession.getInstance().getServer().addMoney(user, sum);
					user = ApplicationSession.getInstance().getServer().getUserByUsername(user.getUserName());
					amount.setText(user.getAmount() + "");
					amount.pack();
					capButtons.stream().forEach(cb -> {
						if (Integer.parseInt(cb.getText()) <= user.getAmount()) {
							cb.setEnabled(true);
						}
					});
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			});
		}

		viewPages.addPage("ProfileView", profile);
	}

	private FormData getFormData(int top, int bottom, int left, int right) {
		FormData data = new FormData();

		data.top = new FormAttachment(top, 0);
		data.bottom = new FormAttachment(bottom, 0);
		data.left = new FormAttachment(left, 0);
		data.right = new FormAttachment(right, 0);

		return data;
	}

	@Override
	public void shellSetSize() {
		this.shell.setMinimumSize(1085, 655);
		this.shell.setSize(1085, 655);
	}
}
