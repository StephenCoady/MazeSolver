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

	private String mazeOneLocation = System.getProperty("user.dir")+"/"+"src/files/maze1.txt";
	private String mazeTwoLocation = System.getProperty("user.dir")+"/"+"src/files/maze2.txt";
	private String mazeThreeLocation = System.getProperty("user.dir")+"/"+"src/files/maze3.txt";
	private String mazeFourLocation = System.getProperty("user.dir")+"/"+"src/files/maze4.txt";
	private String mazeFiveLocation = System.getProperty("user.dir")+"/"+"src/files/maze5.txt";
	private String mazeSixLocation = System.getProperty("user.dir")+"/"+"src/files/maze6.txt";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Welcome To Maze Solver");
		Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
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
		startUp(chosenFile.getText());
		
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	public void chooseSampleMaze(ActionEvent event) throws IOException, InterruptedException
	{
		String path = null;
		if(event.getSource().toString().contains("Maze 1")){
			path = mazeOneLocation;
		}
		if(event.getSource().toString().contains("Maze 2")){
			path = mazeTwoLocation;
		}
		if(event.getSource().toString().contains("Maze 3")){
			path = mazeThreeLocation;
		}
		if(event.getSource().toString().contains("Maze 4")){
			path = mazeFourLocation;
		}
		if(event.getSource().toString().contains("Maze 5")){
			path = mazeFiveLocation;
		}
		if(event.getSource().toString().contains("Maze 6")){
			path = mazeSixLocation;
		}
		startUp(path);
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	private void startUp(String path) throws IOException
	{
		MazeApp systemController = new MazeApp(path);

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"MazeApp.fxml"
						)
				);
		loader.setController(systemController);
		systemController.displayMaze();

		Stage mainStage = new Stage();
		mainStage.setTitle("Maze Solver");
		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.show();
	}

}
