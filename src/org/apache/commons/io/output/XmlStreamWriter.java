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
package org.apache.commons.io.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.input.XmlStreamReader;

/**
 * Character stream that handles all the necessary Voodo to figure out the
 * charset encoding of the XML document written to the stream.
 *
 * @version $Id: XmlStreamWriter.java 1304052 2012-03-22 20:55:29Z ggregory $
 * @see XmlStreamReader
 * @since 2.0
 */
public class XmlStreamWriter extends Writer
{
	static final Pattern		ENCODING_PATTERN	= XmlStreamReader.ENCODING_PATTERN;
	
	private static final int	BUFFER_SIZE			= 4096;
	
	private final String		defaultEncoding;
	
	private String				encoding;
	
	private final OutputStream	out;
	
	private Writer				writer;
	
	private StringWriter		xmlPrologWriter		= new StringWriter(
															XmlStreamWriter.BUFFER_SIZE);
	
	/**
	 * Construct an new XML stream writer for the specified file
	 * with a default encoding of UTF-8.
	 * 
	 * @param file
	 *            The file to write to
	 * @throws FileNotFoundException
	 *             if there is an error creating or
	 *             opening the file
	 */
	public XmlStreamWriter(final File file) throws FileNotFoundException
	{
		this(file, null);
	}
	
	/**
	 * Construct an new XML stream writer for the specified file
	 * with the specified default encoding.
	 * 
	 * @param file
	 *            The file to write to
	 * @param defaultEncoding
	 *            The default encoding if not encoding could be detected
	 * @throws FileNotFoundException
	 *             if there is an error creating or
	 *             opening the file
	 */
	public XmlStreamWriter(final File file, final String defaultEncoding)
			throws FileNotFoundException
	{
		this(new FileOutputStream(file), defaultEncoding);
	}
	
	/**
	 * Construct an new XML stream writer for the specified output stream
	 * with a default encoding of UTF-8.
	 *
	 * @param out
	 *            The output stream
	 */
	public XmlStreamWriter(final OutputStream out)
	{
		this(out, null);
	}
	
	/**
	 * Construct an new XML stream writer for the specified output stream
	 * with the specified default encoding.
	 *
	 * @param out
	 *            The output stream
	 * @param defaultEncoding
	 *            The default encoding if not encoding could be detected
	 */
	public XmlStreamWriter(final OutputStream out, final String defaultEncoding)
	{
		this.out = out;
		this.defaultEncoding = defaultEncoding != null ? defaultEncoding
				: "UTF-8";
	}
	
	/**
	 * Close the underlying writer.
	 *
	 * @throws IOException
	 *             if an error occurs closing the underlying writer
	 */
	@Override
	public void close() throws IOException
	{
		if (this.writer == null)
		{
			this.encoding = this.defaultEncoding;
			this.writer = new OutputStreamWriter(this.out, this.encoding);
			this.writer.write(this.xmlPrologWriter.toString());
		}
		this.writer.close();
	}
	
	/**
	 * Flush the underlying writer.
	 *
	 * @throws IOException
	 *             if an error occurs flushing the underlying writer
	 */
	@Override
	public void flush() throws IOException
	{
		if (this.writer != null)
		{
			this.writer.flush();
		}
	}
	
	/**
	 * Return the default encoding.
	 *
	 * @return the default encoding
	 */
	public String getDefaultEncoding()
	{
		return this.defaultEncoding;
	}
	
	/**
	 * Return the detected encoding.
	 *
	 * @return the detected encoding
	 */
	public String getEncoding()
	{
		return this.encoding;
	}
	
	/**
	 * Write the characters to the underlying writer, detecing encoding.
	 * 
	 * @param cbuf
	 *            the buffer to write the characters from
	 * @param off
	 *            The start offset
	 * @param len
	 *            The number of characters to write
	 * @throws IOException
	 *             if an error occurs detecting the encoding
	 */
	@Override
	public void write(final char[] cbuf, final int off, final int len)
			throws IOException
	{
		if (this.xmlPrologWriter != null)
		{
			this.detectEncoding(cbuf, off, len);
		}
		else
		{
			this.writer.write(cbuf, off, len);
		}
	}
	
	/**
	 * Detect the encoding.
	 *
	 * @param cbuf
	 *            the buffer to write the characters from
	 * @param off
	 *            The start offset
	 * @param len
	 *            The number of characters to write
	 * @throws IOException
	 *             if an error occurs detecting the encoding
	 */
	private void detectEncoding(final char[] cbuf, final int off, final int len)
			throws IOException
	{
		int size = len;
		final StringBuffer xmlProlog = this.xmlPrologWriter.getBuffer();
		if ((xmlProlog.length() + len) > XmlStreamWriter.BUFFER_SIZE)
		{
			size = XmlStreamWriter.BUFFER_SIZE - xmlProlog.length();
		}
		this.xmlPrologWriter.write(cbuf, off, size);
		
		// try to determine encoding
		if (xmlProlog.length() >= 5)
		{
			if (xmlProlog.substring(0, 5).equals("<?xml"))
			{
				// try to extract encoding from XML prolog
				final int xmlPrologEnd = xmlProlog.indexOf("?>");
				if (xmlPrologEnd > 0)
				{
					// ok, full XML prolog written: let's extract encoding
					final Matcher m = XmlStreamWriter.ENCODING_PATTERN
							.matcher(xmlProlog.substring(0,
									xmlPrologEnd));
					if (m.find())
					{
						this.encoding = m.group(1).toUpperCase();
						this.encoding = this.encoding.substring(1,
								this.encoding.length() - 1);
					}
					else
					{
						// no encoding found in XML prolog: using default
						// encoding
						this.encoding = this.defaultEncoding;
					}
				}
				else
				{
					if (xmlProlog.length() >= XmlStreamWriter.BUFFER_SIZE)
					{
						// no encoding found in first characters: using default
						// encoding
						this.encoding = this.defaultEncoding;
					}
				}
			}
			else
			{
				// no XML prolog: using default encoding
				this.encoding = this.defaultEncoding;
			}
			if (this.encoding != null)
			{
				// encoding has been chosen: let's do it
				this.xmlPrologWriter = null;
				this.writer = new OutputStreamWriter(this.out, this.encoding);
				this.writer.write(xmlProlog.toString());
				if (len > size)
				{
					this.writer.write(cbuf, off + size, len - size);
				}
			}
		}
	}
}
