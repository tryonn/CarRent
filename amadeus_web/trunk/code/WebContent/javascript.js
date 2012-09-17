var isEditing = false;
var isDoingSomething = false; //define se alguma atividade est? sendo feita naquele momento
var isPowerful = false; //define se um usu?rio ? capaz de editar um m?dulo
var isFirstTime = false;
var isReOrderingModules = true; //define se o processo ? de reorganiza??o de m?dulos
var isDeleting = false; //define se est? no processo de deletar
var isDeletingWithoutSaving = false;
var noAlternatives = 2; //define a quantidade de alternativas, utilizado por forum, multiplaescolha, truorfalse
var modulesCounter = 1; //define a quantidade de m?dulos do curso (?)
var currentEditingModule = 1; //define o m?dulo que est? em edi??o
var newOrder = 0; //define a nova ordem do m?dulo que se est? mudando a posi??o
var usrRole = -1; //define o papel do usu?rio (professor, aluno, administrador)(?)
var usrProfile = -1;
var forumIdentification = 0;
var moduleIdentification = 0;
var visibleNumber = 1;
var isCreatingModule = false;



/**
PARTE RELATIVA ? CRIA??O DE M?DULOS
*/

/*function initModuleCreation(){
	alert('initModuleCreation');
	currentEditingModule = 1;
	createModule();
}*/

function initModuleCreation() {
	ContManag.clear({callback: function() {}, async:false});
	ContManag.init(0);
	isPowerful = true;
	//modulesCounter = 0;
	saveJustCreatedModule();
}

function cancel() {
	var html =
		'<a href="javascript:void(0)" onclick="saveModuleInEdit();">Salvar</a>' +
    				  ' / <a href="javascript:void(0)" onclick="cancelModule();">Cancelar</a>' +
        			  ' / <a href="javascript:void(0)" onclick="deleteModule();">Excluir</a>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function cancelModule() {
	Module.cancelModule(currentEditingModule, cancelModuleCB);
}
function cancelModuleCB(data) {
	if (data != null) {
		fromEditingToViewMode(data.name,
				data.desc, data.visible, data.order);
	} else {
		deleteOrCancelWithoutSaving();
	}
}

function deleteOrCancelWithoutSaving() {
	var form = '';
	var iIndex = '';
	var fIndex = '';
	form = DWRUtil.getValue('form');
	iIndex = form.indexOf('<table id="module' + modulesCounter);
	fIndex = form.indexOf('<table id="module', iIndex + 15);
	newForm = form.substring(0, iIndex);
	if (fIndex != -1)
		newForm += form.substring(fIndex);
	DWRUtil.setValue("form", newForm);
	
	modulesCounter--;
	addCreateNewModuleOption(modulesCounter);
	isEditing = false;
	isDoingSomething = false;
}

function deleteModule() {
	isFirstTime = true;
	isDeletingWithoutSaving = true;
	Module.reorderModules(modulesCounter, currentEditingModule, fillModsCB);	
}

function deleteM() {
	Module.deleteModule(modulesCounter);
	var form = '';
	var iIndex = '';
	var fIndex = '';
	
	for (var i = 0; i <= 1; i++) {
		form = DWRUtil.getValue('form');
		iIndex = form.indexOf('<table id="module' + (modulesCounter + i));
		fIndex = form.indexOf('<table id="module', iIndex + 10);
		
		newForm = form.substring(0, iIndex);
		if (fIndex != -1)
			newForm += form.substring(fIndex);
		DWRUtil.setValue("form", newForm);
	}
	
	modulesCounter--;
	addCreateNewModuleOption(modulesCounter);
	isEditing = false;
}

function fromEditingToViewMode(name, desc, visible, number) {
	//alert('fromEditingToViewMode');
	var moduleVisible = "moduleVisible" + currentEditingModule;
	var moduleNumber = "moduleNumber" + currentEditingModule;
	var moduleId = "module" + currentEditingModule;
	var materialsList = "materialsList" + currentEditingModule;
	var activitiesList = "activitiesList" + currentEditingModule;
	var dynamic = "dynamic" + currentEditingModule;
	
	var module = '<tr>' +
			     	'<td width="50%" class="headTab">' + name + '</td>' +
				 	'<td align="center" class="headTab">';
	if (visible == true) {
		module += 'Vis&iacute;vel</td>';
	} else {
		module += '<span class="alert">Invis&iacute;vel</span></td>';
	}			  
		module += 	'<td align="right" class="headTab">' + number + '</td>' +
				  '</tr>' + 
 				  '<tr>' + 
  				  	'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo: </td>' +
 				  '</tr>' +
				  '<tr>' +
    			  	'<td colspan="3" class="modDescrip">' + desc + '</td>' +
  				  '</tr>' +
				  '<tr>' +
     			  	'<td width="50%" class="headTab2">Materiais</td>' +
				  	'<td colspan="3" class="headTab2">Atividades</td>' +
  				  '</tr>' +
  				  '<tr valign="top">' + 
			    	'<td><ul id="' + materialsList + '">';
		module +=   '</ul></td>' +
			    	'<td colspan="3"> <ul id="' + activitiesList + '">';
		module += 	'</ul></td>' +
			  	  '</tr>';
		
	name = "'" + name + "'";
	desc = "'" + desc + "'";
	visible = "'" + visible + "'";
	number = "'" + number + "'";
		
		module += '<tr align="right" id=' + dynamic + '>' + 
			    	'<td colspan="3"><a href="javascript:void(0)" onclick="retrieveModuleInfo('+ currentEditingModule+')"' +
  	    				'>Editar</a></td>' +
			  	  '</tr>';
	DWRUtil.setValue(moduleId, module);
  	addCreateNewModuleOption(currentEditingModule);
	isEditing = false;
	isDoingSomething = false;
	Activities.retrieveInfo(currentEditingModule, fillActivitiesDataCB);
	Materials.retrieveInfo(currentEditingModule, fillMaterialsDataCB);
}

function saveModule() {
	alert('saveModule');
	var moduleName = "moduleName" + currentEditingModule;
	var moduleDescription = "moduleDescription" + currentEditingModule;
	var moduleVisible = "moduleVisible" + currentEditingModule;
	var moduleNumber = "moduleNumber" + currentEditingModule;
	
	var name    = trim(DWRUtil.getValue(moduleName));
	var desc    = trim(DWRUtil.getValue(moduleDescription));
	var visible = DWRUtil.getValue(moduleVisible);
	var number  = DWRUtil.getValue(moduleNumber);
	
	for (var i = 0; i < desc.length; i++) {
		if (desc[i].charCodeAt(0) == 10) {
			var aux = desc.substring(0, i) + ' ' + desc.substring(i+1, desc.length);
			desc = aux;
		}
	}
	if (name == '') {
		alert("O nome eh obrigatorio!");
	} else if (desc == '') {
		alert("A descricao eh obrigatoria!");
	} else {
		Module.saveModule(name, desc, visible, number, currentEditingModule); 
		//O bug eh aqui!!!!		
		fromEditingToViewMode(name, desc, visible, number);
	}
}

function trim(str) {
	str = str.replace( /^\s+/g, '' );
	return str.replace( /\s+$/g, '' );
}

function validateInt(value) {
	for (var i = 0; i < value.length; i++) {
		var charrr = value.charAt(i);
		if (isNaN(parseInt(charrr)) == true)
			return false;
	}
	return true;
}

function verifyDate(day, month, year) {
	var result = true;
	if (!validateInt(day) || !validateInt(month) ||
								!validateInt(year)) {
		result = false;
	}
	return result;
}

function editingState(name, desc, visible, number, module) {
	//alert('editingState');
	if (isEditing == true) {
		alert('Existe um modulo em modo de edicao');
		return;
	}
	isEditing = true;
	currentEditingModule = module;
	
	var cb = {
		callback:function(data) {
			editingStateCB(data, name, desc, visible, number);
		}
	};
	ContManag.init(module, cb);
//	Activitiesaa.init(module, cb);
}


function editingStateCB(data, name, desc, visible, number) {
	//alert('editingStateCB');
	var moduleName = "moduleName" + currentEditingModule;
	var moduleVisible = "moduleVisible" + currentEditingModule;
	var moduleDescription = "moduleDescription" + currentEditingModule;
	var moduleNumber = "moduleNumber" + currentEditingModule;
	var dynamic = "dynamic" + currentEditingModule;
	var materialsList = "materialsList" + currentEditingModule;
	var activitiesList = "activitiesList" + currentEditingModule;
	var moduleId = "module" + currentEditingModule;
	var listHolder = "listHolder" + currentEditingModule;	
	var module = '<tr>' +
    				'<td width="50%" class="headTab"><input type="text" name="' + moduleName + '" value="' + name + '"/></td>' +
					'<td align="center" class="headTab">Vis&iacute;vel <input name="' + moduleVisible + '" type="checkbox" ';
	if (visible == 'true' || visible == true) {
		module += 'checked="checked" ';
	} 			
		module += '/></td>' +
				  '<td align="right" class="headTab"><select name="' + moduleNumber + '" onchange="reorderModules(' + currentEditingModule + ');">'; 
		for (var i = 1; i <= modulesCounter; i++) {
			module += '<option ';
			if (currentEditingModule == i) {
				module += 'selected';
			}
			module += ' value="' + i + '"/>' + i;
		}
		module += '</select></td>' +
  				  '</tr>' +
			 	  '<tr>' +
    				'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo:</td>' +
  				  '</tr>' +
				  '<tr>' +
					'<td colspan="3" class="modDescrip"><textarea name="' + moduleDescription + '" class="modDescriptTextarea">' + desc + '</textarea></td>' +
				  '</tr>' +
				  '<tr>' +
					'<td class="headTab2">Materiais [<a href="javascript:void(0)" onclick="chooseMaterial();">+</a>]</td>' + 
					'<td colspan="3" class="headTab2">Atividades [<a href="javascript:void(0)" onclick="chooseActivity();">+</a>]</td>' +
				  '</tr>' +
			 	  '<tr valign="top">' +
    				'<td>' +
						'<ul id="' + materialsList + '">';
		module += addMaterialFeatures(data.materials);			
		module += 		'</ul></td>' +
    				'<td colspan="3">' +
						'<ul id="' + activitiesList + '">';
		module += addActivityFeatures(data.activities);
		module +=		'</ul></td>' +
				  '</tr>' +
			 	  '<tr align="right" id="' + dynamic + '">' +
    				 '<td colspan="3"><a href="javascript:void(0)" onclick="saveModule();">Salvar</a>' +
    								 ' / <a href="javascript:void(0)" onclick="cancelModule();">Cancelar</a>' +
        					         ' / <a href="javascript:void(0)" onclick="deleteModule();">Excluir</a></td>' +
  				  '</tr>';
  	DWRUtil.setValue(moduleId, module);
  	//Materialsaa.init(currentEditingModule, orgMaterials);
}

function addActivityFeatures(data) {
	var options = "";

	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = '<li>' + data[i][1];
		//if (i != (data.length - 1))
		if(act!='CourseEvaluation')
			option += ' [<a href="javascript:Activities.retrieve' + act + '(' + id + ',retrieve' + act + 'CB)">E</a>] ';
		option += ' [<a href="javascript:Activities.delete' + act + '(' + id + ',orgActivities)">X</a>]</li>';
		options += option;
	}
	return options;
}

function addMaterialFeatures(data) {
	var options = "";
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = '<li>' + data[i][1];
		option +=
			'[<a href="javascript:void(0)" onclick="javascript:Materials.delete' + act + '(' + id + ',orgMaterials)">X</a>]</li>';
		options += option;
	}
	return options;
}

function createNewModule() {
	//alert('createNewModule');
	if (isEditing == true) {
		alert('Existe um modulo em modo de edicao');
		return;
	}
	isEditing = true;
	modulesCounter++;
	currentEditingModule = modulesCounter;
	//Materials.init(currentEditingModule - 1);
	//Activities.init(currentEditingModule - 1);
	ContManag.init(currentEditingModule - 1);
	//Materialsaa.init(currentEditingModule);
	//Activitiesaa.init(currentEditingModule);

	var moduleId = "module" + modulesCounter;
	var moduleName = "moduleName" + modulesCounter;
	var moduleVisible = "moduleVisible" + modulesCounter;
	var moduleNumber = "moduleNumber" + modulesCounter;
	var moduleDescription = "moduleDescription" + modulesCounter;
	var dynamic = "dynamic" + modulesCounter;
	var materialsList = "materialsList" + modulesCounter;
	var activitiesList = "activitiesList" + modulesCounter;
	
	var module = '<tr>' +
    					'<td width="50%" class="headTab"><input type="text" name="' + moduleName + '" value="Nome do M&oacute;dulo"/></td>' +
					    '<td align="center" class="headTab">Vis&iacute;vel <input name="' + moduleVisible + '" type="checkbox" checked="checked" /></td>' +
					    '<td align="right" class="headTab"><select name="' + moduleNumber + '" onchange="reorderModules(' + currentEditingModule + ');">';
	for (var i = 1; i <= modulesCounter; i++) {
		module += '<option ';
		if (currentEditingModule == i) {
			module += 'selected';
		}
		module += ' value="' + i + '"/>' + i;
	}				        
		module += '</select></td>' + 
  				    '</tr>' +
			 		'<tr>' +
    					'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo:</td>' + 
  					'</tr>' + 
					'<tr>' + 
					    '<td colspan="3" class="modDescrip"><textarea name="' + moduleDescription + '" class="modDescriptTextarea">Lorem ipsum sit amet abajour rolis strange text just for fun girls have more than this</textarea></td>' + 
					'</tr>' + 
					'<tr>' + 
					    '<td class="headTab2">Materiais [<a href="javascript:void(0)" onclick="chooseMaterial();">+</a>]</td>' + 
					    '<td colspan="3" class="headTab2">Atividades [<a href="javascript:void(0)" onclick="chooseActivity();">+</a>]</td>' + 
					'</tr>' + 
			 		'<tr valign="top">' + 
    					'<td>' + 
							'<ul id="' + materialsList + '"></ul>' + 
						'</td>' + 
    					'<td colspan="3">' + 
							'<ul id="' + activitiesList + '"></ul>' + 
						'</td>' + 
				    '</tr>' + 
			 		'<tr align="right" id="' + dynamic + '">' +
    					'<td colspan="3"><a href="javascript:void(0)" onclick="saveModule();">Salvar</a>' +
    								 ' / <a href="javascript:void(0)" onclick="cancelModule();">Cancelar</a>' + 
        					         ' / <a href="javascript:void(0)" onclick="deleteModule();">Excluir</a></td>' +
  					'</tr>';
	DWRUtil.setValue(moduleId, module);
	saveJustCreatedModule();
}

function reorderModules(currentModule) {
	var newValue = DWRUtil.getValue('moduleNumber' + currentModule);
	Module.reorderModules(newValue, currentModule, fillModsCB);	
}

function fillModsCB(data) {
	if (data == null) {
	 	moduleNumber = "moduleNumber" + currentEditingModule;
	 	DWRUtil.setValue(moduleNumber, currentEditingModule);
	 	if (isDeletingWithoutSaving) {
	 		deleteOrCancelWithoutSaving();	
	 		isDeletingWithoutSaving = false;
	 	} else {
	 		alert("O modulo nao foi salvo");
	 	}
	} else {
		DWRUtil.setValue("form", '');
		moduleIdentification = 1;
		Module.retrieveInfo(moduleIdentification, fillModDataCB);
		currentEditingModule = data.newOrder;
	}
}

function fillModDataCB(data) {
	if (data != null) {
		var html = DWRUtil.getValue("form");
		var visible = data.visible;
		var name = data.name;
		var desc = data.description;
		var order = data.order;
		var current = data.current;
		Activities.init(current - 1);
		Materials.init(current - 1);
		var modId = "module" + current;
		var materialsList = "materialsList" + current;
		var activitiesList = "activitiesList" + current;
		var dynamicId = "dynamic" + current;
		
		html += '<table id="' + modId + '" class="tableMod">' + 
					'<tr>' +
			     		'<td width="50%" class="headTab">' + name + '</td>' +
				 		'<td align="center" class="headTab">';
		if (visible == true) {
			html += 'Vis&iacute;vel</td>';
		} else {
			html += '<span class="alert">Invis&iacute;vel</span></td>';
		}			  
			html += 	'<td align="right" class="headTab">' + order + '</td>' +
				  	'</tr>' + 
 				  	'<tr>' + 
  				  		'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo: </td>' +
 				  	'</tr>' +
				  	'<tr>' +
    			  		'<td colspan="3" class="modDescrip">' + desc + '</td>' +
  				  	'</tr>';
  		name = "'" + name + "'";
		desc = "'" + desc + "'";
		visible = "'" + visible + "'";
		order = "'" + order + "'";		  	
			html += '<tr>' +
     			  		'<td width="50%" class="headTab2">Materiais</td>' +
				  		'<td colspan="3" class="headTab2">Atividades</td>' +
  				  	'</tr>' +
  				  	'<tr valign="top">' + 
			    		'<td><ul id="' + materialsList + '"></ul></td>' +
			    		'<td colspan="3"> <ul id="' + activitiesList + '"></ul></td>' +
			  	  	'</tr>' + 
					'<tr align="right" id=' + dynamicId + '>' + 
			    		'<td colspan="3"><a href="javascript:void(0)" onclick="editingState(' +
  	    				name + ', ' + desc + ', ' + visible + ', ' + order + ', ' +
  	    				current + ');">Editar</a></td>' +
			  	  	'</tr></table>';
		DWRUtil.setValue("form", html);
	  	isEditing = false;	
	  	if (moduleIdentification <= modulesCounter) {
		  	addCreateNewModuleOption(moduleIdentification);
	  		Activities.retrieveInfo(moduleIdentification, fillActDataCB);
	  	}
	} else {
		isEditing = false;
		if (!isFirstTime) {
			Module.retrieveInfo(currentEditingModule, preparationForEditionCB);
		} else {
			isFirstTime = false;
			deleteM();
		}
	}
}

function fillActDataCB(data) {
	//alert('fillActDataCB');
	var currentModule = data.current;
	var noPolls = data.pollCounter;
	var noMDS = data.mdCounter;
	var noForuns = data.forumCounter;
	var noStudentEvaluation = data.studentEvaluationCounter;
	var deliveries = data.deliveries;
	var polls = data.polls;
	var foruns = data.foruns;
	var evaluation = data.evaluation;
	var studentEvaluation = data.studentEvaluation;
	var hasEvaluated = data.hasEvaluated;
	var activitiesList = "activitiesList" + currentModule;
	var html = '';
	
	for (var i = 0; i < noMDS; i++) {
		html += '<li>' + deliveries[i] + '</li>';
	}
	for (var j = 0; j < noPolls; j++) {
		html += '<li>' + polls[j] + '</li>';
	}
	for (var k = 0; k < noForuns; k++) {
		html += '<li>' + foruns[k] + '</li>';
	}
	if (evaluation && !hasEvaluated){
	html += '<li> Avaliacao de Curso </li>';
	}
	for (var w = 0; w< noStudentEvaluation; w++) {
		html += '<li>' + studentEvaluation[w] + '</li>';
	}
	DWRUtil.setValue(activitiesList, html);
	Materials.retrieveInfo(currentModule, fillMatDataCB);
}

function fillMatDataCB(data) {
	var matId = data.materialsId;
	var matList = data.materialsList;
	var html = '';
	for (var i = 0; i < matList.length; i++) {
		html += '<li>' + matList[i] + '</li>';
	}
	DWRUtil.setValue("materialsList" + data.moduleId, html);
	moduleIdentification++;
	Module.retrieveInfo(moduleIdentification, fillModDataCB);
}

function preparationForEditionCB(data) {
	editingState(data.name, data.description, data.visible, data.order, data.current);
}

function addCreateNewModuleOption(modId) {
	if (modId == modulesCounter) {
		var	nextModule = "module" + (modulesCounter + 1);
		if ($(nextModule) == null) {
			var create = '<a href="javascript:void(0)" onclick="createNewModule();">Criar novo m&oacute;dulo</a>';
			var form = DWRUtil.getValue("form");
			form += '<table id="' + nextModule + '" class="tableMod">'+create+'</table>';
			DWRUtil.setValue("form", form);
		}
	}
}

function listActivities() {
	Activities.listActivities(currentEditingModule, listActivitiesCB);
}

function listActivitiesCB(data) {
	DWRUtil.removeAllOptions("activities");
	DWRUtil.addOptions("activities", data);
}

function chooseActivity() {
	var html = '<td  class="cont-tableinterna" colspan="3">' +
					'<table class="tableinterna">' + 
        				'<tr>' +  
          					'<td colspan="3" class="headTab">Nova Atividade</td>' + 
        				'</tr>' +
        				'<tr>' +  
          					'<td colspan="3"><select id="activities"></select></td>' + 
        				'</tr>' + 
        				'<tr>' + 
          					'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="selectActivity()">Selecionar</a>' + 
          					' / <a href="javascript:void(0)" onclick="cancel()">Cancelar</a></td>' + 
        				'</tr>' + 
      				'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	listActivities();
}

function selectActivity() {
	var selection = DWRUtil.getValue("activities");
	if (selection != "select") {
		eval('activitySelected_' + selection + '();');
	}
}
 
function activitySelected_material() {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table  class="tableinterna">' + 
  						'<tr>' +  
    						'<td class="headTab">Entrega de Material</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome do Material:<br /> <input id="name" type="text" /></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Descri&ccedil;&atilde;o do Material:<br /> <textarea id="desc" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Entrega:<br /> <input id="day" type="text" size="2" maxlength="2" />' + 
     						' / <input id="month" type="text" size="2" maxlength="2" />' + 
     						' / <input id="year" type="text" size="4" maxlength="4" /> </td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td><input id="allow" type="checkbox" value="checkbox" checked="checked" />Permitir entregas ap&oacute;s a data.</td>' +
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_material(-1)">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' +
				'</td>';
 	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function activitySelected_forum() {
	var html = '<td class="cont-tableinterna" colspan="3" >' + 
					'<table class="tableinterna">' + 
  						'<tr>' +  
    						'<td colspan="3" class="headTab">Forum</td>' + 
  						'</tr>' + 
					  	'<tr>' +  
    						'<td colspan="3">T&oacute;pico do Forum:<br /> <input id="topic" type="text" /></td>' + 
						'</tr>' + 
  						'<tr>' +  
    						'<td colspan="3">Descri&ccedil;&atilde;o do Forum:<br /> <textarea name="description" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_forum(-1)">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function activitySelected_poll() {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table class="tableinterna">' + 
						'<tr>' +  
    						'<td class="headTab">Enquete</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome da Enquete:<br /> <input id="name" type="text" /></td>' + 
	 	 				'</tr>' + 
  						'<tr>' +  
    						'<td>Pergunta da Enquete:<br /> <textarea id="question" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Alternativas:' +  
      							'<ul id="poll_alt"></ul>' + 
        						'<ul><li><a href="javascript:void(0)" onclick="addInputFieldToPoll();">Adicionar nova alternativa</a>.</li></ul>' + 
      						'</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Encerramento:<br /> <input id="day" type="text" size="2" maxlength="2" />' + 
    						' / <input id="month" type="text" size="2" maxlength="2" />' + 
    						' / <input id="year" type="text" size="4" maxlength="4" /> </td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_poll(-1)">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	DWRUtil.addOptions("poll_alt", ['<input type="text" id="alt1"> [<a href="javascript:void(0)" onclick="removeInputField(1)">X</a>]']);
	DWRUtil.addOptions("poll_alt", ['<input type="text" id="alt2"> [<a href="javascript:void(0)" onclick="removeInputField(2)">X</a>]']);
	noAlternatives = 2;
}
function addInputFieldToPoll() {
	noAlternatives += 1;
	var alt = noAlternatives;
	DWRUtil.addOptions("poll_alt",
		['<input type="text" id="alt' + alt + '"> ' +
		 '[<a href="javascript:void(0)" onclick="removeInputField('+alt+')">X</a>]']);
}

function activitySelected_scorm() {

}

function activitySelected_evaluation() {
	var html = '<td  class="cont-tableinterna" colspan="3">' +
					'<table class="tableinterna" id="courseEval">';
		html += courseEvaluationHeader();			
	    html +=	collapseCourseEval(true, true, true, true, true, true);
	    html += '</table>' +
			'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);			
}



function collapseCourseEval(chkImp, chkCrit, chkInt, chkTutor, chkPeer, chkUnderstanding) {
	var importanceDesc = "'" + 'importanceDesc' + "'";
	var importanceText = ' Relevancia ';
	var criticalReflectionDesc = "'" + 'criticalReflectionDesc' + "'";
	var criticalText = ' Reflexao Critica ';
	var interactionDesc = "'" + 'interactionDesc' + "'";
	var interactionText = ' Interacao ';
	var tutorSupportDesc = "'" + 'tutorSupportDesc' + "'";
	var tutorText = ' Apoio dos Tutores ';
	var peerSupportDesc = "'" + 'peerSupportDesc' + "'";
	var peerText = ' Apoio dos Colegas ';
	var understandingDesc = "'" + 'understandingDesc' + "'";
	var understandingText = ' Compreensao ';
	
	var html =  '<tr id="importance">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkImp)  
		html += ' checked="checked" ';
		html += ' id="importanceCol"/>' + importanceText + '(<a href="javascript:void(0)" ';
	importanceText = "'" + importanceText + "'";
		html +=     'onclick="expandCriterium(' + importanceDesc + ', ' + importanceText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="importanceDesc">Descricao da relevancia</td>' +
		        '</tr>' +
		        '<tr id="criticalReflection">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkCrit) 	            
		 html += ' checked="checked" ';
		 html += ' id="criticalReflectionCol"/>' + criticalText + '(<a href="javascript:void(0)" ';
	criticalText = "'" + criticalText + "'";	            
		 html +=    'onclick="expandCriterium(' + criticalReflectionDesc + ', ' + criticalText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="criticalReflectionDesc">Descricao da reflexao critica</td>' +
		        '</tr>' +
		        '<tr id="interaction">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkInt) 	            
		 html += ' checked="checked" ';
		 html += ' id="interactionCol"/>' + interactionText + '(<a href="javascript:void(0)" ';
	interactionText = "'" + interactionText + "'";	            
		  html +=   'onclick="expandCriterium(' + interactionDesc + ', ' + interactionText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="interactionDesc">Descricao da interacao</td>' +
		        '</tr>' +
		        '<tr id="tutorSupport">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkTutor) 	            
		  html += ' checked="checked" ';
		  html += ' id="tutorSupportCol"/>' + tutorText + '(<a href="javascript:void(0)" ';
	tutorText = "'" + tutorText + "'";	            
		  html +=   'onclick="expandCriterium(' + tutorSupportDesc + ', ' + tutorText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="tutorSupportDesc">Descricao do apoio dos tutores</td>' +
		        '</tr>' +
		        '<tr id="peerSupport">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkPeer) 		            
		html += ' checked="checked" ';
		html += ' id="peerSupportCol"/>' + peerText + '(<a href="javascript:void(0)" ';
	peerText = "'" + peerText + "'";	            
		  html +=   'onclick="expandCriterium(' + peerSupportDesc + ', ' + peerText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="peerSupportDesc">Descricao do apoio dos colegas</td>' +
		        '</tr>' +
		        '<tr id="understanding">' +
		            '<td colspan="3"><input type="checkbox" ';
	if (chkUnderstanding) 		            
		html += ' checked="checked" ';
		html += ' id="understandingCol"/>' + understandingText + '(<a href="javascript:void(0)" ';
	understandingText = "'" + understandingText + "'";	            
		  html +=   'onclick="expandCriterium(' + understandingDesc + ', ' + understandingText + ')">+</a>)</td>' +
		        '</tr>' +
		        '<tr>' +
		            '<td colspan="3" class="courseEvalDesc" style="display: none" id="understandingDesc">Descricao da compreensao</td>' +
		        '</tr>' + 
		        '<tr>' +
		          '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_evaluation()">Salvar</a></td>' +
		        '</tr>'; 
	return html;			        
}

function expandCriterium(type, text) {
	updateCourseEvalFields();
	$(type).style.display = 'block';
	var typeLength = type.length;
	var typeOption = type.substring(0, typeLength - 4);
	var typeCol = typeOption + 'Col';
	var content = '<td colspan="3"><input type="checkbox" ';
	
	if (DWRUtil.getValue(typeCol)) 
		content += 'checked="checked" '; 
		
	content += 'id="' + typeCol + '"/>' + text + '(<a href="javascript:void(0)" onclick="updateCourseEvalFields()">-</a>)</td>'; 
	DWRUtil.setValue(typeOption, content);
}

function updateCourseEvalFields() {
	var chkImp = DWRUtil.getValue('importanceCol');
	var chkCrit = DWRUtil.getValue('criticalReflectionCol');
	var chkInt = DWRUtil.getValue('interactionCol');
	var chkTutor = DWRUtil.getValue('tutorSupportCol');
	var chkPeer = DWRUtil.getValue('peerSupportCol');
	var chkUnderstanding = DWRUtil.getValue('understandingCol');
	
	var html = courseEvaluationHeader();			
	    html +=	collapseCourseEval(chkImp, chkCrit, chkInt, chkTutor, chkPeer, chkUnderstanding);
	DWRUtil.setValue('courseEval', html);
}

function activitySave_material(materialId) {
	var name	= trim(DWRUtil.getValue("name" ));
	var desc	= trim(DWRUtil.getValue("desc" ));
	var day		= DWRUtil.getValue("day"  );
	var month	= DWRUtil.getValue("month");
	var year	= DWRUtil.getValue("year" );
	var allow	= DWRUtil.getValue("allow");
	
	if (!isDate(month + '/' + day + '/' + year)) {
		return;
	}
	if (name == '') {
		alert("O nome eh obrigatorio");
		return;
	} else if (desc == '') {
		alert("A descricao eh obrigatoria!");
		return;
	}
	
	Activities.saveMaterial(name, desc, day,
						month, year, allow, materialId, currentEditingModule,
						orgActivities);
}

function activitySave_forum(forumId) {
	var topic = trim(DWRUtil.getValue("topic"));
	var desc;
	if (forumId != -1) {
		desc = 'none';
	} else {
		desc = trim(DWRUtil.getValue("description"));
	}
	if (topic == '') {
		alert("O topico eh obrigatorio");
	} else if (desc == '') {
		alert("A descricao eh obrigatoria");
	} else {
		Activities.saveForum(topic, desc, forumId, currentEditingModule, orgActivities);
	}
}

function activitySave_poll(pollId) {
	var name		= trim(DWRUtil.getValue("name"));
	var question	= trim(DWRUtil.getValue("question"));
	var day			= DWRUtil.getValue("day");
	var month		= DWRUtil.getValue("month");
	var year		= DWRUtil.getValue("year");
	if (!isDate(month + '/' + day + '/' + year)) {
		return;
	}
	if (name == '') {
		alert("O nome eh obrigatorio");
		return;
	} else if (question == '') {
		alert("A pergunta eh obrigatoria");
		return;
	}		
	
	var alternatives = new Array();
	for (var i = 0; i < noAlternatives; i++) {
		alternatives[i] = trim(DWRUtil.getValue("alt" + (i + 1)));
		if (alternatives[i] == '') {
			alert("Nao podem existir alternativas em branco");
			return;
		}
	}
	
	Activities.savePoll(name, question, day, month,
					year, alternatives, pollId, currentEditingModule, 
					orgActivities);
}

function activitySave_evaluation() {
	//alert('activitySave_evaluation');
	var criteria = new Array();
	var totalChecked = 0;
	criteria[0] = DWRUtil.getValue("importanceCol");
	criteria[1] = DWRUtil.getValue("criticalReflectionCol");
	criteria[2] = DWRUtil.getValue("interactionCol");
	criteria[3] = DWRUtil.getValue("tutorSupportCol");
	criteria[4] = DWRUtil.getValue("peerSupportCol");
	criteria[5] = DWRUtil.getValue("understandingCol");
	
	if (criteria[0]) 
		totalChecked += 1;
	if (criteria[1]) 
		totalChecked += 1;
	if (criteria[2]) 
		totalChecked += 1;
	if (criteria[3]) 
		totalChecked += 1;
	if (criteria[4]) 
		totalChecked += 1;
	if (criteria[5]) 
		totalChecked += 1;
		
	if (totalChecked >= 3)
		if(!isCreatingModule)
			Activities.saveCourseEvaluation(criteria, currentEditingModule, orgActivities);
		else
			Activities.saveCourseEvaluation(criteria, currentEditingModule, orgActivities, evalCB);	
	 else
	 	alert("Selecione mais criterios de avaliacao para este modulo");
}

function evalCB(){
	//alert("Call back");
}

function orgActivities(data) {
	//alert('orgActivities');
	if (data.length > 0 && data[0][2] == 'Error') {
		showErrors(data);
		return;
	}
	var options = new Array();
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = data[i][1];
		//alert('orgActivities');
		//if (i != (data.length - 1))
		if(act!='CourseEvaluation')
			option += ' [<a href="javascript:void(0)" onclick="Activities.retrieve' + act + '(' + id + ',retrieve' + act + 'CB)">E</a>] ';
			option += ' [<a href="javascript:void(0)" onclick="Activities.delete' + act + '(' + id + ',orgActivities)">X</a>]';
			options[i] = option;
			
	}
	var div = 'activitiesList' + currentEditingModule;
	DWRUtil.removeAllOptions(div);
	DWRUtil.addOptions(div, options);
	cancel();
}

function showErrors(errors) {
	var error = '';
	for (var i = 0; i < errors.length; i++) {
		error += errors[i][1] + '\n';
	}
	alert(error);
}

function retrieveMaterialDeliveryCB(data) {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table  class="tableinterna">' + 
  						'<tr>' +  
    						'<td class="headTab">Entrega de Material</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome do Material:<br /> <input id="name" type="text" value="' + data.name + '"/></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Descri&ccedil;&atilde;o do Material:<br /> <textarea id="desc" class="ativDescriptTextarea">' + data.description + '</textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Entrega:<br /> <input id="day" type="text" size="2" maxlength="2" value="' + data.day + '" />' + 
     						' / <input id="month" type="text" size="2" maxlength="2" value="' + data.month + '"/>' + 
     						' / <input id="year" type="text" size="4" maxlength="4" value="' + data.year + '"/> </td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td><input id="allow" type="checkbox" value="checkbox" ';
    if (data.allow == true)			
    	html +=	'checked="checked"';					
    	html += '/>Permitir entregas ap&oacute;s a data.</td>' +
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_material(' + data.materialId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function retrieveForumCB(data) {
	var html = '<td class="cont-tableinterna" colspan="3" >' + 
					'<table class="tableinterna">' + 
  						'<tr>' +  
    						'<td colspan="3" class="headTab">Forum</td>' + 
  						'</tr>' + 
					  	'<tr>' +  
    						'<td colspan="3">T&oacute;pico do Forum:<br /> <input id="topic" type="text" value="' + data.topic + '"/></td>' + 
						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_forum(' + data.forumId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function retrievePollCB(data) {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table class="tableinterna">' + 
						'<tr>' +  
    						'<td class="headTab">Enquete</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome da Enquete:<br /> <input id="name" type="text" value="' + data.name + '"/></td>' + 
	 	 				'</tr>' + 
  						'<tr>' +  
    						'<td>Pergunta da Enquete:<br /> <textarea id="question" class="ativDescriptTextarea">' + data.question + '</textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Alternativas:' +  
      							'<ul id="poll_alt"></ul>' + 
        						'<ul><li><a href="javascript:void(0)" onclick="addInputFieldToPoll();">Adicionar nova alternativa</a>.</li></ul>' + 
      						'</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Encerramento:<br /> <input id="day" type="text" size="2" maxlength="2" value="' + data.day + '"/>' + 
    						' / <input id="month" type="text" size="2" maxlength="2" value="' + data.month + '"/>' + 
    						' / <input id="year" type="text" size="4" maxlength="4" value="' + data.year + '"/> </td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td><a href="javascript:void(0)" onclick="cancel()">Cancelar</a> / <a href="javascript:void(0)" onclick="activitySave_poll(' + data.pollId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	for (var i = 0; i < data.alts.length; i++) {
		DWRUtil.addOptions("poll_alt",
			['<input type="text" id="alt'+(i+1)+'" value="' + data.alts[i] + '"> [<a href="javascript:void(0)" onclick="removeInputField('+(i+1)+')">X</a>]']);
	}
	noAlternatives = data.alts.length;
}

/**
PARTE RELATIVA A VISUALIZA??O DO MODULO
*/

function fillModules(noModules, userRole, userProfile) {
	//alert('fillModules');
	ContManag.clear({callback: function() {}, async:false});
	Activities.init(1);
	Materials.init(1);
	usrRole = userRole;
	usrProfile = userProfile;
	modulesCounter = noModules;
	currentEditingModule = modulesCounter;
	isFirstTime = true;
	if ((usrRole == 0) || (usrRole == 3) || (usrProfile == 2)) {
		isPowerful = true;
	}
	DWRUtil.setValue("form", '');
	moduleIdentification = 1;
	Module.retrieveInfo(moduleIdentification, fillModuleDataCB);
}

function fillModulesCB(data) {
	//alert('xegou');
	if (data == null) {
		if (isDeleting) {
			deleteOrCancelWithoutSaving();
		} else {
	 		alert("O modulo nao foi salvo");
	 	}
	} else {
		DWRUtil.setValue("form", '');
		moduleIdentification = 1;
		Module.retrieveInfo(moduleIdentification, fillModuleDataCB);
		newOrder = data.newOrder;
	}
}

/*
 *	Essa fun??o monta os m?dulos na tela.
 *	? chamada por FillModules
 *	Chama FillActivitiesDataCB e FillMaterialsDataCB
 */
 
function fillModuleDataCB(data) {
	var html = DWRUtil.getValue("form");
	if (data != null) {
		var visible = data.visible;
		if ((visible == true) || (isPowerful == true)) {
			var name = data.name;
			var desc = data.description;
			var order = data.order;
			var current = data.current;
			currentEditingModule = current;
			var modId = "module" + current;
			var visibleId = "moduleVisible" + current;
			var materialsList = "materialsList" + current;
			var activitiesList = "activitiesList" + current;
			var dynamicId = "dynamic" + current;
			
			html += '<div id="' + modId + '" >'+ 
			'<table class="tableMod" >' + 
  						'<tr>' + 
						    '<td width="50%" class="headTab">' + name + '</td>';
		if ((isPowerful == true) && (visible == true)) {
			html += 		'<td align="center" name="' + visibleId + '" class="headTab">Vis&iacute;vel</td>';
		} else if ((isPowerful == true) && (visible == false)) {
			html += 		'<td align="center" name="' + visibleId + '" class="headTab"><span class="alert">Invis&iacute;vel</span></td>';	
		}
    	if(isPowerful){
    	html += 		'<td align="right" class="headTab">' + order + '</td>';
    	}
    	else{
    	html += 		'<td align="right" class="headTab">' + visibleNumber + '</td>';
    	}	
    		html += 	'</tr>' + 
  						'<tr>' +  
    						'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo: </td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td colspan="3" class="modDescrip">' + desc + '</td>' + 
  						'</tr>' +
  						'<tr>' +  
    						'<td width="50%" class="headTab2">Materiais</td>' + 
						    '<td colspan="3" class="headTab2">Atividades</td>' + 
					  	'</tr>' + 
  						'<tr valign="top">' +  
    						'<td> <ul id="' + materialsList + '"></ul></td>' + 
    						'<td colspan="3"> <ul id="' + activitiesList + '"></ul></td>' +
  						'</tr>' + 
  						'<tr align="right">'+
  						'<td colspan="3">'+
  						'<div id="' + dynamicId + '">'+
  						'</div>'+
  						'</td>'+
  						'</tr>' + 
					'</table>'+
					'</div>';
			DWRUtil.setValue("form", html);
			if (isPowerful == true) {
				var dynamic = '';
				dynamic += '<a href="javascript:void(0)" onclick="retrieveModuleInfo(' + current + ')">Editar</a>';
				DWRUtil.setValue(dynamicId, dynamic);
			}
			isEditing = false;	
	  		if(moduleIdentification <= modulesCounter){ 
			  	addCreateModuleOption(moduleIdentification);
			  	if(!(isDeleting && moduleIdentification==modulesCounter)){
		  		Activities.retrieveInfo(moduleIdentification, fillActivitiesDataCB);
				Materials.retrieveInfo(moduleIdentification, fillMaterialsDataCB);
				}else{
					/*
					S? entra aqui quando est? deletando um m?dulo. 
					Essa fun??o ? necess?ria para chamar a fun??o deleteMod
					*/
					if (isReOrderingModules == true) {
						moduleIdentification++;
						Module.retrieveInfo(moduleIdentification, fillModuleDataCB);
					}
				}
		  	}
		  	visibleNumber++;
		} else {
			if (moduleIdentification <= modulesCounter) {
				moduleIdentification++;
				Module.retrieveInfo(moduleIdentification, fillModuleDataCB);
			}
		}
	} else {
		 addCreateModuleOption(moduleIdentification);
		 isEditing = false;
		 if (!isFirstTime) {
		 	Module.retrieveInfo(newOrder, editState);
		 } else {
		 	isFirstTime = false;
		 	if (isDeleting == true) {
		 		isDeleting = false;
		 		deleteMod();
		 	}
		 }
	}
}

/*
 *	Insere as atividades no m?dulo
 *	Eh invocada por fillModuleDataCB
 */
 
function fillActivitiesDataCB(data) {
	var currentModule = data.current;
	currentEditingModule = currentModule;
	var noPolls = data.pollCounter;
	var noMDS = data.mdCounter;
	var noForuns = data.forumCounter;
	var noStudentEvaluation = data.studentEvaluationCounter
	var deliveries = data.deliveries;
	var polls = data.polls;
	var foruns = data.foruns;
	var evaluation = data.evaluation;
	var hasEvaluated = data.hasEvaluated;
	var studentEvaluation = data.studentEvaluation
	var activitiesList = "activitiesList" + currentModule;
	var html = '';
	for (var i = 0; i < noMDS; i++) {
		html += '<li><a href="javascript:void(0)" onclick="viewHomework(' + i + ', ' + currentModule + ')">' + deliveries[i] + '</a></li>';
	}
	for (var j = 0; j < noPolls; j++) {
		html += '<li><a href="javascript:void(0)" onclick="viewPoll(' + j + ', ' + currentModule + ')">' + polls[j] + '</a><br />' +
		        '<a href="javascript:void(0)" onclick="viewPollResults(' + j + ', ' + currentModule + ')">Ver Resultados </a></li>';
	}
	for (var k = 0; k < noForuns; k++) {
		html += '<li><a href="javascript:void(0)" onclick="viewForumMessages(' + k + ', ' + currentModule + ')">' + foruns[k] + '</a></li>';
	}
	if (evaluation && !hasEvaluated) {
		html += '<li><a href="javascript:void(0)" onclick="evaluateCourse(' + currentModule + ')"> Avaliacao de Curso </a></li>'; 
	}
	for (var w = 0; w< noStudentEvaluation; w++) {
		html += '<li><a href="javascript:void(0)" onclick="viewStudentEvaluation('+w+','+currentModule+')">' + studentEvaluation[w] + '</a></li>';
	}
	
	
	DWRUtil.setValue(activitiesList, html);
}
/*
 *	Insere os materiais no m?dulo
 *	Eh invocada por fillModuleDataCB
 */
function fillMaterialsDataCB(data) {
	var matId = data.materialsId;
	var matList = data.materialsList;
	var html = '';
	for (var i = 0; i < matList.length; i++) {
		html += '<li><a href="javascript:void(0)" onclick="downloadMaterial(' + matId[i] + ')">' + matList[i] + '</a></li>';
	}
	DWRUtil.setValue("materialsList" + data.moduleId, html);
	if (isReOrderingModules == true) {
		moduleIdentification++;
		Module.retrieveInfo(moduleIdentification, fillModuleDataCB);
	}
}

function reorderMods(currentModule) {
	isReOrderingModules = true;
	var newValue = DWRUtil.getValue('moduleNumber' + currentModule);
	Module.reorderModules(newValue, currentModule, fillModulesCB);	
}

function downloadMaterial(materialId) {
	document.location.href = getPath() + 'downloadMaterial.do?id=' + materialId;
}

function getPath() {
	var path = document.location.pathname;
	return path.substring(0, path.lastIndexOf('/') + 1);
}

function viewHomework(homeworkId, moduleId) {
	if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	var cb = {callback:function(data) {viewHomeworkCB(moduleId, data);}};
	Activities.retrieveHW(homeworkId, moduleId, cb );
}

function viewHomeworkCB(moduleId, data) {
	var formId = 'delivery'+data.homeworkId;
	var isLate = data.isLate;
	if (!data.allow && (data.isLate == true)) {
		alert("O prazo para entrega deste material foi finalizado!");
		isDoingSomething = false;
		return;
	}
	var html = '<td  class="cont-tableinterna" colspan="3" >' + 
					'<table class="tableinterna">' + 
	  					'<tr>' +  
               				'<td class="headTab" colspan="3">Entrega de Material</td>' + 
          				'</tr>' + 
          				'<tr>' + 
              				'<td colspan="2">' + data.name + '</td>' + 
              				'<td align="right">Data de Entrega:</td>' + 
              			'</tr>' + 
              			'<tr align="right">';	
    if (isLate) {         				 
        html +=       		'<td colspan="3"><span class="alert">' + data.deadline + '</span></td>';
    } else {
    	html +=             '<td colspan="3">'+ data.deadline + '</td>';
    }
        html +=   		'</tr>' + 
          				'<tr>'; 
    if (isLate) {          				
        html +=				'<td colspan="2">' + data.description + '</td>' +
        					'<td align="right">Fora do Prazo:</td>';
    } else {
    	html +=				'<td colspan="3">' + data.description + '</td>';
    }
        html +=			'</tr>';
    if (isLate) {   
        html +=			'<tr align="right">' +
        					'<td colspan="3"><span class="alert">' + data.today + '</span></td>' +
        				'</tr>';	
    }   
		html += 		'<iframe src="about:blank" name="'+formId+'frame" ' +
						'style="width:0px; height:0px; border: 0px"></iframe>' +
						'<tr><td colspan="3">' +
						'<form action="' + getPath() + 'uploadDelivery.do" method="post" '+
						'id="'+formId+'" enctype="multipart/form-data" target="'+formId+'frame">' +
							'<input type="hidden" name="moduleId" value="'+moduleId+'" />' +
							'<input type="hidden" name="homeworkId" value="'+data.homeworkId+'" />' +
							'<input type="file" name="file"/>' +
						'</form>' +
						'</td></tr>' +
						'<tr>' +
             				'<td colspan="3">Limite de 888kb</td>' + 
        				'</tr>' +
  						'<tr align="right">' + 
             				'<td colspan="3" ><a href="javascript:void(0)" onclick="cancelAction(' + moduleId + ')">' +
             				'Cancelar</a> / <a href="javascript:void(0)" onclick="$(\''+formId+'\').submit()">Enviar</a></td>' +
        				'</tr>' +
  					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+moduleId, html);
}

function viewPoll(pollId, currentModule) {
	if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	Activities.retrieveP(pollId, currentModule, viewPollCB);
} 

function viewPollCB(data) {
	var isLate = data.isLate;
	var hasAnswered = data.hasAnswered;
	var alts = data.alts;
	
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
			   		'<table class="tableinterna">' +
	  					'<tr>' + 
               				'<td class="headTab" colspan="3">Responder Enquete</td>' +
          				'</tr>' +
          				'<tr>' +
              				'<td colspan="2">' + data.name + '</td>' +
              				'<td align="right">Encerramento:</td>' +
          				'</tr>' +
          				'<tr align="right">' +
               				'<td colspan="3">' + data.finishDate + '</td>' +
          				'</tr>' +
          				'<tr>' +
              				'<td colspan="3">' + data.question + '</td>' +
          				'</tr>' +
          				'<tr>' +
              				'<td colspan="3">Alternativas:</td>' +
          				'</tr>';
    for (var i = 0; i < alts.length; i++) {
		html += '<tr><td colspan="3"><input type="radio" id="alt' + i + '" name="poll_alt"/>' + alts[i] + '</td></tr>';
	}      				
        html += 		'<tr align="right">' +
              				'<td colspan="3" ><a href="javascript:void(0)" onclick="cancelAction(' + data.module + ')">Cancelar</a>' +
              				' / <a href="javascript:void(0)" onclick="replyPoll(' + data.pollId + 
              				',' + hasAnswered + ',' + isLate + ',' + alts.length + ',' + data.module + 
              				')">Enviar</a></td>' +
          				'</tr>' +
					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+data.module, html);
}

function cancelAction (moduleId) {
	isDoingSomething = false;
	var dynamic = '';
	if (isPowerful == true) {
		dynamic += '<td colspan="3"><a href="javascript:void(0)" onclick="retrieveModuleInfo(' +
					moduleId + ');">Editar</a>' +
        			'</td>';
	}
	DWRUtil.setValue('dynamic'+moduleId, dynamic);
}

function replyPoll(pollId, hasAnswered, isLate, noAlts, currentModule) {
	var altIndex = retrieveCheckedAlternative(noAlts);
	if (altIndex == -1) {
		alert("O usuario deve escolher uma alternativa");
		return;
	}
	if (hasAnswered) {
		alert("O usuario ja votou nesta enquete");
		return;
	}
	if (isLate) {
		alert("A data de encerramento da enquete ja passou");
		return;
	}
	isDoingSomething = false;
	Activities.replyPoll(pollId, altIndex, currentModule, cancelActionCB);
}

function cancelActionCB(data) {
	cancelAction(data);
}

function retrieveCheckedAlternative (noAlts) {
	var result = -1;
	var checked = false;
	for (var i = 0; i < noAlts; i++) {
		checked = DWRUtil.getValue('alt' + i);
		if (checked == true)
			result = i;
	}
	return result;
}

function viewPollResults(pollId, currentModule) {
    if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	Activities.viewPollResults(pollId, currentModule, viewPollResultsCB);
}

function viewPollResultsCB(data) {
	var alts = new Array();
	var length = data.length;
	var question = data[length - 1][0];
	var html = '<td  class="cont-tableinterna" colspan="3" >' +
					'<table class="tableinterna">' +
	  					'<tr>' + 
               				'<td class="headTab" colspan="3">Resultado da Enquete</td>' +
          				'</tr>' + 
          				'<tr>' +
          					'<td colspan="3">' + question + '</td>' +
          				'</tr>'+
          				'<tr>' +
              				'<td colspan="3">Alternativas:</td>' +
          				'</tr>' +
          				'<tr>' +
 			             	'<td colspan="3">' +
                  				'<ul id="poll_alt">';
	var moduleId = data[length - 2][0];
	for (var i = 0; i < length - 2; i++) {
		var option = data[i][0];
		var percentage = data[i][1];
		html += '<li>&nbsp;' + option + '  -  ' + percentage.toFixed(2) + '%</li>';
	 }             				
     html += 					'</ul>' +
              				'</td>' +
 					     '</tr>' +
          				 '<tr align="right">' +
              				'<td colspan="3" ><a href="javascript:void(0)" onclick="cancelAction(' + moduleId + ')">Voltar</a></td>' +
          				 '</tr>' +
					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+moduleId, html);
}

function viewForumMessages(forumId, currentModule) {
	if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	Activities.viewForumMessages(forumId, currentModule, viewForumMessagesCB);
}

function viewForumMessagesCB(data) {
	var msgs = new Array();
	var forumId = data.forumId;
	var moduleId = data.moduleId;

	var	html =  '<td  class="cont-tableinterna" colspan="3" >' +
				'<table class="tableinterna">' + 
					'<tr>' + 
						'<td class="headTab" colspan="3">Forum</td>' +
					'</tr>' + 
					'<tr>' + 
						'<td colspan="2">' + data.name + '</td>' + 
						'<td align="right">' + data.dateString + '</td>' + 
					'</tr>' + 
					'<tr>' + 
						'<td colspan="3">' + data.desc + '</td>' + 
					'</tr>' + 
					'<tr align="right">' + 
						'<td colspan="3"><a href="javascript:void(0)" onclick="cancelAction(' + moduleId + ')">' +
						'Voltar</a> / <a href="javascript:void(0)" onclick="forumReplyMessageArea(' + forumId + ', ' +
						moduleId + ');">Responder</a></td>' +
					'</tr>' + 
				'</table>';
	
	var messages = data.messages;
	for (var i = 0; i < messages.length; i++) {
		var message = 'message' + (i + 1);
		html += '<table id="' + message + '" class="tableinterna">' + 
					'<tr align="right">' + 
						'<td colspan="3"><a href="javascript:void(0)" onclick="collapseMessage(' +
						(i + 1) + ', ' + forumId + ', ' + moduleId + ');">-</a></td>' + 
					'</tr>' + 
					'<tr>' + 
						'<td colspan="3">' + messages[i].body + '</td>' + 
					'</tr>' + 
					'<tr align="right">' + 
						'<td colspan="3">' + messages[i].author + ', ' + messages[i].dateString + '</td>' + 
					'</tr>' + 
				'</table>';
	}
	html += '</td>';
    DWRUtil.setValue('dynamic'+moduleId, html);
}


 
function forumReplyMessageArea(forumId, moduleId) {
	var html = DWRUtil.getValue('dynamic' + moduleId);
	var length = html.length;
	html = html.substring(0, (length - 13));
	html += '<tr>' + 
              '<td colspan="3"><textarea name="message" class="textWidth"></textarea></td>' + 
          	'</tr>' + 
          	'<tr align="right">' + 
              '<td colspan="3"><a href="javascript:void(0)" onclick="cancelAction(' +
              + moduleId + ')">Cancelar</a> / <a href="javascript:void(0)" ' +
              'onclick="replyForum(' + forumId + ', ' + moduleId + ')">Responder</a></td>' + 
          	'</tr>' + 
          	'</table>' + 
          	'</td>';
	DWRUtil.setValue('dynamic'+moduleId, html);
}

function replyForum(forumId, moduleId) {
	var message = trim(DWRUtil.getValue("message"));
	if (message == '') {
		alert("Campo de mensagem nulo");
		return;
	}
	forumIdentification = forumId;
	moduleIdentification = moduleId;
	Activities.replyForum(forumId, moduleId, message);
	setTimeout("Activities.viewForumMessages(forumIdentification, moduleIdentification, viewForumMessagesCB)",200);
}

function expandMessage(messageId, forumId, moduleId) {
	Activities.expandForumDetails(messageId, forumId, moduleId, expandMessageCB);
}

function expandMessageCB(data) {
	var messageId = 'message' + data.messageId;
	var html = '<tr align="right">' + 
              		'<td colspan="3"><a href="javascript:void(0)" onclick="collapseMessage(' +
              		data.messageId + ',' + data.forumId + ',' + data.moduleId + ');">-</a></td>' + 
          		'</tr>' + 
          		'<tr>' + 
              		'<td colspan="3">' + data.body + '</td>' + 
          		'</tr>' + 
          		'<tr align="right">' + 
              		'<td colspan="3">' + data.author + ', ' + data.dateString + '</td>' + 
          		'</tr>';
	DWRUtil.setValue(messageId, html);	    	    
}

function collapseMessage(messageId, forumId, moduleId) {
	var symbol = '<tr align="right">' + 
              		'<td colspan="3"><a href="javascript:void(0)" onclick="expandMessage(' +
              		messageId + ', ' + forumId + ', ' + moduleId + ')";>+</a></td>' +
          		 '</tr>';
	DWRUtil.setValue('message' + messageId, symbol);
}

/**
PARTE RELATIVA A EDICAO DO MODULO
*/


function retrieveModuleInfo(moduleId) {
	//alert('retrieveModuleInfo');
	if (isDoingSomething == true) {
		alert("Voce esta fazendo edicoes em outro local");
		return;
	}
	Module.retrieveInfo(moduleId, editState);
	isDoingSomething = true;
}
/**M?todo abaixo chamado em fillModuleDataCB() em 
	Module.retrieveInfo(newOrder, editState);
	Fixado com um if
	Verificar efeitos colaterais
*/
function editState(data) {
	//alert('editState');
	if(data!=null){
	var name = data.name;
	var desc = data.description;
	var order = data.order;
	var visible = data.visible;
	var current = data.current;
	currentEditingModule = current;
	
	var cb = {
		callback:function(dat) {
			toEditStateCB(dat, name, desc, visible, order);
		}
	};
	//Activities.init(current, cb);
	ContManag.init(current, cb);
	//Activitiesaa.init(currentEditingModule - 1, cb);
	}
}

//Essa fun??o ? usada quando o professor est? editando o m?dulo

function toEditStateCB(data, name, desc, visible, number) {
	//alert('toEditStateCB');
	//alert('isEditing: ' + isEditing);
	//alert('isDoingSomething: ' + isDoingSomething);
	var modId = "module" + currentEditingModule;
	var nameId = "moduleName" + currentEditingModule;
	var descId = "moduleDescription" + currentEditingModule;
	var visibleId = "moduleVisible" + currentEditingModule;
	var orderId = "moduleNumber" + currentEditingModule;
	var materialsList = "materialsList" + currentEditingModule;
	var activitiesList = "activitiesList" + currentEditingModule;
	var dynamicId = "dynamic" + currentEditingModule;
	var module = '<table class="tableMod"><tr>' +
    				'<td width="50%" class="headTab"><input type="text" name="' + nameId + '" value="' + name + '"/></td>' +
					'<td align="center" class="headTab">Vis&iacute;vel <input name="' + visibleId + '" type="checkbox" ';
	if (visible == true) {
		module += 'checked="checked" ';
	}				
		module += '/></td>' +
				  '<td align="right" class="headTab"><select name="' + orderId + '" onchange="reorderMods(' + currentEditingModule + ');">'; 
		for (var i = 1; i <= modulesCounter; i++) {
			module += '<option ';
			if (currentEditingModule == i) {
				module += 'selected';
			}
			module += ' value="' + i + '"/>' + i;
		}
		module += '</select></td>' +
  				  '</tr>' +
			 	  '<tr>' +
    				'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo:</td>' +
  				  '</tr>' +
				  '<tr>' +
					'<td colspan="3" class="modDescrip"><textarea name="' + descId + '" class="modDescriptTextarea">' + desc + '</textarea></td>' +
				  '</tr>' +
				  '<tr>' +
					'<td class="headTab2">Materiais [<a href="javascript:void(0)" onclick="chooseMaterial();">+</a>]</td>' + 
					'<td colspan="3" class="headTab2">Atividades [<a href="javascript:void(0)" onclick="chooseAct();">+</a>]</td>' +
				  '</tr>' +
			 	  '<tr valign="top">' +
    				'<td>' +
						'<ul id="' + materialsList + '">';
		module += addMaterialFeatures(data.materials);			
		module += 		'</ul></td>' +
    				'<td colspan="3">' +
						'<ul id="' + activitiesList + '">';
		module += addActFeatures(data.activities);
		module +=		'</ul></td>' +
				  '</tr>' +
			 	  '<tr align="right">' +
    				 '<td colspan="3"><div  id="' + dynamicId + '"><a href="javascript:void(0)" onclick="saveModuleInEdit();">Salvar</a>' +
    								 ' / <a href="javascript:void(0)" onclick="cancelModuleInEdit();">Cancelar</a>' +
        					         ' / <a href="javascript:void(0)" onclick="deleteModuleInEdit();">Excluir</a></div></td>' +
  				  '</tr></table>';
  
  	DWRUtil.setValue(modId, module);
  	isEditing = true;
  	
  	
}

function addActFeatures(data) {
	//alert('addActFeatures');
	var options = "";
	//alert(data.length);
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		//alert(act);
		var option = '<li>' + data[i][1];
		if(data[i][2] != 'CourseEvaluation') 
			option += ' [<a href="javascript:void(0)" onclick="Activities.retrieve' + act + '(' + id + ',retrieve' + act + ')">E</a>] ';
		option += ' [<a href="javascript:void(0)" onclick="Activities.delete' + act + '(' + id + ',orgAct)">X</a>]</li>';
		options += option;
	}
	return options;
	
}

function chooseAct() {
	var html = '<td  class="cont-tableinterna" colspan="3">' +
					'<table class="tableinterna">' + 
        				'<tr>' +  
          					'<td colspan="3" class="headTab">Nova Atividade</td>' + 
        				'</tr>' +
        				'<tr>' +  
          					'<td colspan="3"><select id="activities"></select></td>' + 
        				'</tr>' + 
        				'<tr>' + 
          					'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="selectAct()">Selecionar</a>' + 
          					' / <a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a></td>' + 
        				'</tr>' + 
      				'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	listActivities();
}

function selectAct() {
	var selection = DWRUtil.getValue("activities");
	if (selection != "select") {
		eval('actSelected_' + selection + '();');
	}
}

function actSelected_evaluation(){
	isCreatingModule = true;
	activitySelected_evaluation();
}
 
function actSelected_material() {
		var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table  class="tableinterna">' + 
  						'<tr>' +  
    						'<td class="headTab">Entrega de Material</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome do Material:<br /> <input id="name" type="text" /></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Descri&ccedil;&atilde;o do Material:<br /> <textarea id="desc" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Entrega:<br /> <input id="day" type="text" size="2" maxlength="2" />' + 
     						' / <input id="month" type="text" size="2" maxlength="2" />' + 
     						' / <input id="year" type="text" size="4" maxlength="4" /> </td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td><input id="allow" type="checkbox" value="checkbox" checked="checked" />Permitir entregas ap&oacute;s a data.</td>' +
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a> / <a href="javascript:void(0)" onclick="saveMaterialDelivery(-1)">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function actSelected_forum() {
	var html = '<td class="cont-tableinterna" colspan="3" >' + 
					'<table class="tableinterna">' + 
  						'<tr>' +  
    						'<td colspan="3" class="headTab">Forum</td>' + 
  						'</tr>' + 
					  	'<tr>' +  
    						'<td colspan="3">T&oacute;pico do Forum:<br /> <input id="topic" type="text" /></td>' + 
						'</tr>' + 
  						'<tr>' +  
    						'<td colspan="3">Descri&ccedil;&atilde;o do Forum:<br /> <textarea name="description" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a> / <a href="javascript:void(0)" onclick="saveForum(-1)">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function actSelected_poll() {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table class="tableinterna">' + 
						'<tr>' +  
    						'<td class="headTab">Enquete</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome da Enquete:<br /> <input id="name" type="text" /></td>' + 
	 	 				'</tr>' + 
  						'<tr>' +  
    						'<td>Pergunta da Enquete:<br /> <textarea id="question" class="ativDescriptTextarea"></textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Alternativas:' +  
      							'<ul id="poll_alt"></ul>' + 
        						'<ul><li><a href="javascript:void(0)" onclick="addInputField();">Adicionar nova alternativa</a>.</li></ul>' + 
      						'</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Encerramento:<br /> <input id="day" type="text" size="2" maxlength="2" />' + 
    						' / <input id="month" type="text" size="2" maxlength="2" />' + 
    						' / <input id="year" type="text" size="4" maxlength="4" /> </td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td> <a href="javascript:void(0)" onclick="savePoll(-1)">Salvar</a> / <a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	DWRUtil.addOptions("poll_alt", ['<input type="text" id="alt1"> [<a href="javascript:void(0)" onclick="removeInputField(1)">X</a>]']);
	DWRUtil.addOptions("poll_alt", ['<input type="text" id="alt2"> [<a href="javascript:void(0)" onclick="removeInputField(2)">X</a>]']);
	noAlternatives = 2;
}

//Funcao chamada quando a atividade de avaliacao do aluno e executada.


function retrieveMaterialDelivery(data) {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table  class="tableinterna">' + 
  						'<tr>' +  
    						'<td class="headTab">Entrega de Material</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome do Material:<br /> <input id="name" type="text" value="' + data.name + '"/></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Descri&ccedil;&atilde;o do Material:<br /> <textarea id="desc" class="ativDescriptTextarea">' + data.description + '</textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Entrega:<br /> <input id="day" type="text" size="2" maxlength="2" value="' + data.day + '" />' + 
     						' / <input id="month" type="text" size="2" maxlength="2" value="' + data.month + '"/>' + 
     						' / <input id="year" type="text" size="4" maxlength="4" value="' + data.year + '"/> </td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td><input id="allow" type="checkbox" value="checkbox" ';
    if (data.allow == true)			
    	html +=	'checked="checked"';					
    	html += '/>Permitir entregas ap&oacute;s a data.</td>' +
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a> / <a href="javascript:void(0)" onclick="saveMaterialDelivery(' + data.materialId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' +
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function saveMaterialDelivery(materialId) {
	var name	= DWRUtil.getValue("name" );
	var desc	= DWRUtil.getValue("desc" );
	var day		= DWRUtil.getValue("day"  );
	var month	= DWRUtil.getValue("month");
	var year	= DWRUtil.getValue("year" );
	var allow	= DWRUtil.getValue("allow");
	
	if (!isDate(month + '/' + day + '/' + year)) {
		return;
	}
	if (name == '') {
		alert("O nome eh obrigatorio");
		return;
	} else if (desc == '') {
		alert("A descricao eh obrigatoria!");
		return;
	}
	
	Activities.saveMaterial(name, desc, day,
						month, year, allow, materialId, currentEditingModule, 
						orgAct);
}

function retrieveForum(data) {
	var html = '<td class="cont-tableinterna" colspan="3" >' + 
					'<table class="tableinterna">' + 
  						'<tr>' +  
    						'<td colspan="3" class="headTab">Forum</td>' + 
  						'</tr>' + 
					  	'<tr>' +  
    						'<td colspan="3">T&oacute;pico do Forum:<br /> <input id="name" type="text" value="' + data.name + '"/></td>' + 
						'</tr>' +
  						'<tr>' +  
    						'<td colspan="3">Descri&ccedil;&atilde;o do Forum:<br /> <textarea name="desc" class="ativDescriptTextarea">' + data.desc + '</textarea></td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td colspan="3"><a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a> / <a href="javascript:void(0)" onclick="saveEditingForum(' + data.forumId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function saveForum(forumId) {
	var topic = DWRUtil.getValue("topic");
	var desc = trim(DWRUtil.getValue("description"));
	if (topic == '') {
		alert("O topico eh obrigatorio");
	} else if (desc == '') {
		alert("A descricao eh obrigatoria");
	} else { 
		Activities.saveForum(topic, desc, forumId, currentEditingModule, orgAct);
	}
}

function saveEditingForum(forumId) {
	var name = trim(DWRUtil.getValue("name"));
	var desc = trim(DWRUtil.getValue("desc"));
	if (name == '') {
		alert("O topico eh obrigatorio");
	} else if (desc == '') {
		alert("A descricao eh obrigatoria");
	} else { 
		Activities.saveForum(name, desc, forumId, currentEditingModule, orgAct);
	}
}

function retrievePoll(data) {
	var html = '<td  class="cont-tableinterna" colspan="3">' + 
					'<table class="tableinterna">' + 
						'<tr>' +  
    						'<td class="headTab">Enquete</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Nome da Enquete:<br /> <input id="name" type="text" value="' + data.name + '"/></td>' + 
	 	 				'</tr>' + 
  						'<tr>' +  
    						'<td>Pergunta da Enquete:<br /> <textarea id="question" class="ativDescriptTextarea">' + data.question + '</textarea></td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Alternativas:' +  
      							'<ul id="poll_alt"></ul>' + 
        						'<ul><li><a href="javascript:void(0)" onclick="addInputField();">Adicionar nova alternativa</a>.</li></ul>' + 
      						'</td>' + 
  						'</tr>' + 
  						'<tr>' +  
    						'<td>Data de Encerramento:<br /> <input id="day" type="text" size="2" maxlength="2" value="' + data.day + '"/>' + 
    						' / <input id="month" type="text" size="2" maxlength="2" value="' + data.month + '"/>' + 
    						' / <input id="year" type="text" size="4" maxlength="4" value="' + data.year + '"/> </td>' + 
  						'</tr>' + 
  						'<tr align="right">' +  
    						'<td><a href="javascript:void(0)" onclick="cancelOperation()">Cancelar</a> / <a href="javascript:void(0)" onclick="savePoll(' + data.pollId + ')">Salvar</a></td>' + 
  						'</tr>' + 
					'</table>' + 
				'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
	var alts = data.alts;
	for (var i = 0; i < alts.length; i++) {
		DWRUtil.addOptions("poll_alt",
			['<input type="text" id="alt'+(i+1)+'" value="'+alts[i]+'"> [<a href="javascript:void(0)" onclick="removeInputField('+(i+1)+')">X</a>]']);
	}
	noAlternatives = alts.length;
}

function addInputField() {
	noAlternatives += 1;
	var alt = noAlternatives;
	DWRUtil.addOptions("poll_alt",
		['<input type="text" id="alt' + alt + '"> ' +
		 '[<a href="javascript:void(0)" onclick="removeInputField('+alt+')">X</a>]']);
}

function removeInputField(id) {
	if (noAlternatives <= 2) {
		alert('Uma enquete nao pode de maneira alguma ter menos de 2 alternativas');
		return;
	}
	var backup = new Array();
	for (var i = 1, j = 1; i <= noAlternatives; i++) {
		if (i != id) {
			backup[j] = DWRUtil.getValue('alt'+i);
			j++;
		}
	}
	DWRUtil.removeAllOptions('poll_alt');
	for (var i = 1; i < backup.length; i++) {
		DWRUtil.addOptions("poll_alt",
			['<input type="text" id="alt'+i+'" value="'+backup[i]+'"> [<a href="javascript:void(0)" onclick="removeInputField('+i+')">X</a>]']);
	}
	noAlternatives--;
}

function savePoll(pollId) {
	var name		= DWRUtil.getValue("name");
	var question	= DWRUtil.getValue("question");
	var day			= DWRUtil.getValue("day");
	var month		= DWRUtil.getValue("month");
	var year		= DWRUtil.getValue("year");
	
	if (!isDate(month + '/' + day + '/' + year)) {
		return;
	}
	if (name == '') {
		alert("O nome eh obrigatorio");
		return;
	} else if (question == '') {
		alert("A pergunta eh obrigatoria");
		return;
	}		 
	var alternatives = new Array();
	for (var i = 0; i < noAlternatives; i++) {
		alternatives[i] = DWRUtil.getValue("alt" + (i + 1));
		if (alternatives[i] == '') {
			alert("Todas as alternativas devem estar preenchidas");
			return;
		}
	}
	Activities.savePoll(name, question, day, month,
					year, alternatives, pollId, currentEditingModule, 
					orgAct);
}

function orgAct(data) {
	//alert('orgAct');
	//alert(data);
	if (data.length > 0 && data[0][2] == 'Error') {
		showErrors(data);
		return;
	}
	var options = new Array();
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = data[i][1];
		//if (i != (data.length - 1))
		if(act!='CourseEvaluation')
			option += ' [<a href="javascript:void(0)" onclick="Activities.retrieve' + act + '(' + id + ',retrieve' + act + ')">E</a>] ';
		option += ' [<a href="javascript:void(0)" onclick="Activities.delete' + act + '(' + id + ',orgAct)">X</a>]';
		options[i] = option;
	}
	var div = 'activitiesList' + currentEditingModule;
	DWRUtil.removeAllOptions(div);
	DWRUtil.addOptions(div, options);
	cancelOperation();
}

function saveModuleInEdit() {
	var moduleName = "moduleName" + currentEditingModule;
	var moduleDescription = "moduleDescription" + currentEditingModule;
	var moduleVisible = "moduleVisible" + currentEditingModule;
	var moduleNumber = "moduleNumber" + currentEditingModule;
	
	var name    = trim(DWRUtil.getValue(moduleName));
	var desc    = trim(DWRUtil.getValue(moduleDescription));
	var visible = DWRUtil.getValue(moduleVisible);
	var number  = DWRUtil.getValue(moduleNumber);
	
	/*for (var i = 0; i < desc.length; i++) {
		if (desc[i].charCodeAt(0) == 10) {
			var aux = desc.substring(0, i) + ' ' + desc.substring(i+1, desc.length);
			desc = aux;
			alert(desc);
		}
	}*/
	if (name == '') {
		alert("O nome eh obrigatorio!");
	} else if (desc == '') {
		alert("A descricao eh obrigatoria!");
	} else {
		Module.saveModule(name, desc, visible, number, currentEditingModule);
		setTimeout("Module.retrieveInfo(currentEditingModule, editToView)",200);
	}
}
/**
Fun??o que salva um m?dulo rec?m criado, ? chama logo ap?s o m?todo createModule()
previne erros de busca por materiais inexistentes.

*/


function saveJustCreatedModule() {
	//alert('saveJustCreatedModule');
	var moduleName = "moduleName" + currentEditingModule;
	var moduleDescription = "moduleDescription" + currentEditingModule;
	var moduleVisible = "moduleVisible" + currentEditingModule;
	var moduleNumber = "moduleNumber" + currentEditingModule;
	
	var name    = trim(DWRUtil.getValue(moduleName));
	var desc    = trim(DWRUtil.getValue(moduleDescription));
	var visible = DWRUtil.getValue(moduleVisible);
	var number  = DWRUtil.getValue(moduleNumber);
	
	for (var i = 0; i < desc.length; i++) {
		if (desc[i].charCodeAt(0) == 10) {
			var aux = desc.substring(0, i) + ' ' + desc.substring(i+1, desc.length);
			desc = aux;
		}
	}
		Module.saveModule(name, desc, visible, number, currentEditingModule);
}


function cancelOperation() {
	var html =
		'<td colspan="3"><a href="javascript:void(0)" onclick="saveModuleInEdit();">Salvar</a>' +
    				  ' / <a href="javascript:void(0)" onclick="cancelModuleInEdit();">Cancelar</a>' +
        			  ' / <a href="javascript:void(0)" onclick="deleteModuleInEdit();">Excluir</a>' +
        '</td>';
	DWRUtil.setValue('dynamic' + currentEditingModule, html);
}

function cancelModuleInEdit() {
	//alert('eh aki');
	isDeletingWithoutSaving = true;
	Module.retrieveInfo(currentEditingModule, editToView);
}

function deleteModuleInEdit() {
    isFirstTime = true;
    isReOrderingModules = true;
    isDeleting = true;
    Module.reorderModules(modulesCounter, currentEditingModule, fillModulesCB);
}

function deleteMod () {
	Module.deleteModule(modulesCounter);
	var form = '';
	var iIndex = '';
	var fIndex = '';
	
	for (var i = 0; i <= 1; i++) {
		form = DWRUtil.getValue('form');
		iIndex = form.indexOf('<table id="module' + (modulesCounter + i));
		fIndex = form.indexOf('<table id="module', iIndex + 10);
		
		newForm = form.substring(0, iIndex);
		if (fIndex != -1)
			newForm += form.substring(fIndex);
		DWRUtil.setValue("form", newForm);
	}
	isDoingSomething = false;
	modulesCounter--;
	addCreateModuleOption(modulesCounter);
}
/**
M?todo chamado quando um m?dulo sai do modo de edi??o e passa para o modo de visualiza??o.
*/
function editToView(data) {
	if (data == null) {
		//alert("O m&oacute;dulo n&atilde;o foi salvo ainda");
		if (isDeletingWithoutSaving) {
			deleteOrCancelWithoutSaving();
			isDeletingWithoutSaving = false;
		}
		return;
	}
//	alert('editToView');
	isEditing = false;
	var visible = data.visible;
	if ((visible == true) || (isPowerful == true)) {
		var name = data.name;
		var desc = data.description;
		var order = data.order;
		var current = data.current;
		currentEditingModule = current;
		ContManag.init(currentEditingModule - 1);
		//Activitiesaa.init(currentEditingModule - 1);
		var modId = "module" + currentEditingModule;
		var visibleId = "moduleVisible" + currentEditingModule;
		var orderId = "moduleNumber" + currentEditingModule;
		var materialsList = "materialsList" + currentEditingModule;
		var activitiesList = "activitiesList" + currentEditingModule;
		var dynamicId = "dynamic" + currentEditingModule;
		
		var module = '<table class="tableMod"><tr>' +
			     		'<td width="50%" class="headTab">' + name + '</td>' +
				 		'<td align="center" class="headTab">';
		if (visible == true) {
			module += 'Vis&iacute;vel</td>';
		} else {
			module += '<span class="alert">Invis&iacute;vel</span></td>';
		}			  
			module += 	'<td align="right" class="headTab">' + order + '</td>' +
				  	'</tr>' + 
 				  	'<tr>' + 
	  				  	'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo: </td>' +
	 				'</tr>' +
					'<tr>' +
	    			  	'<td colspan="3" class="modDescrip">' + desc + '</td>' +
	  				'</tr>' +
					'<tr>' +
     			  		'<td width="50%" class="headTab2">Materiais</td>' +
				  		'<td colspan="3" class="headTab2">Atividades</td>' +
  				    '</tr>' +
  				  	'<tr valign="top">' + 
			    		'<td><ul id="' + materialsList + '"></ul></td>' +
			    		'<td colspan="3"> <ul id="' + activitiesList + '"></ul></td>' +
			  	  	'</tr>' +
					'<tr align="right">'+
					'<td colspan="3">'+
  					'<div id="' + dynamicId + '">'+
  					'</div>'+
  					'</td>'+
					'</tr></table>';
		DWRUtil.setValue(modId, module);
	  	isReOrderingModules = false;
		Activities.retrieveInfo(currentEditingModule, fillActivitiesDataCB);
		Materials.retrieveInfo(currentEditingModule, fillMaterialsDataCB);
		cancelAction(currentEditingModule);
		addCreateModuleOption(currentEditingModule);
	}
}
/**M?todo utilizado quando um novo m?dulo ? criado.
*/
function createModule() {
	//alert('createModule');
	if (isDoingSomething == true) {
		alert('Existe um modulo em modo de edicao');
		return;
	}
	modulesCounter++;
	currentEditingModule = modulesCounter;
	isDoingSomething = true;
	//Materials.init(currentEditingModule - 1);
	//Activities.init(currentEditingModule - 1);
	ContManag.init(currentEditingModule - 1);
	//Materialsaa.init(currentEditingModule);
	//Activitiesaa.init(currentEditingModule);

	var moduleId = "module" + modulesCounter;
	var moduleName = "moduleName" + modulesCounter;
	var moduleVisible = "moduleVisible" + modulesCounter;
	var moduleNumber = "moduleNumber" + modulesCounter;
	var moduleDescription = "moduleDescription" + modulesCounter;
	var dynamic = "dynamic" + modulesCounter;
	var materialsList = "materialsList" + modulesCounter;
	var activitiesList = "activitiesList" + modulesCounter;
	
	var module = 	'<table class="tableMod"><tr>' +
    					'<td width="50%" class="headTab"><input type="text" name="' + moduleName + '" value="Nome do M&oacute;dulo"/></td>' +
					    '<td align="center" class="headTab">Vis&iacute;vel <input name="' + moduleVisible + '" type="checkbox" checked="checked" /></td>' +
					    '<td align="right" class="headTab"><select name="' + moduleNumber + '" onchange="reorderMods(' + currentEditingModule + ');">';
	for (var i = 1; i <= modulesCounter; i++) {
		module += '<option ';
		if (currentEditingModule == i) {
			module += 'selected';
		}
		module += ' value="' + i + '"/>' + i;
	}				        
		module += '</select></td>' + 
  				    '</tr>' +
			 		'<tr>' +
    					'<td colspan="3">Descri&ccedil;&atilde;o do M&oacute;dulo:</td>' + 
  					'</tr>' + 
					'<tr>' + 
					    '<td colspan="3" class="modDescrip"><textarea name="' + moduleDescription + '" class="modDescriptTextarea">Lorem ipsum sit amet abajour rolis strange text just for fun girls have more than this</textarea></td>' + 
					'</tr>' + 
					'<tr>' + 
					    '<td class="headTab2">Materiais [<a href="javascript:void(0)" onclick="chooseMaterial();">+</a>]</td>' + 
					    '<td colspan="3" class="headTab2">Atividades [<a href="javascript:void(0)" onclick="chooseAct();">+</a>]</td>' + 
					'</tr>' + 
			 		'<tr valign="top">' + 
    					'<td>' + 
							'<ul id="' + materialsList + '"></ul>' + 
						'</td>' + 
    					'<td colspan="3">' + 
							'<ul id="' + activitiesList + '"></ul>' + 
						'</td>' + 
				    '</tr>' + 
			 		'<tr align="right">' +
    					'<td colspan="3">'+
    					'<div id="' + dynamic + '">' + '<a href="javascript:void(0)" onclick="saveModuleInEdit();">Salvar</a>' +
    								 ' / <a href="javascript:void(0)" onclick="cancelModuleInEdit();">Cancelar</a>' + 
        					         ' / <a href="javascript:void(0)" onclick="deleteModuleInEdit();">Excluir</a></div></td>' +
  					'</tr></table>';
	DWRUtil.setValue(moduleId, module);
	saveJustCreatedModule();
}

function addCreateModuleOption(modId) {
	if (isPowerful == true) {
		if (modId == modulesCounter || modulesCounter == 0) {
				var nextModule = "module" + (modulesCounter + 1);
			if ($(nextModule) == null) {
				var create = '<a href="javascript:void(0)" onclick="createModule();">Criar novo modulo</a>';
				var form = DWRUtil.getValue("form");
				form += '<div id="' + nextModule + '" class="tableMod">'+create+'</div>';
				DWRUtil.setValue("form", form);
			}
		}
	}
}
