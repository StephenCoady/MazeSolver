package view;

import controller.*;

import java.io.IOException;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdOut;
import view.GridDisplay;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MazeApp extends Application {

	@FXML private static GridPane mazeGrid = new GridPane();
	@FXML private Pane canvas = new Pane();

	private MazeSolver solver;
	private GridDisplay gridDisplay;
	private ArrayList<String> maze;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException 
	{
		solver = new MazeSolver("src/files/more_complex_maze.txt");
		int[] dimensions = solver.getDimensions();
		int width = dimensions[0];
		int height = dimensions[1];
		primaryStage.setTitle("Maze Solver");

		maze = solver.getMazeLayout();

		//Represents the grid with Rectangles
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);

		BorderPane mainPanel = new BorderPane();

		mainPanel.setCenter(gridDisplay.getDisplay());

		Scene scene = new Scene(mainPanel, 900, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
		//re paints the grid and its boxes
		gridDisplay.createElements();
	}
}


