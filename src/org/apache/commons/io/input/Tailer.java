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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Simple implementation of the unix "tail -f" functionality.
 * <p>
 * <h2>1. Create a TailerListener implementation</h3>
 * <p>
 * First you need to create a {@link TailerListener} implementation (
 * {@link TailerListenerAdapter} is provided for convenience so that you don't
 * have to implement every method).
 * </p>
 * <p>
 * For example:
 * </p>
 * 
 * <pre>
 * public class MyTailerListener extends TailerListenerAdapter
 * {
 * 	public void handle(String line)
 * 	{
 * 		System.out.println(line);
 * 	}
 * }
 * </pre>
 * <h2>2. Using a Tailer</h2> You can create and use a Tailer in one of three
 * ways:
 * <ul>
 * <li>Using one of the static helper methods:
 * <ul>
 * <li>{@link Tailer#create(File, TailerListener)}</li>
 * <li>{@link Tailer#create(File, TailerListener, long)}</li>
 * <li>{@link Tailer#create(File, TailerListener, long, boolean)}</li>
 * </ul>
 * </li>
 * <li>Using an {@link java.util.concurrent.Executor}</li>
 * <li>Using an {@link Thread}</li>
 * </ul>
 * An example of each of these is shown below.
 * <h3>2.1 Using the static helper method</h3>
 *
 * <pre>
 * TailerListener	listener	= new MyTailerListener();
 * 														
 * 														Tailer	tailer	= Tailer.create(
 * 																				file,
 * 																				listener,
 * 																				delay);
 * </pre>
 * 
 * <h3>2.2 Use an Executor</h3>
 *
 * <pre>
 * TailerListener listener = new MyTailerListener();
 * Tailer tailer = new Tailer(file, listener, delay);
 * 
 * // stupid executor impl. for demo purposes
 * Executor executor = new Executor()
 * {
 * 	public void execute(Runnable command)
 * 	{
 * 		command.run();
 * 	}
 * };
 * 
 * executor.execute(tailer);
 * </pre>
 * 
 * <h3>2.3 Use a Thread</h3>
 * 
 * <pre>
 * TailerListener listener = new MyTailerListener();
 * Tailer tailer = new Tailer(file, listener, delay);
 * Thread thread = new Thread(tailer);
 * thread.setDaemon(true); // optional
 * thread.start();
 * </pre>
 *
 * <h2>3. Stop Tailing</h3>
 * <p>
 * Remember to stop the tailer when you have done with it:
 * </p>
 * 
 * <pre>
 * tailer.stop();
 * </pre>
 *
 * @see TailerListener
 * @see TailerListenerAdapter
 * @version $Id: Tailer.java 1348698 2012-06-11 01:09:58Z ggregory $
 * @since 2.0
 */
public class Tailer implements Runnable
{
	
	private static final int	DEFAULT_BUFSIZE			= 4096;
	
	private static final int	DEFAULT_DELAY_MILLIS	= 1000;
	
	private static final String	RAF_MODE				= "r";
	
	/**
	 * Creates and starts a Tailer for the given file, starting at the beginning
	 * of the file
	 * with the default delay of 1.0s
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener)
	{
		return Tailer
				.create(file, listener, Tailer.DEFAULT_DELAY_MILLIS, false);
	}
	
	/**
	 * Creates and starts a Tailer for the given file, starting at the beginning
	 * of the file
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener,
			final long delayMillis)
	{
		return Tailer.create(file, listener, delayMillis, false);
	}
	
	/**
	 * Creates and starts a Tailer for the given file with default buffer size.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener,
			final long delayMillis, final boolean end)
	{
		return Tailer.create(file, listener, delayMillis, end,
				Tailer.DEFAULT_BUFSIZE);
	}
	
	/**
	 * Creates and starts a Tailer for the given file with default buffer size.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param reOpen
	 *            whether to close/reopen the file between chunks
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final boolean reOpen)
	{
		return Tailer.create(file, listener, delayMillis, end, reOpen,
				Tailer.DEFAULT_BUFSIZE);
	}
	
	/**
	 * Creates and starts a Tailer for the given file.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param reOpen
	 *            whether to close/reopen the file between chunks
	 * @param bufSize
	 *            buffer size.
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final boolean reOpen,
			final int bufSize)
	{
		final Tailer tailer = new Tailer(file, listener, delayMillis, end,
				reOpen, bufSize);
		final Thread thread = new Thread(tailer);
		thread.setDaemon(true);
		thread.start();
		return tailer;
	}
	
	/**
	 * Creates and starts a Tailer for the given file.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param bufSize
	 *            buffer size.
	 * @return The new tailer
	 */
	public static Tailer create(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final int bufSize)
	{
		final Tailer tailer = new Tailer(file, listener, delayMillis, end,
				bufSize);
		final Thread thread = new Thread(tailer);
		thread.setDaemon(true);
		thread.start();
		return tailer;
	}
	
	/**
	 * The amount of time to wait for the file to be updated.
	 */
	private final long				delayMillis;
	
	/**
	 * Whether to tail from the end or start of file
	 */
	private final boolean			end;
	
	/**
	 * The file which will be tailed.
	 */
	private final File				file;
	
	/**
	 * Buffer on top of RandomAccessFile.
	 */
	private final byte				inbuf[];
	
	/**
	 * The listener to notify of events when tailing.
	 */
	private final TailerListener	listener;
	
	/**
	 * Whether to close and reopen the file whilst waiting for more input.
	 */
	private final boolean			reOpen;
	
	/**
	 * The tailer will run as long as this value is true.
	 */
	private volatile boolean		run	= true;
	
	/**
	 * Creates a Tailer for the given file, starting from the beginning, with
	 * the default delay of 1.0s.
	 * 
	 * @param file
	 *            The file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 */
	public Tailer(final File file, final TailerListener listener)
	{
		this(file, listener, Tailer.DEFAULT_DELAY_MILLIS);
	}
	
	/**
	 * Creates a Tailer for the given file, starting from the beginning.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 */
	public Tailer(final File file, final TailerListener listener,
			final long delayMillis)
	{
		this(file, listener, delayMillis, false);
	}
	
	/**
	 * Creates a Tailer for the given file, with a delay other than the default
	 * 1.0s.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 */
	public Tailer(final File file, final TailerListener listener,
			final long delayMillis, final boolean end)
	{
		this(file, listener, delayMillis, end, Tailer.DEFAULT_BUFSIZE);
	}
	
	/**
	 * Creates a Tailer for the given file, with a delay other than the default
	 * 1.0s.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param reOpen
	 *            if true, close and reopen the file between reading chunks
	 */
	public Tailer(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final boolean reOpen)
	{
		this(file, listener, delayMillis, end, reOpen, Tailer.DEFAULT_BUFSIZE);
	}
	
	/**
	 * Creates a Tailer for the given file, with a specified buffer size.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param reOpen
	 *            if true, close and reopen the file between reading chunks
	 * @param bufSize
	 *            Buffer size
	 */
	public Tailer(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final boolean reOpen,
			final int bufSize)
	{
		this.file = file;
		this.delayMillis = delayMillis;
		this.end = end;
		
		this.inbuf = new byte[bufSize];
		
		// Save and prepare the listener
		this.listener = listener;
		listener.init(this);
		this.reOpen = reOpen;
	}
	
	/**
	 * Creates a Tailer for the given file, with a specified buffer size.
	 * 
	 * @param file
	 *            the file to follow.
	 * @param listener
	 *            the TailerListener to use.
	 * @param delayMillis
	 *            the delay between checks of the file for new content in
	 *            milliseconds.
	 * @param end
	 *            Set to true to tail from the end of the file, false to tail
	 *            from the beginning of the file.
	 * @param bufSize
	 *            Buffer size
	 */
	public Tailer(final File file, final TailerListener listener,
			final long delayMillis, final boolean end, final int bufSize)
	{
		this(file, listener, delayMillis, end, false, bufSize);
	}
	
	/**
	 * Return the delay in milliseconds.
	 *
	 * @return the delay in milliseconds.
	 */
	public long getDelay()
	{
		return this.delayMillis;
	}
	
	/**
	 * Return the file.
	 *
	 * @return the file
	 */
	public File getFile()
	{
		return this.file;
	}
	
	/**
	 * Follows changes in the file, calling the TailerListener's handle method
	 * for each new line.
	 */
	@Override
	public void run()
	{
		RandomAccessFile reader = null;
		try
		{
			long last = 0; // The last time the file was checked for changes
			long position = 0; // position within the file
			// Open the file
			while (this.run && (reader == null))
			{
				try
				{
					reader = new RandomAccessFile(this.file, Tailer.RAF_MODE);
				}
				catch (final FileNotFoundException e)
				{
					this.listener.fileNotFound();
				}
				
				if (reader == null)
				{
					try
					{
						Thread.sleep(this.delayMillis);
					}
					catch (final InterruptedException e)
					{
					}
				}
				else
				{
					// The current position in the file
					position = this.end ? this.file.length() : 0;
					last = System.currentTimeMillis();
					reader.seek(position);
				}
			}
			
			while (this.run)
			{
				
				final boolean newer = FileUtils.isFileNewer(this.file, last); // IO-279,
// must be done first
				
				// Check the file length to see if it was rotated
				final long length = this.file.length();
				
				if (length < position)
				{
					
					// File was rotated
					this.listener.fileRotated();
					
					// Reopen the reader after rotation
					try
					{
						// Ensure that the old file is closed iff we re-open it
// successfully
						final RandomAccessFile save = reader;
						reader = new RandomAccessFile(this.file,
								Tailer.RAF_MODE);
						position = 0;
						// close old file explicitly rather than relying on GC
// picking up previous RAF
						IOUtils.closeQuietly(save);
					}
					catch (final FileNotFoundException e)
					{
						// in this case we continue to use the previous reader
// and position values
						this.listener.fileNotFound();
					}
					continue;
				}
				else
				{
					
					// File was not rotated
					
					// See if the file needs to be read again
					if (length > position)
					{
						
						// The file has more content than it did last time
						position = this.readLines(reader);
						last = System.currentTimeMillis();
						
					}
					else if (newer)
					{
						
						/*
						 * This can happen if the file is truncated or
						 * overwritten with the exact same length of
						 * information. In cases like this, the file position
						 * needs to be reset
						 */
						position = 0;
						reader.seek(position); // cannot be null here
						
						// Now we can read new lines
						position = this.readLines(reader);
						last = System.currentTimeMillis();
					}
				}
				if (this.reOpen)
				{
					IOUtils.closeQuietly(reader);
				}
				try
				{
					Thread.sleep(this.delayMillis);
				}
				catch (final InterruptedException e)
				{
				}
				if (this.run && this.reOpen)
				{
					reader = new RandomAccessFile(this.file, Tailer.RAF_MODE);
					reader.seek(position);
				}
			}
			
		}
		catch (final Exception e)
		{
			
			this.listener.handle(e);
			
		}
		finally
		{
			IOUtils.closeQuietly(reader);
		}
	}
	
	/**
	 * Allows the tailer to complete its current loop and return.
	 */
	public void stop()
	{
		this.run = false;
	}
	
	/**
	 * Read new lines.
	 *
	 * @param reader
	 *            The file to read
	 * @return The new position after the lines have been read
	 * @throws java.io.IOException
	 *             if an I/O error occurs.
	 */
	private long readLines(final RandomAccessFile reader) throws IOException
	{
		final StringBuilder sb = new StringBuilder();
		
		long pos = reader.getFilePointer();
		long rePos = pos; // position to re-read
		
		int num;
		boolean seenCR = false;
		while (this.run && ((num = reader.read(this.inbuf)) != -1))
		{
			for (int i = 0; i < num; i++)
			{
				final byte ch = this.inbuf[i];
				switch (ch)
				{
					case '\n':
						seenCR = false; // swallow CR before LF
						this.listener.handle(sb.toString());
						sb.setLength(0);
						rePos = pos + i + 1;
						break;
					case '\r':
						if (seenCR)
						{
							sb.append('\r');
						}
						seenCR = true;
						break;
					default:
						if (seenCR)
						{
							seenCR = false; // swallow final CR
							this.listener.handle(sb.toString());
							sb.setLength(0);
							rePos = pos + i + 1;
						}
						sb.append((char) ch); // add character, not its ascii
// value
				}
			}
			
			pos = reader.getFilePointer();
		}
		
		reader.seek(rePos); // Ensure we can re-read if necessary
		return rePos;
	}
	
}
