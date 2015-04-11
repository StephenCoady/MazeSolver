package model;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



/**
 * @author Stephen Coady
 * @date 31-03-15
 * 
 * A custom class to display, repaint and calculate the size of the maze object.
 *
 */

public class GridDisplay{

	private static double ELEMENT_SIZE = 30;
	private static double GAP = ELEMENT_SIZE / 90;

	private TilePane tilePane = new TilePane();
	private Group display = new Group(tilePane);
	private int nRows;
	private int nCols;
	private ArrayList<String> maze = null;
	private static ArrayList<Square> previouslyColoured = new ArrayList<Square>();

	public GridDisplay(int nRows, int nCols) 
	{
		tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
		tilePane.setHgap(GAP);
		tilePane.setVgap(GAP);
		setColumns(nCols);
		setRows(nRows);
		tilePane.setCache(true);
	}

	public void setColumns(int newColumns) 
	{
		nCols = newColumns;
		tilePane.setPrefColumns(nCols);
		createElements();
	}

	public void setRows(int newRows) 
	{
		nRows = newRows;
		tilePane.setPrefRows(nRows);
		createElements();
	}

	public Group getDisplay() 
	{
		return display;
	}

	/**
	 * creates the default maze when no action has been taken to solve it. 
	 * only the plain colours are painted here.
	 * 
	 */
	public void createElements() 
	{
		tilePane.getChildren().clear();
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if(maze==null)
					tilePane.getChildren().add(createElement(Color.RED));
				else
				{
					Color color = Color.WHITE;
					if(Character.toString(maze.get(i).charAt(j)).equals("#"))
						color = Color.BLACK;
					if(Character.toString(maze.get(i).charAt(j)).equals("o"))
						color = Color.GREEN;
					if(Character.toString(maze.get(i).charAt(j)).equals("*"))
						color = Color.RED;
					tilePane.getChildren().add(createElement(color));
				}
			}
		}
	}

	
	/**
	 * @param stack - the list (as a stack) of solution squares. only colours the final path to the solution, 
	 * not every square traveled.
	 */
	public void colorSolved(Stack<Square> stack) 
	{
		ArrayList<Square> squares = new ArrayList<Square>();
		while(!stack.isEmpty())
		{
			squares.add(stack.pop());

		}
		tilePane.getChildren().clear();
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if(maze==null)
					tilePane.getChildren().add(createElement(Color.RED));
				else
				{
					Color color = Color.WHITE;
					if(Character.toString(maze.get(i).charAt(j)).equals("."))
					{
						for(int e = 0; e<squares.size();e++)
						{
							if(squares.get(e).getX() == i && squares.get(e).getY() == j)
							{
								color = Color.YELLOW;
							}
						}
					}
					if(Character.toString(maze.get(i).charAt(j)).equals("#"))
						color = Color.BLACK;
					if(Character.toString(maze.get(i).charAt(j)).equals("o"))
						color = Color.GREEN;
					if(Character.toString(maze.get(i).charAt(j)).equals("*"))
						color = Color.RED;
					tilePane.getChildren().add(createElement(color));
				}
			}
		}
	}
	
	
	/**
	 * @param squares - the arraylist of squares showing the total queue solution.
	 * 
	 * a method to paint the queue solution for the maze.
	 */
	public void colorSolvedQueue(ArrayList<Square> squares) 
	{
		
		tilePane.getChildren().clear();
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if(maze==null)
					tilePane.getChildren().add(createElement(Color.RED));
				else
				{
					Color color = Color.WHITE;
					if(Character.toString(maze.get(i).charAt(j)).equals("."))
					{
						for(int e = 0; e<squares.size();e++)
						{
							if(squares.get(e).getX() == i && squares.get(e).getY() == j)
							{
								color = Color.YELLOW;
							}
						}
					}
					if(Character.toString(maze.get(i).charAt(j)).equals("#"))
						color = Color.BLACK;
					if(Character.toString(maze.get(i).charAt(j)).equals("o"))
						color = Color.GREEN;
					if(Character.toString(maze.get(i).charAt(j)).equals("*"))
						color = Color.RED;
					tilePane.getChildren().add(createElement(color));
				}
			}
		}
	}
	
	
	/**
	 * @param nextSquare - the next square to be coloured on the maze. 
	 * 
	 * colours one square at a time by remembering the list of previously coloured squares maintained in this class
	 * 
	 */
	public void colorStep(Square nextSquare) 
	{
		ArrayList<Square> tempSquares = new ArrayList<Square>();
		previouslyColoured.add(nextSquare);
		for(Square square: previouslyColoured)
		{
			tempSquares.add(square);
		}
		tilePane.getChildren().clear();
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if(maze==null)
					tilePane.getChildren().add(createElement(Color.RED));
				else
				{
					Color color = Color.WHITE;
					if(Character.toString(maze.get(i).charAt(j)).equals("."))
					{
						for(int e = 0; e<tempSquares.size();e++)
						{
							if(tempSquares.get(e).getX() == i && tempSquares.get(e).getY() == j)
							{
								color = Color.YELLOW;
							}
						}
					}
					if(Character.toString(maze.get(i).charAt(j)).equals("#"))
						color = Color.BLACK;
					if(Character.toString(maze.get(i).charAt(j)).equals("o"))
						color = Color.GREEN;
					if(Character.toString(maze.get(i).charAt(j)).equals("*"))
						color = Color.RED;
					tilePane.getChildren().add(createElement(color));
				}
			}
		}
	}


	public void setMaze(ArrayList<String> maze)
	{
		this.maze = maze;
	}

	
	/**
	 * @param color the color to paint the element (square)
	 * @return returns the rectangle to the painting process
	 */
	private Rectangle createElement(Color color) {
		Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(color);
		return rectangle;
	}

	public double getELEMENT_SIZE() {
		return ELEMENT_SIZE;
	}

	public void setELEMENT_SIZE(double eLEMENT_SIZE) {
		ELEMENT_SIZE = eLEMENT_SIZE;
	}

	public double getGAP() {
		return GAP;
	}

	public void setGAP(double gAP) {
		GAP = gAP;
	}
	
	public static void emptyPreviouslyColoured() {
		previouslyColoured.removeAll(previouslyColoured);
	}
}
