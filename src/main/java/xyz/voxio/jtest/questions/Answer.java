package xyz.voxio.jtest.questions;

public class Answer
{
	private String string;
	
	public Answer(String string)
	{
		this.string = string;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Answer)) { return false; }
		Answer other = (Answer) obj;
		if (string == null)
		{
			if (other.string != null) { return false; }
		}
		else if (!string.equals(other.string)) { return false; }
		return true;
	}

	public String toString()
	{
		return string;
	}
}
