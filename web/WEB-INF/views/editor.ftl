<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<#include "/layout/fieldsets.ftl">

<div class="hide">
	<div class="template" data-trigger="phone"><@fieldsetContactPhone type="" content="" /></div>
	<div class="template" data-trigger="email"><@fieldsetContactEmail content=""/></div>
	<div class="template" data-trigger="website"><@fieldsetContactWebsite content=""/></div>
	<div class="template" data-trigger="work"><@fieldsetWork period="" employer="" position="" activities="" sector=""/></div>
	<div class="template" data-trigger="education"><@fieldsetEducation period="" organisation="" description=""/></div>
	<div class="template" data-trigger="skill"><@fieldsetSkill name="" content="" /></div>
	<div class="template" data-trigger="language"><@fieldsetLanguage level="" content="" /></div>
</div>

<div class="container">
	<#if new??>
		<div class="alert alert-block">
			<h4 class="alert-heading">CV is not saved yet!</h4>
			<p>Your CV is not saved yet. You have to input required fields and than save cv.</p>
		</div>
	</#if>

	<form class="form-horizontal" method="POST" action="/editor/${cv.meta.hash}/">
		<section class="row-fluid" id="editor-meta">
			<@meta hash=cv.meta.hash key=cv.meta.key email=(cv.meta.email) privacy=(cv.meta.privacy) created=(cv.meta.created)!"Brand new" modified=(cv.meta.modified)!"Brand new" />
		</section>

		<section class="editor row-fluid" id="editor-personal">
			<header class="span3">
				<h2>Personal</h2>
			</header>
			<div class="placeholder span9"><@fieldsetPersonal firstName=(cv.personal.firstName)!"" middleName=(cv.personal.middleName)!"" lastName=(cv.personal.lastName)!"" dateofbirth=(cv.personal.dateofbirth)!"" gender=(cv.personal.gender)!"" nationality=(cv.personal.nationality)!"" /></div>
		</section>

		<section class="editor row-fluid" id="editor-address">
			<header class="span3">
				<h2>Address</h2>
			</header>
			<div class="placeholder span9"><@fieldsetAddress street=(cv.address.street)!"" city=(cv.address.city)!""  zip=(cv.address.zip)!""  state=(cv.address.state)!""  description=(cv.address.description)!"" /></div>
		</section>

		<section class="editor row-fluid" id="editor-contacts">
			<header class="span3">
				<h2>Contacts</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="phone" href="#"><i class="icon icon-plus"></i> phone</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="email" href="#"><i class="icon icon-plus"></i> email</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="website" href="#"><i class="icon icon-plus"></i> website</a></p>
			</header>
			<div class="placeholder span9"></div>
		</section>


		<section class="editor row-fluid" id="editor-works">
			<header class="span3">
				<h2>Works</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="work" href="#"><i class="icon icon-plus"></i> work</a></p>
			</header>
			<div class="placeholder span9"></div>
		</section>

		<section class="editor row-fluid" id="editor-educations">
			<header class="span3">
				<h2>Educations</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="education" href="#"><i class="icon icon-plus"></i> education</a></p>
			</header>
			<div class="placeholder span9"></div>
		</section>

		<section class="editor row-fluid" id="editor-skills">
			<header class="span3">
				<h2>Skills</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="skill" href="#"><i class="icon icon-plus"></i> skill</a></p>
			</header>
			<div class="placeholder span9"></div>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-languages">
			<header class="span3">
				<h2>Languages</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="language" href="#"><i class="icon icon-plus"></i> language</a></p>
			</header>
			<div class="placeholder span9">
				<div class="debug"></div>
			</div>
		</section>

		<div class="form-actions">
			<button type="submit" class="btn btn-primary">Save</button>
			<button class="btn">Cancel</button>
		</div>
	</form>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">