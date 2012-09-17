package controller.pendulo.action;

import model.Model;
import model.pendulo.PenduloMainModel;
import exception.ActionCreateException;

/**
 * Classe responsável pela ação de decrementar o passo (ou alguma instrução
 * interna do mesmo)
 * 
 * @author amadeus
 * @version 1.0
 */
public class PriorAction extends PenduloAction {

	/**
	 * Contrutor da classe
	 * 
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação da ação de decrementar o passo
	 */
	public PriorAction() throws ActionCreateException  {
		super(PenduloMainModel.class);
	}
	
	/**
	 * Método que realiza a ação de decrementar o passo (ou alguma instrução
	 * interna do mesmo)
	 * 
	 * @param model Modelo atual cujo passo será decrementado
	 * @return boolean Sucesso da operação
	 */
	public boolean doAction(Model model) {
		PenduloMainModel penduloModel = (PenduloMainModel) model;
		penduloModel.prior();
		return true;
	}

}
