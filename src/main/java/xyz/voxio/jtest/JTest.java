package xyz.voxio.jtest;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import com.google.gson.Gson;

import xyz.voxio.jtest.gui.AboutFrame;
import xyz.voxio.jtest.gui.AppFrame;
import xyz.voxio.jtest.questions.Questions;

public class JTest
{
	public static final String	GOOGLE					= "http://www.google.com";
	
	public static final String	ISSUES					= "https://github.com/Commador/JavaTest/issues";
	
	public static final String	PULL_REQUESTS			= "https://github.com/Commador/JavaTest/pulls";
	
	public static final String	QUESTIONS_JSON_LOCAL	= "questions.json";
	
	public static final String	QUESTIONS_JSON_REMOTE	= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.json";
	
	public static final String	QUESTIONS_REPO			= "https://github.com/Commador/JavaTestQuestions";
	
	public static final String	REPO					= "https://github.com/Commador/JavaTest";
	
	public static final String	TEMP					= ".jtest_temp/";
	
	private static JTest		instance;
	
	public static int[] copyArray(final int[] array)
	{
		final int[] newArray = new int[array.length];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
	}
	
	public static void copyRemoteToLocal(final URI local, final URI remote)
	{
		try
		{
			Files.copy(remote.toURL().openStream(), Paths.get(local), StandardCopyOption.REPLACE_EXISTING);
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
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJSON(final String json)
	{
		final Gson gson = new Gson();
		return (Map<String, Object>) gson.fromJson(json, Object.class);
	}
	
	public static JTest instance()
	{
		return JTest.instance;
	}

	public static boolean isInternetReachable()
	{
		try
		{
			final URL url = new URL(JTest.GOOGLE);
			final HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
			urlConnect.getContent();
		}
		catch (final UnknownHostException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(final String[] args)
	{
		JTest.setInstance(new JTest());
		JTest.instance().initialize().start();
	}

	public static void openWebpage(final String url)
	{
		try
		{
			JTest.openWebpage(new URI(url));
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
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
	
	public static void openWebpage(final URL url)
	{
		try
		{
			JTest.openWebpage(url.toURI());
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public static String readRemoteToString(final String urlString) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			final URL url = new URL(urlString);
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
	
	public static String readStringFromFile(final File file)
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
	
	public static String readURIToString(final URI uri)
	{
		try
		{
			return JTest.readRemoteToString(uri.toString());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String readURLToString(final URL url)
	{
		try
		{
			return JTest.readRemoteToString(url.toString());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

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
	
	private static void setInstance(final JTest instance)
	{
		JTest.instance = instance;
	}

	private JFrame		aboutWindow;
	
	private JFrame		appWindow;

	private Questions	questions;

	public void cleanup()
	{

	}
	
	public Questions getQuestions()
	{
		return this.questions;
	}
	
	public JTest initialize()
	{
		final File tempDir = new File(JTest.TEMP);
		tempDir.mkdir();
		tempDir.deleteOnExit();
		this.setQuestions(this.loadQuestions());
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					JTest.this.appWindow = new AppFrame();
					JTest.this.aboutWindow = new AboutFrame();
					JTest.this.errorWindow = new ErrorFrame();
					JTest.this.appWindow.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		return this;
	}
	
	public void showAboutWindow()
	{
		this.aboutWindow.setVisible(true);
	}
	
	public void shutdown()
	{
		this.cleanup();
		System.exit(0);
	}

	public void start()
	{
		
	}

	private void cloneQuestions()
	{
		try
		{
			final URL remote = new URL(JTest.QUESTIONS_JSON_REMOTE);
			final ReadableByteChannel rbc = Channels.newChannel(remote.openStream());
			final FileOutputStream fos = new FileOutputStream(JTest.QUESTIONS_JSON_LOCAL);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Questions loadQuestions()
	{
		Questions questions = null;
		final File localQuestions = new File("questions.json");
		if ((!localQuestions.exists()))
		{
			if (!JTest.isInternetReachable())
			{
				this.shutdown();
			}
			else
			{
				this.cloneQuestions();
				return this.loadQuestions();
			}
		}
		else
		{
			try
			{
				final Map<String, Object> qMap = JTest.getMapFromJSON(JTest.readStringFromFile(localQuestions));
				final Object version = qMap.get("version");
				System.out.println(version.toString());
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				this.cloneQuestions();
				questions = this.loadQuestions();
			}
		}
		return questions;
	}
	
	private void setQuestions(final Questions questions)
	{
		this.questions = questions;
	}
}
