package bd;

import java.util.Date;

public class ParticipantesBO {
	
	private int id_participante;
	private int id_partida;
	private String id_usuario;
	private Date dt_entrada;
	private Date dt_saida;
	private int flag;// 1: Partida Criada, 2: Partida Finalizada (Normal), 3: Partida Finalizada (TimeOut)
	private int pontos;
	
	public void setId_participante(int id_participante) {
		this.id_participante = id_participante;
	}
	
	public int getId_participante() {
		return id_participante;
	}
	
	public void setId_partida(int id_partida) {
		this.id_partida = id_partida;
	}

	public int getId_partida() {
		return id_partida;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public String getId_usuario() {
		return id_usuario;
	}
	
	public void setDt_entrada(Date dt_entrada) {
		this.dt_entrada = dt_entrada;
	}
	
	public Date getDt_entrada() {
		return dt_entrada;
	}
	
	public void setDt_saida(Date dt_saida) {
		this.dt_saida = dt_saida;
	}
	
	public Date getDt_saida() {
		return dt_saida;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getFlag() {
		return flag;
	}
	
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int getPontos() {
		return pontos;
	}

}