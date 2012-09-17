package br.ufpe.cin.amadeus.system.academic.module.homework.delivery;

import java.util.Date;

import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Delivery {

	private int id;

	private Homework homework;

	private Person person;

	private Date date;

	private byte[] file;

	public Delivery() {

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
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

	public boolean equals(Object obj) {
		if (!(obj instanceof Delivery)) {
			return false;
		}
		return ((Delivery) obj).getPerson().equals(this.getPerson())
				&& ((Delivery) obj).getHomework().equals(this.getHomework());
	}

}
