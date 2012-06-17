<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">

	<section class="viewer row-fluid" id="viewer-personal">
		<header class="span3">
			<h2>Personal</h2>
		</header>
		<div class="placeholder span9">
			<dl><dt>firstName</dt><dd>${(cv.personal.firstName)!""}</dd></dl>
			<dl><dt>middleName</dt><dd>${(cv.personal.middleName)!""}</dd></dl>
			<dl><dt>lastName</dt><dd>${(cv.personal.lastName)!""}</dd></dl>
			<dl><dt>dateofbirth</dt><dd>${(cv.personal.dateofbirth)!""}</dd></dl>
			<dl><dt>gender</dt><dd>${(cv.personal.gender=="male")?string('male', "female")}</dd></dl>
			<dl><dt>nationality</dt><dd>${(cv.personal.nationality)!""}</dd></dl>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-address">
		<header class="span3">
			<h2>Address</h2>
		</header>
		<div class="placeholder span9">
			<dl><dt>street</dt><dd>${(cv.address.street)!""}</dd></dl>
			<dl><dt>city</dt><dd>${(cv.address.city)!""}</dd></dl>
			<dl><dt>zip</dt><dd>${(cv.address.zip)!""}</dd></dl>
			<dl><dt>state</dt><dd>${(cv.address.state)!""}</dd></dl>
			<dl><dt>description</dt><dd>${(cv.address.description)!""}</dd></dl>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-contacts">
		<header class="span3">
			<h2>Contacts</h2>
		</header>
		<div class="placeholder span9">
			<#list cv.getContacts().getPhoneArray() as phone>
				<dl><dt>Phone</dt><dd>(${phone.getType()!""}) ${phone.getStringValue()!""}</dd></dl>
			</#list>
			<#list cv.getContacts().getEmailArray() as email>
				<dl><dt>Email</dt><dd>${email!""}</dd></dl>
			</#list>
			<#list cv.getContacts().getWebsiteArray() as website>
				<dl><dt>Website</dt><dd>${website!""}</dd></dl>
			</#list>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-works">
		<header class="span3">
			<h2>Works</h2>
		</header>
		<div class="placeholder span9">
			<#list cv.getWorks().getWorkArray() as work>
				<div class="viewer-work">
					<@period period=work.period />
					<dl><dt>employer</dt><dd>${work.employer}</dd></dl>
					<dl><dt>position</dt><dd>${work.position}</dd></dl>
					<dl><dt>activities</dt><dd>${work.activities}</dd></dl>
					<dl><dt>sector</dt><dd>${work.sector}</dd></dl>
			</#list>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-educations">
		<header class="span3">
			<h2>Educations</h2>
		</header>
		<div class="placeholder span9">
		<#list cv.getEducations().getEducationArray() as education>
		<div class="viewer-work">
			<@period period=education.period />
			<dl><dt>organisation</dt><dd>${education.organisation}</dd></dl>
			<dl><dt>description</dt><dd>${education.description}</dd></dl>
		</#list>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-skills">
		<header class="span3">
			<h2>Skills</h2>
		</header>
		<div class="placeholder span9">
		<#list cv.getSkills().getSkillArray() as skill>
		<div class="viewer-work">
			<dl><dt>${skill.getName()!""}</dt><dd>${skill.getStringValue()!""}</dd></dl>
		</#list>
		</div>
	</section>

	<section class="viewer row-fluid" id="viewer-languages">
		<header class="span3">
			<h2>Languages</h2>
		</header>
		<div class="placeholder span9">
		<#list cv.getLanguages().getLanguageArray() as language>
		<div class="viewer-work">
			<dl><dt>${language.getLevel()!""}</dt><dd>${language.getStringValue()!""}</dd></dl>
		</#list>
		</div>
	</section>

</div>

<#macro period period>
	<dl><dt>Period</dt><dd>${period.from.year!""}/${period.from.month!""}/${period.from.day!""} to ${period.to.year!""}/${period.to.month!""}/${period.to.day!""}</dd></dl>
</fieldset>

</#macro>


<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">