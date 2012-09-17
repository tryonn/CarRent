package br.ufpe.cin.amadeus.system.human_resources.image;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Image {
	
	private int id;
	private byte[] photo;
	private Person person;

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
