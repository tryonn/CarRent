package workgroup.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.message.ChatMessage;
import workgroup.message.Message;

/**
 * Classe gráfica relativa ao panel do chat
 * @author amadeus
 * @version 1.3
 */
public class WGChatPanel extends JPanel {
	
	private static final long serialVersionUID = 13L;
	
	protected JPanel pnlTitle = new JPanel();
	protected JPanel bottomPanel = new JPanel();
	protected JScrollPane scrChatWindow;
	protected JTextPane textWindow = new JTextPane();
	protected JTextField chatField = new JTextField();
	protected JButton btnSend = new JButton("Enviar");

	private WorkGroupConnection connection;
	
	/**
	 * Construtor da classe. Ele inicializa todos os componentes e adiciona os listeners 
	 * nos componentes e as ações a serem tomadas (actionPerformed)
	 * 
	 * @param connection Conexão do workgroup
	 */
	public WGChatPanel(WorkGroupConnection connection) {
		
		this.connection = connection;
		
		if (connection != null) {
			connection.addWorkGroupListener(new WorkGroupAdapter() {
				public void onMessage(Message message) {
					if (message instanceof ChatMessage) {
						textWindow.setText(textWindow.getText() + message.getObject() + "\n");
						textWindow.setCaretPosition(textWindow.getText().length());
					}
				}
			});
		}
		
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		pnlTitle.add( new JLabel("WorkGroup Chat") );
		pnlTitle.setBorder(BorderFactory.createEtchedBorder());
		
		textWindow.setEditable(false);
	    scrChatWindow = new JScrollPane( textWindow );
	    scrChatWindow.setMinimumSize(new Dimension(250,150));

		btnSend.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendChat();
			}
		});
		
		chatField.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendChat();
			}
		});
		
		bottomPanel.setBorder(BorderFactory.createEtchedBorder());
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(chatField, BorderLayout.CENTER);
		bottomPanel.add(btnSend, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		//this.add( pnlTitle, BorderLayout.NORTH );
		this.add( scrChatWindow, BorderLayout.CENTER );
		this.add( bottomPanel, BorderLayout.SOUTH );
		this.setMinimumSize(new Dimension(250,150));
		this.validate();
	}
	
	/**
	 * Método que altera a fonte do chat
	 * 
	 *  @param font A nova fonte do chat
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (this.textWindow != null) {
			this.textWindow.setFont(font);
		}
	}
	
	/**
	 * Método que modifica o texto do chat
	 * 
	 * @param text O novo texto do chat
	 */
	public void setText(String text) {
		textWindow.setText(text);
	}
	
	/**
	 * Método que retorna o texto do chat
	 * 
	 * @return String O texto do chat
	 */
	public String getText() {
		return textWindow.getText();
	}
	
	/**
	 * Método que envia o chat através da rede (utilizando o método 
	 * sendChatMessage de WorkGroupConnection)
	 *
	 */
	private void sendChat() {
		try {
			connection.sendChatMessage(chatField.getText());
			chatField.setText("");
		} catch (IOException e) {
			/**TODO apresentar messagem de erro ao usuário */
			e.printStackTrace();
		}
	}

	/**
	 * Método main usado para testar o chat
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("testando Chat");
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createBevelBorder(1));
		  
		// Componente WGChatPanel
		WGChatPanel chatPanel = new WGChatPanel(null);
		bottomPanel.add(chatPanel, BorderLayout.CENTER);
		
		// Adiciona WGChatPanel ao Frame
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		frame.setSize(480,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.validate();
		frame.setVisible(true);
	}
}


