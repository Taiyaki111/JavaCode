import java.util.ArrayList;
import java.util.List;

public class Furniture {
	protected String name;
	protected int num; //number of the furniture
	protected int inUse = 0; //number of the furniture in use
	protected List<Person> User = new ArrayList<Person>();
	
	public Furniture(String newName, int newNum) {
		name = newName;
		num = newNum;
		System.out.println("Made a new Furniture instance");	
		System.out.println("Set name to " + name + " and number to " + num);
	}
	
	String getName() {
		return name;
	}
	
	int getNum() {
		return num;
	}
	
	int getInUse() {
		return inUse;
	}
	
	void setName(String newName) {
		name = newName;
	}
	
	void setNum(int newNum) {
		num = newNum;
	}
	
	List<Person> getUser(){
		return User;
	}
	
	public boolean Use(Person newUser) {
		if(inUse < num) {
			inUse++;
			System.out.println( newUser.getName() + " can use " + name);
			User.add(newUser);
			return true;
		} else {
			System.out.println(name + " is already used");
			return false;
		}
	}
	
	public void back(Person p) {
		if(inUse > 0) {
			inUse--;
			User.remove(p);
			System.out.println(name + " was given backed");
		} else {
			System.out.println("all " + name + " was already given back");
		}
	}
}
