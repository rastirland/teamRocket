package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class adminPanelController {

//	UserRepository UserRepository = new UserRepository();

	@FXML
	private Button userDisplay;

	@FXML
	private Button backToMain;

	@FXML
	private Button userDelete;

	@FXML
	private Button searchBtn;

	@FXML
	private Button addAdmin;

	@FXML
	TextArea userTextArea;
	

	@FXML
	TextField searchField;

	@FXML
	private TableColumn<String[], String> col1;

	@FXML
	private TableColumn<?, ?> col2;

	@FXML
	TableView<String[]> userTable;
	
	

	@FXML
	public void backToMain(ActionEvent e) throws IOException, NoSuchAlgorithmException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainPanelView.fxml"));
		// Build the scene graph.
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("AdminPanel");
		stage.setScene(scene);
		stage.show();
	}

	// populating the table
	@FXML
	void getUsers(ActionEvent e) throws Exception {
		
		  System.out.println("Button clicked."); // Log message to indicate button click

		    // Clear existing columns and items from the TableView
		    userTable.getColumns().clear();
		    userTable.getItems().clear();

		    // Create ObservableList to hold the data
		    ObservableList<String[]> data = FXCollections.observableArrayList();

		    // Specify the file path of the CSV file
		    String csvFile = "C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\userData.csv";

		    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		            String[] row = line.split(","); // assuming comma-separated values
		            data.add(row);
		        }
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }

		    // Get column names from user input (modify this as needed)
		    String[] columnNames = {"UserName", "Password", "Role", "ID"};

		    // Create TableColumns and add them to the TableView
		    for (int i = 0; i < columnNames.length; i++) {
		        final int colIndex = i;
		        TableColumn<String[], String> col = new TableColumn<>(columnNames[i]);
		        col.setCellValueFactory(param -> {
		            String[] row = param.getValue();
		            if (row != null && colIndex < row.length) {
		                return new ReadOnlyStringWrapper(row[colIndex]);
		            } else {
		                return new ReadOnlyStringWrapper("");
		            }
		        });
		        col.setCellFactory(TextFieldTableCell.forTableColumn());
		        col.setEditable(true); // Set column editable
		        col.setOnEditCommit(event -> {
		            // Show an alert dialog to confirm save changes
		            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		            alert.setTitle("Save Changes");
		            alert.setHeaderText("Save Changes");
		            alert.setContentText("Are you sure you want to save changes?");
		            Optional<ButtonType> result = alert.showAndWait();

		            if (result.isPresent() && result.get() == ButtonType.OK) {
		                String[] row = event.getRowValue();
		                row[colIndex] = event.getNewValue();
		                saveDataToCSV(data, csvFile); // Save changes to CSV file
		            } else {
		                // Cancel edit if user cancels the alert
		                userTable.getItems().set(event.getTablePosition().getRow(), event.getRowValue());
		            }
		        });
		        userTable.getColumns().add(col);
		    }

		    // Set the populated data to the TableView
		    userTable.setItems(data);

		    // Enable editing for the table
		    userTable.setEditable(true);

		    // Enable row selection in TableView (optional)
		    userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}

		private void saveDataToCSV(ObservableList<String[]> data, String csvFile) {
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
		        for (String[] row : data) {
		            bw.write(String.join(",", row));
		            bw.newLine();
		        }
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }

	}

	public void searchBar(ActionEvent event) {
		String searchTerm = searchField.getText().toLowerCase();
		ObservableList<String[]> filteredData = FXCollections.observableArrayList();
		for (String[] rowData : userTable.getItems()) {
			if (rowData[0].toLowerCase().contains(searchTerm) || rowData[1].toLowerCase().contains(searchTerm)) {
				filteredData.add(rowData);
			}
		}
		userTable.setItems(filteredData);
	}

	public static ObservableList<String[]> getUserAccounts(String filename) {

		ObservableList<String[]> data = FXCollections.observableArrayList();

		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

	@FXML
	void UserDelete(ActionEvent e) throws Exception {
		// Get the selected row
		String[] selectedRow = userTable.getSelectionModel().getSelectedItem();

		if (selectedRow != null) {
			// Remove the selected row from the table
			userTable.getItems().remove(selectedRow);

			// Ask the user if they want to save the changes to the CSV file
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Save Changes");
			alert.setHeaderText("Do you want to save the changes to the CSV file?");
			alert.setContentText("Click OK to save or Cancel to discard the changes.");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				// Save the changes to the CSV file
				List<String[]> updatedData = new ArrayList<>(userTable.getItems());
				try (FileWriter writer = new FileWriter("userData.csv")) {
					for (String[] row : updatedData) {
						writer.write(String.join(",", row));
						writer.write("\n");
					}
				} catch (IOException ex) {
					System.out.println("Error writing to file: " + ex.getMessage());
				}
			}
		}
	}

	@FXML
	void AddAdmin(ActionEvent e) throws Exception {

		Parent parent = FXMLLoader.load(getClass().getResource("AddAdminView.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Add Admin");
		stage.setScene(scene);
		stage.show();

	}
	
	

}
