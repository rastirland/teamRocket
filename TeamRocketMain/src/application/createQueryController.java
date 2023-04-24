package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class createQueryController {
		@FXML
		private Button btn_viewQuery;
		
		
	    @FXML
		private Button searchBtn;
	    
	    @FXML
		private Button btn_sendQuery;
	    
	    @FXML
		TextField searchField;
		
	    @FXML
		TableView<String[]> userTable;
		
		

		public void searchBar(ActionEvent event) {
		    String searchTerm = searchField.getText().toLowerCase();
		    ObservableList<String[]> filteredData = FXCollections.observableArrayList();
		    for (String[] rowData : userTable.getItems()) {
		        boolean matchFound = false;
		        for (String value : rowData) {
		            if (value.toLowerCase().contains(searchTerm)) {
		                matchFound = true;
		                break;
		            }
		        }
		        if (matchFound) {
		            filteredData.add(rowData);
		        }
		    }
		    userTable.setItems(filteredData);
		}
		
		
		@FXML
		void getQueries(ActionEvent e) throws Exception {
		    System.out.println("Button clicked."); // Log message to indicate button click

		    // Clear existing columns and items from the TableView
		    userTable.getColumns().clear();
		    userTable.getItems().clear();

		    // Create ObservableList to hold the data
		    ObservableList<String[]> data = FXCollections.observableArrayList();

		    // Specify the file path of the CSV file
		    String csvFile = "C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\customer_queries.csv";

		    // Get the current user's ID
		    String creatorID = getQueryID();

		    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		            String[] row = line.split(","); // assuming comma-separated values
		            if (row.length > 7 && row[7].equals(creatorID)) { // Check if creatorID matches
		                data.add(row);
		            }
		        }
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }

		    // Get column names from user input (modify this as needed)
		    String[] columnNames = {"URN", "Name", "Email", "Query", "Priority", "type", "date", "ID"};

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
		                try {
							saveDataToCSV(data, csvFile);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // Save changes to CSV file
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

		public String getQueryID() {
		    // TODO: Replace with your implementation to get the current user's ID
		    return loginController.currentUsername;
		}



		private void saveDataToCSV(ObservableList<String[]> data, String filePath) throws IOException {
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
		        // Write data to CSV file
		        for (String[] row : data) {
		            bw.write(String.join(",", row));
		            bw.newLine();
		        }
		    }
		}


@FXML
public void createQueryBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
	
				Parent parent = FXMLLoader.load(
			    getClass().getResource("AddQueryView.fxml"));
			    Scene scene = new Scene(parent); 
			    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			    stage.setTitle(" Creator "); 
			    stage.setScene(scene);
			    stage.show(); 
}


//public void sendQueryBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
//    // Get the selected row from the TableView
//    String[] selectedRow = userTable.getSelectionModel().getSelectedItem();
//    
//    if (selectedRow != null) {
//        // Create a new CSV file for handling open queries
//        String handlerFile = "C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\handlerOpenQueries.csv";
//        
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(handlerFile))) {
//            // Write the selected row to the CSV file
//            bw.write(String.join(",", selectedRow));
//            bw.newLine();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        
//        System.out.println("Selected row saved to handlerOpenQueries.csv");
//    } else {
//        System.out.println("No row selected");
//    }
//}


public void sendQueryBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
	  // Get the selected row from the TableView
    String[] selectedRow = userTable.getSelectionModel().getSelectedItem();

    if (selectedRow != null) {
        // Create a new CSV file for handling open queries
        String handlerFile = "C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\handlerOpenQueries.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(handlerFile, true))) {
            // Append the selected row to the CSV file
            bw.write(String.join(",", selectedRow));
            bw.newLine(); // Add newline character
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Selected row appended to customer_queries.csv");

        // Remove the selected row from the table
        userTable.getItems().remove(selectedRow);

        // Create a new thread to save the changes to the CSV file
        Thread saveChangesThread = new Thread(() -> {
            List<String[]> updatedData = new ArrayList<>(userTable.getItems());
            try (FileWriter writer = new FileWriter("C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\customer_queries.csv")) {
                for (String[] row : updatedData) {
                    writer.write(String.join(",", row));
                    writer.write("\n");
                }
            } catch (IOException ex) {
                System.out.println("Error writing to file: " + ex.getMessage());
            }
        });

        saveChangesThread.start();

    } else {
        System.out.println("No row selected");
    }
}

}

