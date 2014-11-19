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
import java.lang.management.ManagementFactory;
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
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import xyz.voxio.jtest.gui.AboutFrame;
import xyz.voxio.jtest.gui.AppFrame;

public final class JTest
{
	/**
	 * The web address for github, used to test the internet connectivity
	 */
	public static final String	GITHUB					= "https://github.com/";

	/**
	 * The web address for the issue tracker
	 */
	public static final String	ISSUES					= "https://github.com/Commador/JavaTest/issues";

	/**
	 * The {@link Logger} used by the application
	 */
	public static final Logger	logger					= Logger.getLogger(JTest.class
																.getCanonicalName());

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
	 * Sun property pointing the main class and its arguments.
	 * Might not be defined on non Hotspot VM implementations.
	 */
	public static final String	SUN_JAVA_COMMAND		= "sun.java.command";

	/**
	 * The temporary directory path, and I can't remember what I wanted to do
	 */
	public static final String	TEMP					= ".jtest_temp/";

	/**
	 * The "about" window
	 */
	private static JFrame		aboutWindow;

	/**
	 * The primary application window
	 */
	private static JFrame		appWindow;

	/**
	 * The local questions
	 */
	private static Questions	localQuestions;
	
	private static State		state;

	/**
	 * Cleanup the games objects
	 */
	public static void cleanup()
	{
		// There doesn't seem to be a whole lot that's actually necessary :/
	}
	
	public static void cloneQuestions()
	{
		try
		{
			if (!(new File(JTest.QUESTIONS_JSON_LOCAL).exists()))
			{
				new File(JTest.QUESTIONS_JSON_LOCAL).createNewFile();
			}
			final URL remote = new URL(JTest.QUESTIONS_JSON_REMOTE);
			final ReadableByteChannel rbc = Channels.newChannel(remote
					.openStream());
			final FileOutputStream fos = new FileOutputStream(
					JTest.QUESTIONS_JSON_LOCAL);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

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
	
	/**
	 * @return the local questions
	 */
	public static Questions getLocalQuestions()
	{
		if (JTest.localQuestions == null)
		{
			final String json = JTest.parseFileToString(new File(
					JTest.QUESTIONS_JSON_LOCAL));
			final Gson gson = new Gson();
			final Questions newQ = gson.fromJson(json, Questions.class);
			JTest.localQuestions = newQ;
		}
		return JTest.localQuestions;
	}

	public static File getLocalQuestionsFile()
	{
		return new File(JTest.QUESTIONS_JSON_LOCAL);
	}
	
	/**
	 * @return the local questions version
	 */
	public static Integer getLocalVersion()
	{
		final String json = JTest.parseFileToString(new File(
				JTest.QUESTIONS_JSON_LOCAL));
		final Gson gson = new Gson();
		return gson.fromJson(json, Questions.class).version;
	}
	
	/**
	 * @return the remote questions
	 */
	public static Questions getRemoteQuestions()
	{
		try
		{
			final String json = JTest.parseURLtoString(new URL(
					JTest.QUESTIONS_JSON_REMOTE));
			final Gson gson = new Gson();
			return gson.fromJson(json, Questions.class);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return JTest.getRemoteQuestions();
	}

	public static String getScoreFormatted()
	{
		return "";
		// TODO
	}

	public static State getState()
	{
		return JTest.state;
	}

	public static void infoBox(final String infoMessage, final String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage,
				"InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Initializes the application, creating the necessary objects
	 */
	public static void initialize()
	{
		final File tempDir = new File(JTest.TEMP);
		tempDir.mkdir();
		tempDir.deleteOnExit();
		JTest.setLocalQuestions(JTest.loadQuestions());
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				JTest.cleanup();
			}
		});
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					JTest.appWindow = new AppFrame();
					JTest.aboutWindow = new AboutFrame();
					JTest.appWindow.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
		});
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
		try
		{
			final URL url = new URL(JTest.GITHUB);
			final HttpURLConnection urlConnect = (HttpURLConnection) url
					.openConnection();
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
	 * Determines whether or not the questions need to be updated, updates them,
	 * and then loads them as an instance of {@link Questions}
	 *
	 * @return the Questions
	 */
	public static Questions loadQuestions()
	{
		final boolean connection = JTest.isInternetReachable();
		final boolean isLocalPresent = new File(JTest.QUESTIONS_JSON_LOCAL)
				.exists();
		if (!connection && !isLocalPresent)
		{
			JTest.infoBox(
					"There are no local questions stored, and there is no internet connection.\nThe application must now close.",
					"Error");
			JTest.shutdown();
			return null;
		}
		else if (!connection && isLocalPresent)
		{
			return JTest.getLocalQuestions();
		}
		else if (connection && !isLocalPresent)
		{
			JTest.cloneQuestions();
			return JTest.getLocalQuestions();
		}
		else
		{
			if (JTest.shouldUpdateQuestions())
			{
				JTest.cloneQuestions();
				return JTest.getLocalQuestions();
			}
			else
			{
				return JTest.getLocalQuestions();
			}
		}
	}
	
	/**
	 * Launches the application
	 */
	public static void main(final String[] args)
	{
		JTest.initialize();
		JTest.start();
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

	public static void restartApplication()
	{
		try
		{
			JTest.restartApplication(new Thread()
			{
				@Override
				public void run()
				{
					JTest.cleanup();
				}
			});
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			JTest.infoBox(e.getMessage(), "");
		}
	}

	/**
	 * Restart the current Java application
	 *
	 * @param runBeforeRestart
	 *            some custom code to be run before restarting
	 * @throws IOException
	 */
	public static void restartApplication(final Runnable runBeforeRestart)
			throws IOException
	{
		try
		{
			final String java = System.getProperty("java.home") + "/bin/java";
			final List<String> vmArguments = ManagementFactory
					.getRuntimeMXBean().getInputArguments();
			final StringBuffer vmArgsOneLine = new StringBuffer();
			for (final String arg : vmArguments)
			{
				if (!arg.contains("-agentlib"))
				{
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" "
					+ vmArgsOneLine);
			final String[] mainCommand = System.getProperty(
					JTest.SUN_JAVA_COMMAND).split(" ");
			if (mainCommand[0].endsWith(".jar"))
			{
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			}
			else
			{
				cmd.append("-cp \"" + System.getProperty("java.class.path")
						+ "\" " + mainCommand[0]);
			}
			for (int i = 1; i < mainCommand.length; i++)
			{
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						Runtime.getRuntime().exec(cmd.toString());
					}
					catch (final IOException e)
					{
						e.printStackTrace();
					}
				}
			});
			if (runBeforeRestart != null)
			{
				runBeforeRestart.run();
			}
			System.exit(0);
		}
		catch (final Exception e)
		{
			throw new IOException(
					"Error while trying to restart the application", e);
		}
	}

	/**
	 * @param localQuestions
	 */
	public static void setLocalQuestions(final Questions localQuestions)
	{
		if (localQuestions == null) { return; }
		JTest.localQuestions = localQuestions;
	}

	public static void setState(final State state)
	{
		if ((state == null) || (state == JTest.state)) { return; }
		JTest.logger.info("Game state changing from " + JTest.state.toString()
				+ " to " + state.toString());
		JTest.state = state;
	}

	public static boolean shouldUpdateQuestions()
	{
		return JTest.getRemoteQuestions().version > JTest.getLocalQuestions().version;
	}

	/**
	 * Show the about window
	 */
	public static void showAboutWindow()
	{
		JTest.aboutWindow.setVisible(true);
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

	/**
	 * Shutdown the application nicely
	 */
	public static void shutdown()
	{
		JTest.cleanup();
		System.exit(0);
	}
	
	/**
	 * Start the game
	 */
	public static void start()
	{
		// I'm really not sure what I'm going to do here
	}
	
	private JTest()
	{
		
	}
}
