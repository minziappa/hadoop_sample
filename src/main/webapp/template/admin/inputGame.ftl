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
						<form action="/admin/admin/registerGame.sg" enctype="multipart/form-data" method="POST">
				            <fieldset>
				                <legend>クライアント登録</legend>
				                <b style="color:red" >${errorMessage?if_exists}</b>
								<table id="space">
									<tr>
										<td>ゲームＩＤ</td><td><input type="text" name="gameId" size="45"></td><td>例)bt</td>
									</tr>
									<tr>
										<td>ゲームドメイン</td><td><input type="text" name="gameDomain" size="45"></td><td>例)bt.samplegame.jp</td>
									</tr>
									<tr>
										<td>ゲームタイトル</td><td><input type="text" name="gameTitle" size="45"></td><td>例)バトル時間</td>
									</tr>
									<tr>
										<td>ゲーム説明</td><td><textarea name="gameExplain" rows="2" cols="40"></textarea></td><td>例)アンドロイドで戦うカードゲーム</td>
									</tr>
									<tr>
										<td>file</td><td><input type="file" name="upfile"></td><td>例)solitaire_dev.p12</td>
									</tr>
									<tr>
										<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
									</tr>
									<tr>
										<td><input type="submit" value="ゲーム登録" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" /></td><td></td><td></td>
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