function putFlash(nome){
   var object = "<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='682' height='489' id='"+nome+"' align='middle'>";
   object += "<param name='allowScriptAccess' value='sameDomain' />";
   object += "<param name='movie' value='"+nome+".swf' />";
   object += "<param name='quality' value='high' />";
   object += "<param name='bgcolor' value='#ffffff' />";
   object += "<embed src='"+nome+".swf' quality='high' bgcolor='#ffffff' width='682' height='489' name='"+nome+"' align='middle' allowScriptAccess='sameDomain' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />";
   object += "</object>";
   document.write(object);
}


function getVariaveisLobby(nome){
    var url = location.href;
    var divisao = url.split("?");
    var variaveis = divisao[1].split ("&");
    for (i = 0; i < variaveis.length; i++) {
        tmp = variaveis[i].split("=");
	eval ('var '+tmp[0]+'="'+tmp[1]+'"');
    }
    var objeto = document.getElementById(nome);
    objeto.SetVariable("login", login);
    objeto.SetVariable("disciplina", disciplina);
}
function getVariaveisJogo(nome){
    var url = location.href;
    var divisao = url.split("?");
    var variaveis = divisao[1].split ("&");
    for (i = 0; i < variaveis.length; i++) {
        tmp = variaveis[i].split("=");
	eval ('var '+tmp[0]+'="'+tmp[1]+'"');
    }
    var objeto = document.getElementById(nome);
    objeto.SetVariable("login", login);
    objeto.SetVariable("idRoom", idRoom);
}