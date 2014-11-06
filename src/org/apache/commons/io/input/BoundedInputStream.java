/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a stream that will only supply bytes up to a certain length - if its
 * position goes above that, it will stop.
 * <p>
 * This is useful to wrap ServletInputStreams. The ServletInputStream will block
 * if you try to read content from it that isn't there, because it doesn't know
 * whether the content hasn't arrived yet or whether the content has finished.
 * So, one of these, initialized with the Content-length sent in the
 * ServletInputStream's header, will stop it blocking, providing it's been sent
 * with a correct content length.
 *
 * @version $Id: BoundedInputStream.java 1307462 2012-03-30 15:13:11Z ggregory $
 * @since 2.0
 */
public class BoundedInputStream extends InputStream
{
	
	/** the wrapped input stream */
	private final InputStream	in;
	
	/** the marked position */
	private long				mark			= -1;
	
	/** the max length to provide */
	private final long			max;
	
	/** the number of bytes already returned */
	private long				pos				= 0;
	
	/** flag if close shoud be propagated */
	private boolean				propagateClose	= true;
	
	/**
	 * Creates a new <code>BoundedInputStream</code> that wraps the given input
	 * stream and is unlimited.
	 *
	 * @param in
	 *            The wrapped input stream
	 */
	public BoundedInputStream(final InputStream in)
	{
		this(in, -1);
	}
	
	/**
	 * Creates a new <code>BoundedInputStream</code> that wraps the given input
	 * stream and limits it to a certain size.
	 *
	 * @param in
	 *            The wrapped input stream
	 * @param size
	 *            The maximum number of bytes to return
	 */
	public BoundedInputStream(final InputStream in, final long size)
	{
		// Some badly designed methods - eg the servlet API - overload length
		// such that "-1" means stream finished
		this.max = size;
		this.in = in;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int available() throws IOException
	{
		if ((this.max >= 0) && (this.pos >= this.max)) { return 0; }
		return this.in.available();
	}
	
	/**
	 * Invokes the delegate's <code>close()</code> method
	 * if {@link #isPropagateClose()} is {@code true}.
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public void close() throws IOException
	{
		if (this.propagateClose)
		{
			this.in.close();
		}
	}
	
	/**
	 * Indicates whether the {@link #close()} method
	 * should propagate to the underling {@link InputStream}.
	 *
	 * @return {@code true} if calling {@link #close()} propagates to the
	 *         <code>close()</code> method of the
	 *         underlying stream or {@code false} if it does not.
	 */
	public boolean isPropagateClose()
	{
		return this.propagateClose;
	}
	
	/**
	 * Invokes the delegate's <code>mark(int)</code> method.
	 * 
	 * @param readlimit
	 *            read ahead limit
	 */
	@Override
	public synchronized void mark(final int readlimit)
	{
		this.in.mark(readlimit);
		this.mark = this.pos;
	}
	
	/**
	 * Invokes the delegate's <code>markSupported()</code> method.
	 * 
	 * @return true if mark is supported, otherwise false
	 */
	@Override
	public boolean markSupported()
	{
		return this.in.markSupported();
	}
	
	/**
	 * Invokes the delegate's <code>read()</code> method if
	 * the current position is less than the limit.
	 * 
	 * @return the byte read or -1 if the end of stream or
	 *         the limit has been reached.
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public int read() throws IOException
	{
		if ((this.max >= 0) && (this.pos >= this.max)) { return -1; }
		final int result = this.in.read();
		this.pos++;
		return result;
	}
	
	/**
	 * Invokes the delegate's <code>read(byte[])</code> method.
	 * 
	 * @param b
	 *            the buffer to read the bytes into
	 * @return the number of bytes read or -1 if the end of stream or
	 *         the limit has been reached.
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public int read(final byte[] b) throws IOException
	{
		return this.read(b, 0, b.length);
	}
	
	/**
	 * Invokes the delegate's <code>read(byte[], int, int)</code> method.
	 * 
	 * @param b
	 *            the buffer to read the bytes into
	 * @param off
	 *            The start offset
	 * @param len
	 *            The number of bytes to read
	 * @return the number of bytes read or -1 if the end of stream or
	 *         the limit has been reached.
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public int read(final byte[] b, final int off, final int len)
			throws IOException
	{
		if ((this.max >= 0) && (this.pos >= this.max)) { return -1; }
		final long maxRead = this.max >= 0 ? Math.min(len, this.max - this.pos)
				: len;
		final int bytesRead = this.in.read(b, off, (int) maxRead);
		
		if (bytesRead == -1) { return -1; }
		
		this.pos += bytesRead;
		return bytesRead;
	}
	
	/**
	 * Invokes the delegate's <code>reset()</code> method.
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public synchronized void reset() throws IOException
	{
		this.in.reset();
		this.pos = this.mark;
	}
	
	/**
	 * Set whether the {@link #close()} method
	 * should propagate to the underling {@link InputStream}.
	 *
	 * @param propagateClose
	 *            {@code true} if calling {@link #close()} propagates to the
	 *            <code>close()</code> method of the underlying stream or
	 *            {@code false} if it does not.
	 */
	public void setPropagateClose(final boolean propagateClose)
	{
		this.propagateClose = propagateClose;
	}
	
	/**
	 * Invokes the delegate's <code>skip(long)</code> method.
	 * 
	 * @param n
	 *            the number of bytes to skip
	 * @return the actual number of bytes skipped
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	public long skip(final long n) throws IOException
	{
		final long toSkip = this.max >= 0 ? Math.min(n, this.max - this.pos)
				: n;
		final long skippedBytes = this.in.skip(toSkip);
		this.pos += skippedBytes;
		return skippedBytes;
	}
	
	/**
	 * Invokes the delegate's <code>toString()</code> method.
	 * 
	 * @return the delegate's <code>toString()</code>
	 */
	@Override
	public String toString()
	{
		return this.in.toString();
	}
}
