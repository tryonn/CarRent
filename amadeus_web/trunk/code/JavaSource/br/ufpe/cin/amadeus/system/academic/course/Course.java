package br.ufpe.cin.amadeus.system.academic.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.amadeus.system.academic.area.Area;
import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.tool.Tool;


public class Course implements Comparable {
	
	private int id; 
	private Area area; 
	private String name; 
	private int idAuthor; 
	private String objectives; 
	private String content;
	private int students;
	private Date creationDate;
	private Date initialRegistrationDate;
	private Date finalRegistrationDate;
	private Date initialCourseDate;
	private Date finalCourseDate;
	private String targetAudience;
	private Set<Tool> tools = new HashSet<Tool>();
	private Set<Keyword> keywords = new HashSet<Keyword>();
	private List<Module> modules = new ArrayList<Module>();
	
	public Set<Tool> getTools(){
		return this.tools;
	}
	
	public void setTools(Set<Tool> tools){
		this.tools=tools;
	}
	
	public int getStudents() {
		return students;
	}
	public void setStudents(int alunos) {
		this.students = alunos;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public int getIdAuthor() {
		return idAuthor;
	}
	public void setIdAuthor(int idAutor) {
		this.idAuthor = idAutor;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date dataCriacao) {
		this.creationDate = dataCriacao;
	}
	public Date getFinalCourseDate() {
		return finalCourseDate;
	}
	public void setFinalCourseDate(Date dataFim) {
		this.finalCourseDate = dataFim;
	}
	public Date getFinalRegistrationDate() {
		return finalRegistrationDate;
	}
	public void setFinalRegistrationDate(Date dataFimInscricao) {
		this.finalRegistrationDate = dataFimInscricao;
	}
	public Date getInitialCourseDate() {
		return initialCourseDate;
	}
	public void setInitialCourseDate(Date dataInicio) {
		this.initialCourseDate = dataInicio;
	}
	public Date getInitialRegistrationDate() {
		return initialRegistrationDate;
	}
	public void setInitialRegistrationDate(Date dataInicioInscricao) {
		this.initialRegistrationDate = dataInicioInscricao;
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
	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objetivo) {
		this.objectives = objetivo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String programa) {
		this.content = programa;
	}
	
	public void setTargetAudience(String publicoAlvo){
		this.targetAudience=publicoAlvo;
	}
	
	public String getTargetAudience(){
		return this.targetAudience;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	public int compareTo(Object course){
		if(!(course instanceof Course)){
			return -10;
		}
		Course temp = (Course) course;
		int retorno;
		Date today = new Date();
		if(today.after(temp.getInitialRegistrationDate()) && today.before(temp.getFinalRegistrationDate())){
			retorno = 1;
		} else if (today.after(temp.getInitialCourseDate()) && today.before(temp.getFinalCourseDate())){
			retorno = 0;
		} else {
			retorno = -1;
		}
		return retorno;
	}
	
	public boolean equals(Object course){
		boolean result = false;
		if(course instanceof Course){
			Course temp = (Course) course;
			if (this.id==temp.getId()){
				result = true;
			}
		}
		return result;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

}
