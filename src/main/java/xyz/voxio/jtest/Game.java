package xyz.voxio.jtest;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static final Logger	logger					= Logger.getLogger(Game.class
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
	 * The temporary directory path, and I can't remember what I wanted to do
	 */
	public static final String	TEMP					= ".jtest_temp/";
	
	public static final String	TITLE					= "JTest";
	
	private static Game			instance;

	public static File getLocalQuestionsFile()
	{
		return new File(Game.QUESTIONS_JSON_LOCAL);
	}

	public static String getNewTitle()
	{
		return Game.TITLE + " - " + Game.getRandomSplash();
	}
	
	public static String getRandomSplash()
	{
		return new Gson().fromJson(
				Util.parseStreamToString(Game.class
						.getResourceAsStream("splash.json")), Splash.class)
						.getRandomSplash();
	}
	
	/**
	 * @return the remote questions
	 */
	public static Questions getRemoteQuestions()
	{
		try
		{
			final String json = Util.parseURLtoString(new URL(
					Game.QUESTIONS_JSON_REMOTE));
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
			Game.logger.info("Game state is now " + this.state.toString());
			return;
		}
		Game.logger.info("Game state changing from " + this.state.toString()
				+ " to " + state.toString());
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
			final ReadableByteChannel rbc = Channels.newChannel(remote
					.openStream());
			final FileOutputStream fos = new FileOutputStream(
					Game.QUESTIONS_JSON_LOCAL);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public EndFrame endFrame()
	{
		return this.endFrame;
	}
	
	/**
	 *
	 */
	public void endGame()
	{
		// TODO
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
		if (this.questions == null)
		{
			final String json = Util.parseFileToString(new File(
					Game.QUESTIONS_JSON_LOCAL));
			final Gson gson = new Gson();
			final Questions newQ = gson.fromJson(json, Questions.class);
			this.questions = newQ;
		}
		return this.questions;
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
		{
			Game.logger.info("Dirs being mkd");
			final File tempDir = new File(Game.TEMP);
			tempDir.mkdir();
			tempDir.deleteOnExit();
		}
		Game.logger.info("Loading the questions");
		this.setLocalQuestions(this.loadQuestions());
		Game.logger.info("Questions have been loaded");
		{
			Game.logger.info("Registering shutdown hook");
			Runtime.getRuntime().addShutdownHook(new Thread()
			{
				@Override
				public void run()
				{
					Game.this.changeState(xyz.voxio.jtest.State.SHUTTING_DOWN);
				}
			});
		}
		{
			Game.logger.info("Invoking later...");
			EventQueue.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Game.this.appFrame = new AppFrame();
						Game.this.aboutFrame = new AboutFrame();
						Game.this.appFrame.setVisible(true);
					}
					catch (final Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			Game.logger.info("Creating a few objects...");
			this.player = new Player();
		}
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
		final boolean isLocalPresent = new File(Game.QUESTIONS_JSON_LOCAL)
		.exists();
		if (!connection && !isLocalPresent)
		{
			Util.infoBox(
					"There are no local questions stored, and there is no internet connection.\nThe application must now close.",
					"Error");
			this.shutdown();
			return null;
		}
		else if (!connection && isLocalPresent)
		{
			return this.getQuestions();
		}
		else if (connection && !isLocalPresent)
		{
			this.cloneQuestions();
			return this.getQuestions();
		}
		else
		{
			if (this.shouldUpdateQuestions())
			{
				this.cloneQuestions();
				return this.getQuestions();
			}
			else
			{
				return this.getQuestions();
			}
		}
	}

	public void refreshPanes()
	{
		this.appFrame.refresh();
	}

	/**
	 * Restart the current Java application
	 *
	 * @param runBeforeRestart
	 *            some custom code to be run before restarting
	 * @throws IOException
	 */
	public void restartApplication()
	{
		this.changeState(State.RESTARTING);
		final Runnable runBeforeRestart = new Runnable()
		{
			@Override
			public void run()
			{

			}
		};
		final String SUN_JAVA_COMMAND = "sun.java.command";
		final String java = System.getProperty("java.home") + "/bin/java";
		final List<String> vmArguments = ManagementFactory.getRuntimeMXBean()
				.getInputArguments();
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
		final String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND)
				.split(" ");
		if (mainCommand[0].endsWith(".jar"))
		{
			cmd.append("-jar " + new File(mainCommand[0]).getPath());
		}
		else
		{
			cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" "
					+ mainCommand[0]);
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
	
	/**
	 * @param localQuestions
	 */
	public void setLocalQuestions(final Questions localQuestions)
	{
		if (localQuestions == null) { return; }
		this.questions = localQuestions;
	}
	
	public boolean shouldUpdateQuestions()
	{
		try
		{
			return Game.getRemoteQuestions().getVersion() > this.getQuestions()
					.getVersion();
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
		this.changeState(State.RUNNING);
		Game.logger
		.info("Things seem to be working.  If you're seeing this, it means that things haven't completely broken yet.");
	}
}
