package xyz.voxio.jtest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import xyz.voxio.lib.api.Application;
import xyz.voxio.lib.api.Meta;
import xyz.voxio.lib.api.Splash;
import xyz.voxio.lib.util.Bytes;
import xyz.voxio.lib.util.SwingUtilities;
import xyz.voxio.lib.util.WebUtilities;

/**
 * The main application class, using a basic singleton design, with 'most' of
 * the applications methods self contained. This class tracks all of the
 * pertinent objects, and is the closest thing I've ever come to writing a God
 * class.
 *
 * @author Tim Miller
 */
public final class Game implements Application
{
	/**
	 * This class is procedurally generated using the eclipse windowbuilder
	 */
	public final class AboutFrame extends JFrame
	{

		/**
		 *
		 */
		private static final long	serialVersionUID	= -4208952844824385757L;

		private final JPanel		contentPane;

		/**
		 * Create the frame.
		 */
		public AboutFrame()
		{
			this.setResizable(false);
			this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			this.setBounds(100, 100, 450, 300);
			this.contentPane = new JPanel();
			this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setContentPane(this.contentPane);
			this.contentPane.setLayout(new BorderLayout(0, 0));

			final JTextPane txtpnThisApplicationWas = new JTextPane();
			txtpnThisApplicationWas.setEditable(false);
			txtpnThisApplicationWas
			.setText("This application was wriiten in 2014 by Timothy Miller.  It's a simple game, where the player answers questions about Java.\r\n\r\nThe player begins with 10 points, and answers questions until they've either run out of points, answered all of the questions, or gotten bored.  A correct answer rewards a point, and an incorrect answer results in a loss of a point.\r\n\r\nThe source code is available on github, as are the questions.\r\n\r\nSource code repo: https://github.com/Commador/JavaTest\r\nQuestions repo: https://github.com/Commador/JavaTestQuestions\r\n\r\nThank you for playing");
			this.contentPane.add(txtpnThisApplicationWas, BorderLayout.CENTER);
		}

	}

	/**
	 * This class is procedurally generated using the eclipse windowbuilder
	 */
	public final class AppFrame extends JFrame
	{

		/**
		 *
		 */
		private static final long	serialVersionUID	= 4593115469915216836L;

		private final JPanel		contentPane;

		private final JTextPane		questionPane;

		private final JTextPane		scorePane;

		/**
		 * Create the frame.
		 *
		 * @throws URISyntaxException
		 * @throws IOException
		 * @throws JsonSyntaxException
		 */
		public AppFrame() throws JsonSyntaxException, IOException,
		URISyntaxException
		{
			this.setResizable(false);
			this.setTitle(Game.this.getNewTitle());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBounds(100, 100, 500, 460);
			this.contentPane = new JPanel();
			this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setContentPane(this.contentPane);
			this.contentPane.setLayout(null);

			final JMenuBar menuBar = new JMenuBar();
			menuBar.setBounds(5, 5, 482, 21);
			this.contentPane.add(menuBar);

			final JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);

			final JMenuItem mntmRefreshApplication = new JMenuItem(
					"Refresh application");
			mntmRefreshApplication.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					Game.this.restart();
				}
			});
			mnFile.add(mntmRefreshApplication);

			final JSeparator sep0 = new JSeparator();
			mnFile.add(sep0);

			final JMenuItem mntmExit = new JMenuItem("Exit...");
			mntmExit.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					Game.this.shutdown();
				}
			});
			mnFile.add(mntmExit);

			final JMenu mnMeta = new JMenu("Meta");
			menuBar.add(mnMeta);

			final JMenuItem mntmSourceCode = new JMenuItem("Source Code");
			mntmSourceCode.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					WebUtilities.openWebpage(Game.REPO);
				}
			});
			mnMeta.add(mntmSourceCode);

			final JMenuItem mntmIssues = new JMenuItem("Issues");
			mntmIssues.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					WebUtilities.openWebpage(Game.ISSUES);
				}
			});
			mnMeta.add(mntmIssues);

			final JMenuItem mntmContriboot = new JMenuItem("Contriboot");
			mntmContriboot.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					WebUtilities.openWebpage(Game.PULL_REQUESTS);
				}
			});
			mnMeta.add(mntmContriboot);

			final JSeparator sep1 = new JSeparator();
			mnMeta.add(sep1);

			final JMenuItem mntmAbout = new JMenuItem("About");
			mntmAbout.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					Game.this.showAboutWindow();
				}
			});
			mnMeta.add(mntmAbout);

			final JButton btnA = new JButton("A");
			btnA.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					try
					{
						Game.this.getPlayer().choose(
								Game.this.getQuestions().getCurrentQuestion(),
								Choice.A);
					}
					catch (final IOException e1)
					{
						e1.printStackTrace();
					}
					catch (final URISyntaxException e1)
					{
						e1.printStackTrace();
					}
					AppFrame.this.repaint();
				}
			});
			btnA.setBounds(10, 314, 100, 45);
			this.contentPane.add(btnA);

			final JButton btnB = new JButton("B");
			btnB.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					try
					{
						Game.this.getPlayer().choose(
								Game.this.getQuestions().getCurrentQuestion(),
								Choice.B);
					}
					catch (final IOException e1)
					{
						e1.printStackTrace();
					}
					catch (final URISyntaxException e1)
					{
						e1.printStackTrace();
					}
					AppFrame.this.repaint();
				}
			});
			btnB.setBounds(120, 314, 100, 45);
			this.contentPane.add(btnB);

			final JButton btnC = new JButton("C");
			btnC.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					try
					{
						Game.this.getPlayer().choose(
								Game.this.getQuestions().getCurrentQuestion(),
								Choice.C);
					}
					catch (final IOException e1)
					{
						e1.printStackTrace();
					}
					catch (final URISyntaxException e1)
					{
						e1.printStackTrace();
					}
					AppFrame.this.repaint();
				}
			});
			btnC.setBounds(10, 370, 100, 45);
			this.contentPane.add(btnC);

			final JButton btnD = new JButton("D");
			btnD.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					try
					{
						Game.this.getPlayer().choose(
								Game.this.getQuestions().getCurrentQuestion(),
								Choice.D);
					}
					catch (final IOException e1)
					{
						e1.printStackTrace();
					}
					catch (final URISyntaxException e1)
					{
						e1.printStackTrace();
					}
					AppFrame.this.repaint();

				}
			});
			btnD.setBounds(120, 370, 100, 45);
			this.contentPane.add(btnD);

			this.questionPane = new JTextPane();
			this.questionPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
			this.questionPane.setEditable(false);
			this.questionPane.setBounds(15, 37, 467, 257);
			this.contentPane.add(this.questionPane);

			this.scorePane = new JTextPane();
			this.scorePane.setFont(new Font("Arial Black", (this.scorePane
					.getFont().getStyle() & ~Font.ITALIC) | Font.BOLD, 58));
			this.scorePane.setEditable(false);
			this.scorePane.setBounds(230, 314, 142, 101);
			{
				final StyledDocument doc = this.scorePane.getStyledDocument();
				final SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants
				.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
			}
			this.contentPane.add(this.scorePane);
			this.repaint();
		}

		@Override
		public JPanel getContentPane()
		{
			return this.contentPane;
		}

		public String getQuestionPaneText()
		{
			return this.questionPane.getText();
		}

		public String getScorePaneText()
		{
			return this.scorePane.getText();
		}

		@Override
		public void repaint()
		{
			try
			{
				this.setQuestionPaneText(Game.this.getQuestions()
						.getCurrentQuestion().getQuestionFormatted());
			}
			catch (final IOException e1)
			{
				e1.printStackTrace();
			}
			catch (final URISyntaxException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				this.setScorePaneText("" + Game.this.getPlayer().getScore());
			}
			catch (final Exception e)
			{
				this.setScorePaneText("");
				EventQueue.invokeLater(new Runnable(){public void run() {AppFrame.this.repaint();}});
			}
			super.repaint();
		}

		public void setQuestionPaneText(final String text_1)
		{
			this.questionPane.setText(text_1);
		}

		public void setScorePaneText(final String text)
		{
			this.scorePane.setText(text);
		}
	}

	/**
	 * An enumerated type for the 4 possible choices for the questions, i.e. A,
	 * B, C, D
	 *
	 * @author Tim Miller
	 */
	public static enum Choice
	{
		A, B, C, D;

		/**
		 * @return the number corresponding to the given choice
		 */
		public synchronized int toIndex()
		{
			switch (this)
			{
				case A:
					return 0;
				case B:
					return 1;
				case C:
					return 2;
				case D:
					return 3;
			}
			return 0;
		}
	}

	/**
	 * This class is procedurally generated using the eclipse windowbuilder
	 */
	public final class EndFrame extends JFrame
	{
		/**
		 *
		 */
		private static final long	serialVersionUID	= 5487569515468347037L;

		private final JPanel		contentPane;

		private final JTextPane		textPane;

		/**
		 * Create the frame.
		 */
		protected EndFrame()
		{
			this.setResizable(false);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setBounds(100, 100, 450, 300);
			this.contentPane = new JPanel();
			this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setContentPane(this.contentPane);
			this.contentPane.setLayout(null);

			this.textPane = new JTextPane();
			this.textPane.setEditable(false);
			this.textPane.setBounds(5, 5, 432, 222);
			this.textPane.setFont(new Font("Arial Black", (this.textPane
					.getFont().getStyle() & ~Font.ITALIC) | Font.BOLD, 25));
			final StyledDocument doc = this.textPane.getStyledDocument();
			final SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
			this.contentPane.add(this.textPane);

			final JButton endGameButton = new JButton("End");
			endGameButton.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					Game.this.shutdown();
				}
			});
			endGameButton.setBounds(5, 238, 89, 23);
			this.contentPane.add(endGameButton);

			final JButton playAgainButton = new JButton("Play Again");
			playAgainButton.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(final MouseEvent e)
				{
					Game.this.restart();
				}
			});
			playAgainButton.setBounds(345, 238, 89, 23);
			this.contentPane.add(playAgainButton);
		}

		public EndFrame(final String endMessage)
		{
			this();
			this.setTextPaneText(endMessage);
			this.setTitle("Thanks for playing");
		}

		public String getTextPaneText()
		{
			return this.textPane.getText();
		}

		@Override
		public String getTitle()
		{
			return super.getTitle();
		}

		public void setTextPaneText(final String text)
		{
			this.textPane.setText(text);
		}

		@Override
		public void setTitle(final String title)
		{
			super.setTitle(title);
		}
	}

	public final class Player
	{
		/**
		 * The starting score
		 */
		public static final int	STARTING_SCORE	= 10;

		/**
		 * The score
		 */
		private int				score			= Player.STARTING_SCORE;

		/**
		 * Determines whether or not the choice was correct, and performs the
		 * appropriate actions
		 *
		 * @param question
		 *            the question
		 * @param choice
		 *            the choice
		 * @throws URISyntaxException
		 * @throws IOException
		 */
		public void choose(final Question question, final Choice choice)
				throws IOException, URISyntaxException
		{
			Game.LOGGER.info("Choice:" + choice.toString() + " chosen");
			final boolean correct = question.correct(choice);
			if (correct)
			{
				Game.LOGGER.info("CORRECT");
				this.correctAnswer();
			}
			else
			{
				Game.LOGGER.info("INCORRECT");
				this.incorrectAnswer();
			}
			if (this.score <= 0)
			{
				this.score = 0;
				Game.this.endGame(Reason.OUT_OF_POINTS);
			}
			Game.this.getQuestions().nextQuestion();
		}

		/**
		 * Increments the score by 1
		 */
		public void correctAnswer()
		{
			this.score++;
		}

		/**
		 * @return the score
		 */
		public int getScore()
		{
			return this.score;
		}

		/**
		 * Decrements the score
		 */
		private void incorrectAnswer()
		{
			this.score--;
		}

		/**
		 * Part of the unfinished RUN feature
		 */
		public void run()
		{
			Game.LOGGER.info("RUN selected");
		}
	}

	public final class Question
	{

		/**
		 * The four possible answers, in no particular order
		 */
		private List<String>	answers	= null;

		/**
		 * The correct answer
		 */
		private String			correct	= null;

		/**
		 * The numeric id for the question, used in debugging
		 */
		private int				id		= Integer.MIN_VALUE;

		/**
		 * The prompt for the question
		 */
		private String			prompt	= null;

		public Question()
		{
			this.id = Game.questionCount;
			Game.questionCount++;
		}

		/**
		 * @param choice
		 *            the choice
		 * @return whether or not the choice is correct
		 */
		public boolean correct(final Choice choice)
		{
			return this.correct.equals(this.answers.get(choice.toIndex()));
		}

		/**
		 * @return a list of the answers
		 */
		public List<String> getAnswers()
		{
			return this.answers;
		}

		/**
		 * @return the correct answer
		 */
		public String getCorrectAnswer()
		{
			return this.correct;
		}

		/**
		 * @return the prompt
		 */
		public String getPrompt()
		{
			return this.prompt;
		}

		/**
		 * @return the question in it's fully formatted format, with the answers
		 *         and linebreaks and stuff
		 */
		public String getQuestionFormatted()
		{
			return this.prompt + "\n\nA: " + this.answers.get(0) + "\nB: "
					+ this.answers.get(1) + "\nC: " + this.answers.get(2)
					+ "\nD: " + this.answers.get(3);
		}

		/**
		 * Initializes the question object, assigning the correct answer and
		 * shuffling the questions
		 */
		public void initialize()
		{
			Game.LOGGER.info("Question:" + this.id + " is being initialized");
			this.correct = this.answers.get(0);
			Collections.shuffle(this.answers);
		}

		protected void setAnswers(final List<String> list)
		{
			this.answers = list;
		}

		protected void setCorrect(final String string)
		{
			this.correct = string;
		}

		protected void setPrompt(final String string)
		{
			this.prompt = string;
		}
	}

	public final class Questions
	{
		/**
		 * The current question
		 */
		private int				index	= 0;

		/**
		 * The questions
		 */
		public List<Question>	questions;

		/**
		 * The version
		 */
		private int				version;

		public Questions()
		{

		}

		/**
		 * @return the current question number
		 */
		public int getCurrentNum()
		{
			return this.index + 1;
		}

		/**
		 * @return the current question
		 * @throws URISyntaxException
		 * @throws IOException
		 */
		public Question getCurrentQuestion() throws IOException,
				URISyntaxException
		{
			try
			{
				return this.questions.get(this.index);
			}
			catch (final IndexOutOfBoundsException e)
			{
				this.index--;
				Game.this.endGame(Reason.NO_MORE_QUESTIONS);
				return this.getCurrentQuestion();
			}
		}

		/**
		 * @return the questions
		 */
		public List<Question> getQuestion()
		{
			return this.questions;
		}

		/**
		 * @return the total number of questions
		 */
		public int getTotalNum()
		{
			return this.questions.size();
		}

		/**
		 * @return the version
		 */
		public int getVersion()
		{
			return this.version;
		}

		/**
		 * Moves the index to the next question
		 */
		public void nextQuestion()
		{
			this.index++;
		}
	}

	/**
	 * The enumerated class for the possible reasons for ending the game, of
	 * which there are only two.
	 *
	 * @author Tim Miller
	 */
	public static enum Reason
	{
		NO_MORE_QUESTIONS, OUT_OF_POINTS;
	}

	/**
	 * The enumerated class for the possible lifecycle states of the application
	 *
	 * @author Tim Miller
	 */
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
	public static final Logger	LOGGER					= Logger.getLogger(Game.class
			.getCanonicalName());

	/**
	 * The web address for the pull requests page
	 */
	public static final String	PULL_REQUESTS			= "https://github.com/Commador/JavaTest/pulls";

	/**
	 * The number of questions, starting at 1
	 */
	private static int			questionCount			= 1;

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
	 * Launches the application
	 */
	public static void main(final String[] args)
	{
		final Game game = new Game();
		game.initialize(args);
		game.start();
	}

	/**
	 * The "about" window
	 */
	private AboutFrame		aboutFrame;

	/**
	 * The primary application window
	 */
	private AppFrame		appFrame;

	/**
	 * The end game application window
	 */
	private EndFrame		endFrame;

	private final Splash	endList		= Splash.getSplash(Game.class,
												"end.json");

	private final Splash	loseList	= Splash.getSplash(Game.class,
												"lose.json");

	/**
	 * The player object
	 */
	private Player			player;

	/**
	 * The local questions
	 */
	private Questions		questions;

	private final Splash	splashList	= Splash.getSplash(Game.class,
												"splash.json");

	/**
	 * The current lifecycle state
	 */
	private State			state;

	private Game()
	{

	}

	/**
	 * Changes the state and logs it to the console
	 *
	 * @param state
	 *            the new state
	 */
	public void changeState(final State state)
	{
		if ((state == null) || (state == this.state))
		{
			return;
		}
		if (this.state == null)
		{
			this.state = state;
			Game.LOGGER.info("Game state is now " + this.state.toString());
			return;
		}
		Game.LOGGER.info("Game state changing from " + this.state.toString()
				+ " to " + state.toString());
		this.state = state;
	}

	/**
	 * Deletes the questions
	 */
	public void clearQuestions()
	{
		this.questions = null;
	}

	/**
	 * Clones the questions from the remote to the harddisc
	 */
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

	/**
	 * Configure the questions object
	 */
	public void configQuestions()
	{
		for (final Question question : this.questions.questions)
		{
			question.initialize();
		}
		Collections.shuffle(this.questions.questions);
	}

	/**
	 * Create the three gui frames
	 */
	private void createFrames()
	{
		try
		{
			this.appFrame = new AppFrame();
		}
		catch (final JsonSyntaxException e)
		{
			e.printStackTrace();
			this.restart();

		}
		catch (final IOException e)
		{
			e.printStackTrace();
			this.restart();

		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
			this.restart();
		}
		this.endFrame = new EndFrame();
		this.aboutFrame = new AboutFrame();
	}

	/**
	 * Ends the game
	 *
	 * @param reason
	 *            the reason for ending
	 */
	public void endGame(final Reason reason) throws IOException,
	URISyntaxException
	{
		String endMessage = "";
		switch (reason)
		{
			case NO_MORE_QUESTIONS:
				endMessage = "You completed all of the questions!\n"
						+ this.endList.getRandomSplash();
				break;
			case OUT_OF_POINTS:
				endMessage = "You ran out of points!\n"
						+ this.loseList.getRandomSplash();
				break;
		}
		final EndFrame frame = new EndFrame(endMessage);
		frame.setVisible(true);
	}

	/**
	 * @return the end frame
	 */
	public EndFrame getEndFrame()
	{
		return this.endFrame;
	}

	/**
	 * @return a new instance of the local questions file
	 */
	public File getLocalQuestionsFile()
	{
		return new File(Game.QUESTIONS_JSON_LOCAL);
	}

	/**
	 * @return a new title, formatted, with a random splash appended to the end
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	public String getNewTitle() throws JsonSyntaxException, IOException,
	URISyntaxException
	{
		return Game.TITLE + " - " + this.splashList.getRandomSplash();
	}

	/**
	 * @return the player object
	 */
	public Player getPlayer()
	{
		return this.player;
	}

	/**
	 * @return the local questions
	 * @throws IOException
	 *             if the IO is exceptional
	 */
	public Questions getQuestions()
	{
		try
		{
			if (this.questions != null)
			{
				return this.questions;
			}
			final String json = Bytes.fromFile(
					new File(Game.QUESTIONS_JSON_LOCAL)).toString();
			final Gson gson = new Gson();
			final Questions newQ = gson.fromJson(json, Questions.class);
			this.questions = newQ;
		}
		catch (final JsonSyntaxException e)
		{
			e.printStackTrace();
			this.restart();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			this.restart();
		}
		return this.getQuestions();
	}

	/**
	 * @return the remote questions
	 */
	public Questions getRemoteQuestions()
	{
		try
		{
			final String json = WebUtilities.parseURLtoString(new URL(
					Game.QUESTIONS_JSON_REMOTE));
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
	 * @return the current state
	 */
	public State getState()
	{
		return this.state;
	}

	/**
	 * Initializes the application, creating the necessary objects
	 */
	public void initialize(String[] args)
	{
		this.changeState(State.INITIALIZING);
		this.args = args;
		this.registerHooks();
		Game.LOGGER.info("Loading the questions");
		try
		{
			this.loadQuestions();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			this.restart();
		}
		this.configQuestions();
		Game.LOGGER.info("Questions have been loaded");
		Game.LOGGER.info("Creating the GUI");
		Game.this.createFrames();
		Game.this.appFrame.setVisible(true);
		Game.LOGGER.info("Creating a few objects...");
		this.player = new Player();
		new File("questions.json").deleteOnExit();
	}

	/**
	 * Determines whether or not the questions need to be updated, updates them,
	 * and then loads them as an instance of {@link Questions}
	 *
	 * @return the Questions
	 * @throws IOException
	 *             just in case
	 */
	public Questions loadQuestions() throws IOException
	{
		final boolean connection = WebUtilities.isInternetReachable();
		if (connection)
		{
			this.cloneQuestions();
			return this.getQuestions();
		}
		else
		{
			SwingUtilities
			.infoBox(
					"There is no internet connection.\nThe application must now close.",
					"Error");
			this.shutdown();
			return null;
		}
	}

	@Override
	public Meta meta()
	{
		return Meta.getMeta(Game.class, "meta.json");
	}

	/**
	 * Registers the game hooks, which used to consist of the exception handler,
	 * shutdown hook, and a few threads to be ran after initialization, but now
	 * consists only of the exception handler
	 */
	public void registerHooks()
	{
		Game.LOGGER.info("Registering hooks and handlers");
		Game.LOGGER.info("Registering the global event handler");
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{

			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				e.printStackTrace();
				Game.LOGGER.info("Restarting due to an error...");
				restart();
			}
		});
	}

	/**
	 * Restarts the application. this sometimes doesn't work, depending upon the
	 * user's java configuration
	 */
	@Override
	public void restart()
	{
		try
		{
			final StringBuilder cmd = new StringBuilder();
			cmd.append(System.getProperty("java.home") + File.separator + "bin"
					+ File.separator + "java ");
			for (final String jvmArg : ManagementFactory.getRuntimeMXBean()
					.getInputArguments())
			{
				cmd.append(jvmArg + " ");
			}
			cmd.append("-cp ")
			.append(ManagementFactory.getRuntimeMXBean().getClassPath())
			.append(" ");
			cmd.append(Game.class.getName()).append(" ");
			for (final String arg : args)
			{
				cmd.append(arg).append(" ");
			}
			Runtime.getRuntime().exec(cmd.toString());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	private String[] args;

	/**
	 * @return whether or not the application should update the questions, used
	 *         back when the questions were cached locally
	 */
	public boolean shouldUpdateQuestions()
	{
		try
		{
			return this.getRemoteQuestions().getVersion() > this.getQuestions()
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
	@Override
	public void shutdown()
	{
		this.changeState(State.SHUTTING_DOWN);
		System.exit(0);
	}

	/**
	 * Start the game
	 */
	@Override
	public void start()
	{
		EventQueue
				.invokeLater(new Runnable(){public void run()
				{
					Game.this.changeState(State.RUNNING);
					Game.LOGGER
							.info("Things seem to be working.  If you're seeing this, it means that things haven't completely broken yet.");
				}});
	}

}
