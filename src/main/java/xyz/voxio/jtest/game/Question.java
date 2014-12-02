package xyz.voxio.jtest.game;

import java.util.Collections;
import java.util.List;

/**
 * @author Tim Miller
 */
public final class Question
{
	private List<String>	answers;

	private String			correct;
	
	private String			prompt;
	
	public Question()
	{
		
	}

	public boolean correct(final Choice choice)
	{
		return this.correct.equals(this.answers.get(choice.toIndex()));
	}

	/**
	 * @return the answers
	 */
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
		return this.prompt + "\n\nA: " + this.answers.get(0) + "\nB: "
				+ this.answers.get(1) + "\nC: " + this.answers.get(2) + "\nD: "
				+ this.answers.get(3);
	}

	public void shuffle()
	{
		
	}
	
	void initialize()
	{
		this.correct = this.answers.get(0);
		Collections.shuffle(this.answers);
	}
}
