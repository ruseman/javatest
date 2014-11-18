package xyz.voxio.jtest.questions;

public class Question
{
	public Question()
	{
		
	}
	
	String prompt;
	String[] answers;
	
	public String getCorrectAnswer()
	{
		return answers[0];
	}
}
