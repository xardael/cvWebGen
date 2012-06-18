<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">

	<div class="heading"><h2>Viewing CV: ${(cv.personal.firstName)!""} ${(cv.personal.lastName)!""}</h2></div>

	<section class="row-fluid" id="viewer-controls">
		<p><a class="btn btn-danger" href="/editor/${cv.meta.hash}/?key=${cv.meta.key}">Edit CV [DEBUG]</a> <a class="btn" href="/export/${cv.meta.hash}.pdf">Export to PDF</a> <a class="btn" href="/viewer/${cv.meta.hash}/export.xml">Export to XML</a> </p>
	</section>

	<div class="row-fluid well">
		<section class="viewer span6">
			<header><h3>Personal & Contact</h3></header>
			<dl><dt>Name</dt><dd>${(cv.personal.firstName)!"-"} ${(cv.personal.middleName)!"-"} ${(cv.personal.lastName)!"-"}</dd></dl>
			<dl><dt>Birth</dt><dd>${(cv.personal.dateofbirth)!"-"}</dd></dl>
			<dl><dt>Gender</dt><dd>${(cv.personal.gender=="male")?string('male', "female")}</dd></dl>
			<dl><dt>Nationality</dt><dd>${(cv.personal.nationality)!"-"}</dd></dl>
			<hr />
			<dl>
				<dt>Address</dt>
				<dd>${(cv.address.street)!"-"}<br />${(cv.address.city)!"-"}<br />${(cv.address.zip)!"-"}<br />${(cv.address.state)!"-"}<br />${(cv.address.description)!"-"}</dd>
			</dl>
			<hr />
			<dl>
				<dt>Phones</dt>
				<dd><#list cv.getContacts().getPhoneArray() as phone><div>(${phone.getType()!"-"}) ${phone.getStringValue()!""}</div></#list></dd>
			</dl>
			<dl>
				<dt>Emails</dt>
				<dd><#list cv.getContacts().getEmailArray() as email><div>${email!""}</div></#list></dd>
			</dl>
			<dl>
				<dt>Website</dt>
				<dd><#list cv.getContacts().getWebsiteArray() as website><div>${website!""}</div></#list></dd>
			</dl>
		</section>

		<section class="viewer span6">
			<header><h3>Languages & Skills</h3></header>
			<dl>
				<dt>Languages</dt>
				<dd><#list cv.getLanguages().getLanguageArray() as language><div>${language.getStringValue()!""} (${language.getLevel()!""})</div></#list></dd>
			</dl>
			<hr />
			<dl>
				<#list cv.getSkills().getSkillArray() as skill>
					<dt>${skill.getName()!""}</dt>
					<dd>${skill.getStringValue()!""}</dd>
				</#list>
			</dl>
		</section>
	</div>

	<div class="row-fluid">
		<section class="viewer span6">
			<header><h3>Work experiences</h3></header>
			<#list cv.getWorks().getWorkArray() as work>
			<div class="well clearfix">
				<@period period=work.period />
				<dl><dt>Employer</dt><dd>${work.employer}</dd></dl>
				<dl><dt>Position</dt><dd>${work.position}</dd></dl>
				<dl><dt>Activities</dt><dd>${work.activities}</dd></dl>
				<dl><dt>Sector</dt><dd>${work.sector}</dd></dl>
			</div>
			</#list>
		</section>

		<section class="viewer span6">
			<header><h3>Education</h3></header>
			<#list cv.getEducations().getEducationArray() as education>
			<div class="well clearfix">
				<@period period=education.period />
				<dl><dt>Organisation</dt><dd>${education.organisation!"-"}</dd></dl>
				<dl><dt>Description</dt><dd>${education.description!"-"}</dd></dl>
			</div>
			</#list>
		</section>
	</div>
</div>

<#macro period period>
	<dl><dt>Period</dt><dd>${period.from.year?c!""}/${period.from.month?c!""}/${period.from.day?c!""} to ${period.to.year?c!""}/${period.to.month?c!""}/${period.to.day?c!""}</dd></dl>
</#macro>

<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">