<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Login</title>

<script language="JavaScript" type="text/javascript">

function destino() {
	if (document.form.disciplina[0].checked){
		document.form.action = "game/Prototipo.html";
		document.form.submit();
	}else{
		if (document.form.disciplina[1].checked){
			document.form.action = "game/PrototipoCasa.html";
			document.form.submit();
		}else{
			if (document.form.disciplina[2].cheched){
				document.form.action = 
"//ead.cin.ufpe.br:8180/AmadeusGame/game/lobbyEad.html";
				document.submit();
			}
                }
	}
}



</script>
</head>
<body>
<form id="form" name="form" method="get" action="" >
  <p>Login:
    <input name="login" type="text" id="login" />
</p>
  <p>
    <label>
    Server:  
    <input name="disciplina" type="radio" value="1" checked="checked"  />
Jupi</label>
    <input name="disciplina" type="radio" value="2" />
    <label>
 Teste - LocalHost</label>
  </p>
  <p><br />
    <input type="submit" name="Submit" value="Logon" onclick="destino()"/>
    <input type="reset" name="Reset" value="reset" />
  </p>
</form>
</body>
</html>
