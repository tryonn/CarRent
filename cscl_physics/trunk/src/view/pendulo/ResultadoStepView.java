package view.pendulo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import model.Model;
import model.pendulo.ResultadoStepModel;
import util.resource.Resource;

/**
 * Classe que representa a visão do passo de Resultados do experimento
 * 
 * @author amadeus
 * @version 1.0
 */
public class ResultadoStepView extends PenduloView {

	private JLabel stepID = null;
	private JLabel stepTitle = null;
	private JLabel pendulum = null;
	private JPanel panelCard = null;
	private JPanel panelWaiting = null;
	private JPanel panelTable = null;
	private JLabel waitLabel = null;
	private JTable resultTable = null;
	private JScrollPane resultScroll = null;
	
	private CardLayout cardLayout = null;
	
	/**
	 * Construtor da classe que inicializa os labels
	 *
	 */
	public ResultadoStepView() {
		setLayout(null);
        this.setBackground(Resource.backgroundColor);
        
		stepID = new JLabel();
		stepTitle = new JLabel();
		pendulum = new JLabel();
		panelCard = new JPanel();
		panelWaiting = new JPanel();
		panelTable = new JPanel();		
		waitLabel = new JLabel();
		cardLayout = new CardLayout();
		resultTable = new JTable();
		resultScroll = new JScrollPane(resultTable);
		
		init();
	}
		
	/**
	 * Método que configura todos os labels da classe
	 *
	 */
	private void init() {
		stepID.setIcon(Resource.stepID_4);
    	stepID.setLocation(541,11);
    	stepID.setSize(204,26);
    	add(stepID);
    	
        stepTitle.setIcon(Resource.resultados);
        stepTitle.setSize(227,50);
        stepTitle.setLocation(20,7);
        add(stepTitle);

        pendulum.setIcon(Resource.pendulum);
        pendulum.setSize(223,470);
        pendulum.setLocation(582,5);
        add(pendulum);

        panelCard.setLayout(cardLayout);
        panelCard.setLocation(35, 85);
        panelCard.setSize(530,181);
        add(panelCard);
        
        waitLabel.setText(Resource.waitingText);
        waitLabel.setFont(Resource.waitFont);
        waitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelWaiting.setLayout(new BorderLayout());
        panelWaiting.setLocation(0,0);
        panelWaiting.setBackground(Resource.backgroundColor);
        panelWaiting.add(waitLabel, BorderLayout.CENTER);
       
        panelTable.setLocation(0, 0);
        panelTable.setBackground(Resource.backgroundColor);
        panelTable.add(resultScroll);
                
        panelCard.add(panelWaiting, Resource.panelWaitingIdentifier);
        panelCard.add(panelTable, Resource.panelTableIdentifier);
        
    }
	
	/**
     * Método que atualiza a visão quando há mudança no modelo correspondente
     * 
     * @param model recebe o modelo a partir do qual a visão se desenha
     */
	public void update(Model model) {
		if (model instanceof ResultadoStepModel) {
			ResultadoStepModel resultadoStepModel = (ResultadoStepModel) model;
			if (resultadoStepModel.isReading()) {
				cardLayout.show(panelCard, Resource.panelWaitingIdentifier);
			} else {
				resultTable.setModel(resultadoStepModel.getTableModel());
				resultTable.setGridColor(Color.BLUE);
				cardLayout.show(panelCard, Resource.panelTableIdentifier);
			}
		}
		
	}
	

}  