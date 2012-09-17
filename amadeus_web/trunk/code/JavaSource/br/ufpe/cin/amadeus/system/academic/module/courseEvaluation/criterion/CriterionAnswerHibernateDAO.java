package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class CriterionAnswerHibernateDAO extends GenericHibernateDAO<CriterionAnswer, Integer> implements CriterionAnswerDAO{
	
	public CriterionAnswerHibernateDAO(){
		super(CriterionAnswer.class);
	}
	
	public int getFrequencyVotes(int i, int j,int k, int criterion, int eval){
		char frequencyType = 'a';
		if(k == 0){
			frequencyType = 'd';
		}
		String question = "";
		switch(i){
		case 0:
			question = "in_r1";
		case 1:
			question = "in_r2";
		case 2: 
			question = "in_r3";
		case 3:
			question = "in_r4";
		}
		Session session = getSession();
		 List<CriterionAnswer> results = session
				.createSQLQuery(
						"SELECT {c.*} from criterionanswer {c} where "+question+" = "+j+" and sqcriterionanswer in (SELECT sqcriterionanswer from evaluationanswer where ds_frequencytype = \'"+frequencyType+"\' and sqcriterion = "+criterion+" and sqcourseevaluation = "+eval+" )").addEntity("c", CriterionAnswer.class)
				.list();
		 return results.size();
	}

}
