package xyz.voxio.jtest;

import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class Logger extends java.util.logging.Logger
{
	private static Logger	instance;
	
	public static Logger getInstance()
	{
		return Logger.instance;
	}

	public static Logger getNewLogger(final String string)
	{
		return (Logger) java.util.logging.Logger.getLogger(string);
	}
	
	public static void initialize()
	{
		Logger.setInstance(Logger.getNewLogger(Logger.class.getCanonicalName()));
	}
	
	public static void setInstance(final Logger instance)
	{
		Logger.instance = instance;
	}
	
	protected Logger(final String name, final String resourceBundleName)
	{
		super(name, resourceBundleName);
	}

	@Override
	public ResourceBundle getResourceBundle()
	{
		return super.getResourceBundle();
	}

	@Override
	public String getResourceBundleName()
	{
		return super.getResourceBundleName();
	}

	@Override
	public void setFilter(Filter newFilter) throws SecurityException
	{
		super.setFilter(newFilter);
	}

	@Override
	public Filter getFilter()
	{
		return super.getFilter();
	}

	@Override
	public void log(LogRecord record)
	{
		super.log(record);
	}

	@Override
	public void log(Level level, String msg)
	{
		super.log(level, msg);
	}

	@Override
	public void log(Level level, String msg, Object param1)
	{
		super.log(level, msg, param1);
	}

	@Override
	public void log(Level level, String msg, Object[] params)
	{
		super.log(level, msg, params);
	}

	@Override
	public void log(Level level, String msg, Throwable thrown)
	{
		super.log(level, msg, thrown);
	}

	@Override
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg)
	{
		super.logp(level, sourceClass, sourceMethod, msg);
	}

	@Override
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object param1)
	{
		super.logp(level, sourceClass, sourceMethod, msg, param1);
	}

	@Override
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object[] params)
	{
		super.logp(level, sourceClass, sourceMethod, msg, params);
	}

	@Override
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Throwable thrown)
	{
		super.logp(level, sourceClass, sourceMethod, msg, thrown);
	}

	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg)
	{
		super.logrb(level, sourceClass, sourceMethod, bundleName, msg);
	}

	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object param1)
	{
		super.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
	}

	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object[] params)
	{
		super.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
	}

	@Override
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Throwable thrown)
	{
		super.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
	}

	@Override
	public void entering(String sourceClass, String sourceMethod)
	{
		super.entering(sourceClass, sourceMethod);
	}

	@Override
	public void entering(String sourceClass, String sourceMethod, Object param1)
	{
		super.entering(sourceClass, sourceMethod, param1);
	}

	@Override
	public void entering(String sourceClass, String sourceMethod,
			Object[] params)
	{
		super.entering(sourceClass, sourceMethod, params);
	}

	@Override
	public void exiting(String sourceClass, String sourceMethod)
	{
		super.exiting(sourceClass, sourceMethod);
	}

	@Override
	public void exiting(String sourceClass, String sourceMethod, Object result)
	{
		super.exiting(sourceClass, sourceMethod, result);
	}

	@Override
	public void throwing(String sourceClass, String sourceMethod,
			Throwable thrown)
	{
		super.throwing(sourceClass, sourceMethod, thrown);
	}

	@Override
	public void severe(String msg)
	{
		super.severe(msg);
	}

	@Override
	public void warning(String msg)
	{
		super.warning(msg);
	}

	@Override
	public void info(String msg)
	{
		super.info(msg);
	}

	@Override
	public void config(String msg)
	{
		super.config(msg);
	}

	@Override
	public void fine(String msg)
	{
		super.fine(msg);
	}

	@Override
	public void finer(String msg)
	{
		super.finer(msg);
	}

	@Override
	public void finest(String msg)
	{
		super.finest(msg);
	}

	@Override
	public void setLevel(Level newLevel) throws SecurityException
	{
		super.setLevel(newLevel);
	}

	@Override
	public Level getLevel()
	{
		return super.getLevel();
	}

	@Override
	public boolean isLoggable(Level level)
	{
		return super.isLoggable(level);
	}

	@Override
	public String getName()
	{
		return super.getName();
	}

	@Override
	public void addHandler(Handler handler) throws SecurityException
	{
		super.addHandler(handler);
	}

	@Override
	public void removeHandler(Handler handler) throws SecurityException
	{
		super.removeHandler(handler);
	}

	@Override
	public Handler[] getHandlers()
	{
		return super.getHandlers();
	}

	@Override
	public void setUseParentHandlers(boolean useParentHandlers)
	{
		super.setUseParentHandlers(useParentHandlers);
	}

	@Override
	public boolean getUseParentHandlers()
	{
		return super.getUseParentHandlers();
	}

	@Override
	public java.util.logging.Logger getParent()
	{
		return super.getParent();
	}

	@Override
	public void setParent(java.util.logging.Logger parent)
	{
		super.setParent(parent);
	}
}
