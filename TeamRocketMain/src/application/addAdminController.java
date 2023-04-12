
package application;


import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Platform;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class addAdminController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	@FXML
	private Spinner<String> accessLevelField;

	@FXML
	private Button btn_Login;
	
	@FXML 
	private Button btn_Exit;
	
	@FXML 
	private Button BackToAdmin;

    @FXML
    protected TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox showPassword;

    @FXML
    private TextField hiddenPasswordTextField;

    @FXML
    public TextField errorField;
	
    public void initialize() {
		// Set up the garden spinner with options Yes and No
		ObservableList<String> gardenOptions = FXCollections.observableArrayList("ADMIN", "CREATOR", "HANDLER", "ANALYST");
		SpinnerValueFactory<String> gardenValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(gardenOptions);
		accessLevelField.setValueFactory(gardenValueFactory);
		
		
	}

    @FXML
	public void BackToAdmin(ActionEvent e) throws IOException, NoSuchAlgorithmException {
    	Parent parent = FXMLLoader.load(
	               getClass().getResource("AdminPanelView.fxml")); 
				
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	
	      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	      // Display our window, using the scene graph.
	      stage.setTitle("AdminPanel"); 
	      stage.setScene(scene);
	      stage.show(); 
    }
     
    
    
    

    // File where the user login credentials are stored
    File file = new File("userData.csv");

    // Map containing <Username, Password>
    HashMap<String, String> loginInfo = new HashMap<>();

    Encryptor encryptor = new Encryptor(); 

    @FXML
    void changeVisibility(ActionEvent event) {
        if (showPassword.isSelected()) {
            passwordTextField.setText(hiddenPasswordTextField.getText());
            passwordTextField.setVisible(true);
            hiddenPasswordTextField.setVisible(false);
            return;
        }
        hiddenPasswordTextField.setText(passwordTextField.getText());
        hiddenPasswordTextField.setVisible(true);
        passwordTextField.setVisible(false);
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        writeToFile();
    }

    @FXML
    public void loginBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
        String username = userNameTextField.getText();
        String password = getPassword();
        ArrayList<admin> adminList = updateLoginUsernamesAndPasswords();

        for (admin admin : adminList) {
            if (admin.getUsername().equals(username) && encryptor.encryptString(password).equals(admin.getPassword())) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("MainPanelView.fxml"));
                    Scene scene = new Scene(parent);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setTitle("AdminPanel");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
    			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    			alert.setTitle("Welcome " +  admin.getUsername());
    			alert.setHeaderText( "Hi, " + admin.getUsername() + " Have a great day!.. ");
    			Optional<ButtonType> result = alert.showAndWait();

    			if (result.isPresent() && result.get() == ButtonType.OK) {
    			  System.out.println("Successfully logged in as " + admin.getUsername());
                return;	
    
    			}
              
            }
        }
        errorField.setVisible(true);
    }

    protected String getPassword() {
        if (passwordTextField.isVisible()) {
            return passwordTextField.getText();
        } else {
            return hiddenPasswordTextField.getText();
        }
    }

    private ArrayList<admin> updateLoginUsernamesAndPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        ArrayList<admin> adminList = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] usernameAndPassword = scanner.nextLine().split(",");
            admin admin = new admin();
            admin.setUsername(usernameAndPassword[0]);
            admin.setPassword(usernameAndPassword[1]);
            adminList.add(admin);
        }
        scanner.close();
        return adminList;
    }

   
    private void writeToFile() throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException,
    IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
    String username = userNameTextField.getText();
    String password = getPassword();
    String role = accessLevelField.getValue(); // Get selected item from ComboBox
    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

    writer.write(username + "," + encryptor.encryptString(password) + "," + role + "\n"); // Include role in the string to write
    writer.close();
}
	    
}