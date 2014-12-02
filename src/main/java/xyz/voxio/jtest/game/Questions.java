package xyz.voxio.jtest.game;

import java.util.List;

/**
 * @author Tim Miller
 */
public final class Questions
{
	public List<Question>	questions;
	
	private int				currentQuestionIndex;
	
	private int				version;

	/**
	 *
	 */
	public Questions()
	{
// EventQueue.invokeLater(new Thread()
// {
// @Override
// public void run()
// {
// Collections.shuffle(Questions.this.questions);
// for (final Question question : Questions.this.questions)
// {
// question.initialize();
// }
// }
// });
	}

	/**
	 * @return
	 */
	public Question getCurrentQuestion()
	{
		return this.questions.get(this.currentQuestionIndex);
	}
	
	/**
	 * @return
	 */
	public Question getNextQuestion()
	{
		return null;
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
