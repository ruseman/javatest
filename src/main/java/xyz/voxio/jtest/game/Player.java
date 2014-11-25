package xyz.voxio.jtest.game;

public class Player
{
	public void run()
	{
		
	}
	
	public void choose(Choice choice)
	{
		choice: switch(choice)
		{
			case RUN:
				this.run();
				break choice;
			default:
				this.selectAnswer(choice);
				break choice;
		}
	}
	
	private void selectAnswer(Choice choice)
	{
		
	}
}
