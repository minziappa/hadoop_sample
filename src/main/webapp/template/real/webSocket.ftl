<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
			<!-- start #header -->
<#include "../common/header2.ftl">
			<!-- end #header -->
<body>

<script language="javascript" type="text/javascript">
	var wsUri = "ws://172.28.153.116:8083/websocket";
	var output;
	var websocket;

	function init() {
		output = document.getElementById("output");
		testWebSocket();
	}

	function testWebSocket() {
		if(window.WebSocket) {
			websocket = new WebSocket(wsUri);
			// websocket.onopen = function(evt) { onOpen(evt) };
			websocket.onopen = function(evt) { alert('onopen') };
			websocket.onclose = function(evt) { onClose(evt) };
			websocket.onmessage = function(evt) { onMessage(evt) };
			websocket.onerror = function(evt) { onError(evt) };
		} else {
			alert('Your browser does not support WebSockets yet.');
		}
	}

	function onOpen(evt) {
		writeToScreen("OPEN");
		writeToScreen("SENT: test message"); 
		websocket.send("testmessage");
	}

	function onClose(evt) {
		writeToScreen("CLOSE");
	}

	function onMessage(evt){
		writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
		websocket.close();
	}

	function onError(evt){
		writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
	}

	function writeToScreen(message){
		var pre = document.createElement("p");
		pre.style.wordWrap = "break-word";
		pre.innerHTML = message;
		output.appendChild(pre);
	}

	window.addEventListener("load", init, false);

</script>

<div id="wrapper">
			<!-- start #top -->
<#include "../common/top.ftl">
			<!-- end #top -->
			<!-- start #menu -->
<#include "../common/menu.ftl">
			<!-- end #menu -->
			<!-- start #sidebar -->
<#include "../common/sidebar.ftl">
			<!-- end #sidebar -->
			<!-- end #content -->

<h2>準備中</h2>

<script language="javascript" type="text/javascript">
$(function() {
	
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});
	
	// Create the chart
	$('#container').highcharts('StockChart', {
		chart : {
			events : {
				load : function() {

					// set up the updating of the chart each second
					var series = this.series[0];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
						y = Math.round(Math.random() * 100);
						series.addPoint([x, y], true, true);
					}, 1000);
				}
			}
		},
		
		rangeSelector: {
			buttons: [{
				count: 1,
				type: 'minute',
				text: '1M'
			}, {
				count: 5,
				type: 'minute',
				text: '5M'
			}, {
				type: 'all',
				text: 'All'
			}],
			inputEnabled: false,
			selected: 0
		},
		
		title : {
			text : 'Live random data'
		},
		
		exporting: {
			enabled: false
		},
		
		series : [{
			name : 'Random data',
			data : (function() {
				// generate an array of random data
				var data = [], time = (new Date()).getTime(), i;

				for( i = -999; i <= 0; i++) {
					data.push([
						time + i * 1000,
						Math.round(Math.random() * 100)
					]);
				}
				return data;
			})()
		}]
	});

});
</script>

<div id="container" style="height: 500px; min-width: 500px"></div>

			<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>