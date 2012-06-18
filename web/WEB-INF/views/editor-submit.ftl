<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<#if status == "ok">
		<div class="heading"><h2>Your CV has been created</h2></div>
		<section class="row-fluid">
			<table class="table table-bordered">
				<tbody>
					<tr><th>View</th><td> <a href="/viewer/${cv.meta.hash}/">/viewer/${cv.meta.hash}/</a> </td></tr>
					<tr><th>Edit</th><td> <a href="/editor/${cv.meta.hash}/?key=${cv.meta.key}">/editor/${cv.meta.hash}/?key=${cv.meta.key}</a> </td></tr>
				</tbody>
			</table>
		</section>

		<section class="row-fluid">
			<h3>XML Preview</h3>
			<pre>${xml?html!"No XML"}</pre>
		</section>
	<#elseif status == "error-duplicateHash">
		<h1>Error when creating CV - duplicate hash</h1>
		<p>CV with this hash is already created. You can view it: <a href="/viewer/${cv.meta.hash}/">/viewer/${cv.meta.hash}/</a></p>
	</#if>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">