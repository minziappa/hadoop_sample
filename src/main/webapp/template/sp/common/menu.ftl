	<div id="menu">
		<ul>
			<li><a href="/admin/sp/sampleApp.sg?family=allFree">トップ無料</a></li>
			<li><a href="/admin/sp/sampleApp.sg?family=allGrossing">トップセール</a></li>
			<li>
<#if admin?exists >
							<a href="/admin/auth/logout#">ログアウト</a>
<#else>
							<a href="/admin/login/login.sg#">ログイン</a>
</#if>
			</li>
		</ul>
	</div>