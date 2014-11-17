package xyz.voxio.jtest;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AppFrame extends JFrame
{
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 4593115469915216836L;

	private final JPanel		contentPane;

	/**
	 * Create the frame.
	 */
	public AppFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);
	}
	
}
