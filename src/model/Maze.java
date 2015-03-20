package model;

import java.util.ArrayList;

public class Maze 
{
	private int width;
	private int height;
	private Square startSquare;
	private Square finishSquare;
	private String[][] squares;
	private Boolean[][] visitedList;
	private ArrayList<Square> allSquares;

	public Maze(int width, int height)
	{
		this.width = width;
		this.height = height;
		squares = new String[height][width];
		visitedList = new Boolean[height][width];
		allSquares = new ArrayList<Square>();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addSquare(int height, int width, String type)
	{
		squares[height][width] = type;
		setVisited(height, width, false);
		allSquares.add(new Square(height, width, type));
	}

	public String getSquareType(int height, int width) {
		return squares[height][width];
	}

	public Square getSquare(int height, int width)
	{
		for(Square square : allSquares)
		{
			if(square.getX() == height && square.getY() == width)
			{
				return square;
			}
		}
		return null;
	}

	public void setStart(int x, int y)
	{
		startSquare = new Square(x, y, "o");
	}

	//will only be set when the finish is found
	public void setFinish(int x, int y)
	{
		finishSquare = new Square(x, y, "*");
	}

	public void findStartPoint()
	{
		for(int x = 0; x < getHeight(); x++)
		{
			for(int y = 0; y <getWidth(); y++)
			{
				if(getSquareType(x, y).equals("o"))
				{
					setStart(x, y);
					break;
				}
			}
		}
	}

	public void findFinishPoint()
	{
		for(int x = 0; x < getHeight(); x++)
		{
			for(int y = 0; y <getWidth(); y++)
			{
				if(getSquareType(x, y).equals("*"))
				{
					setFinish(x, y);
					break;
				}
			}
		}
	}

	public Square getStartPoint()
	{
		return startSquare;
	}

	public Square getFinishPoint()
	{
		return finishSquare;
	}

	public Square getNeighbour(Square square)
	{
		Square neighbour = null;
		Boolean neighbourFound = false;
		int row = square.getX();
		int col = square.getY();

		//get northern neighbour
		if(row>0 && !neighbourFound)
		{
			if(!getSquareType(row-1, col).equals("#"))
			{
				if(!getVisited(row-1, col))
				{
					neighbour = getSquare(row-1, col);
					neighbourFound = true;
				}
			}
		}

		//get western neighbour
		if(col>0 && !neighbourFound)
		{
			if(!getSquareType(row, col-1).equals("#"))
			{
				if(!getVisited(row, col-1))
				{
					neighbour = getSquare(row, col-1);
					neighbourFound = true;
				}
			}
		}

		//get eastern neighbour
		if(getWidth()>col && !neighbourFound)
		{
			if(!getSquareType(row, col+1).equals("#"))
			{
				if(!getVisited(row, col+1))
				{
					neighbour = getSquare(row, col+1);
					neighbourFound = true;
				}
			}
		}

		//get southern neighbour
		if(getHeight()>row && !neighbourFound)
		{
			if(!getSquareType(row+1, col).equals("#"))
			{
				if(!getVisited(row+1, col))
				{
					neighbour = getSquare(row+1, col);
					neighbourFound = true;
				}
			}
		}

		return neighbour;
	}
	public ArrayList<Square> getListofNeighbours(Square square)
	{
		ArrayList<Square> squares = new ArrayList<Square>();
		int row = square.getX();
		int col = square.getY();

		//get northern neighbour
		if(row>0)
		{
			if(!getSquareType(row-1, col).equals("#"))
			{
				if(!getVisited(row-1, col))
				{
					Square neighbour = getSquare(row-1, col);
					squares.add(neighbour);
				}
			}
		}

		//get southern neighbour
		if(getHeight()>row)
		{
			if(!getSquareType(row+1, col).equals("#"))
			{
				if(!getVisited(row+1, col))
				{
					Square neighbour = getSquare(row+1, col);
					squares.add(neighbour);

				}
			}
		}

		//get western neighbour
		if(col>0)
		{
			if(!getSquareType(row, col-1).equals("#"))
			{
				if(!getVisited(row, col-1))
				{
					Square neighbour = getSquare(row, col-1);
					squares.add(neighbour);

				}
			}
		}

		//get eastern neighbour
		if(getWidth()>col)
		{
			if(!getSquareType(row, col+1).equals("#"))
			{
				if(!getVisited(row, col+1))
				{
					Square neighbour = getSquare(row, col+1);
					squares.add(neighbour);

				}
			}
		}

		return squares;
	}

	public Boolean getVisited(int x, int y) {
		return visitedList[x][y];
	}

	public void setVisited(int x, int y, boolean visited) {
		visitedList[x][y] = visited;
	}

	public void clearAllVisits()
	{
		for(int i = 0; i< height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				setVisited(i, j, false);
			}
		}
	}
}
