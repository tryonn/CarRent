package view.pendulo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Model;
import model.pendulo.MedicaoStepModel;
import util.ControllerObserver;
import util.resource.Resource;
import controller.pendulo.PenduloMainControler;
import exception.ActionCreateException;
import exception.ActionExecuteException;

/**
 * Classe que representa a visão do passo de Medição do aparato
 * 
 * @author amadeus
 * @version 1.0
 */
public class MedicaoStepView extends PenduloView {
	
	private JLabel stepID = null;
	private JLabel stepTitle = null;
	private JLabel pendulum = null;
	private JLabel animacao = null;
	private JLabel instruction1 = null;
	private JLabel instruction2 = null;
	private JLabel instruction3 = null;
	private JLabel altura = null;
	private JTextField alturaBox = null;
	
	private int currentInstruction = 1;
	
	private PenduloMainControler controller;
	private AtualizaAltura atualizaAltura = new AtualizaAltura();

	private ViewControllerListener listener = new ViewControllerListener(); 

	/**
	 * Construtor que inicializa os labels e coloca o ViewCOntrolerListener como observador 
	 * do PenduloMainController
	 *  
	 * @param controller controlador principal que terá como observador o ViewControllerListener 
	 */	
	public MedicaoStepView(PenduloMainControler controller) {
		
		this.controller = controller;
		controller.addObserver(listener);
		
		setLayout(null);
        this.setBackground(Resource.backgroundColor);
        
		stepID = new JLabel();
		stepTitle = new JLabel();
		pendulum = new JLabel();
		animacao = new JLabel();
		instruction1 = new JLabel();
		instruction2 = new JLabel();
		instruction3 = new JLabel();
		altura = new JLabel();
		alturaBox = new JTextField();
		alturaBox.setEditable(false);
		
		init();
	}

	/**
	 * Método que configura todos os labels da classe
	 *
	 */
    private void init() {
    	stepID.setIcon(Resource.stepID_3);
    	stepID.setLocation(541,11);
    	stepID.setSize(204,26);
    	add(stepID);
    	
        stepTitle.setIcon(Resource.medicao);
        stepTitle.setSize(227,50);
        stepTitle.setLocation(20,7);
        add(stepTitle);

        pendulum.setIcon(Resource.pendulum);
        pendulum.setSize(223,470);
        pendulum.setLocation(582,5);
        add(pendulum);

        animacao.setIcon(Resource.findCenterMass);
        animacao.setLocation(52,124);
        animacao.setSize(310,240);
        add(animacao);
        
        instruction1.setText(Resource.medicaoInstructionText1);
        instruction1.setSize(242,15);
        instruction1.setLocation(40,106);
        instruction1.setFont(Resource.font);
        add(instruction1);

        instruction2.setText(Resource.medicaoInstructionText2);
        instruction2.setSize(219,15);
        instruction2.setLocation(40,377);
        instruction2.setFont(Resource.font);
        add(instruction2);
        
        instruction3.setText(Resource.medicaoInstructionText3);
        instruction3.setSize(219,15);
        instruction3.setLocation(40,395);
        instruction3.setFont(Resource.font);
        add(instruction3);

        altura.setText(Resource.medicaoInstructionText4);
        altura.setSize(219,15);
        altura.setLocation(40,409);
        altura.setFont(Resource.font);
        add(altura);

        alturaBox.setLocation(40,427);
        alturaBox.setSize(60,20);
        alturaBox.setFont(Resource.font);
        add(alturaBox);
        
        alturaBox.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent event) {
				SwingUtilities.invokeLater(atualizaAltura);
			}

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}
        	
        });
    }
    
    /**
     * Método que atualiza a visão quando há mudança no modelo correspondente
     * 
     * @param model recebe o modelo a partir do qual a visão se desenha
     */
	public void update(Model model) {
		if (model instanceof MedicaoStepModel) {
			MedicaoStepModel stepModel = (MedicaoStepModel) model;
			
			// Mudança de Instrução
			if (stepModel.getInstrucao() != getCurrentInstruction()) {
				setCurrentInstruction(stepModel.getInstrucao());
				updateView();
			} 
			
			// Alteração da altura
			if (! stepModel.getAltura().equals(alturaBox.getText())) {
				alturaBox.setText(stepModel.getAltura());
			}
		}
	}
	
	/**
	 * Método que atualiza a visão de acordo com a instrução atual
	 *
	 */
	private void updateView() {
		switch (getCurrentInstruction()) {
		case 1:
	        instruction2.setLocation(40,377);
	        instruction3.setLocation(40,395);
	        animacao.setIcon(Resource.findCenterMass);
	        animacao.setLocation(52,124);
			break;
		case 2:
	        instruction2.setLocation(40,124);
	        instruction3.setLocation(40,385);
	        animacao.setIcon(Resource.measureHeightGif);
	        animacao.setLocation(52,142);
			break;
		case 3:
			instruction2.setLocation(40,124);
	        instruction3.setLocation(40,142);
	        animacao.setIcon(Resource.movePendulum);
	        animacao.setLocation(52,160);
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
	
	/**
	 * Classe que atualiza a altura do aparato, caso o controlador tenhsa poder suficiente
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class AtualizaAltura implements Runnable {

		public void run() {
			try {
				controller.atualizaAltura(alturaBox.getText());
			} catch (ActionCreateException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			} catch (ActionExecuteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Classe privada que verifica se o controlador tem ou não poder para ter 
	 * a altura como uma característica configurável
	 * 
	 * @author amadeus
	 * @version 1.0
	 *
	 */
	private class ViewControllerListener implements ControllerObserver {

		public void update() {
			if (controller.isOwner()) {
				alturaBox.setEditable(true);
			}
		}
		
	}

}
