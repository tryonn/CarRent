package br.ufpe.cin.amadeus.system.human_resources.resume;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Resume {
	
	private int idResume;
	private Person person;
	private String degree;
	private Integer year;
	private String institution;
	private String description;
	
	public Resume(){
		
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdResume() {
		return idResume;
	}

	public void setIdResume(int idResume) {
		this.idResume = idResume;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}



}
