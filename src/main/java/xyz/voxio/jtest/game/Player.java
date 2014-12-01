package xyz.voxio.jtest.game;

import xyz.voxio.jtest.JTest;

public class Player
{
	public static final int		STARTING_SCORE		= 10;
	
	private static final String	SCORE_FORMAT_PREFIX	= "<html><div><p>";
	
	private static final String	SCORE_FORMAT_SUFFIX	= "</p></div></html>";
	
	private int					score				= Player.STARTING_SCORE;

	public void choose(final Question question, final Choice choice)
	{
		JTest.logger.info("Choice:" + choice.toString() + " chosen");
		final boolean correct = question.correct(choice);
		if (correct)
		{
			JTest.logger.info("CORRECT");
			this.correctAnswer();
		}
		else
		{
			JTest.logger.info("INCORRECT");
			this.incorrectAnswer();
		}
		if (this.score <= 0)
		{
			JTest.endGame();
		}
	}
	
	public void correctAnswer()
	{
		this.score++;
	}
	
	/**
	 * @return the score
	 */
	public int getScore()
	{
		return this.score;
	}
	
	public String getScoreFormatted()
	{
		return Player.SCORE_FORMAT_PREFIX + this.score + Player.SCORE_FORMAT_SUFFIX;
	}
	
	public void run()
	{
		JTest.logger.info("RUN selected");
	}
	
	private void incorrectAnswer()
	{
		this.score--;
	}
}
