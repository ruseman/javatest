package xyz.voxio.jtest.game;

import java.awt.EventQueue;
import java.util.Collections;
import java.util.List;

/**
 * @author Tim Miller
 */
public final class Questions
{
	private static final String	SHUFFLE_THREAD_DEFAULT_NAME	= "shuffleThread";
	
	private int					currentQuestionIndex;
	
	private List<Question>		questions;
	
	private int					version;

	public Questions()
	{
		EventQueue.invokeLater(new Thread()
		{
			@Override
			public void run()
			{
				Collections.shuffle(Questions.this.questions);
			}
			
			@Override
			public void start()
			{
				this.setName(Questions.SHUFFLE_THREAD_DEFAULT_NAME);
			}
		});
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
