package xyz.voxio.jtest;

import java.util.List;

public class Question
{
	public List<String>	answers;
	
	/**
	 * The prompt for the question
	 */
	public String		prompt;

	public int			value;

	public double getValueInPoints()
	{
		return Double.parseDouble("" + this.value + ",000");
	}
}
