package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GroupwareModel;
import model.Model;
import model.pendulo.MedicaoStepModel;
import model.pendulo.PenduloMainModel;
import util.resource.Resource;
import view.View;
import view.pendulo.MedicaoStepView;
import controller.Controller;
import controller.pendulo.PenduloMainControler;
import exception.ActionCreateException;
import exception.ActionExecuteException;

public class Teste extends JFrame {

	private PenduloMainControler controller;
	private MedicaoStepView view;
	private Model model;
	

	private JPanel norte = new JPanel();
	private JButton button1 = new JButton(Resource.nextButtonText);
	private JButton button2 = new JButton(Resource.previousButtonText);
	
	public Teste() throws ActionCreateException {
		
		model = new PenduloMainModel();
		controller = new PenduloMainControler(model);
		view = new MedicaoStepView(controller);
		model.addObserver(view);

		this.setSize(new Dimension(800, 600));
        this.setTitle(Resource.title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(norte, BorderLayout.NORTH);
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.next();
				} catch (ActionExecuteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
		});
		
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.prior();
				} catch (ActionExecuteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
		});
		norte.add(button2);
		norte.add(button1);
	}
	
	/**
	 * @param args
	 * @throws ActionCreateException 
	 */
	public static void main(String[] args) throws ActionCreateException {
		Teste t = new Teste();
		t.setVisible(true);
	}

}
