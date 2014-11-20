package xyz.voxio.jtest;

public class Player
{
	private final Score	score;

	public Player()
	{
		this.score = new Score(0);
	}

	public void choose(final Choice c)
	{

	}

	public Score score()
	{
		return this.score;
	}
}
