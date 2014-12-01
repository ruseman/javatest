package xyz.voxio.jtest.game;

import xyz.voxio.jtest.Game;

public class Player
{
	public static final int		STARTING_SCORE		= 10;
	
	private static final String	SCORE_FORMAT_PREFIX	= "<html><div><p>";
	
	private static final String	SCORE_FORMAT_SUFFIX	= "</p></div></html>";
	
	private int					score				= Player.STARTING_SCORE;

	public void choose(final Question question, final Choice choice)
	{
		Game.logger.info("Choice:" + choice.toString() + " chosen");
		final boolean correct = question.correct(choice);
		if (correct)
		{
			Game.logger.info("CORRECT");
			this.correctAnswer();
		}
		else
		{
			Game.logger.info("INCORRECT");
			this.incorrectAnswer();
		}
		if (this.score <= 0)
		{
			Game.instance().endGame();
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
		return Player.SCORE_FORMAT_PREFIX + this.score
				+ Player.SCORE_FORMAT_SUFFIX;
	}
	
	public void run()
	{
		Game.logger.info("RUN selected");
	}
	
	private void incorrectAnswer()
	{
		this.score--;
	}
}
