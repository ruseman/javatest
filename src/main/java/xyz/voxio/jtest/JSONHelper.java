package xyz.voxio.jtest;

import java.util.Map;

import com.google.gson.Gson;

public final class JSONHelper
{
	private JSONHelper()
	{

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapFromJSON(final String json)
	{
		final Gson gson = new Gson();
		return (Map<String, Object>) gson.fromJson(json, Object.class);
	}
}
