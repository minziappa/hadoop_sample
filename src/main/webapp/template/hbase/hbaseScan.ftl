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
					<fieldset id="fieldset1">
					<legend>Hbase</legend>

					テストのためにテーブル検索機能<br/>

					<div>
						<form action="/admin/hadoop/hbaseScan.sg" method="post">
							Table Name<input type="text" name="tableName" value="itunesApp" size="50" /><br/>
							Row Key<input type="text" name="rowKey" value="" size="50" /><br/>
							Family<input type="text" name="family" value="" size="50" />
							<input type="submit" value="検索" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" />
						</form>
						<br/>
						<table class="listing" id="space">
							<thead>
							<tr>
								<th align="left" scope="col">row</th>
								<th align="left" scope="col">family</th>
								<th align="left" scope="col">qualifier</th>
								<th align="left" scope="col">value</th>
								<th align="left" scope="col">timestamp</th>
							</tr>
							</thead>
							<tbody>
<#assign data = "">
<#if model.hbaseResultList?has_content>
<#list model.hbaseResultList as hbaseResult>
							<tr>
								<td>${hbaseResult.row?if_exists}</td>
								<td>${hbaseResult.family?if_exists}</td>
								<td>${hbaseResult.qualifier?if_exists}</td>
								<td>${hbaseResult.value?if_exists}</td>
								<td>${hbaseResult.timestamp?if_exists}</td>
							</tr>
</#list>
</#if>
							<tbody>
						</table>
					</div>

<br/>

					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
				<!-- end #content -->

			<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>