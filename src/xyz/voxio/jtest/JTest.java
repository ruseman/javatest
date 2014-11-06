package xyz.voxio.jtest;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.io.FileUtils;

public class JTest
{
	
	public enum State
	{
		CLOSING, INITIALIZING, RUNNING;
	}

	public static String		GITHUB_URI					= ("https://github.com");

	public static JTest			instance;
	
	public static String		ISSUES_URI					= ("https://github.com/Commador/JavaTest/issues");
	
	public static String		QUESTIONS_FILE_URI			= "questions.cfg";

	public static final String	QUESTIONS_FILE_URI_REMOTE	= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.cfg";
	
	public static String		SOURCE_URI					= ("https://github.com/Commador/JavaTest");

	public static String		VERSION						= "1.0.0";
	
	public static void exitAll()
	{
		System.exit(0);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args)
	{
		EventQueue.invokeLater(new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					JTest.instance = new JTest();
					JTest.instance.frame.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
			
			@Override
			public void start()
			{
				this.setName("gui_thread");
				super.start();
			}
		});
	}

	public List<Question>	questions;

	private JFrame			frame;
	
	private State			state;

	/**
	 * Create the application.
	 */
	public JTest()
	{
		this.initialize();
	}
	
	public void exit()
	{
		this.setState(State.CLOSING);
		JTest.exitAll();
	}
	
	public State getState()
	{
		return this.state;
	}
	
	private List<Question> getQuestions() throws URISyntaxException,
			IOException
	{
		final List<Question> questions = new ArrayList<Question>();
		final File qfile = new File(new URI(JTest.QUESTIONS_FILE_URI));
		if (!qfile.exists())
		{
			FileUtils.copyURLToFile(
					new URI(JTest.QUESTIONS_FILE_URI_REMOTE).toURL(), qfile);
		}
		return questions;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		this.setState(State.INITIALIZING);
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JMenuBar menuBar = new JMenuBar();
		this.frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		final JMenu menuJavaTest = new JMenu("JavaTest");
		menuBar.add(menuJavaTest);
		
		final JMenuItem menuButtonExit = new JMenuItem("Exit");
		menuButtonExit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.this.exit();
			}
		});
		menuJavaTest.add(menuButtonExit);
		
		final JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		final JMenuItem menuButtonSource = new JMenuItem("Source");
		menuButtonSource.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(JTest.SOURCE_URI));
				}
				catch (final IOException e1)
				{
					e1.printStackTrace();
				}
				catch (final URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		final JMenuItem menuButtonGithub = new JMenuItem("Github");
		menuButtonGithub.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(JTest.GITHUB_URI));
				}
				catch (final IOException e1)
				{
					e1.printStackTrace();
				}
				catch (final URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		menuHelp.add(menuButtonGithub);
		menuHelp.add(menuButtonSource);
		
		final JMenuItem menuButtonIssues = new JMenuItem("Issues");
		menuButtonIssues.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI(JTest.ISSUES_URI));
				}
				catch (final IOException e1)
				{
					e1.printStackTrace();
				}
				catch (final URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		menuHelp.add(menuButtonIssues);
		try
		{
			this.questions = this.getQuestions();
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
		}
		this.setState(State.RUNNING);
	}
	
	private void setState(final State state)
	{
		this.state = state;
	}
}
