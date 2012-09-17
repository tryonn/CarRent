package controller.pendulo.action;

import model.Model;
import model.pendulo.MedicaoStepModel;
import exception.ActionCreateException;

/**
 * Classe respons�vel pela a��o de atualizar a altura do aparato
 * 
 * @author amadeus
 * @version 1.0
 */
public class atualizaAlturaAction extends PenduloAction {

	private String altura;
	
	/**
	 * Construtor da classe
	 * 
	 * @param altura Nova altura do aparato
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o da a��o de atualizar a altura do aparato 
	 */
	public atualizaAlturaAction(String altura) throws ActionCreateException {
		super(MedicaoStepModel.class);
		this.altura = altura;
	}
	
	/**
	 * M�todo que realiza a a��o de atualizar a altura do aparato
	 * 
	 * @param model Modelo cuja altura ser� atualizada
	 * @return boolean Sucesso da opera��o
	 */
	public boolean doAction(Model model) {
		MedicaoStepModel stepModel = (MedicaoStepModel) model;
		stepModel.setAltura(this.altura);
		return true;
	}

}
