package ui;

import helper.DatabaseHelper;
import helper.TwitterHelper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.TwitterApplication;

public class ApplicationKeyFrame extends JFrame{
	public ApplicationKeyFrame() {
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		setSize(200,100);
		JLabel lblNewLabel = new JLabel("Consumer Key :");
		getContentPane().add(lblNewLabel);
		
		txtConsumerKey = new JTextField();
		txtConsumerKey.setText("Consumer Key");
		getContentPane().add(txtConsumerKey);
		txtConsumerKey.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Consumer Secret : ");
		getContentPane().add(lblNewLabel_1);
		
		txtConsumerSecret = new JTextField();
		txtConsumerSecret.setText("Consumer Secret");
		getContentPane().add(txtConsumerSecret);
		txtConsumerSecret.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Access Token : ");
		getContentPane().add(lblNewLabel_2);
		
		txtAccessToken = new JTextField();
		txtAccessToken.setText("Access Token");
		getContentPane().add(txtAccessToken);
		txtAccessToken.setColumns(10);
		
		JLabel lblAccessTokenSecret = new JLabel("Access Token Secret : ");
		getContentPane().add(lblAccessTokenSecret);
		
		txtAccessTokenSecret = new JTextField();
		txtAccessTokenSecret.setText("Access Token Secret");
		getContentPane().add(txtAccessTokenSecret);
		txtAccessTokenSecret.setColumns(10);
		
		JLabel lblOwner = new JLabel("Owner : ");
		getContentPane().add(lblOwner);
		
		txtOwner = new JTextField();
		txtOwner.setText("Owner");
		getContentPane().add(txtOwner);
		txtOwner.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TwitterHelper.applications.add(new TwitterApplication(-1, txtConsumerKey.getText(), txtConsumerSecret.getText(), txtAccessToken.getText(), txtAccessTokenSecret.getText()));
				
					Connection con  = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DatabaseHelper.getDatabaseConnectionPath();
						PreparedStatement st = con.prepareStatement("insert into TwitterApplications(ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret,Owner) VALUES(?,?,?,?,?)");
						st.setString(1, txtConsumerKey.getText());
						st.setString(2, txtConsumerSecret.getText());
						st.setString(3, txtAccessToken.getText());
						st.setString(4, txtAccessTokenSecret.getText());
						st.setString(5, txtOwner.getText());
						st.executeUpdate();
						for(int i =0;i<getContentPane().getComponentCount();i++){
							if(getContentPane().getComponent(i) instanceof JTextField){
								((JTextField)(getContentPane().getComponent(i))).setText("");
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}finally{
						try {
							con.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			
		});
		getContentPane().add(btnAdd);
		
		JButton btnDispose = new JButton("Dispose");
		btnDispose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
				dispose();
			}
		});
		getContentPane().add(btnDispose);
	}
	private static final long serialVersionUID = 1L;
	private JTextField txtConsumerKey;
	private JTextField txtConsumerSecret;
	private JTextField txtAccessToken;
	private JTextField txtAccessTokenSecret;
	private JTextField txtOwner;

	
}
