var matIndex	= 0;

function chooseMaterial() {Materials.initUploading(chooseMaterialCB);}
function chooseMaterialCB(data) {
	matIndex = 0;
	var html = '<td  class="cont-tableinterna" colspan="3">' +
					'<table class="tableinterna">' + 
        				'<tr>' +  
          					'<td colspan="3" class="headTab">Upload de Materiais</td>' + 
        				'</tr>' +
        				'<span id="uploading_material"></span>' + 
        				'<tr>' + 
          					'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a>' + 
          					' / <a href="javascript:void(0)" onclick="persistMaterials();">Enviar</a></td>' + 
        				'</tr>' + 
      				'</table>' + 
       				'<span id="matFrames1"></span>' +
				'</td>';
	/**var html = 
		'<div id="uploading_material"></div>' +
		'<div class="moduleControl"><a href="javascript:void(0)" onclick="cancel();">' +
		'Cancelar</a> / <a href="javascript:void(0)" onclick="persistMaterials();" >' +
		'Enviar</a></div><span id="matFrames1"></div>';*/
	DWRUtil.setValue('dynamic' + currentEditingModule, html);
	addMaterialField();
}

function addMaterialField() {
	matIndex++;
	var div = 'matFrames' + matIndex;
	var html =
		'<iframe src="about:blank" name="matHidden'+matIndex+'" ' +
		'style="width:0px; height:0px; border: 0px"></iframe>' +
		'<span id="matFrames'+ (matIndex+1) +'"></span>';
	$(div).innerHTML = html;

	div = 'uploading_material';
	html =
		'<tr><td><span id="matItem'+matIndex+'">' +
		'<form action="' + getPath() + 'uploadMaterial.do" method="post" ' +
			  'enctype="multipart/form-data" target="matHidden'+matIndex+'">' +
		'<input type="hidden" name="index" value="'+matIndex+'" />' +
		'<input type="text" name="name" value="Nome do Material"/>' +
		'<input type="file" name="file" onchange="uploadMaterial(this.form);" />' +
		'</form></span></td></tr>';
	$(div).innerHTML += html;
}

function uploadMaterial(form) {
	var name = trim(form.name.value);
	if (name == '' || name == 'Nome do Material') {
		alert('Especifique o nome do material primeiro');
		DWRUtil.setValue('file', '');
		return;
	}
	var cb = {callback:function(data) {uploadMaterialCB(form);}};
	Materials.uploadingMaterial(matIndex, cb);
}
function uploadMaterialCB(form) {
	form.submit();
	var name = form.name.value;
	var file = form.file.value;
	file = file.substring(file.lastIndexOf('\\') + 1);
	var html =
		'&nbsp;' + name + '<br />&nbsp;' + file + '&nbsp;&nbsp;' +
		'[<a href="javascript:void(0)" onclick="removeMaterial(' + matIndex + ')">X</a>]';
	DWRUtil.setValue('matItem' + matIndex, html);
	addMaterialField();
}

function removeMaterial(theIndex) {
	Materials.removeMaterial(theIndex);
	DWRUtil.setValue('matItem' + theIndex, '');
}

function persistMaterials() {
	Materials.init(currentEditingModule);
	Materials.persistMaterials(orgMaterials);
	
}
function orgMaterials(data) {
	var options = new Array();
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = data[i][1];
		option +=
			' [<a href="javascript:void(0)" onclick="javascript:Materials.delete' + act + '(' + id + ',orgMaterials)">X</a>]';
		options[i] = option;
	}
	var div = 'materialsList' + currentEditingModule;
	DWRUtil.removeAllOptions(div);
	DWRUtil.addOptions(div, options);
	cancel();
}

function matReplyAttached(fileName, theIndex) {
	//alert(fileName + ' :: ' + theIndex);
}

function matReplyFailed(theIndex) {
	alert("Failed uploading file");
	DWRUtil.setValue('matItem' + theIndex, '');
}

function matReplyFailed(theIndex, fileName) {
	alert("Failed uploading file '" + fileName + "'");
	DWRUtil.setValue('matItem' + theIndex, '');
}

function getPath() {
	var path = document.location.pathname;
	return path.substring(0, path.lastIndexOf('/') + 1);
}