package info.nauar.irc.triviascore.processor.nick;

public interface NickProcessor
{
	public void processNickChange(String oldNick, String login, String hostname,
                                  String newNick);
}
