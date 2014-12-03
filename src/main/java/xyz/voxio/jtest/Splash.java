package xyz.voxio.jtest;

import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import xyz.voxio.lib.Util;

public final class Splash
{
	public static Splash getSplash()
	{
		return new Gson().fromJson(Util.parseStreamToString(Splash.class.getResourceAsStream("splash.json")), Splash.class);
	}

	private List<String>	endList;

	private List<String>	list;
	
	private List<String>	loseList;
	
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
	
	public String getRandomLoseSplash()
	{
		try
		{
			return this.loseList.get(new Random().nextInt(this.loseList.size()));
		}
		catch (final NullPointerException e)
		{
			return "Game over man, game over";
		}
	}
	
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
