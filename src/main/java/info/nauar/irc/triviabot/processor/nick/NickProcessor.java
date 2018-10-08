package info.nauar.irc.triviabot.processor.nick;

public interface NickProcessor
{
	public void processNickChange(String oldNick, String login, String hostname,
                                  String newNick);
}
