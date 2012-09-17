package workgroup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import workgroup.client.WorkGroupConnection;
import workgroup.client.WorkGroupListener;
import workgroup.group.GroupView;
import workgroup.message.ChatMessage;
import workgroup.message.Message;
import workgroup.message.ModelMessage;
import workgroup.ui.WGUserListPanel;

public class ClienteTeste extends JFrame implements WorkGroupListener {

	/**
	 *  Versão da classe ClienteTeste 1.6
	 */
	private static final long serialVersionUID = 16L;
	
	private static Logger log = Logger.getLogger(ClienteTeste.class);
	private static WorkGroupConnection connection;
	public static JTextPane text;
	public static JTextField chatField;
	public static JTextField modelField;
	public static ImageIcon iconControlOpen;  
	public static ImageIcon iconControlClose;
	public static JLabel lblControl;
	public static JButton btnRequestControl;
	public static JButton btnLeaveControl; 
	
	public ClienteTeste(String titulo) {
		this.addWindowListener(new CloseWindow(this));
		setTitle("[" + titulo + "]");
		iconControlOpen = new ImageIcon("images/lockopen.gif");  
		iconControlClose = new ImageIcon("images/lockshut.gif");  
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		try {
			String servidor = null;
			String grupo = null;
			String usuario = null;
			if (args.length == 0) {
				servidor = JOptionPane.showInputDialog("Endereco do Servidor", "localhost");
				if (servidor == null) {
					System.exit(1);
				}
				grupo = JOptionPane.showInputDialog("Nome do Grupo", "cin");
				if (grupo == null) {
					System.exit(1);
				}
				usuario = JOptionPane.showInputDialog("Login do Usuario", "");
				if (usuario == null) {
					System.exit(1);
				}
			} else if (args.length < 3) {
				System.out.println("Numero de parametros diferente do esperado! +" +
									"\nTente: +" +
									"\n>ClenteTeste +" +
									"\nou +" +
									"\n>ClienteTeste <servidor> <grupo> <usuario>");
			} else {
				servidor = args[0];
				grupo = args[1];
				usuario = args[2];
			}

			ClienteTeste cliente = new ClienteTeste(usuario + "@" + grupo);
			connection = new WorkGroupConnection(servidor, cliente);

			JPanel topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new BorderLayout());
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
			rightPanel.setBorder(BorderFactory.createBevelBorder(1));
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
			bottomPanel.setBorder(BorderFactory.createBevelBorder(1));
			WGUserListPanel userPanel = new WGUserListPanel(connection);
			text = new JTextPane();
			text.setEditable(false);
			chatField = new JTextField();
			JButton btnSend = new JButton("Enviar");
			SendChat send = new SendChat(cliente);
			chatField.addActionListener(send);
			btnSend.addActionListener(send);
			
			modelField = new JTextField();
			modelField.setEditable(false);
			lblControl = new JLabel(iconControlClose);
			
			RequestFloorControl requestFloorControl = new RequestFloorControl(cliente);
			btnRequestControl = new JButton("Solicita Controle");
			btnRequestControl.addActionListener(requestFloorControl);
			
			LeaveFloorControl leaveFloorControl = new LeaveFloorControl(cliente);
			btnLeaveControl = new JButton("Abandona Controle");
			btnLeaveControl.addActionListener(leaveFloorControl);
			btnLeaveControl.setEnabled(false);
			
			JPanel controlPanel = new JPanel();
			controlPanel.add(btnRequestControl);
			controlPanel.add(btnLeaveControl);
			ModelListener listener = new ModelListener(cliente);
			modelField.addKeyListener(listener);

			Container iFrame = cliente.getContentPane();
			iFrame.add(topPanel, BorderLayout.NORTH);
			iFrame.add(leftPanel, BorderLayout.CENTER);
			iFrame.add(rightPanel, BorderLayout.EAST);
			iFrame.add(bottomPanel, BorderLayout.SOUTH);
			rightPanel.add(userPanel, BorderLayout.CENTER);
			leftPanel.add(text, BorderLayout.CENTER);
			topPanel.add(lblControl, BorderLayout.WEST);
			topPanel.add(controlPanel, BorderLayout.SOUTH);
			topPanel.add(modelField, BorderLayout.CENTER);
			bottomPanel.add(chatField, BorderLayout.CENTER);
			bottomPanel.add(btnSend, BorderLayout.EAST);
			cliente.setSize(300,200);
			cliente.validate();
			cliente.setVisible(true);
			
			connection.open("ClienteTeste", grupo, usuario, "");
			//log.debug("Vou enviar mensagem");
			//connection.sendChatMessage("[" + args[2] + "] diz oi");
			//log.debug("enviei!!!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		connection.close();
		System.exit(0);
	}

	public void onMessage(Message message) {
		if (message instanceof ChatMessage) {
			text.setText(text.getText() + message.getObject() + "\n");
			System.out.println(message.getObject());
		} else if (message instanceof ModelMessage) {
			System.out.println("Mensagem de Modelo: " + message.getObject());
			modelField.setText(message.getObject().toString());
		}
	}

	public void onClose() {
		System.err.println("Conexão fechada!");	
		text.setEnabled(false);
		chatField.setEnabled(false);
		setTitle("xx Desconectado xx");
	}

	public void onChangeGroupView(GroupView view) {
		log.debug("Novo GroupView para o grupo [" + view.getGroupName() + "] em " + view.getTimestamp());
		log.debug("GroupView = " + view.getuserList());
	}
	
	public void onTakeControl() {
		modelField.setEditable(true);
		lblControl.setIcon(iconControlOpen);
		btnLeaveControl.setEnabled(true);
		btnRequestControl.setEnabled(false);
	}

	public void sendChat() throws IOException {
		connection.sendChatMessage(chatField.getText());
		chatField.setText("");
	}
	
	public void sendModel() throws IOException {
		connection.sendModelMessage(modelField.getText());
	}

	public void requestFloorControl() throws IOException {
		connection.requestFloorControl();
	}

	public void leaveFloorControl() throws IOException {
		connection.leaveFloorControl();
		modelField.setEditable(false);
		lblControl.setIcon(iconControlClose);
		btnLeaveControl.setEnabled(false);
		btnRequestControl.setEnabled(true);
	}

}

class SendChat implements ActionListener {
	private ClienteTeste cliente;

	public SendChat(ClienteTeste frame) {
		this.cliente = frame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			cliente.sendChat();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class RequestFloorControl implements ActionListener {
	private ClienteTeste cliente;

	public RequestFloorControl(ClienteTeste frame) {
		this.cliente = frame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			cliente.requestFloorControl();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class LeaveFloorControl implements ActionListener {
	private ClienteTeste cliente;

	public LeaveFloorControl(ClienteTeste frame) {
		this.cliente = frame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			cliente.leaveFloorControl();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ModelListener extends KeyAdapter {

	private ClienteTeste cliente;
	
	public ModelListener(ClienteTeste cliente) {
		this.cliente = cliente;
	}

	public void keyTyped(KeyEvent key) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						cliente.sendModel();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
	}
	
}

class CloseWindow extends WindowAdapter {
	private ClienteTeste cliente;
	
	public CloseWindow(ClienteTeste frame) {
		this.cliente = frame;
	}
	
	public void windowClosing(WindowEvent e) {
		cliente.close();
	}

}