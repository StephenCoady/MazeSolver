package view;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridDisplay {

	private static final double ELEMENT_SIZE = 30;
	private static final double GAP = ELEMENT_SIZE / 90;

	private TilePane tilePane = new TilePane();
	private Group display = new Group(tilePane);
	private int nRows;
	private int nCols;
	private ArrayList<String> maze = null;

	public GridDisplay(int nRows, int nCols) 
	{
		tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
		tilePane.setHgap(GAP);
		tilePane.setVgap(GAP);
		setColumns(nCols);
		setRows(nRows);
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

	public void setMaze(ArrayList<String> maze)
	{
		this.maze = maze;
	}

	private Rectangle createElement(Color color) {
		Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(color);

		return rectangle;
	}
}
