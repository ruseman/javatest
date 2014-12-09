package xyz.voxio.jtest.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.google.gson.JsonSyntaxException;

import xyz.voxio.jtest.Game;
import xyz.voxio.jtest.game.Question.Choice;
import xyz.voxio.lib.util.WebUtilities;

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
				try
				{
					Game.instance()
							.getPlayer()
							.choose(Game.instance().getQuestions()
									.getCurrentQuestion(), Choice.A);
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
					Game.instance()
							.getPlayer()
							.choose(Game.instance().getQuestions()
									.getCurrentQuestion(), Choice.B);
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
					Game.instance()
							.getPlayer()
							.choose(Game.instance().getQuestions()
									.getCurrentQuestion(), Choice.C);
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
					Game.instance()
							.getPlayer()
							.choose(Game.instance().getQuestions()
									.getCurrentQuestion(), Choice.D);
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
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
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
			this.setQuestionPaneText(Game.instance().getQuestions()
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
			this.setScorePaneText("" + Game.instance().getPlayer().getScore());
		}
		catch (final Exception e)
		{
			this.setScorePaneText("");
			EventQueue.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					AppFrame.this.repaint();
				}
			});
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
