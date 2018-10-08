package info.nauar.irc.triviabot.user;

public class User implements Comparable<User>
{
	private String name;
	private Integer score;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getScore()
	{
		return score;
	}
	
	public void setScore(Integer score)
	{
		this.score = score;
	}

	@Override
	public int compareTo(User o)
	{
		return Integer.signum(score-o.score);
	}

}
