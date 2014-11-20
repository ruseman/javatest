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
		switch(c)
		{
			case A:
				break;
			case B:
				break;
			case C:
				break;
			case D:
				break;
			case RUN:
				break;
		}
	}

	public Score score()
	{
		return this.score;
	}
}
