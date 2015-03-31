package view;

import java.io.*;
import java.net.URISyntaxException;

import files.Dummy;
import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;

public class StartScreen extends Application {

	@FXML private TextField chosenFile = new TextField();

	public static void main(String[] args) {
		File dir = new File("resources");
		dir.mkdir();
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
		if(event.getSource().toString().contains("Maze 1")){
			path = writeFile("maze1.txt");
		}
		if(event.getSource().toString().contains("Maze 2")){
			path = writeFile("maze2.txt");
		}
		if(event.getSource().toString().contains("Maze 3")){
			path = writeFile("maze3.txt");
		}
		if(event.getSource().toString().contains("Maze 4")){
			path = writeFile("maze4.txt");
		}
		if(event.getSource().toString().contains("Maze 5")){
			path = writeFile("maze5.txt");
		}
		if(event.getSource().toString().contains("Maze 6")){
			path = writeFile("maze6.txt");
		}
		startUp(path);
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	private String writeFile(String fileName) throws IOException
	{
		String path;
		InputStream stream = Dummy.class.getResourceAsStream(fileName);
		OutputStream outStream = new FileOutputStream("resources/"+fileName);
		int i;
		while((i = stream.read()) != -1)
		{
			outStream.write(i);
		}
		outStream.close();
		path = "resources/"+fileName;
		return path;
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
