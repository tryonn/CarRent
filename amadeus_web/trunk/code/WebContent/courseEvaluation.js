function evaluateCourse(currentModule){
	
	if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	Activities.retrieveCourseEvaluation(currentModule, evaluateCourseCB);
	
}

function evaluateCourseCB(data){
	
	var constCriterions = data.constCriterions;
	var hasEvaluated = data.hasEvaluated;
	var profile = data.profile;
	
	
	if(profile == 1){
	
		var html = '<td  class="cont-tableinterna" colspan="3">' + 
						'<div class="tableCourseEvaluation">'+
				   		'<table>' +
		  					'<tr>' + 
	               				'<td class="headTab" colspan="3">Responder Avalia&ccedil;&atilde;o de Curso</td>' +
	          				'</tr>';
	          				
		html += '<tr>'+
				   '<td colspan="3"><b>Como responder ao question&aacute;rio?</b><BR/> </td>' +
				   '</tr>';
		html += '<tr>'+
				  '<td colspan="3">O objetivo deste question&aacute;rio &eacute; fazer uma pesquisa sobre a medida em que o seu processo de aprendizagem neste curso corresponde &agrave;s suas expectativas.</td>'+
			   '</tr>';
	    html += '<tr>'+
	       '<td colspan="3">Cada &iacute;tem abaixo consiste na compara&ccedil;&atilde;o entre as suas expectativas e a sua experi&ecirc;ncia efetiva ao frequentar este curso.<BR/></td>'+
		'</tr>';
		html += '<tr>'+
	       '<td colspan="3">Reflita sobre cada declara&ccedil;&atilde;o e, depois, selecione a op&ccedil;&atilde;o que descreve melhor a frequ&ecirc;ncia em que voc&ecirc; gostaria que as situa&ccedil;&otilde;es descritas ocorressem neste curso (frequ&ecirc;ncia desejada) e a frequ&ecirc;ncia em que as situa&ccedil;&otilde;es descritas ocorrem atualmente neste curso (frequ&ecirc;ncia atual).<BR/></td>'+
		'</tr>';
		html += '<tr>'+
	       '<td colspan="3">N&atilde;o h&aacute; respostas certas ou erradas; n&oacute;s estamos interessados apenas na sua opini&atilde;o. Todas as respostas dadas s&atilde;o confidenciais e n&atilde;o t&ecirc;m nenhum impacto sobre a avalia&ccedil;&atilde;o do participante.<BR/></td>'+
		'</tr>';
		html += '<tr>'+
	       '<td colspan="3">Agradecemos a sua colabora&ccedil;&atilde;o.<BR/></td>'+
		'</tr>';
		
		for (var i = 0; i < constCriterions.length; i++) {
	    	if(constCriterions[i] == 0)
	    	html += relevanceQuestions();
	    	if(constCriterions[i] == 1)
	    	html += reflectionQuestions();
	    	if(constCriterions[i] == 2)
	    	html += interactionQuestions();
	    	if(constCriterions[i] == 3)
	    	html += tutorsQuestions();
	    	if(constCriterions[i] == 4)
	    	html += classmatesQuestions();
	    	if(constCriterions[i] == 5)
	    	html += compreensionQuestions();
	    }
	    html += '<tr>'+
	           	'<td colspan="3">'+
	            'Deseja fazer algum comentario?'+
	            '<br/>'+
	           	'<textarea name="textareaComments" class="courseEvaluationComments"></textarea>'+
	           	'</td>'+
	        	'</tr>'+
	       		'<tr align="right">' +
	    		'<td colspan="3" ><a href="javascript:void(0)" onclick="cancelAction(' + data.module + ')">Cancelar</a>' +
	    		' / <a href="javascript:void(0)" onclick="replyEvaluation(' + data.module + ')">Enviar</a></td>' +
	    		'</tr>' +
				'</table>'+
				'</div>'+
				'</td>';
	}
	if(profile == 0 || profile == 2 || profile == 3){
		var html = '<td  class="cont-tableinterna" colspan="3">' + 
				   		'<div class="tableCourseEvaluation">'+
				   		'<table>' +
		  					'<tr>' + 
	               				'<td class="headTab" colspan="3">Resultado da Avalia&ccedil;&atilde;o de Curso</td>' +
	          				'</tr>';
	          				
	    for (var i = 0; i < constCriterions.length; i++) {
	    	if(constCriterions[i] == 0)
	    	html += relevanceQuestionsProfessor(data.crit0question0, data.crit0question1,data.crit0question2,data.crit0question3);
	    	if(constCriterions[i] == 1)
	    	html += reflectionQuestionsProfessor(data.crit1question0, data.crit1question1,data.crit1question2,data.crit1question3);
	    	if(constCriterions[i] == 2)
	    	html += interactionQuestionsProfessor(data.crit2question0, data.crit2question1,data.crit2question2,data.crit2question3);
	    	if(constCriterions[i] == 3)
	    	html += tutorsQuestionsProfessor(data.crit3question0, data.crit3question1,data.crit3question2,data.crit3question3);
	    	if(constCriterions[i] == 4)
	    	html += classmatesQuestionsProfessor(data.crit4question0, data.crit4question1,data.crit4question2,data.crit4question3);
	    	if(constCriterions[i] == 5)
	    	html += compreensionQuestionsProfessor(data.crit5question0, data.crit5question1,data.crit5question2,data.crit5question3);
	    }
	          			
	    html += '<tr align="right">' +
	    		'<td colspan="3" ><a href="javascript:void(0)" onclick="cancelAction(' + data.module + ')">Cancelar</a>' +
	    		'</td>' +
	    		'</tr>' +
				'</table>'+
				'</div>'+
				'</td>';
	          				
	    
	}
	
	DWRUtil.setValue('dynamic'+data.module, html);
}

function evaluationDone(data){
	cancelActionCB(data);
	Activities.retrieveInfo(data, fillActivitiesDataCB);
}

function replyEvaluation(moduleId){
var cb = {
		callback:function(data) {
			replyEvaluationCB(moduleId, data);
		}
	};
	Activities.retrieveCourseEvaluation(moduleId, cb);
}

function replyEvaluationCB(moduleId, data){
	var currentFrequence, desiredFrequency;
	var constCriterions = data.constCriterions;
	var answers = new Array(constCriterions.length)
	var string = '';
	var commentary = '';
	    			
	for (var i = 0; i < constCriterions.length; i++) {
    	if(constCriterions[i] == 0){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('relevance1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('relevance1' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string+= '_';
	    	
	    	currentFrequence = DWRUtil.getValue('relevance2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('relevance2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string+= '_';
	    	
	    	currentFrequence = DWRUtil.getValue('relevance3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('relevance3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('relevance4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('relevance4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Relevancia.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    		    	
	    	answers[i] = string;
	    }
    	if(constCriterions[i] == 1){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('reflection1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('reflection1' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('reflection2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('reflection2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('reflection3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('reflection3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('reflection4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    		    	
	    	desiredFrequency = DWRUtil.getValue('reflection4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Reflexao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    		    	
	    	answers[i] = string;
	    }
    	if(constCriterions[i] == 2){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('interaction1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('interaction1' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('interaction2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('interaction2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('interaction3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('interaction3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('interaction4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('interaction4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Interacao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';   	
	    	
	    	answers[i] = string;
    	}
    	if(constCriterions[i] == 3){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('tutor1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('tutor1' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('tutor2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('tutor2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('tutor3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('tutor3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('tutor4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('tutor4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Apoio dos Tutores.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	answers[i] = string;
    	}
    	if(constCriterions[i] == 4){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('cole1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('cole1' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('cole2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('cole2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('cole3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('cole3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('cole4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('cole4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Apoio dos Colegas.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	string += '_';
	    	
	    	answers[i] = string;
    	}
    	if(constCriterions[i] == 5){
	    	string = '_';
	    	currentFrequence = DWRUtil.getValue('compreension1' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('compreension1' + 2);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a primeira pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('compreension2' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('compreension2' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a segunda pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('compreension3' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('compreension3' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a terceira pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	currentFrequence = DWRUtil.getValue('compreension4' + 1);
	    	if(currentFrequence.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += currentFrequence;
	    	
	    	desiredFrequency = DWRUtil.getValue('compreension4' + 2);
	    	if(desiredFrequency.toString() == 'false'){
	    		alert('Falta responder a quarta pergunta do criterio Compreensao.');
	    		return;
	    	}
	    	string += desiredFrequency;
	    	
	    	string += '_';
	    	
	    	answers[i] = string;
	    }
	    
    }
    commentary = trim(DWRUtil.getValue('textareaComments'));
    Activities.replyCourseEvaluation(moduleId, commentary, answers, evaluationDone);    
    
}

function courseEvaluationHeader() {
	var html = '<tr>' + 
  					'<td colspan="3" class="headTab">Avaliacao de Curso</td>' +
				'</tr>' +
		        '<tr>' +
		          '<td colspan="3">Selecione os criterios para avaliar o Curso.</td>' +
		        '</tr>' +
		        '<tr>' +
		          '<td colspan="3">(Minimo de 3 criterios)</td>' +
		        '</tr>';
	return html;	        
}

function resultsQuestionsGen(data){
	var html =	'<tr>'+
					'<td>'+
						'Frequ&ecirc;ncia Atual <BR/>'+
     					data[0][0].toFixed(2)+'% Quase Nunca<BR/>'+
          				data[0][1].toFixed(2)+'% Raramente<BR/>'+
		               	data[0][2].toFixed(2)+'% Algumas Vezes<BR/>'+
               			data[0][3].toFixed(2)+'% Frequentemente<BR/>'+
               			data[0][4].toFixed(2)+'% Quase sempre<BR/>'+
     				'</td>'+
		     		'<td>'+
     				    'Frequ&ecirc;ncia Desejada <br/>'+
         				data[1][0].toFixed(2)+'% Quase Nunca<BR/>'+
          				data[1][1].toFixed(2)+'% Raramente<BR/>'+
		               	data[1][2].toFixed(2)+'% Algumas Vezes<BR/>'+
               			data[1][3].toFixed(2)+'% Frequentemente<BR/>'+
               			data[1][4].toFixed(2)+'% Quase sempre<BR/>'+
     					'</td>'+
 				'</tr>';
	return html;
}

function questionsGen(val){
	var html =	'<tr>'+
					'<td>'+
					'Frequ&ecirc;ncia Atual <BR/>'+
     					'<input type="radio" name="'+ val +'1" value="0">Quase Nunca<BR/>'+
          				'<input type="radio" name="'+ val +'1" value="1">Raramente<BR/>'+
		               	'<input type="radio" name="'+ val +'1" value="2">Algumas Vezes<BR/>'+
               			'<input type="radio" name="'+ val +'1" value="3">Frequentemente<BR/>'+
               			'<input type="radio" name="'+ val +'1" value="4">Quase sempre<BR/>'+
     				'</td>'+
		     		'<td>'+
     				'Frequ&ecirc;ncia Desejada <br/>'+
         				'<input type="radio" name="'+ val +'2" value="0">Quase Nunca<BR/>'+
          				'<input type="radio" name="'+ val +'2" value="1">Raramente<BR/>'+
               			'<input type="radio" name="'+ val +'2" value="2">Algumas Vezes<BR/>'+
               			'<input type="radio" name="'+ val +'2" value="3">Frequentemente<BR/>'+
               			'<input type="radio" name="'+ val +'2" value="4">Quase sempre<BR/>'+
     					'</td>'+
 				'</tr>';
	return html;
}

function classmatesQuestions(){
	var html = 	'<tr>'+
 			   		'<td><b>Apoio aos Colegas<b><BR/> </td>'+
 				'</tr>'+
				'<tr>'+
 					'<td colspan="2">Os outros participantes me encorajam a participar?</td>'+
				'</tr>';
		html+= questionsGen('cole1');
		html+= '<tr>'+
				 '<td colspan="2">Os outros participantes elogiam as minhas contribui&ccedil;&otilde;es?</td></tr>';
		html+= questionsGen('cole2');
		html+= '<tr>'+
 					'<td colspan="2">Os outros participantes estimam as minhas contribui&ccedil;&otilde;es?</td></tr>';
		html+= questionsGen('cole3'); 
   		html+= '<tr>'+
					'<td colspan="2">Os outros participantes demonstram empatia quando me esfor&ccedil;o para aprender?</td></tr>';
		html+= questionsGen('cole4');
		return html;
}

function tutorsQuestions(){
	var html= '<tr>'+
 				'<td><b>Apoio aos Tutores<b><BR/> </td>'+
 			'</tr>'+
  			'<tr>'+
 				'<td colspan="2">O tutor me estimula a refletir?</td> </tr>';
 	html+= questionsGen('tutor1');
 	html+= '<tr>'+
 				'<td colspan="2">O tutor me encoraja a participar?</td></tr>';
	html+= questionsGen('tutor2');
	html+= '<tr>'+
 				'<td colspan="2">O tutor ajuda a melhorar a qualidade dos discursos?</td></tr>';
	html+= questionsGen('tutor3');
	html+= '<tr>'+
				'<td colspan="2">O tutor ajuda a melhorar o processo de reflex&atilde;o auto-cr&iacute;tica?</td></tr>'
 	html+= questionsGen('tutor4');
 	return html;
}

function compreensionQuestions(){
	var html = '<tr>'+
					'<td><b>Compreens&atilde;o<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu compreendo bem as mensagens dos outros participantes?</td> </tr>';
 	html+= questionsGen('compreension1');
 	html+= '<tr>'+
				'<td colspan="2">Os outros participantes compreendem bem as minhas mensagens?</td></tr>';
	html+= questionsGen('compreension2');
	html+= '<tr>'+
 				'<td colspan="2">Eu compreendo bem as mensagens do tutor?</td></tr>';
	html+= questionsGen('compreension3'); 
  	html+= '<tr>'+
				'<td colspan="2">O tutor compreende bem as minhas mensagens?</td></tr>';
 	html+= questionsGen('compreension4');	
	return html;
}
 
function interactionQuestions(){
	var html = '<tr>'+
 					'<td><b>Intera&ccedil;&atilde;o<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu explico as minhas id&eacute;ias aos outros participantes?</td> </tr>';
 		html+= questionsGen('interaction1');	
     	html+= '<tr>'+
 				'<td colspan="2">Pe&ccedil;o aos outros alunos explica&ccedil;&otilde;es sobre as id&eacute;ias deles?</td></tr>';
 		html+= questionsGen('interaction2');	
		html+= '<tr>'+
				 '<td colspan="2">Os outros participantes me pedem explica&ccedil;&otilde;es sobre as minhas id&eacute;ias?</td></tr>';
 		html+= questionsGen('interaction3');
 		html+= '<tr>'+
					'<td colspan="2">Os outros participantes reagem &agrave;s minhas id&eacute;ias?</td></tr>';
 		html+= questionsGen('interaction4');
 	return html;
}

function reflectionQuestions(){
	var html = '<tr>'+
 					'<td><b>Reflex&atilde;o Cr&iacute;tica<b><BR/> </td>'+
 				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu reflito sobre como eu aprendo?</td> </tr>';
		html+= questionsGen('reflection1');
    	html+= '<tr>'+
					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre as minhas pr&oacute;prias id&eacute;ias?</td></tr>';
 		html+= questionsGen('reflection2');
		html+= '<tr>'+
 					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre as id&eacute;ias dos outros participantes?</td></tr>';
 		html+= questionsGen('reflection3'); 
  		html+= '<tr>'+
					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre os conte&uacute;dos do curso?</td></tr>';
		html+= questionsGen('reflection4');
		return html;
 }
 
function relevanceQuestions(){
	var html = '<tr>'+
					'<td><b>Relev&acirc;ncia<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">A minha aprendizagem &eacute; focalizada em assuntos que me interessam?</td> </tr>';
 		html+= questionsGen('relevance1');
		html+= '<tr>'+
					'<td colspan="2">O que eu estou aprendendo &eacute; importante para a pr&aacute;tica da minha profiss&atilde;o?</td></tr>';
		html+= questionsGen('relevance2');
		html+= '<tr>'+
 					'<td colspan="2">Eu aprendo como fazer para melhorar o meu desempenho profissional?</td></tr>';
		html+= questionsGen('relevance3');
		html+= '<tr>'+
					'<td colspan="2">O que eu aprendo tem boas conex&otilde;es com a minha atividade profissional?</td></tr>';
		html+= questionsGen('relevance4');
		return html;
} 







function classmatesQuestionsProfessor(data1, data2, data3, data4){
	var html = 	'<tr>'+
 			   		'<td><b>Apoio aos Colegas<b><BR/> </td>'+
 				'</tr>'+
				'<tr>'+
 					'<td colspan="2">Os outros participantes me encorajam a participar?</td>'+
				'</tr>';
		html+= resultsQuestionsGen(data1);
		html+= '<tr>'+
				 '<td colspan="2">Os outros participantes elogiam as minhas contribui&ccedil;&otilde;es?</td></tr>';
		html+= resultsQuestionsGen(data2);
		html+= '<tr>'+
 					'<td colspan="2">Os outros participantes estimam as minhas contribui&ccedil;&otilde;es?</td></tr>';
		html+= resultsQuestionsGen(data3); 
   		html+= '<tr>'+
					'<td colspan="2">Os outros participantes demonstram empatia quando me esfor&ccedil;o para aprender?</td></tr>';
		html+= resultsQuestionsGen(data4);
		return html;
}

function tutorsQuestionsProfessor(data1, data2, data3, data4){
	var html= '<tr>'+
 				'<td><b>Apoio aos Tutores<b><BR/> </td>'+
 			'</tr>'+
  			'<tr>'+
 				'<td colspan="2">O tutor me estimula a refletir?</td> </tr>';
 	html+= resultsQuestionsGen(data1);
 	html+= '<tr>'+
 				'<td colspan="2">O tutor me encoraja a participar?</td></tr>';
	html+= resultsQuestionsGen(data2);
	html+= '<tr>'+
 				'<td colspan="2">O tutor ajuda a melhorar a qualidade dos discursos?</td></tr>';
	html+= resultsQuestionsGen(data3);
	html+= '<tr>'+
				'<td colspan="2">O tutor ajuda a melhorar o processo de reflex&atilde;o auto-cr&iacute;tica?</td></tr>'
 	html+= resultsQuestionsGen(data4);
 	return html;
}

function compreensionQuestionsProfessor(data1, data2, data3, data4){
	var html = '<tr>'+
					'<td><b>Compreens&atilde;o<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu compreendo bem as mensagens dos outros participantes?</td> </tr>';
 	html+= resultsQuestionsGen(data1);
 	html+= '<tr>'+
				'<td colspan="2">Os outros participantes compreendem bem as minhas mensagens?</td></tr>';
	html+= resultsQuestionsGen(data2);
	html+= '<tr>'+
 				'<td colspan="2">Eu compreendo bem as mensagens do tutor?</td></tr>';
	html+= resultsQuestionsGen(data3);
  	html+= '<tr>'+
				'<td colspan="2">O tutor compreende bem as minhas mensagens?</td></tr>';
 	html+= resultsQuestionsGen(data4);
	return html;
}
 
function interactionQuestionsProfessor(data1, data2, data3, data4){
	var html = '<tr>'+
 					'<td><b>Intera&ccedil;&atilde;o<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu explico as minhas id&eacute;ias aos outros participantes?</td> </tr>';
 		html+= resultsQuestionsGen(data1);	
     	html+= '<tr>'+
 				'<td colspan="2">Pe&ccedil;o aos outros alunos explica&ccedil;&otilde;es sobre as id&eacute;ias deles?</td></tr>';
 		html+= resultsQuestionsGen(data2);	
		html+= '<tr>'+
				 '<td colspan="2">Os outros participantes me pedem explica&ccedil;&otilde;es sobre as minhas id&eacute;ias?</td></tr>';
 		html+= resultsQuestionsGen(data3);
 		html+= '<tr>'+
					'<td colspan="2">Os outros participantes reagem &agrave;s minhas id&eacute;ias?</td></tr>';
 		html+= resultsQuestionsGen(data4);
 	return html;
}

function reflectionQuestionsProfessor(data1, data2, data3, data4){
	var html = '<tr>'+
 					'<td><b>Reflex&atilde;o Cr&iacute;tica<b><BR/> </td>'+
 				'</tr>'+
				'<tr>'+
					'<td colspan="2">Eu reflito sobre como eu aprendo?</td> </tr>';
		html+= resultsQuestionsGen(data1);
    	html+= '<tr>'+
					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre as minhas pr&oacute;prias id&eacute;ias?</td></tr>';
 		html+= resultsQuestionsGen(data2);
		html+= '<tr>'+
 					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre as id&eacute;ias dos outros participantes?</td></tr>';
 		html+= resultsQuestionsGen(data3); 
  		html+= '<tr>'+
					'<td colspan="2">Fa&ccedil;o reflex&otilde;es cr&iacute;ticas sobre os conte&uacute;dos do curso?</td></tr>';
		html+= resultsQuestionsGen(data4);
		return html;
 }
 
function relevanceQuestionsProfessor(data1, data2, data3, data4){
	var html = '<tr>'+
					'<td><b>Relev&acirc;ncia<b><BR/> </td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="2">A minha aprendizagem &eacute; focalizada em assuntos que me interessam?</td> </tr>';
 		html+= resultsQuestionsGen(data1);
		html+= '<tr>'+
					'<td colspan="2">O que eu estou aprendendo &eacute; importante para a pr&aacute;tica da minha profiss&atilde;o?</td></tr>';
		html+= resultsQuestionsGen(data2);
		html+= '<tr>'+
 					'<td colspan="2">Eu aprendo como fazer para melhorar o meu desempenho profissional?</td></tr>';
		html+= resultsQuestionsGen(data3);
		html+= '<tr>'+
					'<td colspan="2">O que eu aprendo tem boas conex&otilde;es com a minha atividade profissional?</td></tr>';
		html+= resultsQuestionsGen(data4);
		return html;
}