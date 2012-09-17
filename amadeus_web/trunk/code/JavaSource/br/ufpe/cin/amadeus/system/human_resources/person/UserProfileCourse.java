package br.ufpe.cin.amadeus.system.human_resources.person;

import java.io.Serializable;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;


public class UserProfileCourse implements Serializable{
	
	private User user;
	private Profile profile;
	private Course course;
	
	public UserProfileCourse()
	{
		
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}