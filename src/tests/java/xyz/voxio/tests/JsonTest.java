package xyz.voxio.tests;

import com.google.gson.Gson;

/**
 * @author Tim Miller
 */
public class JsonTest
{
	public static class TestType
	{
		public int				val1;
		
		private final String	val2	= "noyoudon't";

		public TestType()
		{
			System.out.println("This constructor was called");
		}
		
		@Override
		public String toString()
		{
			return "TestType [val2=" + this.val2 + ", val1=" + this.val1 + "]";
		}
	}
	
	public static String	jsonString1	= "{\"val1\":11,\"val2\":\"disguytho\"}";
	
	public static void main(final String[] args)
	{
		final Gson gson = new Gson();
		final TestType test = gson.fromJson(JsonTest.jsonString1, TestType.class);
		System.out.println(test.toString());
	}
}
