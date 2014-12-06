package xyz.voxio.jtest.game;

import java.util.Collections;
import java.util.List;

import xyz.voxio.jtest.Game;

/**
 * The class for a single question
 *
 * @author Tim Miller
 */
public final class Question
{
	/**
	 * An enumerated type for the 4 possible choices for the questions, i.e. A,
	 * B, C, D
	 *
	 * @author Tim Miller
	 */
	public static enum Choice
	{
		A, B, C, D;

		/**
		 * @return the number corresponding to the given choice
		 */
		public int toIndex()
		{
			switch (this)
			{
				case A:
					return 0;
				case B:
					return 1;
				case C:
					return 2;
				case D:
					return 3;
			}
			return 0;
		}
	}
	
	/**
	 * The number of questions, starting at 1
	 */
	private static int		count	= 1;

	/**
	 * The four possible answers, in no particular order
	 */
	private List<String>	answers;
	
	/**
	 * The correct answer
	 */
	private String			correct;

	/**
	 * The numeric id for the question, used in debugging
	 */
	private final int		id;

	/**
	 * The prompt for the question
	 */
	private String			prompt;
	
	public Question()
	{
		this.id = Question.count;
		Question.count++;
	}

	/**
	 * @param choice
	 *            the choice
	 * @return whether or not the choice is correct
	 */
	public boolean correct(final Choice choice)
	{
		return this.correct.equals(this.answers.get(choice.toIndex()));
	}

	/**
	 * @return a list of the answers
	 */
	public List<String> getAnswers()
	{
		return this.answers;
	}

	/**
	 * @return the correct answer
	 */
	public String getCorrectAnswer()
	{
		return this.correct;
	}

	/**
	 * @return the prompt
	 */
	public String getPrompt()
	{
		return this.prompt;
	}

	/**
	 * @return the question in it's fully formatted format, with the answers and
	 *         linebreaks and stuff
	 */
	public String getQuestionFormatted()
	{
		return this.prompt + "\n\nA: " + this.answers.get(0) + "\nB: " + this.answers.get(1) + "\nC: " + this.answers.get(2) + "\nD: " + this.answers.get(3);
	}
	
	/**
	 * Initializes the question object, assigning the correct answer and
	 * shuffling the questions
	 */
	public void initialize()
	{
		Game.LOGGER.info("Question:" + this.id + " is being initialized");
		this.correct = this.answers.get(0);
		Collections.shuffle(this.answers);
	}
}
