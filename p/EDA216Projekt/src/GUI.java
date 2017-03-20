import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI {
	// Database
	private Database db;

	// Scene
	private Scene scene;

	// "Root"
	private VBox vBox;

	// Tab objects
	public TabCreatePallet tabCreatePallet;
	private TabRegisterPallet tabRegisterPallet;
	private TabSearchPallet tabSearchPallet;
	private TabBlockedPallets tabBlockedPallets;

	// Layout properties
	private final int GAP = 10;
	private final int PADDING = 15;

	// Menu bar
	private MenuBar menuBar;


	public GUI(Stage primaryStage, Database db) throws SQLException {
		this.db = db;
		startDB();
		// "Root"
		vBox = new VBox();

		// Initialize menu bar and tabs
		initializeMenuBar(primaryStage);
		initializeTabs(primaryStage);

		// Scene
		scene = new Scene(vBox, 775, 450); // Arbitrary integers
	}

	private void initializeMenuBar(Stage primaryStage) {
		// MenuBar
		menuBar = new MenuBar();
		vBox.getChildren().add(menuBar);

		// Menu 1
		Menu menu1 = new Menu("File");
		MenuItem menu1Item1 = new MenuItem("Exit");
		menu1Item1.setOnAction(e -> exit());

		menu1.getItems().add(menu1Item1);

		// Add menus to the menu bar
		menuBar.getMenus().add(menu1);

	}
	
	private void exit() {
		db.closeConnection();
		System.exit(0);
	}

	private void initializeTabs(Stage primaryStage) {
		// TabPane
		TabPane tabPane = new TabPane();
		vBox.getChildren().add(tabPane);

		// Tabs
		Tab tab1 = new Tab("New pallet");
		Tab tab2 = new Tab("Produce pallet");
		Tab tab3 = new Tab("Search pallet");
		Tab tab4 = new Tab("See blocked");

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tab4);


		// Tab configurations
		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab4.setClosable(false);

		// Tab objects and tab content
		tabCreatePallet = new TabCreatePallet(GAP, PADDING, db, primaryStage);
		tab1.setContent(tabCreatePallet.hBox);
		
		tabRegisterPallet = new TabRegisterPallet(GAP, PADDING, db);
		tab2.setContent(tabRegisterPallet.hBox);


//		tabRegisterUser = new TabRegisterUser(GAP, PADDING, garage, primaryStage);
//		tab2.setContent(tabRegisterUser.hBox);
//
//		tabSearchUser = new TabSearchUser(GAP, PADDING, primaryStage, garage, tabRegisterUser);
//		tab3.setContent(tabSearchUser.mainLayout);
//		tab3.setOnSelectionChanged(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event arg0) {
//				if (tab3.isSelected()) {
//					tabSearchUser.updateUserTable();
//				}
//			}
//
//		});
//
//		tabSearchBike = new TabSearchBike(GAP, PADDING, tabSearchUser, garage);
//		tab4.setContent(tabSearchBike.mainLayout);
//		tab4.setOnSelectionChanged(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event arg0) {
//				if (tab4.isSelected()) {
//					// Inserts bike data into the table
//					tabSearchBike.addAllBikesToTable();
//
//				}
//			}
//		});

	}

	private void startDB() throws SQLException {
		if (db.openConnection("cookies.db")) {
			System.out.println("Database connected");
			db.foreignKey();
		} else {
			System.out.println("Database not connected");
			System.exit(0);
		}
	}

	public Scene getScene() {
		return scene;
	}
}