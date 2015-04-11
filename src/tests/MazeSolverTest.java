package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.*;

import model.Square;

import org.junit.*;
import controller.MazeSolver;

/**
 * @author Stephen Coady
 * 
 * JUnit tests to test basic functionality of the maze solver class
 *
 */
public class MazeSolverTest {

	private MazeSolver solver;

	@Before
	public void setUp() throws FileNotFoundException
	{
		solver = new MazeSolver("src/files/maze2.txt");
	}

	@Test
	public void testMaze()
	{
		assertEquals(solver.getMaze().getHeight(), 10);
		assertEquals(solver.getMaze().getWidth(), 12);
	}

	@Test
	public void testStack()
	{
		assertTrue(solver.depthFirst().size()>0);
		assertTrue(solver.depthFirst().size()<=(solver.getMaze().getHeight()*solver.getMaze().getWidth()));
		assertTrue(solver.depthFirst().contains(solver.getMaze().getStartPoint()));

		//ensures stack contains only valid points
		Stack<Square> stack = solver.depthFirst();
		while(!stack.isEmpty())
		{
			Square square = stack.pop();
			if(!square.getType().equals("o") && !square.getType().equals("*"))
			{
				assertTrue(square.getType().equals("."));
			}
		}
	}
	
	@Test
	public void testQueue()
	{
		assertTrue(solver.breadthFirst().size()>0);
		assertTrue(solver.breadthFirst().size()<=(solver.getMaze().getHeight()*solver.getMaze().getWidth()));
		
		//ensures queue contains only valid points
		ArrayList<Square> queue = solver.breadthFirst();
		while(!queue.isEmpty())
		{
			Square square = queue.remove(queue.size()-1);
			if(!square.getType().equals("o") && !square.getType().equals("*"))
			{
				assertTrue(square.getType().equals("."));
			}
		}
	}
}
