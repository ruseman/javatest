package xyz.voxio.jtest.game;

import java.awt.EventQueue;
import java.util.Collections;
import java.util.List;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Reason;

/**
 * @author Tim Miller
 */
public final class Questions
{
	public List<Question>	questions;

	private int				index	= 0;
	
	private int				version;
	
	public Questions()
	{
		EventQueue.invokeLater(new Thread()
		{
			@Override
			public void run()
			{
				for (final Question question : Questions.this.questions)
				{
					question.initialize();
				}
				Collections.shuffle(Questions.this.questions);
			}
		});
	}
	
	public Question getCurrentQuestion()
	{
		try
		{
			return this.questions.get(this.index);
		}
		catch (final IndexOutOfBoundsException e)
		{
			this.index--;
			Game.instance().endGame(Reason.NO_MORE_QUESTIONS);
			return this.getCurrentQuestion();
		}
	}

	public List<Question> getQuestion()
	{
		return this.questions;
	}

	public int getVersion()
	{
		return this.version;
	}

	public void nextQuestion()
	{
		this.index++;
	}
}
