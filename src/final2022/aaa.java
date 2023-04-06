package final2022;

import java.util.ArrayList;
import java.util.List;

public class aaa {
	private List<Furniture> furnitureList = new ArrayList<Furniture>();
	private List<Person> personList = new ArrayList<Person>();
	
	void regFurniture(String newName, int newNum) {
		for(int i=0; i<furnitureList.size(); i++) {
			if(newName.equals(furnitureList.get(i).getName())) {
				System.out.println(newName + "already exists.");
				return;
			}
		}
		furnitureList.add(new Furniture(newName, newNum));
	}
	
	void regPerson(String newName, int newId) {
		for(int i=0; i<personList.size(); i++) {
			if(newId == personList.get(i).getId()) {
				System.out.println(newId + "already exists.");
				return;
			}
		}
		personList.add(new Person(newName,newId));
	}
	
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
				personList.remove(i);
				return;
			}
		}
		System.out.println(delId + "doesn't exist");
	}
	
	public static void main(String[] args) {
		//Furniture chair = new Furniture("chair", 3);
		//Person p1 = new Furniture("")
	}
	
	
}
