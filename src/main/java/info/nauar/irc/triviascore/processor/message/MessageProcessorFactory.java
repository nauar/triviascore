package info.nauar.irc.triviascore.processor.message;

import info.nauar.irc.triviascore.Scorebot;

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
