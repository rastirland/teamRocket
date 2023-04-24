package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.loginController;



public class mainPanelController {
	


	@FXML
	private Button btn_UserAccount;
	
	@FXML
	private Button btn_PropertyList;
	
	@FXML
	private Button customerListBtn;
	
	@FXML
	private Button hirePropertyListBtn;
	
	@FXML
    private Label usernameField;
	
	
	
	    
	@FXML
	public void GoToAccounts(ActionEvent e) throws IOException, NoSuchAlgorithmException {
		
					Parent parent = FXMLLoader.load(
				    getClass().getResource("AdminPanelView.fxml"));
				    Scene scene = new Scene(parent); 
				    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				    stage.setTitle("AdminPanel"); 
				    stage.setScene(scene);
				    stage.show(); 
}
	@FXML
	
	public void goToCustomers(ActionEvent e) throws IOException, NoSuchAlgorithmException {
		
					Parent parent = FXMLLoader.load(
				    getClass().getResource("HandlerPanelView2.fxml"));
				    Scene scene = new Scene(parent); 
				    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				    stage.setTitle("AdminPanel"); 
				    stage.setScene(scene);
				    stage.show(); 
}
	
	@FXML
	public void goToPropertyList(ActionEvent e) throws IOException, NoSuchAlgorithmException {
		
					Parent parent = FXMLLoader.load(
				    getClass().getResource("CreatorPanelView3.fxml"));
				    Scene scene = new Scene(parent); 
				    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				    stage.setTitle("AdminPanel"); 
				    stage.setScene(scene);
				    stage.show(); 
}
	
	@FXML
	public void goToHireReturn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
		
					Parent parent = FXMLLoader.load(
				    getClass().getResource("AnalystHireListView4.fxml"));
				    Scene scene = new Scene(parent); 
				    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				    stage.setTitle("AdminPanel"); 
				    stage.setScene(scene);
				    stage.show(); 
}
	
	 private String getCurrentUsername() {
	        return loginController.currentUsername;
	    }
	 
	 
	 @FXML
	    private TextField statCreated;
	 
	 @FXML
	    private TextField statHandler;
	 
	 @FXML
	    private TextField statAnalyst;
	    
	    // ...

	    // Method to count the number of lines in customer_query.csv file
	    private int countLinesInFile(String fileName) {
	        int count = 0;
	        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	            while (br.readLine() != null) {
	                count++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return count;
	    }

	

	    public void initialize() {
	        // Retrieve the current from my loginController, 
	    	// and should initialise when class first starts to display welcome "username"
	        String currentUsername = getCurrentUsername();

	        usernameField.setText("Welcome " + currentUsername);
	        System.out.println(currentUsername + "starts when java class is run");
	        
	        // Count the number of lines in customer_query.csv and update the statCreator label
	        int lineCount = countLinesInFile("customer_queries.csv");
	        statCreated.setText(" " + lineCount);
	        
	        int lineCount2 = countLinesInFile("HandlerOpenQueries.csv");
	        statHandler.setText(" " + lineCount2);
	        
	        
	        int lineCount3 = countLinesInFile("AnalystQueries.csv");
	        statAnalyst.setText(" " + lineCount3);
	    }
}