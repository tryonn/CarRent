package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion;

public class CriterionEvaluationResults {
	
	private Criterion criterion;
	private double[][] rd;
	private double[][] rc;

	public CriterionEvaluationResults(){
		
	}
	
	public double[][] getRc() {
		return rc;
	}
	public void setRc(double[][] rc) {
		this.rc = rc;
	}
	public double[][] getRd() {
		return rd;
	}
	public void setRd(double[][] rd) {
		this.rd = rd;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}
	
	
	
	

}
