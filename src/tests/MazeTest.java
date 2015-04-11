package tests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import model.*;

import org.junit.*;

/**
 * @author Stephen Coady
 * 
 * JUnit tests to test basic functionality of the maze model.
 *
 */

public class MazeTest 
{

	private Maze maze;
	private int width;
	private int height;


	@Before
	public void setUp() throws FileNotFoundException
	{
		File file = new File("src/files/maze2.txt");
		Scanner sc = new Scanner(file);
		width = sc.nextInt();
		height = sc.nextInt();
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

	@Test
	public void testDimmensions() {
		assertTrue(maze.getHeight() == height);
		assertTrue(maze.getWidth() == width);
	}

	@Test
	public void testSquares() {
		assertTrue(maze.getAllSquares().size()==(height*width));

		//ensure start point is (8,1) (known)
		assertTrue(maze.getStartPoint().getX()==8);
		assertTrue(maze.getStartPoint().getY()==1);

		//ensure finish point is (4,6) (known)
		assertTrue(maze.getFinishPoint().getX()==4);
		assertTrue(maze.getFinishPoint().getY()==6);
	}


	@Test
	public void testNeighbours()
	{
		//ensure a point which is know to only have 2 neighbours only has 2
		ArrayList<Square> neighbours = new ArrayList<Square>();
		Square square2 = new Square(1, 6, ".");
		maze.setVisited(1, 6, true);
		for(int i = 0; i < 4; i++)
		{
			Square neighbour2 = maze.getNeighbour(square2);
			if(neighbour2!=null)
			{
				neighbours.add(neighbour2);
				maze.setVisited(neighbour2.getX(), neighbour2.getY(), true);
			}
		}
		
		//we know the coordinates of both neighbours, check if they are (1,5) & (1,7)
		for(int j = 0; j< neighbours.size();j++)
		{
			assertTrue(neighbours.get(j).getX() == 1);
			assertTrue(neighbours.get(j).getY() == 5 || neighbours.get(j).getY() == 7);
		}
		maze.clearAllVisits();
	}

	@Test
	public void testAmountOfNeighbours()
	{
		//ensure a square which cannot have neighbours (corner point) doesn't have any
		Square square = new Square(0, 0, "#");
		Square neighbour = maze.getNeighbour(square);
		assertTrue(neighbour==null);


		//ensure a point which is know to only have 2 neighbours only has 2
		ArrayList<Square> neighbours = new ArrayList<Square>();
		Square square2 = new Square(1, 6, ".");
		maze.setVisited(1, 6, true);
		for(int i = 0; i < 4; i++)
		{
			Square neighbour2 = maze.getNeighbour(square2);
			if(neighbour2!=null)
			{
				neighbours.add(neighbour2);
				maze.setVisited(neighbour2.getX(), neighbour2.getY(), true);
			}
		}
		//we know (1,6) only has 2 valid neighbours
		assertTrue(neighbours.size()==2);
		maze.clearAllVisits();
	}
}
