package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class createQueryController {
		@FXML
		private Button addQuery;
		
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
	    public void createPropertyBtn(ActionEvent e) throws IOException, NoSuchAlgorithmException {
	    }
	    

}
