package datacollection;
import helper.Log;
import helper.TwitterHelper;

import java.security.spec.ECField;
import java.util.List;

import model.Tweets;
import model.TwitterApplication;
import model.User;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import ui.MainFrame;


public class readUserTweets implements Runnable {
	private boolean running = false;
	public boolean isRunning(){
		return running;
	}
	String userName;
	public readUserTweets(String userName) {
		this.userName = userName;
	}
	private Twitter twitter = null;
	@Override
	public void run() {
		try {
			System.out.println("giris yaptim");
			 // kullanıcı kayıtlı ve twitleri okunmuş ise tekrar okuma .
			 if(User.isUserExists(userName) && User.isUserTweetReaded(userName)){
				 new Thread(new userFriends(userName)).start();
				 MainFrame.listTweets.remove(this);
				 return;
			 }
			 running = true;
			 twitter = TwitterHelper.getTwitter();
		     int twitterPage = 0;
		     List<Status> status = null ;
		     int tweetCounter = 0;
		     while((status== null || status.size()!=0) && tweetCounter<5){
		    	 Paging pagin = new Paging(++twitterPage, 100);
			     status = twitter.getUserTimeline(userName, pagin);
			     for(int i =0;i<status.size();i++){
			    	 if(Tweets.isTweetOK(status.get(i).getText())){
			    		 Tweets.addTweet(status.get(i).getText(), userName);
				    	 System.out.println(status.get(i).getText());
			    	 }
			    	
			     }
			     tweetCounter++;
		     }
		     User.setUserTweetsReaded(userName);
		     new Thread(new userFriends(userName)).start();
		     Thread.sleep(100000);
		}catch(TwitterException ex){
			ex.printStackTrace();
			if(ex.exceededRateLimitation()){
				Configuration c = twitter.getConfiguration();
				int index = TwitterHelper.findApplication(c.getOAuthConsumerKey(), c.getOAuthConsumerSecret(), c.getOAuthAccessToken(), c.getOAuthAccessTokenSecret());
				TwitterHelper.applications.get(index).setAvailable(false);
				MainFrame.listTweets.add(new readUserTweets(userName));
				Log.applicationLimit(c.getOAuthConsumerKey());
				// hesap daha fazla tweet okuyamadığı için kapatıldı . Başka bir application id ile tekrar denenecek .
				// application id nin engelli olduğunu belirt.
			}
		}catch (Exception e) {
			e.printStackTrace(); 
			Log.e(e.getMessage());
			
		}finally{
			MainFrame.listTweets.remove(this);
			running = false;
		}
		
	}

}
