package view;


import controller.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import model.GridDisplay;
import model.Square;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MazeApp extends Application{

	@FXML private BorderPane mainPanel;
	@FXML private Scene scene;
	@FXML private ScrollPane scrollPane = new ScrollPane();
	@FXML private AnchorPane topCanvas = new AnchorPane();
	@FXML private Pane topPane = new Pane();
	@FXML private Button stacksButton = new Button();
	@FXML private Button queueButton = new Button();
	@FXML private Button stepStacksButton = new Button();
	@FXML private Button stepQueueButton = new Button();
	@FXML private TextArea outputText = new TextArea();

	private MazeSolver solver;
	private GridDisplay gridDisplay;
	private ArrayList<String> maze;
	private static String fileLocation;
	private int height;
	private int width;

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException 
	{
		primaryStage.setTitle("Maze Solver");
		mainPanel = FXMLLoader.load(getClass().getResource("MazeApp.fxml"));
		scene = new Scene(mainPanel);

		scrollPane.setLayoutX(97);
		scrollPane.setLayoutX(107);
		topCanvas.setBottomAnchor(scrollPane, 106.0);
		topCanvas.setTopAnchor(scrollPane, 107.0);
		topCanvas.setRightAnchor(scrollPane, 70.0);
		topCanvas.setLeftAnchor(scrollPane, 97.0);
		scrollPane.setStyle("-fx-background-color: transparent;");
		primaryStage.setScene(scene);
		primaryStage.show();
		displayMaze();
	}

	public void displayMaze() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();
		int[] dimensions = solver.getDimensions();
		this.width = dimensions[0];
		this.height = dimensions[1];
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);
		gridDisplay.createElements();
		
		
		topCanvas.getChildren().add(scrollPane);
		scrollPane.setContent(gridDisplay.getDisplay());
		
		mainPanel.setCenter(topCanvas);
	}
	
	@SuppressWarnings("static-access")
	public void setFile(String fileLocation)
	{
		this.fileLocation = fileLocation;
	}

	@FXML
	public void newFile(ActionEvent event)
	{
		Stage primaryStage = new Stage();
		StartScreen screen = new StartScreen();
		try {
			screen.start(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	/*
	 * 
	 * a method to display the fully solved maze with yellow path drawn
	 * 
	 */
	@FXML
	public void solveStack() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();
		int[] dimensions = solver.getDimensions();
		this.width = dimensions[0];
		this.height = dimensions[1];
		Stack<Square> stack = solver.depthFirst();
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);
		gridDisplay.colorSolved(stack);
		outputText.appendText("Solution found. \n");
		topCanvas.getChildren().clear();
		topCanvas.getChildren().add(scrollPane);
		scrollPane.setContent(gridDisplay.getDisplay());
		mainPanel.setCenter(topCanvas);
	}
}


