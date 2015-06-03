package ui;

import helper.DatabaseHelper;
import helper.Log;
import helper.TwitterHelper;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import datacollection.readUserTweets;

public class MainFrame extends JFrame {
	public static ArrayList<readUserTweets> listTweets = new ArrayList<readUserTweets>();
	
	private static final long serialVersionUID = 1L;
	private void init(){
		
	}
	
	/**
	 * Bu thread sürekli olarak çalışacak ve kullanıcıların tweetlerini çekecektir.
	 * listTweets singleton listindeki userlarin ilk 50 sini çalıştıracaktır.
	 */
	private Thread fetchTweets = new Thread(){
		public void run() {
			while(true){
				try {
					int counter = 0;
					int i =0;
					while(i<listTweets.size() && counter<50){
						if(!listTweets.get(i).isRunning()){
							counter++;
							new Thread(listTweets.get(i)).start();
						}
						i++;
					}
					Thread.sleep(6000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		};
	};
	private DefaultTableModel modelLog,modelTweet,modelUser;
	private JTable tableLogs;
	private JTable tableTweets;
	private JTable tableUsers;
	private void invokeTableDatas(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Connection con = null;
				modelLog.setRowCount(0);
				modelTweet.setRowCount(0);
				modelUser.setRowCount(0);
				try{
					Class.forName("com.mysql.jdbc.Driver"); 
					con = DatabaseHelper.getDatabaseConnectionPath();
					String query = "Select * from Log";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					while(rs.next()){
						modelLog.addRow(new Object[]{rs.getInt(1),rs.getDate(2),rs.getTime(3),rs.getString(4),rs.getString(5)});
					}
					query = "Select * from User";
					rs = st.executeQuery(query);
					while(rs.next()){
						modelUser.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getBoolean(3)});
					}
					query = "Select * from Tweets";
					rs = st.executeQuery(query);
					while(rs.next()){
						modelTweet.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getBoolean(3),rs.getDate(4)});
					}
				}catch(ClassNotFoundException ex){
					ex.printStackTrace();
				} 
				catch(Exception e){
					Log.systemError(e.getMessage().toString());
					e.getMessage();
				}
				finally{
					try {
						con.close();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
	}
	private Thread refreshTableDatas = new Thread(){
		public void run() {
			while(true){
				try {
					invokeTableDatas();
					
					Thread.sleep(1000*10);// 10 saniyede bir tekrarla
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			
		};
	};
	public MainFrame(){
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlLog = new JPanel();
		tabbedPane.addTab("Logs", null, pnlLog, null);
		pnlLog.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneLogs = new JScrollPane();
		pnlLog.add(scrollPaneLogs);
		
		tableLogs = new JTable();
		scrollPaneLogs.setViewportView(tableLogs);
		
		JPanel pnlTweets = new JPanel();
		tabbedPane.addTab("Tweets", null, pnlTweets, null);
		pnlTweets.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneTweets = new JScrollPane();
		pnlTweets.add(scrollPaneTweets, BorderLayout.CENTER);
		
		tableTweets = new JTable();
		scrollPaneTweets.setViewportView(tableTweets);
		
		JPanel pnlUsers = new JPanel();
		tabbedPane.addTab("Users", null, pnlUsers, null);
		pnlUsers.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlUsers.add(scrollPane);
		
		tableUsers = new JTable();
		scrollPane.setViewportView(tableUsers);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btnConcept = new JButton("Concept");
		panel.add(btnConcept);
		
		JButton btnAddNewApplication = new JButton("Add New Application Keys");
		panel.add(btnAddNewApplication);
		
		JButton btnStreamNewHashtag = new JButton("Stream New HashTag");
		panel.add(btnStreamNewHashtag);
		
		modelLog = new DefaultTableModel();
		modelTweet = new DefaultTableModel();
		modelUser = new DefaultTableModel();
		
		modelLog.addColumn("id");
		modelLog.addColumn("Tarih");
		modelLog.addColumn("Saat");
		modelLog.addColumn("AksiyonAdi");
		modelLog.addColumn("Cikti");
		
		modelTweet.addColumn("id");
		modelTweet.addColumn("tweet");
		modelTweet.addColumn("calculated");
		modelTweet.addColumn("Date");
		
		modelUser.addColumn("id");
		modelUser.addColumn("userName");
		modelUser.addColumn("isTweetReaded");
		
		tableLogs.setModel(modelLog);
		tableTweets.setModel(modelTweet);
		tableUsers.setModel(modelUser);
		
		runThreads();
		init();
		refreshTableDatas.start();
	}
	private void runThreads(){
		Connection con= null;
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DatabaseHelper.getDatabaseConnectionPath();
			String query = "Select userName from User where isTweetReaded = ? ";
			PreparedStatement st = con.prepareStatement(query);
			st.setBoolean(1, false);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				listTweets.add(new readUserTweets(rs.getString(1)));
			}
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		} 
		catch(Exception e){
			Log.systemError(e.getMessage().toString());
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		fetchTweets.start();
		TwitterHelper.checkApplicationAvailability.start();
	}
}
