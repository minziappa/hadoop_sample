
						<table class="listing" id="space">
							<thead>
							<tr>
								<th align="left" scope="col">Name</th>
								<th align="left" scope="col">Type</th>
								<th align="left" scope="col">Size</th>
								<th align="left" scope="col">Replication</th>
								<th align="left" scope="col">Block Size</th>
							    <th align="left" scope="col">Modification Time</th>
							    <th align="left" scope="col">Permission</th>
							    <th align="left" scope="col">Owner</th>
							    <th align="left" scope="col">Group</th>
							</tr>
							</thead>
							<tbody>
<#if model.directoryList?has_content>
<#list model.directoryList as directory>
							<tr>
								<td>
									<a href="directoryList.sg?path=${directory.path?if_exists}"><b style="color:red;">${directory.name?if_exists}</b></a>
								</td>
								<td>${directory.type?if_exists}</td>
								<td>${directory.size?if_exists}</td>
								<td>${directory.replication?if_exists}</td>
								<td>${directory.blockSize?if_exists}</td>
								<td>${directory.modificationTime?if_exists}</td>
								<td>${directory.permission?if_exists}</td>
								<td>${directory.owner?if_exists}</td>
								<td>${directory.group?if_exists}</td>
							</tr>
</#list>
</#if>
							<tbody>
						</table>