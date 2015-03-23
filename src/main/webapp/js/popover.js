// SHOW POP-OVER
function showPopOver(divID) {
	// LOAD THE VIEW SIZE + SCROLL POSITION
	loadScreen();
	// SHOW THE BG
	showBG();
	// SHOW THE DIV
	document.getElementById(divID).style.display = "block";
	// SET THE DIV POSITION
	document.getElementById(divID).style.left = ((myWidth / 2)-(document.getElementById(divID).offsetWidth / 2))+"px";
	document.getElementById(divID).style.top = ((myHeight / 2)-(document.getElementById(divID).offsetHeight / 2)+myScroll)+"px";
	
}

// CLOSE POP-OVER
function closePopOver(divID) {
	// HIDE THE BG
	hideBG();
	// HIDE THE DIV
	document.getElementById(divID).style.display = "none";
}

// SHOW BG
function showBG() {
	// SHOW THE DIV
	document.getElementById("transBG").style.display = "block";
	// SET THE DIV SIZE
	document.getElementById("transBG").style.width = myScrollWidth+"px";
	document.getElementById("transBG").style.height = myScrollHeight+"px";
}

// HIDE BG
function hideBG() {
	// HIDE THE DIV
	document.getElementById("transBG").style.display = "none";
}

// LOAD SCREEN ATTRIBUTES
var myWidth = 0, myHeight = 0, myScroll = 0; myScrollWidth = 0; myScrollHeight = 0;
function loadScreen() {
	if (document.all) {
		// IE
		myWidth  = (document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.clientWidth;
		myHeight = (document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.clientHeight;
		myScroll = (document.documentElement.scrollTop) ? document.documentElement.scrollTop : document.body.scrollTop;
	} else {
		// NON-IE
		myWidth = window.innerWidth;
		myHeight = window.innerHeight;
		myScroll = window.pageYOffset;
	}
	if (window.innerHeight && window.scrollMaxY) {	
		// NON-IE
        myScrollWidth = document.body.scrollWidth;
		myScrollHeight = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) { 
		// IE
		myScrollWidth = document.body.scrollWidth;
		myScrollHeight = document.body.scrollHeight;
	} else { 
		// IE MAC
		myScrollWidth = document.body.offsetWidth;
		myScrollHeight = document.body.offsetHeight;
	}
}