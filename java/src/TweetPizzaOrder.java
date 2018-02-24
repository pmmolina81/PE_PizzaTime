import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetPizzaOrder {

	@JsonProperty("Tweet__c")
	String tweet;
	@JsonProperty("User_Name__c")
	String userName;
	
	//public String getTweet() { return tweet; }
	public void setTweet(String tweet) { this.tweet = tweet; }
	//public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
}