package xyz.voxio.jtest;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public final class Question
{
	public static final String	SEP			= "|";

	public static final char	SEP_CHAR	= '|';
	
	private final String		answer;

	private final String		dummy0;

	private final String		dummy1;

	private final String		dummy2;
	
	private final Set<Integer>	order;
	
	private final String		question;
	
	private final Random		rng			= new Random();
	
	public Question(final String qstring) throws Exception
	{
		this(qstring.split("|"));
	}
	
	public Question(final String question, final String answer,
			final String dummy0, final String dummy1, final String dummy2)
					throws Exception
	{
		this.question = question;
		this.answer = answer;
		this.dummy0 = dummy0;
		this.dummy1 = dummy1;
		this.dummy2 = dummy2;
		this.order = this.getNewOrder();
	}
	
	public Question(final String[] set) throws Exception
	{
		this(set[0], set[1], set[2], set[3], set[4]);
	}

	public String[] asStringArray()
	{
		final String[] sa = new String[5];
		sa[0] = this.question;
		sa[1] = this.answer;
		sa[2] = this.dummy0;
		sa[3] = this.dummy1;
		sa[4] = this.dummy2;
		return sa;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (this.getClass() != obj.getClass()) { return false; }
		final Question other = (Question) obj;
		if (this.answer == null)
		{
			if (other.answer != null) { return false; }
		}
		else if (!this.answer.equals(other.answer)) { return false; }
		if (this.dummy0 == null)
		{
			if (other.dummy0 != null) { return false; }
		}
		else if (!this.dummy0.equals(other.dummy0)) { return false; }
		if (this.dummy1 == null)
		{
			if (other.dummy1 != null) { return false; }
		}
		else if (!this.dummy1.equals(other.dummy1)) { return false; }
		if (this.dummy2 == null)
		{
			if (other.dummy2 != null) { return false; }
		}
		else if (!this.dummy2.equals(other.dummy2)) { return false; }
		if (this.question == null)
		{
			if (other.question != null) { return false; }
		}
		else if (!this.question.equals(other.question)) { return false; }
		return true;
	}
	
	public int genRandInt()
	{
		final Random rand = new Random();
		return rand.nextInt(3);
	}
	
	public String getAnswer()
	{
		return this.answer;
	}

	public String getDummy0()
	{
		return this.dummy0;
	}
	
	public String getDummy1()
	{
		return this.dummy1;
	}
	
	public String getDummy2()
	{
		return this.dummy2;
	}
	
	public Set<Integer> getNewOrder() throws Exception
	{
		final Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < 4)
		{
			final Integer next = this.rng.nextInt(4) + 1;
			generated.add(next);
		}
		return generated;
	}
	
	public Integer[] getOrder()
	{
		return (Integer[]) this.order.toArray();
	}
	
	public String getQuestion()
	{
		return this.question;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.answer == null) ? 0 : this.answer.hashCode());
		result = (prime * result)
				+ ((this.dummy0 == null) ? 0 : this.dummy0.hashCode());
		result = (prime * result)
				+ ((this.dummy1 == null) ? 0 : this.dummy1.hashCode());
		result = (prime * result)
				+ ((this.dummy2 == null) ? 0 : this.dummy2.hashCode());
		result = (prime * result)
				+ ((this.question == null) ? 0 : this.question.hashCode());
		return result;
	}

	@Override
	public String toString()
	{
		return "Question [question=" + this.question + ", answer="
				+ this.answer
				+ ", dummy0=" + this.dummy0 + ", dummy1=" + this.dummy1
				+ ", dummy2="
				+ this.dummy2 + "]";
	}
}
