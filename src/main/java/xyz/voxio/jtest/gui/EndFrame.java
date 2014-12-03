package xyz.voxio.jtest.gui;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import xyz.voxio.jtest.Game;

public class EndFrame extends JFrame
{
	/**
	 *
	 */
	private static final long	serialVersionUID	= 5487569515468347037L;

	public static EndFrame getNewInstance(final String endMessage)
	{
		final EndFrame frame = new EndFrame();
		frame.setTextPaneText(endMessage);
		frame.setTitle("Thanks for playing");
		return frame;
	}
	
	private final JPanel	contentPane;
	
	private final JTextPane	textPane;
	
	/**
	 * Create the frame.
	 */
	public EndFrame()
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
		this.textPane.setFont(new Font("Arial Black", (this.textPane.getFont().getStyle() & ~Font.ITALIC) | Font.BOLD, 25));
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
				Game.instance().shutdown();
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
				Game.instance().restartApplication();
			}
		});
		playAgainButton.setBounds(345, 238, 89, 23);
		this.contentPane.add(playAgainButton);
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
