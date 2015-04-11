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

/**
 * @author Stephen Coady
 * @date 31-03-15
 * Main class of app. Runs the simple start screen which 
 * allows the user to use some sample files if they wish.
 *
 */
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

	/**
	 * 
	 * a method to use javafx file chooser to choose a new file.
	 * 
	 * @return whether or not the user has actually chosen a file
	 */
	@FXML
	private boolean newFile()
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
	private void chooseFile(ActionEvent event) throws IOException, InterruptedException
	{
		startUp(chosenFile.getText());
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	/**
	 * a method to choose a file from some pre-prepared ones.
	 * @param event
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 */
	@FXML
	private void chooseSampleMaze(ActionEvent event) throws IOException, InterruptedException, URISyntaxException
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

	/**
	 * 
	 * this method is a workaround to allow the user to choose from some 
	 * already packaged files when they are using the runnable jar file.
	 * 
	 * @param fileName the name of the file to be used.
	 * @return
	 * @throws IOException
	 */
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
