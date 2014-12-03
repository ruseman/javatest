package xyz.voxio.jtest.game;

import java.util.List;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Game.Reason;

/**
 * @author Tim Miller
 */
public final class Questions
{
	public List<Question>	questions;
	
	private int				index	= 0;

	private int				version;

	public Questions()
	{

	}
	
	public int getCurrentNum()
	{
		return this.index + 1;
	}

	public Question getCurrentQuestion()
	{
		try
		{
			return this.questions.get(this.index);
		}
		catch (final IndexOutOfBoundsException e)
		{
			this.index--;
			Game.instance().endGame(Reason.NO_MORE_QUESTIONS);
			return this.getCurrentQuestion();
		}
	}

	public List<Question> getQuestion()
	{
		return this.questions;
	}
	
	public int getTotalNum()
	{
		return this.questions.size();
	}
	
	public int getVersion()
	{
		return this.version;
	}
	
	public void nextQuestion()
	{
		this.index++;
	}
}
