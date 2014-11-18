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
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.google.gson.Gson;

import xyz.voxio.jtest.gui.AboutFrame;
import xyz.voxio.jtest.gui.AppFrame;

public final class JTest
{

	/**
	 * Class representing a specific question, with a prompt, and 4 answers, the first of which is correct
	 *
	 * @author Tim Miller
	 */
	public static final class Question
	{
		/**
		 * The set of answers, where index 0 is the correct answer, and index 1 through 3 are the incorrect answers
		 */
		public List<String>	answers;
		
		/**
		 * The prompt for the question
		 */
		public String		prompt;
	}
	
	/**
	 * Class representing the set of questions used by the game. It is generated from a JSON file.
	 *
	 * @author Tim Miller
	 */
	public static final class Questions
	{
		/**
		 * The {@link List} of the Questions
		 */
		public List<Question>	questions;
		
		/**
		 * The version of the questions, as given in the JSON
		 */
		public int				version;
	}
	
	/**
	 * The web address for google, used to test the internet connectivity
	 */
	public static final String	GOOGLE					= "http://www.google.com";
	
	/**
	 * The web address for the issue tracker
	 */
	public static final String	ISSUES					= "https://github.com/Commador/JavaTest/issues";
	
	/**
	 * The {@link Logger} used by the application
	 */
	public static final Logger	logger					= Logger.getLogger(JTest.class.getCanonicalName());
	
	/**
	 * The web address for the pull requests page
	 */
	public static final String	PULL_REQUESTS			= "https://github.com/Commador/JavaTest/pulls";
	
	/**
	 * The path to the local questions json file
	 */
	public static final String	QUESTIONS_JSON_LOCAL	= "questions.json";
	
	/**
	 * The web address for the remote questions json file
	 */
	public static final String	QUESTIONS_JSON_REMOTE	= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.json";
	
	/**
	 * The repository for the questions
	 */
	public static final String	QUESTIONS_REPO			= "https://github.com/Commador/JavaTestQuestions";
	
	/**
	 * The project repo
	 */
	public static final String	REPO					= "https://github.com/Commador/JavaTest";
	
	/**
	 * The temporary directory path, and I can't remember what I wanted to do
	 */
	public static final String	TEMP					= ".jtest_temp/";
	
	/**
	 * The instance of the application
	 */
	private static JTest		instance;
	
	/**
	 * Copies a remote web address to a local file path, although I suppose it could be used to copy a local to a local
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

	/**
	 * @return the instance of the application
	 */
	public static JTest instance()
	{
		return JTest.instance;
	}
	
	/**
	 * Pings google.com to determine whether or not the internet is reachable. If google is not reachable, then society has clearly collapsed, so this game really shouldn't be on your priority list.
	 *
	 * @return whether or not the internet is coming out
	 */
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

	/**
	 * Launches the application
	 */
	public static void main(final String[] args)
	{
		JTest.setInstance(new JTest());
		JTest.instance().initialize().start();
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
			JTest.openWebpage(new URI(webAddress));
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
			JTest.openWebpage(url.toURI());
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
	 * Shuffles an array, changing the order of the array, but leaving the contents untouched
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
	 * Shuffles an array, changing the order of the array, but leaving the contents untouched
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
	
	/**
	 * Sets the instance
	 *
	 * @param instance
	 *            the instance
	 */
	private static void setInstance(final JTest instance)
	{
		JTest.instance = instance;
	}

	/**
	 * The "about" window
	 */
	private JFrame		aboutWindow;

	/**
	 * The primary application window
	 */
	private JFrame		appWindow;
	
	/**
	 * The local questions
	 */
	private Questions	localQuestions;
	
	/**
	 * Cleanup the games objects
	 */
	public void cleanup()
	{
		// There doesn't seem to be a whole lot that's actually necessary :/
	}
	
	public void cloneQuestions()
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

	/**
	 * @return the local questions
	 */
	public Questions getLocalQuestions()
	{
		if (this.localQuestions == null)
		{
			final String json = JTest.parseFileToString(new File(JTest.QUESTIONS_JSON_LOCAL));
			final Gson gson = new Gson();
			final Questions newQ = gson.fromJson(json, Questions.class);
			this.localQuestions = newQ;
		}
		return this.localQuestions;
	}
	
	/**
	 * @return the local questions version
	 */
	public Integer getLocalVersion()
	{
		final String json = JTest.parseFileToString(new File(JTest.QUESTIONS_JSON_LOCAL));
		final Gson gson = new Gson();
		return gson.fromJson(json, Questions.class).version;
	}
	
	/**
	 * @return the remote questions
	 */
	public Questions getRemoteQuestions()
	{
		try
		{
			final String json = JTest.parseURLtoString(new URL(JTest.QUESTIONS_JSON_REMOTE));
			final Gson gson = new Gson();
			return gson.fromJson(json, Questions.class);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return this.getRemoteQuestions();
	}
	
	/**
	 * Initializes the application, creating the necessary objects
	 *
	 * @return the instance
	 */
	public JTest initialize()
	{
		final File tempDir = new File(JTest.TEMP);
		tempDir.mkdir();
		tempDir.deleteOnExit();
		this.setLocalQuestions(this.loadQuestions());
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				JTest.this.cleanup();
			}
		});
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					JTest.this.appWindow = new AppFrame();
					JTest.this.aboutWindow = new AboutFrame();
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
	
	public Questions loadQuestions()
	{
		Questions questions = null;
		final File localQuestions = new File(JTest.QUESTIONS_JSON_LOCAL);
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
				final Integer localVersion = this.getLocalQuestions().version;
				final Integer remoteVersion = this.getRemoteQuestions().version;
				JTest.logger.info("Local question version is " + localVersion.toString());
				JTest.logger.info("Remote question version is " + remoteVersion.toString());
				if (remoteVersion > localVersion)
				{
					this.cloneQuestions();
					return this.loadQuestions();
				}
				else
				{
					return this.getLocalQuestions();
				}
			}
			catch (final NullPointerException e)
			{
				e.printStackTrace();
				this.shutdown();
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
	
	/**
	 * @param localQuestions
	 */
	public void setLocalQuestions(final Questions localQuestions)
	{
		if (localQuestions == null)
		{
			return;
		}
		else if (this.localQuestions != null)
		{
			// I considered having the game do nothing if a localQuestions already existed, but then I decided, what the hell!
		}
		else if (this.localQuestions == null)
		{
			this.localQuestions = localQuestions;
		}
		this.localQuestions = localQuestions;
	}
	
	/**
	 * Show the about window
	 */
	public void showAboutWindow()
	{
		this.aboutWindow.setVisible(true);
	}

	/**
	 * Shutdown the application nicely
	 */
	public void shutdown()
	{
		this.cleanup();
		System.exit(0);
	}
	
	/**
	 * Start the game
	 */
	public void start()
	{
		// I'm really not sure what I'm going to do here
	}
}
