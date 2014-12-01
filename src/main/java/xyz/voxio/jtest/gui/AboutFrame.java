package xyz.voxio.jtest.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

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
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);
	}

}
