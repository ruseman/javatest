package xyz.voxio.jtest;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;

import xyz.voxio.jtest.gui.AboutFrame;
import xyz.voxio.jtest.gui.AppFrame;

public class JTest
{
	private static JTest	instance;

	public static void copyRemoteToLocal(final URI local, final URI remote)
	{
		try
		{
			Files.copy(remote.toURL().openStream(), Paths.get(local),
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (final MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public static JTest instance()
	{
		return JTest.instance;
	}

	public static void main(final String[] args)
	{
		JTest.setInstance(new JTest());
		JTest.instance().initialize().start();
	}

	public static void openWebpage(final String url)
	{
		try
		{
			JTest.openWebpage(new URI(url));
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public static void openWebpage(final URI uri)
	{
		try
		{
			Desktop.getDesktop().browse(uri);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void openWebpage(final URL url)
	{
		try
		{
			JTest.openWebpage(url.toURI());
		}
		catch (final URISyntaxException e)
		{
			e.printStackTrace();
		}
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
	
	public static String readURIToString(URI uri)
	{
		try
		{
			return readRemoteToString(uri.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static final String	QUESTIONS_JSON_LOCAL	= "questions.json";

	public static final String	QUESTIONS_JSON_REMOTE	= "https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.json";

	public static final String	QUESTIONS_REPO			= "https://github.com/Commador/JavaTestQuestions";
	
	public static final String PULL_REQUESTS = "https://github.com/Commador/JavaTest/pulls";
	
	public static final String REPO = "https://github.com/Commador/JavaTest";
	
	public static final String ISSUES = "https://github.com/Commador/JavaTest/issues";
	
	public static String readURLToString(URL url)
	{
		try
		{
			return readRemoteToString(url.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readRemoteToString(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
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
