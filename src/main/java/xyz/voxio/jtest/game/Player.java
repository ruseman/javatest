package xyz.voxio.jtest.game;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Reason;

public class Player
{
	public static final int	STARTING_SCORE	= 10;
	
	private int				score			= Player.STARTING_SCORE;
	
	public void choose(final Question question, final Choice choice)
	{
		Game.LOGGER.info("Choice:" + choice.toString() + " chosen");
		final boolean correct = question.correct(choice);
		if (correct)
		{
			Game.LOGGER.info("CORRECT");
			this.correctAnswer();
		}
		else
		{
			Game.LOGGER.info("INCORRECT");
			this.incorrectAnswer();
		}
		if (this.score <= 0)
		{
			this.score = 0;
			Game.instance().endGame(Reason.OUT_OF_POINTS);
		}
		Game.instance().getQuestions().nextQuestion();
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
	
	public void run()
	{
		Game.LOGGER.info("RUN selected");
	}
	
	private void incorrectAnswer()
	{
		this.score--;
	}
}
