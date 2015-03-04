package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdOut;
import view.*;
import model.*;


public class MazeSolver 
{
	private Maze maze;
	private String fileLocation;

	public MazeSolver(String fileLocation)
	{
		this.fileLocation = fileLocation;

	}

	public static void main(String[] args) 
	{
		MazeSolver solver = new MazeSolver("src/files/simple_maze.txt");
		try {
			solver.setUp();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setUp() throws FileNotFoundException
	{
		File file = new File(getFileLocation());
		Scanner sc = new Scanner(file);
		int width = sc.nextInt();
		int height = sc.nextInt();
		maze = new Maze(width, height);
		sc.nextLine();

		while (sc.hasNextLine())
		{

			for(int x = 0; x < height; x++)
			{
				String line = sc.nextLine();
				for(int y = 0; y < width; y++)
				{
					Square square = new Square(x, y, Character.toString(line.charAt(y)));
					maze.addSquare(square);
				}
			}
		}
		sc.close();
	}

	
	
	public int[] getDimensions() throws FileNotFoundException
	{
		File file = new File(this.fileLocation);
		Scanner sc = new Scanner(file);
		String[] dimensions = sc.nextLine().split(" ");
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
		}
		int[] nums = {width, height};
		sc.close();
		return nums;
	}

	public ArrayList<String> getMazeLayout() throws FileNotFoundException
	{
		File file = new File(this.fileLocation);
		Scanner sc = new Scanner(file);
		ArrayList<String> mazeLayout = new ArrayList<String>();
		sc.nextLine();
		while(sc.hasNextLine())
		{
			mazeLayout.add(sc.nextLine());
		}

		sc.close();

		return mazeLayout;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}
