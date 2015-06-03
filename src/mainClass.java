import javax.swing.JFrame;

import twitter4j.TwitterException;
import ui.MainFrame;
public class mainClass {
	
		public static void main(String[] args) throws TwitterException{
			MainFrame frame = new MainFrame();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		    
		}
}
