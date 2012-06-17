<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<#include "/layout/fieldsets.ftl">

<div class="hide">
	<div class="template" data-trigger="phone"><@fieldsetContactPhone /></div>
	<div class="template" data-trigger="email"><@fieldsetContactEmail /></div>
	<div class="template" data-trigger="website"><@fieldsetContactWebsite /></div>
	<div class="template" data-trigger="work"><@fieldsetWork /></div>
	<div class="template" data-trigger="education"><@fieldsetEducation /></div>
	<div class="template" data-trigger="skill"><@fieldsetSkill /></div>
	<div class="template" data-trigger="language"><@fieldsetLanguage /></div>
</div>

<div class="container">
	<#if new??>
		<div class="alert alert-block">
			<h4 class="alert-heading">CV is not saved yet!</h4>
			<p>Your CV is not saved yet. You have to input required fields and than save cv.</p>
		</div>
	</#if>

	<form class="form-horizontal" method="POST">
		<section class="row-fluid" id="editor-meta">
			<@meta hash=cv.meta.hash key=cv.meta.key email=(cv.meta.email) privacy=(cv.meta.privacy) created=(cv.meta.created)!"Brand new" modified=(cv.meta.modified)!"Brand new" />
		</section>

		<section class="editor row-fluid" id="editor-personal">
			<header class="span3">
				<h2>Personal</h2>
			</header>
			<div class="placeholder span9"><@fieldsetPersonal firstName=(cv.personal.firstName)!"Pavel" middleName=(cv.personal.middleName)!"Strajk" lastName=(cv.personal.lastName)!"Dolecek" dateofbirth=(cv.personal.dateofbirth)!"1990-04-12" gender=(cv.personal.gender)!"male" nationality=(cv.personal.nationality)!"nationality" /></div>
		</section>

		<section class="editor row-fluid" id="editor-address">
			<header class="span3">
				<h2>Address</h2>
			</header>
			<div class="placeholder span9"><@fieldsetAddress street=(cv.address.street)!"Josefa Homoly" city=(cv.address.city)!"Kromeriz"  zip=(cv.address.zip)!"76701"  state=(cv.address.state)!"Czech Republic"  description=(cv.address.description)!"Second floor" /></div>
		</section>

		<section class="editor row-fluid" id="editor-contacts">
			<header class="span3">
				<h2>Contacts</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="phone" href="#"><i class="icon icon-plus"></i> phone</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="email" href="#"><i class="icon icon-plus"></i> email</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="website" href="#"><i class="icon icon-plus"></i> website</a></p>
			</header>
			<div class="placeholder span9">
				<div class="debug">
					<@fieldsetContactPhone type="home" content="573 11 22 33" />
					<@fieldsetContactPhone type="mobile" content="777 22 33 44" />
					<@fieldsetContactPhone type="emergency" content="158" />
					<@fieldsetContactEmail content="strajk@email.cz" />
					<@fieldsetContactEmail content="strajk@me.com" />
					<@fieldsetContactEmail content="straaajk@gmail.com" />
					<@fieldsetContactWebsite content="www.strajk.cz" />
					<@fieldsetContactWebsite content="www.facebook.com/strajk" />
					<@fieldsetContactPhone type="" content="123 456" />
					<@fieldsetContactPhone type="" content="123 789" />
					<@fieldsetContactPhone type="last" content="999" />
				</div>
			</div>
		</section>


		<section class="editor row-fluid" id="editor-works">
			<header class="span3">
				<h2>Works</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="work" href="#"><i class="icon icon-plus"></i> work</a></p>
			</header>
			<div class="placeholder span9">
				<div class="debug">
					<@fieldsetWork employer="Google" position="Developer" activities="Hard core developing" sector="Search engine" />
					<@fieldsetWork employer="Seznam" position="Programator" activities="Nobody knows" sector="Firmy.cz" />
					<@fieldsetWork employer="Masaryk University" position="Teacher" activities="Teach some motherfuckers" sector="FI" />
				</div>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-educations">
			<header class="span3">
				<h2>Educations</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="education" href="#"><i class="icon icon-plus"></i> education</a></p>
			</header>
			<div class="placeholder span9">
				<div class="debug">
					<@fieldsetEducation organisation="FI MU" description="Study hard, party hard" />
					<@fieldsetEducation organisation="Gymnazium Kromeriz" description="Party hard, no need to study" />
				</div>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-skills">
			<header class="span3">
				<h2>Skills</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="skill" href="#"><i class="icon icon-plus"></i> skill</a></p>
			</header>
			<div class="placeholder span9">
			 	<div class="debug">
			 		<@fieldsetSkill name="Bike" content="Some lorem" />
			 		<@fieldsetSkill name="Cooking" content="Some lorem ipusm" />
				</div>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-languages">
			<header class="span3">
				<h2>Languages</h2>
				<p><a class="btn btn-mini template-trigger-add" rel="language" href="#"><i class="icon icon-plus"></i> language</a></p>
			</header>
			<div class="placeholder span9">
				<div class="debug">
				 	<@fieldsetLanguage level="advanced" content="English" />
				 	<@fieldsetLanguage level="novice" content="Deutsh" />
				</div>
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