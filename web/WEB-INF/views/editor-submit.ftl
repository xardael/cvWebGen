<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">
	<h1>Your CV has been created</h1>
	<p>You can view it <a href="/viewer/${cv.meta.hash}/">/viewer/${cv.meta.hash}/</a></p>
	<p>You can edit it <a href="/editor/${cv.meta.hash}/&key=${cv.meta.key}">/editor/${cv.meta.hash}/</a></p>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">