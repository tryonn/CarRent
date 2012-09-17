package view.pendulo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Model;
import model.pendulo.StepModel;
import util.ControllerObserver;
import util.resource.Resource;
import view.View;
import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.ui.WGChatPanel;
import workgroup.ui.WGUserListPanel;
import controller.pendulo.PenduloMainControler;
import exception.ActionExecuteException;

/**
 * Classe que herda de PenduloView o m�todo update 
 * 
 * @author amadeus
 * @version 1.0
 */
public class PenduloMainView extends PenduloView {

	private PenduloView[] stepViews = new PenduloView[4];
	private int step = 1;
	private PenduloMainControler controller;
	
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel northSouthPanel;
	private JPanel westSouthPanel;
	private JPanel eastSouthPanel;
	private JPanel centerSouthPanel;
	private JButton newButton = null;
	private JButton openButton = null;
	private JButton saveButton = null;
    private JButton previousButton = null;
	private JButton nextButton = null;
	private JLabel logo = null;
	private CardLayout centerCardLayout = new CardLayout();
	
	private MainActionListener mainActionListener = new MainActionListener();
	private String user = Resource.userNotInformed;
	JLabel userLabel = null;

	private ViewControllerListener listener = new ViewControllerListener(); 

	/**
	 * Construtor que inicializa as vis�es e desenha a tela principal
	 * 
	 * @param controller Controlador principal (necess�rio para saber se devemos ou n�o desenhar
	 * os bot�es de "pr�ximo" e "anterior")
	 */
	public PenduloMainView(PenduloMainControler controller) {
		this.controller = controller;
		stepViews[0] = new MontagemStepView();
		stepViews[1] = new AjusteStepView();
		stepViews[2] = new MedicaoStepView(controller);
		stepViews[3] = new ResultadoStepView();
		
		// Chat e Lista de usuarios
		centerSouthPanel = new JPanel();
		eastSouthPanel = new JPanel();

		setLayout(new BorderLayout());
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getSouthPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * Construtor que inicializa as vis�es, desenha a tela principal e designa
	 * o computador como controlador do Chat
	 * 
	 * @param controller Controlador principal
	 * @param user Nome do usu�rio
	 * @param connection Conex�o do WorkGroup 
	 */
	public PenduloMainView(PenduloMainControler controller, String user, WorkGroupConnection connection) {
		this.controller = controller;
		controller.addObserver(listener);
		
		stepViews[0] = new MontagemStepView();
		stepViews[1] = new AjusteStepView();
		stepViews[2] = new MedicaoStepView(controller);
		stepViews[3] = new ResultadoStepView();
		
		// Conex�o, Chat e Lista de usuarios
		centerSouthPanel = new WGChatPanel(connection);;
		eastSouthPanel = new WGUserListPanel(connection);
		this.user = user;

		setLayout(new BorderLayout());
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getSouthPanel(), BorderLayout.SOUTH);
	}

	/**
     * M�todo que atualiza a vis�o quando h� mudan�a no modelo correspondente
     * 
     * @param model recebe o modelo a partir do qual sabe-se que vis�o desenhar
     */
	public void update(Model model) {
		if (model instanceof StepModel) {
			StepModel stepModel = (StepModel) model;
			getView(stepModel.getOrder()).update(model);
			if (getStep() != stepModel.getOrder()){
				setStep(stepModel.getOrder());
			}
		}
	}
	
	/**
	 * M�todo que retorna a ordem da vis�o atual
	 * 
	 * @param order ordem de cuja vis�o se quer ter poder
	 * @return View vis�o cuja ordem � a fornecida como par�metro
	 */
	private View getView(int order) {
		return stepViews[order - 1];
	}

	/**
	 * M�todo que configura o northPanel
	 * 
	 * @return JPanel northPanel com as caracter�sticas j� definidas
	 */
	private JPanel getNorthPanel() {
		if (northPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			northPanel = new JPanel();
			northPanel.setLayout(borderLayout);
			northPanel.setBackground(Resource.backgroundColor);

			FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
			JPanel localCenterPanel = new JPanel();
			localCenterPanel.setLayout(flowLayout);
			localCenterPanel.setBackground(Resource.backgroundColor);
			localCenterPanel.add(getNewButton(), null);
			localCenterPanel.add(getOpenButton(), null);
			localCenterPanel.add(getSaveButton(), null);
			
			userLabel = new JLabel(user);
			System.out.println(userLabel.getHeight());
			userLabel.setFont(Resource.font);
			if (controller.isOwner()) {
				userLabel.setIcon(Resource.userOwnerIcon);
			} else {
				userLabel.setIcon(Resource.userIcon);
			}
			JLabel spaceLabel = new JLabel();
			spaceLabel.setPreferredSize(new Dimension(45,32));
			
			JPanel eastPanel = new JPanel();
			eastPanel.setBackground(Resource.backgroundColor);
			eastPanel.add(userLabel);
			eastPanel.add(spaceLabel);
			
			northPanel.add(localCenterPanel, BorderLayout.CENTER);
			northPanel.add(eastPanel, BorderLayout.EAST);
		}
		return northPanel;
	}

	/**
	 * M�todo que configura o centerPanel
	 * 
	 * @return JPanel centerPanel com as caracter�sticas j� definidas
	 */
	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(centerCardLayout);
			centerPanel.add(stepViews[0], "1");
			centerPanel.add(stepViews[1], "2");
			centerPanel.add(stepViews[2], "3");
			centerPanel.add(stepViews[3], "4");
		}
		return centerPanel;
	}

	/**
	 * M�todo que configura o southPanel
	 * 
	 * @return JPanel southPanel com as caracter�sticas j� definidas
	 */
	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setLayout(new BorderLayout());
			southPanel.setSize(178,113);
			
			if (controller.isOwner()) {
				southPanel.add(getNorthSouthPanel(), BorderLayout.NORTH);
			}
			
			southPanel.add(getWestSouthPanel(), BorderLayout.WEST);
			southPanel.add(getEastSouthPanel(), BorderLayout.EAST);
			southPanel.add(getCenterSouthPanel(), BorderLayout.CENTER);
			southPanel.setBackground(Resource.backgroundColor);
		}
		return southPanel;
	}
	
	/**
	 * M�todo que configura o northSouthPanel
	 * 
	 * @return JPanel northSouthPanel com as caracter�sticas j� definidas
	 */
	private JPanel getNorthSouthPanel() {
		if (northSouthPanel == null) {
			northSouthPanel = new JPanel();
			northSouthPanel.setLayout(new FlowLayout());
			northSouthPanel.add(getPreviousButton(), null);
			northSouthPanel.add(getNextButton(), null);
			northSouthPanel.setBackground(Resource.backgroundColor);
		}
		return northSouthPanel;
	}
	
	/**
	 * M�todo que configura o westSouthPanel
	 * 
	 * @return JPanel westSouthPanel com as caracter�sticas j� definidas
	 */
	private JPanel getWestSouthPanel() {
		if (westSouthPanel == null) {
			FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
			westSouthPanel = new JPanel();
			westSouthPanel.setLayout(flowLayout);
			westSouthPanel.add(getLogo(), null);
			westSouthPanel.setBackground(Resource.backgroundColor);
		}
		return westSouthPanel;
	}
	
	/**
	 * M�todo que configura o eastSouthPanel
	 * 
	 * @return JPanel eastSouthPanel com as caracter�sticas j� definidas
	 */
	private JPanel getEastSouthPanel() {
		eastSouthPanel.setPreferredSize(new java.awt.Dimension(150,100));
		return eastSouthPanel;
	}
	
	/**
	 * M�todo que configura o centerSouthPanel
	 * 
	 * @return JPanel centerSouthPanel com as caracter�sticas j� definidas
	 */
	private JPanel getCenterSouthPanel() {
		centerSouthPanel.setPreferredSize(new java.awt.Dimension(535,100));
		return centerSouthPanel;
	}

	/**
	 * M�todo que configura o newButton
	 * 
	 * @return JPanel newButton com as caracter�sticas j� definidas
	 */
	private JButton getNewButton() {
		if (newButton == null) {
			newButton = new JButton();
			newButton.setText(Resource.newButtonText);
			newButton.setFont(Resource.font);
			newButton.setToolTipText(Resource.newButtonToolTipText);
			newButton.setMnemonic(KeyEvent.VK_N);
			newButton.addActionListener(mainActionListener);
		}
		return newButton;
	}
	
	/**
	 * M�todo que configura o openButton
	 * 
	 * @return JPanel openButton com as caracter�sticas j� definidas
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setText(Resource.openButtonText);
			openButton.setFont(Resource.font);
			openButton.setToolTipText(Resource.openButtonToolTipText);
			openButton.setMnemonic(KeyEvent.VK_A);
			openButton.addActionListener(mainActionListener);
		}
		return openButton;
	}
	
	/**
	 * M�todo que configura o saveButton
	 * 
	 * @return JPanel saveButton com as caracter�sticas j� definidas
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText(Resource.saveButtonText);
			saveButton.setFont(Resource.font);
			saveButton.setToolTipText(Resource.saveButtonToolTipText);
			saveButton.setMnemonic(KeyEvent.VK_S);
			saveButton.addActionListener(mainActionListener);
		}
		return saveButton;
	}
	  
	/**
	 * M�todo que configura o previousButton
	 * 
	 * @return JPanel previousButton com as caracter�sticas j� definidas
	 */
	private JButton getPreviousButton() {
		if (previousButton == null) {
			previousButton = new JButton();
			previousButton.setText(Resource.previousButtonText);
			previousButton.setToolTipText(Resource.previousButtonToolTipText);
			previousButton.setFont(Resource.font);
			previousButton.setEnabled(true);
			previousButton.setSize(82,25);
			previousButton.setMnemonic(KeyEvent.VK_LEFT);
			previousButton.addActionListener(mainActionListener);
		}
		return previousButton;
	}
	
	/**
	 * M�todo que configura o nextButton
	 * 
	 * @return JPanel nextButton com as caracter�sticas j� definidas
	 */
	private JButton getNextButton() {
		if (nextButton == null) {
			nextButton = new JButton();
			nextButton.setText(Resource.nextButtonText);
			nextButton.setToolTipText(Resource.nextButtonToolTipText);
			nextButton.setFont(Resource.font);
			nextButton.setEnabled(true);
			nextButton.setSize(81,25);
			nextButton.setMnemonic(KeyEvent.VK_RIGHT);
			nextButton.addActionListener(mainActionListener);
		}
		return nextButton;
	}
	
	/**
	 * M�todo que configura a marca do CCTE
	 * 
	 * @return JPanel a marca do CCTE com as caracter�sticas j� definidas
	 */
	private JLabel getLogo() {
		if (logo == null) {
			logo = new JLabel();
			logo.setIcon(Resource.logo);
			logo.setPreferredSize(new java.awt.Dimension(115,100));
	        logo.setSize(115,68);
	    }
		return logo;
	}

	/**
	 * M�todo que retorna o passo atual
	 * 
	 * @return int O passo atual
	 */
	public int getStep() {
		return step;
	}

	/**
	 * M�todo que substitui o passo atual pelo fornecido como par�metro
	 * 
	 * @param step O passo atual
	 */
	public void setStep(int step) {
		this.step = step;
		centerCardLayout.show(centerPanel, String.valueOf(step));
	}
	
	/**
	 * Classe privada que muda as vis�es de acordo com o pressionamento dos bot�es
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class MainActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == newButton) {
				//TODO
			} else if (event.getSource() == openButton) {
				//TODO
			} else if (event.getSource() == saveButton) {
				//TODO
			} else if (event.getSource() == nextButton) {
				try {
					controller.next();
				} catch (ActionExecuteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			} else if (event.getSource() == previousButton) {
				try {
					controller.prior();
				} catch (ActionExecuteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			} 
			
		}
		
	}

	/**
	 * Classe privada que desenha o �cone do usu�rio diferentemente caso ele seja o controlador do ChAt
	 * 
	 * @author amadeus
	 * @version 1.0
	 */
	private class ViewControllerListener implements ControllerObserver {

		public void update() {
			if (controller.isOwner()) {
				southPanel.add(getNorthSouthPanel(), BorderLayout.NORTH);
				userLabel.setIcon(Resource.userOwnerIcon);
			}
		}
		
	}
	
}
