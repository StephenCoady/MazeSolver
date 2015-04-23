package controller;

import java.io.*;
import java.util.*;

import model.*;


/**
 * @author Stephen Coady
 * @date 31-03-15
 *
 * a class to solve a maze using breadth-first (queues) and depth-first (stacks) approaches.
 *
 */

public class MazeSolver 
{
	private Maze maze;
	private String fileLocation;
	private ArrayList<Square> breadthPath = new ArrayList<Square>();
	private ArrayList<Square> depthPath = new ArrayList<Square>();

	public MazeSolver(String fileLocation)
	{
		this.fileLocation = fileLocation;
		try {
			setUp();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setUp() throws FileNotFoundException
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
		maze.findFinishPoint();
		sc.close();
	}

	public Stack<Square> depthFirst()
	{
		maze.clearAllVisits();
		Stack<Square> stack = new Stack<Square>();

		Square startSquare = maze.getStartPoint();
		stack.push(startSquare);
		maze.setVisited(startSquare.getX(), startSquare.getY(), true);
		while(!stack.isEmpty())
		{
			Square square = stack.peek();
			Square neighbour = maze.getNeighbour(square);
			if(neighbour != null)
			{
				maze.setVisited(neighbour.getX(), neighbour.getY(), true);
				stack.push(neighbour);
				depthPath.add(neighbour);
				if(neighbour.getType().equals("*"))
				{
					maze.setFinish(neighbour.getX(), neighbour.getY());
					maze.setHasSolution(true);
					break;
				}
			}
			else
			{
				stack.pop();
			}
		}
		maze.clearAllVisits();
		return stack;
	}


	public ArrayList<Square> breadthFirst()
	{
		maze.clearAllVisits();
		Queue<Square> queue = new LinkedList<Square>();
		Square startSquare = maze.getStartPoint();
		maze.setVisited(startSquare.getX(), startSquare.getY(), true);
		queue.add(startSquare);
		solFound:
			while(!queue.isEmpty()) {
				Square square = queue.remove();
				Square child = null;
				while((child=maze.getNeighbour(square))!=null)
				{
					maze.setVisited(child.getX(), child.getY(), true);
					queue.add(child);
					breadthPath.add(child);
					if(child.getType().equals("*"))
					{
						maze.setFinish(child.getX(), child.getY());
						maze.setHasSolution(true);
						break solFound;
					}
				}
			}
		maze.clearAllVisits();
		return breadthPath;
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


	public Maze getMaze() {
		return maze;
	}


	public void setMaze(Maze maze) {
		this.maze = maze;
	}


	public ArrayList<Square> getDepthPath() {
		return depthPath;
	}

}
