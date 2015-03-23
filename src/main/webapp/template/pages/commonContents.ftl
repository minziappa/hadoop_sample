<#if model.searchFlumePara?has_content>
<#if model.searchFlumePara.searchFlag == "false">
<script type="text/javascript">
function codeAddress() {
	if(confirm("検索結果が2Mを超えています。よろしいでしょうか?")) {
		document.forms[0].searchFlag.value = 'true';
		document.forms[0].submit();
	}else{
		return false;
	}
}
window.onload = codeAddress;
</script>
</#if>
</#if>

			<!-- end #content -->
				<div id="content">
					<fieldset id="fieldset1">
					<legend>ログ検索</legend>
					<div>
					Root Directory /<a href="/admin/log/directoryList.sg?path=/home">home</a>/
					<br/>
<#if model.rootdirectoryList?has_content>
<#list model.rootdirectoryList as rootdirectory>
					<a href="directoryList.sg?path=${rootdirectory.path?if_exists}"><b style="color:red;">${rootdirectory.name?if_exists}</b></a>/
</#list>
</#if>
					<br/>
					${model.dirtoryList?if_exists}
					<br/>

						<form action="/admin/log/searchLogs.sg" method="post">
							検索言葉<input type="text" name="searchWord" value="${model.searchFlumePara?if_exists.searchWord?if_exists}" size="50" />
							<input type="hidden" name="path" value="${model.path?if_exists}">
							<input type="hidden" name="searchFlag" value="${model.searchFlumePara?if_exists.searchFlag?if_exists}">
							<input type="submit" value="検索" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" />
						</form>
					</div>

					<br/>
					<!-- start #list -->
					<div>
<#include "../list/getDirectoryList.ftl">
					<!-- end #list -->
					<textarea id="divPost1" readonly="readonly"  wrap="virtual" cols="100" rows="25">
<#if model.resultSearch?exists>
${model.resultSearch?if_exists}
<#else>
					There is no data.
</#if>
					</textarea>
					</div>
					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
				<!-- end #content -->