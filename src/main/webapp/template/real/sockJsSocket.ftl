<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
			<!-- start #header -->
<#include "../common/header2.ftl">
			<!-- end #header -->
			
<body>
<div id="wrapper">
			<!-- start #menu -->
<#include "../common/menu.ftl">
			<!-- end #menu -->
			<!-- start #sidebar -->
<#include "../common/sidebar.ftl">
			<!-- end #sidebar -->

			<!-- start #content -->
				<div id="content">
					<fieldset id="fieldset1">
					<legend>リアルタイム集計</legend>

					<table class="listing">
						<thead>
							<tr id="charge_hearder">
							</tr>
						</thead>
						<tbody>
							<tr id="charge_body">
							</tr>
						</tbody>
					</table>
					<table class="listing">
						<thead>
							<tr id="charge_hearder1">
							</tr>
						</thead>
						<tbody>
							<tr id="charge_body1">
							</tr>
						</tbody>
					</table>

					<table class="listing">
						<thead>
							<tr>
								<th>現在時間集計</th>
								<th>全体集計</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th id="nowSum" style="color:red;">取得中....</th>
								<th id="totalSum" style="color:red;">取得中....</th>
							</tr>
						</tbody>
					</table>
					<div id="container" style="height: 500px; min-width: 500px; max-width: 1000px;"></div>
					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
			<!-- end #content -->

</div>
<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->

</body>
</html>