package br.ufpe.cin.amadeus.system.academic.module;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.forum.Forum;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;

public class Module implements Comparable {
	
	private int id;
	private String name;
	private String description;
	private boolean visible;
	private int order;
	private boolean hasCourseEval;
	private List<Material> materials = new ArrayList<Material>();
	private List<Poll> polls = new ArrayList<Poll>();
	private List<Forum> forums = new ArrayList<Forum>();
	private List<Homework> homeworks = new ArrayList<Homework>();
	private CourseEvaluation courseEvaluation;
	private List<Evaluation> studentEvaluations = new ArrayList<Evaluation>();
	
	public Module(){
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean getHasCourseEval() {
		return this.hasCourseEval;
	}
	
	public void setHasCourseEval(boolean hasCourseEval) {
		this.hasCourseEval = hasCourseEval;
	}
	
	public List<Material> getMaterials() {
		return materials;
	}

	public List<Poll> getPolls() {
		return polls;
	}

	public void setPolls(List<Poll> polls) {
		this.polls = polls;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	public List<Homework> getHomeworks() {
		return homeworks;
	}

	public void setHomeworks(List<Homework> homeworks) {
		this.homeworks = homeworks;
	}
	
	public CourseEvaluation getCourseEvaluation() {
		return courseEvaluation;
	}

	public void setCourseEvaluation(CourseEvaluation courseEvaluation) {
		this.courseEvaluation = courseEvaluation;
	}
	
	public List<Evaluation> getStudentEvaluations() {
		return studentEvaluations;
	}

	public void setStudentEvaluations(List<Evaluation> studentEvaluations) {
		this.studentEvaluations = studentEvaluations;
	}

	public boolean equals(Object m){
		boolean result = false;
		if(m instanceof Module){
			Module module = (Module) m;
			if(this.id == module.getId())
				result = true;
		}
		return result;
	}
	
	public int compareTo(Object module) throws ClassCastException{
		if (!(module instanceof Module)){
			throw new ClassCastException("A Module instance must be compared to another Module instance.");
		} else {
			Module mod = (Module) module;
			return this.order - mod.getOrder();
		}
	}
	
}
