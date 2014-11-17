package xyz.voxio.jtest;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JTest
{
	public enum State
	{
		CLOSING, INITIALIZING, RUNNING;
	}

	public static String			GITHUB_URI						= ("https://github.com");

	public static JTest				instance;
	
	public static String			ISSUES_URI						= ("https://github.com/Commador/JavaTest/issues");

	public static List<Question>	questions;
	
	public static String			QUESTIONS_FILE_LOCATIONS		= "jtest/questions.cfg";

	public static final String		QUESTIONS_FILE_URI_REMOTE		= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.json";
	
	public static final String		QUESTIONS_FILE_VERSION_URI		= "jtest/VERSION";

	public static final String		SOURCE_URI						= "https://github.com/Commador/JavaTest";
	
	public static final String		VERSION_REMOTE_QUESTIONS_URI	= "https://github.com/Commador/JavaTestQuestions/blob/master/VERSION";
																																							
	private static Question			currentQuestion;

	private static State			state;

	private static AppWindow		window;
	
	public static void changeState(final State state)
	{
		JTest.changeState(state, null);
	}
	
	public static void changeState(final State state, final String msg)
	{
		setState(state);
	}
	
	public static final String TITLE = "JTest";
	
	public static void exit()
	{
		JTest.setState(State.CLOSING);
		JTest.exitAll();
	}

	public static void exitAll()
	{
		System.exit(0);
	}

	public static List<Question> genQuestions()
	{
		// TODO
		List<Question> ql = new ArrayList<>();
		return ql;
	}

	public static String getButtonContent()
	{
		return "";
	}
	
	public static Question getCurrentQuestion()
	{
		return JTest.currentQuestion;
	}
	
	public static State getState()
	{
		return JTest.state;
	}

	public static String getVersionLocal()
	{
		try
		{
			String string;
			try
			{
				string = "";
			}
			catch (final Exception e)
			{
				string = "0";
				throw new InvalidLocalException(e);
			}
			return string;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			JTest.refreshLocal();
			return JTest.getVersionLocal();
		}
	}

	public static String getVersionRemote()
	{
		// TODO
		String version = "";
		
		return version;
	}

	public static AppWindow getWindow()
	{
		return JTest.window;
	}

	public static void initialize()
	{
		JTest.changeState(State.INITIALIZING);
		{
			if (JTest.needsUpdate())
			{
				JTest.updateQuestions();

			}
		}
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
	
	public static boolean needsUpdate()
	{
		/*String local;
		local = JTest.getVersionLocal();
		//JTest.refreshLocal();
		local = JTest.getVersionLocal();
		final String remote = JTest.getVersionRemote();
		final double ld = Double.parseDouble(local);
		final double rd = Double.parseDouble(remote);
		return rd > ld;*/
		return false;
		//TODO
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

	public static void updateQuestions()
	{
		// TODO
	}

	private static void refreshLocal()
	{
		// TODO redownload the remote to local
		JTest.needsUpdate();
	}
	
	private static void setState(final State state)
	{
		JTest.state = state;
	}
}
