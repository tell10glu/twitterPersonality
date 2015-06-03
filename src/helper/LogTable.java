package helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LogTable extends JTable{
	private static final long serialVersionUID = 1L;
	DefaultTableModel model = new DefaultTableModel();
	public void readDatas(){
		model.setRowCount(0);
		Connection con  = null;
		try {
			con = DatabaseHelper.getDatabaseConnectionPath();
			Statement st = con.createStatement();
			ResultSet set = st.executeQuery("Select * from Concepts");
			while(set.next()){
				model.addRow(new Object[]{set.getInt(1),set.getDate(2),set.getTime(3),set.getString(4),set.getString(5)});
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
	}
	public LogTable(){
		super();
		model.addColumn("id");
		model.addColumn("Tarih");
		model.addColumn("Saat");
		model.addColumn("Aksiyon Adi");
		model.addColumn("Cikti");
	}
	
}
