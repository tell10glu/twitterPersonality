package model;

import helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Statement;

public class Concepts {
	
	private int id ,parentId;
	private String name,title;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getParentId() {
		return parentId;
	}
	public String getName() {
		return name;
	}
	public String getTitle() {
		return title;
	}
	public String toString(){
		return id+" , "+parentId+" , "+name+" , "+title+" . ";
	}
	public Concepts(int id, int parentid, String name, String title) {
		super();
		this.id = id;
		this.parentId = parentid;
		this.name = name;
		this.title = title;
	}
	public static void removeConcept(int conceptId){
		Connection con  = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			PreparedStatement st = con.prepareStatement("Delete From Concepts where id = ?");
			st.setInt(1, conceptId);
			st.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static ArrayList<Concepts> getConceptList(){
		ArrayList<Concepts> list = new ArrayList<Concepts>();
		Connection con  = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			Statement st = con.createStatement();
			ResultSet set = st.executeQuery("Select * from Concepts");
			while(set.next()){
				list.add(new Concepts(set.getInt(1), set.getInt(4), set.getString(2), set.getString(3)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * Yeni concept eklemeye yarayan fonksiyon.
	 * @param concept eklecenek veya güncellenecek olan concept. Eklenecek ise id 0 gelecek...
	 * 
	 */
	public static void addConcept(Concepts concept){
		Connection con  = null;
		String query = null;
		try {
			//System.out.println(concept.toString());
			con = DatabaseHelper.getDatabaseConnectionPath();
			
			if(concept.getId()==0)
				query = " insert into Concepts(Name,Title,parentId) VALUES(?,?,?)";
			else{
				query = " update Concepts set Name=? AND Title = ? AND parentId = ? Where id = ?";
			}
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, concept.getName());
			st.setString(2, concept.getTitle());
			st.setInt(3, concept.getParentId());
			if(concept.getId()!=0)
				st.setInt(4, concept.getId());
			
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
