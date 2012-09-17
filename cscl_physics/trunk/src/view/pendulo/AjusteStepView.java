package view.pendulo;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import model.pendulo.AjusteStepModel;
import model.pendulo.MontagemStepModel;
import util.resource.Resource;
import view.View;

/**
 * Classe que representa a visão do passo de Ajuste do aparato
 * 
 * @author amadeus
 * @version 1.0
 */
public class AjusteStepView extends PenduloView {
	
	private JLabel stepID = null;
	private JLabel stepTitle = null;
	private JLabel pendulum = null;
	private JLabel animacao = null;
	private JLabel instruction1 = null;
	private JLabel instruction2 = null;
	
	private int currentInstruction = 1;
	
	/**
	 * Construtor da classe que inicializa os labels
	 *
	 */
	public AjusteStepView() {
		setLayout(null);
        this.setBackground(Resource.backgroundColor);
        
		stepID = new JLabel();
		stepTitle = new JLabel();
		pendulum = new JLabel();
		animacao = new JLabel();
		instruction1 = new JLabel();
		instruction2 = new JLabel();
		
		init();
	}

	/**
	 * Método que configura todos os labels da classe
	 *
	 */
    private void init() {
    	stepID.setIcon(Resource.stepID_2);
    	stepID.setLocation(541,11);
    	stepID.setSize(204,26);
    	add(stepID);
    	
        stepTitle.setIcon(Resource.ajustes);
        stepTitle.setSize(227,50);
        stepTitle.setLocation(20,7);
        add(stepTitle);

        pendulum.setIcon(Resource.pendulum);
        pendulum.setSize(223,470);
        pendulum.setLocation(582,5);
        add(pendulum);

        animacao.setIcon(Resource.opticalsensorGif);
        animacao.setLocation(202,124);
        animacao.setSize(310,240);
        add(animacao);
        
        instruction1.setText(Resource.ajusteInstructionText1);
        instruction1.setSize(242,15);
        instruction1.setLocation(190,106);
        instruction1.setFont(Resource.font);
        add(instruction1);

        instruction2.setText(Resource.ajusteInstructionText2);
        instruction2.setSize(219,15);
        instruction2.setLocation(190,377);
        instruction2.setFont(Resource.font);
        add(instruction2);
    }
    
    /**
     * Método que atualiza a visão quando há mudança no modelo correspondente
     * 
     * @param model recebe o modelo a partir do qual a visão se desenha
     */
	public void update(Model model) {
		if (model instanceof AjusteStepModel) {
			AjusteStepModel stepModel = (AjusteStepModel) model;
			setCurrentInstruction(stepModel.getInstrucao());
			updateView();
		}
	}
	
	/**
	 * Método que atualiza a visão de acordo com a instrução atual
	 *
	 */
	private void updateView() {
		switch (getCurrentInstruction()) {
		case 1:
	        instruction2.setLocation(190,377);
	        animacao.setIcon(Resource.opticalsensorGif);
	        animacao.setLocation(202,124);
			break;
		case 2:
	        instruction2.setLocation(190,124);
	        animacao.setIcon(Resource.testSensorGif);
	        animacao.setLocation(202,142);
			break;
		}
	}

	/**
	 * Método que retorna a instrução atual
	 * 
	 * @return int retorna a instrução atual
	 */
	public int getCurrentInstruction() {
		return currentInstruction;
	}

	/**
	 * Método que substitui a instrução atual pela dada como parâmetro
	 * 
	 * @param currentInstruction instrução que será a atual
	 */
	public void setCurrentInstruction(int currentInstruction) {
		this.currentInstruction = currentInstruction;
	}

}
