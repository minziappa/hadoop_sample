		<div id="sidebar">
<#if game?exists>
			<h3>${game?if_exists.gameTitle?if_exists}</h3>
</#if>

<span style="color:#800000; font-weight:bold;">
<#if admin?exists >
							[${admin.adminName?if_exists}]管理者<br/>ログイン中<br/>
<#else>
							ログアウト<br/>
</#if>
</span>
<br/>
			<div class="date-list">
				<ul class="list date-list">
<#if model?exists>

	<#if model.navi == "admin">
					<li class="first"><a href="/admin/admin/getAdminList.sg">管理者リスト</a></li>
					<li class="first"><a href="/admin/admin/inputAdmin.sg#">管理者登録</a></li>
					<li class="first"><a href="/admin/admin/getGameList.sg">ゲームリスト</a></li>
					<li class="first"><a href="/admin/admin/inputGame.sg#">クライアント登録</a></li>
	<#elseif model.navi == "login">
					<li class="first"><a href="/admin/login/login.sg#">ログイン</a></li>
					<li class="first"><a href="/admin/auth/logout#">ログアウト</a></li>
	<#elseif model.navi == "itunes">
					<li class="first"><a href="/admin/itunes/rankingApp.sg">itunesランキング</a></li>
					<li class="first"><a href="/admin/itunes/sampleApp.sg?family=allFree">トップ無料グラフ</a></li>
					<li class="first"><a href="/admin/itunes/sampleApp.sg?family=allGrossing">トップセールスグラフ</a></li>
	<#elseif model.navi == "log">
					<li class="first"><a href="/admin/log/directoryList.sg?path=/home/hadoop/data/flume">Flume</a></li>
	<#elseif model.navi == "hadoop">
					<li class="first"><a href="/admin/hadoop/hbaseScan.sg">Hbase検索</a></li>
					<li class="first"><a href="/admin/hadoop/hbaseCreate.sg">Hbase作成</a></li>
					<li class="first">Hbase削除</li>
	<#else>

	</#if>
</#if>
				</ul>
			</div>
			<h3>注意事項</h3>
			<p>
				Hadoop用のCss検索用
			</p>
		</div>
