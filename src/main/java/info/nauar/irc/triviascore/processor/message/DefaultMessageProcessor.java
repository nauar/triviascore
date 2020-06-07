package info.nauar.irc.triviascore.processor.message;

import info.nauar.irc.triviascore.Scorebot;

public class DefaultMessageProcessor implements MessageProcessor
{
	private final Scorebot bot;
	
	public DefaultMessageProcessor(Scorebot bot)
	{
		this.bot = bot;
	}
	
	public void processMessage(String channel, String sender, String login, String hostname, String message)
	{
		message = message.trim();
		if (sender.equals(bot.getTriviaName()))
		{
			processTriviaMessage(channel, sender, login,  hostname, message);
		}
		
		if (message.length() > 0 && message.startsWith("!"))
		{
			processCommand(channel, sender, login,  hostname, message);
		}
	}
	
	public void processTriviaMessage(String channel, String sender, String login, String hostname, String message)
	{
		if (message.startsWith("Correct "))
		{
			String[] words = message.split(" ");
			bot.correctResponse(words[1].substring(0, words[1].length()-1));
			bot.sendMessage(channel, bot.getListOfUsers());
		}
		
		if ((message.startsWith("Round completed!"))||(message.startsWith("Game terminated, say !trivia to restart.")))
		{
			bot.sendMessage(channel, bot.showFinalResponses());
			bot.sendMessage(channel, bot.showGamesSoFar());
			bot.initResponses();
		}
	}	
	
	public void processCommand(String channel, String sender, String login, String hostname, String message)
	{
		if (message.startsWith("!reset"))
		{
			bot.initResponses();
			bot.sendMessage(channel, "Reset done.");
		}
		 		
		if (message.startsWith("!shutdown"))
		{
			bot.sendMessage(channel, "Shutting down... Good bye!");
			System.exit(0);
		}

		if (message.startsWith("!init"))
		{
			bot.initGames();
			bot.initResponses();
			bot.sendMessage(channel, "Initialization done. Ready to start a set of games.");
		}
		
		if (message.startsWith("!get"))
			processGetCommand(channel, sender, login,  hostname, message);
	}

	public void processSetCommand(String channel, String sender, String login, String hostname, String message)
	{
		if (message.startsWith("!set"))
			processSetCommand(channel, sender, login,  hostname, message);
	}
	
	public void processGetCommand(String channel, String sender, String login, String hostname, String message)
	{
		if (message.startsWith("!set"))
			processSetCommand(channel, sender, login,  hostname, message);
	}
}
