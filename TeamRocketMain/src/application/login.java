package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class login extends Application {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Load the FXML file.
		Parent parent = FXMLLoader.load(
				// getClass().getResource("LoginTempView.fxml"));
				getClass().getResource("LoginTempView.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		// Display our window, using the scene graph.
		stage.setTitle("Login Page");
		stage.setScene(scene); 
		stage.show();
		
	}



}
