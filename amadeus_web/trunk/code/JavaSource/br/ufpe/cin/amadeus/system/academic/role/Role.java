package br.ufpe.cin.amadeus.system.academic.role;

public class Role {
	
	private int id;
	private String name;
	private int constantRole; 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public int getConstantRole() {
		return constantRole;
	}

	public void setConstantRole(int constantRole) {
		this.constantRole = constantRole;
	}

}
