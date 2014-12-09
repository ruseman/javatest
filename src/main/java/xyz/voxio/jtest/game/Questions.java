package xyz.voxio.jtest.game;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Game.Reason;

/**
 * The container object for all questions, generated from the JSON, with the
 * appropriate version metadata.
 *
 * @author Tim Miller
 */
public final class Questions
{
	/**
	 * The questions
	 */
	public List<Question>	questions;

	/**
	 * The current question
	 */
	private int				index	= 0;
	
	/**
	 * The version
	 */
	private int				version;
	
	public Questions()
	{
		
	}

	/**
	 * @return the current question number
	 */
	public int getCurrentNum()
	{
		return this.index + 1;
	}
	
	/**
	 * @return the current question
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Question getCurrentQuestion() throws IOException, URISyntaxException
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
	
	/**
	 * @return the questions
	 */
	public List<Question> getQuestion()
	{
		return this.questions;
	}

	/**
	 * @return the total number of questions
	 */
	public int getTotalNum()
	{
		return this.questions.size();
	}

	/**
	 * @return the version
	 */
	public int getVersion()
	{
		return this.version;
	}

	/**
	 * Moves the index to the next question
	 */
	public void nextQuestion()
	{
		this.index++;
	}
}
