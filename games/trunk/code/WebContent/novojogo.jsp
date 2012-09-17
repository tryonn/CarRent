<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Novo Jogo</title>
</head>
<body>
	<form action="newgame.do" method="POST">
	Nome do Jogo: <br>
	<input type="text" name="nm_jogo">
	<br>URL: <br>
	<input type="text" size="60" name="caminho">
	<br>Id do Curso <br>
	<input type="text" name="curso">
	<br>Comentário: <br>
	<textarea rows="8" cols="50" name="comentario"></textarea>
	<br><input type="submit" value="Cadastrar">
	
	</form>


</body>
</html>