package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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

public class analystController {
		@FXML
		private Button btn_viewQuery;
		

		
		
	    @FXML
		private Button searchBtn;
	    
	    @FXML
		private Button btn_sendQuery;
	    
	    @FXML
		private Button btn_acceptQuery;
	    
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
			    String csvFile = "C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\analystQueries.csv";

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
			    String[] columnNames = {"URN", "Name", "Email", "Query", "Priority", "Date/Time", };

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



public void sendQueryBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
    // Get the currently selected row in the table view
    String[] selectedRow = userTable.getSelectionModel().getSelectedItem();
    if (selectedRow != null) {
        String emailTo = selectedRow[2]; // Get the email address from the selected row
        String emailSubject = "Accepted Query";
        String emailBody = "Dear " + selectedRow[1] + "," + System.lineSeparator() +
                "Thank you for your query. We are pleased to inform you that your query has been accepted." + System.lineSeparator() +
                "Query: " + selectedRow[3] + System.lineSeparator() +
                "Priority: " + selectedRow[4] + System.lineSeparator() +
                "Date/Time: " + selectedRow[5];

        try {
            // Encode URI with "to" parameter and replace plus signs with %20
            String mailtoUri = "mailto:" + emailTo + "?subject=" + URLEncoder.encode(emailSubject, "UTF-8").replaceAll("\\+", "%20") +
                    "&body=" + URLEncoder.encode(emailBody, "UTF-8").replaceAll("\\+", "%20");
            // Open default email client with pre-filled "to", subject, and body
            Desktop.getDesktop().mail(new URI(mailtoUri));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace(); // Handle exception as appropriate for your application
        }
    }
}



public void acceptQueryBtn(ActionEvent e) {
    // Get the currently selected row in the table view
    String[] selectedRow = userTable.getSelectionModel().getSelectedItem();
    if (selectedRow != null) {
        String emailTo = selectedRow[2]; // Get the email address from the selected row
        String emailSubject = "Accepted Query";
        String emailBody = "Dear " + selectedRow[1] + "," + System.lineSeparator() +
                "Thank you for your query. We are sorry to inform you that your query has been Rejected." + System.lineSeparator() +
                "Query: " + selectedRow[3] + System.lineSeparator() +
                "Priority: " + selectedRow[4] + System.lineSeparator() +
                "Date/Time: " + selectedRow[5];

        try {
            // Encode URI with "to" parameter and replace plus signs with %20
            String mailtoUri = "mailto:" + emailTo + "?subject=" + URLEncoder.encode(emailSubject, "UTF-8").replaceAll("\\+", "%20") +
                    "&body=" + URLEncoder.encode(emailBody, "UTF-8").replaceAll("\\+", "%20");
            // Open default email client with pre-filled "to", subject, and body
            Desktop.getDesktop().mail(new URI(mailtoUri));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace(); // Handle exception as appropriate for your application
        }

        // Remove the selected row from the table view
        userTable.getItems().remove(selectedRow);

        // Create a new thread to save the changes to the CSV file
        Thread saveChangesThread = new Thread(() -> {
            List<String[]> updatedData = new ArrayList<>(userTable.getItems());
            try (FileWriter writer = new FileWriter("C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\analystqueries.csv")) {
                for (String[] row : updatedData) {
                    writer.write(String.join(",", row));
                    writer.write("\n");
                }
            } catch (IOException ex) {
                System.out.println("Error writing to file: " + ex.getMessage());
            }
        });

        // Start the thread to save the changes to the CSV file
        saveChangesThread.start();
    }
}

}







