<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<#include "/layout/fieldsets.ftl">

<div class="hide">
	<div class="template" data-trigger="phone"><@fieldsetContactPhone /></div>
	<div class="template" data-trigger="email"><@fieldsetContactEmail /></div>
	<div class="template" data-trigger="website"><@fieldsetContactWebsite /></div>
	<div class="template" data-trigger="work"><@fieldsetWork /></div>
</div>

<div class="container">
	<form class="form-horizontal" method="POST">
		<section class="editor row-fluid" id="editor-personal">
			<header class="span3">
				<h2>Personal</h2>
			</header>
			<div class="placeholder span9"><@fieldsetPersonal/></div>
		</section>

		<section class="editor row-fluid" id="editor-address">
			<header class="span3">
				<h2>Address</h2>
			</header>
			<div class="placeholder span9"><@fieldsetAddress /></div>
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
				<h2>Contacts</h2>
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

		<#--
		<section id="editor-educations">
			<header><h2>Educations</h2></header>
			<@fieldsetEducation />
		</section>

		<section id="editor-skills">
			<header><h2>Skills</h2></header>
			<@fieldsetSkill />
		</section>

		<section id="editor-languages">
			<header><h2>Languages</h2></header>
			<@fieldsetLanguage />
		</section>
		-->

		<div class="form-actions">
			<button type="submit" class="btn btn-primary">Save</button>
			<button class="btn">Cancel</button>
		</div>
   </form>
</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">