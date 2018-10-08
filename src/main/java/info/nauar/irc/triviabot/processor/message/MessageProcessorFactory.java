package info.nauar.irc.triviabot.processor.message;

import info.nauar.irc.triviabot.Scorebot;

public class MessageProcessorFactory
{
	public static MessageProcessor getInstance(String type, Scorebot bot)
	{
		MessageProcessor mp = null;
		switch (type)
		{
		default:
			mp = new DefaultMessageProcessor(bot);
		}
		return mp;
	}
}
