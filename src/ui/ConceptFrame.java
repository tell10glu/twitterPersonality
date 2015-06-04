package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Concepts;
import modelframes.AddConcept;

public class ConceptFrame extends JFrame{
	private JTable table;
	DefaultTableModel model = new DefaultTableModel();
	ArrayList<Concepts> listConcepts = new ArrayList<Concepts>();
	private Thread refreshTable = new Thread(){
		public void run() {
			listConcepts = Concepts.getConceptList();
			model.setRowCount(0);
			for(int i=0;i<listConcepts.size();i++){
				Concepts concept = listConcepts.get(i);
				model.addRow(new Object[]{concept.getId(),concept.getName(),concept.getTitle(),concept.getParentId()});
			}
		};
	};
	public ConceptFrame() {
		setTitle("Concepts");
		setSize(500,500);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		model.addColumn("id");
		model.addColumn("Name");
		model.addColumn("Title");
		model.addColumn("parentId");
		table.setModel(model);

		
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAddNewConcept = new JButton("Add Concept");
		btnAddNewConcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddConcept conceptFrame;
				if(table.getSelectedRow()==-1){
					conceptFrame = new AddConcept();
				}else{
					conceptFrame = new AddConcept();
				}
				conceptFrame.setSize(200, 200);
				conceptFrame.setVisible(true);
			}
		});
		panel.add(btnAddNewConcept);
		
		JButton btnRemoveConcept = new JButton("Remove Concept");
		btnRemoveConcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1){
					Concepts.removeConcept((Integer)(table.getValueAt(table.getSelectedRow(), 0)));
					startRefreshingTable();
				}else{
					JOptionPane.showMessageDialog(ConceptFrame.this, "Select a concept");
				}
			}
		});
		panel.add(btnRemoveConcept);
		
		JButton btnRefreshTable = new JButton("Refresh Table");
		btnRefreshTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startRefreshingTable();
				
			}
		});
		panel.add(btnRefreshTable);
		refreshTable.start();
	}
	private static final long serialVersionUID = 1L;
	
	public void startRefreshingTable(){
		
		refreshTable.run();
	}
}
