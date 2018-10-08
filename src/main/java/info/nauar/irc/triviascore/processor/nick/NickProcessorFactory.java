package info.nauar.irc.triviascore.processor.nick;

import info.nauar.irc.triviascore.Scorebot;

public class NickProcessorFactory
{
	public static NickProcessor getInstance(String type, Scorebot bot)
	{
		NickProcessor np = null;
		switch (type)
		{
		default:
			np = new DefaultNickProcessor(bot);
		}
		return np;
	}
}
