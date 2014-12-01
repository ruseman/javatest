package xyz.voxio.jtest.game;

import java.awt.EventQueue;
import java.util.Collections;
import java.util.List;

/**
 * @author Tim Miller
 */
public final class Question
{
	private List<String>	answers;
	
	private String			correctAnswer;
	
	public Question()
	{
		EventQueue.invokeLater(new Thread()
		{
			@Override
			public void run()
			{
				Question.this.correctAnswer = Question.this.answers.get(0);
				Collections.shuffle(Question.this.answers);
			}
		});
	}
	
	public boolean correct(final Choice choice)
	{
		Integer ind = null;
		switch (choice)
		{
			case A:
				ind = 0;
				break;
			case B:
				ind = 1;
				break;
			case C:
				ind = 2;
				break;
			case D:
				ind = 3;
				break;
		}
		return this.answers.get(ind).equals(this.correctAnswer);
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
		return this.correctAnswer;
	}

	public String getQuestionFormatted()
	{
		return ""; // TODO
	}
}
