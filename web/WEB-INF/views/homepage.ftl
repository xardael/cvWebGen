<!-- Required model: recentPublicCvs,  -->

<#include "/layout/head.ftl">
<#include "/layout/navigation.ftl">
<div class="container">

	<div class="heading"><h2>Create, manage, export&nbsp;&&nbsp;print CVs.</h2></div>

    <section id="homepage-features" class="row">
        <div class="span3">
            <h4>Java</h4>
            <p>CvWebGen is written entirely in Java. It is based on Spring Framework, uses XmlBeans for XML-Java objects mapping, Log4J for logging, Java Mail for e-mail (via GMail smtp), JUnit for unit testing and Freemarker in View tier.</p>
        </div>
        <div class="span3">
            <h4>BaseX</h4>
            <p>For Persistence tier is used native XML database BaseX. It runs as standalone server and communicate with application via BaseX Java Client library. BaseX support full XQuery 3.0 for accessing data.</p>
        </div>
        <div class="span3">
			<h4>Export <span class="label label-info">LaTeX</span></h4>
			<p>Exporting CVs to PDF is done by XSL Transformation to .tex and then rendering PDF via pdflatex. Standart Europe Curriculum Vitae format (eurocv) is used to maintain standardized look.</p>
		</div>
		<div class="span3">
			<h4>School project</h4>
			<p>CVWebGen was made as Final project for PB138 Modern Markup Languages and Their Applications at Masaryk University, Faculty of Informatics. It is completely open source and can be used for learning purposes.</p>
		</div>
	</section>

	<hr />

    <div class="row">
        <section id="homepage-activity" class="span7">
            <header><h2>Recently generated CVs</h2></header>
            <table class="table table-striped">
                <thead><tr><th>Name</th></tr></thead>
                <tbody>
                <#if (recentPublicCvs)??>
                    <#list recentPublicCvs as cvDoc>
                        <tr>
							<td><a href="/viewer/${cvDoc.cv.meta.hash}/">${cvDoc.cv.personal.firstName!"Unknown"} ${cvDoc.cv.personal.lastName!"Unknown"}</a></td>
						</tr>
                    </#list>
                <#else>
                    <tr><th></th>No data</td></tr>
                </#if>
                </tbody>
            </table>
        </section>
        <div class="span5">
            <section id="homepage-create">
                <header><h2>Create CV</h2></header>
                <div class="well">
                    <p class="intro">Creating CV is <strong>very easy and intuitive</strong>, fast, doesn't require no kind of registration and your data will be safe.</p>
                    <form class="form-vertical" action="/editor/create/" METHOD="POST">
						<input type="hidden" name="do" value="create" />
                        <div class="control-group">
                            <label class="control-label" for="homepage-create-email">E-mail</label>
                            <div class="controls">
								<div class="input-prepend"><span class="add-on">@</span><input type="email" id="homepage-create-email" name="email"></div>
								<p class="help-block"><strong>(Required)</strong> Can NOT be changed later.</p>
							</div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <label class="radio"><dl><dt><input type="radio" name="privacy" value="public" checked="checked">Public</dt><dd>Anyone can search for and view</dd></dl></label>
                                <label class="radio"><dl><dt><input type="radio" name="privacy" value="private">Private</dt><dd>Just who you decide to share.</dd></dl></label>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Create CV</button>
                        </div>
                    </form>
                </div>
            </section>
            <section id="homepage-forget">
                <header><h3>Looking for your already created CV?</h3></header>
                <div class="well">
                    <p class="intro">Enter your e-mail. We will send you all of your CVs.</p>
                    <form action="/" method="GET">
						<input type="hidden" name="do" value="forget" />
                        <div class="control-group">
                            <div class="controls">
                                <div class="input-prepend input-append"><span class="add-on">@</span><input name="email" id="homepage-forget-email" type="text"><button class="btn" type="submit">Go!</button>                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </div>

</div>
<#include "/layout/debug.ftl">
<#include "/layout/footer.ftl">
<#include "/layout/foot.ftl">