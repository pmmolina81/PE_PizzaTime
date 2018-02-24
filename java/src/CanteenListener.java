import static com.salesforce.emp.connector.LoginHelper.login;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.salesforce.emp.connector.BayeuxParameters;
import com.salesforce.emp.connector.EmpConnector;
import com.salesforce.emp.connector.TopicSubscription;

public class CanteenListener {

	private static CanteenListener instance;
	private String receivedEvents = "";

	private CanteenListener() throws Exception {

		Consumer<Map<String, Object>> consumer = events -> instance.processEvents(events);
		BayeuxParameters params = login(Main.username, Main.password);
		EmpConnector connector = new EmpConnector(params);
		connector.start().get(5, TimeUnit.SECONDS);
		TopicSubscription canteenSubscription = connector.subscribe(Main.CanteenPizzaMountain, EmpConnector.REPLAY_FROM_TIP, consumer).get(5, TimeUnit.SECONDS);
		System.out.println(String.format("CORRECTLY SUBSCRIBED TO ITEM SCANNED EVENTS: %s", canteenSubscription));
	}

	// Singleton for creating the streaming channel just once
	public static CanteenListener getInstance() throws Exception{
		if (instance == null) {
			instance = new CanteenListener();
		}
		return instance;
	}

	private void processEvents(Map<String, Object> events) {
		receivedEvents += String.format("RECEIVED:%s", events);
		System.out.println("Receiving events: " + receivedEvents);
	}

	public String getReceivedEvents() {
		// This method will be called every 5 seconds by the subscribe servlet
		return receivedEvents;
	}

}