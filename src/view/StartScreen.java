package view;

import controller.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.GridDisplay;
import edu.princeton.cs.introcs.StdOut;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartScreen extends Application {

	@FXML private TextField chosenFile = new TextField();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		primaryStage.setTitle("Choose a Maze File to Work With");
		Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	@FXML
	public void newFile()
	{
		
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Maze File");
		 File selectedFile = fileChooser.showOpenDialog(null);
		 if (selectedFile != null) {
			 chosenFile.setText(selectedFile.getAbsolutePath());
		 }
	}
	
	@FXML
	public void chooseFile(ActionEvent event) throws IOException, InterruptedException
	{
		Stage primaryStage = new Stage();
		MazeApp maze = new MazeApp();
		maze.setFile(chosenFile.getText());
		maze.start(primaryStage);
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
