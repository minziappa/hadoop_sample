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
				<div id="content">
					<div class="post">
						<form action="/admin/admin/registerAdmin.sg" method="POST">
				            <fieldset>
				                <legend>管理者登録</legend>
				                <b style="color:red" >${errorMessage?if_exists}</b>
								<table id="space">
									<tr>
										<td>Admin ＩＤ</td><td><input type="text" name="adminId" size="45" /></td><td>例)bt-admin</td>
									</tr>
									<tr>
										<td>Password</td><td><input type="password" name="adminPwd" size="45" /></td><td>例)xxxxxxxxx</td>
									</tr>
									<tr>
										<td>ゲームID</td>
										<td>
										<select name="gameId">
<#if model.hadoopGameList?has_content>
<#list model.hadoopGameList as game>
											<option value="${game.gameId?if_exists}">${game.gameTitle?if_exists}</option>
</#list>
</#if>
										</select>
										</td><td>例)bt</td>
									</tr>
									<tr>
										<td>管理者名</td><td><input type="text" name="adminName" size="45" /></td><td>例)名前　名前</td>
									</tr>
									<tr>
										<td>管理者メールアドレス</td><td><input type="text" name="adminMail" size="45" /></td><td>notification@sample.io</td>
									</tr>
									<tr>
										<td>管理者権限</td>
										<td>
										<select name="adminStatusFlag">
											<option value="0">Semi管理者</option>
											<option value="1">Super管理者</option>
										</select>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
									</tr>
									<tr>
										<td><input type="submit" value="管理者登録" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" /></td><td>&nbsp;</td><td>&nbsp;</td>
									</tr>
								</table>
				            </fieldset>
						</form>
					</div>

					<div style="clear: both;">&nbsp;</div>
				</div>
				<!-- end #content -->

				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page -->
</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>