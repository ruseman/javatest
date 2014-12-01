package xyz.voxio.jtest.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.Util;
import xyz.voxio.jtest.game.Choice;

public final class AppFrame extends JFrame
{

	/**
	 *
	 */
	private static final long	serialVersionUID	= 4593115469915216836L;
	
	private final JPanel		contentPane;

	private final JEditorPane	questionPane;

	private final JEditorPane	scorePane;
	
	/**
	 * Create the frame.
	 */
	public AppFrame()
	{
		this.setTitle(Game.getNewTitle());
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
				Game.instance().restartApplication();
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
				Game.instance().shutdown();
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
				Util.openWebpage(Game.REPO);
			}
		});
		mnMeta.add(mntmSourceCode);
		
		final JMenuItem mntmIssues = new JMenuItem("Issues");
		mntmIssues.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				Util.openWebpage(Game.ISSUES);
			}
		});
		mnMeta.add(mntmIssues);
		
		final JMenuItem mntmContriboot = new JMenuItem("Contriboot");
		mntmContriboot.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				Util.openWebpage(Game.PULL_REQUESTS);
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
				Game.instance().showAboutWindow();
			}
		});
		mnMeta.add(mntmAbout);
		
		final JButton btnA = new JButton("A");
		btnA.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				Game.instance()
						.getPlayer()
						.choose(Game.instance().getQuestions()
								.getCurrentQuestion(), Choice.A);
				AppFrame.this.refresh();
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
				Game.instance()
						.getPlayer()
						.choose(Game.instance().getQuestions()
								.getCurrentQuestion(), Choice.B);
				AppFrame.this.refresh();
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
				Game.instance()
						.getPlayer()
						.choose(Game.instance().getQuestions()
								.getCurrentQuestion(), Choice.C);
				AppFrame.this.refresh();
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
				Game.instance()
						.getPlayer()
						.choose(Game.instance().getQuestions()
								.getCurrentQuestion(), Choice.D);
				AppFrame.this.refresh();
				
			}
		});
		btnD.setBounds(120, 370, 100, 45);
		this.contentPane.add(btnD);
		
		this.questionPane = new JEditorPane();
		this.questionPane.setBounds(15, 37, 467, 257);
		this.contentPane.add(this.questionPane);
		
		final JButton btnRun = new JButton("Run");
		btnRun.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				Game.instance().getPlayer().run();
				AppFrame.this.refresh();
			}
		});
		btnRun.setBounds(382, 370, 100, 45);
		this.contentPane.add(btnRun);
		
		this.scorePane = new JEditorPane();
		this.scorePane.setBounds(230, 314, 142, 101);
		this.contentPane.add(this.scorePane);
	}

	@Override
	public JPanel getContentPane()
	{
		return this.contentPane;
	}

	public JEditorPane getQuestionPane()
	{
		return this.questionPane;
	}

	public JEditorPane getScorePane()
	{
		return this.scorePane;
	}

	public void refresh()
	{
		this.getContentPane().repaint();
	}
}
