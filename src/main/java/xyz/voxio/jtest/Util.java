package xyz.voxio.jtest;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Util
{
	/**
	 * Copies a remote web address to a local file path, although I suppose it
	 * could be used to copy a local to a local
	 *
	 * @param local
	 *            the local address
	 * @param remote
	 *            the remote address
	 */
	public static void copyRemoteToLocal(final URI local, final URI remote)
	{
		try
		{
			Files.copy(remote.toURL().openStream(), Paths.get(local),
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (final MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	// TODO move this jive nonsense to a util lib, and upload it to my maven
// repo!!!
	
	public static <K, V> Map<K, V> getMapFromLists(final List<K> keyList,
			final List<V> valueList)
	{
		if (keyList.size() != valueList.size()) { throw new IllegalArgumentException(
				"Cannot combine lists of unequal sizes"); }
		final int size = keyList.size();
		final Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < size; i++)
		{
			map.put(keyList.get(i), valueList.get(i));
		}
		return map;
	}

	/**
	 * Displays an info box with the given title and message.
	 *
	 * @param infoMessage
	 *            the message
	 * @param titleBar
	 *            the title
	 */
	public static void infoBox(final String infoMessage, final String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage, titleBar,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Pings google.com to determine whether or not the internet is reachable.
	 * If google is not reachable, then society has clearly collapsed, so this
	 * game really shouldn't be on your priority list.
	 *
	 * @return whether or not the internet is coming out
	 */
	public static boolean isInternetReachable()
	{
		boolean value;
		try
		{
			final URL url = new URL(Game.GITHUB);
			final HttpURLConnection urlConnect = (HttpURLConnection) url
					.openConnection();
			urlConnect.getContent();
		}
		catch (final UnknownHostException e)
		{
			e.printStackTrace();
			value = false;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			value = false;
		}
		value = true;
		return value;
	}

	/**
	 * Opens a web page in the default browser
	 *
	 * @param webAddress
	 *            the web address in question
	 */
	public static void openWebpage(final String webAddress)
	{
		try
		{
			Util.openWebpage(new URI(webAddress));
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens a web page in the default browser
	 *
	 * @param uri
	 *            the uri in question
	 */
	public static void openWebpage(final URI uri)
	{
		try
		{
			Desktop.getDesktop().browse(uri);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens a web page in the default browser
	 *
	 * @param url
	 *            the url in question
	 */
	public static void openWebpage(final URL url)
	{
		try
		{
			Util.openWebpage(url.toURI());
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Parses a file to a readable string
	 *
	 * @param file
	 *            the file
	 * @return the string
	 */
	public static String parseFileToString(final File file)
	{
		String universe = "";
		try
		{
			final BufferedReader br = new BufferedReader(new FileReader(file));
			try
			{
				final StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null)
				{
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				universe = sb.toString();
			}
			finally
			{
				br.close();
			}
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return universe;
	}
	
	public static String parseStreamToString(final InputStream is)
	{
		final Scanner s = new Scanner(is);
		s.useDelimiter("\\A");
		final String value = s.hasNext() ? s.next() : "";
		s.close();
		return value;
	}
	
	/**
	 * Parses a remote url to a string
	 *
	 * @param url
	 *            the url
	 * @return the string
	 * @throws Exception
	 */
	public static String parseURLtoString(final URL url) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			final StringBuffer buffer = new StringBuffer();
			int read;
			final char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
			{
				buffer.append(chars, 0, read);
			}
			
			return buffer.toString();
		}
		finally
		{
			if (reader != null)
			{
				reader.close();
			}
		}
	}
	
	/**
	 * Shuffles an array, changing the order of the array, but leaving the
	 * contents untouched
	 *
	 * @param array
	 *            the array
	 * @return the new array
	 */
	public static int[] shuffleArray(final int[] array)
	{
		int index, temp;
		final Random random = new Random();
		for (int i = array.length - 1; i > 0; i--)
		{
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		return array;
	}
	
	/**
	 * Shuffles an array, changing the order of the array, but leaving the
	 * contents untouched
	 *
	 * @param array
	 *            the array
	 * @return the new array
	 */
	public static String[] shuffleArray(final String[] array)
	{
		int index;
		String temp;
		final Random random = new Random();
		for (int i = array.length - 1; i > 0; i--)
		{
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		return array;
	}
	
}
