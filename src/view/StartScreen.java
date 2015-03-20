package view;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

import model.Maze;
import edu.princeton.cs.introcs.StdOut;
import files.Dummy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartScreen extends Application {

	@FXML private TextField chosenFile = new TextField();

	private String mazeOneLocation = "../MazeSolver/src/files/maze1.txt";
	private String mazeTwoLocation = "../MazeSolver/src/files/maze2.txt";
	private String mazeThreeLocation = "../MazeSolver/src/files/maze3.txt";
	private String mazeFourLocation = "../MazeSolver/src/files/maze4.txt";
	private String mazeFiveLocation = "../MazeSolver/src/files/maze5.txt";
	private String mazeSixLocation = "../MazeSolver/src/files/maze6.txt";

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
	public boolean newFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Maze File");
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			chosenFile.setText(selectedFile.getAbsolutePath());
			return true;
		}
		return false;
	}

	@FXML
	public void chooseFile(ActionEvent event) throws IOException, InterruptedException
	{
		startUp(chosenFile.getText());
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	public void chooseSampleMaze(ActionEvent event) throws IOException, InterruptedException, URISyntaxException
	{
		String path = null;
		if(event.getSource().toString().contains("Maze 1"))
		{
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
		MazeApp systemController = new MazeApp(path, null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MazeApp.fxml"));
		loader.setController(systemController);

		Stage mainStage = new Stage();
		mainStage.setTitle("Maze Solver");
		BorderPane root = (BorderPane) loader.load();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.show();
	}

}
