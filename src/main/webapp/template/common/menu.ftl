			<!-- end #menu -->
			<div id="header">
				<div id="logo">
					<h1><a href="/admin/login/index.sg#">
<#if game?exists >
							${game.gameTitle?if_exists}<br/>
<#else>
							Log analysis platform<br/>
</#if>
					</a>
					</h1>
				</div>
				<div id="menu">
					<ul>
						<li><a href="/admin/log/directoryList.sg?path=/home/hadoop/data/flume">ログ検索</a></li>
						<li><a href="/admin/itunes/sampleApp.sg?family=allGrossing">チャート</a></li>
						<li><a href="/admin/real/sockJsSocket.sg">RealTime</a></li>
						<li><a href="/admin/hadoop/hbaseScan.sg">Hadoop管理</a></li>
						<li><a href="/admin/admin/getAdminList.sg">システム管理</a></li>
						<li>
<#if admin?exists >
							<a href="/admin/auth/logout#">ログアウト</a>
<#else>
							<a href="/admin/login/login.sg#">ログイン</a>
</#if>
						</li>
						<li class="last"><a href="/admin/login/help.sg#">Help</a></li>
					</ul>
				</div>
			</div>
			<!-- end #menu -->