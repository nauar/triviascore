package info.nauar.irc.triviabot.processor.nick;

import info.nauar.irc.triviabot.Scorebot;

public class DefaultNickProcessor implements NickProcessor
{
	private final Scorebot bot;
	
	public DefaultNickProcessor(Scorebot bot)
	{
		this.bot = bot;
	}
	
	public void processNickChange(String oldNick, String login, String hostname,
			String newNick)
	{
		bot.replaceNick(oldNick, newNick);
	}
	
}
