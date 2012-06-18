<#macro fieldsetMeta email hash created="" modified="">
	<fieldset>
		<table class="table table-condensed table-bordered table-striped">
			<tbody>
				<tr><th>Email</th><td>${email} <input type="hidden" name="meta-email" value="${email}" /></td></tr>
				<tr><th>Hash</th><td>${hash} <input type="hidden" name="meta-hash" value="${hash}" /></td></tr>
				<tr><th>Created</th><td>${created}</td></tr>
				<tr><th>Modified</th><td>${modified}</td></tr>
			</tbody>
		</table>
	</fieldset>
</#macro>


<#macro fieldsetPersonal firstName="" middleName="" lastName="" dateofbirth="" gender="" nationality="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label">First name</label>
			<div class="controls">
				<input type="text" class="span6" name="personal-firstName" value="${firstName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Middle name</label>
			<div class="controls">
				<input type="text" class="span6" name="personal-middleName" value="${middleName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Last name</label>
			<div class="controls">
				<input type="text" class="span6" name="personal-lastName" value="${lastName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Birth</label>
			<div class="controls">
				<input type="date" class="span6" name="personal-dateofbirth" value="${dateofbirth}"/>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Gender</label>
			<div class="controls">
				<label class="radio"><input type="radio" name="personal-gender" value="male" ${(gender=="male")?string('checked', '')}>Male</label>
				<label class="radio"><input type="radio" name="personal-gender" value="female" ${(gender=="female")?string('checked', '')}>Female</label>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Nationality</label>
			<div class="controls">
				<input type="text" class="span6" name="personal-nationality" value="${nationality}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetAddress street="" city="" zip="" state="" description="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label">Street</label>
			<div class="controls">
				<input type="text" class="span6" name="address-street" value="${street}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">City</label>
			<div class="controls">
				<input type="text" class="span6" name="address-city" value="${city}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Zip</label>
			<div class="controls">
				<input type="text" class="span6" name="address-zip" value="${zip}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">State</label>
			<div class="controls">
				<input type="text" class="span6" name="address-state" value="${state}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Description</label>
			<div class="controls">
				<input type="text" class="span6" name="address-description" value="${description}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetContactPhone type="" content="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Phone</label>
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on">type</span><input type="text" name="contact-phone-type" value="${type}" style="width: 80px;">
				</div>
				<input type="text" style="width: 48.9362%;" name="contact-phone-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetContactEmail content="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Email</label>
			<div class="controls">
				<input type="text" class="span6" name="contact-email-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetContactWebsite content="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Website</label>
			<div class="controls">
				<input type="text" class="span6" name="contact-website-content" value="${content}">
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetPeriod parent period="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<label class="control-label">Period from</label>
			<div class="controls">
				<div class="input-prepend"><span class="add-on">YYYY</span><input type="text" name="${parent}-period-from-year" value="<#if (period?length > 0 && period.from.year?length > 0 && period.from.year > 0)>${period.from.year?c}</#if>" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">MM</span><input type="text" name="${parent}-period-from-month" value="<#if (period?length > 0 && period.from.month?length > 0 && period.from.month > 0)>${period.from.month?c}</#if>" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">DD</span><input type="text" name="${parent}-period-from-day" value="<#if (period?length > 0 && period.from.day?length > 0 && period.from.day > 0)>${period.from.day?c}</#if>" style="width: 40px;"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Period to</label>
			<div class="controls">
				<div class="input-prepend"><span class="add-on">YYYY</span><input type="text" name="${parent}-period-to-year" value="<#if (period?length > 0 && period.to.year?length > 0 && period.to.year > 0)>${period.to.year?c}</#if>" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">MM</span><input type="text" name="${parent}-period-to-month" value="<#if (period?length > 0 && period.to.month?length > 0 && period.to.month > 0)>${period.to.month?c}</#if>" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">DD</span><input type="text" name="${parent}-period-to-day" value="<#if (period?length > 0 && period.to.day?length > 0 && period.to.day > 0)>${period.to.day?c}</#if>" style="width: 40px;"></div>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetWork period="" employer="" position="" activities="" sector="">
	<fieldset class="well form-horizontal">
		<i class="icon icon-minus-sign template-trigger-remove"></i>
		<@fieldsetPeriod parent="work" period=period />
		<div class="control-group">
			<label class="control-label">Employer</label>
			<div class="controls">
				<input type="text" class="span6" name="work-employer" value="${employer}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Position</label>
			<div class="controls">
				<input type="text" class="span6" name="work-position" value="${position}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Activities</label>
			<div class="controls">
				<textarea class="span12" name="work-activities">${activities}</textarea>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Sector</label>
			<div class="controls">
				<input type="text" class="span6" name="work-sector" value="${sector}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetEducation period="" organisation="" description="">
	<fieldset class="well form-horizontal">
		<i class="icon icon-minus-sign template-trigger-remove"></i>
		<@fieldsetPeriod parent="education" period=period />
		<div class="control-group">
			<label class="control-label">Organisation</label>
			<div class="controls">
				<input type="text" class="span6" name="education-organisation" value="${organisation}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Description</label>
			<div class="controls">
				<textarea class="span12" name="education-description">${description}</textarea>
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetSkill name="" content="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<div class="controls controls-wide">
				<i class="icon icon-minus-sign template-trigger-remove"></i>
				<div class="input-prepend">
					<span class="add-on">name</span><input type="text" name="skill-name" value="${name}" style="width: 100px;">
				</div>
				<textarea class="span12" name="skill-content">${content}</textarea>
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetLanguage level="" content="">
	<fieldset class="form-horizontal">
		<div class="control-group">
			<div class="controls controls-wide">
				<i class="icon icon-minus-sign template-trigger-remove"></i>
				<select name="language-level">
					<option value="novice">Novice</option>
					<option value="beginner">Beginner</option>
					<option value="intermediate">Intermediate</option>
					<option value="advanced">Advanced</option>
				</select>
				<input type="text" style="width: 48.9362%;" name="language-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

