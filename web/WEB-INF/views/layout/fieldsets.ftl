<#macro fieldsetPersonal firstName="" middleName="" lastName="" dateofbirth="" gender="" nationality="">
	<fieldset>
		<div class="control-group" data-field="personal-firstName">
			<label class="control-label">firstName</label>
			<div class="controls">
				<input type="text" name="personal-firstName" value="${firstName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="personal-middleName">
			<label class="control-label">middleName</label>
			<div class="controls">
				<input type="text" name="personal-middleName" value="${middleName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="personal-lastName">
			<label class="control-label">lastName</label>
			<div class="controls">
				<input type="text" name="personal-lastName" value="${lastName}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="personal-dateofbirth">
			<label class="control-label">dateofbirth</label>
			<div class="controls">
				<input type="date" name="personal-dateofbirth" value="${dateofbirth}"/>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="personal-gender">
			<label class="control-label">gender</label>
			<div class="controls">
				<select name="personal-gender"> <!-- Select default -->
					<option value="male">male</option>
					<option value="female">female</option>
				</select>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="personal-nationality">
			<label class="control-label">nationality</label>
			<div class="controls">
				<input type="text" name="personal-nationality" value="${nationality}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetAddress street="" city="" zip="" state="" description="">
	<fieldset>
		<div class="control-group" data-field="address-street">
			<label class="control-label">street</label>
			<div class="controls">
				<input type="text" name="address-street" value="${street}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="address-city">
			<label class="control-label">city</label>
			<div class="controls">
				<input type="text" name="address-city" value="${city}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="address-zip">
			<label class="control-label">zip</label>
			<div class="controls">
				<input type="text" name="address-zip" value="${zip}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="address-state">
			<label class="control-label">state</label>
			<div class="controls">
				<input type="text" name="address-state" value="${state}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="address-description">
			<label class="control-label">description</label>
			<div class="controls">
				<input type="text" name="address-description" value="${description}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetContactPhone type="" content="">
	<fieldset>
		<div class="control-group" data-field="contact-phone">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Phone</label>
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on">type</span><input type="text" name="contact-phone-type" value="${type}" style="width: 80px;">
				</div>
				<input type="text" name="contact-phone-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetContactEmail content="">
	<fieldset>
		<div class="control-group" data-field="">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Email</label>
			<div class="controls">
				<input type="text" name="contact-email-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>

<#macro fieldsetContactWebsite content="">
	<fieldset>
		<div class="control-group" data-field="">
			<label class="control-label"><i class="icon icon-minus-sign template-trigger-remove"></i> Website</label>
			<div class="controls">
				<input type="text" name="contact-website-content" value="${content}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetPeriod parent="" fromYear="" fromMonth="" fromDay="" toYear="" toMonth="" toDay="">
	<fieldset>
		<div class="control-group" data-field="${parent}-period-from">
			<label class="control-label">period from</label>
			<div class="controls">
				<div class="input-prepend"><span class="add-on">YYYY</span><input type="text" name="${parent}-period-from-year" value="${fromYear}" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">MM</span><input type="text" name="${parent}-period-from-month" value="${fromMonth}" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">DD</span><input type="text" name="${parent}-period-from-day" value="${fromDay}" style="width: 40px;"></div>
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="work-period-from">
			<label class="control-label">period to</label>
			<div class="controls">
				<div class="input-prepend"><span class="add-on">YYYY</span><input type="text" name="${parent}-period-to-year" value="${toYear}" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">MM</span><input type="text" name="${parent}-period-to-month" value="${toMonth}" style="width: 40px;"></div>
				<div class="input-prepend"><span class="add-on">DD</span><input type="text" name="${parent}-period-to-day" value="${toDay}" style="width: 40px;"></div>
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>


<#macro fieldsetWork employer="" position="" activities="" sector="">
	<fieldset class="well">
		<i class="icon icon-minus-sign template-trigger-remove"></i>
		<@fieldsetPeriod parent="work" />
		<div class="control-group" data-field="work-employer">
			<label class="control-label">employer</label>
			<div class="controls">
				<input type="text" name="work-employer" value="${employer}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="work-position">
			<label class="control-label">position</label>
			<div class="controls">
				<input type="text" name="work-position" value="${position}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="work-activities">
			<label class="control-label">activities</label>
			<div class="controls">
				<input type="text" name="work-activities" value="${activities}">
				<p class="help-block"></p>
			</div>
		</div>
		<div class="control-group" data-field="work-sector">
			<label class="control-label">sector</label>
			<div class="controls">
				<input type="text" name="work-sector" value="${sector}">
				<p class="help-block"></p>
			</div>
		</div>
	</fieldset>
</#macro>