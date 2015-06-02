import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserList;
import twitter4j.conf.ConfigurationBuilder;
public class mainClass {

		public static void main(String[] args) throws TwitterException{
			
		       ConfigurationBuilder cb = new ConfigurationBuilder();
		       cb.setDebugEnabled(true)
		       .setOAuthConsumerKey("tedaFpnl6sfQC3WdPkTCMlvna")
		       .setOAuthConsumerSecret("G3m5T74tCWXClynkeWRKkcqbfl0a8cvRrhU25SEcZAadsu8HQF")
		       .setOAuthAccessToken("2655387793-smDESn5tZVKKQVxSNatUyH6z3mTUG6ze4e90tAK")
		       .setOAuthAccessTokenSecret("MabSEuNdDAxakYJEHuGbsL3J4GKWvw8QvLl3UwJ5vBG5d");
		     TwitterFactory tf = new TwitterFactory(cb.build());
		     Twitter twitter = tf.getInstance();
		     int twitterPage = 0;
		     List<Status> status = null ;
		     ResponseList<UserList> list = twitter.getUserLists("abdullahtelli");
		     System.out.println("Abdullah telliogly list");
		     for(int i =0;i<list.size();i++){
		    	 System.out.println(list.get(i).getName());
		     }
		     // deneme faruk
		     while(status== null || status.size()!=0){
		    	 Paging pagin = new Paging(++twitterPage, 100);
			     status = twitter.getUserTimeline("abdullahtelli", pagin);
			     for(int i =0;i<status.size();i++){
			    	 System.out.println(status.get(i).getText());
			     }
		     }
		    
		}
}
