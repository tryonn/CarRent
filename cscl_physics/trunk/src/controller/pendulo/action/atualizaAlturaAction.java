package controller.pendulo.action;

import model.Model;
import model.pendulo.MedicaoStepModel;
import exception.ActionCreateException;

/**
 * Classe responsável pela ação de atualizar a altura do aparato
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
	 * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação da ação de atualizar a altura do aparato 
	 */
	public atualizaAlturaAction(String altura) throws ActionCreateException {
		super(MedicaoStepModel.class);
		this.altura = altura;
	}
	
	/**
	 * Método que realiza a ação de atualizar a altura do aparato
	 * 
	 * @param model Modelo cuja altura será atualizada
	 * @return boolean Sucesso da operação
	 */
	public boolean doAction(Model model) {
		MedicaoStepModel stepModel = (MedicaoStepModel) model;
		stepModel.setAltura(this.altura);
		return true;
	}

}
