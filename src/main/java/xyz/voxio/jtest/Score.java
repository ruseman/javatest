package xyz.voxio.jtest;

import java.text.NumberFormat;
import java.util.Locale;

public final class Score
{
	public Score(final int i)
	{
		score += i;
	}
	
	private int score = 0;
	
	public int get()
	{
		return score;
	}
	
	public String getFormatted()
	{
		NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
		double score = get();
		return fmt.format(score);
	}
}
