package xyz.voxio.jtest;

import java.text.NumberFormat;
import java.util.Locale;

public final class Score
{
	private int	score	= 0;

	public Score(final int i)
	{
		this.score += i;
	}

	public int get()
	{
		return this.score;
	}

	public String getFormatted()
	{
		final NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
		final double score = this.get();
		return fmt.format(score);
	}
}
