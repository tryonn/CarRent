package model.pendulo;

import model.Model;
import util.Action;
import util.ModelObserver;
import exception.ActionExecuteException;

/**
 * Classe que representa o modelo principal do experimento
 * 
 * @author amadeus
 * @version 1.0
 */
public class PenduloMainModel extends Model implements ModelObserver {

	private StepModel[] steps = new StepModel[4];
	private int step;
	
	/**
	 * Construtor da classe que inicializa os submodelos e se torna observador de todos eles
	 *
	 */
	public PenduloMainModel() {
		step = 0;
		steps[0] = new MontagemStepModel(1);
		steps[0].addObserver(this);
		steps[1] = new AjusteStepModel(2);
		steps[1].addObserver(this);
		steps[2] = new MedicaoStepModel(3);
		steps[2].addObserver(this);
		steps[3] = new ResultadoStepModel(4);		
		steps[3].addObserver(this);
	}
	
	/**
	 * M�todo que retorna o modelo do passo atual
	 * 
	 * @return StepModel O modelo do passo atual
	 */
	public StepModel getCurrentStep() {
		return steps[step];
	}
	
	/**
	 * M�todo que incrementa a instru��o de um passo ou o pr�prio passo, caso
	 * esteja na �ltima instru��o do mesmo
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
	 */
	public boolean next() {
		boolean sucesso = steps[step].next();
		if (! sucesso) {
			sucesso = nextStep();
		}
		return sucesso;
	}
	
	/**
	 * M�todo privado que realmente incrementa o passo
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
	 */
	private boolean nextStep() {
		boolean retorno = false;
		if (step < 3) {
			step++;
			
			steps[step].onEnter();
			setChanged();
			notifyObservers();

			retorno = true;
		}
		return retorno;
	}

	/**
	 * M�todo que decrementa a instru��o de um passo ou o pr�prio passo, caso
	 * esteja na �ltima instru��o do mesmo
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
	 */
	public boolean prior() {
		boolean sucesso = steps[step].prior();
		if (! sucesso) {
			sucesso = priorStep();
		}
		return sucesso;
	}
	
	/**
	 * M�todo privado que realmente decrementa o passo
	 * 
	 * @return boolean O sucesso ou n�o da opera��o
	 */
	private boolean priorStep() {
		boolean retorno = false;
		if (step > 0) {
			step--;
			
			steps[step].onExit();
			setChanged();
			notifyObservers();

			retorno = true;
		}
		return retorno;
	}

	/**
	 * M�todo que avisa ao observador desta classe que o modelo sofreu uma altera��o
	 * 
	 * @param model O modelo que sofreu uma altera��o
	 */
	public void update(Model model) {
		setChanged();
		if (model == getCurrentStep()) {
			notifyObservers();
		}
	}

	/**
	 * M�todo que executa uma a��o 
	 * 
	 * @throws ActionExecuteException Exce�ao lan�ada quando algo inesperado ocorre
	 * durante a execu��o do execute
	 */
	public void execute(Action action) throws ActionExecuteException {
		if (! action.execute(this)) {
			getCurrentStep().execute(action);
		}
	}

	/**
	 * M�todo que notifica os observadores deste modelo para que eles se atualizem
	 */
	public void notifyObservers() {
		notifyObservers(getCurrentStep());
	}
}
