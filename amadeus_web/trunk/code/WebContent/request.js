var msgCancel;
var msgApprove;

var lastRequest = null;

function init(approve, cancel) {
	msgCancel = cancel;
	msgApprove = approve;
	pinballEffect();
}

function showDetails(requestId) {
	if (lastRequest != null)
		hideDetails(lastRequest);
	lastRequest = requestId;
	
	$('request'+requestId).className = 'requestExpanded';
	$('reqInfo'+requestId).style.display = 'block';
	$('reqBtns'+requestId).style.display = 'block';
}
function hideDetails(requestId) {
	$('request'+requestId).className = 'requestColapsed';
	$('reqInfo'+requestId).style.display = 'none';
	$('reqJust'+requestId).style.display = 'none';
	$('reqBtns'+requestId).style.display = 'none';
	$('reqJustification'+requestId).value = '';
	$('reqBtnLeft'+requestId).blur();
	$('reqBtnLeft'+requestId).value = msgApprove;
	$('reqBtnLeft'+requestId).onclick = function() {approveRequest(requestId)};	
	$('reqBtnRight'+requestId).onclick = function() {showJustBox(requestId)};
}

function showJustBox(requestId) {
	$('reqInfo'+requestId).style.display = 'none';
	$('reqJust'+requestId).style.display = 'block';
	$('reqJustification'+requestId).focus();
	$('reqBtnLeft'+requestId).value = msgCancel;
	$('reqBtnLeft'+requestId).onclick = function() {showDetails(requestId)};
	$('reqBtnRight'+requestId).onclick = function() {disaproveRequest(requestId)};
}

function approveRequest(requestId) {
	var cb = {callback:function(data) {requestServerResponse(requestId, data);}};
	RequestValidation.approveRequest(requestId, cb);
}
function disaproveRequest(requestId) {
	var just = $('reqJustification'+requestId).value;
	var cb = {callback:function(data) {requestServerResponse(requestId, data);}};
	RequestValidation.disapproveRequest(requestId, just, cb);
}
function requestServerResponse(requestId, data) {
	if (data != null) {
		alert(data);
	} else {
		$('request'+requestId).style.display = 'none';
	}
}

// ## PinBall Effect ## //

var lastElement = null;
var W3CDOM = (document.createElement && document.getElementsByTagName);

function pinballEffect() {
	if (!W3CDOM) return;
	var allElements = document.getElementsByTagName('div');
	var originalBackgrounds=new Array();
	for (var i=0; i<allElements.length; i++) {
		if (allElements[i].className.indexOf('pinball-scoop') !=-1) {
			pinballAddEvents(allElements[i]);
		}
	}
}

function mouseGoesOver() {
	originalClassNameString = this.className;
	this.className += " pinball-on";
	this.style.cursor = "pointer";
	//this.style.cursor = "hand";
}

function mouseGoesOut() {
	this.className = originalClassNameString;
}

function mouseGoesClick() {
	var allThisAreasElements = this.getElementsByTagName('*');
	for (var j=0; j<allThisAreasElements.length; j++) {
		if (allThisAreasElements[j].className.indexOf('pinball-sinkhole') != -1) {
			if (lastElement != null)
				pinballAddEvents(lastElement);

			lastElement = this;
			allThisAreasElements[j].onclick();
			originalClassNameString = this.className;
			pinballRemoveEvents(this);
		}
	}
}

function pinballAddEvents(element) {
	element.onmouseover = mouseGoesOver;
	element.onmouseout = mouseGoesOut;
	element.onclick = mouseGoesClick;
}
function pinballRemoveEvents(element) {
	element.onmouseover = function() {};
	element.onmouseout = function() {};
	element.onclick = function() {};
	element.style.cursor = 'default';
}