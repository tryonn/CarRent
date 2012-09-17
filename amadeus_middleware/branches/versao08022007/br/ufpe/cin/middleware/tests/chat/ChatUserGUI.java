package br.ufpe.cin.middleware.tests.chat;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.util.Debug;

public class ChatUserGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JMenuBar jJMenuBar = null;

	private JMenu fileMenu = null;

	private JMenuItem exitMenuItem = null;

	private JMenuItem saveMenuItem = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnEnviar = null;

	private JTextArea inputArea = null;

	private JTextArea chatArea = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JList jList = null;
	
	//Elementos
	@SuppressWarnings("unused")
	private Vector<String> contatosId;
	@SuppressWarnings("unused")
	private Vector<ChatUser> contatos;
	@SuppressWarnings("unused")
	private String contatoAtual;
	@SuppressWarnings("unused")
	private Vector<String> conversas;
	
	private AmadeusMiddleware middleware;
	private ChatUser userAtual;

	/**
	 * This is the default constructor
	 */
	public ChatUserGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setSize(559, 341);
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(null);
		this.setTitle("Chat - ");
		String id = JOptionPane.showInputDialog(null,"Login","Digite seu login",1);
		String porta = JOptionPane.showInputDialog(null,"Porta","Digite a porta a se registrar",1);
		String hostIp = JOptionPane.showInputDialog(null,"Servidor IP", "Digite o endereço do servidor",1);
		try {
			this.userAtual = new ChatUser(id,InetAddress.getLocalHost().getHostAddress(),Integer.parseInt(porta));
			middleware = new AmadeusMiddleware(hostIp,1099,Integer.parseInt(porta));
			middleware.open();
			middleware.send(this.userAtual,"",0); //manda mensagem de protocolo informando q ele entrou
		} catch (NumberFormatException e) {
			Debug.printStack(e);
		} catch (UnknownHostException e) {
			Debug.printStack(e);
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(458,10,55,21));
			jLabel.setText("Online");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJScrollPane2(), null);
			jContentPane.add(getBtnEnviar(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("Arquivo");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(2,4,423,221));
			jScrollPane.setViewportView(getChatAread());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new java.awt.Rectangle(2,232,351,51));
			jScrollPane2.setViewportView(getInputArea());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes btnEnviar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new JButton();
			btnEnviar.setBounds(new java.awt.Rectangle(354,241,74,31));
			btnEnviar.setText("Enviar");
		}
		return btnEnviar;
	}

	/**
	 * This method initializes inputArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getInputArea() {
		if (inputArea == null) {
			inputArea = new JTextArea();
		}
		return inputArea;
	}

	/**
	 * This method initializes chatAread	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getChatAread() {
		if (chatArea == null) {
			chatArea = new JTextArea();
		}
		return chatArea;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(428,36,115,187));
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
		}
		return jList;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		ChatUserGUI application = new ChatUserGUI();
		application.setVisible(true);
	}

	public void run() {
		while(true) {
			Message message = null;
			while(message == null) {
				try {
					message = middleware.receive();
				} catch (NullPointerException e) {
					Debug.printStack(e);
				} catch (NotConnectedException e) {
					Debug.printStack(e);
				}
			}
			Object info = message.getContent();
			if(info instanceof ChatUser) {
				
			} else {
				
			}
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
