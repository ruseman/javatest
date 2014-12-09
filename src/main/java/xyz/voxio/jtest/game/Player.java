package xyz.voxio.jtest.game;

import java.io.IOException;
import java.net.URISyntaxException;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Game.Reason;
import xyz.voxio.jtest.game.Question.Choice;

/**
 * The class for the player object. The class holds the player's score, as well
 * as the choose(Question, Choice) method, and all related methods.
 * This could've/should've been done as a singleton. I don't feel bad
 *
 * @author Tim Miller
 */
public final class Player
{
	/**
	 * The starting score
	 */
	public static final int	STARTING_SCORE	= 10;

	/**
	 * The score
	 */
	private int				score			= Player.STARTING_SCORE;

	/**
	 * Determines whether or not the choice was correct, and performs the
	 * appropriate actions
	 *
	 * @param question
	 *            the question
	 * @param choice
	 *            the choice
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void choose(final Question question, final Choice choice)
			throws IOException, URISyntaxException
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

	/**
	 * Increments the score by 1
	 */
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

	/**
	 * Part of the unfinished RUN feature
	 */
	public void run()
	{
		Game.LOGGER.info("RUN selected");
	}

	/**
	 * Decrements the score
	 */
	private void incorrectAnswer()
	{
		this.score--;
	}
}
