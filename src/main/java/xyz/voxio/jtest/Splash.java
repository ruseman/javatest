package xyz.voxio.jtest;

import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import xyz.voxio.lib.Util;

/**
 * This class is instantiated from the JSON splash text file, and used only to
 * get a random splash text for the end screen and title bar
 *
 * @author Tim Miller
 */
public final class Splash
{
	/**
	 * This is the factory method for the class. It generates the object from
	 * the JSON file using the GSON reader
	 */
	public static Splash getSplash()
	{
		return new Gson().fromJson(Util.parseStreamToString(Splash.class
				.getResourceAsStream("splash.json")), Splash.class);
	}
	
	private List<String>	endList;
	
	private List<String>	list;

	private List<String>	loseList;

	/**
	 * @return a splash string used in the end screen if the player won
	 */
	public String getRandomEndSplash()
	{
		try
		{
			return this.endList.get(new Random().nextInt(this.endList.size()));
		}
		catch (final NullPointerException e)
		{
			return "Thanks for playing";
		}
	}

	/**
	 * @return a splash string used in the end screen if the player lost
	 */
	public String getRandomLoseSplash()
	{
		try
		{
			return this.loseList
					.get(new Random().nextInt(this.loseList.size()));
		}
		catch (final NullPointerException e)
		{
			return "Game over man, game over";
		}
	}

	/**
	 * @return a splash string used in the title bar
	 */
	public String getRandomSplash()
	{
		try
		{
			return this.list.get(new Random().nextInt(this.list.size()));
		}
		catch (final NullPointerException e)
		{
			return "I'm tired of writing splashes";
		}
	}
}
