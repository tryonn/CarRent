package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class PartidasDTO {

	public int novaPartida(PartidasBO p,ParticipantesBO pa){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("insert into partidas (id_partida, id_jogo, dt_criacao) values (?,?,now())");
         	 ps.setInt(1,p.getId_partida() );
         	 ps.setInt(2,p.getJogo().getId_jogo() );
         	 retorno = ps.executeUpdate();
         	 
         	 novoParticipante(pa);
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;
	}
	
	public int finalizaPartida(PartidasBO p){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("update partidas set dt_fim = now() where id_partida = ?");
         	 ps.setInt(1, p.getId_partida());
         	 retorno = ps.executeUpdate();
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;
	}
		
	public int novoParticipante(ParticipantesBO p){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("insert into participantes (id_partida, id_usuario, dt_entrada) values (?,?,now())");
         	 ps.setInt(1,p.getId_partida() );
         	 ps.setString(2,p.getId_usuario() );
         	 retorno = ps.executeUpdate();
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;		
	}

	public int saiuParticipante(ParticipantesBO p){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("update participantes set dt_saida = now(), flag = ?, pontos = ? where id_partida = ? and id_usuario = ? and dt_saida is null");
         	 ps.setInt(1,p.getFlag() );
         	 ps.setInt(2,p.getPontos() );
         	 ps.setInt(3,p.getId_partida() );
         	 ps.setString(4,p.getId_usuario() );
         	 retorno = ps.executeUpdate();
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;		
	}

	public Vector getNow(int id_curso){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
		
		Vector res =  new Vector();
        ResultSet rs;
        PreparedStatement ps;
        try {
         	 ps = c.prepareStatement("select id_partida, nm_jogo,dt_criacao, comentario from partidas p,jogos j where p.id_jogo = j.id_jogo and j.id_curso = ? and p.dt_fim is null");
         	 ps.setInt(1,id_curso);
         	 rs = ps.executeQuery();
             
             while (rs.next ()){
                 PartidasBO p = new PartidasBO();
                 JogosBO j = new JogosBO();
                 p.setId_partida( rs.getInt("id_partida") );
                 Date data = new Date( rs.getTimestamp("dt_criacao").getTime() );
                 p.setDt_criacao( data );
                 j.setNm_jogo(rs.getString("nm_jogo"));
                 StringBuffer comentario = new StringBuffer();
                 comentario.append( rs.getString("comentario"));
                 j.setComentario(comentario);
                 p.setJogo(j);

                 res.addElement(p);
                 
                 //TODO PEGAR os participantes
             }
             
         } catch (SQLException ex) {
         	System.out.println("ERRO em pegar as partidas atuais");
             ex.printStackTrace();
         }
         
         return res;
	}

	public int getUltimoID(){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
		
		int res =  -1;
        ResultSet rs;
        PreparedStatement ps;
        try {
         	 ps = c.prepareStatement("select id_partida from partidas order by id_partida desc");
         	 rs = ps.executeQuery();
             
             if (rs.next ()){
                 res = rs.getInt("id_partida");
             }else{
            	 res = 0;
             }
             
         } catch (SQLException ex) {
         	System.out.println("ERRO em pegar o ID partida");
             ex.printStackTrace();
         }
         
         return res;		
	}

	public int novaPartidaAvulsa(int id_jogo, String id_usario){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("insert into partidasAvulsas (id_jogo, id_usuario, dt_criacao) values (?,?,now())");
         	 ps.setInt(1,id_jogo );
         	 ps.setString(2, id_usario);
         	 retorno = ps.executeUpdate();
         	 
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;
	}

}
