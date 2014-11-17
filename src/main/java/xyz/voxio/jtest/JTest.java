package xyz.voxio.jtest;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;

public class JTest
{
	private static JTest	instance;

	public static JTest instance()
	{
		return JTest.instance;
	}
	
	public static void main(final String[] args)
	{
		JTest.setInstance(new JTest());
		JTest.instance().initialize().start();
	}
	
	private static void setInstance(final JTest instance)
	{
		JTest.instance = instance;
	}
	
	private JFrame	aboutWindow;
	
	private JFrame	appWindow;

	public void cleanup()
	{

	}
	
	public static void openWebpage(String url)
	{
		try
		{
			openWebpage(new URI(url));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void openWebpage(URI uri)
	{
		try
		{
			Desktop.getDesktop().browse(uri);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void openWebpage(URL url)
	{
		try
		{
			openWebpage(url.toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public JTest initialize()
	{
		// TODO load questions
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					JTest.this.appWindow = new AppFrame();
					JTest.this.aboutWindow = new AboutFrame();
					JTest.this.appWindow.setVisible(true);
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		return this;
	}

	public void showAboutWindow()
	{
		this.aboutWindow.setVisible(true);
	}
	
	public void shutdown()
	{
		this.cleanup();
		System.exit(0);
	}

	public void start()
	{
		
	}
}
