
						<table class="listing" id="space">
							<thead>
							<tr>
								<th align="left" scope="col">管理者ID</th>
								<th align="left" scope="col">ゲームＩＤ</th>
								<th align="left" scope="col">名前</th>
								<th align="left" scope="col">メールアドレス</th>
								<th align="left" scope="col">権限</th>
							    <th align="left" scope="col">更新日</th>
							    <th align="left" scope="col">登録日</th>
							</tr>
							</thead>
							<tbody>
<#if model.hadoopAdminList?has_content>
<#list model.hadoopAdminList as hadoopAdmin>
							<tr>
								<td>
									<a href="editAdmin.sg?adminId=${hadoopAdmin.adminId?if_exists}&gameId=${hadoopAdmin.gameId?if_exists}"><b style="color:red;">${hadoopAdmin.adminId?if_exists}<b></a>
								</td>
								<td>${hadoopAdmin.gameId?if_exists}</td>
								<td>${hadoopAdmin.adminName?if_exists}</td>
								<td>${hadoopAdmin.adminMail?if_exists}</td>
<#if hadoopAdmin.adminStatusFlag == "0">
								<td>Semi管理者</td>
<#else>
								<td>Super管理者</td>
</#if>
								<td>${hadoopAdmin.updateTime?if_exists?datetime}</td>
								<td>${hadoopAdmin.insertTime?if_exists?datetime}</td>
							</tr>
</#list>
</#if>
							<tbody>
						</table>