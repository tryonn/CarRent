var nomultAlternatives = 2;
var evalId = 0;
var expanded = -1;



//Visualiza a avalia??o sem estar no modulo de edi??o. Usado por professor e aluno
function viewStudentEvaluation(evaluationId, currentModule){
	//alert("viewStudentEvaluation");
	if (isDoingSomething == true) {
		alert("Voce ja esta fazendo alguma coisa em algum outro lugar");
		return;
	}
	isDoingSomething = true;
	Activities.retrieveStudentEval(evaluationId, currentModule, viewStudentEvaluationCB);
}

//Visualiza a avalia??o sem estar no modulo de edi??o. Usado por professor e aluno
function viewStudentEvaluationCB(data){
	
	var html = '<td  class="cont-tableinterna" colspan="3">' +
			'<table class="tableinterna">' +
		  		'<tr>' +
	            	'<td colspan="3" class="headTab"></td>' +
	           	'</tr>' +
	           	'<tr>' +
	               '<td colspan="2">'+data.name+'</td>' +
	            '</tr>' +
	            '<tr>' +
	                  '<td colspan="1">Per&iacute;do de Aplica&ccedil;&atilde;o</td>' +
	                  '<td>Valor da Avalia&ccedil;&atilde;o</td>' +
	            '</tr>' +
	            '<tr>' +
	                  '<td colspan="1">&nbsp;In&iacute;cio</td>' +
	                  '<td>&nbsp;Peso&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+data.score+'</td>' +
	            '</tr>' +
	            '<tr>' +
	            	  '<td>&nbsp;&nbsp;&nbsp;'+data.startDate+'&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;'+data.startTime+'</td><td>&nbsp;</td>' +
	 	        '</tr>' +
	            '<tr>' +
	                     '<td colspan="1">&nbsp;T&eacute;rmino</td>' +
	                     '<td>&nbsp;Nota M&aacute;xima&nbsp;'+data.maxNote+'</td>' +
	    	    '</tr>' +
	          	'<tr>' +
	                  	'<td>&nbsp;&nbsp;&nbsp;'+data.finishDate+'&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;'+data.finishTime+'</td><td>&nbsp;</td>' +
	          	'</tr>';
	          	
	   if(isPowerful == true){       	
	          	if(data.hasQuestions == false){
			         html+= 	
			          	'<tr>'+
							'<td colspan="3">'+
						    	'<table name="addQuestion" class="addQuestions">'+
						        	'<tr>'+
						            	'<td colspan="3">'+
						                	'<table id="addQuestion" class="addQuestions">'+
						                    	'<tr>'+
						                        	'<td><B>Quest&otilde;es</B></td>'+
						                        '</tr>'+
						                        '<tr>'+
						                        	'<td colspan="3" align="center">Ainda n&atilde;o h&aacute; quest&otilde;es nesta avalia&ccedil;&atilde;o</td>'+
						                        '</tr>'+
						                    '</table>'+
						                '</td>'+
						             '</tr>'+
						         '</table>'+
						    '</td>'+
					    '</tr>';
	          	}
	          	else{
			      html += '<tr>'+
						      '<td colspan="3">'+
	          					'<table name="addQuestion" class="addQuestions">'+
					                '<tr>'+
	  	                				'<td colspan="3"><B>Quest&otilde;es</B></td>'+
	
	                				'</tr>'+
	                				'<tr>'+
	                					'<td colspan="3" class="cont-tableinterna">';
	                					
	                					
	                					//colocar id nas tables para poder funcionar mais facilmente o [+]
			    	for(var i = 0; i< data.questions.length; i++){
				    	var questionText = data.questions[i][0];
				    	html += '<table id="question'+data.questions[i][2]+'"class="tableinterna2">'+
	                				'<tr>'+
	                					'<td>'+
	                  						'Quest&atilde;o '+(i+1)+'('+questionText.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.questions[i][2]+', previewChoser)">+</a>]</td>' +
	                  				'</tr>'+
	                 			'</table>';
					}
					html+= '</td>'+
						'</tr>'+
						'</table>'+
						'</td>'+
						'</tr>';
		    
		    	}
		    	
		    	////Responder questoes do Aluno============
	   }else{
			
			if(data.hasQuestions == false){
			         html+= 	
			          	'<tr>'+
							'<td colspan="3">'+
						    	'<table name="addQuestion" class="addQuestions">'+
						        	'<tr>'+
						            	'<td colspan="3">'+
						                	'<table id="addQuestion" class="addQuestions">'+
						                    	'<tr>'+
						                        	'<td><B>Quest&otilde;es</B></td>'+
						                        '</tr>'+
						                        '<tr>'+
						                        	'<td colspan="3" align="center">Ainda n&atilde;o h&aacute; quest&otilde;es nesta avalia&ccedil;&atilde;o</td>'+
						                        '</tr>'+
						                    '</table>'+
						                '</td>'+
						             '</tr>'+
						         '</table>'+
						    '</td>'+
					    '</tr>';
	          	}
	          	else{
			      html += '<tr>'+
						      '<td colspan="3">'+
	          					'<table name="addQuestion" class="addQuestions">'+
					                '<tr>'+
	  	                				'<td colspan="3"><B>Quest&otilde;es</B></td>'+
	
	                				'</tr>'+
	                				'<tr>'+
	                					'<td colspan="3" class="cont-tableinterna">';
	                					
	                					
	                					//colocar id nas tables para poder funcionar mais facilmente o [+]
			    	for(var i = 0; i< data.questions.length; i++){
				    	var questionText = data.questions[i][0];
				    	html += '<table id="question'+data.questions[i][2]+'"class="tableinterna2">'+
	                				'<tr>'+
	                					'<td>'+
	                  						'Quest&atilde;o '+(i+1)+'('+questionText.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.questions[i][2]+', answerChoser)">+</a>]</td>' +
	                  				'</tr>'+
	                 			'</table>';
					}
					html+= '</td>'+
						'</tr>'+
						'</table>'+
						'</td>'+
						'</tr>';
		    
		    	}
			
			
			
			}
	          	html+=        	           
                '<tr>' +
	                  	'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="cancelAction('+data.module+')">Cancelar</a></td>' +
	          	'</tr>' +	
			'</table>' + 
		   '</td>';
			
		
	DWRUtil.setValue('dynamic'+data.module, html);
}
//funcao acionada quando se cria uma nova 
function actSelected_studentEvaluation() {
	var html = 
	'<td  class="cont-tableinterna" colspan="3">' +
		'<table class="tableinterna">' +
	  		'<tr>' +
            	'<td colspan="3" class="headTab"></td>' +
           	'</tr>' +
           	'<tr>' +
               '<td colspan="2"><input type="text" name="evaluationName"  size="35" value ="Nome da Avalia&ccedil;&atilde;o"/></td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="1">Per&iacute;do de Aplica&ccedil;&atilde;o</td>' +
                  '<td>Valor da Avalia&ccedil;&atilde;o</td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="">&nbsp;In&iacute;cio</td>' +
                  '<td>&nbsp;Peso&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="score" type="text" size="2" maxlength="2" value = ""/></td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="2">&nbsp;&nbsp;&nbsp;<input id="startDay" type="text" size="2" maxlength="2" />' + 
    						'/<input id="startMonth" type="text" size="2" maxlength="2" />' + 
    						'/<input id="startYear" type="text" size="4" maxlength="4" />'+
    					' - <input name="startTime" type="text" size="5" maxlength="5" value = "hh:mm"/></td>' +
 	        '</tr>' +
            '<tr>' +
                     '<td colspan="1">&nbsp;T&eacute;rmino</td>' +
                     '<td>&nbsp;Nota M&aacute;xima&nbsp;<input name="maxNote" type="text" size="2" maxlength="2" value = ""/></td>' +
    	    '</tr>' +
          	'<tr>' +
                  	 '<td colspan="2">&nbsp;&nbsp;&nbsp;<input id="finishDay" type="text" size="2" maxlength="2" />' + 
    						'/<input id="finishMonth" type="text" size="2" maxlength="2" />' + 
    						'/<input id="finishYear" type="text" size="4" maxlength="4" />'+
    					' - <input name="finishTime" type="text" size="5" maxlength="5" value = "hh:mm"/></td>' +
 	        
          	'</tr>' +
          	'<tr>' +
                  	'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveStudentEvaluation(-1)">Salvar</a>/<a href="javascript:void(0)" onclick="cancel()">Cancelar</a></td>' +
          	'</tr>' +
		'</table>' + 
	'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}


//Salva a avaliacao no banco
function saveStudentEvaluation(evaluationId){
	var name		= DWRUtil.getValue("evaluationName");
	var score	    = DWRUtil.getValue("score");
	var startDay	= DWRUtil.getValue("startDay");
	var startMonth	= DWRUtil.getValue("startMonth");
	var startYear	= DWRUtil.getValue("startYear");
	var startTime	= DWRUtil.getValue("startTime");
	var maxNote		= DWRUtil.getValue("maxNote");
	var finishDay	= DWRUtil.getValue("finishDay");
	var finishMonth	= DWRUtil.getValue("finishMonth");
	var finishYear	= DWRUtil.getValue("finishYear");
	var finishTime	= DWRUtil.getValue("finishTime");
		
	if (name == '') {
		alert("O nome da avaliacao e obrigatorio");
		return;
	} else if (score == '') {
		alert("O peso da avaliacao e obrigatorio");
		return;
	} else if (!isInteger(score)){
	 	alert("O peso da avaliacao e invalido");
		return;	
	} else if (!isDate(startMonth + '/' + startDay + '/' + startYear)) {
		return;
	} else if (!isHour(startTime)) {
		return;
	} else if (maxNote == '') {
		alert("A nota maxima da avaliacao e obrigatoria");
		return;
	} else if (!isInteger(maxNote)){
	 	alert("A nota maxima da avaliacao e invalida");
		return;	
	} else if (!isDate(finishMonth + '/' + finishDay + '/' + finishYear)) {
		return;
	} else if (!isHour(finishTime)) {
		return;
	} 		 
	
	var startDate = startDay + '/' + startMonth + '/' + startYear;
	var finishDate = finishDay + '/' + finishMonth + '/' + finishYear;
	
	Activities.saveStudentEvaluation(name, score, startDate, 
			startTime,maxNote, finishDate, finishTime, evaluationId, currentEditingModule, 
					addEvaluationQuestions);
}

////tela de adi??o de questoes, chamada apenas na hora de salvar a avaliacao;
function addEvaluationQuestions(data){
	//alert('addEvaluationQuestions');
	if (data.length > 0 && data[0][2] == 'Error') {
		showErrors(data);
		return;
	}
	
	var evaluationId;
	for (var i = 0; i < data.length; i++) {
		var id = data[i][0];
		var act = data[i][2];
		var option = data[i][1];
		if(act =='StudentEvaluation')
			evaluationId = id;
	}
	//alert(evaluationId);
	Activities.retrieveStudentEvaluation(evaluationId, retrieveStudentEvaluation);
}

//tela de adicao de questoes
function addQuestions(evaluationId){
	Activities.retrieveStudentEvaluation(evaluationId, retrieveStudentEvaluation);
}

//Edita as informacoes da avaliacao
function editEvaluation(evaluationId){
 Activities.retrieveStudentEvaluation(evaluationId, editEvaluationCB);
}

//Funcao que edita o cabe?alho da avaliacao
function editEvaluationCB(data){
	var html = '<td  class="cont-tableinterna" colspan="3">' +
		'<table class="tableinterna">' +
	  		'<tr>' +
            	'<td colspan="3" class="headTab"></td>' +
           	'</tr>' +
           	'<tr>' +
               '<td colspan="2"><input type="text" name="evaluationName"  size="35" value ="'+data.name+'"/></td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="1">Per&iacute;do de Aplica&ccedil;&atilde;o</td>' +
                  '<td>Valor da Avalia&ccedil;&atilde;o</td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="1">&nbsp;In&iacute;cio</td>' +
                  '<td>&nbsp;Peso&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="score" type="text" size="2" maxlength="2" value = "'+data.score+'"/></td>' +
            '</tr>' +
            '<tr>' +
            	 '<td colspan="2">&nbsp;&nbsp;&nbsp;<input id="startDay" type="text" size="2" maxlength="2" value='+data.startDate.substring(0,2)+'  />' + 
    						'/<input id="startMonth" type="text" size="2" maxlength="2" value='+data.startDate.substring(3,5)+' />' + 
    						'/<input id="startYear" type="text" size="4" maxlength="4" value='+data.startDate.substring(6,10)+'/>'+
    					' - <input name="startTime" type="text" size="5" maxlength="5" value = '+data.startTime+'/></td>' +
 	        '</tr>' +
            '<tr>' +
                     '<td colspan="1">&nbsp;T&eacute;rmino</td>' +
                     '<td>&nbsp;Nota M&aacute;xima&nbsp;<input name="maxNote" type="text" size="2" maxlength="2" value = "'+data.maxNote+'"/></td>' +
    	    '</tr>' +
          	'<tr>' +
            	 '<td colspan="2">&nbsp;&nbsp;&nbsp;<input id="finishDay" type="text" size="2" maxlength="2" value='+data.finishDate.substring(0,2)+'  />' + 
    						'/<input id="finishMonth" type="text" size="2" maxlength="2" value='+data.finishDate.substring(3,5)+' />' + 
    						'/<input id="finishYear" type="text" size="4" maxlength="4" value='+data.finishDate.substring(6,10)+'/>'+
    					' - <input name="finishTime" type="text" size="5" maxlength="5" value = '+data.finishTime+'/></td>' +
 	        '</tr>' +
          	'<tr>' +
                  	'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveStudentEvaluation('+data.evaluationId+')">Salvar</a>/<a href="javascript:void(0)" onclick="cancel()">Cancelar</a></td>' +
          	'</tr>';
          	
          	if(data.hasQuestions == false){
	         html+= 	
	          	'<tr>'+
					'<td colspan="3">'+
				    	'<table name="addQuestion" class="addQuestions">'+
				        	'<tr>'+
				            	'<td colspan="3">'+
				                	'<table id="addQuestion" class="addQuestions">'+
				                    	'<tr>'+
				                        	'<td><B>Quest&otilde;es</B></td>'+
				                        '</tr>'+
				                        '<tr>'+
				                        	'<td colspan="3" align="center">Ainda n&atilde;o h&aacute; quest&otilde;es nesta avalia&ccedil;&atilde;o</td>'+
				                        '</tr>'+
				                        '<tr>'+
				                            '<td><B>Adicionar Quest&otilde;es<B></td>'+
				                        '</tr>'+
				                            '<tr>'+
				                        '<td colspan="3" align="right"><input type="button" value="Nova quest&atilde;o" onclick="chooseQuestion('+data.evaluationId+')"/>&nbsp;&nbsp;<input type="button" value="Banco de Quest&otilde;es"/></td>'+
				                        	'</tr>'+
				                    '</table>'+
				                '</td>'+
				             '</tr>'+
						'</table>'+
				    '</td>'+
			    '</tr>'+
			    
			    '<tr>' +
	           		'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a></td>' +
	        	'</tr>';
		    } else{
		      html += '<tr>'+
					      '<td colspan="3">'+
          					'<table name="addQuestion" class="addQuestions">'+
				                '<tr>'+
  	                				'<td colspan="3"><B>Quest&otilde;es</B></td>'+

                				'</tr>'+
                				'<tr>'+
                					'<td colspan="3" class="cont-tableinterna">';
                					
                					
                					//colocar id nas tables para poder funcionar mais facilmente o [+]
		    	for(var i = 0; i< data.questions.length; i++){
			    	var questionText = data.questions[i][0];
						    	html += '<table id="question'+data.questions[i][2]+'"class="tableinterna2">'+
			                				'<tr>'+
			                					'<td>'+
			                  						'Quest&atilde;o '+(i+1)+'('+questionText.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.questions[i][2]+', previewChoser)">+</a>]</td>' +
			                  				'</tr>'+
			                 			'</table>';
  	

				}
				html+= 				'</td>'+
								'</tr>'+
								'<tr>'+
				                	'<td><B>Adicionar Quest&otilde;es<B></td>'+
				                '</tr>'+
				                '<tr>'+
				                     '<td colspan="3" align="right"><input type="button" value="Nova quest&atilde;o" onclick="chooseQuestion('+data.evaluationId+')"/>&nbsp;&nbsp;<input type="button" value="Banco de Quest&otilde;es"/></td>'+
				                '</tr>'+
						'</table>'+
						'</td>'+
						'</tr>'+
						'<tr>' +
	           				'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="cancel()">Cancelar</a></td>' +
	        			'</tr>';
		    
		    }
		    
		html+= 
				      
		'</table>' + 
	'</td>';
	expanded = -1;
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}


//Recupera a avaliacao do banco para ser editada ou visualizada.
function retrieveStudentEvaluation(data) {
	expanded = -1;
	var html = '<td  class="cont-tableinterna" colspan="3">' +
		'<table class="tableinterna">' +
	  		'<tr>' +
            	'<td colspan="3" class="headTab"></td>' +
           	'</tr>' +
           	'<tr>' +
               '<td colspan="2">'+data.name+'</td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="1">Per&iacute;do de Aplica&ccedil;&atilde;o</td>' +
                  '<td>Valor da Avalia&ccedil;&atilde;o</td>' +
            '</tr>' +
            '<tr>' +
                  '<td colspan="1">&nbsp;In&iacute;cio</td>' +
                  '<td>&nbsp;Peso&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+data.score+'</td>' +
            '</tr>' +
            '<tr>' +
            	  '<td>&nbsp;&nbsp;&nbsp;'+data.startDate+'&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;'+data.startTime+'</td><td>&nbsp;</td>' +
 	        '</tr>' +
            '<tr>' +
                     '<td colspan="1">&nbsp;T&eacute;rmino</td>' +
                     '<td>&nbsp;Nota M&aacute;xima&nbsp;'+data.maxNote+'</td>' +
    	    '</tr>' +
          	'<tr>' +
                  	'<td>&nbsp;&nbsp;&nbsp;'+data.finishDate+'&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;'+data.finishTime+'</td><td>&nbsp;</td>' +
          	'</tr>' +
          	'<tr>' +
                  	'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="editEvaluation('+data.evaluationId+')">Editar</a></td>' +
          	'</tr>' ;
          	
			if(data.hasQuestions == false){
	         html+= 	
	          	'<tr>'+
					'<td colspan="3">'+
				    	'<table name="addQuestion" class="addQuestions">'+
				        	'<tr>'+
				            	'<td colspan="3">'+
				                	'<table id="question" class="addQuestions">'+
				                    	'<tr>'+
				                        	'<td><B>Quest&otilde;es</B></td>'+
				                        '</tr>'+
				                        '<tr>'+
				                        	'<td colspan="3" align="center">Ainda n&atilde;o h&aacute; quest&otilde;es nesta avalia&ccedil;&atilde;o</td>'+
				                        '</tr>'+
				                        '<tr>'+
				                            '<td><B>Adicionar Quest&otilde;es<B></td>'+
				                        '</tr>'+
				                            '<tr>'+
				                        '<td colspan="3" align="right"><input type="button" value="Nova quest&atilde;o" onclick="chooseQuestion('+data.evaluationId+')"/>&nbsp;&nbsp;<input type="button" value="Banco de Quest&otilde;es"/></td>'+
				                        	'</tr>'+
				                    '</table>'+
				                '</td>'+
				             '</tr>'+
				    '</table>'+
				    '</td>'+
			    '</tr>'+
			     '<tr>' +
	           		'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="Activities.teste(orgAct)">Cancelar</a></td>' +
	        	'</tr>';
		    } else{
		      html += '<tr>'+
					      '<td colspan="3">'+
          					'<table name="addQuestion" class="addQuestions">'+
				                '<tr>'+
  	                				'<td colspan="3"><B>Quest&otilde;es</B></td>'+

                				'</tr>'+
                				'<tr>'+
                					'<td colspan="3" class="cont-tableinterna">';
                					
                					
                					//colocar id nas tables para poder funcionar mais facilmente o [+]
		    		for(var i = 0; i< data.questions.length; i++){
			    	var questionText = data.questions[i][0];
						    	html += '<table id="question'+data.questions[i][2]+'"class="tableinterna2">'+
			                				'<tr>'+
			                					'<td>'+
			                  						'Quest&atilde;o '+(i+1)+'('+questionText.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.questions[i][2]+', previewChoser)">+</a>]</td>' +
			                  				'</tr>'+
			                 			'</table>';
  	

					}
					html+=		'</td>'+
								'</tr>'+
								'<tr>'+
				                	'<td colspan="3" class="cont-tableinterna">'+
	                				'<table id="question"class="addQuestions2">'+
										'<tr>'+
						                	'<td><B>Adicionar Quest&otilde;es<B></td>'+
						                '</tr>'+
						                '<tr>'+
						                     '<td colspan="3" align="right"><input type="button" value="Nova quest&atilde;o" onclick="chooseQuestion('+data.evaluationId+')"/>&nbsp;&nbsp;<input type="button" value="Banco de Quest&otilde;es"/></td>'+
						                '</tr>'+
						        	   '</table>'+
									'</td>'+
								'</tr>'+
						'</table>'+
						'</td>'+
						'</tr>'+
						'<tr>' +
	                  		'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="Activities.teste(orgAct)">Cancelar</a></td>' +
	        			'</tr>';
		    
		    }
		    
			html+=	       
		'</table>' + 
	'</td>';
	DWRUtil.setValue('dynamic'+currentEditingModule, html);
}

function chooseQuestion(evaluationId){
	Activities.retrieveStudentEvaluation(evaluationId, chooseQuestionCB);
}

//Funcao que cria o select para o usuario escolher qual o tipo de questao desejada
function chooseQuestionCB(data){
  var numQuestions = 1;  
  var html = '<tr>'+
				                '<td>&nbsp;Quest&atilde;o&nbsp;'+numQuestions+'</td>'+
				             '</tr>'+
				             '<tr>'+
				                  '<td>'+
						                '<select id="questions"></select>'+
				                  '</td>'+
				             '</tr>'+
				             '<tr>'+
				                   '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="selectQuestions('+data.evaluationId+', '+numQuestions+')">Selecionar</a>' + 
				  					' / <a href="javascript:void(0)" onclick="addQuestions('+data.evaluationId+')">Cancelar</a></td>'+
				             '</tr>';
		             
                DWRUtil.setValue("question", html);
	listQuestions();
}

//Funcao que desenha apenas o select das questoes.
function drawSelectQuestions(evaluationId, numQuestions){
	var html = 
	'<tr>'+
	   '<td>&nbsp;Quest&atilde;o&nbsp;'+numQuestions+'</td>'+
	'</tr>'+
	'<tr>'+
		'<td>'+
			'<select id="questions"></select>'+
		'</td>'+
	'</tr>'+
	'<tr>'+
		'<td colspan="3" align="right"><a href="javascript:void(0)" onclick="selectQuestions('+evaluationId+', '+numQuestions+')">Selecionar</a>' + 
		' / <a href="javascript:void(0)" onclick="addQuestions('+evaluationId+')">Cancelar</a></td>'+
	'</tr>';
	listQuestions();
	DWRUtil.setValue("question", html);
}

 
//Funcao que preenche o select com o tipo das questoes
function listQuestions() {
	Activities.listQuestions(currentEditingModule, listQuestionsCB);
}

//Funcao que preenche o select com o tipo das questoes
function listQuestionsCB(data) {
	DWRUtil.removeAllOptions("questions");
	DWRUtil.addOptions("questions", data);
}

//Funcao que seleciona um tipo de questao
function selectQuestions(evaluationId, numQuestions){
	var selection = DWRUtil.getValue("questions");
	if (selection != "select") {
		eval('questionSelected_' + selection + '('+evaluationId+','+numQuestions+');');
	}
}

//Funcao que desenha na tela os campos da questao preencher lacunas
function questionSelected_blanksfilling(evaluationId, numQuestions){
	var html = '<tr>'+
           			'<td>Quest&atilde;o '+numQuestions+'</td>'+
       			'</tr>'+
       			'<tr>'+
     					'<td align="right">N&iacute;vel:<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>'+
       			'</tr>'+
       			'<tr>'+
         				'<td colspan="3"><input name="questionText" type="text" class="courseEvaluationComments" value="Digite aqui a Quest&atilde;o" /></td>'+

       			'</tr>'+
       			'<tr>'+
         				'<td colspan="3">(*) Para inserir a lacuna e sua resposta, basta utilizar colchetes. Ex: [palavra]</td>'+
       			'</tr>'+
       			'<tr>'+
          				'<td colspan="3" align="right"><a href="#">Salvar</a>&nbsp;/&nbsp;<a href="#">Cancelar</a></td>'+
       			'</tr>';
          	DWRUtil.setValue("question", html);
}

////Funcao que desenha na tela os campos da questao discursiva
function questionSelected_discursive(evaluationId, numQuestions){
var html =  
		'<tr>'+
          '<td>Quest&atilde;o '+numQuestions+'</td>'+
          '<td align="right">N&iacute;vel:<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>'+
        '</tr>'+
        '<tr>'+
        	'<td colspan="3"><textarea name="questionText" class="courseEvaluationComments">Digite o enunciado da quest&atilde;o aqui</textarea></td>'+
        '</tr>'+
        '<tr>'+
           '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveDiscursiveQuestion('+evaluationId+', -1)">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="drawSelectQuestions('+evaluationId+','+numQuestions+')">Cancelar</a></td>'+
        '</tr>';
						
		DWRUtil.setValue("question", html);
}

//Funcao que salva a questao discursiva no banco.
function saveDiscursiveQuestion(evaluationId, questionId){
	var questionText = DWRUtil.getValue("questionText");
	var level = DWRUtil.getValue("level");
	
	if (questionText == '') {
		alert("Digite o enunciado da Avaliacao.");
		return;
	} else if(level.toString() == 'false'){
		alert("Selecione o nivel da Avaliacao.");
		return;
	} 
	
	Activities.saveDiscursiveEvaluation(evaluationId, questionText, level, questionId, retrieveStudentEvaluation);
	
	
}

////Funcao que desenha na tela os campos da questao associacao de colunas
function questionSelected_association(evaluationId, numQuestions){
var html =            	
			   '<tr>'
                    '<td>Quest&atilde;o '+numQuestions+'</td>'+
               '</tr>'+		                
               '<tr>'+
               	'<td>N&iacute;vel:<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>'+
               '</tr>'+
               '<tr>'+
               	'<td>Primeira Coluna</td>'+
               	'<td align="left">Segunda Coluna</td>'+
               '</tr>'+
                '<td>1 -<input name="textfield4232" type="text" size="15"/></td>'+
                '<td><input name="textfield4232" type="text" size="1"/>-<input name="textfield4232" type="text" size="15"/></td>'+
               '<tr>'+
                '<td width="40%">2 -<input name="textfield4232" type="text" size="15" /></td>'+
                '<td><input name="textfield4232" type="text" size="1"/>-<input name="textfield4232" type="text" size="15"/></td>'+
               '</tr>'+
               '<tr>'+
                '<td>3 -<input name="textfield4232" type="text" size="15"/></td>'+
               '<td><input name="textfield4232" type="text" size="1"/>-<input name="textfield4232" type="text" size="15"/></td>'+
               '</tr>'+
               '<tr>'+
               	'<td><a href="#">Adicionar associa&ccedil;&atilde;o</a></td>'+
               '</tr>'+
               '<tr>'+
                  '<td colspan="3" align="right"><a href="#">Salvar</a>&nbsp;/&nbsp;<a href="#">Cancelar</a></td>'+
               '</tr>';
		      
			DWRUtil.setValue("question", html);
}

//Funcao que desenha na tela os campos da questao multipla escolha

function questionSelected_multChoice(evaluationId, numQuestions){
nomultAlternatives = 2;
var html =            '<tr>'+
		                    '<td>Quest&atilde;o '+numQuestions+'</td>'+
		                	'<td align="right">N&iacute;vel:<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>'+
		                '</tr>'+
		                '<tr>'+
		                  '<td colspan="3"><input name="questionText" type="text" class="courseEvaluationComments" value="Digite aqui a Quest&atilde;o"/></td>'+
						'</tr>'+
		                '<tr>'+
		                  '<td colspan="2">Alternativas</td>'+
		                  '<td align="right">Correta</td>'+
		                '</tr>'+
		                '<tr>'+
		                  '<td colspan="2">'+
		                  	'<ul id="multChoiceQuestion_alt" TYPE=a></ol>' +
		                  '</td>'+
		                  '<td>'+
		                  	'<ul id="multChoiceChoice_alt" TYPE=None></ul>' +
		                  '</td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="addMultChoiceField()">Adicionar nova alternativa</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="deleteChoiceField()">Excluir a &uacute;ltima alternativa</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveMultChoice('+evaluationId+', -1)">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="drawSelectQuestions('+evaluationId+','+numQuestions+')">Cancelar</a></td>'+
		                '</tr>';
			DWRUtil.setValue("question", html);
	

	DWRUtil.addOptions("multChoiceQuestion_alt", ['<input type="text" id="alt1">']);
	DWRUtil.addOptions("multChoiceQuestion_alt", ['<input type="text" id="alt2">']);
	DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="1">']);
	DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="2">']);
	nomultAlternatives = 2;
}
//Adiciona Quest?es de Multipla Escolha na p?gina web (o banco n ? alterado)
function addMultChoiceField() {
	nomultAlternatives += 1;
	var alt = nomultAlternatives;
	DWRUtil.addOptions("multChoiceQuestion_alt", ['<input type="text" id="alt'+ alt +'">']);
	DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="'+ alt +'">']);
}

//Remove Quest?es de Multipla Escolha na p?gina web (o banco n ? alterado)
function deleteChoiceField(){
	var backup = new Array();
	for (var i = 1, j = 1; i <= nomultAlternatives; i++) {
			backup[j] = DWRUtil.getValue('alt'+i);
			j++;
	}
	var correct = DWRUtil.getValue("correct");
	DWRUtil.removeAllOptions('multChoiceQuestion_alt');
	DWRUtil.removeAllOptions('multChoiceChoice_alt');
	
	var size = nomultAlternatives;
	for (var i = 1; i < size; i++) {
		DWRUtil.addOptions("multChoiceQuestion_alt", ['<input id="alt'+ i +'" type="text" value="'+ backup[i]+'">']);
		if(correct == i)
		DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="'+ i +'" CHECKED>']);
		else
		DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="'+ i +'">']);
	}
	nomultAlternatives--;
}
//funcao que salva uma questao de multipla escolha
function saveMultChoice(evaluationId, questionId){
	var question = DWRUtil.getValue('questionText');
	var	alternatives = new Array();
	var right = DWRUtil.getValue('correct');
	var level = DWRUtil.getValue('level');
	if(question == ''||right==''|| level == ''){
		alert('Voc&etilde; precisa preencher todos os campos');
		return;
	}
	for (var i = 1; i <= nomultAlternatives; i++) {
			alternatives[i] = DWRUtil.getValue('alt'+i);
			if(alternatives[i] == ''){
				alert('Voc&etilde; precisa preencher todos os campos');
				return;
			}
	}
	Activities.saveMultChoiceQuestion(question, alternatives, right, level, evaluationId, questionId, retrieveStudentEvaluation);
}

////Funcao que desenha na tela os campos da questao verdadeiro/falso
function questionSelected_trueFalse(evaluationId,  numQuestions){
nomultAlternatives = 2;
var html =  
		            	'<tr>'+
		                    '<td>Quest&atilde;o '+numQuestions+'</td>'+
		                	'<td align="right">N&iacute;vel:<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>'+
		                '</tr>'+
		                '<tr>'+
		                	'<td colspan="2">Senten&ccedil;as</td>'+
		                  	'<td align="center">V&nbsp;/&nbsp;F</td>'+
		                '</tr>'+
		                '<tr>'+
		                	'<td colspan="3">'+
		                  	'<ol id="trueFalseQuestion_alt" TYPE=I></ol>' +
		                  	'</td>'+	
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="addTrueFalseField()">Adicionar Senten&ccedil;a</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="deleteTrueFalseField()">Remover Senten&ccedil;a</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveQuestionTrueFalse('+evaluationId+', -1)">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="drawSelectQuestions('+evaluationId+','+numQuestions+')">Cancelar</a></td>'+
		                '</tr>';
		    
	DWRUtil.setValue("question", html);
	DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence1" type="text" size="27"><input type="radio" name="s1" value="1"><input type="radio" name="s1" value="0">']);
	DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence2" type="text" size="27"><input type="radio" name="s2" value="1"><input type="radio" name="s2" value="0">']);
	DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence3" type="text" size="27"><input type="radio" name="s3" value="1"><input type="radio" name="s3" value="0">']);
	nomultAlternatives = 3;
}

//fun??o que adiciona um campo de senten?a da p?gina web (o banco n ? alterado).
function addTrueFalseField() {
	nomultAlternatives += 1;
	var alt = nomultAlternatives;
	DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+alt+'" type="text" size="27"><input type="radio" name="s'+alt+'" value="1"><input type="radio" name="s'+alt+'" value="0">']);
}

//funcao que deleta um campo de senten?a da p?gina web (o banco n ? alterado).
function deleteTrueFalseField(){
	var backup = new Array();
	var selected = new Array();
	for (var i = 1, j = 1; i <= nomultAlternatives; i++) {
			backup[j] = DWRUtil.getValue('sentence'+i);
			selected[j] = DWRUtil.getValue('s'+i);
			j++;
	}
	DWRUtil.removeAllOptions('trueFalseQuestion_alt');
	var size = nomultAlternatives;
	for (var i = 1; i < size; i++) {
		switch (selected[i]){
		case '1':
		DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+i+'" type="text" size="27" value="'+backup[i]+'"><input type="radio" name="s'+i+'" value="1" CHECKED><input type="radio" name="s'+i+'" value="0">']);
		break;
		case '0':
		DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+i+'" type="text" size="27" value="'+backup[i]+'"><input type="radio" name="s'+i+'" value="1" ><input type="radio" name="s'+i+'" value="0" CHECKED>']);
		break;
		default:
		DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+i+'" type="text" size="27" value="'+backup[i]+'"><input type="radio" name="s'+i+'" value="1"><input type="radio" name="s'+i+'" value="0">']);
		break;
		}
	}
	nomultAlternatives--;
}
//Funcao que recupera os dados da pagina e salva-os no banco
function saveQuestionTrueFalse(evaluationId, questionId){
	var sentences = new Array();
	var level = DWRUtil.getValue('level');
	if(level =='')
	{
		alert('Voc&etilde; precisa preencher todos os campos');
		return;
	}
	for (var i = 1, j = 1; i <= nomultAlternatives; i++) {
			sentences[i] = new Array();
			sentences[i][0] = DWRUtil.getValue('sentence'+i);
			sentences[i][1] = DWRUtil.getValue('s'+i);
			if(sentences[i][0] == '' || sentences[i][1] == ''){
				alert('Voc&etilde; precisa preencher todos os campos');
				return;
			}
			j++;
	}
	Activities.saveTrueFalseQuestion(sentences, level, evaluationId, questionId, retrieveStudentEvaluation);
}
/**
*Funcao que verifica qual o tipo da quest?o e redireciona para a fun??o adequada quando presiona-se [+]
* na vis?o do professor
*/
function previewChoser(data){
	if(expanded != -1){
		Activities.retrieveQuestionData(data.evaluationId, expanded, colapseQuestion)
	}
	var type = data.type;
	switch(type)
	{
	case 'multipleChoice':
  		multipleChoicePreview(data);
  		break;    
	case 'trueOrFalse':
		trueOrFalsePreview(data);
   		break;
  	case 'discursiveQuestion':
  		discoursiveQuestionPreview(data);
  		break;
	}
	expanded = data.id;
}

/*Funcao que verifica qual o tipo da questao e redireciona para a funcao adequada quando presiona-se [+]
* na vis?o do aluno
*/
function answerChoser(data){
	if(expanded != -1){
		Activities.retrieveQuestionData(data.evaluationId, expanded, colapseQuestion)
	}
	var type = data.type;
	switch(type)
	{
	case 'multipleChoice':
  		multipleChoiceAnswer(data);
  		break;    
	case 'trueOrFalse':
		trueOrFalseAnswer(data);
   		break;
  	case 'discursiveQuestion':
  		discoursiveQuestionAnswer(data);
  		break;
	}
	expanded = data.id;
}


/**
*Funcao chamada quando pression-se o [+] da quest?o verdadeiro ou falso
*/
function trueOrFalsePreview(data){
	var list = data.sentences;
	
	var html = '<tr>'+
                '<td colspan="2">Quest&atilde;o '+ data.id+'</td>'+
                '<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestion)">-</a>]</td>'+
                  '</tr>'+
                  '<tr>'+
                     '<td>'+
                          'Senten&ccedil;as'+
                      '</td>'+
                  '</tr>'+
                  '<tr>'+
		            '<td colspan="3">'+
		            '<ol id="trueFalseQuestion_alt" TYPE=I>';
		            for (var i = 0; i < list.length; i++){	
		            	html+='<li>'+list[i];
		            }
		      html+='</ol>'+
		            '</td>'+	
		          '</tr>';
             if(isEditing){
             html+= '<tr>'+
                    '<td colspan="3" align="right"><input type="button" value="Salva no Banco de Quest&otilde;es"/>&nbsp;&nbsp;<input type="button" value="Editar" onclick="Activities.retrieveQuestionData('+data.evaluationId+','+data.id+',trueOrFalseEdit)"/></td>'+
                    '</tr>';
             }
	DWRUtil.setValue('question'+data.id, html);
}
/**
*Funcao chamada quando pression-se o [+] da quest?o multipla escolha
*/

function multipleChoicePreview(data){
	var list = data.alternatives;
	var html = '<tr>'+
                '<td colspan="2">Quest&atilde;o '+ data.id+'</td>'+
                '<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestion)">-</a>]</td>'+
                  '</tr>'+
                  '<tr>'+
                  data.question+
                  '</tr>'+
                  '<tr>'+
		            '<td colspan="3">'+
		            '<ol id="trueFalseQuestion_alt" TYPE=a>';
		            for (var i = 0; i < list.length; i++){	
		            	html+='<li>'+list[i];
		            }
		      html+='</ol>'+
		            '</td>'+	
		          '</tr>';
             if(isEditing){
             html+= '<tr>'+
                    '<td colspan="3" align="right"><input type="button" value="Salva no Banco de Quest&otilde;es"/>&nbsp;&nbsp;<input type="button" value="Editar" onclick="Activities.retrieveQuestionData('+data.evaluationId+','+data.id+',multipleChoiceEdit)"/></td>'+
                    '</tr>';
             }
	DWRUtil.setValue('question'+data.id, html);
}
/**
*Funcao chamada quando pression-se o [+] da quest?o discursiva
*/
function discoursiveQuestionPreview(data){
	var html = '<tr>'+
                '<td colspan="2">Quest&atilde;o '+ data.id+'</td>'+
                '<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestion)">-</a>]</td>'+
                  '</tr>'+
                  '<tr>&nbsp;&nbsp;'+
                  data.question+
                  '</tr>';
             if(isEditing){
             html+= '<tr>'+
                    '<td colspan="3" align="right"><input type="button" value="Salva no Banco de Quest&otilde;es"/>&nbsp;&nbsp;<input type="button" value="Editar" onclick="Activities.retrieveQuestionData('+data.evaluationId+','+data.id+',discoursiveQuestionEdit)"/></td>'+
                    '</tr>';
             }
	DWRUtil.setValue('question'+data.id, html);
}
/**
*Funcao chamada quando pression-se o [-] para colapsar a quest?o no perfil do professor
*/
function colapseQuestion(data){
var html='<tr>'+
		'<td>'+
	'Quest&atilde;o '+ data.id+' ('+data.question.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', previewChoser)">+</a>]</td>' +
		'</tr>';
		if(expanded == data.id)
			expanded = -1;
	DWRUtil.setValue('question'+data.id, html);
}

//Funcao chamada quando o [-] do perfil do aluno eh selecionado.
function colapseQuestionAnswer(data){
var html='<tr>'+
		'<td>'+
	'Quest&atilde;o '+ data.id+' ('+data.question.substring(0,21)+')</td><td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', answerChoser)">+</a>]</td>' +
		'</tr>';
		if(expanded == data.id)
			expanded = -1;
	DWRUtil.setValue('question'+data.id, html);
}
/**
*Fun??o chamada quando se deseja editar uma quest?o discursiva
*
*
*/
function discoursiveQuestionEdit(data){
	DWRUtil.removeAllRows('question'+data.id);
	var html =  
		'<tr>'+
          '<td>Quest&atilde;o '+data.id+'</td>'+
          '<td align="right">N&iacute;vel:';
          var nivel = data.nivel;
          switch(nivel){
          	case '1':
          	html+='<input type="radio" name="level" value="1" checked><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>';
          	break;
          	case '2':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2" checked><input type="radio" name="level" value="3"></td>';
          	break;
          	case '3':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3" checked></td>';
          	break;
          	          
          }
          
          
        html+=  
        '</tr>'+
        '<tr>'+
        	'<td colspan="3"><textarea name="questionText" class="courseEvaluationComments">'+data.question+'</textarea></td>'+
        '</tr>'+
        '<tr>'+
           '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveDiscursiveQuestion('+data.evaluationId+', '+data.id+')">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)">Cancelar</a></td>'+
        '</tr>';
						
		DWRUtil.setValue("question", html);
}
/**
*Fun??o chamada quando se deseja editar uma quest?o de Verdadeiro ou falso
*
*
*/
function trueOrFalseEdit(data){
	DWRUtil.removeAllRows('question'+data.id);
	nomultAlternatives = 2;
	var html =  
		            	'<tr>'+
		                    '<td>Quest&atilde;o '+data.id+'</td><td align="right">N&iacute;vel:';
		  var nivel = data.nivel;
          switch(nivel){
          	case '1':
          	html+='<input type="radio" name="level" value="1" checked><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>';
          	break;
          	case '2':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2" checked><input type="radio" name="level" value="3"></td>';
          	break;
          	case '3':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3" checked></td>';
          	break;
          	          
          }
   html+=	  '</tr>'+
		                '<tr>'+
		                	'<td colspan="2">Senten&ccedil;as</td>'+
		                  	'<td align="center">V&nbsp;/&nbsp;F</td>'+
		                '</tr>'+
		                '<tr>'+
		                	'<td colspan="3">'+
		                  	'<ol id="trueFalseQuestion_alt" TYPE=I></ol>' +
		                  	'</td>'+	
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="addTrueFalseField()">Adicionar Senten&ccedil;a</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="deleteTrueFalseField()">Remover Senten&ccedil;a</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveQuestionTrueFalse('+data.evaluationId+', '+data.id+')">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)">Cancelar</a></td>'+
		                '</tr>';
		    
	DWRUtil.setValue("question", html);
	var size = data.sentences.length;
	for(var i = 1; i<= size; i++){
		if(data.value[i])	
			DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+i+'" type="text" size="27" value="'+data.sentences[(i-1)]+'"><input type="radio" name="s'+i+'" value="1" CHECKED><input type="radio" name="s'+i+'" value="0">']);
		else
			DWRUtil.addOptions("trueFalseQuestion_alt", ['<input id="sentence'+i+'" type="text" size="27" value="'+data.sentences[(i-1)]+'"><input type="radio" name="s'+i+'" value="1"><input type="radio" name="s'+i+'" value="0" CHECKED>']);
	}
	nomultAlternatives = size;
}

/**
*Fun??o chamada quando se deseja editar uma quest?o de multipla escolha
*
*
*/

function multipleChoiceEdit(data){
DWRUtil.removeAllRows('question'+data.id);
nomultAlternatives = 2;
var html =            '<tr>'+
		   '<td>Quest&atilde;o '+data.id+'</td>'+
		   	'<td align="right">N&iacute;vel:';
		   var nivel = data.nivel;
          switch(nivel){
          	case '1':
          	html+='<input type="radio" name="level" value="1" checked><input type="radio" name="level" value="2"><input type="radio" name="level" value="3"></td>';
          	break;
          	case '2':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2" checked><input type="radio" name="level" value="3"></td>';
          	break;
          	case '3':
          	html+='<input type="radio" name="level" value="1"><input type="radio" name="level" value="2"><input type="radio" name="level" value="3" checked></td>';
          	break;
          	          
          }
		       html+='</tr>'+
		                '<tr>'+
		                  '<td colspan="3"><input name="questionText" type="text" class="courseEvaluationComments" value="'+data.question+'"/></td>'+
						'</tr>'+
		                '<tr>'+
		                  '<td colspan="2">Alternativas</td>'+
		                  '<td align="right">Correta</td>'+
		                '</tr>'+
		                '<tr>'+
		                  '<td colspan="2">'+
		                  	'<ul id="multChoiceQuestion_alt" TYPE=a></ol>' +
		                  '</td>'+
		                  '<td>'+
		                  	'<ul id="multChoiceChoice_alt" TYPE=None></ul>' +
		                  '</td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="addMultChoiceField()">Adicionar nova alternativa</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3"><a href="javascript:void(0)" onclick="deleteChoiceField()">Excluir a &uacute;ltima alternativa</a></td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3" align="right"><a href="javascript:void(0)" onclick="saveMultChoice('+data.evaluationId+', '+data.id+')">Salvar</a>&nbsp;/&nbsp;<a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)">Cancelar</a></td>'+
		                '</tr>';
			DWRUtil.setValue("question", html);
	var size = data.alternatives.length;
	for(var i = 1; i<= size; i++){
		DWRUtil.addOptions("multChoiceQuestion_alt", ['<input type="text" id="alt'+i+'" value="'+data.alternatives[(i-1)]+'">']);
		if(data.value[(i-1)])	
				DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="'+i+'" CHECKED>']);
		else
				DWRUtil.addOptions("multChoiceChoice_alt", ['<input type="radio" name="correct" value="'+i+'">']);
	}
	nomultAlternatives = size;
}

//Funcao chamada para se responder uma quest?o multipla escolha
function multipleChoiceAnswer(data){

	var html =  
		'<tr>'+
          '<td colspan="2">Quest&atilde;o '+data.id+'</td>'+
          '<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestionAnswer)">-</a>]</td>'+
        '</tr>'+
        '<tr>'+
        '<td>&nbsp;&nbsp;'+data.question+'</td>'+
        '</tr>'+
		                '<tr>'+
		                  '<td colspan="2">Alternativas</td>'+
		                  '<td align="right">Selecione</td>'+
		                '</tr>'+
		                '<tr>'+
		                  '<td colspan="2">'+
		                  	'<ul id="multChoiceQuestion_alt" TYPE=a></ol>' +
		                  '</td>'+
		                  '<td>'+
		                  	'<ul id="multChoiceChoice_alt" TYPE=None></ul>' +
		                  '</td>'+
		                '</tr>'+
		                '<tr>'+
		                   '<td colspan="3" align="right"><input type="button" value="Salvar" onclick="#"/><a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)"></a></td>'+
		                '</tr>';
			DWRUtil.setValue('question'+data.id, html);
		sacana(data);

}

function sacana(data){
	var size = data.alternatives.length;
	DWRUtil.addOptions('multChoiceQuestion_alt', data.alternatives);
	for(var i = 1; i<= size; i++){
		
		DWRUtil.addOptions('multChoiceChoice_alt', ['<input type="radio" name="correct" value="'+i+'">']);
	}
}

//Funcao chamada para se responder uma quest?o Verdadeiro ou Falso
function trueOrFalseAnswer(data){
var html =  
		'<tr>'+
          '<td colspan="2">Quest&atilde;o '+data.id+'</td>'+
          '<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestionAnswer)">-</a>]</td>'+
            '</tr>'+
		                '<tr>'+
		                	'<td colspan="2">Senten&ccedil;as</td>'+
		                  	'<td align="center">V&nbsp;/&nbsp;F</td>'+
		                '</tr>'+
		                '<tr>'+
		                	'<td colspan="3">'+
		                  	'<ol id="trueFalseQuestion_alt" TYPE=I></ol>' +
		                  	'</td>'+	
		                '</tr>'+
		                '<tr>'+
				            '<td colspan="3" align="right"><input type="button" value="Salvar" onclick="#"/><a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)"></a></td>'+
        				'</tr>';
		    
	DWRUtil.setValue('question'+data.id, html);
	
	var size = data.sentences.length;
	for(var i = 1; i<= size; i++){
		DWRUtil.addOptions("trueFalseQuestion_alt", [data.sentences[(i-1)]+'<input type="radio" name="s'+i+'" value="1"><input type="radio" name="s'+i+'" value="0">']);
	}          
          	
}

//Funcao chamada para se responder uma quest?o discursiva
function discoursiveQuestionAnswer(data){
	var html = '<tr>'+
          '<td colspan="2">Quest&atilde;o '+data.id+'</td>'+
          	'<td align="right">[<a href="javascript:void(0)" onclick="Activities.retrieveQuestionData('+data.evaluationId+', '+data.id+', colapseQuestionAnswer)">-</a>]</td>'+
          '</tr>'+
	      '<tr>'+
	      	'<td>&nbsp;&nbsp;'+data.question+'</td>'+
	      '</tr>'+
	      '<tr>'+
	      	'<td colspan="3"><textarea name="questionText" class="courseEvaluationComments"></textarea></td>'+
	      '</tr>'+
	      '<tr>'+
	      	'<td colspan="3" align="right"><input type="button" value="Salvar" onclick="#"/><a href="javascript:void(0)" onclick="Activities.retrieveStudentEvaluation('+data.evaluationId+',retrieveStudentEvaluation)"></a></td>'+
	      '</tr>';
	      DWRUtil.setValue('question'+data.id, html);
	
}




