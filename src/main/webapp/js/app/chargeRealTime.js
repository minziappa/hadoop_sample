var xx = 0;
var yy = 0;
var yyPreviousSum = 0;
function myFunction(x, y, yPreviousSum) {
	xx = x;
	yy = y;
	yyPreviousSum = yPreviousSum;
}
var eb = new vertx.EventBus("http://172.28.153.117:8091/eventbus");

function browserHandler(msg, replyTo) {
	var objs = msg.text;
	var objsPreviousSum = msg.privousData;
	var obj = JSON.parse(objs);
	var objPreviousSum = JSON.parse(objsPreviousSum);

	var keys = [], k, i, len;

	for (k in obj)
	{
	    if (obj.hasOwnProperty(k))
	    {
	        keys.push(k);
	    }
	}

	keys.sort();
	len = keys.length;

	var chargeHearder = "";
	var chargeBody = "";
	var chargeHearder1 = "";
	var chargeBody1 = "";
	for (i = 0; i < len; i++)
	{
	    k = keys[i];
	    if(i < 13) {
		    chargeHearder = chargeHearder + "<th>" + k + "</th>";
		    chargeBody = chargeBody + "<th>" + obj[k].toLocaleString() + "&yen;</th>";
	    } else {
		    chargeHearder1 = chargeHearder1 + "<th>" + k + "</th>";
		    chargeBody1 = chargeBody1 + "<th>" + obj[k].toLocaleString() + "&yen;</th>";
	    }
	}

	var trHeader =  document.getElementById('charge_hearder');
    trHeader.innerHTML = chargeHearder;
	var trBody =  document.getElementById('charge_body');
    trBody.innerHTML = chargeBody;
	var trHeader1 =  document.getElementById('charge_hearder1');
    trHeader1.innerHTML = chargeHearder1;
	var trBody1 =  document.getElementById('charge_body1');
    trBody1.innerHTML = chargeBody1;

	var trNowSum = document.getElementById('nowSum');
	trNowSum.innerHTML = obj.nowSum.toLocaleString() + "&yen;";
	var trTotalSum = document.getElementById('totalSum');
	trTotalSum.innerHTML = obj.totalSum.toLocaleString() + "&yen;";
	myFunction(50, obj.totalSum, objPreviousSum.previousSum);
}

eb.onopen = function() {
    eb.registerHandler('app.conduit', browserHandler);
};

// This is HighChart
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
					var seriesPreviousSum = this.series[1];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
						y = yy;
						yPreviousSum = yyPreviousSum;
						series.addPoint([x, y], true, true);
						seriesPreviousSum.addPoint([x, yPreviousSum], true, true);
					}, 10000);
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
				count: 10,
				type: 'minute',
				text: '10M'
			}, {
				type: 'all',
				text: 'All'
			}],
			inputEnabled: false,
			selected: 0
		},

		title : {
			text : 'アメバ全体課金'
		},

		exporting: {
			enabled: true
		},

		series : [
		          {
			name : '今日の全体課金',
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
		}, {
			name : '前日の全体課金',
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
		}
		]
	});

});
