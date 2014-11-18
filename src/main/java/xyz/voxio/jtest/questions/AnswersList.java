package xyz.voxio.jtest.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AnswersList implements List<Answer>, Iterable<Answer>
{
	private final List<Answer>	list	= new ArrayList<Answer>();

	@Override
	public boolean add(final Answer e)
	{
		return this.list.add(e);
	}

	@Override
	public void add(final int index, final Answer element)
	{
		this.list.add(index, element);
	}

	@Override
	public boolean addAll(final Collection<? extends Answer> c)
	{
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Answer> c)
	{
		return this.list.addAll(index, c);
	}

	@Override
	public void clear()
	{
		this.list.clear();
	}

	@Override
	public boolean contains(final Object o)
	{
		return this.list.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c)
	{
		return this.list.containsAll(c);
	}

	@Override
	public Answer get(final int index)
	{
		return this.list.get(index);
	}

	@Override
	public int indexOf(final Object o)
	{
		return this.list.indexOf(o);
	}

	@Override
	public boolean isEmpty()
	{
		return this.list.isEmpty();
	}

	@Override
	public Iterator<Answer> iterator()
	{
		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(final Object o)
	{
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Answer> listIterator()
	{
		return this.list.listIterator();
	}

	@Override
	public ListIterator<Answer> listIterator(final int index)
	{
		return this.list.listIterator(index);
	}

	@Override
	public Answer remove(final int index)
	{
		return this.list.remove(index);
	}

	@Override
	public boolean remove(final Object o)
	{
		return this.list.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c)
	{
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c)
	{
		return this.list.retainAll(c);
	}

	@Override
	public Answer set(final int index, final Answer element)
	{
		return this.list.set(index, element);
	}

	@Override
	public int size()
	{
		return this.list.size();
	}

	@Override
	public List<Answer> subList(final int fromIndex, final int toIndex)
	{
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray()
	{
		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a)
	{
		return this.list.toArray(a);
	}
	
}
