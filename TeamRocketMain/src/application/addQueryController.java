package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addQueryController {
	
	@FXML
	private Button addQueryBtn;
	
	@FXML
	TextArea queryTextArea;
	

	@FXML
	TextField queryNameField;
	
	@FXML
	TextField queryEmailField;
	
	@FXML
	private Spinner<String> PriorityFIeld;
	
    @FXML
	private Spinner<String> TypeFIeld;
    
	@FXML
	private Button BackToCreator;
	
	 public void BackToCreator(ActionEvent e) throws IOException, NoSuchAlgorithmException {
	    	Parent parent = FXMLLoader.load(
		               getClass().getResource("CreatorPanelView3.fxml")); 
					
		      // Build the scene graph.
		      Scene scene = new Scene(parent); 
		
		      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		      // Display our window, using the scene graph.
		      stage.setTitle("AdminPanel"); 
		      stage.setScene(scene);
		      stage.show(); 
	    }
    
    
    private ObservableList<String> priorityOptions = FXCollections.observableArrayList(
            "Low", "Medium", "High"
    );

    private ObservableList<String> typeOptions = FXCollections.observableArrayList(
            "Information", "Guidance", "Notifcation"
    );

    public void initialize() {
        // Configure the Spinner with the priority options
        SpinnerValueFactory<String> priorityValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(priorityOptions);
        PriorityFIeld.setValueFactory(priorityValueFactory);

        // Configure the Spinner with the type options
        SpinnerValueFactory<String> typeValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(typeOptions);
        TypeFIeld.setValueFactory (typeValueFactory);
    }
    private String getCurrentUsername() {
        return loginController.currentUsername;
    }
    
    public void addQueryBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
    	 String username = queryNameField.getText();
    	    String email = queryEmailField.getText();
    	    String message = queryTextArea.getText();
    	    // generating a random number in id, as id is set to not editable on the fxml file
    	    customerQuery newQuery = new customerQuery("", "", "", "", "", "", "", null);
    	    Random rand = new Random();

    	    // using the form to collect data with the getters and setters in property.java
    	    newQuery.setQueryURN(generateQueryURN());

    	    newQuery.setQueryusername(queryNameField.getText());
    	    newQuery.setQueryemail(queryEmailField.getText());
    	    newQuery.setQuerymessage(queryTextArea.getText());
    	    newQuery.setQueryCreatorID(loginController.currentUsername);

    	    String priority = PriorityFIeld.getValue();
    	    String type = TypeFIeld.getValue();
    	    newQuery.setQueryPrority(priority);
    	    newQuery.setQueryType(type); // Set the query type value

    	    LocalDateTime currentDate = LocalDateTime.now();
    	    newQuery.setQueryOpenDate(currentDate);

    	    // Format the LocalDateTime object to a string before writing it to the CSV file
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	    String formattedDate = currentDate.format(formatter);

    	    // Add the new query to the CSV file
    	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\rasti\\git\\teamRocket\\TeamRocketMain\\customer_queries.csv", true));
    	    writer.write(newQuery.getQueryURN() + "," + newQuery.getQueryusername() + "," + newQuery.getQueryemail() + "," + newQuery.getQuerymessage() + "," + newQuery.getQueryPrority() + ","+ newQuery.getQueryType() + "," + formattedDate + "," + newQuery.getQueryID()); // Include the formatted date value
    	    writer.newLine();
    	    writer.close();

    	    // Alert user that the file has been updated
    	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Query added successfully", ButtonType.OK);
    	    alert.showAndWait();
    	}
    private String generateQueryURN() {
        // Generate a random 6-digit number as the query URN
        Random random = new Random();
        int urn = 100000 + random.nextInt(900000);
        return Integer.toString(urn);
    }
}
