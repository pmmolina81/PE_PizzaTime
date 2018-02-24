import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import org.apache.log4j.BasicConfigurator;

import twitter4j.*;
import twitter4j.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.util.Map;

public class Main {

	protected static final String username = "yourUser";
	protected static final String password = "yourPassAndSecurityToken";
	
	protected static final String TwPizza = "/event/TwitterPizza__e";
	protected static final String CanteenPizzaMountain = "/event/pizza_mountain__e";

	public static void main(String[] args) throws Exception {

		BasicConfigurator.configure();

		String port = System.getenv("PORT");
		if (port==null)
			port = "8080";

		Server server = new Server(Integer.valueOf(port));
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(WelcomeServlet.class, "/");
		handler.addServletWithMapping(TwitterPizzaServlet.class, "/twitterPizza");
		handler.addServletWithMapping(CanteenListenerServlet.class, "/canteen");
		
		server.start();
		server.join();
	}

	@SuppressWarnings("serial")
	public static class WelcomeServlet extends HttpServlet {
		@Override
		protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,IOException {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("<h1>Welcome: endpoints - /canteen /twitterPizza</h1>");
		}
	}

	@SuppressWarnings("serial")
	public static class TwitterPizzaServlet extends HttpServlet {
		@Override
		protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,IOException {
			try
			{
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);

				TwitterListenerV3 tInstanceV3 = new TwitterListenerV3();

				for(Status result : tInstanceV3.getMyTimeline()){
					response.getWriter().println("<h1>Result: "+FirePizza.getInstance().pizTweet(result.getText(), result.getUser().getName())+"</h1>");
				}
			} catch(Exception e) 
			{
 				throw new ServletException(e);
			}

		}
	}

	@SuppressWarnings("serial")
	public static class CanteenListenerServlet extends HttpServlet {
		@Override
		protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,IOException {
			try
			{
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println("<h1>Listening...</h1>");
				response.getWriter().println("<h1>"+CanteenListener.getInstance().getReceivedEvents()+"</h1>");
				// Refresh servlet every 5 seconds to see if we have received more events
				response.addHeader("Refresh", "5");
			} catch(Exception e) {
 				throw new ServletException(e);
			}
		}
	}
}
