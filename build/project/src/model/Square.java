package model;

public class Square 
{
	private int x;
	private int y;
	private String type;
	private boolean visited;
	
	public Square(int x, int y, String type)
	{
		setX(x);
		setY(y);
		setType(type);
		setVisited(false);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isVisited()
	{
		return this.visited;
	}
	
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
}
