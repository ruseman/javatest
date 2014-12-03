package xyz.voxio.jtest;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;

import xyz.voxio.jtest.game.Player;
import xyz.voxio.jtest.game.Questions;
import xyz.voxio.jtest.gui.AboutFrame;
import xyz.voxio.jtest.gui.AppFrame;
import xyz.voxio.jtest.gui.EndFrame;
import xyz.voxio.lib.Util;

public final class Game
{
	public static enum Reason
	{
		NO_MORE_QUESTIONS, OUT_OF_POINTS;
	}

	public static enum State
	{
		INITIALIZING, RUNNING, SHUTTING_DOWN;
	}

	/**
	 * The web address for the issue tracker
	 */
	public static final String	ISSUES					= "https://github.com/Commador/JavaTest/issues";

	/**
	 * The {@link Logger} used by the application
	 */
	public static final Logger	LOGGER					= Logger.getLogger(Game.class.getCanonicalName());

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
	 * The title of the application
	 */
	public static final String	TITLE					= "JTest";

	/**
	 * The instance of the game
	 */
	private static Game			instance;
	
	/**
	 * @return a new instance of the local questions file
	 */
	public static File getLocalQuestionsFile()
	{
		return new File(Game.QUESTIONS_JSON_LOCAL);
	}
	
	/**
	 * @return a new title, formatted, with a random splash appended to the end
	 */
	public static String getNewTitle()
	{
		return Game.TITLE + " - " + Splash.getSplash().getRandomSplash();
	}

	/**
	 * @return the remote questions
	 */
	public static Questions getRemoteQuestions()
	{
		try
		{
			final String json = Util.parseURLtoString(new URL(Game.QUESTIONS_JSON_REMOTE));
			final Gson gson = new Gson();
			return gson.fromJson(json, Questions.class);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return Game.getRemoteQuestions();
	}
	
	public static Game instance()
	{
		if (Game.instance == null)
		{
			Game.instance = new Game();
		}
		return Game.instance;
	}

	/**
	 * Launches the application
	 */
	public static void main(final String[] args)
	{
		Game.instance().initialize();
		Game.instance().start();
	}

	/**
	 * The "about" window
	 */
	private AboutFrame	aboutFrame;
	
	/**
	 * The primary application window
	 */
	private AppFrame	appFrame;

	private EndFrame	endFrame;

	private Player		player;

	/**
	 * The local questions
	 */
	private Questions	questions;

	private State		state;
	
	private Game()
	{

	}
	
	public void changeState(final State state)
	{
		if ((state == null) || (state == this.state)) { return; }
		if (this.state == null)
		{
			this.state = state;
			Game.LOGGER.info("Game state is now " + this.state.toString());
			return;
		}
		Game.LOGGER.info("Game state changing from " + this.state.toString() + " to " + state.toString());
		this.state = state;
	}

	public void cloneQuestions()
	{
		try
		{
			if (!(new File(Game.QUESTIONS_JSON_LOCAL).exists()))
			{
				new File(Game.QUESTIONS_JSON_LOCAL).createNewFile();
			}
			final URL remote = new URL(Game.QUESTIONS_JSON_REMOTE);
			final ReadableByteChannel rbc = Channels.newChannel(remote.openStream());
			final FileOutputStream fos = new FileOutputStream(Game.QUESTIONS_JSON_LOCAL);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void endGame(final Reason reason)
	{
		String endMessage = "";
		switch (reason)
		{
			case NO_MORE_QUESTIONS:
				endMessage = "You completed all of the questions!\n" + Splash.getSplash().getRandomEndSplash();
				break;
			case OUT_OF_POINTS:
				endMessage = "You ran out of points!\n" + Splash.getSplash().getRandomLoseSplash();
				break;
		}
		final EndFrame frame = EndFrame.getNewInstance(endMessage);
		frame.setVisible(true);
	}

	public EndFrame getEndFrame()
	{
		return this.endFrame;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	/**
	 * @return the local questions
	 */
	public Questions getQuestions()
	{
		if (this.questions != null) { return this.questions; }
		final String json = Util.parseFileToString(new File(Game.QUESTIONS_JSON_LOCAL));
		final Gson gson = new Gson();
		final Questions newQ = gson.fromJson(json, Questions.class);
		this.questions = newQ;
		return this.getQuestions();
	}

	public State getState()
	{
		return this.state;
	}
	
	/**
	 * Initializes the application, creating the necessary objects
	 */
	public void initialize()
	{
		this.changeState(State.INITIALIZING);
		Game.LOGGER.info("Loading the questions");
		this.loadQuestions();
		Game.LOGGER.info("Questions have been loaded");
		this.registerHooks();

		Game.LOGGER.info("Creating a few objects...");
		this.player = new Player();
		new File("questions.json").deleteOnExit();
	}
	
	/**
	 * Determines whether or not the questions need to be updated, updates them,
	 * and then loads them as an instance of {@link Questions}
	 *
	 * @return the Questions
	 */
	public Questions loadQuestions()
	{
		final boolean connection = Util.isInternetReachable();
		if (connection)
		{
			this.cloneQuestions();
			return this.getQuestions();
		}
		else
		{
			Util.infoBox("There is no internet connection.\nThe application must now close.", "Error");
			this.shutdown();
			return null;
		}
	}
	
	public void restartApplication()
	{
		final String SUN_JAVA_COMMAND = "sun.java.command";
		final String java = System.getProperty("java.home") + "/bin/java";
		final List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		final StringBuffer vmArgsOneLine = new StringBuffer();
		for (final String arg : vmArguments)
		{
			if (!arg.contains("-agentlib"))
			{
				vmArgsOneLine.append(arg);
				vmArgsOneLine.append(" ");
			}
		}
		final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);
		final String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
		if (mainCommand[0].endsWith(".jar"))
		{
			cmd.append("-jar " + new File(mainCommand[0]).getPath());
		}
		else
		{
			cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
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
		System.exit(0);
	}
	
	public boolean shouldUpdateQuestions()
	{
		try
		{
			return Game.getRemoteQuestions().getVersion() > this.getQuestions().getVersion();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * Show the about window
	 */
	public void showAboutWindow()
	{
		this.aboutFrame.setVisible(true);
	}

	/**
	 * Shutdown the application nicely
	 */
	public void shutdown()
	{
		this.changeState(State.SHUTTING_DOWN);
		System.exit(0);
	}
	
	/**
	 * Start the game
	 */
	public void start()
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Game.this.changeState(State.RUNNING);
				Game.LOGGER.info("Things seem to be working.  If you're seeing this, it means that things haven't completely broken yet.");
			}
		});
	}

	private void createFrames()
	{
		this.appFrame = new AppFrame();
		this.endFrame = new EndFrame();
		this.aboutFrame = new AboutFrame();
	}

	private void registerHooks()
	{
		Game.LOGGER.info("Registering hooks and handlers");
		Game.LOGGER.info("Registering the global event handler");
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(final Thread t, final Throwable e)
			{
				e.printStackTrace();
				if (Game.this.state == State.INITIALIZING)
				{
					Game.LOGGER.info("Restarting due to an error...");
					Game.instance().restartApplication();
				}
			}
		});
		Game.LOGGER.info("Registering shutdown hook");
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				Game.this.changeState(xyz.voxio.jtest.Game.State.SHUTTING_DOWN);
			}
		});
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Game.this.createFrames();
					Game.this.appFrame.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}
}
