package br.ufpe.cin.amadeus.system.util.dao.hibernate;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.course.CourseHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.keyword.KeywordHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.module.ModuleHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluation;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluationHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswerHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.EvaluationHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswerHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswerHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.BlankFillingQuestionHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.BlanksFillingQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ColumnMatching;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ColumnMatchingHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestionDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestionHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestionHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOFalseQuestionHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOrFalseQuestion;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.homework.HomeworkHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.Delivery;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.DeliveryHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.academic.module.material.MaterialHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;
import br.ufpe.cin.amadeus.system.academic.module.poll.PollHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.Answer;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.AnswerHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.ChoiceHibernateDAO;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.academic.role.RoleHibernateDAO;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.access_control.user.UserHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.image.Image;
import br.ufpe.cin.amadeus.system.human_resources.image.ImageHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.permission.Permission;
import br.ufpe.cin.amadeus.system.human_resources.permission.PermissionHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonRoleCourse;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonRoleCourseHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.person.UserProfileCourse;
import br.ufpe.cin.amadeus.system.human_resources.person.UserProfileCourseHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;
import br.ufpe.cin.amadeus.system.human_resources.profile.ProfileHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.resume.Resume;
import br.ufpe.cin.amadeus.system.human_resources.resume.ResumeHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequestHibernateDAO;
import br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation.RequestEvaluation;
import br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation.RequestEvaluationHibernateDAO;
import br.ufpe.cin.amadeus.system.util.dao.DAOFactory;

public class HibernateDAOFactory extends DAOFactory <GenericHibernateDAO> {

	@Override
	public GenericHibernateDAO createDAO(Class classe) {
		if (classe != null) {
			if (classe.equals(User.class)) {
				return new UserHibernateDAO();//.getCurrentSession();HibernateUtil.getSessionFactory().openSession()
			} else if (classe.equals(Person.class)){
				return new PersonHibernateDAO();
			} else if (classe.equals(Course.class)){
				return new CourseHibernateDAO();
			} else if (classe.equals(Profile.class)){
				return new ProfileHibernateDAO();
			} else if (classe.equals(PersonRoleCourse.class)){
				return new PersonRoleCourseHibernateDAO();
			} else if (classe.equals(Role.class)){
				return new RoleHibernateDAO();
			} else if (classe.equals(UserProfileCourse.class)){
				return new UserProfileCourseHibernateDAO();
			} else if (classe.equals(Permission.class)) {
				return new PermissionHibernateDAO();
			} else if (classe.equals(Resume.class)){
				return new ResumeHibernateDAO();
			} else if (classe.equals(Keyword.class)){
				return new KeywordHibernateDAO();
			} else if (classe.equals(UserRequest.class)){
				return new UserRequestHibernateDAO();
			} else if (classe.equals(Image.class)){
				return new ImageHibernateDAO();
			} else if (classe.equals(Module.class)){
				return new ModuleHibernateDAO();
			} else if (classe.equals(Poll.class)){
				return new PollHibernateDAO();
			} else if (classe.equals(Material.class)){
				return new MaterialHibernateDAO();
			} else if (classe.equals(Choice.class)){
				return new ChoiceHibernateDAO();
			} else if (classe.equals(Answer.class)){
				return new AnswerHibernateDAO();
			} else if (classe.equals(Homework.class)){
				return new HomeworkHibernateDAO();
			} else if (classe.equals(Delivery.class)){
				return new DeliveryHibernateDAO();
			} else if (classe.equals(RequestEvaluation.class)){
				return new RequestEvaluationHibernateDAO();
			} else if (classe.equals(Criterion.class)){
				return new CriterionHibernateDAO();
			} else if (classe.equals(CourseEvaluation.class)){
				return new CourseEvaluationHibernateDAO();
			} else if (classe.equals(CriterionAnswer.class)){
				return new CriterionAnswerHibernateDAO();
			} else if (classe.equals(Evaluation.class)){
				return new EvaluationHibernateDAO();
			} else if(classe.equals(DiscoursiveQuestion.class)){
				return new DiscoursiveQuestionHibernateDAO();
			} else if(classe.equals(MultipleChoiceQuestion.class)){
				return new MultipleChoiceQuestionHibernateDAO();
			}  else if(classe.equals(BlanksFillingQuestion.class)){
				return new BlankFillingQuestionHibernateDAO();
			}  else if(classe.equals(TrueOrFalseQuestion.class)){
				return new TrueOFalseQuestionHibernateDAO();
			}  else if(classe.equals(ColumnMatching.class)){
				return new ColumnMatchingHibernateDAO();
			}  else if(classe.equals(DiscoursiveQuestionAnswer.class)){
				return new DiscoursiveQuestionAnswerHibernateDAO();
			}  else if(classe.equals(EvaluationUserAnswer.class)){
				return new EvaluationUserAnswerHibernateDAO();
			}
		}
		return null;
	}

}
