package br.ufpe.cin.amadeus.system.human_resources.person;

import java.util.Date;

public class Person{

    private int id;
	private String name;
	private String city;
	private String state;
	private Date birthDate;
	private char gender = ' ';
    private String email;
	private String cpf;
	private String phoneNumber;

	public Person() {
		// Empty Constructor
	}
	
    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char value) {
        gender = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String value) {
        cpf = value;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public boolean equals(Object obj){
		if (!(obj instanceof Person)){
			return false;
		}
		return ((Person)obj).getId() == this.getId();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}