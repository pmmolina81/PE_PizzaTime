import com.force.api.ApiVersion;
import com.force.api.ApiConfig;
import com.force.api.ForceApi;

public class FirePizza {

	private static FirePizza instance;
	private ForceApi api;

	private FirePizza()
	{
		ApiConfig config = new ApiConfig().setApiVersion(ApiVersion.V39);
		config.setUsername(Main.username);
		config.setPassword(Main.password);

		this.api = new ForceApi(config);
	}

	// Singleton for login just once
	public static FirePizza getInstance() {
		if (instance == null) {
			instance = new FirePizza();
		}
		return instance;
	}

	public String pizTweet(String tweet, String userName) {
		//changes
		TweetPizzaOrder event = new TweetPizzaOrder();
		if(tweet.toLowerCase().contains("#pizzatime"))
		{
			event.setTweet(tweet);
			event.setUserName(userName);	
			String id = this.api.createSObject("TwitterPizza__e", event);

			return String.format("Tweet sent: %s", id);
		}else 
		{
			return String.format("No pizza stuff");
		}
	}
}