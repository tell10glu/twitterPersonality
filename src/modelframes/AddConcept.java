package modelframes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Concepts;

public class AddConcept extends JFrame{
	private JComboBox comboParent ;
	private void init(){
		setTitle("Add New Concept");
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblConceptName = new JLabel("Concept Name : ");
		getContentPane().add(lblConceptName);
		
		txtConceptName = new JTextField();
		getContentPane().add(txtConceptName);
		txtConceptName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Title :");
		getContentPane().add(lblNewLabel);
		
		txtTitle = new JTextField();
		getContentPane().add(txtTitle);
		txtTitle.setColumns(10);
		
		JLabel lblParent = new JLabel("Parent : ");
		getContentPane().add(lblParent);
		
		comboParent = new JComboBox();
		getContentPane().add(comboParent);
		
		JButton btnAddSave = new JButton("Add / Update");
		btnAddSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String parentString  = null; 
				if(comboParent.getSelectedIndex()!=-1){
					parentString = comboParent.getSelectedItem().toString();
				}
				System.out.println(parentString);
				int parentId;
				if(parentString!=null){
					System.out.println(parentString.split(" . ")[0]);
					parentId = Integer.parseInt(parentString.split(" . ")[0]);
				}else{
					parentId = 0;
				}
				
				if(concept==null){
					concept = new Concepts(0,parentId , txtConceptName.getText(), txtTitle.getText());
				}else{
					concept.setName(txtConceptName.getText());
					concept.setParentId(parentId);
					concept.setTitle(txtTitle.getText());
				}
				Concepts.addConcept(concept);
			}
		});
		getContentPane().add(btnAddSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancel);
	}
	Concepts concept; 
	
	public AddConcept() {
		init();
		writeElementsIntoComboBox();
	}
	public AddConcept(Concepts concept) {
		this.concept = concept;
		init();
		writeElementsIntoComboBox();
	}
	private void writeElementsIntoComboBox(){
		comboParent.addItem("0 . No Parent");
		ArrayList<Concepts> conceptList = Concepts.getConceptList();
		for(int i=0;i<conceptList.size();i++){
			String conceptText = conceptList.get(i).getId()+" . "+conceptList.get(i).getName();
			comboParent.addItem(conceptText);
		}
	}
	private static final long serialVersionUID = 1L;
	private JTextField txtConceptName;
	private JTextField txtTitle;

}
