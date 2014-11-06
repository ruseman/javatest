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
package org.apache.commons.io.monitor;

import java.io.File;
import java.io.Serializable;

/**
 * {@link FileEntry} represents the state of a file or directory, capturing
 * the following {@link File} attributes at a point in time.
 * <ul>
 * <li>File Name (see {@link File#getName()})</li>
 * <li>Exists - whether the file exists or not (see {@link File#exists()})</li>
 * <li>Directory - whether the file is a directory or not (see
 * {@link File#isDirectory()})</li>
 * <li>Last Modified Date/Time (see {@link File#lastModified()})</li>
 * <li>Length (see {@link File#length()}) - directories treated as zero</li>
 * <li>Children - contents of a directory (see
 * {@link File#listFiles(java.io.FileFilter)})</li>
 * </ul>
 * <p>
 * <h3>Custom Implementations</h3> If the state of additional {@link File}
 * attributes is required then create a custom {@link FileEntry} with properties
 * for those attributes. Override the {@link #newChildInstance(File)} to return
 * a new instance of the appropriate type. You may also want to override the
 * {@link #refresh(File)} method.
 * 
 * @see FileAlterationObserver
 * @since 2.0
 */
public class FileEntry implements Serializable
{
	
	static final FileEntry[]	EMPTY_ENTRIES		= new FileEntry[0];
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= -2505664948818681153L;
	
	private FileEntry[]			children;
	
	private boolean				directory;
	
	private boolean				exists;
	
	private final File			file;
	
	private long				lastModified;
	
	private long				length;
	
	private String				name;
	
	private final FileEntry		parent;
	
	/**
	 * Construct a new monitor for a specified {@link File}.
	 *
	 * @param file
	 *            The file being monitored
	 */
	public FileEntry(final File file)
	{
		this((FileEntry) null, file);
	}
	
	/**
	 * Construct a new monitor for a specified {@link File}.
	 *
	 * @param parent
	 *            The parent
	 * @param file
	 *            The file being monitored
	 */
	public FileEntry(final FileEntry parent, final File file)
	{
		if (file == null) { throw new IllegalArgumentException(
				"File is missing"); }
		this.file = file;
		this.parent = parent;
		this.name = file.getName();
	}
	
	/**
	 * Return the directory's files.
	 *
	 * @return This directory's files or an empty
	 *         array if the file is not a directory or the
	 *         directory is empty
	 */
	public FileEntry[] getChildren()
	{
		return this.children != null ? this.children : FileEntry.EMPTY_ENTRIES;
	}
	
	/**
	 * Return the file being monitored.
	 *
	 * @return the file being monitored
	 */
	public File getFile()
	{
		return this.file;
	}
	
	/**
	 * Return the last modified time from the last time it
	 * was checked.
	 *
	 * @return the last modified time
	 */
	public long getLastModified()
	{
		return this.lastModified;
	}
	
	/**
	 * Return the length.
	 *
	 * @return the length
	 */
	public long getLength()
	{
		return this.length;
	}
	
	/**
	 * Return the level
	 *
	 * @return the level
	 */
	public int getLevel()
	{
		return this.parent == null ? 0 : this.parent.getLevel() + 1;
	}
	
	/**
	 * Return the file name.
	 *
	 * @return the file name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Return the parent entry.
	 *
	 * @return the parent entry
	 */
	public FileEntry getParent()
	{
		return this.parent;
	}
	
	/**
	 * Indicate whether the file is a directory or not.
	 *
	 * @return whether the file is a directory or not
	 */
	public boolean isDirectory()
	{
		return this.directory;
	}
	
	/**
	 * Indicate whether the file existed the last time it
	 * was checked.
	 *
	 * @return whether the file existed
	 */
	public boolean isExists()
	{
		return this.exists;
	}
	
	/**
	 * Create a new child instance.
	 * <p>
	 * Custom implementations should override this method to return a new
	 * instance of the appropriate type.
	 *
	 * @param file
	 *            The child file
	 * @return a new child instance
	 */
	public FileEntry newChildInstance(final File file)
	{
		return new FileEntry(this, file);
	}
	
	/**
	 * Refresh the attributes from the {@link File}, indicating
	 * whether the file has changed.
	 * <p>
	 * This implementation refreshes the <code>name</code>, <code>exists</code>,
	 * <code>directory</code>, <code>lastModified</code> and <code>length</code>
	 * properties.
	 * <p>
	 * The <code>exists</code>, <code>directory</code>,
	 * <code>lastModified</code> and <code>length</code> properties are compared
	 * for changes
	 *
	 * @param file
	 *            the file instance to compare to
	 * @return {@code true} if the file has changed, otherwise {@code false}
	 */
	public boolean refresh(final File file)
	{
		
		// cache original values
		final boolean origExists = this.exists;
		final long origLastModified = this.lastModified;
		final boolean origDirectory = this.directory;
		final long origLength = this.length;
		
		// refresh the values
		this.name = file.getName();
		this.exists = file.exists();
		this.directory = this.exists ? file.isDirectory() : false;
		this.lastModified = this.exists ? file.lastModified() : 0;
		this.length = this.exists && !this.directory ? file.length() : 0;
		
		// Return if there are changes
		return (this.exists != origExists) ||
				(this.lastModified != origLastModified) ||
				(this.directory != origDirectory) ||
				(this.length != origLength);
	}
	
	/**
	 * Set the directory's files.
	 *
	 * @param children
	 *            This directory's files, may be null
	 */
	public void setChildren(final FileEntry[] children)
	{
		this.children = children;
	}
	
	/**
	 * Set whether the file is a directory or not.
	 *
	 * @param directory
	 *            whether the file is a directory or not
	 */
	public void setDirectory(final boolean directory)
	{
		this.directory = directory;
	}
	
	/**
	 * Set whether the file existed the last time it
	 * was checked.
	 *
	 * @param exists
	 *            whether the file exists or not
	 */
	public void setExists(final boolean exists)
	{
		this.exists = exists;
	}
	
	/**
	 * Return the last modified time from the last time it
	 * was checked.
	 *
	 * @param lastModified
	 *            The last modified time
	 */
	public void setLastModified(final long lastModified)
	{
		this.lastModified = lastModified;
	}
	
	/**
	 * Set the length.
	 *
	 * @param length
	 *            the length
	 */
	public void setLength(final long length)
	{
		this.length = length;
	}
	
	/**
	 * Set the file name.
	 *
	 * @param name
	 *            the file name
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
}
