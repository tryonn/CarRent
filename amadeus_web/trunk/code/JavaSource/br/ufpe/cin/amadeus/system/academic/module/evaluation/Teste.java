package br.ufpe.cin.amadeus.system.academic.module.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.ObjectiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.TrueOrFalseAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;

public class Teste {
	
	private AmadeusSystem amadeus = AmadeusSystem.getInstance();
	private Map<Integer, Evaluation> studentEvaluations = new LinkedHashMap<Integer, Evaluation>();
	
	
	public void saveDiscursiveEvaluation(int evaluationId, String questionText, int level, int questionId){
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		DiscoursiveQuestion discursive = new DiscoursiveQuestion();
		discursive.setLevel(String.valueOf(level).charAt(0));
		discursive.setQuestion(questionText);
		if(questionId==-1){
			amadeus.insertDiscoursiveQuestion_Evaluation(studentEval, discursive);
		}else{
			discursive.setId(questionId);
			amadeus.editQuestion(discursive);
		}
	}
	
	public static void main(String[] args) {
		//Teste t  = new Teste();
		//t.saveDiscursiveEvaluation(87, "Justifique ae", 3, 23);
		
		
		
		//povoando o banco -> Inserindo uma avaliação
		Evaluation evaluation = new Evaluation();
		evaluation.setId(1);
		evaluation.setBeginning(new Date(7000000));
		evaluation.setEnd(new Date(8934789257L));
		evaluation.setName("avaliacao");
		evaluation.setMaxScore(50.0);
		evaluation.setWeight(50.0);
		evaluation.setSqmodule(1);
		
		//adicionando um user a resposta avaliação
		User user = new User();
		user.setId(1);
		
		//criando um list de discoursive questions
		/*DiscoursiveQuestionAnswer dqa = new DiscoursiveQuestionAnswer();
		dqa.setAnswer("A resposta correta é");
		DiscoursiveQuestionAnswer dqa1 = new DiscoursiveQuestionAnswer();
		dqa1.setAnswer("A resposta e nada naum, vou responder nada.Prof doido");
		List ld = new ArrayList<DiscoursiveQuestionAnswer>();
		ld.add(dqa);
		ld.add(dqa1);*/
		
		//criando um list de Objective questions
		ObjectiveQuestionAnswer oqa = new TrueOrFalseAnswer();
		ObjectiveQuestionAnswer oqa1 = new TrueOrFalseAnswer();
		List oa = new ArrayList<ObjectiveQuestionAnswer>();
		oa.add(oqa);
		oa.add(oqa1);
		
		EvaluationUserAnswer ea = new EvaluationUserAnswer();
		ea.setDateAnswer(new Date(7000000));
		ea.setEvaluation(evaluation);
		ea.setUser(user);
		//ea.setDiscoursiveAnswers(ld);
		ea.setObjectiveAnswers(oa);
		
		
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		amadeus.insertEvaluationAnswer(ea);
	}
}
