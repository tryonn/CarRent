package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.pendulo.ResultadoTableModel;

public class TesteTable extends JFrame {
	
	private JTable tabela;
	
	public TesteTable() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabela = new JTable(new ResultadoTableModel());
		tabela.setCellSelectionEnabled(false);
		//tabela.setPreferredScrollableViewportSize(new Dimension(500, 150));
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setAutoscrolls(true);
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		getContentPane().add(panel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new TesteTable();
		frame.setVisible(true);
	}

}
