package xyz.voxio.jtest;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class JTest
{
	public enum State
	{
		CLOSING, INITIALIZING, RUNNING;
	}
	
	public static String			GITHUB_URI						= ("https://github.com");
	
	public static JTest				instance;

	public static String			ISSUES_URI						= ("https://github.com/Commador/JavaTest/issues");

	public static boolean			needsUpdate;
	
	public static List<Question>	questions;

	public static String			QUESTIONS_FILE_LOCATIONS		= "jtest/questions.cfg";
	
	public static final String		QUESTIONS_FILE_URI_REMOTE		= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.cfg";

	public static final String		QUESTIONS_FILE_VERSION_URI		= "jtest/VERSION";
	
	public static String			SOURCE_URI						= ("https://github.com/Commador/JavaTest");

	public static String			VERSION							= "1.0";

	public static final String		VERSION_REMOTE_QUESTIONS_URI	= "https://github.com/Commador/JavaTestQuestions/blob/master/VERSION";					// TODO

	private static Question			currentQuestion;
	
	private static State			state;
	
	private static AppWindow		window;

	public static void changeState(final State state)
	{
		JTest.changeState(state, null);
	}

	public static void changeState(final State state, final String msg)
	{
		try
		{
			try
			{
				String newmsg = "State changed from "
						+ JTest.getState().toString() + " to "
						+ state.toString();
				if ((msg != null) && !msg.isEmpty())
				{
					newmsg += ": " + msg;
				}
				Logger.getLogger().log(Level.INFO, newmsg);
				JTest.setState(state);
			}
			catch (final Exception e)
			{
				throw new Exception("Change state failed");
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void exit()
	{
		JTest.setState(State.CLOSING);
		JTest.exitAll();
	}
	
	public static void exitAll()
	{
		System.exit(0);
	}
	
	public static List<Question> genQuestions() throws Exception
	{
		final List<Question> questions = new ArrayList<Question>();
		final File qfile = new File(JTest.QUESTIONS_FILE_LOCATIONS);
		if (!qfile.exists() || JTest.needsUpdate)
		{
			final URL remote = new URL(JTest.QUESTIONS_FILE_URI_REMOTE);
			final ReadableByteChannel rbc = Channels.newChannel(remote
					.openStream());
			final FileOutputStream fos = new FileOutputStream(qfile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			rbc.close();
			fos.close();
		}
		{
			final BufferedReader br = new BufferedReader(new FileReader(qfile));
			String line;
			while ((line = br.readLine()) != null)
			{
				questions.add(new Question(line));
			}
			br.close();
		}
		return questions;
	}
	
	public static String getButtonContent()
	{
		return "";
	}

	public static Question getCurrentQuestion()
	{
		return JTest.currentQuestion;
	}

	public static String getLocalVersion()
	{
		return "";//TODO
	}
	

	public static State getState()
	{
		return JTest.state;
	}
	
	public static String getVersionRemote()
	{
		// TODO
		return null;
	}
	
	public static AppWindow getWindow()
	{
		return JTest.window;
	}
	
	public static void initialize()
	{
		JTest.changeState(State.INITIALIZING);
		JTest.needsUpdate = JTest.needsUpdate(JTest.VERSION,
				JTest.getVersionRemote());
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				JTest.window = new AppWindow();
				JTest.window.enable();
			}
		});
	}
	
	public static void main(final String[] args)
	{
		JTest.setWindow(new AppWindow());
		JTest.initialize();
		JTest.start();
	}

	public static boolean needsUpdate(final String local, final String remote)
	{
		final double ld = Double.parseDouble(local);
		final double rd = Double.parseDouble(remote);
		return rd > ld;
	}

	public static void openWebPage(final String uri)
	{
		try
		{
			JTest.openWebPage(new URI(uri));
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public static void openWebPage(final URI uri)
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

	public static void setCurrentQuestion(final Question currentQuestion)
	{
		JTest.currentQuestion = currentQuestion;
	}

	public static void setWindow(final AppWindow window)
	{
		JTest.window = window;
	}
	
	public static void showAboutWindow()
	{

	}
	
	public static void start()
	{
		JTest.changeState(State.RUNNING);
	}

	private static void setState(final State state)
	{
		JTest.state = state;
	}
}
