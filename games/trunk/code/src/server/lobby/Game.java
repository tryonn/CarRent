package server.lobby;

import java.util.Date;

public class Game {

	private int id_game;
	private String nameGame;
	private int maxUsers;
	private StringBuffer url;
	private int flag; //0 para jogo avulso e 1 para jogo padronizao
	private StringBuffer comentario;
	private Date atualizacao;
	
	public Game(int id_game, String name, int size, StringBuffer url, int flag, StringBuffer cm, Date atuali){
		setId_game(id_game);
		setNameGame(name);
		setMaxUsers(size);
		setUrl(url);
		setFlag(flag);
		setComentario(cm);
		setAtualizacao(atuali);
	}
	
	private void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}
	
	public int getMaxUsers() {
		return maxUsers;
	}

	private void setNameGame(String nameGame) {
		this.nameGame = nameGame;
	}

	public String getNameGame() {
		return nameGame;
	}

	public void setId_game(int id_game) {
		this.id_game = id_game;
	}

	public int getId_game() {
		return id_game;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setUrl(StringBuffer url) {
		this.url = url;
	}
	
	public StringBuffer getUrl() {
		return url;
	}

	public void setComentario(StringBuffer comentario) {
		this.comentario = comentario;
	}

	public StringBuffer getComentario() {
		return comentario;
	}

	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}

	public Date getAtualizacao() {
		return atualizacao;
	}	

}
