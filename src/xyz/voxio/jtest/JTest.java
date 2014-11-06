package xyz.voxio.jtest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class JTest
{

	public enum State
	{
		CLOSING, INITIALIZING, RUNNING;
	}
	
	public static JTest		instance;
	
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

	private JFrame	frame;

	private State	state;

	/**
	 * Create the application.
	 */
	public JTest()
	{
		this.initialize();
	}

	public void exit()
	{
		System.exit(0);
	}

	public State getState()
	{
		return this.state;
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

		final JMenu menuJTest = new JMenu("JavaTest");
		menuBar.add(menuJTest);

		final JMenuItem menuButtonExit = new JMenuItem("Exit");
		menuButtonExit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				JTest.this.exit();
			}
		});
		menuButtonExit.setEnabled(false);
		menuJTest.add(menuButtonExit);
		
		this.setState(State.RUNNING);
	}
	
	private void setState(final State state)
	{
		this.state = state;
	}
}
