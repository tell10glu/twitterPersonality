package datacollection;
import helper.Log;
import helper.TwitterHelper;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.conf.Configuration;
import ui.MainFrame;


public class userFriends implements Runnable {
	String userName ;
	public userFriends(String userName){
		this.userName = userName;
	}
	Twitter twitter = null;
	@Override
	public void run() {
		
		try {
			// veritabaninda var mi kontrol et
			 
			  twitter = TwitterHelper.getTwitter();				
		      long cursor = -1;
		      PagableResponseList<User> followers;
		      System.out.println("Listing followers's ids."); 
		    	  followers = twitter.getFollowersList(userName, cursor);
		    	  for (User follower : followers) {
		    		  
		    		  	System.out.println(follower.getScreenName());
		    		  	MainFrame.listTweets.add(new readUserTweets(follower.getScreenName()));
		    	 
		    	  }
		      Thread.sleep(15000);
		} catch (TwitterException e) {
			if(e.exceededRateLimitation()){
				Configuration c = twitter.getConfiguration();
				int index = TwitterHelper.findApplication(c.getOAuthConsumerKey(), c.getOAuthConsumerSecret(), c.getOAuthAccessToken(), c.getOAuthAccessTokenSecret());
				TwitterHelper.applications.get(index).setAvailable(false);
				Log.applicationLimit(c.getOAuthConsumerKey());
				// eğer kullanılan twitter application maximuma ulaşmış ise .
			}
			e.printStackTrace();
		}catch(Exception ex){
			Log.systemError(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

}
