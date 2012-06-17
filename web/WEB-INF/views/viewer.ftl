<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<#include "/layout/fieldsets.ftl">
<div class="container">

	<section class="row-fluid" id="editor-meta">
	<@meta hash=cv.meta.hash key=cv.meta.key email=(cv.meta.email) privacy=(cv.meta.privacy) created=(cv.meta.created)!"Brand new" modified=(cv.meta.modified)!"Brand new" />
	</section>

	<section class="editor row-fluid" id="editor-personal">
		<header class="span3">
			<h2>Personal</h2>
		</header>
		<div class="placeholder span9"><@fieldsetPersonal firstName=(cv.personal.firstName)!"Undefined" middleName=(cv.personal.middleName)!"Undefined" lastName=(cv.personal.lastName)!"Undefined" dateofbirth=(cv.personal.dateofbirth)!"Undefined" gender=(cv.personal.gender)!"Undefined" nationality=(cv.personal.nationality)!"Undefined" /></div>
	</section>

</div>



<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">