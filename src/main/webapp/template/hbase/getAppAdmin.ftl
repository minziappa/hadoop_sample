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

			<!-- start #content -->
				<div id="content">
					<fieldset id="fieldset1">
					<legend>自分が設定したアプリリスト</legend>
					<b style="color:red" >${errorMessage?if_exists}</b>
					<div style="float:left;">
					<form action="/admin/itunes/deleteAppAdmin.sg" method="post">
					<table class="listing" id="space" style="width:500pt;">
						<thead>
							<th align="center" scope="col" colspan=3>トップ無料</th>
							<th align="center" scope="col"><input type="submit" value="削除" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" /></th>
						</thead>
						<tbody>
							<tr align="center" scope="col">
								<td>チェック</td>
								<td>アプリＩＤ</td>
								<td>アプリイメージ</td>
								<td>タイトル</td>
							</tr>
<#if model.appAdminList?has_content>
<#list model.appAdminList as appAdmin>
							<tr align="left" scope="col">

								<td><input type="checkbox" name="appId" value="${appAdmin.appId?if_exists}"></td>
								<td>${appAdmin.appId?if_exists}</td>
								<td>
									<a href="/admin/itunes/appDataDetails.sg?id=${appAdmin.appId?if_exists}">
									<img src="${appAdmin.appAdminImg?if_exists}" alt="image">
									</a>
								</td>
								<td>${appAdmin.appAdminName?if_exists}</td>
							</tr>
</#list>
</#if>
						</tbody>
					</table>
					<input type="hidden" name="appAdminGenre" value="${model.sampleAppPara?if_exists.family?if_exists}">
					</form>
					</div>

					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
				<!-- end #content -->

				<div style="clear: both;">&nbsp;</div>
</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>