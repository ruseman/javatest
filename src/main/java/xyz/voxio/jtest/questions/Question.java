package xyz.voxio.jtest.questions;

import xyz.voxio.jtest.JTest;

public class Question
{
	String[]	answers;
	
	String		prompt;

	public Question()
	{
		
	}
	
	public String[] getAnswersInRandomOrder()
	{
		return JTest.shuffleArray(this.answers);
	}

	public String getCorrectAnswer()
	{
		return this.answers[0];
	}
}
