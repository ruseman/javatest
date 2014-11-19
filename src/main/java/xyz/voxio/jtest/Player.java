package xyz.voxio.jtest;

public class Player
{
	public Player()
	{
		score = new Score(0);
	}
	
	private final Score score;
	
	public Score score()
	{
		return score;
	}
	
	public void choose(Choice c)
	{
		
	}
}
