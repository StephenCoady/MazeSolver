package view;


import controller.*;

import java.io.*;
import java.net.URL;
import java.util.*;

import model.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;

/**
 * @author Stephen Coady
 * @date 31-03-15
 * 
 * Class to run the app once it has passed the first stage. 
 * runs all major functions.
 *
 */
public class MazeApp implements Initializable{

	@FXML private BorderPane mainPanel = new BorderPane();
	@FXML private Scene scene;
	@FXML private ScrollPane scrollPane = new ScrollPane();
	@FXML private AnchorPane topCanvas = new AnchorPane();
	@FXML private HBox topPane = new HBox();
	@FXML private TextArea outputText = new TextArea();
	@FXML private MenuBar myMenuBar = new MenuBar();
	@FXML private MenuItem exitCmd = new MenuItem();
	@FXML private MenuItem openCmd = new MenuItem();
	@FXML private MenuItem clearCmd = new MenuItem();
	@FXML private MenuItem aboutCmd = new MenuItem();
	@FXML private MenuItem fullCmd = new MenuItem();

	@FXML private Button recursionSolve = new Button();
	@FXML private Button recursionStep = new Button();
	@FXML private Separator recursionDivider = new Separator();
	@FXML private Separator vertSep1 = new Separator();
	@FXML private Separator vertSep2 = new Separator();
	@FXML private Label recursionLabel = new Label();
	@FXML private ImageView javaLogo = new ImageView();

	private static MazeSolver solver;
	private static GridDisplay gridDisplay;
	private static ArrayList<String> maze;
	private static String fileLocation;
	private static Boolean stopButton = false;
	private static ArrayList<Square> stackSquares;
	private static ArrayList<Square> stackSquaresByStep;
	private static ArrayList<Square> queueSquares;
	private static String newFile = "";
	private Timeline timeline;
	private String console;


	private static int height;
	private static int width;


	/**
	 * @param fileLocation - the location of the file to be used as the maze
	 * @param console - the text to be set in the console of the app. used for when the app has been
	 * previously running and console text needs to be saved and re-entered.
	 */
	public MazeApp(String fileLocation, String console)
	{
		MazeApp.fileLocation = fileLocation;
		this.console = console;
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			displayInitialMaze();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		vertSep1.setMaxHeight(63);
		vertSep2.setMaxHeight(63);
		recursionSolve.setVisible(false);
		recursionStep.setVisible(false);
		recursionDivider.setVisible(false);
		recursionLabel.setVisible(false);

		if(System.getProperty("os.name").toLowerCase().contains("mac"))
		{
			exitCmd.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN));
			openCmd.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN));
			clearCmd.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
			aboutCmd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN));
			fullCmd.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
		}
		else
		{
			exitCmd.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
			openCmd.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
			clearCmd.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
			aboutCmd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
			fullCmd.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		}
	}

	/**
	 * shows a blank maze with no solution upon startup.
	 * 
	 * @throws FileNotFoundException
	 */
	private void displayInitialMaze() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		int[] dimensions = solver.getDimensions();
		width = dimensions[0];
		height = dimensions[1];
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);
		gridDisplay.createElements();

		topPane.getChildren().clear();
		topPane.getChildren().add(gridDisplay.getDisplay());
		scrollPane.setContent(topPane);

		if(console!=null){
			outputText.setText(console);
			outputText.end();
		}

		topCanvas.getChildren().clear();
		topCanvas.getChildren().add(scrollPane);

		mainPanel.setCenter(topCanvas);
		prepareArrays();

	}

	/**
	 * 
	 * displays the maze without solution, also used to clear the maze.
	 * 
	 * @throws FileNotFoundException
	 */
	@FXML
	private void displayMaze() throws FileNotFoundException
	{

		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		int[] dimensions = solver.getDimensions();
		width = dimensions[0];
		height = dimensions[1];
		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);
		gridDisplay.createElements();

		topPane.getChildren().clear();
		topPane.getChildren().add(gridDisplay.getDisplay());
		scrollPane.setContent(topPane);

		topCanvas.getChildren().clear();
		topCanvas.getChildren().add(scrollPane);

		mainPanel.setCenter(topCanvas);
		outputText.appendText("Maze cleared. \n");
		outputText.appendText("\n");
		prepareArrays();

	}

	/**
	 * a method to set up all arrays used to show the solutions of the maze
	 */
	private void prepareArrays()
	{
		stackSquares = new ArrayList<Square>();
		queueSquares = new ArrayList<Square>();
		stackSquaresByStep = new ArrayList<Square>();

		stackSquares.clear();
		queueSquares.clear();
		stackSquaresByStep.clear();

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

		stackSquaresByStep = solver.getDepthPath();
		Collections.reverse(stackSquaresByStep);

		queueSquares = solver.breadthFirst();
		Collections.reverse(queueSquares);
		GridDisplay.emptyPreviouslyColoured();

	}

	public void setFile(String fileLocation)
	{
		MazeApp.fileLocation = fileLocation;
	}

	/**
	 * 
	 * allows the user to choose a new file from the GUI
	 * 
	 * @param event - the event which triggered this method
	 * @throws IOException
	 */
	@FXML
	private void newFile(ActionEvent event) throws IOException
	{
		if(timeline!=null){
			timeline.stop();
		}
		if(openFile())
		{
			outputText.appendText("New Maze file loaded. \n");
			outputText.appendText("\n");

			MazeApp systemController = new MazeApp(newFile, outputText.getText());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("MazeApp.fxml"));
			loader.setController(systemController);

			Stage mainStage = new Stage();
			mainStage.setTitle("Maze Solver");
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.show();
			Stage stage = (Stage) myMenuBar.getScene().getWindow();
			stage.hide();
		}
	}

	/**
	 * shows a JavaFX file chooser window, with the parent being the window it was called from.
	 * @return a boolean of whether the file choice has been successful.
	 */
	private boolean openFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Maze File");
		File selectedFile = fileChooser.showOpenDialog(myMenuBar.getScene().getWindow());
		if (selectedFile != null) {
			newFile = selectedFile.getAbsolutePath();
			return true;
		}
		return false;
	}


	/**
	 * 
	 * solve the maze using stacks.
	 * @throws FileNotFoundException
	 */
	@FXML
	private void solveStack() throws FileNotFoundException
	{

		if(timeline!=null){
			timeline.stop();
			timeline = null;
		}

		double start = System.currentTimeMillis();
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		if(solver.getMaze().getStartPoint()!=null && solver.getMaze().getFinishPoint()!=null)
		{
			if(solver.getMaze().getHasSolution())
			{
				Stack<Square> stack = solver.depthFirst();

				gridDisplay = new GridDisplay(height, width);
				gridDisplay.setMaze(maze);
				int steps = stack.size()-1;
				gridDisplay.colorSolved(stack);

				double end = System.currentTimeMillis();
				double totalTime = (end - start)/1000;
				outputText.appendText("Solution found using STACK: \n");
				outputText.appendText("---" + steps + " steps. \n");
				outputText.appendText("---" + totalTime + " seconds. \n");
				outputText.appendText("\n");
				topPane.getChildren().clear();
				topPane.getChildren().add(gridDisplay.getDisplay());
				scrollPane.setContent(topPane);

				topCanvas.getChildren().clear();
				topCanvas.getChildren().add(scrollPane);

				mainPanel.setCenter(topCanvas);
			}
			else 
			{
				outputText.appendText("No solution found. \n");
				outputText.appendText("Play or step through the solution to see the path followed. \n");
				outputText.appendText("\n");
			}
		}
		else
		{
			outputText.appendText("This maze is missing at least one critical point needed to find a solution. \n");
		}
	}

	/**
	 * solve the maze using queues.
	 * @throws FileNotFoundException
	 */
	@FXML
	private void solveQueue() throws FileNotFoundException
	{

		if(timeline!=null){
			timeline.stop();
			timeline = null;
		}

		double start = System.currentTimeMillis();
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();
		if(solver.getMaze().getStartPoint()!=null && solver.getMaze().getFinishPoint()!=null)
		{
			if(solver.getMaze().getHasSolution())
			{
				ArrayList<Square> squares = solver.breadthFirst();

				gridDisplay = new GridDisplay(height, width);
				gridDisplay.setMaze(maze);
				int steps = squares.size()-1;
				gridDisplay.colorSolvedQueue(squares);

				double end = System.currentTimeMillis();
				double totalTime = (end - start)/1000;
				outputText.appendText("Solution found using QUEUE: \n");
				outputText.appendText("---" + steps + " steps. \n");
				outputText.appendText("---" + totalTime + " seconds. \n");
				outputText.appendText("\n");
				topPane.getChildren().clear();
				topPane.getChildren().add(gridDisplay.getDisplay());
				scrollPane.setContent(topPane);

				topCanvas.getChildren().clear();
				topCanvas.getChildren().add(scrollPane);


				mainPanel.setCenter(topCanvas);
			}
			else 
			{
				outputText.appendText("No solution found. \n");
				outputText.appendText("Play or step through the solution to see the path followed. \n");
				outputText.appendText("\n");
			}
		}
		else
		{
			outputText.appendText("This maze is missing at least one critical point needed to find a solution. \n");
			outputText.appendText("\n");
		}
	}


	/**
	 * allows the user to step through the path taken by the computer when it used stacks.
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@FXML
	private void solveStackByStep() throws FileNotFoundException, UnsupportedEncodingException
	{
		if(timeline!=null){
			timeline.stop();
			timeline = null;
		}

		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);

		if(!stackSquaresByStep.isEmpty())
		{
			gridDisplay.colorStep(stackSquaresByStep.get(stackSquaresByStep.size()-1));
			stackSquaresByStep.remove(stackSquaresByStep.size()-1);

			topPane.getChildren().clear();
			topPane.getChildren().add(gridDisplay.getDisplay());
			scrollPane.setContent(topPane);

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("No more steps! The solution has been found. "
					+ "Any remaining blocks will not be explored\n");
			outputText.appendText("\n");
		}
	}


	/**
	 * 
	 * a method to play the stack by automatically stepping through the solution
	 * 
	 */
	@FXML
	private void playStack() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException
	{


		// deals with resetting the maze each time play 
		// is pressed while the maze is already playing
		displayMaze();
		if(timeline!=null){
			timeline.stop();
			timeline = null;
		}

		if(!stopButton)
		{
			outputText.appendText("Auto-play started. \n");
			outputText.appendText("\n");
		}
		stopButton = false;
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);


		timeline = new Timeline(new KeyFrame(
				Duration.millis(400),
				ae -> playStackByStep()));
		timeline.setCycleCount(Animation.INDEFINITE);

		timeline.play();
	}

	@FXML
	private void stopButton()
	{
		stopButton = true;
		outputText.appendText("Auto-play stopped. \n");
		outputText.appendText("\n");
	}

	/**
	 * a sub method to play each step at a time.
	 */
	private void playStackByStep()
	{
		if(!stackSquaresByStep.isEmpty())
		{
			gridDisplay.colorStep(stackSquaresByStep.get(stackSquaresByStep.size()-1));
			stackSquaresByStep.remove(stackSquaresByStep.size()-1);

			topPane.getChildren().clear();
			topPane.getChildren().add(gridDisplay.getDisplay());
			scrollPane.setContent(topPane);

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
			if(stopButton || stackSquaresByStep.isEmpty()){
				timeline.stop();
			}
		}
	}


	/**
	 * allows the user to step through the path taken by the computer when it used queues.
	 * 
	 * @throws FileNotFoundException
	 */
	@FXML
	private void solveQueueByStep() throws FileNotFoundException
	{
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);

		if(!queueSquares.isEmpty())
		{
			gridDisplay.colorStep(queueSquares.get(queueSquares.size()-1));
			queueSquares.remove(queueSquares.size()-1);

			topPane.getChildren().clear();
			topPane.getChildren().add(gridDisplay.getDisplay());
			scrollPane.setContent(topPane);

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);

			mainPanel.setCenter(topCanvas);
		}
		else
		{
			outputText.appendText("No more steps! The solution has been found. "
					+ "Any remaining blocks will not be explored\n");
			outputText.appendText("\n");
		}
	}

	/**
	 * 
	 * allows the user to play the solution when a queue was used.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	@FXML
	private void playQueue() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException
	{

		// deals with resetting the maze each time play 
		// is pressed while the maze is already playing
		displayMaze();
		if(timeline!=null){
			timeline.stop();
			timeline = null;
		}

		if(!stopButton)
		{
			outputText.appendText("Auto-play started. \n");
			outputText.appendText("\n");
		}
		stopButton = false;
		solver = new MazeSolver(fileLocation);
		maze = solver.getMazeLayout();

		gridDisplay = new GridDisplay(height, width);
		gridDisplay.setMaze(maze);


		timeline = new Timeline(new KeyFrame(
				Duration.millis(300),
				ae -> playQueueByStep()));
		timeline.setCycleCount(Animation.INDEFINITE);

		timeline.play();
	}

	/**
	 * a sub method to play each step at a time.
	 */
	private void playQueueByStep()
	{
		if(!queueSquares.isEmpty())
		{
			gridDisplay.colorStep(queueSquares.get(queueSquares.size()-1));
			queueSquares.remove(queueSquares.size()-1);

			topPane.getChildren().clear();
			topPane.getChildren().add(gridDisplay.getDisplay());
			scrollPane.setContent(topPane);

			topCanvas.getChildren().clear();
			topCanvas.getChildren().add(scrollPane);


			mainPanel.setCenter(topCanvas);
			if(stopButton || queueSquares.isEmpty()){
				timeline.stop();
			}
		}
	}

	public void setMainPanel(BorderPane mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * exit the app
	 * @param event - the keyboard shortcut alt+q on mac or ctrl+q on windows
	 * @throws IOException
	 */
	@FXML
	private void exit(ActionEvent event) throws IOException
	{
		Stage stage = (Stage) myMenuBar.getScene().getWindow();
		stage.hide();
	}


	/**
	 * 
	 * go full screen
	 * @param event - the keyboard shortcut alt+f on mac or ctrl+f on windows
	 * @throws IOException
	 */
	@FXML
	private void fullScreen(ActionEvent event) throws IOException
	{
		if(timeline!=null){
			timeline.stop();
		}
		Stage stage = (Stage) myMenuBar.getScene().getWindow();
		stage.setFullScreen(true);
	}


	/**
	 * show the about screen, which contains readme
	 * @param event - the keyboard shortcut alt+a on mac or ctrl+a on windows
	 * @throws IOException
	 */
	@FXML
	private void about(ActionEvent event) throws IOException
	{
		if(timeline!=null){
			timeline.stop();
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
		loader.setController(this.getClass());


		Stage mainStage = new Stage();
		mainStage.setTitle("About Maze Solver");
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.show();
	}

}


