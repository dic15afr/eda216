import java.sql.SQLException;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
/**
 * 
 * @author cek11kmi
 *This class is NOT used
 */
public class TabRegisterPallet {

	// Layout holder
	public HBox hBox;

	// Main components
	private TextField palletBarcodeTF;
	private Label invalidInputMessage;
	private Button registerPallet;
	private Label registrationMessage;

	// External objects
	private Database db;

	public TabRegisterPallet(int gap, int padding, Database db) {
		this.db = db;
		initializeComponents(gap, padding);

	}

	private void initializeComponents(int gap, int padding) {
		// Horizontal box
		hBox = new HBox();
		hBox.setSpacing(gap);
		hBox.setPadding(new Insets(padding, padding, padding, padding));

		// Grid pane (Is put at the top in "vBox1", contains labels and text
		// fields)
		GridPane grid = new GridPane();
		grid.setHgap(gap);
		grid.setVgap(gap);

		// Grid pane (grid) components
		Label name = new Label("Pallet barcode");

		palletBarcodeTF = new TextField();

		palletBarcodeTF.setPromptText("E.g. 1");

		// Add grid pane (grid) components
		grid.add(name, 0, 0);
		grid.add(palletBarcodeTF, 0, 1);

		// Vertical box (Is put in the left in hBox, contains "grid" and
		// "invalidInputMessage"-label)
		VBox vBox1 = new VBox();
		vBox1.setSpacing(gap);

		// Vertical box (vBox1) components
		invalidInputMessage = new Label("");
		invalidInputMessage.setTextFill(Color.RED);
		invalidInputMessage.setWrapText(false);

		registrationMessage = new Label("");
		registrationMessage.setTextFill(Color.GREEN);
		registrationMessage.setWrapText(false);
		grid.add(invalidInputMessage, 0, 2);
		grid.add(registrationMessage, 0, 3);





		// Create pallet
		registerPallet = new Button("Create pallet");
		registerPallet.setOnAction(e -> createPallet());

		grid.add(registerPallet, 1, 1);
		
		vBox1.getChildren().add(grid);




		// Add major component holders (vBox1 and vBox2) to the horizontal box
		// (hBox)
		hBox.getChildren().add(vBox1);

	}

	private void createPallet() {
		// Restore any previous error marks (e.g. text field marked yellow)
		restoreInvalidInputs();

		// Get Strings from text fields
		String barCode = palletBarcodeTF.getText();

		// Check if all text fields contains text of acceptable length
		if (barCode.length() > 0 && barCode.length() < 101) {
			try {
				if (db.setProductionDate(barCode)) {
					restoreInvalidInputs();
					validBarcodeInput();
					clearTextField();
				} else {
					restoreInvalidInputs();
					invalidBarcodeInput();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			restoreInvalidInputs();
			invalidBarcodeInput();
		}
	}

	private void invalidBarcodeInput() {
		// Mark as YELLOW
		palletBarcodeTF.setStyle("-fx-background-color: #ffff0052");
		addInvalidInputMessage(
				palletBarcodeTF.getText() + " is not a valid cookie barcode or the pallet has already been registered");
	}

	public void restoreInvalidInputs() {
		// Restore text field colors
		palletBarcodeTF.setStyle("");

		// Set the error message label text to an "empty" string
		invalidInputMessage.setText("");
	}

	private void addInvalidInputMessage(String message) {
		if (invalidInputMessage.getText().length() == 0) {
			invalidInputMessage.setText(invalidInputMessage.getText() + message);
		} else {
			invalidInputMessage.setText(invalidInputMessage.getText() + ", " + message);
		}
	}

	private void validBarcodeInput() {
		// Mark as GREEN
		addValidInputMessage(palletBarcodeTF.getText() + " was successfully registered");
	}

	private void addValidInputMessage(String message) {
		if (registrationMessage.getText().length() == 0) {
			registrationMessage.setText(registrationMessage.getText() + message);
		} else {
			registrationMessage.setText(registrationMessage.getText() + ", " + message);
		}
	}

	private void clearTextField() {
		palletBarcodeTF.setText("");
	}

}
