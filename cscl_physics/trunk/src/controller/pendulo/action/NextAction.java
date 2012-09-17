package controller.pendulo.action;

import model.Model;
import model.pendulo.PenduloMainModel;
import exception.ActionCreateException;

/**
 * Classe respons�vel pela a��o de incrementar o passo (ou alguma instru��o
 * interna do mesmo)
 * 
 * @author amadeus
 * @version 1.0
 */
public class NextAction extends PenduloAction {

	/**
	 * Contrutor da classe
	 * 
	 * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o da a��o de incrementar o passo
	 */
	public NextAction() throws ActionCreateException {
		super(PenduloMainModel.class);
	}
	
	/**
	 * M�todo que realiza a a��o de incrementar o passo (ou alguma instru��o
	 * interna do mesmo)
	 * 
	 * @param model Modelo atual cujo passo ser� incrementado
	 * @return boolean Sucesso da opera��o
	 */
	public boolean doAction(Model model) {
		PenduloMainModel penduloModel = (PenduloMainModel) model;
		penduloModel.next();
		return true;
	}

}
