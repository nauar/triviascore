package info.nauar.irc.triviascore.processor.message;

public interface MessageProcessor
{
	public void processMessage(String channel, String sender, String login, String hostname, String message);
}
