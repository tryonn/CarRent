package br.ufpe.cin.amadeus;

import java.util.Date;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.MultipleChoiceAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.TrueOrFalseAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.alternative.AlternativeAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.sentence.SentenceAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOrFalseQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.alternative.Alternative;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.sentence.Sentence;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;


public class Teste {

	public static void main(String[] args) {
		
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		
		
		/*TrueOrFalseAnswer tfa = new TrueOrFalseAnswer();
		TrueOrFalseQuestion tfq = new TrueOrFalseQuestion();
		Sentence sentence = new Sentence();
		sentence.setDescription("Eu nunca digo a verdade");
		sentence.setOrder('1');
		sentence.setPosition(0);
		sentence.setValue(true);
		tfq.addSentence(sentence);
		tfq.setLevel('b');
		tfq.setQuestion("Responda corretamente a pergunta");
		tfq.setPosInEvaluation(2);
		
		Evaluation eva = new Evaluation();
		eva.setBeginning(new Date(2007,03,14));
		eva.setEnd(new Date(2007,11,14));
		eva.setMaxScore(10.0);
		eva.setName("Avaliação EOF");
		eva.addObjectiveQuestion(tfq);
		eva.setSqmodule(10);
		amadeus.insertEvaluation(eva);*/
		
		
		
		
		/*AlternativeAnswer answer = new AlternativeAnswer();
		answer.setAnswered(true);
		MultipleChoiceAnswer mca = new MultipleChoiceAnswer();
		mca.addAlternatives(answer);*/
		
		//questao de avaliacao
		/*Alternative alt  = new Alternative();
		alt.setCorrect(true);
		alt.setDescription("Eu tenho impressao que isso funciona");
		alt.setOrder('a');
		MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
		mcq.setLevel('a');
		mcq.setQuestion("Responda se icamaan ta bugando a base de dados:");
		mcq.addAlternatives(alt);*/
		
		/*User user = new User(); 
	    user.setId(27);
	    
	    Evaluation eva2 = new Evaluation();
		eva2.setId(21);*/
		//eva2.addObjectiveQuestion(mcq);
		//amadeus.insertObjectiveQuestion_Evaluation(eva2,mcq);
		
		//nova resposta da avaliacao
		/*EvaluationUserAnswer eva = new EvaluationUserAnswer();
		eva.setId(10);
	    eva.setUser(user);
		eva.setDateAnswer(new Date(70000));
		eva.setEvaluation(eva2);*/
		
		//true or false resposta
		SentenceAnswer answer2 = new SentenceAnswer();
		answer2.setAnswer(false);
		answer2.setStatus(true);
		
	//	TrueOrFalseAnswer tfa = new TrueOrFalseAnswer();
		/*tfa.addSentences(answer2);
		tfa.setPosInEvaluation(tfq.getPosInEvaluation());
		//multiple choice resposta
		AlternativeAnswer alta = new AlternativeAnswer();
		alta.setAnswered(true);
		MultipleChoiceAnswer mca = new MultipleChoiceAnswer();
		mca.addAlternatives(alta);
		
		DiscoursiveQuestionAnswer dqa = new DiscoursiveQuestionAnswer();
		dqa.setAnswer("ta certo porque tem que ta");*/
		
	/*	EvaluationUserAnswer eua = new EvaluationUserAnswer();
		eua.setDateAnswer(new Date(2007,03,26));
		eua.setEvaluation(eva2);
		eua.setUser(user);
		eua.addObjectiveQuestion(tfa);
		eua.addDiscoursiveQuestion(dqa);
		amadeus.insertEvaluationAnswer(eua);*/
		
		/*DiscoursiveQuestion dq = new DiscoursiveQuestion();
		dq.setLevel('a');
		dq.setPosInEvaluation(1);
		dq.setQuestion("Qual o sentido da vida?");
		
		Evaluation eva = new Evaluation();
		eva.setBeginning(new Date(2007,03,14));
		eva.setEnd(new Date(2007,11,14));
		eva.setMaxScore(10.0);
		eva.setName("Avaliação que não é para os fracos");
		eva.setSqmodule(10);
		eva.addDiscoursiveQuestion(dq);
		amadeus.insertEvaluation(eva);
		
		EvaluationUserAnswer eua = new EvaluationUserAnswer();
		eua.setEvaluation(eva);
		eua.setDateAnswer(new Date(2007,10,14));
		User user = new User(); user.setId(27); 
		eua.setUser(user);
		
		DiscoursiveQuestionAnswer dqa = new DiscoursiveQuestionAnswer();
		dqa.setAnswer("Rapaz... o sentido da vida eh viver ta ligado...");
		dqa.setPosInEvaluation(dq.getPosInEvaluation());
		
		eua.addDiscoursiveQuestion(dqa);
		amadeus.insertEvaluationAnswer(eua);*/
		
		
		Evaluation eva = amadeus.searchEvaluation(38);
		List<DiscoursiveQuestion> question = eva.getDiscoursiveQuestions();
		System.out.println("Posicao da questao na avaliacao"+question.get(0).getPosInEvaluation());
		
		EvaluationUserAnswer eua = amadeus.getEvaluationUserAnswer(38,27);//38 eh o evaluationID e 27 o userID
		List<DiscoursiveQuestionAnswer> answer = eua.getDiscoursiveAnswers();
		System.out.println("Posicao da resposta na avaliacao"+answer.get(0).getPosInEvaluation());
		

	
		
		

		
		
		
//		List lista = amadeus.getListEvaluationUserAnswer(1);
		
		//amadeus.getEvaluationsFromModule(20);
		//amadeus.insertEvaluationAnswer(eva);
		

//		
//		List<Curso> results = amadeus.searchrCursoPorCriterio("dores");
//		System.out.println(results.get(0).getNome());
		
		
//		
//		Curso curso = amadeus.searchrCurso(2);
//		System.out.println(courses.getNome());
//		//Usuario yuri = amadeus.searchrUsuarioPorLogin("ycssp");
//		Usuario rodrigo = amadeus.searchrUsuarioPorLogin("rlmb");
//		
//		try{
//		//amadeus.registrationUsuario(yuri, curso);
//		amadeus.registrationUsuario(rodrigo,curso);
//		} catch (Exception e){
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
		
		/*try{
			amadeus.registrationUsuario(usr,curso);
		} catch (MatriculaUsuarioException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception err) {
			err.printStackTrace();
			System.out.println(err.getMessage());
		}*/
		/*Papel papel = amadeus.searchrPapel(1);
		
		
		PessoaPapelCurso ppc = new PessoaPapelCurso();
		ppc.setCurso(curso);
		ppc.setPapel(papel);
		ppc.setPessoa(usr.getPessoa());
		
		System.out.println(courses.getId()+","+papel.getId());
		ppc.setData(new Date(12,12,1212));
		try{
			amadeus.inserirPessoaPapelCurso(ppc);
		} catch (Exception e){
			System.out.println("capturou exceção!");
			e.printStackTrace();
		}*/
		
		
		/*Usuario usr = amadeus.searchrUsuarioPorEmail("rlmb@cin.ufpe.br");
		System.out.println(usr.getPessoa().getNome());
		
		try{
			Usuario usr2 = amadeus.searchrUsuarioPorEmail("emailnaoexistente@a.c");
			if(usr2 == null){
				System.out.println("Retorna nulo!");
			} else {
				System.out.println("nao retorna nulo!");
			}
		} catch (Exception e){
			System.out.println("XXXXXXXXXXXXXXXX");
		}*/
		/*Usuario usr = new Usuario();
		Pessoa pessoa = new Pessoa();
		Perfil perfil = new Perfil();
		Permissao p = new Permissao();
		
		usr.setLogin("rlmb");
		usr.setSenha("rlmb");
		pessoa.setCpf("02445454");
		pessoa.setDataNascimento(new Date(12,12,1212));
		pessoa.setEmail("rlmb@cin.ufpe.br");
		pessoa.setEndereco("nao tem");
		pessoa.setNome("Rodrigo Lumack");
		pessoa.setSexo('M');
		pessoa.setTelefone("99999999");
		perfil.setInterno('1');
		perfil.setNome("Usuário");
		p.setNome("Sei la2");
		
		HashSet permissoes = new HashSet();
		permissoes.add(p);
		
		perfil.setPermissoes(permissoes);
		usr.setPerfil(perfil);
		
		usr.setPessoa(pessoa);
		
		amadeus.registerUsuario(usr);*/
		
		/*Usuario usr =  amadeus.searchrUsuario("abcde");
		usr.getPessoa().setNome("Majfifjf");
		usr.getPessoa().setEndereco("Recife");
		usr.getPessoa().setEmail("hrjrhjshrjsh");
		usr.getPessoa().setDataNascimento(new Date(05,05,1515));
		amadeus.atualizarUsuario(usr);
		*/
		
		
		/*
		 List<Usuario> usuarios = amadeus.listUsuarios();
	
		for(Usuario usr : usuarios){
			System.out.println(usr.getPessoa().getNome());
		}*/
		
		/*Ferramenta ferramenta = new Ferramenta();
		ferramenta.setDescricao("Chat para discussao dos alunos...");
		ferramenta.setNome("Chat");
		Ferramenta ferramenta2 = new Ferramenta();
		ferramenta2.setDescricao("Mural para discussao dos alunos...");
		ferramenta2.setNome("Mural");
		Ferramenta ferramenta3 = new Ferramenta();
		ferramenta3.setDescricao("Forum para discussao dos alunos...");
		ferramenta3.setNome("forum");
		HashSet ferramentas = new HashSet();
		ferramentas.add(ferramenta);
		ferramentas.add(ferramenta2);
		ferramentas.add(ferramenta3);
		Area area = new Area();
		area.setDescricao("Essa area eh Compiladores");
		area.setNome("Compiladores");
		Curso curso = new Curso();
		courses.setAlunos(10);
		courses.setIdAutor(1);
		courses.setNome("Compiladores");
		courses.setArea(area);
		courses.setFerramentas(ferramentas);
		courses.setDataCriacao(new Date(2006,12,2));
		courses.setDataFim(new Date(2006,12,2));
		courses.setDataFimInscricao(new Date(2006,12,2));
		courses.setDataInicio(new Date(2006,12,6));
		courses.setDataInicioInscricao(new Date(2006,12,2));
		amadeus.registerCurso(curso);*/
		
		//System.out.println("Nome: "+usuario.getPessoa().getNome());
		
		/*
		Usuario usuario2 = new Usuario();
		usuario2.setLogin("usuario2");
		System.out.println("Passou por aqui 3!");
		usuario2.setSenha("senha2");
		amadeus.registerUsuario(usuario2);
		System.out.println("Passou por aqui 4!");
		Usuario usuario = amadeus.searchrUsuario("usuario1");
		Usuario user = amadeus.searchrUsuario("usuario2");
		System.out.println("Passou por aqui 5");
		System.out.println("Login: " + usuario.getLogin());
		System.out.println("Senha: " + usuario.getSenha());
		System.out.println("Passou por aqui 6");
		System.out.println("Login: " + user.getLogin());
		System.out.println("Senha: " + user.getSenha());
		*/
		
		/*
		Pessoa pessoa = (Pessoa) HibernateUtil.getSessionFactory().openSession().get(Pessoa.class, "1");
		System.out.println(pessoa.getNome());
		*/
	}
}
