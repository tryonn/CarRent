package bd;

import java.util.Date;
import java.util.Vector;

public class PartidasBO {
	
	private int id_partida;
	private int flag;
	private Date dt_criacao;
	private Date dt_fim;
	private JogosBO jogo;
	private Vector participantes;
	
	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}
	
	public int getId_partida() {
		return id_partida;
	}
		
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
	
	public Date getDt_criacao() {
		return dt_criacao;
	}
	
	public void setDt_fim(Date dt_fim) {
		this.dt_fim = dt_fim;
	}
	
	public Date getDt_fim() {
		return dt_fim;
	}

	
	public void setParticipantes(Vector participantes) {
		this.participantes = participantes;
	}
	

	public Vector getParticipantes() {
		return participantes;
	}

	public void setJogo(JogosBO jogo) {
		this.jogo = jogo;
	}

	public JogosBO getJogo() {
		return jogo;
	}
	
}
