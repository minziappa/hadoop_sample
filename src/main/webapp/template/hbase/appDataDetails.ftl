<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
			<!-- start #header -->
<#include "../common/header.ftl">
			<!-- end #header -->
<body>
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

			<!-- start #content -->
				<div id="content">
					<fieldset id="fieldset2">
					<legend>Hbase</legend>

					アプリ詳細情報<br/>

<script type="text/javascript">
$(function () {
        $('#container').highcharts({
            title: {
<#if model.appDataDetailsPara?has_content>
	<#if model.appDataDetailsPara.family == "allFree">
	                text: 'トップ無料'
	<#else>
	                text: 'トップセールス'
	</#if>
</#if>
            },
            subtitle: {
                text: 'Source: Ameba Analytics'
            },
            xAxis: {
                type: 'datetime',
                tickInterval: 24 * 3600 * 1000, // one week
                tickWidth: 0,
                gridLineWidth: 1,
                labels: {
                    align: 'left',
                    x: 3,
                    y: 15
                }
            },
            yAxis: { // left y axis
            	reversed: true,
                title: {
                    text: 'Ranking'
                },
                labels: {
                    align: 'left',
                    x: 3,
                    y: 16,
                    formatter: function() {
                        return Highcharts.numberFormat(this.value, 0);
                    }
                },
                tickInterval: 50,
			    min: 1,
			    max: 400,
                showFirstLabel: true
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                shared: true,
                crosshairs: true
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function() {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: this.pageX,
                                        y: this.pageY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: Highcharts.dateFormat('%A, %b %e, %Y', this.x) +':<br/> '+
                                        this.y +' visits',
                                    width: 100
                                });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },
            series: [{
                name: '${model.appDataDetails.trackCensoredName?if_exists}',
                lineWidth: 4,
                marker: {radius: 4},
                pointStart: Date.UTC(2013, 8, 31),
                data: [

<#if model.appData?has_content>
<#list model.appData as data>
[Date.UTC(${data.year?if_exists},  ${data.month?if_exists}, ${data.day?if_exists}, ${data.hour?if_exists}), ${data.value?if_exists} ]<#if data_has_next>,</#if>
</#list>
</#if>

                ]
            }]
        });
    });
</script>

						<div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>

					<br/>

					<table class="listing" id="space">
						<tbody>
							<tr>
								<td><img src="${model.appDataDetails.artworkUrl60?if_exists}" alt="image"></td>
								<td>${model.appDataDetails.trackCensoredName?if_exists}</td>
								<td>
								${model.appDataDetails.averageUserRating?if_exists}
								<img src="/images/star_${model.appDataDetails.averageUserRating?if_exists}.gif" alt="${model.appDataDetails.averageUserRating?if_exists}">
								</td>
								<td>${model.appDataDetails.currency?if_exists}</td>
							</tr>
							<tr>
								<td>${model.appDataDetails.artistName?if_exists}</td>
								<td colspan=2>
									<#if model.appDataDetails.genres?has_content>
									<#list model.appDataDetails.genres as genre>
									${genre}
									</#list>
									</#if>
								</td>
								<td>
									<#if model.appDataDetails.screenshotUrls?has_content>
									<#list model.appDataDetails.screenshotUrls as screen>
									<img src="${screen}" alt="screenshot" style="width: auto; max-height: 100px;" >
									</#list>
									</#if>
								</td>
							</tr>
							<tr>
								<td colspan=4>
								<textarea id="divPost1" readonly="readonly"  wrap="virtual" cols="200" rows="25" style="background-color: #C5D1E0;">
									${model.appDataDetails.description?if_exists}
								</textarea>
								</td>
							</tr>
						<tbody>
					</table>

					<br/>
					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
				<!-- end #content -->

			<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>