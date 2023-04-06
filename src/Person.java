import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private int id;
	private List<Furniture> inUse = new ArrayList<Furniture>();
	
	public Person(String newName, int newId) {
		name = newName;
		id = newId;
		System.out.println("Made a new Person instance");	
		System.out.println("Set name to " + name + " and id to " + id);
	}
	
	String getName() {
		return name;
	}
	
	int getId() {
		return id;
	}
	
	List<Furniture> getInUse() {
		return inUse;
	}
	
	void setName(String newName) {
		name = newName;
	}
	
	boolean rent(Furniture f) {
		if(f.Use(this)) {
			inUse.add(f);
			System.out.println(name + " rent " + f.getName());
			return true;
		} else {
			System.out.println("All" + f.getName() + " is be used" );
			return false;
		}
		
	}
	
	void giveBack(Furniture f) {
		if(inUse.contains(f)) {
			inUse.remove(inUse.indexOf(f));
			System.out.println(name + " returned " + f.getName());
			f.back(this);
		}
	}
	
	void giveBackAll() {
		System.out.println(name + " returned all furnitures");
		for(int i=0; i<inUse.size(); i++) {
			inUse.get(i).back(this);
		}
		inUse.removeAll(inUse);
	}
}
