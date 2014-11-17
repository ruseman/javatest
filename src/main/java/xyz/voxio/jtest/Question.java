package xyz.voxio.jtest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Question implements Map<String, String>
{
	private final Map<String, String>	data	= new HashMap<String, String>();

	public Question(final Map<? extends String, ? extends String> map)
	{
		this.putAll(map);
	}

	public Question(final String prompt, final String answer,
			final String false0, final String false1, final String false2)
	{
		this.put("prompt", prompt);
		this.put("answer", answer);
		this.put("false0", false0);
		this.put("false1", false1);
		this.put("false2", false2);
	}

	public Question(final String[] data)
	{
		this(data[0], data[1], data[2], data[3], data[4]);
	}
	
	@Override
	public void clear()
	{
		this.data().clear();
	}

	@Override
	public boolean containsKey(final Object key)
	{
		return this.data().containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value)
	{
		return this.data().containsValue(value);
	}
	
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet()
	{
		return this.data().entrySet();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (this.getClass() != obj.getClass()) { return false; }
		final Question other = (Question) obj;
		if (this.data == null)
		{
			if (other.data != null) { return false; }
		}
		else if (!this.data.equals(other.data)) { return false; }
		return true;
	}
	
	@Override
	public String get(final Object key)
	{
		return this.data().get(key);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.data == null) ? 0 : this.data.hashCode());
		return result;
	}
	
	@Override
	public boolean isEmpty()
	{
		return this.data().isEmpty();
	}
	
	@Override
	public Set<String> keySet()
	{
		return this.data().keySet();
	}
	
	@Override
	public String put(final String key, final String value)
	{
		return this.data().put(key, value);
	}
	
	@Override
	public void putAll(final Map<? extends String, ? extends String> m)
	{
		this.data().putAll(m);
	}
	
	@Override
	public String remove(final Object key)
	{
		return this.data().remove(key);
	}
	
	@Override
	public int size()
	{
		return this.data().size();
	}
	
	@Override
	public String toString()
	{
		return "Question [data=" + this.data + "]";
	}
	
	@Override
	public Collection<String> values()
	{
		return this.data().values();
	}

	private Map<String, String> data()
	{
		return this.data;
	}
}
