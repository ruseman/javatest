package xyz.voxio.jtest.game;

import java.util.List;

/**
 * @author Tim Miller
 */
public final class Questions
{
	private List<Question>	questions;

	private int				version;

	private Questions()
	{

	}
	
	public List<Question> getQuestion()
	{
		return this.questions;
	}

	/**
	 * @return the version
	 */
	public int getVersion()
	{
		return this.version;
	}
}
