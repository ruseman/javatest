package xyz.voxio.jtest.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

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
		txtpnThisApplicationWas.setText("This application was wriiten in 2014 by Timothy Miller.  It's a simple game, where the player answers questions about Java.\r\n\r\nThe player begins with 10 points, and answers questions until they've either run out of points, answered all of the questions, or gotten bored.  A correct answer rewards a point, and an incorrect answer results in a loss of a point.\r\n\r\nThe source code is available on github, as are the questions.\r\n\r\nSource code repo: https://github.com/Commador/JavaTest\r\nQuestions repo: https://github.com/Commador/JavaTestQuestions\r\n\r\nThank you for playing");
		this.contentPane.add(txtpnThisApplicationWas, BorderLayout.CENTER);
	}

}
