<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<!-- start #header -->
<#include "common/header.ftl">
<!-- end #header -->
<body>
<!-- start #menu -->
<!-- end #menu -->

<script type="text/javascript">
Highcharts.theme = {
   colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
   chart: {
      backgroundColor: {
         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
         stops: [
            [0, 'rgb(255, 255, 255)'],
            [1, 'rgb(240, 240, 255)']
         ]
      },
      borderWidth: 2,
      plotBackgroundColor: 'rgba(255, 255, 255, .9)',
      plotShadow: true,
      plotBorderWidth: 1
   },
   title: {
      style: {
         color: '#000',
         font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   subtitle: {
      style: {
         color: '#666666',
         font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   xAxis: {
      gridLineWidth: 1,
      lineColor: '#000',
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'

         }
      }
   },
   yAxis: {
      minorTickInterval: 'auto',
      lineColor: '#000',
      lineWidth: 1,
      tickWidth: 1,
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '7px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '7px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
         }
      }
   },
   legend: {
      itemStyle: {
         font: '9pt Trebuchet MS, Verdana, sans-serif',
         color: 'black'

      },
      itemHoverStyle: {
         color: '#039'
      },
      itemHiddenStyle: {
         color: 'gray'
      }
   },
   labels: {
      style: {
         color: '#99b'
      }
   },

   navigation: {
      buttonOptions: {
         theme: {
            stroke: '#CCCCCC'
         }
      }
   }
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);

$(function () {
        $('#container').highcharts({
            chart: {
				reversed: false,
                type: 'spline'
            },
            title: {
                text: 'Native Studio Apps'
            },
            subtitle: {
                text: 'Irregular time data in Highcharts JS'
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: { // don't display the dummy year
                    month: '%e. %b',
                    year: '%b'
                }
            },
            yAxis: {
	            title: {
	                text: null
	            },
	            labels: {
	                align: 'left',
	                x: 3,
	                y: 16,
	                formatter: function() {
	                    return Highcharts.numberFormat(this.value, 0);
	                }
	            },
	            showFirstLabel: false,
				reversed: true,
                min: 0,
			    max: 400
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' m';
                }
            },
            series: [
<#if model.sampleAppList?has_content>
<#list model.sampleAppList as sampleApp>
            {
                name: '${sampleApp.name?if_exists}',
                data: [
	<#if sampleApp.appDataList?has_content>
	<#list sampleApp.appDataList as appData>
		<#if appData_index = 0>
					{x:Date.UTC(${appData.year?if_exists},  ${appData.month?if_exists}, ${appData.day?if_exists}, ${appData.hour?if_exists}),y:${appData.value?if_exists},marker:{symbol:'url(${sampleApp.img?if_exists})'}},
		<#else>
					[Date.UTC(${appData.year?if_exists},  ${appData.month?if_exists}, ${appData.day?if_exists}, ${appData.hour?if_exists}), ${appData.value?if_exists} ]<#if appData_has_next>,</#if>
		</#if>
	</#list>
	</#if>
                ]
            }<#if sampleApp_has_next>,</#if>
</#list>
</#if>		]
        });
    });
</script>

<#include "../common/calendar.ftl">
	<div id="main">
		<p><div id="container" style="min-width: 200px; height: 400px; margin: 0 auto"></div></p>
	</div>
	<#include "common/footer.ftl">
</body>
</html>