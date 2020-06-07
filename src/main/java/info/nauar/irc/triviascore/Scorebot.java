package info.nauar.irc.triviascore;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.cli.*;
import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

import info.nauar.irc.triviascore.processor.message.MessageProcessor;
import info.nauar.irc.triviascore.processor.message.MessageProcessorFactory;
import info.nauar.irc.triviascore.processor.nick.NickProcessor;
import info.nauar.irc.triviascore.processor.nick.NickProcessorFactory;

public class Scorebot extends PircBot
{
	private String triviaName;
	private MessageProcessor mp;
	private NickProcessor np;
	private TreeMap<String, Integer> scores;
	private TreeMap<String, Integer> games;
	
	public Scorebot(String triviaName)
	{
		super();
		scores = new TreeMap<>();
		games = new TreeMap<>();
		mp = MessageProcessorFactory.getInstance("", this);
		np = NickProcessorFactory.getInstance("", this);
		this.triviaName = triviaName;
	}

	public static void main(String args[]) throws Exception
	{
		Options options = new Options();

		Option name = new Option("n", "name", true, "nickname of this bot");
		name.setRequired(true);
		options.addOption(name);

		Option trivianame = new Option("tn", "trivianame", true, "nickname of the trivia bot");
		trivianame.setRequired(true);
		options.addOption(trivianame);


		Option channel = new Option("ch", "channel", true, "channel to connect");
		channel.setRequired(true);
		options.addOption(channel);

		Option server = new Option("s", "server", true, "IRC server to connect");
		server.setRequired(true);
		options.addOption(server);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("java -jar triviascore.jar", options);
			System.exit(-1);
			throw e;
		}

		Scorebot bot = new Scorebot(cmd.getOptionValue("trivianame"));
		bot.setVerbose(true);
		bot.setAutoNickChange(true);
		bot.setName(cmd.getOptionValue("name"));
		bot.connect(cmd.getOptionValue("server"));
		bot.joinChannel(cmd.getOptionValue("channel"));
	}

	public String getTriviaName() {
		return triviaName;
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

	public void initGames()
	{
		games.clear();
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

	public String showGamesSoFar()
	{
		StringBuffer sb = new StringBuffer();

		List<String> maxScoreUsers = new LinkedList<String>();
		Integer maxScore = 0;


		for (String user : games.descendingKeySet())
		{

			if (sb.length() != 0)
				sb.append(", ");
			else
				sb.append(Colors.BOLD + "Results: " + Colors.NORMAL);
			sb.append(user);
			sb.append(": ");

			Integer score = games.get(user);
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

		sb.append(" " + Colors.BOLD + ((maxScoreUsers.size() > 1)?"Leader: ":"Leaders: ") + Colors.NORMAL);

		boolean first = true;
		for (String user : maxScoreUsers)
		{
			if (!first)
				sb.append(", ");
			sb.append(user);
			first = false;
		}

		sb.append(" with " + maxScore + "games." );
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
