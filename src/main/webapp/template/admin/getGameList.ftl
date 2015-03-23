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
			<div id="content">
				<div class="post">
				    <fieldset>
				    <legend>ゲームリスト</legend>
					<!-- start #list -->
<#include "../list/getGameList.ftl">
					<!-- end #list -->
				    <div style="clear: both;">&nbsp;</div>
				    </fieldset>
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