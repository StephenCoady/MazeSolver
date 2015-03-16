package view;


import controller.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import edu.princeton.cs.introcs.StdOut;
import model.GridDisplay;
import model.Square;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MazeApp extends Application{

	@FXML private BorderPane mainPanel;
	@FXML private Scene scene;
	@FXML private ScrollPane scrollPane = new ScrollPane();
	@FXML private AnchorPane topCanvas = new AnchorPane();
	@FXML private Pane topPane = new Pane();
	@FXML private TextArea outputText = new TextArea();

	private static MazeSolver solver;
	private static GridDisplay gridDisplay;
	private static ArrayList<String> maze;
	private static String fileLocation;
	private static ArrayList<Square> stackSquares;
	private static ArrayList<Square> queueSquares;

	private static int height;
	private static int width;

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException 
	{
		primaryStage.setTitle("Maze Solver");
		mainPanel = FXMLLoader.load(getClass().getResource("MazeApp.fxml"));
		scene = new Scene(mainPanel);

		stackSquares = new ArrayList<Square>();
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		AnchorPane.setBottomAnchor(scrollPane, 10.0);
		AnchorPane.setTopAnchor(scrollPane, 10.0);
		AnchorPane.setRightAnchor(scrollPane, 10.0);
		AnchorPane.setLeftAnchor(scrollPane, 10.0);

		scrollPane.setStyle("-fx-background-color: transparent;");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		displayMaze();
	}

	@FXML
	public void displayMaze() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		int[] dimensions = solver.getDimensions();
		width = dimensions[0];
		height = dimensions[1];
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);
		gridDisplay.createElements();

		topCanvas.getChildren().clear();
		topCanvas.getChildren().add(scrollPane);
		scrollPane.setContent(gridDisplay.getDisplay());
		//centerNodeInScrollPane(scrollPane, gridDisplay.getDisplay());
		mainPanel.setCenter(topCanvas);
		outputText.appendText("Maze cleared. \n");
		prepareArrays();
	}

	private void prepareArrays()
	{
		Stack<Square> stack = solver.depthFirst();
		while(!stack.isEmpty()){
			stackSquares.add(stack.pop());
		}
		if(!stackSquares.isEmpty()){
			stackSquares.remove(stackSquares.size()-1);
		}
		if(!stackSquares.isEmpty()){
			stackSquares.remove(0);
		}

		queueSquares = solver.breadthFirst();
		Collections.reverse(queueSquares);
		GridDisplay.emptyPreviouslyColoured();

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
		double start = System.currentTimeMillis();
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		if(solver.getMaze().getStartPoint()!=null && solver.getMaze().getFinishPoint()!=null)
		{
			Stack<Square> stack = solver.depthFirst();

			gridDisplay = new GridDisplay(height, width);
			gridDisplay.setMaze(maze);
			int steps = stack.size()-1;
			gridDisplay.colorSolved(stack);

			double end = System.currentTimeMillis();
			double totalTime = (end - start)/1000;
			outputText.appendText("Solution found: \n");
			outputText.appendText("---" + steps + " steps. \n");
			outputText.appendText("---" + totalTime + " seconds. \n");

			scrollPane.setContent(gridDisplay.getDisplay());

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("This maze is missing at least one critical point needed to find a solution. \n");
		}
	}
	
	@FXML
	public void solveQueue() throws FileNotFoundException
	{
		double start = System.currentTimeMillis();
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		if(solver.getMaze().getStartPoint()!=null && solver.getMaze().getFinishPoint()!=null)
		{
			ArrayList<Square> squares = solver.breadthFirst();

			gridDisplay = new GridDisplay(height, width);
			gridDisplay.setMaze(maze);
			int steps = squares.size()-1;
			gridDisplay.colorSolvedQueue(squares);

			double end = System.currentTimeMillis();
			double totalTime = (end - start)/1000;
			outputText.appendText("Solution found: \n");
			outputText.appendText("---" + steps + " steps. \n");
			outputText.appendText("---" + totalTime + " seconds. \n");

			scrollPane.setContent(gridDisplay.getDisplay());

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("This maze is missing at least one critical point needed to find a solution. \n");
		}
	}

	@FXML
	public void solveStackByStep() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);

		if(!stackSquares.isEmpty())
		{
			gridDisplay.colorStep(stackSquares.get(stackSquares.size()-1));
			stackSquares.remove(stackSquares.size()-1);
			scrollPane.setContent(gridDisplay.getDisplay());

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("No more steps! \n");
		}
	}

	@FXML
	public void solveQueueByStep() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);

		if(!queueSquares.isEmpty())
		{
			gridDisplay.colorStep(queueSquares.get(queueSquares.size()-1));
			queueSquares.remove(queueSquares.size()-1);
			scrollPane.setContent(gridDisplay.getDisplay());

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);

			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("No more steps! The solution has been found. "
					+ "Any remaining blocks will not be explored\n");
		}
	}


	public double centerNodeInScrollPane(ScrollPane scrollPane, Group node) {
		double h = scrollPane.getContent().getBoundsInLocal().getHeight();
		double y = (node.getBoundsInParent().getMaxY() + 
				node.getBoundsInParent().getMinY()) / 200.0;
		double v = scrollPane.getViewportBounds().getHeight();
		return (scrollPane.getVmax() * ((y - 0.5 * v) / (h - v)));
	}
}


