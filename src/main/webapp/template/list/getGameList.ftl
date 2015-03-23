
						<table class="listing" id="space">
							<thead>
							<tr>
								<th align="left" scope="col">ゲームID</th>
								<th align="left" scope="col">ドメイン</th>
								<th align="left" scope="col">タイトル</th>
							    <th align="left" scope="col">更新日</th>
							    <th align="left" scope="col">登録日</th>
							</tr>
							</thead>
							<tbody>
<#if model.hadoopGameList?has_content>
<#list model.hadoopGameList as hadoopGame>
							<tr>
								<td>
								<a href="editGame.sg?gameId=${hadoopGame.gameId?if_exists}"><b style="color:red;">${hadoopGame.gameId?if_exists}<b></a>
								</td>
								<td>${hadoopGame.gameDomain?if_exists}</td>
								<td>${hadoopGame.gameTitle?if_exists}</td>
								<td>${hadoopGame.updateTime?if_exists?datetime}</td>
								<td>${hadoopGame.insertTime?if_exists?datetime}</td>
							</tr>
</#list>
</#if>
							</tbody>
						</table>