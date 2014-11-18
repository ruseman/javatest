package xyz.voxio.jtest.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Tim Miller
 */
public class ErrorFrame extends JFrame
{
	private static final long	serialVersionUID	= 8945276316837710237L;

	private final JPanel		contentPane;
	
	/**
	 * Create the frame.
	 */
	public ErrorFrame()
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 250, 50);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));

		final JLabel lblSorryTheGame = new JLabel("  Sorry, the application has crashed :(");
		this.contentPane.add(lblSorryTheGame, BorderLayout.CENTER);
	}
}
