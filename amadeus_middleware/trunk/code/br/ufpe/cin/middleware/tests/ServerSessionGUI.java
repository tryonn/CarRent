package br.ufpe.cin.middleware.tests;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.services.naming.Naming;
import br.ufpe.cin.middleware.services.naming.exceptions.NamingServiceException;
import br.ufpe.cin.middleware.services.security.DBWrapper;
import br.ufpe.cin.middleware.services.session.MicroMundo;
import br.ufpe.cin.middleware.services.session.Session;

public class ServerSessionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private Session [] sessions = null; 
	private Session sessionStub = null;
	private DBWrapper authStub = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JPasswordField jPasswordField = null;
	private JButton jButton1 = null;

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(15, 15, 300, 412));
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(0,2,10,10));
		}
		return jPanel;
	}

	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(466, 16, 102, 18));
		}
		return jTextField;
	}

	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(467, 39, 100, 18));
		}
		return jPasswordField;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(359, 71, 209, 42));
			jButton1.setText("Autenticar");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						if (authStub != null ) {
							String login = jTextField.getText();
							String senha = new String(jPasswordField.getPassword());
							boolean autorizado = authStub.validateLogin(login, senha);
							if (autorizado) {
								sessionStub = new Session (
										new MicroMundo("localhost","6666"),
										InetAddress.getLocalHost().getHostAddress(),
										InetAddress.getLocalHost().getHostAddress(),
										login,
										new Date(System.currentTimeMillis()));
								Thread t = new Thread() {
									public void run() {
										while (this.isAlive()) {
											if (sessionStub != null) {
												sessions = sessionStub.getUsers();
												jScrollPane.setViewportView(null);
												jPanel = null;
												getJPanel();
												if ((sessions != null) && (sessions.length != 0)) {
													for (Session session : sessions) {
														jPanel.add(new JButton(session.getUserHost()));
														jPanel.add(new JTextField(session.getUserLogin()));
													}
												}
												jScrollPane.setViewportView(getJPanel());
												repaint();
											}
											try {
												Thread.sleep(5000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
									}
								};
								t.setName("Thread atualizadora");
								t.start();
								jButton1.setText("Usuário autenticado");
								jButton1.setEnabled(false);
							} else {
								JOptionPane.showMessageDialog(ServerSessionGUI.this, "Usuário / senha inválidos");
							}
						}
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerSessionGUI thisClass = new ServerSessionGUI();
				thisClass.setVisible(true);
			}
		});
	}

	public ServerSessionGUI() {
		super();
		initialize();
		try {
			authStub = (DBWrapper) Naming.resolve("DBWrapper");
		} catch (NamingServiceException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}


	private void initialize() {
		this.setSize(640, 480);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.center();
		this.setTitle("Servidor de Sessão");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (sessionStub != null) {
					try {
//						sessionStub.remove(sessionStub);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void center() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getWidth()) / 2,
				(d.height - this.getHeight()) / 2);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(359, 39, 102, 17));
			jLabel1.setText("Senha: ");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(359, 17, 101, 16));
			jLabel.setText("Login:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

}
