package model;

import java.util.ArrayList;

public class Maze 
{
	private int width;
	private int height;
	private ArrayList<Square> squares = new ArrayList<Square>(); 
	
	public Maze(int width, int height)
	{
		this.width = width;
		this.height = height;
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
	
	public void addSquare(Square square)
	{
		squares.add(square);
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}
	
	
}
