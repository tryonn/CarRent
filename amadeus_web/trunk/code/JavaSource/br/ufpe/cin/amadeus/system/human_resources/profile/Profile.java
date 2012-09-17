package br.ufpe.cin.amadeus.system.human_resources.profile;

import java.util.HashSet;
import java.util.Set;

public class Profile {
	
	private int id;
	private String name;
	private char intern;
	private int constantProfile;
	private Set permissions = new HashSet();
	
	public Profile(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getIntern() {
		return intern;
	}

	public void setIntern(char intern) {
		this.intern = intern;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getPermissions() {
		return permissions;
	}

	public void setPermissions(Set permissions) {
		this.permissions = permissions;
	}
	
	public boolean equals(Object profile){
		if(!(profile instanceof Profile)){
			return false;
		}
		Profile temp = (Profile) profile;
		if(this.name.equalsIgnoreCase(temp.getName())){
			return true;
		}else{
			return false;
		}
	}

	public int getConstantProfile() {
		return constantProfile;
	}

	public void setConstantProfile(int constantProfile) {
		this.constantProfile = constantProfile;
	}


}
