package application;

import java.io.*;
import application.mainPanelController;
import java.security.*;
import java.util.*;

import javax.crypto.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.*;

public class loginController {

    @FXML
    private Button btn_Login;

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
    
    public static String currentUsername;
    

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
                    currentUsername = admin.getUsername();

                    // Check the access level of the logged-in user
                    switch (admin.getAccessLevel()) {
                        case ADMIN: 
                            // Load Admin Panel view
                            Parent adminPanel = FXMLLoader.load(getClass().getResource("MainPanelView.fxml"));
                            Scene adminScene = new Scene(adminPanel);
                            Stage adminStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            adminStage.setTitle("Admin Panel");
                            adminStage.setScene(adminScene);
                            adminStage.show();
                            break;
                        case HANDLER:
                            // Load Handler Panel view
                            Parent handlerPanel = FXMLLoader.load(getClass().getResource("HandlerPanelView2.fxml"));
                            Scene handlerScene = new Scene(handlerPanel);
                            Stage handlerStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            handlerStage.setTitle("Handler Panel");
                            handlerStage.setScene(handlerScene);
                            handlerStage.show();
                            break;
                        case CREATOR:
                            // Load Creator Panel view
                            Parent creatorPanel = FXMLLoader.load(getClass().getResource("CreatorPanelView3.fxml"));
                            Scene creatorScene = new Scene(creatorPanel);
                            Stage creatorStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            creatorStage.setTitle("Creator Panel");
                            creatorStage.setScene(creatorScene);
                            creatorStage.show();
                            break;
                        case ANALYST:
                            // Load Creator Panel view
                            Parent analystPanel = FXMLLoader.load(getClass().getResource("AnalystPanelView4.fxml"));
                            Scene analystScene = new Scene(analystPanel);
                            Stage analystStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            analystStage.setTitle("Creator Panel");
                            analystStage.setScene(analystScene);
                            analystStage.show();
                            break;
                        default:
                            // Invalid access level, show error
                            errorField.setVisible(true);
                            break;
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Welcome ");
                alert.setHeaderText("Hi, " + admin.getUsername() + " Have a great day!.. ");
                alert.setGraphic(new ImageView(this.getClass().getResource("smileynoBg.png").toString()));
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

    
    public ArrayList<admin> updateLoginUsernamesAndPasswords() throws IOException {
        ArrayList<admin> adminList = new ArrayList<>();

        // Read CSV file
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rasti\\OneDrive - The University of Northampton\\Desktop\\OneDrive - The University of Northampton\\eclipse-workspace\\TeamRocketMain\\userData.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            // Assuming the third column contains the "ADMIN" value
            String accessLevel = data[2].trim(); // Update index based on your CSV file structure
            admin.AccessLevel level = admin.AccessLevel.valueOf(accessLevel.toUpperCase());
            admin admin = new admin(level);
            admin.setUsername(data[0].trim());
            admin.setPassword(data[1].trim());
            adminList.add(admin);
        }
        br.close();

        return adminList;
    }


    
    
    private void writeToFile() throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String username = userNameTextField.getText();
        String password = getPassword();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        writer.write(username + "," + encryptor.encryptString(password) + "\n");
        writer.close();
    }
}
