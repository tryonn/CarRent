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
 * Classe gr�fica relativa � lista de usu�rios logados
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
		pnlTitle.add( new JLabel("Usu�rios") );
		pnlTitle.setBorder(BorderFactory.createEtchedBorder());
		lstUserList.setCellRenderer(new GroupUserListCellRenderer());
		this.setLayout(new BorderLayout());
		this.add( pnlTitle, BorderLayout.NORTH );
		this.add(lstUserList, BorderLayout.CENTER);
		setPreferredSize(new Dimension(150,200));
	}

	/**
	 * M�todo que atualiza a lista de usu�rios
	 * 
	 * @param view GroupView: Objeto que cont�m o nome do grupo, a lista de usu�rios
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
	 * Construtor da classe. Inicializa o panel da lista de usu�rios.
	 * 
	 * @param panel O panel onde ser�o mostrados os usu�rios
	 */
	public GroupViewAdapter(WGUserListPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * M�todo que limpa a lista de usu�rios quando o panel � fechado
	 */
	public void onClose() {
		panel.lstUserList.setListData(new Vector());
	}
	
	/**
	 * M�todo que atualiza a vis�o quando ocorre alguma mudan�a no GroupView (na lista
	 * de usu�rios, etc)
	 */
	public void onChangeGroupView(GroupView view) {
		panel.update(view);
	}
}

/**
 * Classe respons�vel pelo modelo da lista de usu�rios
 * 
 * @author amadeus
 *
 */
class GroupUserListModel implements ListModel {

	List<User> userList; 
	
	/**
	 * Construtor da classe. Inicializa a lista de usu�rios atrav�s da lista 
	 * fornecida.
	 * 
	 * @param userList A lista de usu�rios
	 */
	public GroupUserListModel(List<User> userList) {
		this.userList = userList;		
	}
	
	/**
	 * M�todo que retorna quantos usu�rios est�o na lista
	 */
	public int getSize() {
		return userList.size();
	}

	/**
	 * M�todo que retorna o identificador (Id) do usu�rio dada a sua posi��o 
	 * na lista mais o tipo do mesmo (se n�o tem o controle, se o tem ou se o quer)
	 * 
	 * @param i O �ndice do usu�rio na lista
	 * @return Object O Id do usu�rio mais o seu tipo (ver descri��o acima)
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
	 * M�todo que adiciona um listener na lista
	 * 
	 * @param arg0 O listener a ser adicionado
	 */
	public void addListDataListener(ListDataListener arg0) {
	}

	/**
	 * M�todo que remove um listener da lista
	 * 
	 * @param arg0 O listener a ser removido
	 */
	public void removeListDataListener(ListDataListener arg0) {
	}

}

/**
 * Classe respons�vel pela renderiza��o das c�lulas da lista de usu�rios
 * 
 * @author amadeus
 *
 */
class GroupUserListCellRenderer implements ListCellRenderer {
	
	private Icon[] icons;
	
	/**
	 * Construtor da classe. Inicializa os �cones localizados ao lado
	 * do nome dos usu�rios
	 */
	public GroupUserListCellRenderer() {
		icons = new ImageIcon[3];
		icons[0] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/userred.png"));
		icons[1] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/usergreen.png"));
		icons[2] = new ImageIcon(GroupUserListCellRenderer.class.getResource("images/userblue.png"));
	}
	
	/**
	 * M�todo que decide qual ser� a cor do boneco que fica ao lado do nome do usu�rio
	 * 
	 * @param list A lista de usu�rios (n�o � importante)
	 * @param value Usu�rio
	 * @param modelIndex  �ndice do modelo (n�o � importante)
	 * @param isSelected Se o usu�rio est� selecionado (n�o � importante)
	 * @param cellHasFocus Se a c�lula do usu�rio tem o foco (n�o � importante)
	 */
	public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus) {
		String user = (String) value;
		JLabel label = new JLabel(user.substring(2));
		label.setIcon(icons[Integer.valueOf(user.substring(0, 1))]);
		return label;
	}
	
}