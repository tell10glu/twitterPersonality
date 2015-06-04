package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class SpaceFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel maxMemory,totalMemory,freeMemory;
	private void init(){
	getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		maxMemory = new JLabel("Maximum Memory : 0");
		getContentPane().add(maxMemory);
		
		totalMemory = new JLabel("Total Memory : 0");
		getContentPane().add(totalMemory);
		
		freeMemory = new JLabel("Free Memory : 0");
		getContentPane().add(freeMemory);
		
	}
	private Thread refreshMemory = new Thread(){
		public void run() {
			while(true){
				try {
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							maxMemory.setText("Maximum Memory : "+Runtime.getRuntime().maxMemory());
							totalMemory.setText("Total Memory : "+Runtime.getRuntime().totalMemory());
							freeMemory.setText("Free Memory : "+Runtime.getRuntime().freeMemory());
						}
					});
					Thread.sleep(20000);// 20 saniyede bir refreshle
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			
		};
	};
	public SpaceFrame(){
	
		refreshMemory.setPriority(Thread.MIN_PRIORITY);
		init();
		refreshMemory.start();
	}
}
