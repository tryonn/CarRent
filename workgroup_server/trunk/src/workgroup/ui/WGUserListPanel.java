package workgroup.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import workgroup.client.WorkGroupAdapter;
import workgroup.client.WorkGroupConnection;
import workgroup.group.GroupView;
import workgroup.group.User;

/**
 * Classe gráfica relativa à lista de usuários logados
 * 
 * @author amadeus
 */
public class WGUserListPanel extends JPanel {
	
	private static final long serialVersionUID = 12L;
	
	protected JList lstUserList = new JList();
	protected JPanel pnlTitle = new JPanel();
	
	/**
	 * Construtor do classe
	 * @param connection WorkGroupConnection
	 */
	public WGUserListPanel(WorkGroupConnection connection) {
		connection.addWorkGroupListener(new GroupViewAdapter(this));
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		pnlTitle.add( new JLabel("Usuários") );
		pnlTitle.setBorder(BorderFactory.createEtchedBorder());
		lstUserList.setCellRenderer(new GroupUserListCellRenderer());
		this.setLayout(new BorderLayout());
		this.add( pnlTitle, BorderLayout.NORTH );
		this.add(lstUserList, BorderLayout.CENTER);
		setPreferredSize(new Dimension(150,200));
	}

	/**
	 * Método que atualiza a lista de usuários
	 * 
	 * @param view GroupView: Objeto que contém o nome do grupo, a lista de usuários
	 * e uma data 
	 */
	public void update(GroupView view) {
		List<User> userList = view.getuserList();
		lstUserList.setModel(new GroupUserListModel(userList));
		lstUserList.repaint();
	}	
	
}

/**
 * Classe adaptadora do GroupView
 * 
 * @author amadeus
 *
 */
class GroupViewAdapter extends WorkGroupAdapter {
	WGUserListPanel panel;
	
	/**
	 * Construtor da classe. Inicializa o panel da lista de usuários.
	 * 
	 * @param panel O panel onde serão mostrados os usuários
	 */
	public GroupViewAdapter(WGUserListPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * Método que limpa a lista de usuários quando o panel é fechado
	 */
	public void onClose() {
		panel.lstUserList.setListData(new Vector());
	}
	
	/**
	 * Método que atualiza a visão quando ocorre alguma mudança no GroupView (na lista
	 * de usuários, etc)
	 */
	public void onChangeGroupView(GroupView view) {
		panel.update(view);
	}
}

/**
 * Classe responsável pelo modelo da lista de usuários
 * 
 * @author amadeus
 *
 */
class GroupUserListModel implements ListModel {

	List<User> userList; 
	
	/**
	 * Construtor da classe. Inicializa a lista de usuários através da lista 
	 * fornecida.
	 * 
	 * @param userList A lista de usuários
	 */
	public GroupUserListModel(List<User> userList) {
		this.userList = userList;		
	}
	
	/**
	 * Método que retorna quantos usuários estão na lista
	 */
	public int getSize() {
		return userList.size();
	}

	/**
	 * Método que retorna o identificador (Id) do usuário dada a sua posição 
	 * na lista mais o tipo do mesmo (se não tem o controle, se o tem ou se o quer)
	 * 
	 * @param i O índice do usuário na lista
	 * @return Object O Id do usuário mais o seu tipo (ver descrição acima)
	 */
	public Object getElementAt(int i) {
		String icon = "0";
		User user = userList.get(i);
		if (user.isControlOwner()) {
			icon = "1";
		} else if (user.isControlRequested()) { 
			icon = "2";
		}
		return icon + "-" + user.getUserID();
	}

	/**
	 * Método que adiciona um listener na lista
	 * 
	 * @param arg0 O listener a ser adicionado
	 */
	public void addListDataListener(ListDataListener arg0) {
	}

	/**
	 * Método que remove um listener da lista
	 * 
	 * @param arg0 O listener a ser removido
	 */
	public void removeListDataListener(ListDataListener arg0) {
	}

}

/**
 * Classe responsável pela renderização das células da lista de usuários
 * 
 * @author amadeus
 *
 */
class GroupUserListCellRenderer implements ListCellRenderer {
	
	private Icon[] icons;
	
	/**
	 * Construtor da classe. Inicializa os ícones localizados ao lado
	 * do nome dos usuários
	 */
	public GroupUserListCellRenderer() {
		icons = new ImageIcon[3];
		icons[0] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/userred.png"));
		icons[1] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/usergreen.png"));
		icons[2] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/userblue.png"));
	}
	
	/**
	 * Método que decide qual será a cor do boneco que fica ao lado do nome do usuário
	 * 
	 * @param list A lista de usuários (não é importante)
	 * @param value Usuário
	 * @param modelIndex  Índice do modelo (não é importante)
	 * @param isSelected Se o usuário está selecionado (não é importante)
	 * @param cellHasFocus Se a célula do usuário tem o foco (não é importante)
	 */
	public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus) {
		String user = (String) value;
		JLabel label = new JLabel(user.substring(2));
		label.setIcon(icons[Integer.valueOf(user.substring(0, 1))]);
		return label;
	}
	
}