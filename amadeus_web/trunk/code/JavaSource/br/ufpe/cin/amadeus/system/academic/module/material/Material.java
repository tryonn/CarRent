package br.ufpe.cin.amadeus.system.academic.module.material;

import java.util.Date;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Material {
	
	private int id;
	private String name;
	//private Set<Person> visitants;
	private Date postDate;
	private byte[] file;
	private String contentType;
	private Person author;
	
	public Material(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Vector<Person> getVisitants() {
//		return person;
//	}
//
//	public void setVisitants(Vector<Person> person) {
//		this.person = person;
//	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}
	
}
