package xyz.voxio.jtest.game;

import java.util.Collections;
import java.util.List;

import xyz.voxio.jtest.Game;

/**
 * @author Tim Miller
 */
public final class Question
{
	private static int		count	= 1;

	private List<String>	answers;
	
	private String			correct;

	private final int		id;

	private String			prompt;
	
	public Question()
	{
		this.id = Question.count;
		Question.count++;
	}

	public boolean correct(final Choice choice)
	{
		return this.correct.equals(this.answers.get(choice.toIndex()));
	}

	public List<String> getAnswers()
	{
		return this.answers;
	}

	public String getCorrectAnswer()
	{
		return this.correct;
	}

	public String getPrompt()
	{
		return this.prompt;
	}

	public String getQuestionFormatted()
	{
		return this.prompt + "\n\nA: " + this.answers.get(0) + "\nB: " + this.answers.get(1) + "\nC: " + this.answers.get(2) + "\nD: " + this.answers.get(3);
	}
	
	void initialize()
	{
		Game.LOGGER.info("Question:" + this.id + " is being initialized");
		this.correct = this.answers.get(0);
		Collections.shuffle(this.answers);
	}
}
