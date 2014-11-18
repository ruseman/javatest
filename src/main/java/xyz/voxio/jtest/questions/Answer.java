package xyz.voxio.jtest.questions;

public class Answer
{
	private final String	string;

	public Answer(final String string)
	{
		this.string = string;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Answer)) { return false; }
		final Answer other = (Answer) obj;
		if (this.string == null)
		{
			if (other.string != null) { return false; }
		}
		else if (!this.string.equals(other.string)) { return false; }
		return true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.string == null) ? 0 : this.string.hashCode());
		return result;
	}
	
	@Override
	public String toString()
	{
		return this.string;
	}
}
