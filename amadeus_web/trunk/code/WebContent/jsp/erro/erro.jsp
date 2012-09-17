<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Opa, encontramos um Erro.</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/amadeus/jsp/erro/erro-base.css" rel="stylesheet" type="text/css" />

</head>

<body>
<img src="/amadeus/jsp/erro/amadeus-marca-vertical_m.jpg" width="183" height="240" border="0" id="logo" /> 
<div class="erro">
  <p>Erro #</p><h1><%out.println(request.getParameter("erro"));%></h1>
</div>
<div class="humanizando">
<p>Ol&aacute;!</p>
<p>Parece que alguma coisa n&atilde;o est&aacute; funcionando como n&oacute;s gostar&iacute;amos. Voc&ecirc; pode voltar <a href="/amadeus">p&aacute;gina inicial</a>, ou colaborar conosco <a href="#">reportando o erro</a>.</p>
</div>

<div class="rodape"> 
<p>O <a href="http://ead.cin.ufpe.br/wordpress " title="Blog do Projeto">Projeto 
  Amadeus</a> deseja entregar &agrave; população brasileira a plataforma de ensino mais 
  acess&iacute;vel e adequada aos nossos costumes e necessidades. Caso voc&ecirc; deseje contribuir, venha fazer parte de nossa <a href=" http://ead.cin.ufpe.br/phpBB2/" title="Forum do Projeto">comunidade</a>.</p>
</div>
</body>
</html>
