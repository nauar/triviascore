package info.nauar.irc.triviabot.processor.nick;

import info.nauar.irc.triviabot.Scorebot;

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
