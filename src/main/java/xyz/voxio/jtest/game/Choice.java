package xyz.voxio.jtest.game;

public enum Choice
{
	A, B, C, D;
	
	public int toIndex()
	{
		switch (this)
		{
			case A:
				return 0;
			case B:
				return 1;
			case C:
				return 2;
			case D:
				return 3;
		}
		return 0;
	}
}
