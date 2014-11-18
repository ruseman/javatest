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
	
	public static Logger getLogger()
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
	public void addHandler(final Handler handler) throws SecurityException
	{
		super.addHandler(handler);
	}
	
	@Override
	public void config(final String msg)
	{
		super.config(msg);
	}
	
	@Override
	public void entering(final String sourceClass, final String sourceMethod)
	{
		super.entering(sourceClass, sourceMethod);
	}
	
	@Override
	public void entering(final String sourceClass, final String sourceMethod,
			final Object param1)
	{
		super.entering(sourceClass, sourceMethod, param1);
	}
	
	@Override
	public void entering(final String sourceClass, final String sourceMethod,
			final Object[] params)
	{
		super.entering(sourceClass, sourceMethod, params);
	}
	
	@Override
	public void exiting(final String sourceClass, final String sourceMethod)
	{
		super.exiting(sourceClass, sourceMethod);
	}
	
	@Override
	public void exiting(final String sourceClass, final String sourceMethod,
			final Object result)
	{
		super.exiting(sourceClass, sourceMethod, result);
	}
	
	@Override
	public void fine(final String msg)
	{
		super.fine(msg);
	}
	
	@Override
	public void finer(final String msg)
	{
		super.finer(msg);
	}
	
	@Override
	public void finest(final String msg)
	{
		super.finest(msg);
	}
	
	@Override
	public Filter getFilter()
	{
		return super.getFilter();
	}
	
	@Override
	public Handler[] getHandlers()
	{
		return super.getHandlers();
	}
	
	@Override
	public Level getLevel()
	{
		return super.getLevel();
	}
	
	@Override
	public String getName()
	{
		return super.getName();
	}
	
	@Override
	public java.util.logging.Logger getParent()
	{
		return super.getParent();
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
	public boolean getUseParentHandlers()
	{
		return super.getUseParentHandlers();
	}
	
	@Override
	public void info(final String msg)
	{
		super.info(msg);
	}
	
	@Override
	public boolean isLoggable(final Level level)
	{
		return super.isLoggable(level);
	}
	
	@Override
	public void log(final Level level, final String msg)
	{
		super.log(level, msg);
	}
	
	@Override
	public void log(final Level level, final String msg, final Object param1)
	{
		super.log(level, msg, param1);
	}
	
	@Override
	public void log(final Level level, final String msg, final Object[] params)
	{
		super.log(level, msg, params);
	}
	
	@Override
	public void log(final Level level, final String msg, final Throwable thrown)
	{
		super.log(level, msg, thrown);
	}
	
	@Override
	public void log(final LogRecord record)
	{
		super.log(record);
	}
	
	@Override
	public void logp(final Level level, final String sourceClass,
			final String sourceMethod, final String msg)
	{
		super.logp(level, sourceClass, sourceMethod, msg);
	}
	
	@Override
	public void logp(final Level level, final String sourceClass,
			final String sourceMethod, final String msg, final Object param1)
	{
		super.logp(level, sourceClass, sourceMethod, msg, param1);
	}
	
	@Override
	public void logp(final Level level, final String sourceClass,
			final String sourceMethod, final String msg, final Object[] params)
	{
		super.logp(level, sourceClass, sourceMethod, msg, params);
	}
	
	@Override
	public void logp(final Level level, final String sourceClass,
			final String sourceMethod, final String msg, final Throwable thrown)
	{
		super.logp(level, sourceClass, sourceMethod, msg, thrown);
	}
	
	@Override
	public void removeHandler(final Handler handler) throws SecurityException
	{
		super.removeHandler(handler);
	}
	
	@Override
	public void setFilter(final Filter newFilter) throws SecurityException
	{
		super.setFilter(newFilter);
	}
	
	@Override
	public void setLevel(final Level newLevel) throws SecurityException
	{
		super.setLevel(newLevel);
	}
	
	@Override
	public void setParent(final java.util.logging.Logger parent)
	{
		super.setParent(parent);
	}
	
	@Override
	public void setUseParentHandlers(final boolean useParentHandlers)
	{
		super.setUseParentHandlers(useParentHandlers);
	}
	
	@Override
	public void severe(final String msg)
	{
		super.severe(msg);
	}
	
	@Override
	public void throwing(final String sourceClass, final String sourceMethod,
			final Throwable thrown)
	{
		super.throwing(sourceClass, sourceMethod, thrown);
	}

	@Override
	public void warning(final String msg)
	{
		super.warning(msg);
	}
}
