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

			<!-- start #content -->
				<div id="content">
					<fieldset id="fieldset1">
					<legend>Itunes Ranking</legend>
					<b style="color:red" >${errorMessage?if_exists}</b>
					<div style="float:left;">
					<form action="/admin/itunes/registerAppAdmin.sg" method="post">
					<table class="listing" id="space" style="width:350pt;">
						<thead>
							<th align="center" scope="col" colspan=4>トップ無料, ID入力:<input type="text" name="appId" value="" maxlength="14" size="16"/></th>
							<th align="center" scope="col"><input type="submit" value="登録" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" /></th>
						</thead>
						<tbody>
							<tr align="center" scope="col">
								<td>チェック</td>
								<td>ランキング</td>
								<td>アプリＩＤ</td>
								<td>アプリイメージ</td>
								<td>タイトル</td>
							</tr>
<#if model.selectFree?has_content>
<#list model.selectFree as ranking>
<#if ranking?has_content>
<#list ranking as rank>
							<tr align="left" scope="col">
								<td><input type="checkbox" name="appId" value="${rank.id.imId?if_exists}"></td>
								<td>${((model.paging.nowPage?number-1) * 10) + (rank_index + 1)}</td>
								<td>${rank.id.imId?if_exists}</td>
								<td>
								<#if rank.imImageList?has_content>
									<#list rank.imImageList as imImage>
										<#if imImage_index = 2>
										<a href="/admin/itunes/appDataDetails.sg?id=${rank.id.imId?if_exists}&family=allFree">
										<img src="${imImage.label?if_exists}" alt="image">
										</a>
										</#if>
									</#list>
								</#if>
								</td>
								<td>${rank.imName.label?if_exists}</td>
							</tr>
</#list>
</#if>
</#list>
</#if>
						</tbody>
					</table>
					<input type="hidden" name="appAdminGenre" value="allFree">
					</form>
					</div>

					<div>
					<form action="/admin/itunes/registerAppAdmin.sg" method="post">
					<table class="listing" id="space" style="width:350pt;">
						<thead>
							<th align="center" scope="col" colspan=4>トップセールス, ID入力:<input type="text" name="appId" value="" maxlength="14" size="16"/></th>
							<th align="center" scope="col"><input type="submit" value="登録" style="width:60pt;height:20pt;background-color:#566D7E;color:white;" /></th>
						</thead>
						<tbody>
							<tr align="center" scope="col">
								<td>チェック</td>
								<td>ランキング</td>
								<td>アプリＩＤ</td>
								<td>アプリイメージ</td>
								<td>タイトル</td>
							</tr>
<#if model.selectGrossing?has_content>
<#list model.selectGrossing as ranking>
<#if ranking?has_content>
<#list ranking as rank>
							<tr align="left" scope="col">
								<td><input type="checkbox" name="appId" value="${rank.id.imId?if_exists}"></td>
								<td>${((model.paging.nowPage?number-1) * 10) + (rank_index + 1)}</td>
								<td>${rank.id.imId?if_exists}</td>
								<td>
								<#if rank.imImageList?has_content>
									<#list rank.imImageList as imImage>
										<#if imImage_index = 2>
										<a href="/admin/itunes/appDataDetails.sg?id=${rank.id.imId?if_exists}&family=allGrossing">
										<img src="${imImage.label?if_exists}" alt="image">
										</a>
										</#if>
									</#list>
								</#if>
								</td>
								<td>${rank.imName.label?if_exists}</td>
							</tr>
</#list>
</#if>
</#list>
</#if>
						</tbody>
					</table>
					<input type="hidden" name="appAdminGenre" value="allGrossing">
					</form>
					</div>

					<div class="pagination">
					<#if model?exists>
						<#if model.paging?exists>
						<#if model.paging.prevPage?exists>
							<a href="/admin/itunes/rankingApp.sg?nowPage=${model.paging.prevPage.nowPage}&allCount=${model.paging.allCount?c}" title="前のページへ" accesskey="*"><span class="next">前へ</span></a>
						</#if>
						<#if model.paging.pagingInfoList?has_content>
							<#list model.paging.pagingInfoList as pageList>
								<#if model.paging.nowPage?if_exists == pageList.pageNumber?if_exists>
									<span class="current">${pageList.pageNumber}</span>
								<#else>
									<a href="/admin/itunes/rankingApp.sg?nowPage=${pageList.pageNumber}&allCount=${model.paging.allCount?c}">${pageList.pageNumber}</a>
								</#if>
							</#list>
						</#if>
			 			<#if model.paging.nextPage?exists>
							<a href="/admin/itunes/rankingApp.sg?nowPage=${model.paging.nextPage.nowPage}&allCount=${model.paging.allCount?c}" accesskey="#" title="次のページへ"><span class="next">次へ</span></a>
						</#if>
						</#if>
					</#if>
					</div>

					<div style="clear: both;">&nbsp;</div>
					</fieldset>
				</div>
				<!-- end #content -->

			<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
</div>

<!-- start footer -->
<#include "../common/footer.ftl">
<!-- end footer -->
</body>
</html>