package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import edu.princeton.cs.introcs.StdOut;
import view.*;
import model.*;


public class MazeSolver 
{
	private Maze maze;
	private String fileLocation;
	private ArrayList<Square> breadthPath = new ArrayList<Square>();

	public MazeSolver(String fileLocation)
	{
		this.fileLocation = fileLocation;

	}

	public static void main(String[] args) 
	{
		MazeSolver solver = new MazeSolver("src/files/complex_maze.txt");
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
					maze.addSquare(x, y, Character.toString(line.charAt(y)));
				}
			}
		}
		maze.findStartPoint();
		sc.close();
		//depthFirst();
		//breadthFirst();
	}

	public void depthFirst()
	{
		Stack<Square> stack = new Stack<Square>();
		Square startSquare = maze.getStartPoint();
		startSquare.setVisited(true);
		stack.push(startSquare);
		while(!stack.isEmpty())
		{
			Square square = stack.peek();
			square.setVisited(true);
			Square neighbour = maze.getNeighbour(square);
			if(neighbour != null && !neighbour.isVisited())
			{
				neighbour.setVisited(true);
				stack.push(neighbour);
				if(neighbour.getType().equals("*")){
					maze.setFinish(neighbour.getX(), neighbour.getY());
					break;
				}
			}
			else
			{
				stack.pop();
			}
		}
	}

	public void breadthFirst()
	{
		Queue<Square> queue = new LinkedList<Square>();
		Square startSquare = maze.getStartPoint();
		startSquare.setVisited(true);
		queue.add(startSquare);
		while(!queue.isEmpty()) {
			Square square = queue.remove();
			breadthPath.add(square);
			Square child=null;
			while((child = maze.getNeighbour(square))!=null) 
			{
				child.setVisited(true);
				queue.add(child);
			}
		}
	}

	public int[] getDimensions() throws FileNotFoundException
	{
		File file = new File(this.fileLocation);
		Scanner sc = new Scanner(file);
		String[] dimensions = sc.nextLine().split(" ");
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}
