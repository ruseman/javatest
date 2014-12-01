package xyz.voxio.jtest;

import java.util.List;
import java.util.Random;

public final class Splash
{
	public List<String>	list;
	
	public String getRandomSplash()
	{
		return this.list.get(new Random().nextInt(this.list.size()));
	}
}
