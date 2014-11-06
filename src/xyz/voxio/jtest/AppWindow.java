package xyz.voxio.jtest;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class AppWindow
{
	private JFrame	frmJtest;
	
	/**
	 * Create the application.
	 */
	public AppWindow()
	{
		this.initialize();
	}
	
	public void enable()
	{
		this.frmJtest.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		this.frmJtest = new JFrame();
		this.frmJtest.setTitle("JTest");
		this.frmJtest.setBounds(100, 100, 450, 300);
		this.frmJtest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel panel = new JPanel();
		this.frmJtest.getContentPane().add(panel, BorderLayout.CENTER);
		
		final JMenuBar menuBar = new JMenuBar();
		this.frmJtest.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		final JMenu mnJtest = new JMenu("JTest");
		menuBar.add(mnJtest);
		
		final JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.exit();
			}
		});
		mnJtest.add(mntmExit);
		
		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		final JMenuItem mntmSource = new JMenuItem("Source");
		mntmSource.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.openWebPage(JTest.SOURCE_URI);
			}
		});
		mnHelp.add(mntmSource);
		
		final JMenuItem mntmIssues = new JMenuItem("Issues");
		mntmIssues.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.openWebPage(JTest.ISSUES_URI);
			}
		});
		mnHelp.add(mntmIssues);
		
		final JSeparator separator0 = new JSeparator();
		mnHelp.add(separator0);
		
		final JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.showAboutWindow();
			}
		});
		mnHelp.add(mntmAbout);
		
		final JSeparator separator1 = new JSeparator();
		mnHelp.add(separator1);
		
		final JMenuItem mntmGithub = new JMenuItem("GitHub");
		mntmGithub.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				JTest.openWebPage(JTest.GITHUB_URI);
			}
		});
		mnHelp.add(mntmGithub);
	}
	
}
