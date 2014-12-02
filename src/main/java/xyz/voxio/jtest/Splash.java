package xyz.voxio.jtest;

import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import xyz.voxio.lib.Util;

public final class Splash
{
	public static Splash getSplash()
	{
		return new Gson().fromJson(
				Util.parseStreamToString(Splash.class
						.getResourceAsStream("splash.json")), Splash.class);
	}

	private List<String>	endList;

	private List<String>	list;
	
	private List<String>	loseList;
	
	public String getRandomEndSplash()
	{
		return this.endList.get(new Random().nextInt(this.endList.size()));
	}
	
	public String getRandomLoseSplash()
	{
		return this.loseList.get(new Random().nextInt(this.loseList.size()));
	}
	
	public String getRandomSplash()
	{
		return this.list.get(new Random().nextInt(this.list.size()));
	}
}
