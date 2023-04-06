package final2022;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class  bbb{
	private List<Furniture> furnitureList = new ArrayList<Furniture>();
	private List<Person> personList = new ArrayList<Person>();
	
	private JTextField newFurnitureName;
	private JTextField newFurnitureNum;
	private JTextField newPersonName;
	private JTextField newPersonId;
	
	private DefaultListModel<String> listFurnitureModel;
	private JList<String> listFurniture;
	
	private DefaultListModel<String> listPersonModel;
	private JList<String> listPerson;
	

	
	void delFurniture(String delName) {
		for(int i=0; i<furnitureList.size(); i++) {
			if(delName.equals(furnitureList.get(i).getName())) {
				System.out.println("delete" + delName);
				furnitureList.remove(i);
				return;
			}
		}
		System.out.println(delName + "dosen't exist");
	}
	
	void delPerson(int delId) {
		for(int i=0; i<personList.size(); i++) {
			if(delId == personList.get(i).getId()) {
				
				System.out.println("delete" + delId);
				personList.get(i).giveBackAll();
				personList.remove(i);
				return;
			}
		}
		System.out.println(delId + "doesn't exist");
	}
	
	class RentButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int fIndex = listFurniture.getSelectedIndex();
			int pIndex = listPerson.getSelectedIndex();
			if (fIndex == -1 || pIndex == -1) {
				
			} else {
				String selectedName = listFurniture.getSelectedValue();
				Furniture selectedFurniture = null;
				for(int i=0; i<furnitureList.size(); i++) {
					if(selectedName.equals(furnitureList.get(i).getName())) {
						selectedFurniture = furnitureList.get(i);
					}
				}
				
				String[] tempStr = listPerson.getSelectedValue().split(": ");
				int tempId = Integer.parseInt(tempStr[0]);
				Person selectedPerson = null;
				for(int i=0; i<personList.size(); i++) {
					if(tempId == personList.get(i).getId()) {
						selectedPerson = personList.get(i);
					}
				}
				boolean flag = selectedPerson.rent(selectedFurniture);
				if(flag) {
					JOptionPane.showMessageDialog(listFurniture, selectedPerson.getName()+ " is rented " + selectedFurniture.getName(), "Rental Imformation", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(listFurniture, "No more " + selectedFurniture.getName() + " can be used", "Rental Imformation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
		}
	}
	
	class FurnitureRegButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String tempText = newFurnitureName.getText();
			int tempNum = Integer.parseInt(newFurnitureNum.getText());
			boolean flag = true;
			
			for(int i=0; i<furnitureList.size(); i++) {
				if(tempText.equals(furnitureList.get(i).getName())) {
					System.out.println(tempText + "already exists.");
					flag = false;
				}
			}
			if(flag) {
				furnitureList.add(new Furniture(tempText, tempNum));
				listFurnitureModel.addElement(tempText);
			}
		}
	}
	
	class FurnitureSeeButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = listFurniture.getSelectedIndex();
			if (index != -1) {
				String selectedName = listFurniture.getSelectedValue();
				Furniture selectedFurniture = null;
				String tmpText;
				
				for(int i=0; i<furnitureList.size(); i++) {
					if(selectedName.equals(furnitureList.get(i).getName())) {
						selectedFurniture = furnitureList.get(i);
					}
				}
				int max = selectedFurniture.getNum();
				int inUse = selectedFurniture.getInUse();
				int counter = 0;
				if(inUse == 0) {
					tmpText = inUse + " of " + max + " is used.";
				} else {
					tmpText = inUse + " of " + max+ " is used by ";
					for(int i=0; i<personList.size(); i++) {
						List<Furniture> rentalList = personList.get(i).getInUse();
						for(int j=0; j<rentalList.size(); j++) {
							if(selectedName.equals(rentalList.get(j).getName())) {
								tmpText = tmpText + personList.get(i).getName() + ", ";
								counter++;
							}
							if(counter == inUse) {
								tmpText = tmpText + ".";
								counter++;
							}
						}
					}
				}
				JOptionPane.showMessageDialog(listFurniture, tmpText, "Rental Imformation", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(listFurniture, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class FurnitureRenewButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = listFurniture.getSelectedIndex();
			if (index != -1) {
				String selectedName = listFurniture.getSelectedValue();
				Furniture selectedFurniture = null;
				for(int i=0; i<furnitureList.size(); i++) {
					if(selectedName.equals(furnitureList.get(i).getName())) {
						selectedFurniture = furnitureList.get(i);
					}
				}
				int inUse = selectedFurniture.getInUse();
				String value = JOptionPane.showInputDialog(this, "Input new name");
				if (value == null){
					System.out.println("did not change name.");
				}else{
					selectedFurniture.setName(value);
					listFurnitureModel.set(index, value);
				} 
				int num = Integer.parseInt(JOptionPane.showInputDialog(this, "Input new number"));
				if (inUse == 0) {
					selectedFurniture.setNum(num);
				} else {
					if (inUse <= num) {
						selectedFurniture.setNum(num);
					} else {
						int option = JOptionPane.showConfirmDialog(null, "number of furniture becomes lower than user. Are you sure to set?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (option == JOptionPane.YES_OPTION){
							do {
								Person p = selectedFurniture.getUser().get(0);
						    	p.giveBack(selectedFurniture);
						    	inUse = selectedFurniture.getInUse();
						    	selectedFurniture.setNum(num);
							} while(inUse > num);
						}else if (option == JOptionPane.NO_OPTION){
						      System.out.println("did not change number");
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(listFurniture, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class FurnitureDelButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = listFurniture.getSelectedIndex();
			if (index != -1) {
				delFurniture(listFurniture.getSelectedValue());
				listFurnitureModel.remove(index);
			} else {
				JOptionPane.showMessageDialog(listFurniture, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class PersonRegButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String tempText = newPersonName.getText();
			int tempNum = Integer.parseInt(newPersonId.getText());
			boolean flag = true;
			
			for(int i=0; i<personList.size(); i++) {
				if(tempNum == personList.get(i).getId()) {
					System.out.println(tempNum + ": " + tempText + " already exists. Use different ID.");
					flag = false;
				}
			}
			if(flag) {
				personList.add(new Person(tempText,tempNum));
				listPersonModel.addElement(tempNum + ": " + tempText);
			}
		}
	}
	
	class PersonSeeButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {			
			int index = listPerson.getSelectedIndex();
			if (index != -1) {
				String[] tempStr = listPerson.getSelectedValue().split(": ");
				int tempId = Integer.parseInt(tempStr[0]);
				Person selectedPerson = null;
				String tmpText;
				
				for(int i=0; i<personList.size(); i++) {
					if(tempId == personList.get(i).getId()) {
						selectedPerson = personList.get(i);
					}
				}
				String name = selectedPerson.getName();
				List<Furniture> inUse = selectedPerson.getInUse();
				if(inUse.size() == 0) {
					tmpText = name + " is not using furnitures.";
				} else {
					tmpText = name + " is using ";
					for(int i=0; i<inUse.size(); i++) {
						tmpText = tmpText + inUse.get(i).getName();
							if(i+1<inUse.size()) {
								tmpText = tmpText + ", ";
							} else {
								tmpText = tmpText + ".";
							}
						}
					}
				JOptionPane.showMessageDialog(listFurniture, tmpText, "Rental Imformation", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(listFurniture, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class PersonRenewButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = listPerson.getSelectedIndex();
			if (index != -1) {
				String[] tempStr = listPerson.getSelectedValue().split(": ");
				int tempId = Integer.parseInt(tempStr[0]);
				Person selectedPerson = null;
				for(int i=0; i<personList.size(); i++) {
					if(tempId == personList.get(i).getId()) {
						selectedPerson = personList.get(i);
					}
				}
				List<Furniture> inUse = selectedPerson.getInUse();
				String value = JOptionPane.showInputDialog(this, "Input new name");
				if (value == null){
					System.out.println("did not change name");
				}else{
					selectedPerson.setName(value);
					listPersonModel.set(index, tempId + ": " + value);
				}
				for(int i=0; i<inUse.size();i++) {
					Furniture f = inUse.get(i);
					int option = JOptionPane.showConfirmDialog(null, "do you want to return " + f.getName() + "?", "confirm",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == JOptionPane.YES_OPTION){
						selectedPerson.giveBack(f);
					}else if (option == JOptionPane.NO_OPTION){
						System.out.println("did not return the furniture");
					}
				}
			} else {
				JOptionPane.showMessageDialog(listFurniture, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class PersonDelButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = listPerson.getSelectedIndex();
			if (index != -1) {
				String[] tempStr = listPerson.getSelectedValue().split(":");
				int tempNum = Integer.parseInt(tempStr[0]);
				delPerson(tempNum);
				listPersonModel.remove(index);
			} else {
				JOptionPane.showMessageDialog(listPerson, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public Component createFurnitureComponents() {
		newFurnitureName = new JTextField("new furniture name");
		newFurnitureNum = new JTextField("number of new furniture");

		
		listFurnitureModel = new DefaultListModel<String>();
		
		listFurniture = new JList<String>(listFurnitureModel);
		listFurniture.setVisibleRowCount(10);
		listFurniture.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(listFurniture);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton regButton = new JButton("Register");
		FurnitureRegButtonAction regButtonListener = new FurnitureRegButtonAction();
		regButton.addActionListener(regButtonListener);
		
		JButton delButton = new JButton("Delete");
		FurnitureDelButtonAction delButtonListener = new FurnitureDelButtonAction();
		delButton.addActionListener(delButtonListener);
		
		JButton seeButton = new JButton("See");
		FurnitureSeeButtonAction seeButtonListener = new FurnitureSeeButtonAction();
		seeButton.addActionListener(seeButtonListener);
		
		JButton renewButton = new JButton("renew");
		FurnitureRenewButtonAction renewButtonListener = new FurnitureRenewButtonAction();
		renewButton.addActionListener(renewButtonListener);
		
		JPanel mainPane = new JPanel();
		mainPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30,30));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		JPanel subPane1 = new JPanel();
		subPane1.setLayout(new GridLayout(1,0));
		subPane1.add(regButton);
		subPane1.add(Box.createRigidArea(new Dimension(30, 10)));
		subPane1.add(delButton);
		
		JPanel subPane2 = new JPanel();
		subPane2.setLayout(new GridLayout(1,0));
		subPane2.add(seeButton);
		subPane2.add(Box.createRigidArea(new Dimension(30, 10)));
		subPane2.add(renewButton);
		
		mainPane.add(newFurnitureName);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(newFurnitureNum);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(scrollPane);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(subPane1);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(subPane2);
		
		return mainPane;
	}
	
	public Component createPersonComponents() {
		newPersonName = new JTextField("new person name");
		newPersonId = new JTextField("id of new person");
		
		listPersonModel = new DefaultListModel<String>();
		
		listPerson = new JList<String>(listPersonModel);
		listPerson.setVisibleRowCount(10);
		listPerson.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(listPerson);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton regButton = new JButton("Register");
		PersonRegButtonAction regButtonListener = new PersonRegButtonAction();
		regButton.addActionListener(regButtonListener);
		
		JButton delButton = new JButton("Delete");
		PersonDelButtonAction delButtonListener = new PersonDelButtonAction();
		delButton.addActionListener(delButtonListener);
		
		JButton seeButton = new JButton("See");
		PersonSeeButtonAction seeButtonListener = new PersonSeeButtonAction();
		seeButton.addActionListener(seeButtonListener);
		
		JButton renewButton = new JButton("renew");
		PersonRenewButtonAction renewButtonListener = new PersonRenewButtonAction();
		renewButton.addActionListener(renewButtonListener);
		
		JPanel subPane1 = new JPanel();
		subPane1.setLayout(new GridLayout(1,0));
		subPane1.add(regButton);
		subPane1.add(Box.createRigidArea(new Dimension(30, 10)));
		subPane1.add(delButton);
		
		JPanel subPane2 = new JPanel();
		subPane2.setLayout(new GridLayout(1,0));
		subPane2.add(seeButton);
		subPane2.add(Box.createRigidArea(new Dimension(30, 10)));
		subPane2.add(renewButton);
		
		JPanel mainPane = new JPanel();
		mainPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30,30));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		mainPane.add(newPersonName);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(newPersonId);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(scrollPane);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(subPane1);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(subPane2);
		
		return mainPane;
	}
	
	public Component createComponents() {
		JPanel mainPane = new JPanel();
		mainPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30,30));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
		
		JButton rentButton = new JButton("rent");
		RentButtonAction rentButtonListener = new RentButtonAction();
		rentButton.addActionListener(rentButtonListener);
		
		mainPane.add(createFurnitureComponents());
		mainPane.add(Box.createRigidArea(new Dimension(30, 30)));
		mainPane.add(rentButton);
		mainPane.add(Box.createRigidArea(new Dimension(30, 30)));
		mainPane.add(createPersonComponents());
		
		return mainPane;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("RentalApplication");
		bbb app = new bbb();
		Component contents = app.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
