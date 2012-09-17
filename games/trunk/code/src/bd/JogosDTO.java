package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class JogosDTO {

	public Vector getAll(int id_curso){
		
		Conexao con = new Conexao();
		Connection c = con.getConnection();
		
		Vector res =  new Vector();
        ResultSet rs;
        PreparedStatement ps;
        try {
         	 ps = c.prepareStatement("Select * from jogos j where j.id_curso = ? order by j.nm_jogo desc");
         	 ps.setInt(1,id_curso);
         	 rs = ps.executeQuery();
             
             while (rs.next ()){
                 JogosBO j = new JogosBO();
                 j.setId_jogo( rs.getInt("id_jogo") );
                 j.setId_curso( rs.getInt("id_curso") );
                 j.setNm_jogo( rs.getString("nm_jogo") );
                 j.setMinimo( rs.getInt("minimo") );
                 j.setMaximo( rs.getInt("maximo") );
                 StringBuffer url = new StringBuffer();
                 url.append( rs.getString("url") );
                 j.setUrl( url );
                 j.setFlag( rs.getInt("flag") );
                 StringBuffer cm = new StringBuffer();
                 cm.append( rs.getString("comentario") );
                 j.setComentario( cm );
                 Date data = new Date( rs.getTimestamp("atualizacao").getTime() );
                 j.setAtualizacao(data);
                 //j.setAtualizacao( rs.getTimestamp("atualizacao") );
                 res.addElement(j);
             }
             
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
         
         return res;		
		
	}
	
	public int insertNewGame(String nm_jogo,int max, int min ,StringBuffer url, int padrao,int curso, StringBuffer comentario){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        PreparedStatement ps;
        int retorno = 0;
        try {
         	 ps = c.prepareStatement("insert into jogos (nm_jogo,maximo,minimo,url,flag,id_curso,comentario,atualizacao) values (?,?,?,?,?,?,?,now())");
         	 ps.setString(1,nm_jogo );
         	 ps.setInt(2,max );
         	 ps.setInt(3,min );
         	 ps.setString(4,url.toString() );
         	 ps.setInt(5, padrao);
         	 ps.setInt(6,curso );
         	 ps.setString(7,comentario.toString() );
         	 retorno = ps.executeUpdate();
         } catch (SQLException ex) {
         	System.out.println("ERRO na inclusão de uma nova partida");
             ex.printStackTrace();
         }
		
		return retorno;
	}
	
	public int updateGame(int id_jogo, String nm_jogo,int max, int min ,StringBuffer url, int padrao,int curso, StringBuffer comentario){
		Conexao con = new Conexao();
		Connection c = con.getConnection();
        int retorno = 0;
        PreparedStatement ps;
        try {
         	 ps = c.prepareStatement("update jogos set nm_jogo = ?, maximo = ?,minimo = ?,url = ?,flag = ?,id_curso = ?,comentario = ?,atualizacao = now() where id_jogo = ?");
         	 ps.setString(1,nm_jogo );
         	 ps.setInt(2,max );
         	 ps.setInt(3,min );
         	 ps.setString(4,url.toString() );
         	 ps.setInt(5, padrao);
         	 ps.setInt(6,curso );
         	 ps.setString(7,comentario.toString() );
         	 ps.setInt(8,id_jogo );
         	 retorno = ps.executeUpdate();
         } catch (SQLException ex) {
         	System.out.println("ERRO");
             ex.printStackTrace();
         }
		
		return retorno;
	}

}
