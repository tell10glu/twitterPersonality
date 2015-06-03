package helper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.TwitterApplication;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter apisini threadlere döndürecek olan sınıf.
 * Twitter apisiyle , keylerle olan bütün işlemler bu sayfada yapılacak .
 * @author abdullahtellioglu
 *
 */
public class TwitterHelper {
	
	public static ArrayList<TwitterApplication> applications  = TwitterApplication.getTwitterApplications();
	
	/**Twitter applicationlarını döndüren fonksiyon
	 * @return boş olan bir applicationi dondurur . Hiç bulamaz ise null döndürür.
	 */
	public static Twitter getTwitter(){
		
		int i =0;
		boolean found = false;
		while(i<applications.size() && !found){
			if(applications.get(i).isAvailable()){
				found=true;
			}else{
				i++;
			}
		}
		if(i==applications.size())
			return null;
		ConfigurationBuilder cb = new ConfigurationBuilder();
	       cb.setDebugEnabled(true)
	       .setOAuthConsumerKey(applications.get(i).getConsumerKey())
	       .setOAuthConsumerSecret(applications.get(i).getConsumerSecret())
	       .setOAuthAccessToken(applications.get(i).getAccessToken())
	       .setOAuthAccessTokenSecret(applications.get(i).getAccessTokenSecret());
	     TwitterFactory tf = new TwitterFactory(cb.build());
	     Twitter twitter = tf.getInstance();
	     return twitter;
	}
	/**
	 * Değerleri verilen Twitter apisini bulur.
	 * @param cKey Consumer Key
	 * @param cSecret Consumer Secret
	 * @param aToken Access Token
	 * @param aTokenSecret Access Token Secret
	 * @return TwitterHelper.applications içindeki indexi döndürür. Bulamaz ise -1
	 */
	public static int findApplication(String cKey,String cSecret,String aToken,String aTokenSecret){
		for(int i =0;i<applications.size();i++){
			TwitterApplication app = applications.get(i);
			if(app.getConsumerKey().equals(cKey) && app.getConsumerSecret().equals(cSecret) && app.getAccessToken().equals(aToken)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Bu thread programın başlangıcından itibaren çalışır ve application keylerinin süresi dolup dolmamış mı diye bakar . 
	 * Varsayılan saat 2 saattir!
	 */
	public static Thread checkApplicationAvailability = new Thread(){
		
		public void run() {
			while(true){
				try {
					for(int i =0;i<applications.size();i++){
						if(!applications.get(i).isAvailable()){
							// burada eğer hesap uygun değilse lastusedtime a bakar ve 2 saati geçmişse tekrar available yapar.
							Calendar cal = Calendar.getInstance();
							cal.setTime(new Date());
							cal.add(Calendar.HOUR, -2);
							Date twoHourBack = cal.getTime();
							if(applications.get(i).getLastUsedTime().before(twoHourBack)){
								applications.get(i).setAvailable(true);
							}
						}
					}
					Thread.sleep(1000*60*10);// 10 dakika bekle
				} catch (Exception e) {
					
					// TODO: handle exception
				}
			}
		};
	};
}
