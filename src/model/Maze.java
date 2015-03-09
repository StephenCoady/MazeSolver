package model;

import java.util.ArrayList;

public class Maze 
{
	private int width;
	private int height;
	private Square startSquare;
	private Square finishSquare;
	private String[][] squares;
	private ArrayList<Square> allSquares;
	
	public Maze(int width, int height)
	{
		this.width = width;
		this.height = height;
		squares = new String[height][width];
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
				if(!getSquare(row-1, col).isVisited())
				{
					neighbour = getSquare(row-1, col);
					neighbourFound = true;
				}
			}
		}

		//get southern neighbour
		if(getHeight()>row && !neighbourFound)
		{
			if(!getSquareType(row+1, col).equals("#"))
			{
				if(!getSquare(row+1, col).isVisited())
				{
					neighbour = getSquare(row+1, col);
					neighbourFound = true;
				}
			}
		}

		//get western neighbour
		if(col>0 && !neighbourFound)
		{
			if(!getSquareType(row, col-1).equals("#"))
			{
				if(!getSquare(row, col-1).isVisited())
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
				if(!getSquare(row, col+1).isVisited())
				{
					neighbour = getSquare(row, col+1);
					neighbourFound = true;
				}
			}
		}

		return neighbour;
	}
}
