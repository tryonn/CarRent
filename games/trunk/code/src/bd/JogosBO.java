package bd;

import java.util.Date;

public class JogosBO {
	
	private int id_jogo;
	private String nm_jogo;
	private int id_curso;
	private int minimo;
	private int maximo;
	private StringBuffer url;
	private int flag; //0 para jogo avulso e 1 para jogo padronizao
	private StringBuffer comentario;
	private Date atualizacao;
	
	public void setNm_jogo(String nm_jogo) {
		this.nm_jogo = nm_jogo;
	}
	
	public String getNm_jogo() {
		return nm_jogo;
	}
	
	public void setId_jogo(int id_jogo) {
		this.id_jogo = id_jogo;
	}
	
	public int getId_jogo() {
		return id_jogo;
	}
	
	public void setMinimo(int minimo) {
		this.minimo = minimo;
	}
	
	public int getMinimo() {
		return minimo;
	}
	
	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}
	
	public int getMaximo() {
		return maximo;
	}
	
	public void setUrl(StringBuffer url) {
		this.url = url;
	}
	
	public StringBuffer getUrl() {
		return url;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public int getId_curso() {
		return id_curso;
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
