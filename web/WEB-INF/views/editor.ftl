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

<form method="POST" action="/editor/${cv.meta.hash}/">
	<div class="container">
		<section class="row-fluid" id="editor-head">
			<#if !(cv.meta.created)??>
				<div class="heading"><h2>Creating new CV</h2></div>
				<div class="alert alert-block">
					<h4 class="alert-heading">CV is not saved yet!</h4>
					<p>Your CV is not saved yet. You have to input required fields and than save cv. Otherwise all CV will be lost.</p>
				</div>
				<input type="hidden" name="meta-key" value="${cv.meta.key}" />
			<#else>
				<div class="heading"><h2>Editing existing CV</h2></div>
				<#if urlKey == cv.meta.key>
					<div class="alert alert-info alert-block">
						<h4 class="alert-heading">You are authenticated to edit this CV.</h4>
						<p>Key in URL matches key saved in database.</p>
						<input type="hidden" name="urlKey" value="${urlKey}" />
					</div>
				<#else>
					<div class="alert alert-error alert-block">
						<h4 class="alert-heading">You are NOT authenticated to edit this CV.</h4>
						<p>Key in URL DOES NOT match key saved in database.</p>
					</div>
				</#if>
			</#if>
		</section>

		<section class="editor row-fluid" id="editor-meta">
			<header class="span3">
				<h3>Meta</h3>
				<p>Cannot be changed.</p>
			</header>
			<div class="placeholder span9"><@fieldsetMeta email=(cv.meta.email) hash=(cv.meta.hash) created=(cv.meta.created) modified=(cv.meta.modified) /></div>
		</section>

		<section class="editor row-fluid" id="editor-personal">
			<header class="span3">
				<h3>Personal</h3>
			</header>
			<div class="placeholder span9"><@fieldsetPersonal firstName=(cv.personal.firstName)!"" middleName=(cv.personal.middleName)!"" lastName=(cv.personal.lastName)!"" dateofbirth=(cv.personal.dateofbirth)!"" gender=(cv.personal.gender)!"" nationality=(cv.personal.nationality)!"" /></div>
		</section>

		<section class="editor row-fluid" id="editor-address">
			<header class="span3">
				<h3>Address</h3>
			</header>
			<div class="placeholder span9"><@fieldsetAddress street=(cv.address.street)!"" city=(cv.address.city)!""  zip=(cv.address.zip)!""  state=(cv.address.state)!""  description=(cv.address.description)!"" /></div>
		</section>

		<section class="editor row-fluid" id="editor-contacts">
			<header class="span3">
				<h3>Contacts</h3>
				<p><a class="btn btn-mini template-trigger-add" rel="phone" href="#"><i class="icon icon-plus"></i> phone</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="email" href="#"><i class="icon icon-plus"></i> email</a></p>
				<p><a class="btn btn-mini template-trigger-add" rel="website" href="#"><i class="icon icon-plus"></i> website</a></p>
			</header>
			<div class="placeholder span9">
				<#if cv.getContacts()??>
					<#list cv.getContacts().getPhoneArray() as phone>
						<@fieldsetContactPhone type=(phone.type) content=(phone.getStringValue()) />
					</#list>
				</#if>
				<#if cv.getContacts()??>
					<#list cv.getContacts().getEmailArray() as email>
						<@fieldsetContactEmail content=(email) />
					</#list>
				</#if>
				<#if cv.getContacts()??>
					<#list cv.getContacts().getWebsiteArray() as website>
						<@fieldsetContactWebsite content=(website) />
					</#list>
				</#if>
			</div>
		</section>


		<section class="editor row-fluid" id="editor-works">
			<header class="span3">
				<h3>Works</h3>
				<p><a class="btn btn-mini template-trigger-add" rel="work" href="#"><i class="icon icon-plus"></i> work</a></p>
			</header>
			<div class="placeholder span9">
				<#if cv.getWorks()??>
					<#list cv.getWorks().getWorkArray() as work>
						<@fieldsetWork period=(work.period) employer=(work.employer) position=(work.position) activities=(work.activities) sector=(work.sector) />
					</#list>
				</#if>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-educations">
			<header class="span3">
				<h3>Educations</h3>
				<p><a class="btn btn-mini template-trigger-add" rel="education" href="#"><i class="icon icon-plus"></i> education</a></p>
			</header>
			<div class="placeholder span9">
				<#if cv.getEducations()??>
					<#list cv.getEducations().getEducationArray() as education>
						<@fieldsetEducation period=(education.period) organisation=(education.organisation) description=(education.description) />
					</#list>
				</#if>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-skills">
			<header class="span3">
				<h3>Skills</h3>
				<p><a class="btn btn-mini template-trigger-add" rel="skill" href="#"><i class="icon icon-plus"></i> skill</a></p>
			</header>
			<div class="placeholder span9">
				<#if cv.getSkills()??>
					<#list cv.getSkills().getSkillArray() as skill>
						<@fieldsetSkill name=(skill.name) content=(skill.getStringValue()) />
					</#list>
				</#if>
			</div>
		</section>

		<section class="editor row-fluid" id="editor-languages">
			<header class="span3">
				<h3>Languages</h3>
				<p><a class="btn btn-mini template-trigger-add" rel="language" href="#"><i class="icon icon-plus"></i> language</a></p>
			</header>
			<div class="placeholder span9">
				<#if cv.getLanguages()??>
					<#list cv.getLanguages().getLanguageArray() as language>
						<@fieldsetLanguage level=(language.name) content=(language.getStringValue()) />
					</#list>
				</#if>
			</div>
		</section>

		<div class="form-actions row-fluid form-inline" style="text-align: right;">
			<label class="radio"><input type="radio" name="meta-privacy" value="public" ${(cv.meta.privacy=="public")?string('checked', "")}>public</label>
			<label class="radio"><input type="radio" name="meta-privacy" value="private" ${(cv.meta.privacy=="private")?string('checked', '')}>private</label>
			<#if !(cv.meta.created)??>
				<button type="submit" class="btn btn-primary" style="width: 200px;">Create new CV</button>
			<#else>
				<#if urlKey == cv.meta.key>
					<button type="submit" class="btn btn-primary" style="width: 200px;">Edit CV</button>
				<#else>
					<button type="submit" class="btn btn-danger disabled" style="width: 200px;">Forbidden, keys do not match</button>
				</#if>
			</#if>
		</div>
	</div>
</form>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">