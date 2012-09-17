package br.ufpe.cin.amadeus.system.human_resources.permission;

public class Permission {
	
	private int id;
	private String name;
	private int permissionConstant;
	
	public Permission(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object permission){
		if(!(permission instanceof Permission)){
			return false;
		}
		Permission perm = (Permission) permission;
		return this.name.equalsIgnoreCase(perm.getName());
	}

	public int hashCode() {
		return name.charAt(0) + name.charAt(1); 
	}

	public int getPermissionConstant() {
		return permissionConstant;
	}

	public void setPermissionConstant(int permissionConstant) {
		this.permissionConstant = permissionConstant;
	}

}
