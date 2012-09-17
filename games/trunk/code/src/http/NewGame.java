package http;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

 public class NewGame extends javax.servlet.http.HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String jogo = request.getParameter("nm_jogo");
		StringBuffer url = new StringBuffer();
		url.append( request.getParameter("caminho") );
		int id_curso = Integer.parseInt( request.getParameter("curso") );
		StringBuffer comentario = new StringBuffer();
		comentario.append( request.getParameter("comentario") );
		
		AddGame newGame = new AddGame();
		StringBuffer res = newGame.newGame(jogo, url, id_curso, comentario);
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println(res);
		
	}  	
	  	    
}