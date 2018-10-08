package info.nauar.irc.triviabot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

import info.nauar.irc.triviabot.processor.message.MessageProcessor;
import info.nauar.irc.triviabot.processor.message.MessageProcessorFactory;
import info.nauar.irc.triviabot.processor.nick.NickProcessor;
import info.nauar.irc.triviabot.processor.nick.NickProcessorFactory;

public class Scorebot extends PircBot
{
	private String triviaChannel = "#bot.xonotic";
	private String triviabot = "triviabot";
	private MessageProcessor mp;
	private NickProcessor np;
	private TreeMap<String, Integer> scores;
	
	public Scorebot()
	{
		super();
		scores = new TreeMap<>();
		mp = MessageProcessorFactory.getInstance("", this);
		np = NickProcessorFactory.getInstance("", this);
	}
	
	public static void main(String args[]) throws Exception
	{

		Scorebot bot = new Scorebot();
		bot.setVerbose(true);
		bot.setAutoNickChange(true);
		bot.setName("triviaScore");
		bot.connect("irc.quakenet.org");
		if (args.length == 1)
			bot.setTriviaChannel(args[0]);
		bot.joinChannel(bot.triviaChannel);
	}
	
	public String getTriviaChannel()
	{
		return triviaChannel;
	}

	public void setTriviaChannel(String triviaChannel)
	{
		this.triviaChannel = triviaChannel;
	}

	public String getTriviabot()
	{
		return triviabot;
	}

	public void setTriviabot(String triviabot)
	{
		this.triviabot = triviabot;
	}

	@Override
	public void onMessage(String channel, String sender,
			String login, String hostname, String message)
	{
		mp.processMessage(channel, sender, login, hostname, message);
	}
	
	@Override
	protected void onNickChange(String oldNick, String login, String hostname, String newNick)
	{
		np.processNickChange(oldNick, login, hostname, newNick);
	}

	public void correctResponse(String user)
	{
		if (scores.containsKey(user))
		{
			scores.put(user, scores.get(user)+1);
		}
		else
			scores.put(user, 1);
	}
	
	public void initResponses()
	{
		scores.clear();
	}
	
	public String showFinalResponses()
	{
		StringBuffer sb = new StringBuffer();
		
		List<String> maxScoreUsers = new LinkedList<String>();
		Integer maxScore = 0;
		
		
		for (String user : scores.descendingKeySet())
		{
			
			if (sb.length() != 0)
				sb.append(", ");
			else
				sb.append(Colors.BOLD + "Results: " + Colors.NORMAL);
			sb.append(user);
			sb.append(": ");
			
			Integer score = scores.get(user);
			sb.append(score);
			if (maxScore < score)
			{
				maxScoreUsers.clear();
				maxScoreUsers.add(user);
				maxScore = score;
			}
			else if (maxScore == score)
			{
				maxScoreUsers.add(user);
			}
		}
		
		sb.append(" " + Colors.BOLD + "Max. Score: " + maxScore + " by: " + Colors.NORMAL);
		
		boolean first = true;
		for (String user : maxScoreUsers)
		{
			if (!first)
				sb.append(", ");
			sb.append(user);
			first = false;
		}
		
		return sb.toString();
	}
	
	public String getListOfUsers()
	{
		StringBuffer sb = new StringBuffer();
		for (String user : scores.keySet())
		{
			if (sb.length() != 0)
				sb.append(", ");
			else
				sb.append(Colors.BOLD + "Status: " + Colors.NORMAL);
			sb.append(user);
			sb.append(": ");
			
			Integer score = scores.get(user);
			sb.append(score);
		}
		return sb.toString();
	}
	
	public void replaceNick(String oldNick, String newNick)
	{
		if (scores.containsKey(oldNick))
			scores.put(newNick, scores.remove(oldNick));
	}
}
