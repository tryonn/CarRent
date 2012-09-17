package br.ufpe.cin.amadeus.system.academic.area;

/**
 * Classe que encapsula atributos e m�todos relativos a area de conhecimento
 * @author amadeus
 *
 */

public class Area {
		
	private int id;
	private String name;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String descricao) {
		this.description = descricao;
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
	public void setName(String nome) {
		this.name = nome;
	}

}
