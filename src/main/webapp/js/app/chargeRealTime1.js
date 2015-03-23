var xx = 0;
var yy = 0;
var yy1 = 0;
function myFunction(x, y1, y) {
	xx = x;
	yy1 = y1;
	yy = y;
}
var eb = new vertx.EventBus("http://172.28.153.116:8091/eventbus");

function browserHandler(msg, replyTo) {
	var objs = msg.text;
	var objs1 = msg.privousDate;
	
	var obj = JSON.parse(objs);
	var obj1 = JSON.parse(objs1);

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

	for (i = 0; i < len; i++)
	{
	    k = keys[i];
	    chargeHearder = chargeHearder + "<th>" + k + "</th>";
	    chargeBody = chargeBody + "<th>" + obj[k] + "</th>";
	    // alert(k + ':' + obj[k]);
	}

	var trHeader =  document.getElementById('charge_hearder');
    trHeader.innerHTML = chargeHearder;
	var trBody =  document.getElementById('charge_body');
    trBody.innerHTML = chargeBody;

	//var div = document.getElementById('demo');
	//div.innerHTML = objs;
	var div1 = document.getElementById('demo1');
	div1.innerHTML = "現在時間集計 : " + obj.nowSum;
	var div2 = document.getElementById('demo2');
	div2.innerHTML = "全体集計 : " + obj.totalSum;
	myFunction(50, obj1.totalSum, obj.totalSum);

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
					var series1 = this.series[1];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
						y = yy;
						y1 = yy1;
						//y = Math.round(Math.random() * 100);
						series.addPoint([x, y], true, true);
						series1.addPoint([x, y1], true, true);
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
